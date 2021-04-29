package gamebook.fakes;

import gamebook.supervisers.CheckView;

public class FakeCheckView extends Fake implements CheckView {

	@Override
	public void clearResult() {
		countCall("clearResult");
	}

	@Override
	public void setTitle(String title) {
		countCall("setTitle", title);
	}

	@Override
	public void startResultFor(String analysisName) {
		countCall("startResultFor", analysisName);
	}

	@Override
	public void setDescription(String description) {
		countCall("setDescription", description);
	}

	@Override
	public void addResultItem(String content) {
		countCall("addResultItem", content);
	}

	@Override
	public void endResult() {
		countCall("endResult");
	}

}
