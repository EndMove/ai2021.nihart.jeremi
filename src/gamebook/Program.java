package gamebook;

import java.util.List;

import gamebook.domains.*;
import gamebook.supervisers.*;
import gamebook.swing.*;

/**
 * Construit le syst�me logiciel.
 * */
public class Program {
	
	/**
	 * Point d'entr�e du programme
	 * */
	public static void main(String[] args) {
		// Cr�ation des paragraphes
		List<Paragraph> ps = GameBookFactory.makeParagraphs();

		// Cr�ation des choix affili�s aux paragraphes
		GameBookFactory.makeChoices(ps);

		// Cr�er le livre
		GameBook book = new GameBook(GameBookFactory.BOOK_TITLE, ps);

		// Cr�er la session
		Session sess = new Session(book);

		// Cr�er l'interface
		var mainWindow = new MainWindow(
				new SwingReaderView(new ReadSuperviser(sess, book)),
				new SwingEditView(new EditSuperviser()),
				new SwingCheckView(new CheckSuperviser())
		);
		mainWindow.setVisible(true);
	}

}
