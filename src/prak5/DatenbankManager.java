package prak5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import de.fh_muenster.its.info2.vokabel.VocabularyDatabase;

public class DatenbankManager implements VokabelManager {

	private Connection connection;

	/**
	 * So startet man die Datenbank:.
	 * 
	 * The HSQLDB server can be started with the following commands: cd
	 * <pfad_zu_diesem_projekt> java -cp lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:db_vokabel --dbname.0 vokabeltrainer
	 * 
	 * @throws SQLException
	 * 
	 */
	VocabularyDatabase db = new VocabularyDatabase();

	/**
	 * Connect to a storage database and store the connection.
	 * @throws SQLException 
	 */
	public DatenbankManager() throws Exception {
		this("jdbc:hsqldb:hsql://localhost/vokabeltrainer", "usr", "ooz1ooHi");
		db.wipe();
	}


	/**
	 * Connect to a database and store the connection.
	 * 
	 * @param url      A JDBC URL.
	 * @param username A user name for the database connection.
	 * @param password The corresponding password to the user name.
	 * @throws SQLException
	 */
	public DatenbankManager(String url, String username, String password) throws SQLException {
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
	 * Insert an English vocabulary and it's German translation. Note: Having
	 * multiple translations for a single vocabulary is not possible.
	 * 
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
	 * 
	 * @throws SQLException
	 */
	public void wipe() throws SQLException {
		String sql = "DELETE FROM vokabeln";
		PreparedStatement statement = this.connection.prepareStatement(sql);

		statement.execute();
	}

	/**
	 * Select vocabulary entries from the database.
	 * 
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
	 * 
	 * @param rs A ResultSet of a previously executed SQL query.
	 * @throws SQLException
	 */
	public void print(ResultSet rs) throws SQLException {
		System.out.println("List of Vocabularies:");

		for (int i = 1; rs.next(); i++) {
			System.out.printf("\t[%d]: EN: %s, DE: %s, Correct: %d, Wrong: %d\n", i, rs.getString("en"),
					rs.getString("de"), rs.getInt("korrekt"), rs.getInt("falsch"));
		}

		System.out.println();
	}

	@Override
	public boolean save(Element toSave) {
		try {
			db.insert(toSave.getEng(), toSave.getGer());
			System.out.println(toSave.getGer() + ", " + toSave.getEng() + " hinzugefuegt");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String toDeleteGer) throws SQLException {

		String sql = "DELETE FROM vokabeln WHERE (de) = (?)";
		PreparedStatement statement = this.connection.prepareStatement(sql);

		statement.setString(1, toDeleteGer);

		statement.execute();
		return false;
	}

	@Override
	public Element getRandomElement() {
		Random random = new Random();
		int zahl;
		ArrayList<Element> list = getAllVokabeln();
		zahl = random.nextInt(list.size());
		return list.get(zahl);
	}

	@Override
	public ArrayList<Element> getAllVokabeln() {
		ArrayList<Element> list = new ArrayList<Element>();
		try {
			ResultSet rs = db.list();
			while (rs.next()) {
				Element e = new Element();
				e.setEng(rs.getString("en"));
				e.setGer(rs.getString("de"));
				list.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

}
