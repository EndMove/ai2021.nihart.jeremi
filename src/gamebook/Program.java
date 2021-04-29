package gamebook;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;
import gamebook.domains.Session;
import gamebook.supervisers.CheckSuperviser;
import gamebook.supervisers.EditSuperviser;
import gamebook.supervisers.ReadSuperviser;
import gamebook.swing.MainWindow;
import gamebook.swing.SwingCheckView;
import gamebook.swing.SwingEditView;
import gamebook.swing.SwingReaderView;

/**
 * Construit le système logiciel.
 * */
public class Program {
	
	/**
	 * Point d'entrée du programme
	 * */
	public static void main(String[] args) {
		// Créer un livre
		GameBook book = GameBookFactory.makeGameBook();
		
		// Créer la session
		Session sess = new Session(book);

		// Créer les superviser
		ReadSuperviser readSuperviser = new ReadSuperviser(sess, book);
		EditSuperviser editSuperviser = new EditSuperviser(book, readSuperviser);
		CheckSuperviser checkSuperviser = new CheckSuperviser();
		
		// Créer l'interface
		var mainWindow = new MainWindow(
				new SwingReaderView(readSuperviser),
				new SwingEditView(editSuperviser),
				new SwingCheckView(checkSuperviser)
		);
		mainWindow.setVisible(true);
	}

}
