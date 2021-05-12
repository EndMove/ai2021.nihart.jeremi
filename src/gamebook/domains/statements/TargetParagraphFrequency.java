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
	private static final String DESCRIPTION = "Relève pour le livre '%s' le nombre de fois que chaque § figure dans une destination.";
	private static final String RESULT_ANSWER = "Le §%d apparaît (%dx) : %s";
	
	private final Map<Paragraph, Integer> count = new HashMap<>();
	private final List<String> result = new ArrayList<>();
	
	private GameBook book;
	
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
