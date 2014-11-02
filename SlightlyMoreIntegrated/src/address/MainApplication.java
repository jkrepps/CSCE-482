package address;
import address.view.Network;
import address.view.ScreensController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
public class MainApplication extends Application {
	/*----------------------------------*/
	/*			DATA MEMBERS		 	*/
	/*----------------------------------*/
	public static final String LOGIN_SCREEN = "login";
	public static final String LOGIN_SCREEN_FXML = "LoginFXML.fxml";
	public static final String OPENING_SCREEN = "opening";
	public static final String OPENING_SCREEN_FXML = "OpeningFXML.fxml";
	public static final String CREATE_SCREEN = "create";
	public static final String CREATE_SCREEN_FXML = "CreateFXML.fxml";
	public static final String JOIN_SCREEN = "join";
	public static final String JOIN_SCREEN_FXML = "JoinFXML.fxml";
	public static final String HOWTO_SCREEN = "howto";
	public static final String HOWTO_SCREEN_FXML = "HowToFXML.fxml";
	public static final String GAME_SCREEN = "game";
	public static final String GAME_SCREEN_FXML = "GameFXML.fxml";
	public static final String WAITING_SCREEN = "waiting";
	public static final String WAITING_SCREEN_FXML = "WaitingFXML.fxml";
		
	/*----------------------------------*/
	/*		HELPER FUNCTIONS			*/
	/*----------------------------------*/

	public void start(Stage primaryStage) {
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(MainApplication.LOGIN_SCREEN, MainApplication.LOGIN_SCREEN_FXML);
		mainContainer.loadScreen(MainApplication.OPENING_SCREEN, MainApplication.OPENING_SCREEN_FXML);
		mainContainer.loadScreen(MainApplication.CREATE_SCREEN, MainApplication.CREATE_SCREEN_FXML);
		mainContainer.loadScreen(MainApplication.JOIN_SCREEN, MainApplication.JOIN_SCREEN_FXML);
		mainContainer.loadScreen(MainApplication.HOWTO_SCREEN, MainApplication.HOWTO_SCREEN_FXML);
		mainContainer.loadScreen(MainApplication.GAME_SCREEN, MainApplication.GAME_SCREEN_FXML);
		mainContainer.loadScreen(MainApplication.WAITING_SCREEN, MainApplication.WAITING_SCREEN_FXML);
		
		mainContainer.setScreen(MainApplication.LOGIN_SCREEN);
		
		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("Industry of Deceit");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
