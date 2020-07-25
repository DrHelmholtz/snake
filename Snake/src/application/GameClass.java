package application;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Server.Database;
import Server.Points;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Setup the game canvas, Keyframe, and the game motor
 * @author Boldi
 *
 */
public class GameClass {

	// Game variable
	static int speed = 5;
	static int foodcolor = 0;
	static int width = 20;
	static int height = 20;
	static int foodX = 0;
	static int foodY = 0;
	static int cornersize = 20;
	static List<Corner> snake = new ArrayList<>();
	static Dir direction = Dir.left;
	static boolean gameOver = false;
	static boolean paused = false;
	static Random rand = new Random();
	static Dir lastDirection = Dir.left;
	
	private static int score = 0;
	private static GamePreferencies preferencies;

	public enum Dir {
		left, right, up, down
	}
	/**
	 * Read the game properties, and set the size and the difficulty
	 */
	public static void startGame() {
		preferencies = PropertiesWriteRead.getproperites();
		switch (preferencies.getSize()) {
		case SMALL:
			width = 20;
			height = 20;
			break;
		case MEDIUM:
			width = 30;
			height = 30;
			break;
		case BIG:
			width = 50;
			height = 30;
			break;
		}
		switch (preferencies.getDif()) {
		case EASY:
			speed = 5;
			break;
		case MEDIUM:
			speed = 7;
			break;
		case HARD:
			speed = 7;
			break;
		case GOD:
			speed = 99999;
			break;
		}

	}

	public static class Corner {
		int x;
		int y;

		public Corner(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}
/**
 * Set up the game scene and the basic values.
 * @return Game Scene
 */
	public static Scene gameload() {
		try {
			startGame();


			score = 0;
			gameOver = false;
			paused = false;
			snake = new ArrayList<>();
			lastDirection = Dir.left;
			direction = Dir.left;
			newFood();
			VBox root = new VBox();
			Canvas c = new Canvas(width * cornersize, height * cornersize);
			GraphicsContext gc = c.getGraphicsContext2D();
			root.getChildren().add(c);

			float framerate = (float) 1 / speed;
			KeyFrame frame = new KeyFrame(Duration.seconds(framerate), event -> {
				tick(gc);
			});
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(frame);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();

			Scene scene = new Scene(root, width * cornersize, height * cornersize);
			// key control
			scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
				if (key.getCode() == KeyCode.W) {
					if (direction != Dir.down)
						direction = Dir.up;
				}
				if (key.getCode() == KeyCode.A) {
					if (direction != Dir.right)
						direction = Dir.left;
				}
				if (key.getCode() == KeyCode.S) {
					if (direction != Dir.up)
						direction = Dir.down;
				}
				if (key.getCode() == KeyCode.D) {
					if (direction != Dir.left)
						direction = Dir.right;
				}
				if (key.getCode() == KeyCode.SPACE) {
					if (!paused) {
						paused = !paused;
						timeline.pause();
						if (!gameOver) {
							gc.setFill(Color.WHITE);
							gc.setFont(new Font("", 50));
							gc.fillText("PAUSED", width * cornersize / 2 - 90, height * cornersize / 2);
						}
					} else {
						paused = !paused;
						timeline.play();
					}
				}if (key.getCode() == KeyCode.ESCAPE) {
					if (gameOver) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
						Database.saveDatabase(new Points(score, preferencies.getUsername(), format.format(new Date())));
						timeline.stop();

						Menu.menustart();
						
					}
				}
			});

			// add start snake parts
			snake.add(new Corner(width / 2, height / 2));
			snake.add(new Corner(width / 2, height / 2));
			snake.add(new Corner(width / 2, height / 2));

			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Process during 1frame
	 * @param gc GraphicsContext to the game
	 */
	public static void tick(GraphicsContext gc) {

		if (gameOver) {
			gc.setFill(Color.RED);
			gc.setFont(new Font("", 50));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText("GAME OVER", width * cornersize / 2 , height * cornersize / 2);
			gc.setFill(Color.WHITE);
			gc.setFont(new Font("", 20));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText("Press \"ESC\" to return to the main menu", width * cornersize / 2, height * cornersize / 2+35);
			
			return;
		}

		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}

		switch (direction) {
		case up:
			if (lastDirection != Dir.down) {
				snake.get(0).y--;
				if (snake.get(0).y < 0) {
					gameOver = true;
					System.out.println("WALL HIT");
				}
			}
			break;
		case down:
			if (lastDirection != Dir.up) {
				snake.get(0).y++;
				if (snake.get(0).y > height) {
					gameOver = true;
					System.out.println("WALL HIT");
				}
			}
			break;
		case left:
			if (lastDirection != Dir.right) {
				snake.get(0).x--;
				if (snake.get(0).x < 0) {
					gameOver = true;
					System.out.println("WALL HIT");
				}
			}
			break;
		case right:
			if (lastDirection != Dir.left) {
				snake.get(0).x++;
				if (snake.get(0).x > width) {
					gameOver = true;
					System.out.println("WALL HIT");
				}
			}
			break;

		}
		lastDirection = direction;
		// eat food
		if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
			snake.add(new Corner(-1, -1));
			newFood();
			score++;
		}

		// self destroy
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				gameOver = true;
				System.out.println("SELF DESTROY");
			}
		}

		// fill
		// background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width * cornersize, height * cornersize);

		// score
		gc.setFill(Color.WHITE);
		gc.setFont(new Font("", 30));
		gc.fillText("Score: " + score, 10, 30);

		// random foodcolor
		Color cc = Color.WHITE;

		switch (foodcolor) {
		case 0:
			cc = Color.PURPLE;
			break;
		case 1:
			cc = Color.LIGHTBLUE;
			break;
		case 2:
			cc = Color.YELLOW;
			break;
		case 3:
			cc = Color.PINK;
			break;
		case 4:
			cc = Color.ORANGE;
			break;
		}
		gc.setFill(cc);
		gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);

		// snake
		for (Corner c : snake) {
			gc.setFill(Color.LIGHTGREEN);
			gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);
			gc.setFill(Color.GREEN);
			gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 2, cornersize - 2);

		}

	}

	/**
	 * This will create a random food on the canvas
	 */
	public static void newFood() {
		start: while (true) {
			foodX = rand.nextInt(width);
			foodY = rand.nextInt(height);

			for (Corner c : snake) {
				if (c.x == foodX && c.y == foodY) {
					continue start;
				}
			}
			foodcolor = rand.nextInt(5);
			break;

		}
	}

}
