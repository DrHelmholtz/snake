package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Get value to the Client, or write after the game
 * @author Boldi
 *
 */
public class Database {
	private final static String CONNECTION = "jdbc:sqlite:points.db";

	/**
	 * Save new data to the database
	 * @param point Points class  
	 */
	public static void saveDatabase(Points point) {
		dbExist();
		try (Connection conn = DriverManager.getConnection(CONNECTION)) {

			try (Statement stmt = conn.createStatement()) {
				boolean insertCount = stmt.execute("INSERT INTO points (username, date, point) VALUES " + "(\'"
						+ point.getUsername() + "\', \'" + point.getDate() + "\', " + point.getPoint() + ");");
			}

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * Get all the points,username,date to the leaderboards
	 * @return List with all the old scores, usernames, and the dates.
	 */
	public static List<Points> getDatabase() {
		dbExist();
		List<Points> lpoints = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(CONNECTION)) {

			try (Statement stmt = conn.createStatement()) {

				ResultSet rs = stmt.executeQuery("SELECT * FROM points");
				while (rs.next()) {
					Points p_tmp = new Points(rs.getInt("point"), rs.getString("username"), rs.getString("date"));
					lpoints.add(p_tmp);
				}
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return lpoints;
	}
/**
 * Create the points table, if it doesn't exist
 */
	public static void dbExist() {
		try (Connection conn = DriverManager.getConnection(CONNECTION)) {

			try (Statement stmt = conn.createStatement()) {
				boolean create = stmt
						.execute("CREATE TABLE IF NOT EXISTS points(username TEXT ,date TEXT, point INT);");
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
