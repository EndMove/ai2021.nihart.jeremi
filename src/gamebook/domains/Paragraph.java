/**
 * File name    : Paragraph.java
 *
 * Description  : Classe g�rant les paragraphe et choix assosi�s.
 *
 * Version      : 1.1
 * Since        : 1.0
 * Date         : 04/05/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
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
 * Permet de cr�er un paragraphe du livre et de lui assigner
 * un contenu par defaut, ainsi que de lui attribuer des choix.
 *
 * <hr>
 * 
 * <h2>L'interface utilis�e : Map</h2>
 * <p>J'utilise une Map comme interface car j'ai besoin d'avoir un dictionaire cl� valeur
 * pour acc�der � mes choix. J'utilise le libell� du choix comme cl� et un objet Paragraph comme valeur.</p>
 * 
 * <h2>L'impl�mentation utilis�e : HashMap</h2>
 * <p>J'ai impl�ment� une HashMap car chaque cl� doit est unique, et qu'il m'ait inutile de
 * trier les �l�ments qu'elle contient. De plus ses principales op�rations ont une CTT de <b>O(1)</b></p>
 * 
 * Pincipales op�rations :
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
	 * @param		content Contenu du paragraphe, si aucun contenu n'est sp�cifi�
	 *                      la fonction d'assigniation du contenu d�finira
	 *                      {@link Paragraph#PARAGRAPH_CONTENT} comme valeur par defaut.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph#setContent(String)
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
	 * des choix disponibles pour le paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      List des cl�s des choix.
	 *
	 * @since       1.1
	 *
	 * @author      J�r�mi Nihart
	 */
	public List<String> getChoices() {
		return new ArrayList<>(choices.keySet());
	}
	
	/** 
	 * Getter, permettant de r�cup�rer l'objet {@link Paragraph} associ� � la cl� 'key'.<br>
	 * <u>CTT : O(1)</u>
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
	
	/** 
	 * Setter, permettant de d�finir le contenu du paragraphe courant.
	 * 
	 * <hr>
	 * 
	 * <u>Pr�conditions:</u> Le param�tre 'content' doit �tre une valeur non null et non vide.<br>
	 * <ul>
	 *  <li>Dans le cas contaire et que le contenu du paragraphe n'a pas �t� initialis� � la cr�ation de
	 *      l'objet celui-ci est d�fini � {@link Paragraph#PARAGRAPH_CONTENT}.</li>
	 *  <li>Si il � d�j� �t� initialis� et que les pr�conditions ne sont pas resp�ct�es, il reste inchang�.</li>
	 *  <li>Si les pr�conditions sont resp�ct�es, il est mis � jour avec la nouvelle valeur.</li>
	 * </ul>
	 * <hr>
	 *
	 * @return      True: Si le contenu sp�cifi� a �t� d�fini comme contenu courrant<br>
	 * 				      False: Si le contenu sp�cifi� a �t� refus�.
	 * @param		content La valeur que l'on souhaite d�finir comme contenu du paragraphe.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#PARAGRAPH_CONTENT
	 * @author      J�r�mi Nihart
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
	 * Setter, permettant de d�fini le contenu d'un paragraphe.
	 *
	 * @return      Un boolean indiquant si oui ou non la red�finition de contenu
	 * 				   � fonctionner.
	 * @param 		paragraph Objet {@link Paragraph} dont il faut d�finir le contenu. 
	 * @param 		content Contenu du paragraphe doit �tre non null et non vide.
	 *
	 * @since       1.1
	 *
	 * @see			Paragraph#setContent(String)
	 * @author      J�r�mi Nihart
	 */
	public static boolean setContent(Paragraph paragraph, String content) {
		return paragraph.setContent(content);
	}
	
	/**
	 * Permet d'ajouter un choix au paragraph avec une cl� 'key' pointant
	 * sur un Objet 'paragraph' {@link Paragraph}.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		key Cl� du choix � associer au paragraphe. Si la cl� est
	 *                  null ou vide elle sera d�finie sur {@link Paragraph#PARAGRAPH_CHOICE}.
	 * @param  		paragraph Objet paragraphe {@link Paragraph} � assigner � la cl�.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph#PARAGRAPH_CHOICE
	 * @author      J�r�mi Nihart
	 */
	public void addChoice(String key, Paragraph paragraph) {
		String workKey = (key == null || key.isBlank()) ? PARAGRAPH_CHOICE : key;
		choices.put(workKey, paragraph);
	}
	
	/**
	 * Permet de supprimer un choix du paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		key Cl� du choix � supprimer du paragraphe courrant.
	 *
	 * @since       1.1
	 *
	 * @author      J�r�mi Nihart
	 */
	public void deleteChoice(String key) {
		choices.remove(key);
	}
	
	/**
	 * Permet de supprimer un choix du paragraphe si il cible un objet
	 * {@link Paragraph} particuli�.
	 * 
	 * <hr>
	 * <u>Postcondition :</u> les choix qui pointes vers le paragraphe
	 *    pass� en param�tre ont �t� supprim�.<br>
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
	 * @author      J�r�mi Nihart
	 */
	public void deleteChoiceByParagraph(Paragraph paragraph) {
		for (String key : getChoices()) {
			if (paragraph.equals(getParagraphByChoiceKey(key))) {
				deleteChoice(key);
			}
		}
	}
	
	/**
	 * Permet de mettre � jour la cl� d'un choix du paragraphe.
	 * 
	 * <hr>
	 * 
	 * <u>Postconditions:</u> La 'newKey' doit �tre non null, non vide
	 *    et ne dois pas exister dans la Map tandis que oldKey doit exister.
	 * <hr>
	 *
	 * @return 		True : La cl� � �t� mise � jour<br>
	 * 					   False : La cl� n'a pas �t� mise � jour.
	 * @param		oldKey Valeur actuel de la cl�.
	 * @param       newKey Nouvelle valeur de la cl�.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#addChoice(String, Paragraph)
	 * @see 		Paragraph#getParagraphByChoiceKey(String)
	 * @see 		Paragraph#deleteChoice(String)
	 * @author      J�r�mi Nihart
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
	 * Permet de mettre � jour le paragraph vers lequel pointe un choix.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		key Cl� du choix dont il faut mettre � jour le paragraphe.
	 * @param  		paragraph Objet paragraphe {@link Paragraph} a assign� � la cl�.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph
	 * @author      J�r�mi Nihart
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
	 * @author      J�r�mi Nihart
	 */
	public boolean hasChoice() {
		return !choices.isEmpty();
	}
}
