package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * Controller to the settings tab
 * @author Boldi
 *
 */
public class PropertiesController implements Initializable {

	@FXML
	TextField username_text;
	@FXML
	RadioButton small_radio;
	@FXML
	RadioButton medium_radio;
	@FXML
	RadioButton large_radio;
	@FXML
	RadioButton easy_radio;
	@FXML
	RadioButton normal_radio;
	@FXML
	RadioButton hard_radio;
	@FXML
	RadioButton god_radio;
	@FXML
	Button back_btn;
	@FXML
	Button save_btn;
	@FXML
	ToggleGroup size;
	@FXML
	ToggleGroup difficulty;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GamePreferencies gp = PropertiesWriteRead.getproperites();
		username_text.setText(gp.getUsername());
		switch (gp.getSize()) {
		case SMALL:
			small_radio.setSelected(true);
			break;
		case MEDIUM:
			medium_radio.setSelected(true);
			break;
		case BIG:
			large_radio.setSelected(true);
			break;
		default: 
			small_radio.setSelected(true);
			break;
		}

		switch (gp.getDif()) {
		case EASY:
			easy_radio.setSelected(true);
			break;
		case MEDIUM:
			normal_radio.setSelected(true);
			break;
		case HARD:
			hard_radio.setSelected(true);
			break;
		case GOD:
			god_radio.setSelected(true);
			break;
		default: 
			easy_radio.setSelected(true);
			break;
		}
		back_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Menu.menustart();
			}

		});
		save_btn.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				GamePreferencies gp = new GamePreferencies();
				gp.setSize(GamePreferencies.boardSize.valueOf(((RadioButton) size.getSelectedToggle()).getText().toUpperCase()));				
				gp.setDif(GamePreferencies.difficulty.valueOf(((RadioButton) difficulty.getSelectedToggle()).getText().toUpperCase()));
				gp.setUsername(username_text.getText());
				PropertiesWriteRead.writePropertiesFile(gp);
				Menu.menustart();
			}

		});

	}

}
