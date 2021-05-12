package gamebook;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;
import gamebook.domains.Session;
import gamebook.domains.statements.*;
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
		
		// Créer les Objet d'algorithme de vérification
		ShortestWayToTheEnd swtte = new ShortestWayToTheEnd();
		TargetParagraphFrequency tpf = new TargetParagraphFrequency();

		// Créer les superviser
		ReadSuperviser readSuperviser = new ReadSuperviser(sess, book);
		CheckSuperviser checkSuperviser = new CheckSuperviser(book, swtte, tpf);
		EditSuperviser editSuperviser = new EditSuperviser(book, readSuperviser, checkSuperviser);
		
		// Créer l'interface
		var mainWindow = new MainWindow(
				new SwingReaderView(readSuperviser),
				new SwingEditView(editSuperviser),
				new SwingCheckView(checkSuperviser)
		);
		mainWindow.setVisible(true);
	}

}
