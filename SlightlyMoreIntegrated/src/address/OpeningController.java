package address;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import address.MainApplication;

public class OpeningController implements ControlledScreen {
	/*------------------------------------------*/
	/*			DATA MEMBERS					*/
	/*------------------------------------------*/
	
	java.net.URL buttonSound = getClass().getResource("buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("buttonSound.mp3");
	private AudioClip buttonDrag = new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());
	private ScreensController myController;
	
	@FXML
	private Button createGame;
	@FXML
	private Button joinGame;
	@FXML
	private Button howToPlay;
	
	/*------------------------------------------*/
	/*			CONSTRUCTORS					*/
	/*------------------------------------------*/
	
	public OpeningController() {}
	
	/*------------------------------------------*/
	/*			FXML FUNCTIONS					*/
	/*------------------------------------------*/
	
	@FXML
	public void initialize() {
		// Plays sound when moust dragged over button and sets effect
		createGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				createGame.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.AZURE, 10, .9, 0, 0));
				buttonDrag.setVolume(.3);
				buttonDrag.play();
			}
		});
		
		// when mouse is drug off button effect goes back
		createGame.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				createGame.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15.17, 0, 15, 5 ));
			}
		});
		
		// if button is clicked play the click sound and change to create screen
		createGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.4);
				buttonClick.play();
				myController.setScreen(MainApplication.CREATE_SCREEN);
			}
		});
		
		// if mouse dragged over button play sounds and set effect
		joinGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				joinGame.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.AZURE, 10, .9, 0, 0));
				buttonDrag.setVolume(.3);
				buttonDrag.play();
			}
		});
		
		// if mouse is drug off button play sounds and set effect back
		joinGame.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				joinGame.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15.17, 0, 15, 5 ));
			}
		});
		
		// when clicked play click sound and go to join game screen
		joinGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.4);
				buttonClick.play();
				myController.setScreen(MainApplication.JOIN_SCREEN);
			}
		});
	}
	// just to get rid of an error
	@FXML public void handle() {}
	
	/*---------------------------------------------*/
	/*			HELPER FUNCTIONS				   */
	/*---------------------------------------------*/
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}
