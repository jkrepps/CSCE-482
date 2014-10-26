// Author: Jeffrey LeRoy
// Date: 10/25/14
// Sr Capstone Design

package Address.View;
import Address.MainApp;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class CreateController {
	/*------------------------------------*/
	/*			DATA MEMBERS			  */
	/*------------------------------------*/
	
	@FXML
	private Button submit;
	@FXML
	private Button back;
	
	java.net.URL buttonSound = getClass().getResource("./Sounds/buttons.aiff");
	java.net.URL buttonSound2 = getClass().getResource("./Sounds/buttonSound.mp3");
	private AudioClip buttonDrag = new AudioClip(buttonSound.toString());
	private AudioClip buttonClick = new AudioClip(buttonSound2.toString());
	private MainApp mainApp = new MainApp();
	public BorderPane parentPane = new BorderPane();

	/*------------------------------------*/
	/*			CONSTRUCTORS			  */
	/*------------------------------------*/
	
	public CreateController() {}
	
	/*------------------------------------*/
	/*			FXML FUNCTIONS			  */
	/*------------------------------------*/
	
	@FXML
	public void initialize() {
		// when mouse drug over play sound and change effect
		submit.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonDrag.setVolume(.3);
				buttonDrag.play();
				submit.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
			}
		});
		
		// when mouse drug off reset effect to previous
		submit.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				submit.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.AZURE, 15.17, 0, 15, 0));
			}
		});
		
		// On mouse click of submit then takes user to the waiting screen
		submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				mainApp.setWaiting();
				buttonClick.setVolume(.7);
				buttonClick.play();
			}
		});
		
		// On mouse drag onto button change effect and play sound
		back.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonDrag.setVolume(.3);
				buttonDrag.play();
				back.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, .7, 5, 5));
			}
		});
		
		// On mouse drag exit change effect back
		back.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				submit.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.AZURE, 15.17, 0, 15, 0));
			}
		});
		
		// When clicked go back to the opening screen
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				buttonClick.setVolume(.7);
				buttonClick.play();
				mainApp.setUpRootLayout();
			}
		});
	}
	
	// solves an error
	@FXML public void handle() {}
	
	/*------------------------------------*/
	/*			HELPER FUNCTIONS		  */
	/*------------------------------------*/
	
	public void setMain(MainApp m) {
		this.mainApp = m;
	}
	
	public void setParent(BorderPane p) {
		this.parentPane = p;
	}
}