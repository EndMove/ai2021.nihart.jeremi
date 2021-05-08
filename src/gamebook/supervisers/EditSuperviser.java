package gamebook.supervisers;

import java.util.ArrayList;
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
		this.currentParagraph = book.getParagraphByID(0);
	}
	
	/**
	 * Initialise la vue.
	 * @param view
	 */
	public void setView(EditView view) {
		this.view = view;
		this.view.setTitle(book.getTitle());
		refreshParagraphAll();
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}
	
	/**
	 * Permet de rafr�chir le contenu des paragraphes pr�sent dans les combo-box,
	 * le contenu du paragraphe courant, ainsi que s�lectionner le pargraphe affich�.
	 * 
	 * @see 			Paragraph#getContent()
	 * @see 			GameBook#getParagraphsContents()
	 * @see 			GameBook#getParagraphIdByObject(Paragraph)
	 * @author			J�r�mi Nihart
	 */
	private void refreshParagraphAll() {
		this.view.setParagraphs(book.getParagraphsContents());
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setSelectedParagraph(book.getParagraphIdByObject(currentParagraph));
	}
	
	/**
	 * Permet de rafr�chir les choix pr�sent dans le combo-box pour le paragraphe courant,
	 * le choix s�lection� et le paragraphe afili�.
	 * 
	 * @see				Paragraph#getChoices()
	 * @see				Paragraph#getParagraphByChoiceKey(String)
	 * @see 			GameBook#getParagraphIdByObject(Paragraph)
	 * @author			J�r�mi Nihart
	 */
	private void refreshSelectedChoice(String key) {
		List<String> choices = new ArrayList<>(currentParagraph.getChoices()); // TODO
		String choiceKey;  // La cl� du choix qu'il faudrait afficher.
		int paragraphID;   // L'id du paragraphe associ� � ce choix.
		if (choices.isEmpty()) {
			choiceKey = "";
			paragraphID = -1;
		} else {
			choiceKey = (key == null) ? choices.get(0) : key;
			paragraphID = book.getParagraphIdByObject(currentParagraph.getParagraphByChoiceKey(choiceKey));
		}
		this.view.setSelectedChoice(choiceKey, paragraphID);
	}
	
	/**
	 * Surcharge de la m�thode priv�e {@link EditSuperviser#refreshSelectedChoice(String)}.
	 * 
	 * @see				EditSuperviser#refreshSelectedChoice(String)
	 * @author			J�r�mi Nihart
	 */
	private void refreshSelectedChoice() {
		refreshSelectedChoice(null);
	}

	/**
	 * Pr�sente le contenu du paragraphe demand� par l'utilisateur.
	 */
	public void onSelectedParagraphChanged(int paragraphIndex) {  // OK
		// Action(s) de la m�thode
		currentParagraph = book.getParagraphByID(paragraphIndex);
		
		// Actualisation vue
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}
	

	/**
	 * Modifie le titre du livre si {@code newTitle} est d�fini et non-blanc.
	 */
	public void onTitleChanged(String newTitle) {  // OK
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
	 */
	public void onNewParagraph() {  // OK
		// Action(s) de la m�thode
		currentParagraph = new Paragraph(null);
		book.addParagraph(currentParagraph);
		
		// Actualisation vue
		refreshParagraphAll();
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}

	/**
	 * Supprime le paragraphe {@code index}.
	 * La m�thode ne fait rien si {@code index} est hors limite.
	 */
	public void onDeleteParagraph(int index) {  // OK
		// Action(s) de la m�thode
		if (book.deleteParagraph(index)) {
			rsHandler.onBookEdited();
		}
		this.currentParagraph = book.getLastParagraph();
		
		// Actualisation vue
		refreshParagraphAll();
		refreshSelectedChoice();
	}

	/**
	 * Met � jour le paragraphe {@code index} du livre-jeu avec le contenu {@code newContent}.
	 * La m�thode ne fait rien si {@code index} est hors limite ou si index est hors-limite.
	 */
	public void onParagraphContentChanged(int index, String newContent) {  // OK
		// Action(s) de la m�thode
		if (Paragraph.setContent(book.getParagraphByID(index), newContent)) {
			rsHandler.onBookEdited();
		}
		
		// Actualisation vue
		refreshParagraphAll();
	}

	/**
	 * Pr�sente le contenu du choix identifi� par {@code key}
	 */
	public void onSelectedChoiceChanged(String key) {  // OK
		// Action(s) de la m�thode
		// Actualisation vue
		refreshSelectedChoice(key);
	}

	/**
	 * Ajoute un nouveau choix au paragraphe courant.
	 */
	public void onNewChoice() {  // OK
		// Action(s) de la m�thode
		currentParagraph.addChoice(null, book.getParagraphByID(0));
		rsHandler.onBookEdited();
		
		// Actualisation vue
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice(Paragraph.PARAGRAPH_CHOICE);
	}

	/**
	 * Retire le choix correspondant � {@code key} du paragraphe courant.
	 */
	public void onDeleteChoice(String key) {  // OK
		// Action(s) de la m�thode
		currentParagraph.deleteChoice(key);
		rsHandler.onBookEdited();

		// Actualisation vue
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}

	/**
	 * Remplace le libell� {@code oldKey} par {@code newKey} dans le paragraphe courant.
	 */
	public void onChoiceLabelChanged(String oldKey, String newKey) {  // OK
		// Action(s) de la m�thode
		if (currentParagraph.updateChoiceKey(oldKey, newKey)) {
			rsHandler.onBookEdited();

		// Actualisation vues
			this.view.setChoices(currentParagraph.getChoices());
			refreshSelectedChoice(newKey);
		} else {
			refreshSelectedChoice(oldKey);
		}
	}

	/**
	 * Modifie la cible du choix {@code key} du paragraphe courant.
	 */
	public void onChoiceTargetChanged(String key, int index) {  // OK
		// Action(s) de la m�thode
		currentParagraph.updateChoiceParagraph(key, book.getParagraphByID(index));
		rsHandler.onBookEdited();

		// Actualisation vue
		refreshSelectedChoice(key);
	}

}
