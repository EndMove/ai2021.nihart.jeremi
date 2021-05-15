/**
 * File name    : ShortestWayToTheEnd.java
 *
 * Description  : Cette class est une implémentation de controller.
 *
 * Version      : 1.0
 * Since        : 1.2
 * Date         : 13/05/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
 */
package gamebook.domains.statements;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

/**
 * ShortestWayToTheEnd
 * 
 * Cette classe permet de trouver le chemin passant par le moins de
 * noeud (le plus cours), démarrant du paragraphe numéro un et
 * s'arretant au premier noeud terminal trouvé.
 * 
 * <hr>
 * 
 * <p>Choix de collection du traitement disponible dans {@link ShortestWayToTheEnd#recursivityWayMaker}</p>
 * 
 * <hr>
 * 
 * <b>Collection stockant les différents noeuds parcourus jusqu'à ce qu'un noeud terminal soit trouvé:</b>
 * 
 * <h2>L'interface utilisée : Map</h2>
 * <p>J'utilise une Map comme interface car j'ai besoin d'avoir un dictionaire clé valeur
 * ou ma clé serait un noeud (paragraph) et la valeur le noeud aillant précédé ma clé
 * (un autre paragraph qui comportait mon noeud clé dans ses choix).</p>
 * 
 * <h2>L'implémentation utilisée : HashMap</h2>
 * <p>J'ai implémenté une HashMap car dans mon cas chaque clé doit être unique.
 * De plus avec cette implémentation les opérations on une petite CTT
 * (généralement <b>O(1)</b>). Je n'ai pas non plus besoin d'avoir un ordre de tri
 * particulié dans ma map.</p>
 * 
 * Pincipales opérations :
 * <ul>
 * 	<li>put() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>get() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
 *  <li>replace() :     <u>CTT:</u> <b>O(1)</b></li>
 * </ul>
 * 
 * <hr>
 * 
 * <b>Collection stockant le résultat final formaté:</b>
 * 
 * <h2>L'interface utilisée : List</h2>
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 *    liste de String ordonnée par index dans l'ordre croissant d'insertion.</p>
 * 
 * <h2>L'implémentation utilisée : ArrayList</h2>
 * <p>L'ArrayList est l'implémentation la plus couramment utilisé lorsque l'on a
 *    besoin de stocker des éléments pour ensuite les parcourir tout en gardant
 *    leur ordre d'insertion et le tout de manière éfficase (Bonne CTT génbérale).</p>
 *    
 * Pincipales opérations :
 * <ul>
 * 	<li>add() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>size() :        <u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
 * </ul>
 *
 * @version     1.0
 * 
 * @see			GameBookStatementReader
 * @author      Jérémi Nihart
 */
public class ShortestWayToTheEnd extends GameBookStatementReader {
	// Constantee privées
	private static final String TITLE = "Chemin terminale le plus rapide";
	private static final String DESCRIPTION = "Le livre '%s' traverse %d § pour atteindre le %d.";
	private static final String RESULT_ANSWER = "Le §%d : %s";
	private static final String NO_RESULT_ANSWER = "Aucun resultat";
	
	// Variables Objet
	private final Map<Paragraph, Paragraph> history = new HashMap<>();
	private final List<String> result = new ArrayList<>();

	private GameBook book;
	private Paragraph terminal;
	
