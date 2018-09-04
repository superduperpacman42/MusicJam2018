package game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

/**
 * Handles animation of GameObjects
 */
public abstract class Sprite {
	
	public int framerate, width, height, duration;
	private static HashMap<String, SpriteSheet> animations = new HashMap<String, SpriteSheet>();
	private Image frame;
	private String state;
	private double t;
	
	/**
	 * Instantiates the sprite's animations
	 * @param frames - array of frame counts
	 * @param columns - array of column counts
	 * @param animations - array of filenames (.png is assumed by default)
	 */
	public Sprite(int[] frames, int[] columns, String... animations) {
		for(int i=0; i<animations.length; i++) {
			loadAnimation(animations[i], columns[i], frames[i]);
		}
		framerate = Game.FRAME_RATE;
	}
	
	/**
	 * Render the sprite centered at (0,0)
	 * @param g - the Graphics context centered on the sprite
	 * @param object - the GameObject possessing the sprite
	 */
	public void draw(Graphics g, GameObject object) {
		if(frame==null||state=="") return; // error detection
		g.drawImage(frame, -width/2, -height/2, width, height, null);
	}
	
	/**
	 * Adds a sprite sheet to the dictionary
	 * @param filename - filename of animation (.png extension by default)
	 * @param columns - number of columns in the sprite sheet
	 * @param frames - total number of frames in the sprite sheet
	 */
	public static void loadAnimation(String filename, int columns, int frames) {
		int i = filename.indexOf(".");
		String name = i>0?filename.substring(0, filename.indexOf(".")):filename;
		if(animations.containsKey(name)) {
			return; // duplicate detection
		}
		animations.put(name, new SpriteSheet(i>0?filename:name+".png", columns, frames));
	}
	
	/**
	 * Updates the animation by a specified time step
	 * @param state - the desired animation
	 * @param dt - the time step in seconds
	 * @return true if the animation completed
	 */
	public boolean animate(String state, double dt) {
		if(!animations.containsKey(state)) {
			System.err.println("Animation not found: "+state);
			return false; // Error detection
		}
		t += dt; // Update elapsed time
		if(!state.equals(this.state)) {
			t = 0;
			this.state = state; // Change animations
		}
		SpriteSheet animation = animations.get(state);
		width = animation.width;
		height = animation.height;
		duration = animation.frames*framerate;
		frame = animation.getFrame((int)(t*framerate)%animation.frames);
		if(t >= duration) {
			t = 0; // Loop animation
			return true;
		} else {
			return false;
		}
	}
}
