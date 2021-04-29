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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Paragraph
 *
 * Permet d'attribuer un numéro, du contenu et des
 * possibilitées (choix) à chaque paragraphe du livre.
 *
 * @version     1.0
 *
 * @author      Jérémi Nihart
 */
public class Paragraph {
	private static final String HEAD = "Paragraphe";
	private final int id;
	private final String content;
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
	public Paragraph(int id, String content) {
		this.id = (id >= 1) ? id : 0;
		this.content = (content != null) ? content : "No content";
	}
	
	/** 
	 * Getter, permettant de récupérer l'id / numéro du paragraphe.
	 *
	 * @return      ID du paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public int getID() {
		return id;
	}
	
	/** 
	 * Getter, permettant de récupérer le titre complet
	 * du paragraphe (<u>Paragraphe #</u> ou '#' représente le numéro).
	 *
	 * @return      Le titre complet du paragraphe.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public String getHead() {
		return String.format("%s %s", HEAD, id);
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
	 * Getter, permettant de récupérer une Collection de toutes les clés
	 * des choix disponibles pour le paragraphe courant.
	 *
	 * @return      Collection des clés des choix.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public Collection<String> getChoices() {
		return choices.keySet();
		//return List.of(choices.keySet());
		//return choices.keySet().toArray(String[]::new);
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
		return String.format("Paragraph(id=%d, content=%s, choices_count=%d)", id, content, choices.size());
	}
}
