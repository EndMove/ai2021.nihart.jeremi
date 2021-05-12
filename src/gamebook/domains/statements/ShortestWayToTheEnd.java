package gamebook.domains.statements;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

public class ShortestWayToTheEnd extends GameBookIterate {
	
	private static final String TITLE = "Chemin terminale le plus rapide";
	private static final String DESCRIPTION = "Le livre '%s' traverse %d § pour atteindre le %d.";
	
	private GameBook book;
	
	private Map<Paragraph, Paragraph> history = new HashMap<>();
	
	@Override
	public void onNewNodeVisited(Paragraph previous, Paragraph element) {
		history.replace(element, previous);
	}

	@Override
	public void parse(GameBook book) {
		this.book = (book != null) ? book : new GameBook(null, null);

		history.clear();
		
		for (int i = 0; i < book.getSize(); i++) {
			history.put(book.getParagraphByID(i), null);
		}
		
		super.parseBook(book);
		
		System.out.println("------------------" + history.size());
		for (Paragraph p : history.keySet()) {
			System.out.println(book.getParagraphIdByObject(p)+1+" => "+(book.getParagraphIdByObject(history.get(p))+1));
		}
		
		
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getDecription() {
		return String.format(DESCRIPTION, book.getTitle(), 10, 5);
	}

	@Override
	public Collection<String> getResults() {
		return null; // TODO
	}

}
