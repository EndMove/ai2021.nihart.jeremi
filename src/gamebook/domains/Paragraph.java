/**
 * File name    : Paragraph.java
 *
 * Description  : Classe gérant les paragraphe et choix assosiés.
 *
 * Version      : 1.1
 * Since        : 1.0
 * Date         : 04/05/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
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
 * <hr>
 * 
 * <h2>L'interface utilisée : Map</h2>
 * <p>J'utilise une Map comme interface car j'ai besoin d'avoir un dictionaire clé valeur
 * pour accéder à mes choix. J'utilise le libellé du choix comme clé et un objet Paragraph comme valeur.</p>
 * 
 * <h2>L'implémentation utilisée : HashMap</h2>
 * <p>J'ai implémenté une HashMap car chaque clé doit est unique, et qu'il m'ait inutile de
 * trier les éléments qu'elle contient. De plus ses principales opérations ont une CTT de <b>O(1)</b></p>
 * 
 * Pincipales opérations :
 * <ul>
 * 	<li>keySet() :      <u>CTT:</u> <b>O(1)</b></li>
 * 	<li>get() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>put() :       	<u>CTT:</u> <b>O(1)</b></li>
 * 	<li>remove() :      <u>CTT:</u> <b>O(1)</b></li>
 *  <li>replace() :     <u>CTT:</u> <b>O(1)</b></li>
 *  <li>isEmpty() :     <u>CTT:</u> <b>O(1)</b></li>
 *  <li>containsKey() : <u>CTT:</u> <b>O(1)</b></li>
 * </ul>
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
	 * des choix disponibles pour le paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      List des clés des choix.
	 *
	 * @since       1.1
	 *
	 * @author      Jérémi Nihart
	 */
	public List<String> getChoices() {
		return new ArrayList<>(choices.keySet());
	}
	
	/** 
	 * Getter, permettant de récupérer l'objet {@link Paragraph} associé à la clé 'key'.<br>
	 * <u>CTT : O(1)</u>
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
	 * <hr>
	 * 
	 * <u>Préconditions:</u> Le paramètre 'content' doit être une valeur non null et non vide.<br>
	 * <ul>
	 *  <li>Dans le cas contaire et que le contenu du paragraphe n'a pas été initialisé à la création de
	 *      l'objet celui-ci est défini à {@link Paragraph#PARAGRAPH_CONTENT}.</li>
	 *  <li>Si il à déjà été initialisé et que les préconditions ne sont pas respéctées, il reste inchangé.</li>
	 *  <li>Si les préconditions sont respéctées, il est mis à jour avec la nouvelle valeur.</li>
	 * </ul>
	 * <hr>
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
	 * Setter, permettant de défini le contenu d'un paragraphe.
	 *
	 * @return      Un boolean indiquant si oui ou non la redéfinition de contenu
	 * 				   à fonctionner.
	 * @param 		paragraph Objet {@link Paragraph} dont il faut définir le contenu. 
	 * @param 		content Contenu du paragraphe doit être non null et non vide.
	 *
	 * @since       1.1
	 *
	 * @see			Paragraph#setContent(String)
	 * @author      Jérémi Nihart
	 */
	public static boolean setContent(Paragraph paragraph, String content) {
		return paragraph.setContent(content);
	}
	
	/**
	 * Permet d'ajouter un choix au paragraph avec une clé 'key' pointant
	 * sur un Objet 'paragraph' {@link Paragraph}.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		key Clé du choix à associer au paragraphe. Si la clé est
	 *                  null ou vide elle sera définie sur {@link Paragraph#PARAGRAPH_CHOICE}.
	 * @param  		paragraph Objet paragraphe {@link Paragraph} à assigner à la clé.
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
	 * Permet de supprimer un choix du paragraphe.<br>
	 * <u>CTT : O(1)</u>
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
	 * Permet de supprimer un choix du paragraphe si il cible un objet
	 * {@link Paragraph} particulié.
	 * 
	 * <hr>
	 * <u>Postcondition :</u> les choix qui pointes vers le paragraphe
	 *    passé en paramètre ont été supprimé.<br>
	 * <u>CTT : O(n)</u> ou 'n' est le nombre de choix du paragraphe.
	 *
	 * @param		paragraph Objet {@link Paragraph} pour le quel il
	 *                        faut supprimer les choix du paragraphe
	 *                        pointant vers lui.
	 *
	 * @since       1.1
	 *
	 * @see			Paragraph#getChoices()
	 * @see 		Paragraph#getParagraphByChoiceKey(String)
	 * @see 		Paragraph#deleteChoice(String)
	 * @author      Jérémi Nihart
	 */
	public void deleteChoiceByParagraph(Paragraph paragraph) {
		for (String key : getChoices()) {
			if (paragraph.equals(getParagraphByChoiceKey(key))) {
				deleteChoice(key);
			}
		}
	}
	
	/**
	 * Permet de mettre à jour la clé d'un choix du paragraphe.
	 * 
	 * <hr>
	 * 
	 * <u>Postconditions:</u> La 'newKey' doit être non null, non vide
	 *    et ne dois pas exister dans la Map tandis que oldKey doit exister.
	 * <hr>
	 *
	 * @return 		True : La clé à été mise à jour<br>
	 * 					   False : La clé n'a pas été mise à jour.
	 * @param		oldKey Valeur actuel de la clé.
	 * @param       newKey Nouvelle valeur de la clé.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#addChoice(String, Paragraph)
	 * @see 		Paragraph#getParagraphByChoiceKey(String)
	 * @see 		Paragraph#deleteChoice(String)
	 * @author      Jérémi Nihart
	 */
	public boolean updateChoiceKey(String oldKey, String newKey) {
		if (newKey == null || newKey.isBlank()) {
			return false;
		}
		if (choices.containsKey(oldKey) && !choices.containsKey(newKey)) {
			addChoice(newKey, getParagraphByChoiceKey(oldKey));
			deleteChoice(oldKey);
			return true;
		}
		return false;
	}
	
	/**
	 * Permet de mettre à jour le paragraph vers lequel pointe un choix.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		key Clé du choix dont il faut mettre à jour le paragraphe.
	 * @param  		paragraph Objet paragraphe {@link Paragraph} a assigné à la clé.
	 *
	 * @since       1.1
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
}
