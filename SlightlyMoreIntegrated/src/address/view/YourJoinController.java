package address.view;

import java.util.Vector;
import address.MainApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

public class YourJoinController implements ControlledScreen {
	
	/*---------------------*/
	/*	DATA MEMBERS	   */
	/*---------------------*/
	java.net.URL buttonSound = getClass().getResource("./Sounds/buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("./Sounds/buttonSound.mp3");
	private AudioClip buttonDrag = new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());
	private ScreensController myController;
	
	@FXML
	private Button back;
	@FXML
	private Button findGames;
	@FXML
	private ScrollPane gamesPane;
	@FXML
	private VBox gamesList;
	
	/*----------------------------------*/
	/*		HELPER FUNCTIONS			*/
	/*----------------------------------*/
	
	// Sets the parent screen
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;	
	}
	
	// Plays the button sound for when the mouse is dragged over a button
	@FXML
	public void mouseEnter() {
		buttonDrag.setVolume(.4);
		buttonDrag.play();
	}
	
	// Plays the button sound for when the mouse is clicked
	public void mouseClick() {
		buttonClick.setVolume(.7);
		buttonClick.play();
	}
	
	// Sets the screen back to the main screen
	@FXML
	public void backClick() {
		mouseClick();
		myController.setScreen(MainApplication.OPENING_SCREEN);
	}
	
	// Dynamically gets the players games from the server
	@FXML
	public void findGame() {
		GameData.getInstance().setYourGames();								 // Get the game data from the server
		Vector<Button> games = GameData.getInstance().getYourGames();		// Acquire data just acquired for use
		for(int i=0; i<games.size(); ++i) {
			// Set EventHandler for all the games if they are clicked
			games.elementAt(i).setOnMouseClicked(new EventHandler<MouseEvent> (){
				public void handle(MouseEvent t) {
					
				}
			});
		}
		gamesList.getChildren().addAll(games);								// Add the games to the VBox that shows the games in the UI
	}
}
