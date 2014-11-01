package address.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	/*------------------*/
	/*	DATA MEMBERS	*/
	/*------------------*/
	@FXML
	private Button loginButton;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	
	/*---------------------*/
	/*	FUNCTION METHODS   */
	/*---------------------*/
	public void initialize(URL location, ResourceBundle resources) {	
	}
	
	@FXML
	public void login() {
		String usernameS = username.getText();
		String passwordS = password.getText();
		System.out.println(usernameS + "\t\t" + passwordS);
	}
}
