package gamebook.supervisers;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;

/**
 * Traite les demandes issues d'une {@code CheckView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu.
 */
public class CheckSuperviser implements BookEditedEventHandler {
	
	private CheckView view;
	private final GameBook book;
	private final GameBookStatement swtte;
	private final GameBookStatement tpf;

	/**
	 * Construit une instance du superviser � l'aide d'un gamebook
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
	 * Permet d'ajouter un r�sultat � la vue
	 * 
	 * @param 			GBStatement Un objet {@link GameBookStatement}
	 * 								(exposition des m�thodes de l'interface).
	 * 
	 * @see 			GameBookStatement#getTitle()
	 * @see				GameBookStatement#getDecription()
	 * @see				GameBookStatement#getResults()
	 * @author			J�r�mi Nihart
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
	 * Met � jour la vue en lui donnant pour titre celui du livre
	 * et en construisant les cadres de r�sultat des analyses effectu�s.
	 */
	public void onParse() {
		// V�rifie que la vue est d�j� d�finie
		if (this.view == null) {
			return;
		}
		// Ex�cution des algorithmes
		swtte.parse(book);
		tpf.parse(book);
		// Nettoyage de la vue et affichage des r�sultats
		this.view.setTitle(book.getTitle());
		this.view.clearResult();
		// -- TargetParagraphFrequency
		addViewResult(tpf);
		// -- ShortestWayToTheEnd
		addViewResult(swtte);
	}
	
	/**
	 * M�thode d'�v�nement, est appel� lorsque le livre est modifi�.
	 */
	@Override
	public void onBookEdited() {
		onParse();
	}

}
