package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Server.Client;
import Server.Points;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller to the Menu.java
 * @author Boldi
 *
 */
public class MenuController implements Initializable{

	@FXML
	Button startgame_btn;
	@FXML
	Button exit_btn;
	@FXML
	Button properties_btn;
	@FXML
	Button leaderboards_btn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		startgame_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Menu.gamestart();
			}			
		});
		
		exit_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
			    Platform.exit();				
			}			
		});
		
		properties_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Menu.propertiesstart();
			}			
		});
		
		leaderboards_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event){
				List<Points> lp=Client.getPoints();
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("/resources/Leaderboards.fxml"));
				try {  
					Parent leaderboardsParent=loader.load();
					Scene leaderboardsScene=new Scene(leaderboardsParent);
					leaderboardsScene.getStylesheets().add("/resources/application.css");
					LeaderboardsController lc=loader.getController();
					lc.initData(lp);
					Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			        
			        window.setScene(leaderboardsScene);
			        window.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}			
		});
	}
	
	
}
