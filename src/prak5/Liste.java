package prak5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Liste {

	private Element first;
	private final String DEFAULTPATH = "src/prak2/";

	public Liste() {
		first = null;
								
	}

	/**
	 * Findet das letzte Element herraus
	 * 
	 * @param first erstes Element
	 * @return das letzte Element
	 */
	public Element last(Element first) {
		Element current = first;
		if (current == null) {
			return current;
		}
		while (current.getNext() != null) {
			current = current.getNext();
		}
		return current;

	}

	/**
	 * Fuegt ein Element zur Liste hinzu
	 * 
	 * @param ger deutsche Vokabel
	 * @param eng englische Vokabel
	 */
	public boolean add(String ger, String eng) {
		Element last = last(first);
		Element newElem = new Element();

		// Validation
		if (ger.isEmpty() || eng.isEmpty() || ger.isBlank() || eng.isBlank() || ger.startsWith(" ")
				|| ger.startsWith(";") || ger.endsWith(" ") || ger.endsWith(";") || eng.startsWith(" ")
				|| eng.startsWith(";") || eng.endsWith(" ") || eng.endsWith(";")) {
			System.err.println("OH NEIN! Die Vokabel ist fehlerhaft und wurde nicht hinzugefuegt :(");
			return false;
		}

		// schaut ob Liste leer ist
		if (first == null) {
			first = newElem;
		} else if (last.getNext() == null) {
			last.setNext(newElem);
			newElem.setPrev(last);
		}
		newElem.setGer(ger);
		newElem.setEng(eng);
		System.out.println(ger + " , " + eng + " hinzugefuegt");
		return true;

	}

	/**
	 * Loescht ein Element in der Liste (Vokabel wird auf Deutsch gesucht)
	 * 
	 * @param elem Deutsche Vokabel, gibt an, welches Element geloescht werden soll
	 */
	public boolean delete(String elem) {
		Element current = first;
		// Ueberpruefen ob Liste leer ist
		if (first == null) {
			System.err.println("Die Liste ist leer :(");
			return false;
		} else {
			while (current != null) {
				if (current.getGer().equals(elem)) {
					// Mitten in Liste
					if (current.getPrev() != null && current.getNext() != null) {
						current.getPrev().setNext(current.getNext());
						current.getNext().setPrev(current.getPrev());
					}
					// Vorne
					else if (current.getPrev() == null && current.getNext() != null) {
						first = current.getNext();
						current.getNext().setPrev(null);
					}
					// Hinten
					else if (current.getPrev() != null && current.getNext() == null) {
						current.getPrev().setNext(null);
					}
					// Ein Element in der Liste
					else if (current.getPrev() == null && current.getNext() == null) {
						first = null;
					}
					System.out.println("Element geloescht :)");
					return true;
				}
				current = current.getNext();
			}
			System.err.println("OH NEIN! Die Vokabel konnte nicht gefunden werden :(");
			return false;

		}
	}

	/**
	 * Zeigt alle Elemente in der Liste an
	 */
	public void show() {
		Element current = first;

		// ist Liste leer
		if (first == null) {
			System.err.println("Die Liste ist leer :(");

		} else {
			int i = 1;
			while (current != null) {
				System.out.println(i + ", Ger: " + current.getGer() + ", Eng: " + current.getEng());
				current = current.getNext();
				i++;
			}
		}
	}

	/**
	 * Fragt Vokabeln zufaellig ab
	 */
	public void call() {
		Scanner sc = new Scanner(System.in);
		int zaehler = 0, rdm = 0, rdmLang = 0;
		Element current = first;
		// ist Liste leer
		if (current == null) {
			System.err.println("Sie koennen nichts abfragen. Die Liste ist leer :(");
			return;
		} else {
			while (current != null) {
				current = current.getNext();
				zaehler++;
			}

			System.out.println("Abfrage gestartet. Sie koennen das Programm beenden mit \"stop\"");
		}
		while (true) {
			current = first;
			rdmLang = (int) ((Math.random() * 2) + 1);

			rdm = (int) ((Math.random() * (zaehler - 1)) + 1);

			// Englisch wird abgefragt
			if (rdmLang == 1) {
				if (rdm != 1) {
					for (int i = 1; i < rdm; i++) {
						current = current.getNext();

					}

				}
				System.out.println("Was ist das englische Wort zu: " + current.getGer() + "?");
				String input = sc.next();
				// Wurde stop eingegeben? (Methode beenden)
				if (input.equals("stop")) {
					return;
				}
				if (current.getEng().equals(input)) {
					System.out.println("Richtig :) " + current.getGer() + " - " + current.getEng());
				} else {
					System.out.println(
							"Das ist leider falsch :( Richtig waere: " + current.getGer() + " - " + current.getEng());
				}
				current = first;

				// Deutsch wird abgefragt
			} else if (rdmLang == 2) {
				if (rdm != 1) {
					for (int i = 1; i < rdm; i++) {
						current = current.getNext();

					}
				}
				System.out.println("Was ist das deutsche Wort zu: " + current.getEng() + "?");
				String input = sc.next();
				// Wurde stop eingegeben? (Methode beenden)
				if (input.equals("stop")) {
					return;
				}
				if (current.getGer().equals(input)) {
					System.out.println("Richtig :) " + current.getGer() + " - " + current.getEng());
				} else {
					System.out.println(
							"Das ist leider falsch :( Richtig waere: " + current.getGer() + " - " + current.getEng());
				}
			}
		}
	}

	/**
	 * Liest eine Datei aus und fuegt den Inhalt zur Liste hinzu
	 * 
	 * @param readFile Datei welche ausgelesen werden soll
	 */
	public void read(String readFile) {
		try {
			String line = "";
			String[] parts;
			String ger;
			String eng;
			File file = new File(DEFAULTPATH + readFile + ".txt");
			if (!file.exists()) {
				System.err.println("OH NEIN! Die Datei exestiert nicht :(");
				return;
			}
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				// validation
				if (data.isEmpty() || !data.contains(";") || data.startsWith(";") || data.endsWith(";")) {
					continue;
				}

				line = data;
				// delimiter ist ";"
				parts = line.split(";");
				ger = parts[0];
				eng = parts[1];
				add(ger, eng);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("HOPPALA, es ist ein Fehler aufgetreten :(");
			e.printStackTrace();
		}

	}

	/**
	 * Speichert die Liste in einer Datei
	 * 
	 * @param writeFile in welche Datei geschrieben werden soll
	 */
	public void write(String writeFile) throws IOException {

		File file = new File(DEFAULTPATH + writeFile + ".txt");
		if (!file.exists()) {
			System.err.println("OH NEIN! Die Datei existiert nicht! :(");
			return;
		}
		FileWriter fw = new FileWriter(DEFAULTPATH + writeFile + ".txt", false);
		BufferedWriter bw = new BufferedWriter(fw);
		Element current = first;
		if (current == null) {
			System.out.println("Die Liste ist leer :( es kann nix gespeichert werden.");
		}
		while (current != null) {
			bw.write(current.getGer() + ";" + current.getEng());
			bw.newLine();
			current = current.getNext();
		}
		bw.close();
	}

	/**
	 * Iteriert ueber die verkettete Liste und speichert Elemente in einer ArrayList
	 * @param first erstes Element
	 * @return ArrayList mit allen Vokabeln
	 */
	public ArrayList<Element> getAll(Element first) {
		ArrayList<Element> list = new ArrayList<Element>();
		Element current = first;

		// ist Liste leer
		if (first == null) {
			System.err.println("Die Liste ist leer :(");

		} else {
			int i = 1;
			while (current != null) {
				list.add(current);
				current = current.getNext();
				i++;
			}
		}
		return list;
	}

	/**
	 * Methode getAll aber fuer das Interface prepared
	 * @return ArrayList mit allen Vokabeln
	 */
	public ArrayList<Element> getAllForStrategy() {
		return getAll(first);
	}

}
