/**
 * File name    : TargetParagraphFrequency.java
 *
 * Description  : Cette class est une impl?mentation de controller.
 *
 * Version      : 1.0
 * Since        : 1.2
 * Date         : 13/05/2021
 *
 * Author       : J?r?mi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
 */
package gamebook.domains.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

/**
 * TargetParagraphFrequency
 * 
 * Cette classe permet de compter le nombre de fois qu'un paragraphe
 * revient dans les choix des noeuds, chaque noeud n'est explor? qu'une
 * fois et l'exploration commence au paragraphe num?ro un.
 * 
 * <hr>
 * 
 * <p>La CTT de la cr?ation du relev? de base est de <b>O(n+m)</b> pour
 *    le cas moyen et <b>O(1)</b> pour le meilleur. (voir
 *    {@link GameBookStatementReader#parseBook(GameBook)}) pour plus
 *    d'information sur les variables de complexit? 'n' et 'm'.<br>
 *    Contre cas : Dans le cas ou la CTT de <code>parseBook(GameBook)</code>
 *    est de 1 mais que le livre contien plus de 1 paragraphe cela signifie
 *    que la CTT applicable est de <b>O(n)</b> ou 'n' est le nombre de paragraphe
 *    dans le livre jeu.</p>
 * 
 * <hr>
 *
 * @version     1.0
 * 
 * @see			GameBookStatementReader
 * @author      J?r?mi Nihart
 */
public class TargetParagraphFrequency extends GameBookStatementReader {
	// Constantee priv?es
	private static final String TITLE = "Nombre d'apparition des paragraphes dans les choix";
	private static final String DESCRIPTION = "Rel?ve pour le livre '%s' le nombre de fois que chaque ? figure dans une destination.";
	private static final String RESULT_ANSWER = "Le ?%d appara?t (%dx) : %s";
	
	// Variables Objet
	private final Map<Paragraph, Integer> count = new HashMap<>();
	private final List<String> result = new ArrayList<>();
	
	private GameBook book;
	
	/** 
	 * Permet de formater un r?sultat de la classe.
	 * 
	 * @return 		String format? d'une r?ponse.
	 * @param		p Objet {@link Paragraph} ? utiliser pour le
	 *   			  formatage du message.
	 * @param 		index Num?ro du paragraphe dans le livre pour
	 *   			      le formatage du message.
	 *
	 * @since       1.0
	 * 
	 * @see			TargetParagraphFrequency#RESULT_ANSWER
	 * @author      J?r?mi Nihart
	 */
	private String formatResult(Paragraph p, int index) {
		return String.format(RESULT_ANSWER, index+1, count.get(p), p.getContent());
	}
	
	@Override
	protected void iniDaughter() {
		count.clear();
		result.clear();
		for (int i = 0; i < book.getSize(); i++) {
			count.put(book.getParagraphByID(i), 0);
		}
	}
	
	@Override
	protected void onNewNodeVisited(Paragraph previous, Paragraph element) {
		return;
	}
	
	@Override
	protected void onNodeVisited(Paragraph element) {
		count.replace(element, count.get(element)+1);
	}

	@Override
	public void parse(GameBook book) {
		this.book = (book == null) ? new GameBook(null, null) : book;
		super.parseBook(this.book);
		
		for (int index = 0; index < this.book.getSize(); index++) {
			result.add(formatResult(this.book.getParagraphByID(index), index));
		}
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getDescription() {
		return String.format(DESCRIPTION, book.getTitle());
	}

	@Override
	public List<String> getResults() {
		return result;
	}

}
