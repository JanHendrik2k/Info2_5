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
	 * Speichert die Vokabel in die Datenbank
	 * 
	 * @param toSave die zu speichernde Vokabel
	 * @return boolean
	 */
	@Override
	public boolean save(Element toSave) {
		try {
			db.insert(toSave.getEng(), toSave.getGer());
			System.out.println(toSave.getGer() + ", " + toSave.getEng() + " hinzugefuegt");
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	
	/**
	 * Loescht eine Vokabel ueber den deutschen Namen aus der Datenbank
	 * 
	 * @param toDeleteGer der deutsche name der Vokabel
	 * @return boolean
	 */
	@Override
	public boolean delete(String toDeleteGer) throws SQLException {

		String sql = "DELETE FROM vokabeln WHERE (de) = (?)";
		PreparedStatement statement = this.connection.prepareStatement(sql);

		statement.setString(1, toDeleteGer);

		statement.execute();
		return (statement.executeUpdate() == 1);
	}

	/**
	 * holt sich ein random element aus der der arraylist
	 * @return die array list
	 */
	@Override
	public Element getRandomElement() {
		Random random = new Random();
		int zahl;
		ArrayList<Element> list = getAllVokabeln();
		zahl = random.nextInt(list.size());
		return list.get(zahl);
	}
	
	/**
	 * holt sich die ArrayList aus der Klasse "Liste"
	 */
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
