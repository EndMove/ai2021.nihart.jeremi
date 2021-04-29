package gamebook.supervisers;

import java.util.List;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;

/**
 * Traite les demandes issues d'une {@code EditView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu.
 * */
public final class EditSuperviser {

	private EditView view;
	private final GameBook book;
	private final BookEditedEventHandler rsHandler; // peut �tre pas bon ...
	
	/**
	 * Initialise un superviseur pour �diter le livre-jeu {@code gameBook}
	 * @param handler 
	 * @param b 
	 * */
	public EditSuperviser(GameBook book, BookEditedEventHandler rsHandler) {
		this.book = (book != null) ? book : new GameBook(null, null);
		this.rsHandler = rsHandler;
	}
	
	public void setView(EditView view) {
		this.view = view;
		this.view.setTitle(book.getTitle());
		this.view.setParagraphs(book.getParagraphsContents());
		this.view.setChoices(List.of("CHOIX 1","CHOIX 2","CHOIX 3"));
		this.view.setCurrentParagraphContent("PARAGRAPHE 1");
		this.view.setSelectedParagraph(0);
		this.view.setSelectedChoice("CHOIX 2", 2);
	}

	/**
	 * Pr�sente le contenu du paragraphe demand� par l'utilisateur.
	 * */
	public void onSelectedParagraphChanged(int paragraphIndex) {
		book.getParagraphByID(paragraphIndex);
	}
	

	/**
	 * Modifie le titre du livre si {@code newTitle} est d�fini et non-blanc.
	 * */
	public void onTitleChanged(String newTitle) {
		book.setTitle(newTitle);
		rsHandler.onBookEdited();
	}

	/**
	 * Ajoute un nouveau paragraphe � la fin du livre-jeu.
	 * La vue est mise � jour avec le contenu de ce nouveau paragraphe.
	 * */
	public void onNewParagraph() {
		
	}

	/**
	 * Supprime le paragraphe {@code index}.
	 * La m�thode ne fait rien si {@code index} est hors limite.
	 * */
	public void onDeleteParagraph(int index) {
		
	}

	/**
	 * Met � jour le paragraphe {@code index} du livre-jeu avec le contenu {@code newContent}.
	 * La m�thode ne fait rien si {@code index} est hors limite ou si index est hors-limite.
	 * */
	public void onParagraphContentChanged(int index, String newContent) {
		

	}
	/**
	 * Pr�sente le contenu du choix identifi� par {@code key}
	 * */
	public void onSelectedChoiceChanged(String key) {
		System.out.println("keypressed: "+key);
	}

	/**
	 * Ajoute un nouveau choix au paragraphe courant.
	 * */
	public void onNewChoice() {
		
	}

	/**
	 * Retire le choix correspondant � {@code key} du paragraphe courant.
	 * */
	public void onDeleteChoice(String key) {
		
	}

	/**
	 * Remplace le libell� {@code oldKey} par {@code newKey} dans le paragraphe courant.
	 * */
	public void onChoiceLabelChanged(String oldKey, String newKey) {
		
	}

	/**
	 * Modifie la cible du choix {@code key} du paragraphe courant.
	 * */
	public void onChoiceTargetChanged(String key, int index) {
		
	}

}
