package gamebook.domains.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;
import gamebook.domains.Paragraph;

public class TargetParagraphFrequency implements GameBookStatement {
	
	private static final String TITLE = "Nombre d'apparition des paragraphes dans les choix";
	private static final String DESCRIPTION = "Relève pour le livre '%s' le nombre de fois que \n chaque § figure dans une destination.";
	
	private GameBook book;
	
	private Map<Paragraph, Integer> pCount;
	private List<String> pResult;

	public TargetParagraphFrequency() {}
	
	private void addCount(Paragraph paragraph, int defaultValue) {
		if (pCount.containsKey(paragraph)) {
			pCount.replace(paragraph, pCount.get(paragraph)+1);
		} else {
			pCount.put(paragraph, defaultValue);
		}
	}

	@Override
	public void parse(GameBook book) {
		this.book = (book != null) ? book : new GameBook(null, null);
		pCount = new HashMap<>();
		
		Paragraph pCurrent;
		
		for (int pIndex = 0; pIndex < book.getSize(); pIndex++) {
			pCurrent = book.getParagraphByID(pIndex);
			if (pIndex == 0) {
				addCount(pCurrent, 0);
			}
			for (String choice : pCurrent.getChoices()) {
				addCount(pCurrent.getParagraphByChoiceKey(choice), 1);
			}
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
		pResult = new ArrayList<>();
		for (int pIndex = 0; pIndex < book.getSize(); pIndex++) {
			pResult.add("");
		}
		return pResult;
	}

}
