package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	public static final int HEIGHT = 755;
	public static final int MAX_STEP = 50000000;
	public static final int FRAME_RATE = 12;
	public int MIN_STEP = 10000000;
	public int windowWidth;
	public int windowHeight;
	public boolean isRunning;
	
	public int delete_this_variable = 0;
	public double test_local_time = 0;
	
	public static void main(String[] args) {
		AudioPlayer.initTestProgram();
//		Game game = new Game();
//		game.start();
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
		

	}

	/**
	 * Draw the graphics
	 * @param g - the game's Graphics context
	 */
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		camera.set_target_pos(-640, -360);
		camera.zoom.set_target_value(2.0);
		System.out.println(camera.get_zoom());
		g2.scale(camera.get_zoom(), camera.get_zoom());
		g2.translate((int)(camera.get_x_pos() + WIDTH/(2*camera.get_zoom())), 
				(int)(camera.get_y_pos() + HEIGHT/(2*camera.get_zoom())));
		
		int xs[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		int ys[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		for (int x : xs) {
			for (int y : ys) {
				if ((x+y)%2 == 0) {
					int sq_size = 80;
					g.setColor(Color.lightGray);
					g.fillRect(sq_size*x, sq_size*y, sq_size, sq_size);
				}
			}
		}
	}
}
