/**
 * File name    : GameBook.java
 *
 * Description  : Classe gérant le livre.
 *
 * Version      : 1.2
 * Since        : 1.0
 * Date         : 11/05/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
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
 * <b>(Mis à jour depuis v1.0)</b>
 * 
 * <h2>L'interface utilisée : List</h2>
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 * liste de {@link Paragraph} ordonnée en fonction de leurs index (dans un
 * ordre croisant d'insertion) et d'implémenter par la suite une ArrayList.</p>
 * 
 * <h2>L'implémentation utilisée : ArrayList</h2>
 * <p>J'ai implémenté une ArrayList comme moyen de stockage de mes paragaphes
 * pour y avoir accès directement depuis leurs index...</p>
 * 
 * Pincipales opérations (cas généreux) :
 * <ul>
 * 	<li>indexOf() :     <u>CTT:</u> <b>O(n)</b>*</li>
 * 	<li>get() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>add() :       	<u>CTT:</u> <b>O(1)</b></li>
 * 	<li>addAll() :      <u>CTT:</u> <b>O(n+m)</b>**</li>
 *  <li>size() :        <u>CTT:</u> <b>O(1)</b></li>
 *  <li>remove() :      <u>CTT:</u> <b>O(n)</b>***</li>
 * </ul>
 * 
 * <h2>Complémentaire :</h2>
 * <p><b><u>Invariant:</u></b> Le livre doit toujours posséder un titre non null et un pragraphe
 * initialisé qui sera son numéro un.</p>
 * 
 * <hr>
 * 
 * <u>Légende :</u>
 * <p>*: Dans le meilleur des cas (si un seul paragraphe est présent dans la liste) la CTT de
 * indexOf est de <b>O(1)</b>. Dans un cas plus générale indexOf boucle ses éléments (les éléments de
 * sa liste) pour vérifier qu'ils correspondent, donc la CTT est de <b>O(n)</b> ou 'n' est le nombre
 * de paragraphe (plus d'informations dans la méthode l'utilisant:
 * {@link GameBook#getParagraphIdByObject(Paragraph)}).</p>
 * <p>**: En faisant des recherche sur le fonctionnement de cette fonction
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
 * <p>***: Dans la pire des cas la CTT de remove est de <b>O(n)</b> ou 'n' est le nombre d'élément
 * à décaler précédent celui à supprimer. Tandit que dans le meilleur des cas la CTT est de <b>O(1)</b>,
 * si l'élement à supprimer est le dernier de la liste.</p>
 * <hr>
 *
 * @version     1.2
 *
 * @see         Paragraph
 * @author      Jérémi Nihart
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
	 * @author      Jérémi Nihart
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
	 * Getter, permettan de récupérer le titre du livre.
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
	 * Getter, permettan de récupérer la taille du livre.
	 *
	 * @return      Taille du livre.
	 *
	 * @since       1.2
	 *
	 * @author      Jérémi Nihart
	 */
	public int getSize() {
		return paragraphs.size();
	}
	
	/**
	 * Getter, permet de récupérer l'en-tête d'un paragraphe.<br>
	 * <u>CTT : O(n)</u> (voir {@link GameBook#getParagraphIdByObject(Paragraph)}).
	 *
	 * @return      En-tête d'un paragraphe {@link Paragraph}.
	 *                      Exemple: <i>Paragraphe 17</i>
	 * @param 		paragraph Objet {@link Paragraph} pour lequel on a besoin de
	 *                        récupérer le head (titre).
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#PARAGRAPH_HEAD
	 * @see 		GameBook#getParagraphIdByObject(Paragraph)
	 * @author      Jérémi Nihart
	 */
	public String getParagraphHead(Paragraph paragraph) {
		return String.format("%s %s", Paragraph.PARAGRAPH_HEAD, getParagraphIdByObject(paragraph)+1);
	}
	
	/**
	 * Getter, permettant de récupérer une liste du contenu de tous les paragraphes.<br>
	 * <u>CTT : O(n)</u> ou 'n' est le nombre de paragraphe(s) du livre.
	 *
	 * @return      Une liste du contenu de chaque objet {@link Paragraph} du
	 *                  livre {@link GameBook}.
	 *
	 * @since       1.1
	 *
	 * @see 		Paragraph#getContent()
	 * @author      Jérémi Nihart
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
	 * @return      Objet {@link Paragraph} dont le numéro correspond.
	 * @param		id L'ID du paragraphe dans la liste de paragraphes du livre.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public Paragraph getParagraphByID(int id) {
		return paragraphs.get(id);
	}
	
	/** 
	 * Getter, permettant de récupérer le dernier paragraphe.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @return      Objet {@link Paragraph} numéro [nombre de paragraphe-1].
	 *
	 * @since       1.1
	 *
	 * @see			GameBook#getParagraphByID(int)
	 * @author      Jérémi Nihart
	 */
	public Paragraph getLastParagraph() {
		return getParagraphByID(getSize()-1);
	}
	
	/** 
	 * Getter, permettant de récupérer l'ID d'un paragraphe par son objet.<br>
	 * <u>Meilleur CTT : O(1)</u> dans le cas ou le nombre de paragraphe est plus
	 *    petit ou égale à un.<br>
	 * <u>Pire CTT : O(n)</u> dans le cas où le nombre de paragraphe est plus grand
	 *    que 1, 'n' équivaut à sa position dans la liste, plus généralement O(n/2).
	 *
	 * @return      ID du paragraphe (équivaut à l'index).
	 * @param		paragraph Objet {@link Paragraph} dont l'on souhaite récupérer l'ID.
	 *
	 * @since       1.1
	 *
	 * @see			Paragraph
	 * @author      Jérémi Nihart
	 */
	public int getParagraphIdByObject(Paragraph paragraph) {
		return paragraphs.indexOf(paragraph);
	}
	
	/** 
	 * Setter, permettant de définir le titre du livre.
	 *
	 * @return 		True si la valeur du paramètre 'title' est valide et que le titre courant a été
	 *              	 mis à jour avec et False dans le cas où la valeur de titre est invalide.
	 * @param		title Titre souhaité. <b>Attention le titre ne doit être ni null ni blanc.</b>
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
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
	 * Permet de supprimer un {@link Paragraph} par son ID, de la liste de
	 * paragraphes du {@link GameBook} tout en supprimant tous les choix
	 * des paragraphes le référençant.
	 * 
	 * <hr>
	 * 
	 * <h2>Précondition de la méthode :</h2>
	 * <p>
	 *    Le nombre de {@link Paragraph} présent dans la liste de paragraphe du
	 *    livre ({@link GameBook}) doit être plus grand que 1.
	 * </p>
	 * 
	 * <h2>Postconditions (ensures) de la méthode :</h2>
	 * <p><u>En cas de respect de la précondition :</u> L'Objet {@link Paragraph} dont
	 *    l'ID correspond au paramètre 'id' aura été supprimé du livre ({@link GameBook})
	 *    et tous les choix des paragraphes du livre ({@link GameBook}) le référençant
	 *    auront été supprimés. De plus les id des paragraphes sucédent celui suprimé
	 *    sont mis à jour automatiquement par la list de manière à combler le vide
	 *    laissé. La méthode retourn 'true'.
	 * </p>
	 * <p>
	 *  <u>En cas de non respect de la précondition :</u> Aucune action n'est effectué et la
	 *     méthode retourne 'false'.
	 * </p>
	 * 
	 * <h2>Etapes de l'algorithme de la méthode :</h2>
	 * <p>
	 *    Bréve explication du fonctionnement de la méthode et de ses étapes, noté que dans le cas
	 *    ou la précondition n'est pas validé l'algorithme s'arrête à l'étape 1 et retourne 'false'.
	 * </p>
	 * <ol>
	 *  <li>Vérification de la précondition ;</li>
	 *  <li>Création d'une variable 'toRemove' pointant sur le {@link Paragraph} à supprimer qui à
	 *      été récupéré dans la liste de paragraphe du {@link GameBook} par son ID ;</li>
	 *  <li>Suppression du {@link Paragraph} de la liste des paragraphe du {@link GameBook} sur
	 *      base de son ID ;</li>
	 *  <li>Bouclage des éléments de la liste de {@link Paragraph} du {@link GameBook} en appellant
	 *      leur méthode {@link Paragraph#deleteChoiceByParagraph(Paragraph)} ou le paramètre
	 *      'paragraph' est le paragraphe à supprimer 'toRemove'. (cette méthode supprime les choix
	 *      référençant un paragraphe donné) ;</li>
	 *  <li>Retourne 'true' et met fin à cette algorithme.</li>
	 * </ol>
	 * 
	 * <h2>Évaluation de la CTT de la méthode :</h2>
	 * <ul>
	 *  <li><u>CTT</u> dans le meilleur des cas : <b>O(1)</b></li>
	 *  <li><u>CTT</u> dans le pire des cas (cas courant): <b>O(n^2)</b></li>
	 * </ul>
	 * <p>
	 * 	<b>Explications :</b><br>
	 *  <u>Meilleur des cas :</u> La précondition n'est pas validée, fin de l'algorithme donc <b>O(1)</b>.<br>
	 *  <u>Pire des cas (et cas courant) :</u> Deux boucles imbriquées ce succèdent la première boucle les
	 *     éléments de la <b>List</b> de {@link Paragraph} du {@link GameBook} la deuxième provient de l'appel de la méthode
	 *     {@link Paragraph#deleteChoiceByParagraph} qui boucle les clés de sa <b>Map</b> de choix pour supprimer
	 *     ceux référançants le paragraphe à supprimer. Ces deux boucles succèssives nous donnent donc une
	 *     complexité de <b>O(x*c)</b> ou 'x' est le nombre de paragraphe dans le livre et 'c' le nombre de choix
	 *     du paragrahe x.
	 * </p>
	 * <hr>
	 * 
	 * @return      True : Suppression du paragraphe effectuée.<br>
	 *                     False : Suppression du paragraphe refusée.
	 * @param		id (IN) L'id du paragraphe ({@link Paragraph}) à supprimer. 
	 *
	 * @since       1.1
	 *
	 * @author      Jérémi Nihart
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