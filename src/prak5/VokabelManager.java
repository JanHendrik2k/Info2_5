package prak5;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface fuer ein strategy Pattern
 * @author Jan Hendrik
 *
 */
public interface VokabelManager {

	/**
	 * Speichert eine Vokabel
	 * @param toSave Vokabel welche gepeichert werden soll
	 * @return boolean
	 */
	public boolean save(Element toSave);
	
	/**
	 * Loescht eine Vokabel
	 * @param toDeleteGer deutsches Wort zur Vokabel, welches geloescht werden soll
	 * @return boolean
	 * @throws SQLException SQL Exception
	 */
	public boolean delete(String toDeleteGer) throws SQLException;
	
	/**
	 * Zufaelliges Element
	 * @return Random Element
	 */
	public Element getRandomElement();
	
	/**
	 * Alle Vokabeln in einer ArrayList
	 * @return ArrayList mit allen Vokabeln
	 */
	public ArrayList<Element> getAllVokabeln();	
	
	
}
