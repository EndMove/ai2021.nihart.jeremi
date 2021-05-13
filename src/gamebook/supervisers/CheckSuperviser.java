package gamebook.supervisers;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;

/**
 * Traite les demandes issues d'une {@code CheckView}.
 * À cette fin, elle contrôle d'autres objets parmi lesquels un livre-jeu.
 */
public class CheckSuperviser implements BookEditedEventHandler {
	
	private CheckView view;
	private final GameBook book;
	private final GameBookStatement swtte;
	private final GameBookStatement tpf;

	/**
	 * Construit une instance du superviser à l'aide d'un gamebook
	 * et de plusieurs {@code parsers}
	 */
	public CheckSuperviser(GameBook book, GameBookStatement swtte, GameBookStatement tpf) {
		this.book = (book != null) ? book : new GameBook(null, null);
		this.swtte = swtte;
		this.tpf = tpf;
	}
	
	/**
	 * Initialise la vue.
	 * @param view
	 */
	public void setView(CheckView view) {
		this.view = view;
		onParse();
	}
	
	/**
	 * Permet d'ajouter un résultat à la vue
	 * 
	 * @param 			GBStatement Un objet {@link GameBookStatement}
	 * 								(exposition des méthodes de l'interface).
	 * 
	 * @see 			GameBookStatement#getTitle()
	 * @see				GameBookStatement#getDecription()
	 * @see				GameBookStatement#getResults()
	 * @author			Jérémi Nihart
	 */
	public void addViewResult(GameBookStatement GBStatement) {
		this.view.startResultFor(GBStatement.getTitle());
		this.view.setDescription(GBStatement.getDecription());
		for (String result : GBStatement.getResults()) {
			this.view.addResultItem(result);
		}
		this.view.endResult();
	}

	/**
	 * Met à jour la vue en lui donnant pour titre celui du livre
	 * et en construisant les cadres de résultat des analyses effectués.
	 */
	public void onParse() {
		// Vérifie que la vue est déjà définie
		if (this.view == null) {
			return;
		}
		// Exécution des algorithmes
		swtte.parse(book);
		tpf.parse(book);
		// Nettoyage de la vue et affichage des résultats
		this.view.setTitle(book.getTitle());
		this.view.clearResult();
		// -- TargetParagraphFrequency
		addViewResult(tpf);
		// -- ShortestWayToTheEnd
		addViewResult(swtte);
	}
	
	/**
	 * Méthode d'évènement, est appelé lorsque le livre est modifié.
	 */
	@Override
	public void onBookEdited() {
		onParse();
	}

}
