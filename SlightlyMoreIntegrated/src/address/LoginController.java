package address;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import address.MainApplication;

public class LoginController implements Initializable, ControlledScreen {
	/*------------------*/
	/*	DATA MEMBERS	*/
	/*------------------*/
	@FXML
	private Button loginButton;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	
	private ScreensController myController;
	java.net.URL buttonSound = getClass().getResource("buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("buttonSound.mp3");
	private AudioClip buttonDrag = new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());
	
	/*---------------------*/
	/*	FUNCTION METHODS   */
	/*---------------------*/
	public void initialize(URL location, ResourceBundle resources) {	
	}
	
	@FXML
	public void login() {
		buttonClick.setVolume(.7);
		buttonClick.play();
		String usernameS = username.getText();
		String passwordS = password.getText();
		Network.getInstance().SetNetInfo(usernameS, passwordS, "54.68.68.208", 4446);
		String connect = Network.getInstance().StartConnection();
		myController.setUserName(usernameS);
		myController.setScreen(MainApplication.OPENING_SCREEN);
	}

	@FXML
	public void mouseOver() {
		buttonDrag.setVolume(.4);
		buttonDrag.play();
		loginButton.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
	}
	
	@FXML
	public void mouseExit() {
		loginButton.setEffect(null);
	}
	
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}
