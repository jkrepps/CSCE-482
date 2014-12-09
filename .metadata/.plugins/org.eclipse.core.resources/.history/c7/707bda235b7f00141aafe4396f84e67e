package address.view;

import java.util.Vector;

import javafx.scene.control.Button;

public class GameData {
	
	Vector<Button> games = new Vector<Button>();
	Vector<Button> yourGames = new Vector<Button>();
	
	public void setGames() {
		Network.getInstance().SendMessage("gamelist");
		String rstring = Network.getInstance().RecieveMessage();
		final int GAMES_ROWS = Integer.parseInt(rstring);
		for(int i=0; i<GAMES_ROWS; ++i) {
			Button b = new Button(Network.getInstance().RecieveMessage());
			games.add(b);
		}
	}
	
	public void setYourGames() {
		Network.getInstance().SendMessage("yourgamelist");
		String rstring = Network.getInstance().RecieveMessage();
		final int GAMES_ROWS = Integer.parseInt(rstring);
		for(int i=0; i<GAMES_ROWS; ++i) {
			Button b = new Button(Network.getInstance().RecieveMessage());
			yourGames.add(b);
		}
	}
	
	public Vector<Button> getGames() {
		return games;
	}
	
	public Vector<Button> getYourGames() {
		return yourGames;
	}
	private static GameData instance;

	  static 
	  {
	      instance = new GameData();
	  }

	  private GameData() 
	  {
	  }

	  public static GameData getInstance() {
	      return GameData.instance;
	  }
 
}
