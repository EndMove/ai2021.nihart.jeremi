package gamebook.supervisers;

import java.util.ArrayList;
import java.util.List;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

/**
 * Traite les demandes issues d'une {@code EditView}.
 * À cette fin, elle contrôle d'autres objets parmi lesquels un livre-jeu.
 * */
public final class EditSuperviser {

	private EditView view;
	private final BookEditedEventHandler rsHandler;
	private final GameBook book;
	private Paragraph currentParagraph;
	
	/**
	 * Initialise un superviseur pour éditer le livre-jeu {@code gameBook}
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
	 * Permet de rafréchir le contenu des paragraphes présent dans les combo-box,
	 * le contenu du paragraphe courant, ainsi que sélectionner le pargraphe affiché.
	 * 
	 * @see 			Paragraph#getContent()
	 * @see 			GameBook#getParagraphsContents()
	 * @see 			GameBook#getParagraphIdByObject(Paragraph)
	 * @author			Jérémi Nihart
	 */
	private void refreshParagraphAll() {
		this.view.setParagraphs(book.getParagraphsContents());
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setSelectedParagraph(book.getParagraphIdByObject(currentParagraph));
	}
	
	/**
	 * Permet de rafréchir les choix présent dans le combo-box pour le paragraphe courant,
	 * le choix sélectioné et le paragraphe afilié.
	 * 
	 * @see				Paragraph#getChoices()
	 * @see				Paragraph#getParagraphByChoiceKey(String)
	 * @see 			GameBook#getParagraphIdByObject(Paragraph)
	 * @author			Jérémi Nihart
	 */
	private void refreshSelectedChoice(String key) {
		List<String> choices = new ArrayList<>(currentParagraph.getChoices()); // TODO
		String choiceKey;  // La clé du choix qu'il faudrait afficher.
		int paragraphID;   // L'id du paragraphe associé à ce choix.
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
	 * Surcharge de la méthode privée {@link EditSuperviser#refreshSelectedChoice(String)}.
	 * 
	 * @see				EditSuperviser#refreshSelectedChoice(String)
	 * @author			Jérémi Nihart
	 */
	private void refreshSelectedChoice() {
		refreshSelectedChoice(null);
	}

	/**
	 * Présente le contenu du paragraphe demandé par l'utilisateur.
	 */
	public void onSelectedParagraphChanged(int paragraphIndex) {  // OK
		// Action(s) de la méthode
		currentParagraph = book.getParagraphByID(paragraphIndex);
		
		// Actualisation vue
		this.view.setCurrentParagraphContent(currentParagraph.getContent());
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}
	

	/**
	 * Modifie le titre du livre si {@code newTitle} est défini et non-blanc.
	 */
	public void onTitleChanged(String newTitle) {  // OK
		// Action(s) de la méthode
		if (book.setTitle(newTitle)) {
			rsHandler.onBookEdited();
		}
		
		// Actualisation vue
		view.setTitle(book.getTitle());
	}

	/**
	 * Ajoute un nouveau paragraphe à la fin du livre-jeu.
	 * La vue est mise à jour avec le contenu de ce nouveau paragraphe.
	 */
	public void onNewParagraph() {  // OK
		// Action(s) de la méthode
		currentParagraph = new Paragraph(null);
		book.addParagraph(currentParagraph);
		
		// Actualisation vue
		refreshParagraphAll();
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}

	/**
	 * Supprime le paragraphe {@code index}.
	 * La méthode ne fait rien si {@code index} est hors limite.
	 */
	public void onDeleteParagraph(int index) {  // OK
		// Action(s) de la méthode
		if (book.deleteParagraph(index)) {
			rsHandler.onBookEdited();
		}
		this.currentParagraph = book.getLastParagraph();
		
		// Actualisation vue
		refreshParagraphAll();
		refreshSelectedChoice();
	}

	/**
	 * Met à jour le paragraphe {@code index} du livre-jeu avec le contenu {@code newContent}.
	 * La méthode ne fait rien si {@code index} est hors limite ou si index est hors-limite.
	 */
	public void onParagraphContentChanged(int index, String newContent) {  // OK
		// Action(s) de la méthode
		if (Paragraph.setContent(book.getParagraphByID(index), newContent)) {
			rsHandler.onBookEdited();
		}
		
		// Actualisation vue
		refreshParagraphAll();
	}

	/**
	 * Présente le contenu du choix identifié par {@code key}
	 */
	public void onSelectedChoiceChanged(String key) {  // OK
		// Action(s) de la méthode
		// Actualisation vue
		refreshSelectedChoice(key);
	}

	/**
	 * Ajoute un nouveau choix au paragraphe courant.
	 */
	public void onNewChoice() {  // OK
		// Action(s) de la méthode
		currentParagraph.addChoice(null, book.getParagraphByID(0));
		rsHandler.onBookEdited();
		
		// Actualisation vue
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice(Paragraph.PARAGRAPH_CHOICE);
	}

	/**
	 * Retire le choix correspondant à {@code key} du paragraphe courant.
	 */
	public void onDeleteChoice(String key) {  // OK
		// Action(s) de la méthode
		currentParagraph.deleteChoice(key);
		rsHandler.onBookEdited();

		// Actualisation vue
		this.view.setChoices(currentParagraph.getChoices());
		refreshSelectedChoice();
	}

	/**
	 * Remplace le libellé {@code oldKey} par {@code newKey} dans le paragraphe courant.
	 */
	public void onChoiceLabelChanged(String oldKey, String newKey) {  // OK
		// Action(s) de la méthode
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
		// Action(s) de la méthode
		currentParagraph.updateChoiceParagraph(key, book.getParagraphByID(index));
		rsHandler.onBookEdited();

		// Actualisation vue
		refreshSelectedChoice(key);
	}

}
