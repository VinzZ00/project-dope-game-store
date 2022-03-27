
public class Game {

	private String gameID;
	private String gameName;
	private String gameType;
	private int gamePrice;
	private int gameStock;
	
	public Game(String gameID, String gameName, String gameType, int gamePrice, int gameStock) {
		super();
		this.gameID = gameID;
		this.gameName = gameName;
		this.gameType = gameType;
		this.gamePrice = gamePrice;
		this.gameStock = gameStock;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public int getGamePrice() {
		return gamePrice;
	}

	public void setGamePrice(int gamePrice) {
		this.gamePrice = gamePrice;
	}

	public int getGameStock() {
		return gameStock;
	}

	public void setGameStock(int gameStock) {
		this.gameStock = gameStock;
	}
	
	
	
	
	

}
