package gamebook.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBookTest {
	
	private final GameBook book = GameBookFactory.makeGameBook();
	
	@BeforeEach
	public void ini() {
		
	}

	@Test
	public void constructor() {
		GameBook b = new GameBook(null, null);
		List<String> pc = b.getParagraphsContents();
		assertEquals(b.getTitle(), GameBook.BOOK_TITLE);
		assertTrue(pc.size() == 1);
		assertTrue(b.getFirstParagraph() instanceof Paragraph);
	}

	@Test
	public void getTitle() {
		assertEquals(book.getTitle(), GameBookFactory.BOOK_TITLE);
	}

	@Test
	public void getParagraphHead() {
		Paragraph p = book.getLastParagraph();
		assertEquals(book.getParagraphHead(p), Paragraph.PARAGRAPH_HEAD+" 5");
	}

	@Test
	public void getParagraphsContents() {
		Paragraph p = book.getFirstParagraph();
		List<String> pc = book.getParagraphsContents();
		assertTrue(pc.size() == 5);
		assertEquals(pc.get(0), p.getContent());
	}

	@Test
	public void getParagraphByID() {
		assertEquals(book.getParagraphByID(0), book.getFirstParagraph());
		assertEquals(book.getParagraphByID(4), book.getLastParagraph());
	}

	@Test
	public void getFirstParagraph() {
		assertEquals(book.getFirstParagraph(), book.getParagraphByID(0));
	}

	@Test
	public void testGetLastParagraph() {
		assertEquals(book.getLastParagraph(), book.getParagraphByID(4));
	}

	@Test
	public void testGetParagraphIdByObject() {
		Paragraph p = book.getParagraphByID(2);
		assertTrue(book.getParagraphIdByObject(p) == 2);
	}

	@Test
	public void getParagraphIdByChoiceKey() {
		Paragraph p = book.getParagraphByID(2);
		List<String> pc = 
	}

	@Test
	public void testSetParagraphContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddParagraph() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddParagraphs() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteParagraph() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
