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

import java.util.ArrayList;
import java.util.Collection;
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
 * Le parcours du livre ce fait dans le {@link GameBookStatementReader}, les
 * informations sur les collections permettant la lecture sont donc dans cette
 * classe.
 * 
 * <hr>
 * 
 * <b>Collection stockant le chemin le plus court et permettant la cr�ation
 * d'un r�sultat:</b>
 * 
 * <h2>L'interface utilis�e : Map</h2>
 * <p>J'utilise une Map comme interface car j'ai besoin d'avoir un dictionaire cl� valeur
 * ou ma cl� serait un noeud (paragraph) et la valeur le noeud aillant pr�c�d� ma cl�
 * (un autre paragraph qui comportait mon noeud cl� dans ses choix).</p>
 * 
 * <h2>L'impl�mentation utilis�e : HashMap</h2>
 * <p>J'ai impl�ment� une HashMap car dans mon cas chaque cl� doit est unique, il me faut
 * le chemin le plus rapide. De plus avec cette impl�mentation les op�rations on une petite
 * CTT (g�n�ralement <b>O(1)</b>). Je n'ai pas non plus besoin d'avoir un ordre de tri
 * particuli� dans ma map.</p>
 * 
 * Pincipales op�rations :
 * <ul>
 * 	<li>add() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>get() :         <u>CTT:</u> <b>O(1)</b></li>
 *  <li>clear() :       <u>CTT:</u> <b>O(n)</b></li>
 *  <li>replace() :     <u>CTT:</u> <b>O(1)</b></li>
 * </ul>
 * 
 * <hr>
 * 
 * <b>Collection stockant le r�sultat final format�:</b>
 * 
 * <h2>L'interface utilis�e : List</h2> TODO
 * <p>J'utilise une List comme interface car elle permet de construire une simple
 * liste de {@link Paragraph} ordonn�e en fonction de leurs index (dans un
 * ordre croisant d'insertion) et d'impl�menter par la suite une ArrayList.</p>
 * 
 * <h2>L'impl�mentation utilis�e : ArrayList</h2> TODO
 * <p>J'ai impl�ment� une ArrayList comme moyen de stockage de mes paragaphes
 * pour y avoir acc�s directement depuis leurs index...</p>
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
	private List<Paragraph> recursivityWayMaker(List<Paragraph> way, Paragraph paragraph) {
		if (paragraph == null) {
			return way;
		}
		way.add(paragraph);
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
	public void iniDaughter() {
		history.clear();
		result.clear();
		terminal = null;
		for (int i = 0; i < book.getSize(); i++) {
			history.put(book.getParagraphByID(i), null);
		}
	}
	
	@Override
	public void onNewNodeVisited(Paragraph previous, Paragraph element) {
		if (terminal == null) {
			history.replace(element, previous);
			if (!element.hasChoice()) {
				terminal = element;
			}
		}
	}
	
	@Override
	public void onNodeVisited(Paragraph element) {
		return;
	}

	@Override
	public void parse(GameBook book) {
		this.book = (book == null) ? new GameBook(null, null) : book;
		super.parseBook(this.book);
		
		List<Paragraph> way = new ArrayList<>();
		
		if (terminal == null) {
			result.add(NO_RESULT_ANSWER);
		} else {
			recursivityWayMaker(way, terminal);
			for (int i = way.size()-1; i >= 0; i--) {
				result.add(formatResult(way.get(i)));
			}
		}
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getDecription() {
		if (terminal == null) {
			return String.format(DESCRIPTION, book.getTitle(), 0, 0);
		}
		return String.format(DESCRIPTION, book.getTitle(), result.size(), book.getParagraphIdByObject(terminal)+1);
	}

	@Override
	public Collection<String> getResults() {
		return result;
	}

}
