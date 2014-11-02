package address.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import address.MainApplication;

public class JoinController implements ControlledScreen {
	/*-------------------------------------------*/
	/*			DATA MEMBERS					 */
	/*-------------------------------------------*/
	java.net.URL buttonSound = getClass().getResource("./Sounds/buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("./Sounds/buttonSound.mp3");
	private AudioClip buttonDrag = new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());
	private ScreensController myController;
	@FXML
	private Button join;
	@FXML
	private Button back;
	
	/*-------------------------------------------*/
	/*		CONSTRUCTORS						 */
	/*-------------------------------------------*/
	public JoinController() {}
	
	/*--------------------------------------------*/
	/*			FXML FUNCTIONS					  */
	/*--------------------------------------------*/
	@FXML
	public void initialize() {
		// When mouse dragged over button play sound and change effect
		join.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				join.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
				buttonDrag.setVolume(.3);
				buttonDrag.play();
			}
		});
		
		// When mouse dragged off button clear effect
		join.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				join.setEffect(null);
			}
		});
		
		// When mouse clicked go to waiting screen
		join.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.7);
				buttonClick.play();
				myController.setScreen(MainApplication.WAITING_SCREEN);
			}
		});
		
		// When mouse dragged over button play sound and change effect
		back.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				back.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
				buttonDrag.setVolume(.3);
				buttonDrag.play();
			}
		});
		
		// When mouse dragged off button clear effect
		back.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				back.setEffect(null);
			}
		});
		
		// When button is clicked play sound and go back to opening screen
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.7);
				buttonClick.play();
				myController.setScreen(MainApplication.OPENING_SCREEN);
			}
		});
	}
	
	// Fixes an error
	@FXML public void handle() {}
	
	/*-----------------------------------------------*/
	/*			HELPER FUNCTIONS					 */
	/*-----------------------------------------------*/
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}
