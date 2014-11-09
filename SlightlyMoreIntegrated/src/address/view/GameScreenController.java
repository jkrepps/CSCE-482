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
	private VBox technologiesList;
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
	private Button technology;
	@FXML
	private Pane market;
	@FXML
	private ScrollPane inventionPane;
	@FXML
	private ScrollPane technologies;
	
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
				//market.setVisible(true);
				inventionPane.setVisible(false);
				technologies.setVisible(false);
			}
		});
		
		inventions.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				inventionPane.setVisible(true);
				//market.setVisible(false);
				technologies.setVisible(false);
				buttonCreationInvent();
			}
		});
		technology.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				technologies.setVisible(true);
				//market.setVisible(false);
				inventionPane.setVisible(false);
				buttonCreationTech();
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
	
	// Dynamically acquires the buttons for the inventions available to the player
	public void buttonCreationInvent() {
		Network.getInstance().SendMessage("itemlist");							// send message to get the itemlist
		String rstring = Network.getInstance().RecieveMessage();				// receive the message returned by the server
		final int ROWS = Integer.parseInt(rstring);								// number of items or buttons created
		for(int i=0; i<ROWS; ++i) {												// Acquire the buttons and create them
			if(i != 0) {
				Button b = new Button(Network.getInstance().RecieveMessage());
				b.setId("buttons");
				b.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent t) {
						String name = getName(b.getText());
						Network.getInstance().SendMessage("buy\t" + name + "\tAsset\t1");
						Network.getInstance().RecieveMessage();
						time = Calendar.getInstance();
						Network.getInstance().SendMessage("logfile");
						String log = Network.getInstance().RecieveMessage();
						final int LOG_ROWS =  Integer.parseInt(log);
						for (int i=0; i<LOG_ROWS; ++i ) {
							activityLog.appendText(Network.getInstance().RecieveMessage() + "\n");
						}
					}
				
				});
				inventionsList.getChildren().add(b);
			}
			else {
				Network.getInstance().RecieveMessage();
			}
		}
	}
	
	public void buttonCreationTech() {
		Network.getInstance().SendMessage("techlist");
		String rstring = Network.getInstance().RecieveMessage();
		final int ROWS = Integer.parseInt(rstring);
		for(int i=0; i<ROWS; ++i) {
			if (i != 0) {
				Button b = new Button(Network.getInstance().RecieveMessage());
				b.setId("buttons");
				b.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent t) {
						String name = getName(b.getText());
						Network.getInstance().SendMessage("buy\t" + name + "\tTech\t1");
						Network.getInstance().RecieveMessage();
						time = Calendar.getInstance();
						Network.getInstance().SendMessage("logfile");
						String log = Network.getInstance().RecieveMessage();
						final int LOG_ROWS = Integer.parseInt(log);
						for(int i=0; i<LOG_ROWS; ++i) {
							activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
						}
					}
				});
				technologiesList.getChildren().add(b);
			}
			else {
				Network.getInstance().RecieveMessage();
			}
		}
	}
	
	public void buttonCreationMarket() {
		Network.getInstance().SendMessage("marketlist");
		String rstring = Network.getInstance().RecieveMessage();
		final int ROWS = Integer.parseInt(rstring);
		for(int i=0; i<ROWS; ++i) {
			if (i != 0) {
				Button b = new Button(Network.getInstance().RecieveMessage());
				b.setId("buttons");
				b.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent t) {
						String name = getName(b.getText());
						Network.getInstance().SendMessage("buy\t" + name + "\tMarket\t1");
						Network.getInstance().RecieveMessage();
						time = Calendar.getInstance();
						Network.getInstance().SendMessage("logfile");
						String log = Network.getInstance().RecieveMessage();
						final int LOG_ROWS = Integer.parseInt(log);
						for(int i=0; i<LOG_ROWS; ++i) {
							activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
						}
					}
				});
				market.getChildren().add(b);
			}
			else {
				Network.getInstance().RecieveMessage();
			}
		}
	}
	
	private String getName(CharSequence c) {
		String t = "";
		for(int i = 0; i < c.length(); i++) {
			if(c.charAt(i) == '\t')
			break;
			t += c.charAt(i);
		}
		return t;
	}
}
