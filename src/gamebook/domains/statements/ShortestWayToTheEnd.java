/**
 * File name    : ShortestWayToTheEnd.java
 *
 * Description  : Cette class est une impl�mentation de controller.
 *
 * Version      : 1.0
 * Since        : 1.2
 * Date         : 13/05/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
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
 * noeud (le plus cours), d�marrant du paragraphe num�ro un et
 * s'arretant au premier noeud terminal trouv�.
 * 
 * <hr>
 * 
 * <p>Choix de collection du traitement disponible dans {@link ShortestWayToTheEnd#recursivityWayMaker}</p>
 * 
 * <hr>
 * 
 * <b>Collection stockant les diff�rents noeuds parcourus jusqu'� ce qu'un noeud terminal soit trouv�:</b>
 * 
 * <h2>L'interface utilis�e : Map</h2>
 * <p>J'utilise une Map comme interface car j'ai besoin d'avoir un dictionaire cl� valeur
 * ou ma cl� serait un noeud (paragraph) et la valeur le noeud aillant pr�c�d� ma cl�
 * (un autre paragraph qui comportait mon noeud cl� dans ses choix).</p>
 * 
 * <h2>L'impl�mentation utilis�e : HashMap</h2>
 * <p>J'ai impl�ment� une HashMap car dans mon cas chaque cl� doit �tre unique.
 * De plus avec cette impl�mentation les op�rations on une petite CTT
 * (g�n�ralement <b>O(1)</b>). Je n'ai pas non plus besoin d'avoir un ordre de tri
 * particuli� dans ma map.</p>
 * 
 * Pincipales op�rations :
 * <ul>
 * 	<li>put() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>get() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
 *  <li>replace() :     <u>CTT:</u> <b>O(1)</b></li>
 * </ul>
 * 
 * <hr>
 * 
 * <b>Collection stockant le r�sultat final format�:</b>
 * 
 * <h2>L'interface utilis�e : List</h2>
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 *    liste de String ordonn�e par index dans l'ordre croissant d'insertion.</p>
 * 
 * <h2>L'impl�mentation utilis�e : ArrayList</h2>
 * <p>L'ArrayList est l'impl�mentation la plus couramment utilis� lorsque l'on a
 *    besoin de stocker des �l�ments pour ensuite les parcourir tout en gardant
 *    leur ordre d'insertion et le tout de mani�re �fficase (Bonne CTT g�nb�rale).</p>
 *    
 * Pincipales op�rations :
 * <ul>
 * 	<li>add() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>size() :        <u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
 * </ul>
 *
 * @version     1.0
 * 
 * @see			GameBookStatementReader
 * @author      J�r�mi Nihart
 */
public class ShortestWayToTheEnd extends GameBookStatementReader {
	// Constantee priv�es
	private static final String TITLE = "Chemin terminale le plus rapide";
	private static final String DESCRIPTION = "Le livre '%s' traverse %d � pour atteindre le %d.";
	private static final String RESULT_ANSWER = "Le �%d : %s";
	private static final String NO_RESULT_ANSWER = "Aucun resultat";
	
	// Variables Objet
	private final Map<Paragraph, Paragraph> history = new HashMap<>();
	private final List<String> result = new ArrayList<>();

	private GameBook book;
	private Paragraph terminal;
	
