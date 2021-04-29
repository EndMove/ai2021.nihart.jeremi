/**
 * File name    : Session.java
 *
 * Description  : Classe permetant de g�rer l'historique des pages du livre
 * 				  qui ont �t� parcourue, de reset la lecture...
 *
 * Version      : 1.0
 * Since        : 1.0
 * Date         : 21/04/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
 */
package gamebook.domains;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Session
 *
 * Permet d'attribuer un livre � une session de lecture.
 * <hr>
 * <h2>L'interface utilis�e : Deque</h2>
 * <p>J'utilise une Deque comme interface car elle permet de construire une liste
 * lin�aire (une queue), et le parcours des pages d'un livre en est tr�s similaire.</p>
 * 
 * <h2>L'impl�mentation utilis�e : LinkedList</h2>
 * <p>J'utilise une LinkedList comme impl�mentation car la LinkedList connait sont
 * �l�ment pr�c�dent et suivant (si disponible). Il m'ai utile de r�cup�rer le
 * premi�re �l�ment pour d�finir le paragraphe en cours de lecture ainsi que
 * pour la fonctionnalit�e de retour en arri�re.</p>
 * 
 * Pincipales op�rations :
 * <ul>
 * 	<li>size() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>removeLast() : 	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>add() : 		<u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() : 		<u>CTT:</u> <b>O(n)</b></li>
 *  <li>getLast() : 	<u>CTT:</u> <b>O(1)</b></li>
 * </ul>
 * 
 * <h2>Compl�mentaire :</h2>
 * <p>La LinkedList est constitu�e d'objets {@link Paragraph}.
 * On a principalement besoin de travailler avec son dernier �l�ment et celui
 * le pr�c�dent pour aller en avant et en arri�re dans la lecture. Ainsi que le
 * premier pour la fonction de r�initialisation de l'objet courant ({@link Session}).
 * Certains paragraphes peuvent apparaitre plusieurs fois dans la LinkedList.</p>
 *
 * <p>La {@link Session} doit avoir perp�tuellement un paragraphe dans son historique,
 * le paragraphe un et ne doit pas permettre qu'aucun paragraphe ne soit d�fini en tant
 * que paragraphe courant.</p>
 * <hr>
 *
 * @version     1.0
 *
 * @see         GameBook
 * @see			Paragraph
 * @author      J�r�mi Nihart
 */
public class Session {
	private final GameBook book;
	private Paragraph currentParagraph;  // paragraphe courant
	private final Deque<Paragraph> history = new LinkedList<>();
	
	/** 
	 * Constructeur
	 *
	 * @param		book Objet du livre � lire, parcourire,
	 * 					 dont il faut versionifier la lecture.
	 *
	 * @since       1.0
	 *
	 * @see 		GameBook
	 * @author      J�r�mi Nihart
	 */
	public Session(GameBook book) {
		this.book = (book != null) ? book : new GameBook(null, null);
		reset();
	}
	
	/** 
	 * Getter, permettant de r�cup�rer l'objet livre de la session.
	 *
	 * @return      Objet livre {@link GameBook}.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook
	 * @author      J�r�mi Nihart
	 */
	public GameBook getBook() {
		return book;
	}
	
	/** 
	 * Getter, permettant de r�cup�rer le paragraphe en cours de lecture.
	 *
	 * @return      Objet paragraphe {@link Paragraph}.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph
	 * @author      J�r�mi Nihart
	 */
	public Paragraph getCurrentParagraph() {
		return currentParagraph;
	}
	
	/** 
	 * Getter, permettant de r�cup�rer le head (titre) du paragraphe courrant.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 * @return      Le head (titre) du paragraphe courrant.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#getHead()
	 * @author      J�r�mi Nihart
	 */
	public String getCurrentHead() {
		return currentParagraph.getHead();
		
	}
	
	/** 
	 * Getter, permettant de r�cup�rer le contenu du paragraphe courrant.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 *
	 * @return      Le contenu du paragraphe courrant.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#getContent()
	 * @author      J�r�mi Nihart
	 */
	public String getCurrentContent() {
		return currentParagraph.getContent();
	}
	
	/** 
	 * Getter, permettant de r�cup�rer un boolean indiquant si
	 * le paragraphe courrant � des choix disponibles.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 *
	 * @return      Si oui ou non des choix sont disponible pour
	 * 				le paragraphe courrant.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#hasChoice()
	 * @author      J�r�mi Nihart
	 */
	public boolean isCurrentHasChoice() {
		return currentParagraph.hasChoice();
	}
	
	/** 
	 * Getter, permettant de r�cup�rer les choix disponibles pour le
	 * paragraphe courrant sous forme de collection.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 *
	 * @return      Collection des choix disponibles.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#getChoices()
	 * @author      J�r�mi Nihart
	 */
	public Collection<String> getCurrentChoices() {
		return currentParagraph.getChoices();
	}
	
	/** 
	 * Retourner en arri�re dans l'historique de lecture de la Session.<br>
	 * <u>CTT : O(1)</u>
	 * <hr>
	 * Postconditions :
	 * <p>Pour qu'un retour en arri�re soit valid� et ex�cut� par
	 * cette fonction il faut que le nombre de paragraphe dans l'historique soit
	 * plus grand qu'un. En effet la session doit perp�tuellement
	 * avoir le paragraphe de d�part dans son historique.</p>
	 * Questions :
	 * <p>Si l'on retire le dernier paragraphe lu <code>1X</code>, le paragraphe
	 * pr�c�dent celui retir� devient le nouveau paragraphe courant. Si aucun
	 * paragraphe ne pr�c�de celui � retirer le retrait est annul�.</p>
	 * <p>Si l'on retire le dernier paragraphe lu <code>nX</code>, le nouveau paragraphe
	 * courant devient le num�ro (<code>nombre de paragraphes - n</code>) dans l'historique.
	 * Si <code>n</code> est �gale ou plus grand que <code>nombre paragraphes</code>
	 * le retrait est annul�.</p>
	 * <hr>
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public void goBack() {
		if (history.size() > 1) {
			history.removeLast();
			updateCurrent();
		}
	}
	
	/** 
	 * Ce rendre � un paragraphe particuli�.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		paragraph Objet paragraph auquel continuer la lecture.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph
	 * @author      J�r�mi Nihart
	 */
	public void goTo(Paragraph paragraph) {
		history.add(paragraph);
		updateCurrent();
	}
	
	/** 
	 * Ce rendre � un paragraphe particuli� depuis 
	 * une cl� 'key' d'un choix du paragraph courant.
	 *
	 * @param		key Cl� du choix � utiliser pour ce rendre au paragraphe suivant.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph#getParagraphByChoiceKey(String)
	 * @see 		Session#goTo(Paragraph)
	 * @author      J�r�mi Nihart
	 */
	public void goToParagraphByChoiceKey(String key) {
		goTo(currentParagraph.getParagraphByChoiceKey(key));
	}
	
	/** 
	 * R�initialiser l'objet courant session ainsi que la lecture.<br>
	 * <u>CTT : O(n)</u> car la fonction h�rit� <code>Collection.clear()</code> boucle sur ces �l�ments pour les nuller.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook#getFirstParagraph()
	 * @see			Session#goTo(Paragraph)
	 * @author      J�r�mi Nihart
	 */
	public void reset() {
		history.clear();
		goTo(book.getFirstParagraph());
	}
	
	/** 
	 * Mise � jour du paragraph courant.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	private void updateCurrent() {
		currentParagraph = history.getLast();
	}
}
