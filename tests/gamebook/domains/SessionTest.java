package gamebook.domains;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
	
	private final GameBook book = GameBookFactory.makeGameBook();
	private final Session sess = new Session(book);
	
	@BeforeEach
	public void ini() {
		
	}

	@Test
	public void constructor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentParagraph() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentHead() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsCurrentHasChoice() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentChoices() {
		fail("Not yet implemented");
	}

	@Test
	public void testGoBack() {
		fail("Not yet implemented");
	}

	@Test
	public void testGoTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGoToParagraphByChoiceKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

}
