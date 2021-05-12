package gamebook.domains.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gamebook.domains.GameBook;
import gamebook.domains.Paragraph;

public class ShortestWayToTheEnd extends GameBookIterate {
	
	private static final String TITLE = "Chemin terminale le plus rapide";
	private static final String DESCRIPTION = "Le livre '%s' traverse %d § pour atteindre le %d.";
	private static final String RESULT_ANSWER = "Le §%d : %s";
	private static final String NO_RESULT_ANSWER = "Aucun chemin valide depuis le §1.";
	
	private GameBook book;
	private Paragraph terminal;
	
	private Map<Paragraph, Paragraph> history = new HashMap<>();
	
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
		this.book = (book != null) ? book : new GameBook(null, null);
		
		// Nettoyage et régénération de la map et variables
		terminal = null;
		history.clear();
		for (int i = 0; i < book.getSize(); i++) {
			history.put(book.getParagraphByID(i), null);
		}
		
		// Nettoyage et régénération de la map
		super.parseBook(book);
		
		System.out.println("------------------------------");
		for (int index = 0; index < book.getSize(); index++) {
			Paragraph p = book.getParagraphByID(index);
			System.out.println(String.format("§%d <= §%d", book.getParagraphIdByObject(p)+1, book.getParagraphIdByObject(history.get(p))+1));
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
		List<String> result = new ArrayList<>();
		List<Paragraph> way = new ArrayList<>();
		
		if (terminal == null) {
			result.add(NO_RESULT_ANSWER);
		} else {
			recursivityWayMaker(way, terminal);
			for (int i = way.size()-1; i >= 0; i--) {
				Paragraph p = way.get(i);
				result.add(String.format(RESULT_ANSWER, book.getParagraphIdByObject(p)+1, p.getContent()));
			}
		}
		return result;
	}
	
	private List<Paragraph> recursivityWayMaker(List<Paragraph> way, Paragraph paragraph) {
		if (paragraph == null) {
			return way;
		}
		way.add(paragraph);
		return recursivityWayMaker(way, history.get(paragraph));
	}

}
