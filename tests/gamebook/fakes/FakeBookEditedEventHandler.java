package gamebook.fakes;

import gamebook.domains.BookEditedEventHandler;

public class FakeBookEditedEventHandler extends Fake implements BookEditedEventHandler {

	@Override
	public void onBookEdited() {
		countCall("onBookEdited");
	}
}
