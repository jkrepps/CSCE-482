package address;

// Imports
import javafx.scene.layout.StackPane;
import javafx.scene.*;

import java.util.HashMap;

import javafx.fxml.*;

public class ScreensController extends StackPane {
	/*--------------------*/
	/*		DATA MEMBERS  */
	/*--------------------*/
	private HashMap<String, Node> screens = new HashMap<>(); // Hash Map to keep track of screens and their respective string identifiers
	private String username;
	
	/*-----------------------*/
	/*		HELPER FUNCTIONS */
	/*-----------------------*/
	
	// Adds screen to the hash map
	public void addScreen(String id, Node screen) {
		screens.put(id, screen);
	}
	
	// Loads a screen with the given id and resource FXML file as string
	public boolean loadScreen(String id, String resource) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) loader.load();
			ControlledScreen myScreenController = ((ControlledScreen) loader.getController());
			myScreenController.setParentScreen(this);
			addScreen(id, loadScreen);
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	// Sets the currently visible screen to the screen with the corresponding name
	public boolean setScreen(final String name) {
		if(screens.get(name) != null) {		// Screen Loaded correctly
			if(!getChildren().isEmpty()) {
				getChildren().remove(0);
				getChildren().add(0, screens.get(name));
			}
			else {
				getChildren().add(screens.get(name));
			}
			return true;
		}
		else {
			System.out.println("screen has not been loaded!\n");
			return false;
		}
	}
	
	public void setUserName(String name){
			username = name;
	}
	
	public String getUserName() {
		return username;
	}
}
