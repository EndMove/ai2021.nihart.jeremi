package gamebook.domains.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

public class TargetParagraphFrequency extends GameBookIterate {
	
	private static final String TITLE = "Nombre d'apparition des paragraphes dans les choix";
	private static final String DESCRIPTION = "Relève pour le livre '%s' le nombre de fois que \n chaque § figure dans une destination.";
	private static final String RESULT_ANSWER = "Le §%d apparaît (%dx) : %s";
	
	private GameBook book;
	
	private Map<Paragraph, Integer> count = new HashMap<>();
	
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
		this.book = (book != null) ? book : new GameBook(null, null);
		
		// Nettoyage et régénération de la map
		count.clear();
		for (int i = 0; i < book.getSize(); i++) {
			count.put(book.getParagraphByID(i), 0);
		}
		
		// Parse le livre avec classe mère
		super.parseBook(book);
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
		List<String> result = new ArrayList<>();
		for (int index = 0; index < book.getSize(); index++) {
			Paragraph p = book.getParagraphByID(index);
			result.add(String.format(RESULT_ANSWER, index+1, count.get(p), p.getContent()));
		}
		return result;
	}

}
