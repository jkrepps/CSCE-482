// Author: Jeffrey LeRoy
// Date: 10/25/14
// Sr Capstone

package Address;

import java.io.IOException;

import Address.View.CreateController;
import Address.View.GameScreenController;
import Address.View.OpeningController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {
	/*----------------------------------*/
	/*			DATA MEMBERS			*/
	/*----------------------------------*/
	private Stage primaryS;						// Stage where all screens are laid out
	private Scene scene;						// Scene that holds what is currently being shown
	private StackPane openingScreen;			// OpeningScreen so basically an object for that
	private BorderPane createScreen;			// CreateScreen object that holds the create a game screen
	private BorderPane joinScreen;				// JoinScreen object that holds the screen to join a game
	private BorderPane gameScreen;				// GameScreen object that hold the screen of the playable game
	private GameScreenController game;				// The game screen controller
	private OpeningController open;				// The openingscreen controller
	private CreateController create;			// The createScreen controller
	
	/*----------------------------------*/
	/*			CONSTRUCTORS			*/
	/*----------------------------------*/
	public MainApp() {}
	
	/*----------------------------------*/
	/*			HELPER FUNCTIONS		*/
	/*----------------------------------*/
	// Sets all the controllers
	public void setcontrollers() {
		open = new OpeningController();
		open.setMain(this);
		open.setPane(openingScreen);
		create = new CreateController();
		create.setMain(this);
		create.setParent(createScreen);
	}
	
	// where the program starts
	public void start(Stage primaryStage) {
		setcontrollers();									// set the controllers
		this.primaryS = primaryStage;						// set the stage
		this.primaryS.setTitle("Industry of Deceit");		// give the stage a title
		setUpRootLayout();									// Set up the opening screen
	}

	// Sets up the opening screen
	public void setUpRootLayout() {
		FXMLLoader loaderO = new FXMLLoader();											// create new loader for fxml file
		loaderO.setLocation(MainApp.class.getResource("View/GameSetup.fxml"));			// set the loaders location
		
		try {
			gameScreen = (BorderPane) loaderO.load();									// load the fxml file
			scene = new Scene(gameScreen);
			primaryS = new Stage();
			primaryS.setScene(scene);
			primaryS.setFullScreen(true);
			primaryS.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Sets the create screen much like the opening screen
	public void setCreate() {
		FXMLLoader loaderC = new FXMLLoader();
		loaderC.setLocation(MainApp.class.getResource("View/CreateSetup.fxml"));
		try {
			createScreen = (BorderPane) loaderC.load();
			Scene newScene = new Scene(createScreen);
			Stage newStage = new Stage();
			newStage.setScene(newScene);
			newStage.setFullScreen(true);
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setcontrollers();
	}
	
	// Sets the screen to the waiting screen
	public void setWaiting() {
		FXMLLoader loaderC = new FXMLLoader();
		loaderC.setLocation(MainApp.class.getResource("View/CreateSetup.fxml"));
		try {
			createScreen = (BorderPane) loaderC.load();
			createScreen.getChildren().clear();
			Text waiting = new Text("Waiting for more players...");
			waiting.setFill(Color.AZURE);
			waiting.setFont(Font.font("Matura MT Script Capitals", 30));
			createScreen.setCenter(waiting);
			Scene newScene = new Scene(createScreen);
			Stage newStage = new Stage();
			newStage.setScene(newScene);
			newStage.setFullScreen(true);
			newStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Sets the screen to the join game screen
	public void setJoin() {
		FXMLLoader loaderJ = new FXMLLoader();
		loaderJ.setLocation(MainApp.class.getResource("View/JoinSetup.fxml"));
		try {
			joinScreen = (BorderPane) loaderJ.load();
			Stage newStage = new Stage();
			Scene newScene = new Scene(joinScreen);
			newStage.setScene(newScene);
			newStage.setFullScreen(true);
			newStage.show();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	// Main function
	public static void main(String[] args) {
		launch(args);
	}
}
