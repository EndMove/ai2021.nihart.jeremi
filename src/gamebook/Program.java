package gamebook;

import java.util.List;

import gamebook.domains.*;
import gamebook.supervisers.*;
import gamebook.swing.*;

/**
 * Construit le système logiciel.
 * */
public class Program {
	
	/**
	 * Point d'entrée du programme
	 * */
	public static void main(String[] args) {
		// Création des paragraphes
		List<Paragraph> ps = GameBookFactory.makeParagraphs();

		// Création des choix affiliés aux paragraphes
		GameBookFactory.makeChoices(ps);

		// Créer le livre
		GameBook book = new GameBook(GameBookFactory.BOOK_TITLE, ps);

		// Créer la session
		Session sess = new Session(book);

		// Créer l'interface
		var mainWindow = new MainWindow(
				new SwingReaderView(new ReadSuperviser(sess, book)),
				new SwingEditView(new EditSuperviser()),
				new SwingCheckView(new CheckSuperviser())
		);
		mainWindow.setVisible(true);
	}

}
