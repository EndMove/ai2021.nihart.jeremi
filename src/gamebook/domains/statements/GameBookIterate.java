package gamebook.domains.statements;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
	
	private Paragraph pCurrent;
	
	private List<Paragraph> getChoices(Paragraph paragraph) {
		List<Paragraph> choices = new ArrayList<>();
		for (String key : paragraph.getChoices()) {
			choices.add(paragraph.getParagraphByChoiceKey(key));
		}
		return choices;
	}
	
	private void ini(GameBook book) {
		// Vider fil d'attente et ensemble.
		visited.clear();
		queue.clear();
		// Récupère premier paragraphe.
		pCurrent = book.getParagraphByID(0);
		// Initialiser la fil d'attente & l'ensemble déjà visité.
		visited.add(pCurrent);
		queue.add(pCurrent);
		// Initialise la fille.
		iniDaughter();
	}
	
	public void parseBook(GameBook book) {
		ini(book);
		do {
			pCurrent = queue.removeFirst();
			for (Paragraph p : getChoices(pCurrent)) {
				if (!visited.contains(p)) {
					visited.add(p);
					queue.add(p);
					onNewNodeVisited(pCurrent, p);
				}
				onNodeVisited(p);
			}
		} while (!queue.isEmpty());
	}
	
	protected abstract void iniDaughter();
	
	protected abstract void onNewNodeVisited(Paragraph previous, Paragraph element);
	
	protected abstract void onNodeVisited(Paragraph element);

}
