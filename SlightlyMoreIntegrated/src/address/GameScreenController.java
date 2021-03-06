package address;

import java.util.Vector;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameScreenController implements ControlledScreen {
	/*------------------------------------*/
	/*			DATA MEMBERS			  */
	/*------------------------------------*/
	private Vector<String> ids = new Vector();
	private ScreensController myController;
	@FXML
	private VBox inventionsList;
	@FXML
	private VBox technologiesList;
	@FXML
	private VBox inventoryList;
	@FXML
	private VBox marketList;
	@FXML
	private VBox playerMarketList;
	@FXML
	private HBox sellBox;
	@FXML
	private TextField chat;
	@FXML
	private TextArea chatWindow;
	@FXML
	private TextArea activityLog;
	@FXML
	private TextField quantity;
	@FXML
	private TextField price;
	@FXML
	private Text money = new Text("100g");
	@FXML
	private Text incomePer;
	@FXML
	private Text weather;
	@FXML
	private Text science;
	@FXML
	private Text marketingVal;
	@FXML
	private Button marketPlace;
	@FXML
	private Button inventions;
	@FXML
	private Button technology;
	@FXML
	private Button inventory;
	@FXML
	private Button playerMarket;
	@FXML
	private Button sellPlayerMarket;
	@FXML
	private Button sellWorldMarket;
	@FXML
	private ScrollPane playerMarketPane;
	@FXML
	private ScrollPane inventoryPane;
	@FXML
	private ScrollPane market;
	@FXML
	private ScrollPane inventionPane;
	@FXML
	private ScrollPane technologies;
	
	/*------------------------------------*/
	/*			CONSTRUCTORS			  */
	/*------------------------------------*/
	public GameScreenController() {}
	
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
					Network.getInstance().SendMessage("chat\t" + message);
					String rstring = Network.getInstance().RecieveMessage();
					chat.setText("");
					System.out.println(rstring);
					populateChatLog();
					updateHUD();
				}
			}
		});
		
		marketPlace.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				market.setVisible(true);
				technologies.setVisible(false);
				inventoryPane.setVisible(false);
				playerMarketPane.setVisible(false);
				sellBox.setVisible(false);
				price.setVisible(false);
				populateChatLog();
				buttonCreationMarket();
				updateHUD();
			}
		});
		
		technology.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				sellBox.setVisible(false);
				price.setVisible(false);
				technologies.setVisible(true);
				market.setVisible(false);
				inventoryPane.setVisible(false);
				playerMarketPane.setVisible(false);
				populateChatLog();
				buttonCreationTech();
				updateHUD();
			}
		});
		
		inventory.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				technologies.setVisible(false);
				market.setVisible(false);
				inventoryPane.setVisible(true);
				playerMarketPane.setVisible(false);
				sellBox.setVisible(true);
				price.setVisible(true);
				populateChatLog();
				buttonCreationInv();
				updateHUD();
			}
		});
		
		playerMarket.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent t) {
				technologies.setVisible(false);
				market.setVisible(false);
				inventoryPane.setVisible(false);
				playerMarketPane.setVisible(true);
				sellBox.setVisible(false);
				price.setVisible(false);
				populateChatLog();
				buttonCreationPlayerMarket();
				updateHUD();
			}
		});
	}
	@FXML public void handle() {}
	
	/*------------------------------------*/
	/*			HELPER FUNCTIONS		  */
	/*------------------------------------*/
	
	// Sets the parent screen
	public void setParentScreen(ScreensController screenPage) {
		myController = screenPage;	
	}
	
	// Updates the Heads Up Display's values
	public void updateHUD() {
		Network.getInstance().SendMessage("money");
		String moneyS = Network.getInstance().RecieveMessage();
		if(!(moneyS.equals("You have lost.")) && !(moneyS.equals("You have won!"))) {
			money.setText(":   " + moneyS + 'g');
		}
		Network.getInstance().SendMessage("weather");
		String weatherS = Network.getInstance().RecieveMessage();
		if(!weatherS.equals("You have lost.") && !weatherS.equals("You have won!")) {
			weather.setText("Weather: " + weatherS);
		}
		Network.getInstance().SendMessage("science");
		String scienceS = Network.getInstance().RecieveMessage();
		if(!scienceS.equals("You have lost.") && !scienceS.equals("You have won!")) {
			science.setText(":   " + scienceS);
		}
		Network.getInstance().SendMessage("income");
		String incomeS = Network.getInstance().RecieveMessage();
		if(!incomeS.equals("You have lost.") && !incomeS.equals("You have won!")) {
			incomePer.setText("Income: " + incomeS);
		}
		Network.getInstance().SendMessage("marketing");
		String marketingS = Network.getInstance().RecieveMessage();
		if(!incomeS.equals("You have lost.") && !incomeS.equals("You have won!")) {
			marketingVal.setText("Marketing: " + marketingS);
		}
	}
	
	// Dynamically acquires the buttons for the technology able to the researched
	public void buttonCreationTech() {
		Network.getInstance().SendMessage("techlist");
		String rstring = Network.getInstance().RecieveMessage();
		if(!(rstring.equals(("You have lost.")) && !(rstring.equals("You have won!")))) {
			final int ROWS = Integer.parseInt(rstring);
			quantity.setVisible(true);
			for(int i=0; i<ROWS; ++i) {
					String title = Network.getInstance().RecieveMessage();
					Button b = new Button(title);
					b.setTooltip(new Tooltip(title));
					b.setId("buttons");
					b.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent t) {
							String name = getName(b.getText());
							String quant = quantity.getText();
							if(!quant.isEmpty()) {
							Network.getInstance().SendMessage("buyt\t" + name + '\t' + quant);
							Network.getInstance().RecieveMessage();
							updateHUD();
							Network.getInstance().SendMessage("logfile");
							String log = Network.getInstance().RecieveMessage();
							final int LOG_ROWS = Integer.parseInt(log);
							for(int i=0; i<LOG_ROWS; ++i) {
								activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
							}
						}
					}
				});
				technologiesList.getChildren().add(b);
			}
		}
		else {
			activityLog.appendText(rstring);
		}
	}
	
	// Dynamically acquires the buttons for the world market items
	public void buttonCreationMarket() {
		Network.getInstance().SendMessage("itemlist");
		String rstring = Network.getInstance().RecieveMessage();
		if(!(rstring.equals("You have lost.")) && !(rstring.equals("You have won!"))) {
			final int ROWS = Integer.parseInt(rstring);
			quantity.setVisible(true);
			for(int i=0; i<ROWS; ++i) {
					String title = Network.getInstance().RecieveMessage();
					Button b = new Button(title);
					b.setTooltip(new Tooltip(title));
					b.setId("buttons");
					b.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent t) {
							String name = getName(b.getText());
							String quant = quantity.getText();
							if(!quant.isEmpty()) {
								Network.getInstance().SendMessage("buy\t" + name + "\t" + quant);
								Network.getInstance().RecieveMessage();
								updateHUD();
								Network.getInstance().SendMessage("logfile");
								String log = Network.getInstance().RecieveMessage();
								final int LOG_ROWS = Integer.parseInt(log);
								for(int i=0; i<LOG_ROWS; ++i) {
									activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
								}
							}
						}
					});
					marketList.getChildren().add(b);
			}
		}
		else {
			activityLog.appendText(rstring);
		}
	}
	
	// Dynamically acquires the buttons for the player's inventory list
	public void buttonCreationInv() {
		Network.getInstance().SendMessage("getResources");
		String rstring = Network.getInstance().RecieveMessage();
		if(!(rstring.equals("You have lost.")) && !(rstring.equals("You have won!"))) {
			final int ROWS = Integer.parseInt(rstring);
			quantity.setVisible(true);
			for(int i=0; i<ROWS; ++i) {
					String title = Network.getInstance().RecieveMessage();
					Button b = new Button(title);
					b.setTooltip(new Tooltip(title));
					b.setId("buttons");
					b.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent t) {
							sellWorldMarket.setOnMouseClicked(new EventHandler<MouseEvent>() {
								public void handle(MouseEvent t) {
									String name = getName(b.getText());
									String quant = quantity.getText();
									if(!quant.isEmpty()) {
										Network.getInstance().SendMessage("sell\t" + name + "\t" + quant);
										Network.getInstance().RecieveMessage();
										updateHUD();
										Network.getInstance().SendMessage("logfile");
										String log = Network.getInstance().RecieveMessage();
										final int LOG_ROWS = Integer.parseInt(log);
										for(int i=0; i<LOG_ROWS; ++i) {
											activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
										}
									}
								}
							});
							sellPlayerMarket.setOnMouseClicked(new EventHandler<MouseEvent>() {
								public void handle(MouseEvent t) {
								String name = getName(b.getText());
								String quant = quantity.getText();
								String price2 = price.getText();
								if(!(quant.isEmpty()) && !(price2.isEmpty())) {
									Float p = Float.parseFloat(price2);
									int q = Integer.parseInt(quant);
									Network.getInstance().SendMessage("sellmarket\t" + name + '\t' + p + '\t' + q);
									Network.getInstance().RecieveMessage();
									updateHUD();
									Network.getInstance().SendMessage("logfile");
									String log = Network.getInstance().RecieveMessage();
									final int LOG_ROWS = Integer.parseInt(log);
									for(int i=0; i<LOG_ROWS; ++i) {
										activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
									}
								}
							}
						});
					}
				});
				inventoryList.getChildren().add(b);
			}
		}
		else {
			activityLog.appendText(rstring);
		}
	}
	
	// Dynamically acquires the buttons for the player market items
	
	public void buttonCreationPlayerMarket() {
		Network.getInstance().SendMessage("marketlist");
		String num = Network.getInstance().RecieveMessage();
		if(!(num.equals("You have lost.")) && !(num.equals("You have won."))) {
			final int ROWS = Integer.parseInt(num);
			for(int i=0; i<ROWS; ++i) {
				String obj = Network.getInstance().RecieveMessage();
				String obj2 = parseMarketItem(obj);
				int id = getId(obj);
				Button b = new Button(obj2);
				b.setTooltip(new Tooltip(obj2));
				b.setId("buttons");
				b.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent arg0) {
						int quant = Integer.parseInt(quantity.getText());
						Network.getInstance().SendMessage("buymarket\t" + id + '\t' + quant);
						Network.getInstance().RecieveMessage();
						updateHUD();
						Network.getInstance().SendMessage("logfile");
						String log = Network.getInstance().RecieveMessage();
						final int LOG_ROWS = Integer.parseInt(log);
						for(int i=0; i<LOG_ROWS; ++i) {
							activityLog.appendText(Network.getInstance().RecieveMessage() + '\n');
						}
					}
				});
				playerMarketList.getChildren().add(b);
			}
		}
		else {
			activityLog.appendText(num + '\n');
		}
	}
	
	// Gets the name of the character sequence for a given item
	
private String getName(CharSequence c) {
		String t = "";
		for(int i = 0; i < c.length(); i++) {
			if(c.charAt(i) == '\t')
			break;
			t += c.charAt(i);
		}
		return t;
	}
	
private String parseMarketItem(String obj) {
		String objId = "";
		String objName = "";
		for(int i = 0; i<obj.length(); ++i) {
			if(obj.charAt(i) == '\t') {
				objName = obj.substring(i+1);
				break;
			}
			objId += obj.charAt(i);
		}
		ids.add(objId);
		return objName;
	}
	
private int getId(String obj) {
		String id = "";
		for(int i=0; i<obj.length(); ++i) {
			if(obj.charAt(i) == '\t')
				break;
			id += obj.charAt(i);
		}
		return Integer.parseInt(id);
	}

	private void populateChatLog() {
		Network.getInstance().SendMessage("chatfile");
		String rstring = Network.getInstance().RecieveMessage();
		String message = "";
		final int CHAT_ROWS = Integer.parseInt(rstring);
		for(int i=0; i<CHAT_ROWS; ++i) {
			message = Network.getInstance().RecieveMessage() + '\n';
		}
		chatWindow.setText(message);
	}
}
