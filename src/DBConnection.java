import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
	
	
	public void storeAccess(String ip, int matrNumber) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sotorrent18_09?" + "user=root&password=95467");
		} catch (SQLException e) {
			System.out.println("Connecting to DB failed");
			e.printStackTrace();
		} 
		
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO se2.accesses VALUES (?, ?)");
			statement.setString(1, ip);
			statement.setInt(2, matrNumber);
			statement.execute(); 
		} catch (SQLException e) {
			System.out.println("Failure writing into DB"); 
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Closing DB connection failed"); 
			e.printStackTrace();
		} 
		
	}
}
