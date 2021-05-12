package gamebook.supervisers;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;

/**
 * Traite les demandes issues d'une {@code CheckView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu.
 * */
public class CheckSuperviser implements BookEditedEventHandler {
	
	private CheckView view;
	private final GameBook book;
	private final GameBookStatement swtte;
	private final GameBookStatement tpf;

	/**
	 * Construit une instance du superviser � l'aide d'un gamebook
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
	 * Met � jour la vue en lui donnant pour titre celui du livre
	 * et en construisant les cadres de r�sultat des analyses effectu�s.
	 * */
	public void onParse() {
		// V�rifie que la vue est d�j� d�finie
		if (this.view == null) return;
		// Ex�cution des algorithmes
		swtte.parse(book);
		tpf.parse(book);
		// Affichage des r�sultats des algorithmes
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
