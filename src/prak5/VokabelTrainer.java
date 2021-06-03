package prak5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Das Hauptmenue mit Konsolenabfragen, etc.
 * @author Jan Hendrik
 *
 */
public class VokabelTrainer {

	private final String DEFAULTPATH = "src/prak5/";

	//main
	public static void main(String[] args) throws Exception {
		new VokabelTrainer();
	}

	public VokabelTrainer() throws Exception {

		ArrayList<Element> allElements = new ArrayList<Element>();

		Scanner sc = new Scanner(System.in);

		VokabelManager manager = null;
		VokabelManager[] managers = new VokabelManager[2];
		int managerPos = 0;

		//Strategien im Array Speichern
		managers[0] = new DatenbankManager();
		managers[1] = new VerketteteListeManager();
		manager = managers[managerPos];

		while (true) {

			String menueValue = "";
			System.out.println("Was wollen Sie machen?\nGeben Sie einen Befehl ein.\nGeben Sie help für \"Hilfe\" ein");
			String eingabe = "";

			eingabe = sc.nextLine();
			menueValue = eingabe;

			//Konsolenabfrage
			switch (menueValue) {
			//liest aus einer Datei, speichert valide Vokabeln
			case "read":
				System.out.println("Aus welcher Datei soll gelesen werden?");
				String readFile = sc.nextLine();
				String line = "";
				String[] parts;
				String ger, eng, data;
				File file = new File(DEFAULTPATH + readFile + ".txt");
				if (!file.exists()) {
					System.err.println("OH NEIN! Die Datei exestiert nicht :(");
					return;
				}
				Scanner myReader = new Scanner(file);
				while (myReader.hasNextLine()) {
					data = myReader.nextLine();
					// Validation
					if (data.isEmpty() || !data.contains(";") || data.startsWith(";") || data.endsWith(";")
							|| data.startsWith(" ") || data.endsWith(" ")) {
						System.err.println("OH NEIN! Die Vokabel ist fehlerhaft und wurde nicht hinzugefuegt :(");
						continue;
					}

					line = data;
					// delimiter ist ";"
					parts = line.split(";");
					ger = parts[0];
					eng = parts[1];

					Element elem = new Element();
					elem.setGer(ger);
					elem.setEng(eng);
					manager.save(elem);
				}
				myReader.close();
				break;

			//Schreibt Vokabeln in eine Datei, falls die richtige Strategy ausgewaehlt ist
			case "write":
				if (manager instanceof DatenbankManager) {
					System.err.println("Wir haben eine Datenbank :)");
				} else {

					ArrayList<Element> list = manager.getAllVokabeln();
					if (list.isEmpty()) {
						System.out.println("Die Vokabelliste ist Leer!");
					} else {

						System.out.println("In welche Datei soll geschrieben werden?");
						String writeFile = sc.nextLine();

						file = new File(DEFAULTPATH + writeFile + ".txt");
						if (!file.exists()) {
							System.err.println("OH NEIN! Die Datei existiert nicht! :(");
							break;
						}
						FileWriter fw = new FileWriter(DEFAULTPATH + writeFile + ".txt", false);
						BufferedWriter bw = new BufferedWriter(fw);
						for (Element elem : list) {
							bw.write(elem.getGer() + ";" + elem.getEng());
							bw.newLine();
						}
						bw.close();

					}
				}
				break;

			//fuegt eine neue Vokabel hinzu
			case "add":
				System.out.print("Deutsch: ");
				ger = sc.nextLine();
				System.out.print("Englisch: ");
				eng = sc.nextLine();

				Element newElem = new Element();
				newElem.setGer(ger);
				newElem.setEng(eng);
				manager.save(newElem);
				break;

			//loescht eine bestehende Vokabel ueber das deutsche Wort
			case "delete":
				System.out.print("Vokabel eingeben welche geloescht werden soll (auf Deutsch): ");
				String gerDel = sc.nextLine();
				manager.delete(gerDel);
				break;

			//fragt Vokabeln der aktuellen Strategie ab
			case "call":
				ArrayList<Element> list = manager.getAllVokabeln();
				Random random;
				int randLang;
				Element element;
				while (true) {
					random = new Random();
					randLang = random.nextInt(2);
					element = manager.getRandomElement();
					// Englisch abfragen
					if (randLang == 0) {
						System.out.println("Was ist das englische Wort zu: " + element.getGer() + "?");
						eingabe = sc.nextLine();

						if (eingabe.equals("stop")) {
							break;
						}
						if (element.getEng().equals(eingabe)) {
							System.out.println("Richtig :) " + element.getGer() + " - " + element.getEng());
						} else {
							System.out.println("Das ist leider falsch :( Richtig waere: " + element.getGer() + " - "
									+ element.getEng());
						}

						// Deutsch abfragen
						if (randLang == 1) {
							System.out.println("Was ist das deutsche Wort zu: " + element.getEng() + "?");
							eingabe = sc.nextLine();

							if (eingabe.equals("stop")) {
								break;
							}
							if (element.getGer().equals(eingabe)) {
								System.out.println("Richtig :) " + element.getGer() + " - " + element.getEng());
							} else {
								System.out.println("Das ist leider falsch :( Richtig waere: " + element.getGer() + " - "
										+ element.getEng());
							}
						}

					}
				}
				break;

			//Zeigt alle Vokabeln wunderschoen und porfessionell auf sysout an
			case "show":
				int i = 1;
				for (Element elem : manager.getAllVokabeln()) {
					System.out.println(i + " GER: " + elem.getGer() + " ENG: " + elem.getEng());
					i++;
				}
				break;

			//schließt das Programm
			case "close":
				System.out.println("Juhu, dieses coole Programm wird jetzt beendet :)))))");
				sc.close();
				System.exit(0);
				break;

			//wechselt die Strategie
			case "switch":
				if (managerPos == 0) {
					managerPos = 1;
					manager = managers[managerPos];
					System.out.println("zur Verketteten Liste Strategie gewechselt");
				} else if (managerPos == 1) {
					managerPos = 0;
					manager = managers[managerPos];
					System.out.println("zur Datenbank Strategie gewechselt");
				}
				break;
			//Manpage
			case "help":
				System.out.println(
						"Befehle:\nread -> Datei auslesen und Vokabeln zur Liste / Datenbank hinzufuegen\nwrite -> Liste in einer Datei sichern\nadd -> Datei zur Liste / Datenbank hinzufuegen\ndelete -> Datei aus der Liste / Datenbank loeschen\ncall -> Vokabeln aus der Liste / Datenbank abfragen\nshow -> Alle Vokabeln in der Liste anzeigen\nswitch -> strategie wechseln\nclose -> Programm beenden\n");
				break;

				//Invalide Eingabe
			default:
				System.out.println("Eingabe fehlerhaft");
				break;
			}
		}

	}

}
