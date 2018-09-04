package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	
	private JFrame frame;
	public Camera camera;
	public static final String NAME = "GAME JAM";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int MAX_STEP = 50000000;
	public static final int FRAME_RATE = 12;
	public int MIN_STEP = 10000000;
	public int windowWidth;
	public int windowHeight;
	public boolean isRunning;
	
	public int delete_this_variable = 0;
	public double test_local_time = 0;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		this(WIDTH, HEIGHT);
	}
	
	public Game(int windowWidth, int windowHeight) {
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
		
		test_local_time += dt;
		delete_this_variable = (int) (1480/3*(test_local_time%3)-100);

	}

	/**
	 * Draw the graphics
	 * @param g - the game's Graphics context
	 */
	public void draw(Graphics g) {
		g.fillRect(delete_this_variable + 10, 10, 100, 100);
		g.fillRect(1180 - delete_this_variable - 10, 120, 100, 100);
		g.fillRect(delete_this_variable + 10, 230, 100, 100);
		g.fillRect(1180 - delete_this_variable - 10, 340, 100, 100);
		g.fillRect(delete_this_variable + 10, 450, 100, 100);
		g.fillRect(1180 - delete_this_variable - 10, 560, 100, 100);
	}
}
