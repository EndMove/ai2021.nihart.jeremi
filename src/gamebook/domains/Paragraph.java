/**
 * File name    : Paragraph.java
 *
 * Description  : Classe gérant les paragraphe et choix assosiés.
 *
 * Version      : 1.0
 * Since        : 1.0
 * Date         : 21/04/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 */
package gamebook.domains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Paragraph
 *
 * Permet d'attribuer un numéro, du contenu et des
 * possibilitées (choix) à chaque paragraphe du livre.
 *
 * @version     1.1
 *
 * @author      Jérémi Nihart
 */
public class Paragraph {

	public static final String PARAGRAPH_HEAD = "Paragraphe";
	private static final String PARAGRAPH_CONTENT = "Nouveau paragraphe";

	private String content;
	private final Map<String, Paragraph> choices = new HashMap<>();
	
	/** 
	 * Constructeur
	 *
	 * @param		id Numéro du livre (plus grand ou égale à 1).
	 * @param		content Contenu du paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public Paragraph(String content) {
		setContent(content);
	}
	
	/** 
	 * Getter, permettant de récupérer le contenu du paragraphe.
	 *
	 * @return      Le contenu du paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public String getContent() {
		return content;
	}
	
	/** 
	 * Getter, permettant de récupérer une List de toutes les clés
	 * des choix disponibles pour le paragraphe courant.
	 *
	 * @return      Collection des clés des choix.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public List<String> getChoices() {
		List<String> list = new ArrayList<>();
		for (String i : choices.keySet()) {
			list.add(i);
		}
		return list;
	}
	
	/** 
	 * Getter, permettant de récupérer l'objet {@link Paragraph} associé à la clé 'key'.
	 *
	 * @return      Un Objet {@link Paragraph}.
	 * @param		key Clé du choix associé à un paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
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
	 * Permet d'ajouter un choix au paragraph avec une clé 'key' pointant
	 * sur un Objet 'paragraph' {@link Paragraph}.
	 *
	 * @param		key Clé du choix à associer au paragraphe.
	 * @param  		paragraph Objet paragraphe {@link Paragraph}.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph
	 * @author      Jérémi Nihart
	 */
	public void addChoice(String key, Paragraph paragraph) {
		if (key == null || key.isBlank()) {
			key = "Nouveau choix";
		}
		choices.put(key, paragraph);
	}
	
	// docs
	public void updateChoiceKey(String oldKey, String newKey) {
		if (choices.containsKey(oldKey) && !choices.containsKey(newKey)) {
			addChoice(newKey, getParagraphByChoiceKey(oldKey));
			choices.remove(oldKey);
		}
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
	 * @author      Jérémi Nihart
	 */
	public boolean hasChoice() {
		return !choices.isEmpty();
	}
	
	/**
	 * Redéfinition de la méthode toString()
	 */
	@Override
	public String toString() {
		return String.format("Paragraph(content=%s, choices_count=%d)", content, choices.size());
	}
}
