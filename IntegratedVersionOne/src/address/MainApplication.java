package address;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
	/*----------------------------------*/
	/*			DATA MEMBERS		 	*/
	/*----------------------------------*/
	
	private Stage primary;
	private Scene rootScene;
	private BorderPane login;
	
	/*----------------------------------*/
	/*		HELPER FUNCTIONS			*/
	/*----------------------------------*/
	
	public void setLogin() {
		FXMLLoader loginLoader = new FXMLLoader();
		loginLoader.setLocation(MainApplication.class.getResource("./View/LoginFXML.fxml"));
		try {
			login = (BorderPane) loginLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		rootScene = new Scene(login);
		primary.setScene(rootScene);
		primary.show();
	}
	
	public void setWaiting() {
		
	}
	
	public void setOpening() {
		
	}
	
	public void setCreate() {
		
	}
	
	public void setJoin() {
		
	}
	
	public void start(Stage primaryStage) {
		this.primary = primaryStage;
		this.primary.setTitle("Industry of Deceit");
		this.primary.setFullScreen(true);
		setLogin();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
