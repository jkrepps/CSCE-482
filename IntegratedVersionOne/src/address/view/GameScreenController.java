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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import address.MainApplication;

public class GameScreenController {
	/*------------------------------------*/
	/*			DATA MEMBERS			  */
	/*------------------------------------*/
	public MainApplication mainApp;
	public BorderPane parentPane;
	public Calendar time;
	public SimpleDateFormat format;
	
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
	private Button cotton;
	@FXML
	private ScrollPane inventionPane;
	@FXML
	private Button battery;
	@FXML
	private Button photos;
	@FXML
	private Button steamEngine;
	@FXML
	private Button spinJenny;
	@FXML
	private Button cottonGin;
	@FXML
	private Button telegraph;
	@FXML
	private Button steamboats;
	@FXML
	private Button typewriter;
	@FXML
	private Button sewing;
	@FXML
	private Button bessemer;
	@FXML
	private Button transcable;
	@FXML
	private Button plastic;
	@FXML
	private Button dynamite;
	@FXML
	private Button vaccine;
	@FXML
	private Button telephone;
	@FXML
	private Button phonograph;
	@FXML
	private Button lightbulb;
	@FXML
	private Button electricmotor;
	@FXML
	private Button dieselEngine;
	@FXML
	private Button brooklynbridge;
	@FXML
	private Button airplane;
	@FXML
	private Button assemblyline;
	@FXML
	private Button modelT;
	
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
		
		cotton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				market.setVisible(false);
				time = time.getInstance();
				activityLog.appendText("Player 2 bought cotton for $0.25.\t" + format.format(time.getTime()) + '\n');
			}
		});
		
		inventions.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				inventionPane.setVisible(true);
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
	public void setMain(MainApplication  m) {
		mainApp = m;
	}
	
	public void setParent(BorderPane b) {
		parentPane = b;
	}
}