	/** 
	 * Établit un ordre de lecture de manière récursif en commencant
	 * par le noeud terminal pour aller jusqu'au noeud de départ.
	 * 
	 * <hr>
	 * 
	 * <b>Collection stockant les paragraphes déjà visités ({@link GameBookStatementReader}):</b>
	 * 
	 * <h2>L'interface utilisée : Set</h2>
	 * <p>J'utilise un Set comme interface car j'ai besoin de stocker un ensemble de paragraphes
	 *    dans lequel chaque paragraphe est unique.</p>
	 * 
	 * <h2>L'implémentation utilisée : HashSet</h2>
	 * <p>J'utilise un HashSet comme implémentation car il permet d'offrir des performances optimales
	 *    pour les principales opérations dont j'ai besoin avec une CTT de <b>O(1)</b>. Je n'ai
	 *    nullement besoin de fonctionnalités particulières (comme par exemple trier mon set et
	 *    ou lier les éléments entre eux...) donc pour l'implémentation un simple HashSet est suffisant.</p>
	 * 
	 * Pincipales opérations :
	 * <ul>
	 * 	<li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
	 *  <li>add() :         <u>CTT:</u> <b>O(1)</b></li>
	 *  <li>contains() :    <u>CTT:</u> <b>O(1)</b></li>
	 * </ul>
	 * 
	 * <hr>
	 * 
	 * <b>Collection stockant les paragraphes à visiter ({@link GameBookStatementReader}):</b>
	 * 
	 * <h2>L'interface utilisée : Deque</h2>
	 * <p>J'utilise une Deque comme interface car j'ai besoin de créer une file d'attente pour
	 *    le parcours des sous noeuds de mes noeuds.</p>
	 * 
	 * <h2>L'implémentation utilisée : ArrayDeque</h2>
	 * <p>J'utilise une ArrayDeque car elle me permet de récupérer son première élément et de
	 *    le supprimer en une fois de plus j'ai la possibilité d'ajouter des élément à sa fin.
	 *    Toutes ces opération sont réalisable avec un petite CTT qui est généralement de <b>
	 *    O(1)</b></p>
	 * 
	 * Pincipales opérations :
	 * <ul>
	 * 	<li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
	 *  <li>add() :         <u>CTT:</u> <b>O(1)</b></li>
	 *  <li>addLast() :     <u>CTT:</u> <b>O(1)</b></li>
	 *  <li>removeFirst() : <u>CTT:</u> <b>O(1)</b></li>
	 * </ul>
	 * 
	 * <hr>
	 * 
	 * <b>Collection stockant le chemin parcourus avant la création du résultat final:</b>
	 * 
	 * <h2>L'interface utilisée : Deque</h2>
	 * <p>J'utilise une Deque comme interface car j'ai besoin de créer une queue qui sera
	 *    l'odre des noeuds à prendre pour arriver au noeud terminal le plus proche, de plus
	 *    j'ai besoin d'ajouter des éléments devant tous les autres car le chemin ce craie
	 *    en partant du noeud terminal en utilisant la map de mapping {@link ShortestWayToTheEnd#history}.</p>
	 * 
	 * <h2>L'implémentation utilisée : ArrayDeque</h2>
	 * <p>J'implémente une ArrayDeque, car j'ai besoin d'un tableau dynamique sous forme de liste me permettant
	 *    d'ajouter des élément en tête de queue sans pour autant devoir décaler tout les autres éléments. Je
	 *    n'utilise pas de linkedlist car bénéficier d'un jeu de nodes entre les éléments de ma file d'attente
	 *    m'est inutile.</p>
	 * 
	 * Pincipales opérations :
	 * <ul>
	 * 	<li>addFirst() :    <u>CTT:</u> <b>O(1)</b></li>
	 * </ul>
	 * 
	 * <hr>
	 * 
	 * <h2>Évaluation de la CTT</h2>
	 * <p>La CTT de {@link ShortestWayToTheEnd#recursivityWayMaker} est de <b>O(n)</b> ou 'n' est le nombre de
	 *    fois qu'elle s'appel. Ce nombre dépend du nombre de noeud dans le map {@link ShortestWayToTheEnd#history}
	 *    que le noeud 'paragraph' passé en argument traverse avant de tomber sur le noeud de départ.
	 *    Dans le cas ou cette méthode s'appelle elle-même moins de 2 fois ca complexité est de <b>O(1)</b>.</p>
	 * 
	 * <hr>
	 * 
	 * @return 		Liste d'objet {@link Paragraph} du noeud final
	 * 				      au noeud de départ.
	 * @param		way Liste vide ou contenant un morceau du chemin.
	 * @param 		paragraph Objet {@link Paragraph} d'un noeud terminal
	 * 					      ou d'un maillon pour lequel il faut reconstruire
	 *                        son chemin jusqu'au noeud de départ.
	 *
	 * @since       1.0
	 * 
	 * @see			ShortestWayToTheEnd#recursivityWayMaker(List, Paragraph)
	 * @author      Jérémi Nihart
	 */
	private Deque<Paragraph> recursivityWayMaker(Deque<Paragraph> way, Paragraph paragraph) {
		if (paragraph == null) {
			return way;
		}
		way.addFirst(paragraph);
		return recursivityWayMaker(way, history.get(paragraph));
	}
	
	/** 
	 * Permet de formater un résultat de la classe.
	 * 
	 * @return 		String formaté d'une réponse.
	 * @param		p Objet {@link Paragraph} à utiliser pour le
	 * 				  formatage du message.
	 *
	 * @since       1.0
	 * 
	 * @see			ShortestWayToTheEnd#RESULT_ANSWER
	 * @author      Jérémi Nihart
	 */
	private String formatResult(Paragraph p) {
		return String.format(RESULT_ANSWER, book.getParagraphIdByObject(p)+1, p.getContent());
	}
	
	@Override
	protected void iniDaughter() {
		history.clear();
		result.clear();
		terminal = null;
		for (int i = 0; i < book.getSize(); i++) {
			history.put(book.getParagraphByID(i), null);
		}
	}
	
	@Override
	protected void onNewNodeVisited(Paragraph previous, Paragraph element) {
		if (terminal == null) {
			history.replace(element, previous);
			if (!element.hasChoice()) {
				terminal = element;
			}
		}
	}
	
	@Override
	protected void onNodeVisited(Paragraph element) {
		return;
	}

	@Override
	public void parse(GameBook book) {
		this.book = (book == null) ? new GameBook(null, null) : book;
		super.parseBook(this.book);
		
		Deque<Paragraph> way = new ArrayDeque<>();
		
		if (terminal == null) {
			result.add(NO_RESULT_ANSWER);
		} else {
			recursivityWayMaker(way, terminal);
			for (Paragraph p : way) {
				result.add(formatResult(p));
			}
		}
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getDescription() {
		if (terminal == null) {
			return String.format(DESCRIPTION, book.getTitle(), 0, 0);
		}
		return String.format(DESCRIPTION, book.getTitle(), result.size(), book.getParagraphIdByObject(terminal)+1);
	}

	@Override
	public List<String> getResults() {
		return result;
	}

}
