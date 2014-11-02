package address.view;

import address.MainApplication;
import javafx.fxml.*;
import javafx.scene.control.*;
public class WaitingController implements ControlledScreen {
	/*-------------------------------*/
	/*		DATA MEMBERS			 */
	/*-------------------------------*/
	private ScreensController myController;
	@FXML
	private Button back;
	
	/*------------------------------*/
	/*		HELPER FUNCTIONS		*/
	/*------------------------------*/
	@FXML
	public void goBack() {
		myController.setScreen(MainApplication.OPENING_SCREEN);
	}
	
	@Override
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;	
	}

}