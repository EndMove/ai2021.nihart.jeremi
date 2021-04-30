package gamebook.supervisers;

import java.util.List;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

/**
 * Traite les demandes issues d'une {@code EditView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu.
 * */
public final class EditSuperviser {

	private EditView view;
	private final BookEditedEventHandler rsHandler;
	private final GameBook book;
	private Paragraph currentParagraph;
	
	/**
	 * Initialise un superviseur pour �diter le livre-jeu {@code gameBook}
	 * @param handler 
	 * @param b 
	 * */
	public EditSuperviser(GameBook book, BookEditedEventHandler rsHandler) {
		this.book = (book != null) ? book : new GameBook(null, null);
		this.rsHandler = rsHandler;
		this.currentParagraph = book.getParagraphFirst();
	}
	
	public void setView(EditView view) {
		this.view = view;
		this.view.setTitle(book.getTitle());
		this.view.setParagraphs(book.getParagraphsContents());
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setChoices(currentParagraph.getChoices());
		this.view.setSelectedChoice("", 0);
		this.view.setSelectedParagraph(0);
	}

	/**
	 * Pr�sente le contenu du paragraphe demand� par l'utilisateur.
	 * */
	public void onSelectedParagraphChanged(int paragraphIndex) {  // OK
		System.out.println("onSelectedParagraphChanged: "+paragraphIndex);
		// Action(s) de la m�thode
		currentParagraph = book.getParagraphByID(paragraphIndex);
		// Actualisation vue
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
	}
	

	/**
	 * Modifie le titre du livre si {@code newTitle} est d�fini et non-blanc.
	 * */
	public void onTitleChanged(String newTitle) {  // OK
		System.out.println("onTitleChanged: "+newTitle);
		// Action(s) de la m�thode
		if (book.setTitle(newTitle)) {
			rsHandler.onBookEdited();
		}
		// Actualisation vue
		view.setTitle(book.getTitle());
	}

	/**
	 * Ajoute un nouveau paragraphe � la fin du livre-jeu.
	 * La vue est mise � jour avec le contenu de ce nouveau paragraphe.
	 * */
	public void onNewParagraph() {  // OK
		System.out.println("onNewParagraph: ");
		// Action(s) de la m�thode
		currentParagraph = new Paragraph(null);
		book.addParagraph(currentParagraph);
		// Actualisation vue
		this.view.setParagraphs(book.getParagraphsContents());
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setChoices(currentParagraph.getChoices());
		this.view.setSelectedChoice("", 0);
		this.view.setSelectedParagraph(book.getParagraphIdByObject(currentParagraph));
	}

	/**
	 * Supprime le paragraphe {@code index}.
	 * La m�thode ne fait rien si {@code index} est hors limite.
	 * */
	public void onDeleteParagraph(int index) {
		System.out.println("onDeleteParagraph: "+index);
	}

	/**
	 * Met � jour le paragraphe {@code index} du livre-jeu avec le contenu {@code newContent}.
	 * La m�thode ne fait rien si {@code index} est hors limite ou si index est hors-limite.
	 * */
	public void onParagraphContentChanged(int index, String newContent) {  // OK
		System.out.println("onParagraphContentChanged : "+index+" "+newContent);
		// Action(s) de la m�thode
		if (book.setParagraphContent(book.getParagraphByID(index), newContent)) {
			rsHandler.onBookEdited();
		}
		// Actualisation vue
		this.view.setParagraphs(book.getParagraphsContents());
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setSelectedParagraph(book.getParagraphIdByObject(currentParagraph));
	}
	/**
	 * Pr�sente le contenu du choix identifi� par {@code key}
	 * */
	public void onSelectedChoiceChanged(String key) {
		System.out.println("onSelectedChoiceChanged: "+key);
	}

	/**
	 * Ajoute un nouveau choix au paragraphe courant.
	 * */
	public void onNewChoice() {
		System.out.println("onNewChoice: ");
	}

	/**
	 * Retire le choix correspondant � {@code key} du paragraphe courant.
	 * */
	public void onDeleteChoice(String key) {
		System.out.println("onDeleteChoice: "+key);
	}

	/**
	 * Remplace le libell� {@code oldKey} par {@code newKey} dans le paragraphe courant.
	 * */
	public void onChoiceLabelChanged(String oldKey, String newKey) {
		System.out.println("onChoiceLabelChanged: "+oldKey+" "+newKey);
	}

	/**
	 * Modifie la cible du choix {@code key} du paragraphe courant.
	 * */
	public void onChoiceTargetChanged(String key, int index) {
		System.out.println("onChoiceTargetChanged: "+key+" "+index);
	}

}
