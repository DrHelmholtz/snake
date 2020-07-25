package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Server.Points;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller to the leaderboards tab
 * @author Boldi
 *
 */
public class LeaderboardsController implements Initializable{

	@FXML
	TableView<Points> score_table;
	@FXML
	TableColumn<Points,String> username;
	@FXML
	TableColumn<Points,String> date;
	@FXML
	TableColumn<Points,Integer> point;
	@FXML
	Button leader_back_btn;
	
	private ObservableList<Points> scores=FXCollections.observableArrayList();
	 /**
	  * Initialize the Leaderboards tab
	  */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		username.setCellValueFactory(new PropertyValueFactory<Points,String>("username"));
		date.setCellValueFactory(new PropertyValueFactory<Points,String>("date"));
		point.setCellValueFactory(new PropertyValueFactory<Points,Integer>("point"));
		score_table.setItems(scores);
		
		leader_back_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Menu.menustart();
			}			
		});
		
	}
	/**
	 * Set data in the tableview in the leaderboards tab
	 * @param lp List that contains all of the username, date and score
	 * */
	public void initData(List<Points> lp){
		scores=FXCollections.observableArrayList(lp);
		score_table.setItems(scores);
	}

}
