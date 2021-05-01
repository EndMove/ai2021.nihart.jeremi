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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	// Constante(s) publique(s)
	public static final String PARAGRAPH_HEAD = "Paragraphe";
	public static final String PARAGRAPH_CONTENT = "Nouveau paragraphe";
	public static final String PARAGRAPH_CHOICE = "Nouveau choix";

	// Variables Objet
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
	 * Getter, permettant de r�cup�rer une List de toutes les cl�s
	 * des choix disponibles pour le paragraphe courant.
	 *
	 * @return      Collection des cl�s des choix.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public List<String> getChoices() {
		List<String> list = new ArrayList<>();
		for (String i : choices.keySet()) {
			list.add(i);
		}
		return list;
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
	public boolean setContent(String content) {
		if (content == null || content.isBlank()) {
			if (this.content == null) {
				this.content = PARAGRAPH_CONTENT;
			}
			return false;
		} else {
			this.content = content;
			return true;
		}
	}
	
	/** docs
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
		key = (key == null || key.isBlank()) ? PARAGRAPH_CHOICE : key;
		choices.put(key, paragraph);
	}
	
	// docs
	public void deleteChoice(String key) {
		choices.remove(key);
	}
	
	// docs
	public boolean updateChoiceKey(String oldKey, String newKey) {
		if (newKey == null || newKey.isBlank()) {
			return false;
		} else if (choices.containsKey(oldKey) && !choices.containsKey(newKey)) {
			addChoice(newKey, getParagraphByChoiceKey(oldKey));
			choices.remove(oldKey);
			return true;
		}
		return false;
	}
	
	//docs
	public void updateChoiceParagraph(String key, Paragraph paragraph) {
		choices.replace(key, paragraph);
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
	
	/**
	 * Red�finition de la m�thode equals(obj)
	 */
	@Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Paragraph) && obj.toString().equals(toString());
    }
}
