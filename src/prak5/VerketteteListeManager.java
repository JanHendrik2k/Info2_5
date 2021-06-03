package prak5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * Strategie der verketteten Liste
 * @author Jan Hendrik
 *
 */
public class VerketteteListeManager implements VokabelManager {

	private Liste list;
	
	/**
	 * Liste erzeugen
	 */
	public VerketteteListeManager() {
		list = new Liste();
		Scanner sc = new Scanner(System.in);
	}

	/**
	 * Speichert das Element in einer verketteten Liste
	 * @param toSave zu speicherndes element
	 */
	@Override
	public boolean save(Element toSave) {
		return list.add(toSave.getGer(), toSave.getEng());
	}

	/**
	 * Loescht das Element zu einem deutschen Wort aus der verketteten Liste
	 * @param toDeleteGer deutsches Wort zur Vokabel, welches geloescht werden soll
	 */
	@Override
	public boolean delete(String toDeleteGer) {
		return list.delete(toDeleteGer);
	}

	/**
	 * Zieht sich ein random Element aus der verketteten liste
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
	 * Holt sich eine ArrayList mit allen Vokabeln aus der verketteten Liste
	 */
	@Override
	public ArrayList<Element> getAllVokabeln() {
		return list.getAllForStrategy();
	}

}
