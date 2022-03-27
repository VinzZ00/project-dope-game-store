
public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
		DbConnection db = new DbConnection();
		new Login(db);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
