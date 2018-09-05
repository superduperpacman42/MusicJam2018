package game;

import java.awt.Graphics;

/**
 * A game element capable of displaying animations
 */
public abstract class GameObject {
	
	protected Sprite sprite;
	public int x, y;
	public boolean visible = true;
	
	/**
	 * Superclass constructor
	 * @param sprite - a Sprite object instantiated by a subclass
	 * @param x - initial x-coordinate in pixels
	 * @param y - initial y-coordinate in pixels
	 */
	public GameObject(Sprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Updates the sprite behavior and animation
	 * @param dt - elapsed time in seconds
	 */
	public abstract void update(double dt);
	
	/**
	 * Renders the sprite
	 * @param g - the Graphics context with appropriate translation and scaling
	 */
	public void draw(Graphics g) {
		if(!visible) return;
		g.translate(x, y);
		sprite.draw(g, this);
		g.translate(-x, -y);
	}
}