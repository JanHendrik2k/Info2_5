package de.fh_muenster.its.info2.vokabel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Lennart Grahl, Sebastian Schinzel
 *
 */
public class VocabularyDatabase {
	private Connection connection;
	
	/**
	 * So startet man die Datenbank:.
	 * 
	 * The HSQLDB server can be started with the following commands:
	 *   cd <pfad_zu_diesem_projekt>
	 *   java -cp lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:db_vokabel --dbname.0 vokabeltrainer
	 *  
	 */
	public static void main(String[] args) throws Exception {
		VocabularyDatabase db = new VocabularyDatabase();
		
		db.wipe();
		
		db.insert("who", "Wer");
		db.insert("wants", "will");
		db.insert("to", "zu");
		db.insert("live", "leben");
		db.insert("forever", "ewig");
		
		db.print(db.list());
		
		db.insert("Hallo", "Test");
	}
	
	/**
	 * Connect to a storage database and store the connection.
	 * @throws SQLException 
	 */
	public VocabularyDatabase() throws SQLException {
		this("jdbc:hsqldb:hsql://localhost/vokabeltrainer", "usr", "ooz1ooHi");
	}
	
	/**
	 * Connect to a database and store the connection.
	 * @param url A JDBC URL.
	 * @param username A user name for the database connection.
	 * @param password The corresponding password to the user name. 
	 * @throws SQLException
	 */
	public VocabularyDatabase(String url, String username, String password) throws SQLException {
		// Probe HSQLDB driver
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e) {
			System.err.println("Couldn't find HSQLDB JDBC driver. Have you referenced the library?");
			System.exit(1);
		}
		
		this.connection = DriverManager.getConnection(url, username, password);
	}
	
	/**
	 * Insert an English vocabulary and it's German translation.
	 * Note: Having multiple translations for a single vocabulary is not possible.
	 * @param en An English vocabulary.
	 * @param de The German translation of the English vocabulary. 
	 * @throws SQLException 
	 */
	public void insert(String en, String de) throws SQLException {
		String sql = "INSERT INTO vokabeln (en, de) VALUES (?, ?)";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		
		statement.setString(1, en);
		statement.setString(2, de);
		
		statement.execute();
	}
	
	/**
	 * Wipe all vocabulary entries from the database.
	 * @throws SQLException 
	 */
	public void wipe() throws SQLException {
		String sql = "DELETE FROM vokabeln";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		
		statement.execute();
	}
	
	/**
	 * Select vocabulary entries from the database.
	 * @return A result set containing every vocabulary entry in the database.
	 * @throws SQLException
	 */
	public ResultSet list() throws SQLException {
		String sql = "SELECT * FROM vokabeln";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		
		return statement.executeQuery();
	}
	
	/**
	 * Print the content of a ResultSet from a select statement to the vocabularies.
	 * @param rs A ResultSet of a previously executed SQL query.
	 * @throws SQLException
	 */
	public void print(ResultSet rs) throws SQLException {
		System.out.println("List of Vocabularies:");
		
		for (int i = 1; rs.next(); i++) {
			System.out.printf("\t[%d]: EN: %s, DE: %s, Correct: %d, Wrong: %d\n",
				i, rs.getString("en"), rs.getString("de"), rs.getInt("korrekt"), rs.getInt("falsch"));
		}
		
		System.out.println();
	}
}
