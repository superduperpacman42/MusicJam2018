package game;

import java.awt.Graphics;

/**
 * A game element capable of displaying animations
 */
public abstract class GameObject implements Comparable<GameObject> {
	
	protected Sprite sprite;
	public double x, y;
	public int z, w, h;
	public boolean visible = true;
	
	/**
	 * Animations that Sprites can display
	 */
	public enum Animations {
		HYDRA("hydra", 4, 4);
		
		public String filename;
		public int columns;
		public int frames;
		private Animations(String filename, int columns, int frames) {
			this.filename = filename;
			this.columns = columns;
			this.frames = frames;
		}
	}
	
	/**
	 * Superclass constructor
	 * @param x - initial x-coordinate in pixels
	 * @param y - initial y-coordinate in pixels
	 * @param z - relative layer to display sprite on-screen
	 * @param w - width of GameObject for collisions in pixels
	 * @param h - height of GameObject for collisions in pixels
	 */
	public GameObject(double x, double y, int z, int w, int h) {
		this.sprite = new Sprite();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Updates the sprite behavior and animation
	 * @param dt - elapsed time in seconds
	 * @return An array containing horizontal and vertical displacement in pixels
	 */
	public abstract double[] update(double dt);
	
	/**
	 * Checks for ability to collide with another object
	 * @param obj - GameObject being collided with
	 * @return If a collision is allowed between the two GameObjects
	 */
	public abstract boolean isCollidable(GameObject obj);
	
	/**
	 * Renders the sprite
	 * @param g - the Graphics context with appropriate translation and scaling
	 */
	public void draw(Graphics g) {
		if(!visible) return;
		g.translate((int)x, (int)y);
		sprite.draw(g, this);
		g.translate((int)-x, (int)-y);
	}
	
	public double[] collide(double dx, double dy, GameObject obj) {
		return new double[]{dx, dy};
	}
	
	/**
	 * Move GameObject by a set distance
	 * @param dx - horizontal movement in pixels
	 * @param dy - vertical movement in pixels
	 */
	public void move(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	@Override
	public int compareTo(GameObject obj) {
		return z - obj.z;
	}
}