package prak5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class VokabelTrainer {

	private final String DEFAULTPATH = "src/prak5/";
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hallo");
		new VokabelTrainer();
	}

	public VokabelTrainer() throws Exception {
		
		ArrayList<Element> allElements = new ArrayList<Element>();

		Scanner sc = new Scanner(System.in);

		VokabelManager manager = null;
		VokabelManager[] managers = new VokabelManager[2];
		int managerPos = 0;

		managers[0] = new DatenbankManager();
		managers[1] = new VerketteteListeManager();
		manager = managers[managerPos];

		while (true) {

			String menueValue = "";
			System.out.println("Was wollen Sie machen?\nGeben Sie einen Befehl ein.\nGeben Sie help für \"Hilfe\" ein");
			String eingabe = "";

			eingabe = sc.nextLine();
			menueValue = eingabe;

			switch (menueValue) {
			//TODO
			case "read":
				System.out.println("Aus welcher Datei soll gelesen werden?");
				String readFile = sc.nextLine();
//				list.read(readFile);
				break;
			case "write":
				if (manager instanceof DatenbankManager) {
					System.out.println("Dumm? lololol");
				} else {

					ArrayList<Element> list = manager.getAllVokabeln();
					if (list.isEmpty()) {
						System.out.println("Die Vokabelliste ist Leer!");
					} else {

						System.out.println("In welche Datei soll geschrieben werden?");
						String writeFile = sc.nextLine();
						
						File file = new File(DEFAULTPATH + writeFile + ".txt");
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
			case "add":
				System.out.print("Deutsch: ");
				String ger = sc.nextLine();
				System.out.print("Englisch: ");
				String eng = sc.nextLine();

				Element newElem = new Element();
				newElem.setGer(ger);
				newElem.setEng(eng);
				manager.save(newElem);
				break;
			case "delete":
				System.out.print("Vokabel eingeben welche geloescht werden soll (auf Deutsch): ");
				String gerDel = sc.nextLine();
				manager.delete(gerDel);
				break;
				//TODO
			case "call":
//				list.call();
				break;
			case "show":
				for (Element elem : manager.getAllVokabeln()) {
					System.out.println("GER: " + elem.getGer() + " ENG: " + elem.getEng());
				}
				break;
			case "close":
				System.out.println("Juhu, dieses coole Programm wird jetzt beendet :)))))");
				sc.close();
				System.exit(0);
				break;
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
			case "help":
				System.out.println(
						"Befehle:\nread -> Datei auslesen und Vokabeln zur Liste hinzufuegen\nwrite -> Liste in einer Datei sichern\nadd -> Datei zur Liste hinzufuegen\ndelete -> Datei aus der Liste loeschen\ncall -> Vokabeln aus der Liste abfragen\nshow -> Alle Vokabeln in der Liste anzeigen\nclose -> Programm beenden\n");
				break;

			default:
				System.out.println("Eingabe fehlerhaft");
				break;
			}
		}

	}

}
