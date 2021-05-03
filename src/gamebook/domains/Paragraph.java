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
 * Permet de créer un paragraphe du livre et de lui assigner
 * un contenu par defaut, ainsi que de lui attribuer des choix.
 *
 * @version     1.1
 *
 * @author      Jérémi Nihart
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
	 * @param		content Contenu du paragraphe, si aucun contenu n'est spécifié
	 *                      la fonction d'assigniation du contenu définira
	 *                      {@link Paragraph#PARAGRAPH_CONTENT} comme valeur par defaut.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph#setContent(String)
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
	 * @return      List des clés des choix.
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
	
	/** 
	 * Setter, permettant de définir le contenu du paragraphe courant.
	 * 
	 * <p>Le paramètre 'content' doit être une valeur non null et non vide. Dans le cas contaire
	 * et si le contenu courrant de l'objet est null celui-ci est défini à {@link Paragraph#PARAGRAPH_CONTENT}
	 * si il ne l'est pas, il reste inchangé.<p>
	 *
	 * @return      True: Si le contenu spécifié a été défini comme contenu courrant<br>
	 * 				      False: Si le contenu spécifié a été refusé.
	 * @param		content La valeur que l'on souhaite définir comme contenu du paragraphe.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#PARAGRAPH_CONTENT
	 * @author      Jérémi Nihart
	 */
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
	
	/**
	 * Permet d'ajouter un choix au paragraph avec une clé 'key' pointant
	 * sur un Objet 'paragraph' {@link Paragraph}.
	 *
	 * @param		key Clé du choix à associer au paragraphe. Si la clé est
	 *                  null ou vide elle sera définie sur {@link Paragraph#PARAGRAPH_CHOICE}.
	 * @param  		paragraph Objet paragraphe {@link Paragraph} a assigné à la clé.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph#PARAGRAPH_CHOICE
	 * @author      Jérémi Nihart
	 */
	public void addChoice(String key, Paragraph paragraph) {
		String workKey = (key == null || key.isBlank()) ? PARAGRAPH_CHOICE : key;
		choices.put(workKey, paragraph);
	}
	
	/**
	 * Permet de supprimer un choix du paragraphe.
	 *
	 * @param		key Clé du choix à supprimer du paragraphe courrant.
	 *
	 * @since       1.1
	 *
	 * @author      Jérémi Nihart
	 */
	public void deleteChoice(String key) {
		choices.remove(key);
	}
	
	/**
	 * Permet de mettre à jour la clé d'un choix du paragraphe.
	 *
	 * @param		oldKey Valeur actuel de la clé.
	 * @param       newKey Nouvelle valeur de la clé.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph
	 * @author      Jérémi Nihart
	 */
	public boolean updateChoiceKey(String oldKey, String newKey) {
		if (newKey == null || newKey.isBlank()) {
			return false;
		}
		if (choices.containsKey(oldKey) && !choices.containsKey(newKey)) {
			addChoice(newKey, getParagraphByChoiceKey(oldKey));
			choices.remove(oldKey);
			return true;
		}
		return false;
	}
	
	/**
	 * Permet de mettre à jour le paragraph vers lequel pointe un choix.
	 *
	 * @param		key Clé du choix dont il faut mettre à jour le paragraphe.
	 * @param  		paragraph Objet paragraphe {@link Paragraph} a assigné à la clé.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph
	 * @author      Jérémi Nihart
	 */
	public void updateChoiceParagraph(String key, Paragraph paragraph) {
		choices.replace(key, paragraph);
	}
	
	/** 
	 * Indique si des choix sont ou pas disponibles pour
	 * ce paragraphe (le paragraphe courrant).
	 *
	 * @return		True: Si le paragraphe a des choix;<br>
	 * 				      False: Si le paragraphe n'a pas de choix.
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
	
	/**
	 * Redéfinition de la méthode equals(obj)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
        if (obj instanceof Paragraph) {
        	String objString = obj.toString();
        	return objString.equals(toString());
        }
        return false;
    }
}
