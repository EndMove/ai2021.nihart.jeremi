package gamebook.fakes;

import java.util.Collection;
import java.util.List;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookStatement;

public class FakeGameBookStatement extends Fake implements GameBookStatement {
	
	private String title;
	private String description;
	private List<String> results;
	
	public FakeGameBookStatement(String title, String description, List<String> results) {
		this.title = title;
		this.description = description;
		this.results = (results == null || results.isEmpty()) ? List.of("Aucun resultat") : results;
	}

	@Override
	public void parse(GameBook book) {
		countCall("parse", book);
	}

	@Override
	public String getTitle() {
		countCall("getTitle");
		return title;
	}

	@Override
	public String getDecription() {
		countCall("getDescription");
		return description;
	}

	@Override
	public Collection<String> getResults() {
		countCall("getResults");
		return results;
	}

}