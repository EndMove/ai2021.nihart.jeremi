/**
 * File name    : Session.java
 *
 * Description  : Classe permetant de gérer l'historique des pages du livre
 * 				  qui ont été parcourue, de reset la lecture...
 *
 * Version      : 1.0
 * Since        : 1.0
 * Date         : 21/04/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 */
package gamebook.domains;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Session
 *
 * Permet d'attribuer un livre à une session de lecture.
 * <hr>
 * <h2>L'interface utilisée : Deque</h2>
 * <p>J'utilise une Deque comme interface car elle permet de construire une liste
 * linéaire (une queue), et le parcours des pages d'un livre en est très similaire.</p>
 * 
 * <h2>L'implémentation utilisée : LinkedList</h2>
 * <p>J'utilise une LinkedList comme implémentation car la LinkedList connait sont
 * élément précédent et suivant (si disponible). Il m'ai utile de récupérer le
 * première élément pour définir le paragraphe en cours de lecture ainsi que
 * pour la fonctionnalitée de retour en arrière.</p>
 * 
 * Pincipales opérations :
 * <ul>
 * 	<li>size() :       	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>removeLast() : 	<u>CTT:</u> <b>O(1)</b></li>
 *  <li>add() : 		<u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() : 		<u>CTT:</u> <b>O(n)</b></li>
 *  <li>getLast() : 	<u>CTT:</u> <b>O(1)</b></li>
 * </ul>
 * 
 * <h2>Complémentaire :</h2>
 * <p>La LinkedList est constituée d'objets {@link Paragraph}.
 * On a principalement besoin de travailler avec son dernier élément et celui
 * le précédent pour aller en avant et en arrière dans la lecture. Ainsi que le
 * premier pour la fonction de réinitialisation de l'objet courant ({@link Session}).
 * Certains paragraphes peuvent apparaitre plusieurs fois dans la LinkedList.</p>
 *
 * <p>La {@link Session} doit avoir perpétuellement un paragraphe dans son historique,
 * le paragraphe un et ne doit pas permettre qu'aucun paragraphe ne soit défini en tant
 * que paragraphe courant.</p>
 * <hr>
 *
 * @version     1.0
 *
 * @see         GameBook
 * @see			Paragraph
 * @author      Jérémi Nihart
 */
public class Session {
	private final GameBook book;
	private Paragraph currentParagraph;  // paragraphe courant
	private final Deque<Paragraph> history = new LinkedList<>();
	
	/** 
	 * Constructeur
	 *
	 * @param		book Objet du livre à lire, parcourire,
	 * 					 dont il faut versionifier la lecture.
	 *
	 * @since       1.0
	 *
	 * @see 		GameBook
	 * @author      Jérémi Nihart
	 */
	public Session(GameBook book) {
		this.book = (book != null) ? book : new GameBook(null, null);
		reset();
	}
	
	/** 
	 * Getter, permettant de récupérer l'objet livre de la session.
	 *
	 * @return      Objet livre {@link GameBook}.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook
	 * @author      Jérémi Nihart
	 */
	public GameBook getBook() {
		return book;
	}
	
	/** 
	 * Getter, permettant de récupérer le paragraphe en cours de lecture.
	 *
	 * @return      Objet paragraphe {@link Paragraph}.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph
	 * @author      Jérémi Nihart
	 */
	public Paragraph getCurrentParagraph() {
		return currentParagraph;
	}
	
	/** 
	 * Getter, permettant de récupérer le head (titre) du paragraphe courrant.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 * @return      Le head (titre) du paragraphe courrant.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#getHead()
	 * @author      Jérémi Nihart
	 */
	public String getCurrentHead() {
		return currentParagraph.getHead();
		
	}
	
	/** 
	 * Getter, permettant de récupérer le contenu du paragraphe courrant.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 *
	 * @return      Le contenu du paragraphe courrant.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#getContent()
	 * @author      Jérémi Nihart
	 */
	public String getCurrentContent() {
		return currentParagraph.getContent();
	}
	
	/** 
	 * Getter, permettant de récupérer un boolean indiquant si
	 * le paragraphe courrant à des choix disponibles.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 *
	 * @return      Si oui ou non des choix sont disponible pour
	 * 				le paragraphe courrant.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#hasChoice()
	 * @author      Jérémi Nihart
	 */
	public boolean isCurrentHasChoice() {
		return currentParagraph.hasChoice();
	}
	
	/** 
	 * Getter, permettant de récupérer les choix disponibles pour le
	 * paragraphe courrant sous forme de collection.<br>
	 * (fait appelle au paragraphe en cours de lecture)
	 *
	 *
	 * @return      Collection des choix disponibles.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph#getChoices()
	 * @author      Jérémi Nihart
	 */
	public Collection<String> getCurrentChoices() {
		return currentParagraph.getChoices();
	}
	
	/** 
	 * Retourner en arrière dans l'historique de lecture de la Session.<br>
	 * <u>CTT : O(1)</u>
	 * <hr>
	 * Postconditions :
	 * <p>Pour qu'un retour en arrière soit validé et exécuté par
	 * cette fonction il faut que le nombre de paragraphe dans l'historique soit
	 * plus grand qu'un. En effet la session doit perpétuellement
	 * avoir le paragraphe de départ dans son historique.</p>
	 * Questions :
	 * <p>Si l'on retire le dernier paragraphe lu <code>1X</code>, le paragraphe
	 * précédent celui retiré devient le nouveau paragraphe courant. Si aucun
	 * paragraphe ne précède celui à retirer le retrait est annulé.</p>
	 * <p>Si l'on retire le dernier paragraphe lu <code>nX</code>, le nouveau paragraphe
	 * courant devient le numéro (<code>nombre de paragraphes - n</code>) dans l'historique.
	 * Si <code>n</code> est égale ou plus grand que <code>nombre paragraphes</code>
	 * le retrait est annulé.</p>
	 * <hr>
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public void goBack() {
		if (history.size() > 1) {
			history.removeLast();
			updateCurrent();
		}
	}
	
	/** 
	 * Ce rendre à un paragraphe particulié.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @param		paragraph Objet paragraph auquel continuer la lecture.
	 *
	 * @since       1.0
	 *
	 * @see			Paragraph
	 * @author      Jérémi Nihart
	 */
	public void goTo(Paragraph paragraph) {
		history.add(paragraph);
		updateCurrent();
	}
	
	/** 
	 * Ce rendre à un paragraphe particulié depuis 
	 * une clé 'key' d'un choix du paragraph courant.
	 *
	 * @param		key Clé du choix à utiliser pour ce rendre au paragraphe suivant.
	 *
	 * @since       1.0
	 *
	 * @see 		Paragraph#getParagraphByChoiceKey(String)
	 * @see 		Session#goTo(Paragraph)
	 * @author      Jérémi Nihart
	 */
	public void goToParagraphByChoiceKey(String key) {
		goTo(currentParagraph.getParagraphByChoiceKey(key));
	}
	
	/** 
	 * Réinitialiser l'objet courant session ainsi que la lecture.<br>
	 * <u>CTT : O(n)</u> car la fonction hérité <code>Collection.clear()</code> boucle sur ces éléments pour les nuller.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook#getFirstParagraph()
	 * @see			Session#goTo(Paragraph)
	 * @author      Jérémi Nihart
	 */
	public void reset() {
		history.clear();
		goTo(book.getFirstParagraph());
	}
	
	/** 
	 * Mise à jour du paragraph courant.<br>
	 * <u>CTT : O(1)</u>
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	private void updateCurrent() {
		currentParagraph = history.getLast();
	}
}
