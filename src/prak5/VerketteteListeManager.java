package prak5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class VerketteteListeManager implements VokabelManager {

	private Liste list;

	public VerketteteListeManager() {
		list = new Liste();

		Scanner sc = new Scanner(System.in);
	}

	@Override
	public boolean save(Element toSave) {
		return list.add(toSave.getGer(), toSave.getEng());
	}

	@Override
	public boolean delete(String toDeleteGer) {
		list.delete(toDeleteGer);
		return false;
	}

	@Override
	public Element getRandomElement() {

		Random random = new Random();
		int zahl;
		ArrayList<Element> list = getAllVokabeln();
		zahl = random.nextInt(list.size()-1);
		return list.get(zahl);
	}

	@Override
	public ArrayList<Element> getAllVokabeln() {
		return list.getAllForStrategy();
	}

}
