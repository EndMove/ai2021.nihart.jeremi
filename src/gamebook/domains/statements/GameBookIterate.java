package gamebook.domains.statements;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;
import gamebook.domains.Paragraph;

public abstract class GameBookIterate implements GameBookStatement {
	
	private final Set<Paragraph> visited = new HashSet<>();
	private final Deque<Paragraph> queue = new ArrayDeque<>();
	
	private List<Paragraph> getChoices(Paragraph paragraph) {
		List<Paragraph> choices = new ArrayList<>();
		for (String key : paragraph.getChoices()) {
			choices.add(paragraph.getParagraphByChoiceKey(key));
		}
		return choices;
	}
	
	public void parseBook(GameBook book) {
		System.out.println("OKKKKK");
		visited.clear();
		queue.clear();
		
		Paragraph pCurrent = book.getParagraphByID(0);
		
		visited.add(pCurrent);
		queue.add(pCurrent);
		do {
			pCurrent = queue.removeFirst();
			for (Paragraph p : getChoices(pCurrent)) {
				if (!visited.contains(p)) {
					visited.add(p);
					queue.add(p);
					onNewNodeVisited(pCurrent, p);
				}
			}
		} while (!queue.isEmpty());
	}
	
	public abstract void onNewNodeVisited(Paragraph previous, Paragraph element);

	@Override
	public void parse(GameBook book) {
		return;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public String getDecription() {
		return null;
	}

	@Override
	public Collection<String> getResults() {
		return null;
	}

}
