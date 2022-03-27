import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DbConnection {

	Connection connect;
	ResultSet rs;
	PreparedStatement ps;
	
	public DbConnection() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dope","root","");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getuserdata() {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("SELECT * FROM `users`");
			try {
				rs = ps.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public String getNewUniqueId() {
		String Id = null;
		
		try {
			ps = connect.prepareStatement("SELECT UserID FROM `users` ORDER BY `users`.`UserID` ASC");
			rs = ps.executeQuery();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(!(rs.next() == false)) {
				rs.last();
				String lastId = rs.getObject("UserID").toString();
				int number = (Integer.valueOf(lastId.substring(2))+1);
				Id = String.format("%s%03d", "US", number);
			} else {
				Id = "US001";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}
	
	public void insertUser(User user) {
		try {
			ps = connect.prepareStatement("INSERT INTO `users` (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserAddress`, `UserPhone`, `UserRole`) VALUES (?, ?, ?, ?, ?, ?, ?, ?); ");
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getUserEmail());
			ps.setString(4, user.getUserPassword());
			ps.setString(5, user.getUserGender());
			ps.setString(6, user.getUserAdddress());
			ps.setString(7, user.getUserPhone());
			ps.setString(8, user.getUserRole());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateuser(User user) {
		// TODO Auto-generated method stub
		try {
			ps = connect.prepareStatement("UPDATE `users` SET `UserName`=?,`UserEmail`=?,`UserPassword`=?,`UserGender`=?,`UserAddress`=?,`UserPhone`=?,`UserRole`=? WHERE UserID = ?");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserEmail());
			ps.setString(3, user.getUserPassword());
			ps.setString(4, user.getUserGender());
			ps.setString(5, user.getUserAdddress());
			ps.setString(6, user.getUserPhone());
			ps.setString(7, user.getUserRole());
			ps.setString(8, user.getUserId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getGameData() {
		try {
			ps = connect.prepareStatement("SELECT * FROM games");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public String getNewGameId() {
		String Id = null;
		try {
			ps = connect.prepareStatement("SELECT GameID FROM `games` ORDER BY `games`.`GameID` ASC ");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				int number = Integer.valueOf(rs.getObject("GameID").toString().substring(2)) + 1; 
				Id = String.format("GA%03d", number);
			} else {
				Id = "GA001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}
	
	public void insertgame(Game game) {
		try {
			ps = connect.prepareStatement("INSERT INTO `games` (`GameID`, `GameName`, `GameType`, `GamePrice`, `GameStock`) VALUES (?, ?, ?, ?, ?);");
			ps.setString(1, game.getGameID());
			ps.setString(2, game.getGameName());
			ps.setString(3, game.getGameType());
			ps.setInt(4, game.getGamePrice());
			ps.setInt(5, game.getGameStock());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateGame(Game game) {
		try {
			ps = connect.prepareStatement("UPDATE `games` SET `GameName`= ? ,`GameType`= ? ,`GamePrice`= ? ,`GameStock`= ?  WHERE GameID = ?");
			ps.setString(1, game.getGameName());
			ps.setString(2, game.getGameType());
			ps.setInt(3, game.getGamePrice());
			ps.setInt(4, game.getGameStock());
			ps.setString(5, game.getGameID());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteGame(String gameId) throws SQLException {
		
			ps = connect.prepareStatement("DELETE FROM `games` WHERE GameID = ?");
			ps.setString(1, gameId);
			
			ps.execute();
	}
	
	
	public void updateGameStock(String gameId, int gameStock) throws SQLException {
			ps = connect.prepareStatement("UPDATE `games` SET GameStock = ? WHERE GameID = ?");
			ps.setInt(1, gameStock);
			ps.setString(2, gameId);
			
			ps.execute();
	}
	
	public String getTransactionId() {
		String Id = null;
		try {
			ps = connect.prepareStatement("SELECT `TransactionID` FROM `headertransactions` ORDER BY TransactionID ASC");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				int number = (Integer.valueOf(rs.getObject("TransactionID").toString().substring(2)) + 1);
				Id = String.format("TR%03d", number);
			} else {
				Id = "TR001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}
	
	public void inputHeaderTransaction(String transactionId, User user) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    Date date = (Date) Calendar.getInstance().getTime(); 
	    String dateString = formatter.format(date);
	    
	    
	    try {
			ps = connect.prepareStatement("INSERT INTO `headertransactions`(`TransactionID`, `UserID`, `TransactionDate`) VALUES (?,?,?)");
			ps.setString(1, transactionId);
			ps.setString(2, user.getUserId());
			ps.setString(3, dateString);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inputDetailTransaction(String transactionId, String gameId, int quantity) {
		try {
			ps = connect.prepareStatement("INSERT INTO `detailtransactions`(`TransactionID`, `GameID`, `Quantity`) VALUES (?,?,?)");
			ps.setString(1, transactionId);
			ps.setString(2, gameId);
			ps.setInt(3, quantity);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getHeaderTransaction() {
		try {
			ps = connect.prepareStatement("SELECT * FROM `headertransactions`");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getDetailTransaction(String transactionId) {
		
		try {
			ps = connect.prepareStatement("SELECT dt.TransactionID, g.GameID, g.GameName, g.GameType, g.GamePrice, dt.Quantity game from games g join detailtransactions dt where g.GameID = dt.GameID and dt.TransactionID = ? ");
			ps.setString(1, transactionId);
			
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	

	
}
