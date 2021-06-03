package prak5;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VokabelManager {

	public boolean save(Element toSave);
	public boolean delete(String toDeleteGer) throws SQLException;
	public Element getRandomElement();
	public ArrayList<Element> getAllVokabeln();	
	
	
}
