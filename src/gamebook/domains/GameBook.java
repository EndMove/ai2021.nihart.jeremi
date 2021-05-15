/**
 * File name    : GameBook.java
 *
 * Description  : Classe g�rant le livre.
 *
 * Version      : 1.2
 * Since        : 1.0
 * Date         : 11/05/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
 */
package gamebook.domains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * GameBook
 *
 * Permet d'attribuer un titre au livre ainsi que des paragraphes.
 * 
 * <hr>
 * <b>(Mis � jour depuis v1.0)</b>
 * 
 * <h2>L'interface utilis�e : List</h2>
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 * liste de {@link Paragraph} ordonn�e en fonction de leurs index (dans un
 * ordre croisant d'insertion) et d'impl�menter par la suite une ArrayList.</p>
 * 
 * <h2>L'impl�mentation utilis�e : ArrayList</h2>
 * <p>J'ai impl�ment� une ArrayList comme moyen de stockage de mes paragaphes
 * pour y avoir acc�s directement depuis leurs index...</p>
 * 
 * Pincipales op�rations (cas g�n�reux) :
 * <ul>
 * 	<li>indexOf() :     <u>CTT:</u> <b>O(n)</b>*</li>
 * 	<li>get() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>add() :       	<u>CTT:</u> <b>O(1)</b></li>
 * 	<li>addAll() :      <u>CTT:</u> <b>O(n+m)</b>**</li>
 *  <li>size() :        <u>CTT:</u> <b>O(1)</b></li>
 *  <li>remove() :      <u>CTT:</u> <b>O(n)</b>***</li>
 * </ul>
 * 
 * <h2>Compl�mentaire :</h2>
 * <p><b><u>Invariant:</u></b> Le livre doit toujours poss�der un titre non null et un pragraphe
 * initialis� qui sera son num�ro un.</p>
 * 
 * <hr>
 * 
 * <u>L�gende :</u>
 * <p>*: Dans le meilleur des cas (si un seul paragraphe est pr�sent dans la liste) la CTT de
 * indexOf est de <b>O(1)</b>. Dans un cas plus g�n�rale indexOf boucle ses �l�ments (les �l�ments de
 * sa liste) pour v�rifier qu'ils correspondent, donc la CTT est de <b>O(n)</b> ou 'n' est le nombre
 * de paragraphe (plus d'informations dans la m�thode l'utilisant:
 * {@link GameBook#getParagraphIdByObject(Paragraph)}).</p>
 * <p>**: En faisant des recherche sur le fonctionnement de cette fonction
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
 * <p>***: Dans la pire des cas la CTT de remove est de <b>O(n)</b> ou 'n' est le nombre d'�l�ment
 * � d�caler pr�c�dent celui � supprimer. Tandit que dans le meilleur des cas la CTT est de <b>O(1)</b>,
 * si l'�lement � supprimer est le dernier de la liste.</p>
 * <hr>
 *
 * @version     1.2
 *
 * @see         Paragraph
 * @author      J�r�mi Nihart
 */
public class GameBook {
	// Constante publique
	public static final String BOOK_TITLE = "Nouveau livre";

	// Variable Objet
	private String title;
	private final List<Paragraph> paragraphs = new ArrayList<>();
	
	/** 
	 * Constructeur
	 *
	 * @param		title Titre du livre.
	 * @param		paragraphs Collection contenant les paragraphes de base
	 * 						   du livre.
	 *
	 * @since       1.2
	 *
	 * @see 		Paragraph
	 * @author      J�r�mi Nihart
	 */
	public GameBook(String title, Collection<Paragraph> paragraphs) {
		setTitle(title);
		if (paragraphs == null || paragraphs.isEmpty()) {
			addParagraph(new Paragraph(null));
		} else {
			for (Paragraph paragraph : paragraphs) {
				addParagraph(paragraph);
			}
		}
	}
	
	/** 
	 * Getter, permettan de r�cup�rer le titre du livre.
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
	 * Getter, permettan de r�cup�rer la taille du livre.
	 *
	 * @return      Taille du livre.
	 *
	 * @since       1.2
	 *
	 * @author      J�r�mi Nihart
	 */
	public int getSize() {
		return paragraphs.size();
	}
	
	/**
	 * Getter, permet de r�cup�rer l'en-t�te d'un paragraphe.<br>
	 * <u>CTT : O(n)</u> (voir {@link GameBook#getParagraphIdByObject(Paragraph)}).
	 *
	 * @return      En-t�te d'un paragraphe {@link Paragraph}.
	 *                      Exemple: <i>Paragraphe 17</i>
	 * @param 		paragraph Objet {@link Paragraph} pour lequel on a besoin de
	 *                        r�cup�rer le head (titre).
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#PARAGRAPH_HEAD
	 * @see 		GameBook#getParagraphIdByObject(Paragraph)
	 * @author      J�r�mi Nihart
	 */
	public String getParagraphHead(Paragraph paragraph) {
		return String.format("%s %s", Paragraph.PARAGRAPH_HEAD, getParagraphIdByObject(paragraph)+1);
	}
	
	/**
	 * Getter, permettant de r�cup�rer une liste du contenu de tous les paragraphes.<br>
	 * <u>CTT : O(n)</u> ou 'n' est le nombre de paragraphe(s) du livre.
	 *
	 * @return      Une liste du contenu de chaque objet {@link Paragraph} du
	 *                  livre {@link GameBook}.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#getContent()
	 * @author      J�r�mi Nihart
	 */
	public List<String> getParagraphsContents() {
		List<String> heads = new ArrayList<>();
		for (Paragraph paragraph : paragraphs) {
			heads.add(paragraph.getContent());
		}
		return heads;
	}
	
	/** 
	 * Getter, permettant d'obtenir un paragraphe en fonction de sa
	 * position dans la liste de paragraphes de l'objet {@link GameBook}.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} dont le num�ro correspond.
	 * @param		id L'ID du paragraphe dans la liste de paragraphes du livre.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public Paragraph getParagraphByID(int id) {
		return paragraphs.get(id);
	}
	
	/** 
	 * Getter, permettant de r�cup�rer le dernier paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} num�ro [nombre de paragraphe-1].
	 *
	 * @since       1.1
	 *
	 * @see			GameBook#getParagraphByID(int)
	 * @author      J�r�mi Nihart
	 */
	public Paragraph getLastParagraph() {
		return getParagraphByID(getSize()-1);
	}
	
	/** 
	 * Getter, permettant de r�cup�rer l'ID d'un paragraphe par son objet.<br>
	 * <u>Meilleur CTT : O(1)</u> dans le cas ou le nombre de paragraphe est plus
	 *    petit ou �gale � un.<br>
	 * <u>Pire CTT : O(n)</u> dans le cas o� le nombre de paragraphe est plus grand
	 *    que 1, 'n' �quivaut � sa position dans la liste, plus g�n�ralement O(n/2).
	 *
	 * @return      ID du paragraphe (�quivaut � l'index).
	 * @param		paragraph Objet {@link Paragraph} dont l'on souhaite r�cup�rer l'ID.
	 *
	 * @since       1.1
	 *
	 * @see			Paragraph
	 * @author      J�r�mi Nihart
	 */
	public int getParagraphIdByObject(Paragraph paragraph) {
		return paragraphs.indexOf(paragraph);
	}
	
	/** 
	 * Setter, permettant de d�finir le titre du livre.
	 *
	 * @return 		True si la valeur du param�tre 'title' est valide et que le titre courant a �t�
	 *              	 mis � jour avec et False dans le cas o� la valeur de titre est invalide.
	 * @param		title Titre souhait�. <b>Attention le titre ne doit �tre ni null ni blanc.</b>
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public boolean setTitle(String title) {
		if (title == null || title.isBlank()) {
			if (this.title == null) {
				this.title = BOOK_TITLE;
			}
			return false;
		} else {
			this.title = title;
			return true;
		}
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
	 * Permet de supprimer un {@link Paragraph} par son ID, de la liste de
	 * paragraphes du {@link GameBook} tout en supprimant tous les choix
	 * des paragraphes le r�f�ren�ant.
	 * 
	 * <hr>
	 * 
	 * <h2>Pr�condition de la m�thode :</h2>
	 * <p>
	 *    Le nombre de {@link Paragraph} pr�sent dans la liste de paragraphe du
	 *    livre ({@link GameBook}) doit �tre plus grand que 1.
	 * </p>
	 * 
	 * <h2>Postconditions (ensures) de la m�thode :</h2>
	 * <p><u>En cas de respect de la pr�condition :</u> L'Objet {@link Paragraph} dont
	 *    l'ID correspond au param�tre 'id' aura �t� supprim� du livre ({@link GameBook})
	 *    et tous les choix des paragraphes du livre ({@link GameBook}) le r�f�ren�ant
	 *    auront �t� supprim�s. De plus les id des paragraphes suc�dent celui suprim�
	 *    sont mis � jour automatiquement par la list de mani�re � combler le vide
	 *    laiss�. La m�thode retourn 'true'.
	 * </p>
	 * <p>
	 *  <u>En cas de non respect de la pr�condition :</u> Aucune action n'est effectu� et la
	 *     m�thode retourne 'false'.
	 * </p>
	 * 
	 * <h2>Etapes de l'algorithme de la m�thode :</h2>
	 * <p>
	 *    Br�ve explication du fonctionnement de la m�thode et de ses �tapes, not� que dans le cas
	 *    ou la pr�condition n'est pas valid� l'algorithme s'arr�te � l'�tape 1 et retourne 'false'.
	 * </p>
	 * <ol>
	 *  <li>V�rification de la pr�condition ;</li>
	 *  <li>Cr�ation d'une variable 'toRemove' pointant sur le {@link Paragraph} � supprimer qui �
	 *      �t� r�cup�r� dans la liste de paragraphe du {@link GameBook} par son ID ;</li>
	 *  <li>Suppression du {@link Paragraph} de la liste des paragraphe du {@link GameBook} sur
	 *      base de son ID ;</li>
	 *  <li>Bouclage des �l�ments de la liste de {@link Paragraph} du {@link GameBook} en appellant
	 *      leur m�thode {@link Paragraph#deleteChoiceByParagraph(Paragraph)} ou le param�tre
	 *      'paragraph' est le paragraphe � supprimer 'toRemove'. (cette m�thode supprime les choix
	 *      r�f�ren�ant un paragraphe donn�) ;</li>
	 *  <li>Retourne 'true' et met fin � cette algorithme.</li>
	 * </ol>
	 * 
	 * <h2>�valuation de la CTT de la m�thode :</h2>
	 * <ul>
	 *  <li><u>CTT</u> dans le meilleur des cas : <b>O(1)</b></li>
	 *  <li><u>CTT</u> dans le pire des cas (cas courant): <b>O(n^2)</b></li>
	 * </ul>
	 * <p>
	 * 	<b>Explications :</b><br>
	 *  <u>Meilleur des cas :</u> La pr�condition n'est pas valid�e, fin de l'algorithme donc <b>O(1)</b>.<br>
	 *  <u>Pire des cas (et cas courant) :</u> Deux boucles imbriqu�es ce succ�dent la premi�re boucle les
	 *     �l�ments de la <b>List</b> de {@link Paragraph} du {@link GameBook} la deuxi�me provient de l'appel de la m�thode
	 *     {@link Paragraph#deleteChoiceByParagraph} qui boucle les cl�s de sa <b>Map</b> de choix pour supprimer
	 *     ceux r�f�ran�ants le paragraphe � supprimer. Ces deux boucles succ�ssives nous donnent donc une
	 *     complexit� de <b>O(x*c)</b> ou 'x' est le nombre de paragraphe dans le livre et 'c' le nombre de choix
	 *     du paragrahe x.
	 * </p>
	 * <hr>
	 * 
	 * @return      True : Suppression du paragraphe effectu�e.<br>
	 *                     False : Suppression du paragraphe refus�e.
	 * @param		id (IN) L'id du paragraphe ({@link Paragraph}) � supprimer. 
	 *
	 * @since       1.1
	 *
	 * @author      J�r�mi Nihart
	 */
	public boolean deleteParagraph(int id) {
		if (paragraphs.size() > 1) {
			Paragraph toRemove = paragraphs.get(id);
			paragraphs.remove(id);
			for (Paragraph p : paragraphs) {
				p.deleteChoiceByParagraph(toRemove);
			}
			return true;
		}
		return false;
	}
	
}