package gamebook.fakes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import gamebook.supervisers.EditView;
/**
 * 
 * */
public class FakeEditView extends FakeView implements EditView {

	@Override
	public void setTitle(String newTitle) {
		countCall("setTitle", newTitle);
	}

	@Override
	public void setParagraphs(List<String> paragraphs) {
		countCall("setParagraphs", paragraphs);
	}

	@Override
	public void setSelectedParagraph(int index) {
		countCall("setSelectedParagraph", index);
	}

	@Override
	public void setChoices(Collection<String> choices) {
		var choicesAsList = new ArrayList<>(choices);
		choicesAsList.sort(Comparator.comparing(String::toString));
		countCall("setChoices", choicesAsList);
	}

	@Override
	public void setSelectedChoice(String key, int targetIndex) {
		countCall("setSelectedChoice", key, targetIndex);
	}

	@Override
	public void setCurrentParagraphContent(String content) {
		countCall("setCurrentParagraphContent", content);
	}

}
