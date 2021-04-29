/**
 * File name    : GameBook.java
 *
 * Description  : Classe gérant le livre.
 *
 * Version      : 1.0
 * Since        : 1.0
 * Date         : 21/04/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 */

package gamebook.domains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GameBook
 *
 * Permet d'attribuer un titre au livre ainsi que des paragraphes.
 * <hr>
 * Info : <i>L'utilisation d'une liste n'était pas requis mais déjà implémenté en prévision des modifications futurs.</i>
 * <h2>L'interface utilisée : List</h2>
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 * liste de {@link Paragraph} ordonnée en fonction de leurs index (dans un
 * ordre croisant d'insertion) et d'implémenter par la suite une ArrayList.</p>
 * 
 * <h2>L'implémentation utilisée : ArrayList</h2>
 * <p>J'ai implémenté une ArrayList comme moyen de stockage de mes paragaphes
 * pour y avoir accès directement depuis leur index...</p>
 * 
 * Pincipales opérations :
 * <ul>
 * 	<li>get() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>add() :       	<u>CTT:</u> <b>O(1)</b></li>
 * 	<li>addAll() :      <u>CTT:</u> <b>O(n+m)</b>*</li>
 * </ul>
 * 
 * <h2>Complémentaire :</h2>
 * <p><b><u>Invariant:</u></b> Le livre doit toujours posséder un titre non null et un pragraphe
 * initialisé qui sera son numéro un.</p>
 * 
 * <hr>
 * 
 * <u>Légende :</u>
 * <p>*: En faisant des recherche sur le fonctionnement de cette fonction
 * j'ai pu découvrir qu'en Java 11 <code>addAll()</code> a été réécrite, celle-ci fonctionne
 * comme suit:<br>De base l'implémentation de collection utilisé est convertie
 * en tableau, elle itère tout c'est élément(s) donc <b>O(n)</b>
 * Ensuite dans le cas ou notre ArrayList est trop petite
 * pour contenir les éléments à ajouter ont l'agrandit donc O(m) de plus la
 * fonction utilise <code>System.arraycopy()</code> pour copier les nouveaux
 * éléments je n'ai pas pu trouver le code de cette fonction et donc par
 * déduction sa CTT est dans le meilleur des cas <b>O(1)</b> et dans le pire
 * <b>O(n)</b> (pour faire une boucle permettant de copier les éléments) la CTT
 * final reste donc inchangé.<br>
 * Dans le cas où l'on ne prend pas en compte l'agrandissement de la List, CTT: <b>O(n)</b>.</p>
 * <hr>
 *
 * @version     1.0
 *
 * @see         Paragraph
 * @author      Jérémi Nihart
 */
public class GameBook {
	private String title;
	private final List<Paragraph> paragraphs = new ArrayList<>();
	
	/** 
	 * Constructeur
	 *
	 * @param		title Titre du livre.
	 * @param		paragraphs Collection contenant les paragraphes de base
	 * 						   du livre.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph
	 * @author      Jérémi Nihart
	 */
	public GameBook(String title, Collection<Paragraph> paragraphs) {
		this.title = (title != null) ? title : "No title";
		if (paragraphs == null || paragraphs.isEmpty()) {addParagraph(new Paragraph(0, null));}
		else {addParagraphs(paragraphs);}
	}
	
	/** 
	 * Getter, permet de récupérer le titre du livre.
	 *
	 * @return      Titre du livre.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public String getTitle() {
		return title;
	}
	
	/** 
	 * Getter, permettant d'obtenir un paragraphe en fonction de son numéro.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} dont le numéro correspond.
	 * @param		id Le numéro du paragraphe à obtenir.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public Paragraph getParagraphByID(int id) {
		return paragraphs.get(id-1);
	}
	
	/** 
	 * Getter, permettant de récupérer le premier paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} numéro 1.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook#getParagraphByID(int)
	 * @author      Jérémi Nihart
	 */
	public Paragraph getFirstParagraph() {
		return getParagraphByID(1);
	}
	
	/** 
	 * Setter, permettant de définir le titre du livre.
	 *
	 * @param		title Titre souhaité.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 
	 * Permet d'ajouter un paragraphe au livre.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		paragraph Paragraphe à ajouter au livre.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public void addParagraph(Paragraph paragraph) {
		this.paragraphs.add(paragraph);
	}
	
	/** 
	 * Permet d'ajouter une Collection de paragraphes ({@link Paragraph}) au livre.<br>
	 * <u>CTT : O(n+m)</u> voir Légende classe pour plus d'informations *
	 *
	 * @param		paragraphs Collection contenant les paragraphes à ajouter.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public void addParagraphs(Collection<Paragraph> paragraphs) {
		this.paragraphs.addAll(paragraphs);
	}
	
	/**
	 * Redéfinition de la méthode toString()
	 */
	@Override
	public String toString() {
		return String.format("Book(title=%s, paragraphs_count=%d)", title, paragraphs.size());
	}
}