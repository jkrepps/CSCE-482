package address.view;

import address.MainApplication;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;

public class HowToController implements ControlledScreen {
	/*-------------------------------------------*/
	/*			DATA MEMBERS				     */
	/*-------------------------------------------*/
	private ScreensController myController;
	@FXML
	private Button back;
	java.net.URL buttonSound = getClass().getResource("./Sounds/buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("./Sounds/buttonSound.mp3");
	private AudioClip buttonDrag =  new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());
	
	/*-------------------------------------------*/
	/*			HELPER FUNCTIONS				 */
	/*-------------------------------------------*/
	
	@FXML
	public void goBack() {
		myController.setScreen(MainApplication.OPENING_SCREEN);
	}
	
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}
