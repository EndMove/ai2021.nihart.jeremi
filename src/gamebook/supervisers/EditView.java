package gamebook.supervisers;

import java.util.*;

/**
 * La vue d'édition permet à l'auteur d'éditer son livre
 * */
public interface EditView {
	/**
	 * Définit le titre du livre.
	 * */
	void setTitle(String newTitle);
	
	/**
	 * Définit les libellés des paragraphes à éditer.
	 * */
	void setParagraphs(List<String> paragraphs);
	
	/**
	 * Définit le paragraphe sélectionné comme étant celui de position {@code index}.
	 * 
	 * @param index un entier compris entre {@code 0 <= index < paragraphs.size()}
	 * */
	void setSelectedParagraph(int index);
	
	/**
	 * Définit le contenu à afficher pour le paragraphe courant.
	 * */
	void setCurrentParagraphContent(String content);
	
	/**
	 * Définit les choix qui se présentent à l'utilisateur.
	 * 
	 * @param une collection de choix sans ordre précis.
	 * */
	void setChoices(Collection<String> choices);
	
	/**
	 * Définit le choix courant.
	 * 
	 * @param key le label du choix courant. Il doit correspondre à un String défini la collection de choix passée à {@code setChoices}
	 * @param targetIndex la position du paragraphe dans la liste passée à {@code setParagraphs}.
	 * */
	void setSelectedChoice(String key, int targetIndex);
}
