/**
 * File name    : TargetParagraphFrequency.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

/**
 * TargetParagraphFrequency
 * 
 * Cette classe permet de compter le nombre de fois qu'un paragraphe
 * revient dans les choix des noeuds, chaque noeud n'est exploré qu'une
 * fois et l'exploration commence au paragraphe numéro un.
 *
 * @version     1.0
 * 
 * @see			GameBookStatementReader
 * @author      Jérémi Nihart
 */
public class TargetParagraphFrequency extends GameBookStatementReader {
	// Constantee privées
	private static final String TITLE = "Nombre d'apparition des paragraphes dans les choix";
	private static final String DESCRIPTION = "Relève pour le livre '%s' le nombre de fois que chaque § figure dans une destination.";
	private static final String RESULT_ANSWER = "Le §%d apparaît (%dx) : %s";
	
	// Variables Objet
	private final Map<Paragraph, Integer> count = new HashMap<>();
	private final List<String> result = new ArrayList<>();
	
	private GameBook book;
	
	/** 
	 * Permet de formater un résultat de la classe.
	 * 
	 * @return 		String formaté d'une réponse.
	 * @param		p Objet {@link Paragraph} à utiliser pour le
	 *   			  formatage du message.
	 * @param 		index Numéro du paragraphe dans le livre pour
	 *   			      le formatage du message.
	 *
	 * @since       1.0
	 * 
	 * @see			TargetParagraphFrequency#RESULT_ANSWER
	 * @author      Jérémi Nihart
	 */
	private String formatResult(Paragraph p, int index) {
		return String.format(RESULT_ANSWER, index+1, count.get(p), p.getContent());
	}
	
	@Override
	public void iniDaughter() {
		count.clear();
		result.clear();
		for (int i = 0; i < book.getSize(); i++) {
			count.put(book.getParagraphByID(i), 0);
		}
	}
	
	@Override
	public void onNewNodeVisited(Paragraph previous, Paragraph element) {
		return;
	}
	
	@Override
	public void onNodeVisited(Paragraph element) {
		count.replace(element, count.get(element)+1);
	}

	@Override
	public void parse(GameBook book) {
		this.book = (book == null) ? new GameBook(null, null) : book;
		super.parseBook(this.book);
		
		for (int index = 0; index < book.getSize(); index++) {
			result.add(formatResult(book.getParagraphByID(index), index));
		}
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getDecription() {
		return String.format(DESCRIPTION, book.getTitle());
	}

	@Override
	public Collection<String> getResults() {
		return result;
	}

}