	/** 
	 * �tablit un ordre de lecture de mani�re r�cursif en commencant
	 * par le noeud terminal pour aller jusqu'au noeud de d�part.
	 * 
	 * <hr>
	 * 
	 * <b>Collection stockant les paragraphes d�j� visit�s ({@link GameBookStatementReader}):</b>
	 * 
	 * <h2>L'interface utilis�e : Set</h2>
	 * <p>J'utilise un Set comme interface car j'ai besoin de stocker un ensemble de paragraphes
	 *    dans lequel chaque paragraphe est unique.</p>
	 * 
	 * <h2>L'impl�mentation utilis�e : HashSet</h2>
	 * <p>J'utilise un HashSet comme impl�mentation car il permet d'offrir des performances optimales
	 *    pour les principales op�rations dont j'ai besoin avec une CTT de <b>O(1)</b>. Je n'ai
	 *    nullement besoin de fonctionnalit�s particuli�res (comme par exemple trier mon set et
	 *    ou lier les �l�ments entre eux...) donc pour l'impl�mentation un simple HashSet est suffisant.</p>
	 * 
	 * Pincipales op�rations :
	 * <ul>
	 * 	<li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
	 *  <li>add() :         <u>CTT:</u> <b>O(1)</b></li>
	 *  <li>contains() :    <u>CTT:</u> <b>O(1)</b></li>
	 * </ul>
	 * 
	 * <hr>
	 * 
	 * <b>Collection stockant les paragraphes � visiter ({@link GameBookStatementReader}):</b>
	 * 
	 * <h2>L'interface utilis�e : Deque</h2>
	 * <p>J'utilise une Deque comme interface car j'ai besoin de cr�er une file d'attente pour
	 *    le parcours des sous noeuds de mes noeuds.</p>
	 * 
	 * <h2>L'impl�mentation utilis�e : ArrayDeque</h2>
	 * <p>J'utilise une ArrayDeque car elle me permet de r�cup�rer son premi�re �l�ment et de
	 *    le supprimer en une fois de plus j'ai la possibilit� d'ajouter des �l�ment � sa fin.
	 *    Toutes ces op�ration sont r�alisable avec un petite CTT qui est g�n�ralement de <b>
	 *    O(1)</b></p>
	 * 
	 * Pincipales op�rations :
	 * <ul>
	 * 	<li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
	 *  <li>add() :         <u>CTT:</u> <b>O(1)</b></li>
	 *  <li>addLast() :     <u>CTT:</u> <b>O(1)</b></li>
	 *  <li>removeFirst() : <u>CTT:</u> <b>O(1)</b></li>
	 * </ul>
	 * 
	 * <hr>
	 * 
	 * <b>Collection stockant le chemin parcourus avant la cr�ation du r�sultat final:</b>
	 * 
	 * <h2>L'interface utilis�e : Deque</h2>
	 * <p>J'utilise une Deque comme interface car j'ai besoin de cr�er une queue qui sera
	 *    l'odre des noeuds � prendre pour arriver au noeud terminal le plus proche, de plus
	 *    j'ai besoin d'ajouter des �l�ments devant tous les autres car le chemin ce craie
	 *    en partant du noeud terminal en utilisant la map de mapping {@link ShortestWayToTheEnd#history}.</p>
	 * 
	 * <h2>L'impl�mentation utilis�e : ArrayDeque</h2>
	 * <p>J'impl�mente une ArrayDeque, car j'ai besoin d'un tableau dynamique sous forme de liste me permettant
	 *    d'ajouter des �l�ment en t�te de queue sans pour autant devoir d�caler tout les autres �l�ments. Je
	 *    n'utilise pas de linkedlist car b�n�ficier d'un jeu de nodes entre les �l�ments de ma file d'attente
	 *    m'est inutile.</p>
	 * 
	 * Pincipales op�rations :
	 * <ul>
	 * 	<li>addFirst() :    <u>CTT:</u> <b>O(1)</b></li>
	 * </ul>
	 * 
	 * <hr>
	 * 
	 * <h2>�valuation de la CTT</h2>
	 * <p>La CTT de {@link ShortestWayToTheEnd#recursivityWayMaker} est de <b>O(n)</b> ou 'n' est le nombre de
	 *    fois qu'elle s'appel. Ce nombre d�pend du nombre de noeud dans le map {@link ShortestWayToTheEnd#history}
	 *    que le noeud 'paragraph' pass� en argument traverse avant de tomber sur le noeud de d�part.
	 *    Dans le cas ou cette m�thode s'appelle elle-m�me moins de 2 fois ca complexit� est de <b>O(1)</b>.</p>
	 * 
	 * <hr>
	 * 
	 * @return 		Liste d'objet {@link Paragraph} du noeud final
	 * 				      au noeud de d�part.
	 * @param		way Liste vide ou contenant un morceau du chemin.
	 * @param 		paragraph Objet {@link Paragraph} d'un noeud terminal
	 * 					      ou d'un maillon pour lequel il faut reconstruire
	 *                        son chemin jusqu'au noeud de d�part.
	 *
	 * @since       1.0
	 * 
	 * @see			ShortestWayToTheEnd#recursivityWayMaker(List, Paragraph)
	 * @author      J�r�mi Nihart
	 */
	private Deque<Paragraph> recursivityWayMaker(Deque<Paragraph> way, Paragraph paragraph) {
		if (paragraph == null) {
			return way;
		}
		way.addFirst(paragraph);
		return recursivityWayMaker(way, history.get(paragraph));
	}
	
	/** 
	 * Permet de formater un r�sultat de la classe.
	 * 
	 * @return 		String format� d'une r�ponse.
	 * @param		p Objet {@link Paragraph} � utiliser pour le
	 * 				  formatage du message.
	 *
	 * @since       1.0
	 * 
	 * @see			ShortestWayToTheEnd#RESULT_ANSWER
	 * @author      J�r�mi Nihart
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
