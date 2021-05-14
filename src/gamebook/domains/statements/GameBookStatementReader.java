/**
 * File name    : GameBookStatementReader.java
 *
 * Description  : Classe gérant le livre.
 *
 * Version      : 1.0
 * Since        : 1.2
 * Date         : 14/05/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
 */
package gamebook.domains.statements;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;
import gamebook.domains.Paragraph;

/**
 * GameBookStatementReader
 *
 * Classe permettant de lire le livre en partant du premier paragraphe.
 * 
 * <hr>
 * 
 * <p>Complexité des collections disponibles dans {@link ShortestWayToTheEnd}
 *    et sa méthode de création du plus court chemin.</p>
 * 
 * <hr>
 *
 * @version     1.0
 * 
 * @see			GameBookStatement
 * @author      Jérémi Nihart
 */
public abstract class GameBookStatementReader implements GameBookStatement {
	
	private final Set<Paragraph> visited = new HashSet<>();
	private final Deque<Paragraph> queue = new ArrayDeque<>();
	
	private Paragraph pCurrent;
	
	/** 
	 * Récupère une list des objet {@link Paragraph} des choix
	 * d'un des paragraphes du livre {@link GameBook}.
	 * 
	 * @return 		Liste d'objet {@link Paragraph}.
	 * @param		book Objet {@link Paragraph} dont on veut récupérer
	 * 					 les choix.
	 *
	 * @since       1.0
	 * 
	 * @see			Paragraph#getChoices()
	 * @see			Paragraph#getParagraphByChoiceKey(String)
	 * @author      Jérémi Nihart
	 */
	private  List<Paragraph> getChoices(Paragraph paragraph) {
		List<Paragraph> choices = new ArrayList<>();
		for (String key : paragraph.getChoices()) {
			choices.add(paragraph.getParagraphByChoiceKey(key));
		}
		return choices;
	}
	
	/** 
	 * Initialise le livre dans un état cohérent.
	 * 
	 * @param		book Objet du livre {@link GameBook}.
	 *
	 * @since       1.0
	 *
	 * @see			GameBook#getParagraphByID(int)
	 * @author      Jérémi Nihart
	 */
	private void ini(GameBook book) {
		// Vider fil d'attente et ensemble.
		visited.clear();
		queue.clear();
		// Récupère premier paragraphe.
		pCurrent = book.getParagraphByID(0);
		// Initialiser la fil d'attente & l'ensemble déjà visité.
		visited.add(pCurrent);
		queue.add(pCurrent);
		// Initialise la fille.
		iniDaughter();
	}
	
	/** 
	 * Démarre l'exploration du livre.
	 * 
	 * <hr>
	 * 
	 * <p><u>Pire CTT : <b>O(n+m)</b></u> ou 'n' est le nombre de noeud dans la queue
	 *    qui doivent être traité et 'm' est le nombre de de sous noeud que possède
	 *    le noeud qui est en cours de traitement. Et dans le cas ou ces sous noeuds
	 *    n'ont pas encore été traité il sont ajouté à 'n' (la queue).<br>
	 *    Dans un cas de livre normal 'n' devrait être le nombre de paragraphe présent
	 *    dans le livre jeu et 'm' le nombre totale de choix de tous les paragraphes.</p>
	 * <p><u>Meilleur CTT : <b>O(1)</b></u> dans le cas ou il n'y a qu'un seul noeud
	 *    ne possédent aucun sous noeud.</p>
	 * 
	 * <hr>
	 * 
	 * @param		book Objet du livre {@link GameBook}.
	 *
	 * @since       1.0
	 * 
	 * @see			GameBookStatementReader#int()
	 * @see			GameBookStatementReader#onNewNodeVisited(Paragraph, Paragraph)
	 * @see			GameBookStatementReader#onNodeVisited(Paragraph)
	 * @author      Jérémi Nihart
	 */
	public void parseBook(GameBook book) {
		ini(book);
		do {
			pCurrent = queue.removeFirst();
			for (Paragraph pChoice : getChoices(pCurrent)) {
				if (!visited.contains(pChoice)) {
					visited.add(pChoice);
					queue.addLast(pChoice);
					onNewNodeVisited(pCurrent, pChoice);
				}
				onNodeVisited(pChoice);
			}
		} while (!queue.isEmpty());
	}
	
	/** 
	 * Méthode d'initialisation, est appelé lorsque la classe mère
	 * s'initialise en vu d'effectuer une exploration du livre.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	protected abstract void iniDaughter();
	
	/** 
	 * Méthode d'évènement, est appelé lorsqu'un nouveau noeud du livre
	 * est visité et que celui-ci n'avait pas encore été visité.
	 * 
	 * @param 		previous Objet {@link Paragraph} : noeud du livre précédent
	 * 						 celle venant d'être visité.
	 * @param 		element Objet {@link Paragraph} : noeud du livre qui à
	 * 						été visité.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	protected abstract void onNewNodeVisited(Paragraph previous, Paragraph element);
	
	/** 
	 * Méthode d'évènement, est appelé lorsqu'un noeud du livre est visité.
	 * 
	 * @param		element Objet {@link Paragraph} : noeud du livre qui à
	 * 						été visité.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	protected abstract void onNodeVisited(Paragraph element);

}
