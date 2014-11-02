package address.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class GameScreenController implements ControlledScreen {
	/*------------------------------------*/
	/*			DATA MEMBERS			  */
	/*------------------------------------*/
	public Calendar time;
	public SimpleDateFormat format;
	private ScreensController myController;
	@FXML
	private VBox inventionsList;
	@FXML
	private TextField chat;
	@FXML
	private TextArea chatWindow;
	@FXML
	private TextArea activityLog;
	@FXML
	private Text money;
	@FXML
	private Text incomePer;
	@FXML
	private Text weather;
	@FXML
	private Text playersRemaining;
	@FXML
	private Text marketingVal;
	@FXML
	private Button marketPlace;
	@FXML
	private Button inventions;
	@FXML
	private Button steelEngines;
	@FXML
	private Button textiles;
	@FXML
	private Button commEnt;
	@FXML
	private Button transInfra;
	@FXML
	private Pane market;
	@FXML
	private ScrollPane inventionPane;
	
	/*------------------------------------*/
	/*			CONSTRUCTORS			  */
	/*------------------------------------*/
	public GameScreenController() {
		time = Calendar.getInstance();
		format = new SimpleDateFormat("hh:mm:ss");
	}
	
	/*------------------------------------*/
	/*			FXML FUNCTIONS			  */
	/*------------------------------------*/
	@FXML
	public void initialize() {
		// When enter is hit send the textfield of chat to the chat window.
		chat.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					String message = chat.getText();
					chatWindow.setVisible(true);
					chatWindow.appendText("Player 1:" + message + '\n');
				}
			}
		});
		
		marketPlace.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				market.setVisible(true);
			}
		});
		
		/*cotton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				market.setVisible(false);
				time = time.getInstance();
				activityLog.appendText("Player 2 bought cotton for $0.25.\t" + format.format(time.getTime()) + '\n');
			}
		});*/
		
		inventions.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				inventionPane.setVisible(true);
				buttonCreation();
			}
		});
		
		steelEngines.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				
			}
		});
		
		textiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				
			}
		});
		
		commEnt.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				
			}
		});
		
		transInfra.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				
			}
		});
	}
	@FXML public void handle() {}
	
	/*------------------------------------*/
	/*			HELPER FUNCTIONS		  */
	/*------------------------------------*/
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;	
	}
	
	public void buttonCreation() {
		Network.getInstance().SendMessage("itemlist");
		String rstring = Network.getInstance().RecieveMessage();
		final int ROWS = Integer.parseInt(rstring);
		for(int i=0; i<ROWS; ++i) {
			Button b = new Button(Network.getInstance().RecieveMessage());
			b.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent t) {
					String name = getName(b.getText());
					Network.getInstance().SendMessage("buy\t" + name + "\tAsset\t1");
					Network.getInstance().RecieveMessage();
				}
			});
			inventionsList.getChildren().add(b);
		}
	}
	
	private String getName(CharSequence c)
	{
		String t = "";
		for(int i = 0; i < c.length(); i++)
		{
			if(c.charAt(i) == '\t')
			break;
			t += c.charAt(i);
		}
		
		
		return t;
	}
}
