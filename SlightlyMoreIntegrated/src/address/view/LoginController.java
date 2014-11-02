package address.view;

import java.net.URL;
import java.util.ResourceBundle;

import address.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
	
	/*---------------------*/
	/*	FUNCTION METHODS   */
	/*---------------------*/
	public void initialize(URL location, ResourceBundle resources) {	
	}
	
	@FXML
	public void login() {
		String usernameS = username.getText();
		String passwordS = password.getText();
		Network.getInstance().SetNetInfo(usernameS, passwordS, "54.68.68.208", 4446);
		String connect = Network.getInstance().StartConnection();
		myController.setScreen(MainApplication.OPENING_SCREEN);
	}

	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}