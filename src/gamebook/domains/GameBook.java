/**
 * File name    : GameBook.java
 *
 * Description  : Classe g�rant le livre.
 *
 * Version      : 1.0
 * Since        : 1.0
 * Date         : 21/04/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
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
 * Info : <i>L'utilisation d'une liste n'�tait pas requis mais d�j� impl�ment� en pr�vision des modifications futurs.</i>
 * <h2>L'interface utilis�e : List</h2>
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 * liste de {@link Paragraph} ordonn�e en fonction de leurs index (dans un
 * ordre croisant d'insertion) et d'impl�menter par la suite une ArrayList.</p>
 * 
 * <h2>L'impl�mentation utilis�e : ArrayList</h2>
 * <p>J'ai impl�ment� une ArrayList comme moyen de stockage de mes paragaphes
 * pour y avoir acc�s directement depuis leur index...</p>
 * 
 * Pincipales op�rations :
 * <ul>
 * 	<li>get() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>add() :       	<u>CTT:</u> <b>O(1)</b></li>
 * 	<li>addAll() :      <u>CTT:</u> <b>O(n+m)</b>*</li>
 * </ul>
 * 
 * <h2>Compl�mentaire :</h2>
 * <p><b><u>Invariant:</u></b> Le livre doit toujours poss�der un titre non null et un pragraphe
 * initialis� qui sera son num�ro un.</p>
 * 
 * <hr>
 * 
 * <u>L�gende :</u>
 * <p>*: En faisant des recherche sur le fonctionnement de cette fonction
 * j'ai pu d�couvrir qu'en Java 11 <code>addAll()</code> a �t� r��crite, celle-ci fonctionne
 * comme suit:<br>De base l'impl�mentation de collection utilis� est convertie
 * en tableau, elle it�re tout c'est �l�ment(s) donc <b>O(n)</b>
 * Ensuite dans le cas ou notre ArrayList est trop petite
 * pour contenir les �l�ments � ajouter ont l'agrandit donc O(m) de plus la
 * fonction utilise <code>System.arraycopy()</code> pour copier les nouveaux
 * �l�ments je n'ai pas pu trouver le code de cette fonction et donc par
 * d�duction sa CTT est dans le meilleur des cas <b>O(1)</b> et dans le pire
 * <b>O(n)</b> (pour faire une boucle permettant de copier les �l�ments) la CTT
 * final reste donc inchang�.<br>
 * Dans le cas o� l'on ne prend pas en compte l'agrandissement de la List, CTT: <b>O(n)</b>.</p>
 * <hr>
 *
 * @version     1.0
 *
 * @see         Paragraph
 * @author      J�r�mi Nihart
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
	 * @author      J�r�mi Nihart
	 */
	public GameBook(String title, Collection<Paragraph> paragraphs) {
		this.title = (title != null) ? title : "No title";
		if (paragraphs == null || paragraphs.isEmpty()) {addParagraph(new Paragraph(0, null));}
		else {addParagraphs(paragraphs);}
	}
	
	/** 
	 * Getter, permet de r�cup�rer le titre du livre.
	 *
	 * @return      Titre du livre.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public String getTitle() {
		return title;
	}
	
	/** 
	 * Getter, permettant d'obtenir un paragraphe en fonction de son num�ro.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} dont le num�ro correspond.
	 * @param		id Le num�ro du paragraphe � obtenir.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public Paragraph getParagraphByID(int id) {
		return paragraphs.get(id-1);
	}
	
	/** 
	 * Getter, permettant de r�cup�rer le premier paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} num�ro 1.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook#getParagraphByID(int)
	 * @author      J�r�mi Nihart
	 */
	public Paragraph getFirstParagraph() {
		return getParagraphByID(1);
	}
	
	/** 
	 * Setter, permettant de d�finir le titre du livre.
	 *
	 * @param		title Titre souhait�.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 
	 * Permet d'ajouter un paragraphe au livre.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		paragraph Paragraphe � ajouter au livre.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public void addParagraph(Paragraph paragraph) {
		this.paragraphs.add(paragraph);
	}
	
	/** 
	 * Permet d'ajouter une Collection de paragraphes ({@link Paragraph}) au livre.<br>
	 * <u>CTT : O(n+m)</u> voir L�gende classe pour plus d'informations *
	 *
	 * @param		paragraphs Collection contenant les paragraphes � ajouter.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public void addParagraphs(Collection<Paragraph> paragraphs) {
		this.paragraphs.addAll(paragraphs);
	}
	
	/**
	 * Red�finition de la m�thode toString()
	 */
	@Override
	public String toString() {
		return String.format("Book(title=%s, paragraphs_count=%d)", title, paragraphs.size());
	}
}