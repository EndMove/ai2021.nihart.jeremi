package gamebook.fakes;

import java.util.*;

import gamebook.supervisers.ReadView;

/**
 * Conserve une trace des appels de m�thodes dont elle a h�rit�e.
 * V�rifie si un appel a �t� re�u.
 * 
 * */
public class FakeReaderView extends Fake implements ReadView {
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setTitle(String bookTitle) {
		countCall("setTitle", bookTitle);
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setParagraph(String head, String content) {
		countCall("setParagraph", head, content);	
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setChoices(Collection<String> choices) {
		var choicesAsList = new ArrayList<>(choices);
		choicesAsList.sort(Comparator.comparing(String::toString));
		countCall("setChoices", choicesAsList);	
	}
}
