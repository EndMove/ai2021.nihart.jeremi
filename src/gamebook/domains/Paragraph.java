/**
 * File name    : Paragraph.java
 *
 * Description  : Classe g�rant les paragraphe et choix assosi�s.
 *
 * Version      : 1.0
 * Since        : 1.0
 * Date         : 21/04/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
 */
package gamebook.domains;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Paragraph
 *
 * Permet d'attribuer un num�ro, du contenu et des
 * possibilit�es (choix) � chaque paragraphe du livre.
 *
 * @version     1.1
 *
 * @author      J�r�mi Nihart
 */
public class Paragraph {

	public static final String PARAGRAPH_HEAD = "Paragraphe";
	public static final String PARAGRAPH_CONTENT = "Nouveau paragraphe";

	private String content;
	private final Map<String, Paragraph> choices = new HashMap<>();
	
	/** 
	 * Constructeur
	 *
	 * @param		id Num�ro du livre (plus grand ou �gale � 1).
	 * @param		content Contenu du paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public Paragraph(String content) {
		setContent(content);
	}
	
	/** 
	 * Getter, permettant de r�cup�rer le contenu du paragraphe.
	 *
	 * @return      Le contenu du paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public String getContent() {
		return content;
	}
	
	/** 
	 * Getter, permettant de r�cup�rer une Collection de toutes les cl�s
	 * des choix disponibles pour le paragraphe courant.
	 *
	 * @return      Collection des cl�s des choix.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public Collection<String> getChoices() {
		return choices.keySet();
	}
	
	/** 
	 * Getter, permettant de r�cup�rer l'objet {@link Paragraph} associ� � la cl� 'key'.
	 *
	 * @return      Un Objet {@link Paragraph}.
	 * @param		key Cl� du choix associ� � un paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public Paragraph getParagraphByChoiceKey(String key) {
		return choices.get(key);
	}
	
	// docs
	public void setContent(String content) {
		this.content = (content == null || content.isBlank()) ? PARAGRAPH_CONTENT : content;
	}
	
	/** 
	 * Permet d'ajouter un choix au paragraph avec une cl� 'key' pointant
	 * sur un Objet 'paragraph' {@link Paragraph}.
	 *
	 * @param		key Cl� du choix � associer au paragraphe.
	 * @param  		paragraph Objet paragraphe {@link Paragraph}.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph
	 * @author      J�r�mi Nihart
	 */
	public void addChoice(String key, Paragraph paragraph) {
		choices.put(key, paragraph);
	}
	
	/** 
	 * Indique si des choix sont ou pas disponibles pour
	 * ce paragraphe (le paragraphe courrant).
	 *
	 * @return		True: si le paragraphe a des choix;<br>
	 * 				False: si le paragraphe n'a pas de choix.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public boolean hasChoice() {
		return !choices.isEmpty();
	}
	
	/**
	 * Red�finition de la m�thode toString()
	 */
	@Override
	public String toString() {
		return String.format("Paragraph(content=%s, choices_count=%d)", content, choices.size());
	}
}
