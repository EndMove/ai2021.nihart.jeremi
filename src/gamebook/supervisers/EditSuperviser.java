package gamebook.supervisers;

import java.util.List;

/**
 * Traite les demandes issues d'une {@code EditView}.
 * À cette fin, elle contrôle d'autres objets parmi lesquels un livre-jeu.
 * */
public final class EditSuperviser {

	private EditView view;
	
	/**
	 * Initialise un superviseur pour éditer le livre-jeu {@code gameBook}
	 * */
	public EditSuperviser() {
	}
	
	public void setView(EditView view) {
		this.view = view;
		this.view.setTitle("LE TITRE VIENT ICI");
		this.view.setParagraphs(List.of("PARAGRAPHE 1","PARAGRAPHE 2","PARAGRAPHE 3","PARAGRAPHE 4"));
		this.view.setChoices(List.of("CHOIX 1","CHOIX 2","CHOIX 3"));
		this.view.setCurrentParagraphContent("PARAGRAPHE 1");
		this.view.setSelectedParagraph(0);
		this.view.setSelectedChoice("CHOIX 2", 2);
	}

	/**
	 * Présente le contenu du paragraphe demandé par l'utilisateur.
	 * */
	public void onSelectedParagraphChanged(int paragraphIndex) {
	}
	

	/**
	 * Modifie le titre du livre si {@code newTitle} est défini et non-blanc.
	 * */
	public void onTitleChanged(String newTitle) {
	}

	/**
	 * Ajoute un nouveau paragraphe à la fin du livre-jeu.
	 * La vue est mise à jour avec le contenu de ce nouveau paragraphe.
	 * */
	public void onNewParagraph() {
	}

	/**
	 * Supprime le paragraphe {@code index}.
	 * La méthode ne fait rien si {@code index} est hors limite.
	 * */
	public void onDeleteParagraph(int index) {
	}

	/**
	 * Met à jour le paragraphe {@code index} du livre-jeu avec le contenu {@code newContent}.
	 * La méthode ne fait rien si {@code index} est hors limite ou si index est hors-limite.
	 * */
	public void onParagraphContentChanged(int index, String newContent) {

	}
	/**
	 * Présente le contenu du choix identifié par {@code key}
	 * */
	public void onSelectedChoiceChanged(String key) {	
	}

	/**
	 * Ajoute un nouveau choix au paragraphe courant.
	 * */
	public void onNewChoice() {
	}

	/**
	 * Retire le choix correspondant à {@code key} du paragraphe courant.
	 * */
	public void onDeleteChoice(String key) {
	}

	/**
	 * Remplace le libellé {@code oldKey} par {@code newKey} dans le paragraphe courant.
	 * */
	public void onChoiceLabelChanged(String oldKey, String newKey) {
	}

	/**
	 * Modifie la cible du choix {@code key} du paragraphe courant.
	 * */
	public void onChoiceTargetChanged(String key, int index) {
	}

}
