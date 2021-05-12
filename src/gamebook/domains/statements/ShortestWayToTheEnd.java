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
	
	private final Map<Paragraph, Paragraph> history = new HashMap<>();
	private final List<String> result = new ArrayList<>();
	
	private GameBook book;
	private Paragraph terminal;
	
	private List<Paragraph> recursivityWayMaker(List<Paragraph> way, Paragraph paragraph) {
		if (paragraph == null) {
			return way;
		}
		way.add(paragraph);
		return recursivityWayMaker(way, history.get(paragraph));
	}
	
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
