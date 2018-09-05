package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.GameObject.Animations;

public class Game {
	
	private JFrame frame;
	public Camera camera;
	public static final String NAME = "GAME JAM";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 755;
	public static final int MAX_STEP = 50000000;
	public static final int FRAME_RATE = 12;
	public int MIN_STEP = 10000000;
	public int windowWidth;
	public int windowHeight;
	public boolean isRunning;
	
	public int delete_this_variable = 0;
	public double test_local_time = 0;
	
	public ArrayList<GameObject> sprites = new ArrayList<GameObject>();
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		this(WIDTH, HEIGHT);
	}
	
	@SuppressWarnings("serial")
	public Game(int windowWidth, int windowHeight) {
		loadAllAnimations();
		frame = new JFrame(NAME);
		camera = new Camera();
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		frame.setSize(windowWidth, windowHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				draw(g);
			}
		});
		sprites.add(new Hydra(100, 100));
		AudioPlayer music = new AudioPlayer("OpenSource.wav");
		music.play();
	}
	
	/**
	 * Start the timer
	 */
	public void start() {
		isRunning = true;
		long then = System.nanoTime();
		long now = then;
		long dt;
		while (isRunning) {
			now = System.nanoTime();
			dt = now - then;
			then = now;
			if (dt > 0) {
				update(Math.min(MAX_STEP, dt)/1000000000.0);
			}
			frame.repaint();
			try{
				Thread.sleep(Math.max((MIN_STEP - (System.nanoTime()-then))/1000000, 0));
			} catch (Exception e) {}
		}
	}

	/**
	 * Update the game model
	 * @param dt - elapsed time in seconds
	 */
	public void update(double dt) {
		// Put code for user interface before camera update, so slowdowns
		// don't affect UI elements.
		
		dt = camera.update(dt);	//	dt changes values here based on camera speed
		Collections.sort(sprites);
		for(GameObject sprite:sprites) {
			double[] movement = sprite.update(dt);
			double dx = movement[0];
			double dy = movement[1];
			for(GameObject sprite2:sprites) {
				if(sprite2!=sprite && sprite2.isCollidable(sprite)) {
//					sprite.collide()
				}
				sprite.move(dx, dy);
			}
		}
	}

	/**
	 * Draw the graphics
	 * @param g - the game's Graphics context
	 */
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		long xfoc = (int)(Math.sin(System.nanoTime()/500000000.0)*(500)) - 640;
		long yfoc = (int)(Math.cos(System.nanoTime()/750000000.0)*(320)) - 360;
		double zoom = Math.sin(System.nanoTime()/2000000000.0)*0.5 + 1.0;
		camera.set_target_pos(xfoc, yfoc);
		camera.zoom.set_target_value(zoom);
		g2.scale(camera.get_zoom(), camera.get_zoom());
		g2.translate((int)(camera.get_x_pos() + WIDTH/(2*camera.get_zoom())), 
				(int)(camera.get_y_pos() + HEIGHT/(2*camera.get_zoom())));
		
		int sq_size = 80;
		int xs[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		int ys[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		for (int x : xs) {
			for (int y : ys) {
				if ((x+y)%2 == 0) {
					g.setColor(Color.lightGray);
					g.fillRect(sq_size*x, sq_size*y, sq_size, sq_size);
				} else {
					g.setColor(Color.WHITE);
					g.fillRect(sq_size*x, sq_size*y, sq_size, sq_size);
				}
			}
		}
		for (GameObject sprite:sprites) {
			sprite.draw(g);
		}
	}
	
	/**
	 * Loads all animation files
	 */
	public static void loadAllAnimations() {
		for(Animations a:Animations.values()) {
			Sprite.loadAnimation(a);
		}
	}
}
