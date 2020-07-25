package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * MainMenu
 * 
 * @author Boldi
 *
 */
public class Menu extends Application {

	public static Stage primaryStage;
	protected static Parent rootmenu;
	protected static Parent rootproperty;
	protected static Parent rootleaderboards;
	protected static Scene menuScene;
	protected static Scene propertyScene;
	protected static Scene leaderboardsScene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Menu.primaryStage = primaryStage;
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/resources/snake.png"));

		rootmenu = FXMLLoader.load(getClass().getResource("/resources/Menu.fxml"));
		rootproperty = FXMLLoader.load(getClass().getResource("/resources/Properties.fxml"));

		propertyScene = new Scene(rootproperty);
		menuScene = new Scene(rootmenu);

		menuScene.getStylesheets().add("/resources/application.css");
		propertyScene.getStylesheets().add("/resources/application.css");
		primaryStage.setScene(menuScene);
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = bounds.getMinX() + (bounds.getWidth() - menuScene.getWidth()) * 0.4;
		double y = bounds.getMinY() + (bounds.getHeight() - menuScene.getHeight()) * 0.3;
		primaryStage.setX(x);
		primaryStage.setY(y);
		primaryStage.show();

	}

	/**
	 * Game start get the Scene from the Gameclass, and positioning the stage
	 */
	public static void gamestart() {
		Scene scene = null;
		scene = GameClass.gameload();
		primaryStage.setScene(scene);
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * 0.3;
		double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * 0.7;
		primaryStage.setX(x);
		primaryStage.setY(y);
	}

	/**
	 * start the menu
	 */
	public static void menustart() {
		primaryStage.setScene(menuScene);

	}

	/**
	 * start the settings tab
	 */
	public static void propertiesstart() {
		primaryStage.setScene(propertyScene);

	}

}
