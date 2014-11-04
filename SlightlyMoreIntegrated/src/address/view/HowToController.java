package address.view;

import address.MainApplication;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

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
		buttonClick.setVolume(.7);
		buttonClick.play();
		myController.setScreen(MainApplication.OPENING_SCREEN);
	}
	
	@FXML
	public void mouseOver() {
		buttonDrag.setVolume(.4);
		buttonDrag.play();
		back.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
	}
	
	@FXML
	public void mouseExit() {
		back.setEffect(null);
	}
	
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;
	}
}
