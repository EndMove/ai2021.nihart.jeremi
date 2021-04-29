package gamebook.supervisers;

import java.util.List;

/**
 * Traite les demandes issues d'une {@code EditView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu.
 * */
public final class EditSuperviser {

	private EditView view;
	
	/**
	 * Initialise un superviseur pour �diter le livre-jeu {@code gameBook}
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
	 * Pr�sente le contenu du paragraphe demand� par l'utilisateur.
	 * */
	public void onSelectedParagraphChanged(int paragraphIndex) {
	}
	

	/**
	 * Modifie le titre du livre si {@code newTitle} est d�fini et non-blanc.
	 * */
	public void onTitleChanged(String newTitle) {
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
