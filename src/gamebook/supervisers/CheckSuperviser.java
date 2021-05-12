package gamebook.supervisers;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;

/**
 * Traite les demandes issues d'une {@code CheckView}.
 * À cette fin, elle contrôle d'autres objets parmi lesquels un livre-jeu.
 * */
public class CheckSuperviser implements BookEditedEventHandler {
	
	private CheckView view;
	private final GameBook book;
	private final GameBookStatement swtte;
	private final GameBookStatement tpf;

	/**
	 * Construit une instance du superviser à l'aide d'un gamebook
	 * et de plusieurs {@code parsers}
	 * */
	public CheckSuperviser(GameBook book, GameBookStatement swtte, GameBookStatement tpf) {
		this.book = (book != null) ? book : new GameBook(null, null);
		this.swtte = swtte;
		this.tpf = tpf;
	}

	public void setView(CheckView view) {
		this.view = view;
		onParse();
	}

	/**
	 * Met à jour la vue en lui donnant pour titre celui du livre
	 * et en construisant les cadres de résultat des analyses effectués.
	 * */
	public void onParse() {
		// Vérifie que la vue est déjà définie
		if (this.view == null) return;
		// Exécution des algorithmes
		swtte.parse(book);
		tpf.parse(book);
		// Affichage des résultats des algorithmes
		this.view.clearResult();
		this.view.setTitle(book.getTitle());
		// -- TargetParagraphFrequency
		this.view.startResultFor(tpf.getTitle());
		this.view.setDescription(tpf.getDecription());
		for (String s : tpf.getResults()) {
			this.view.addResultItem(s);
		}
		this.view.endResult();
		// -- ShortestWayToTheEnd
		this.view.startResultFor(swtte.getTitle());
		this.view.setDescription(swtte.getDecription());
		for (String s : swtte.getResults()) {
			this.view.addResultItem(s);
		}
		this.view.endResult();
	}

	@Override
	public void onBookEdited() {
		onParse();
	}

}
