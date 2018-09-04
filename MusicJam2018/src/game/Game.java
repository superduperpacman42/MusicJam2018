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
	public static final String NAME = "GAME JAM";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int MAX_STEP = 50000000;
	public int MIN_STEP = 10000000;
	public int windowWidth;
	public int windowHeight;
	public boolean isRunning;
	
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		this(WIDTH, HEIGHT);
	}
	
	public Game(int windowWidth, int windowHeight) {
		frame = new JFrame(NAME);
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
		
	}

	/**
	 * Draw the graphics
	 * @param g - the game's Graphics context
	 */
	public void draw(Graphics g) {
		g.fillRect(10, 10, 100, 100);
	}
}
