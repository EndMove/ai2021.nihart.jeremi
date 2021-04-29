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
 * Construit le syst�me logiciel.
 * */
public class Program {
	
	/**
	 * Point d'entr�e du programme
	 * */
	public static void main(String[] args) {
		// Cr�er un livre
		GameBook book = GameBookFactory.makeGameBook();
		
		// Cr�er la session
		Session sess = new Session(book);

		// Cr�er les superviser
		ReadSuperviser readSuperviser = new ReadSuperviser(sess, book);
		EditSuperviser editSuperviser = new EditSuperviser(book, readSuperviser);
		CheckSuperviser checkSuperviser = new CheckSuperviser();
		
		// Cr�er l'interface
		var mainWindow = new MainWindow(
				new SwingReaderView(readSuperviser),
				new SwingEditView(editSuperviser),
				new SwingCheckView(checkSuperviser)
		);
		mainWindow.setVisible(true);
	}

}
