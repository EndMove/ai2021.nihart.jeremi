package gamebook.supervisers;

import java.util.*;

/**
 * La vue d'�dition permet � l'auteur d'�diter son livre
 * */
public interface EditView {
	/**
	 * D�finit le titre du livre.
	 * */
	void setTitle(String newTitle);
	
	/**
	 * D�finit les libell�s des paragraphes � �diter.
	 * */
	void setParagraphs(List<String> paragraphs);
	
	/**
	 * D�finit le paragraphe s�lectionn� comme �tant celui de position {@code index}.
	 * 
	 * @param index un entier compris entre {@code 0 <= index < paragraphs.size()}
	 * */
	void setSelectedParagraph(int index);
	
	/**
	 * D�finit le contenu � afficher pour le paragraphe courant.
	 * */
	void setCurrentParagraphContent(String content);
	
	/**
	 * D�finit les choix qui se pr�sentent � l'utilisateur.
	 * 
	 * @param une collection de choix sans ordre pr�cis.
	 * */
	void setChoices(Collection<String> choices);
	
	/**
	 * D�finit le choix courant.
	 * 
	 * @param key le label du choix courant. Il doit correspondre � un String d�fini la collection de choix pass�e � {@code setChoices}
	 * @param targetIndex la position du paragraphe dans la liste pass�e � {@code setParagraphs}.
	 * */
	void setSelectedChoice(String key, int targetIndex);
}
