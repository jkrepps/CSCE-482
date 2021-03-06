package address;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import address.MainApplication;

public class CreateController implements ControlledScreen {
	/*------------------------------------*/
	/*			DATA MEMBERS			  */
	/*------------------------------------*/
	ScreensController myController;
	@FXML
	private Button submit;
	@FXML
	private Button back;
	@FXML
	private TextField name;
	@FXML
	private TextField playersNum;
	@FXML
	private TextField length;
	
	java.net.URL buttonSound = getClass().getResource("buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("buttonSound.mp3");
	private AudioClip buttonDrag = new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());

	/*------------------------------------*/
	/*			CONSTRUCTORS			  */
	/*------------------------------------*/
	
	public CreateController() {}
	
	/*------------------------------------*/
	/*			FXML FUNCTIONS			  */
	/*------------------------------------*/
	
	@FXML
	public void initialize() {
		// when mouse drug over play sound and change effect
		submit.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonDrag.setVolume(.3);
				buttonDrag.play();
				submit.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
			}
		});
		
		// when mouse drug off reset effect to previous
		submit.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				submit.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.AZURE, 15.17, 0, 15, 0));
			}
		});
		
		// On mouse click of submit then takes user to the waiting screen
		submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.7);
				buttonClick.play();
				String nameG = name.getText();
				String pNums = playersNum.getText();
				String len = length.getText();
				Network.getInstance().SendMessage("newgame\t" + pNums + '\t' + len);
				Network.getInstance().RecieveMessage();
				myController.setScreen(MainApplication.GAME_SCREEN);
			}
		});
		
		// On mouse drag onto button change effect and play sound
		back.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonDrag.setVolume(.3);
				buttonDrag.play();
				back.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
			}
		});
		
		// On mouse drag exit change effect back
		back.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				back.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.AZURE, 15.17, 0, 15, 0));
			}
		});
		
		// When clicked go back to the opening screen
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.7);
				buttonClick.play();
				myController.setScreen(MainApplication.OPENING_SCREEN);
			}
		});
	}
	
	// solves an error
	@FXML public void handle() {}
	
	/*------------------------------------*/
	/*			HELPER FUNCTIONS		  */
	/*------------------------------------*/
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}
