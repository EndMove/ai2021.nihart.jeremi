package gamebook.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBookTest {
	
	private final GameBook book = GameBookFactory.makeGameBook();

	@Test
	public void constructor() {
		List<GameBook> books = List.of(new GameBook(null, null),
									   new GameBook(null, List.of()));
		for (GameBook b : books) {
			List<String> pc = b.getParagraphsContents();
			assertEquals(b.getTitle(), GameBook.BOOK_TITLE);
			assertTrue(pc.size() == 1);
			assertTrue(b.getFirstParagraph() instanceof Paragraph);
		}
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
		Paragraph p = book.getParagraphByID(0);
		assertEquals(book.getParagraphIdByChoiceKey(GameBookFactory.LOOK_BEHIND, p), 2);
	}

	@Test
	public void setParagraphContent() {
		Paragraph p = book.getParagraphByID(1);
		assertEquals(p.getContent(), GameBookFactory.P2_CONTENT);
		book.setParagraphContent(p, GameBookFactory.P5_CONTENT);
		assertEquals(p.getContent(), GameBookFactory.P5_CONTENT);
	}

	@Test
	public void setTitle() {
		String newTitle = "Nouveau Livre";
		assertFalse(book.setTitle(""));
		assertFalse(book.setTitle("     "));
		assertTrue(book.setTitle(newTitle));
		assertEquals(book.getTitle(), newTitle);
	}

	@Test
	public void addParagraph() {
		Paragraph p = new Paragraph("Nouveau p");
		book.addParagraph(p);
		assertEquals(book.getLastParagraph(), p);
	}

	@Test
	public void addParagraphs() {
		List<Paragraph> ps = List.of(new Paragraph("new 01"), new Paragraph("new 02"));
		book.addParagraphs(ps);
		assertEquals(book.getParagraphByID(5), ps.get(0));
		assertEquals(book.getParagraphByID(6), ps.get(1));
	}

	@Test
	public void deleteParagraph() {
		Paragraph p = book.getParagraphByID(2);
		assertEquals(p.getChoices().size(), 3);
		assertTrue(book.deleteParagraph(4));
		assertEquals(p.getChoices().size(), 2);
		// try to reach the deletion limit
		assertTrue(book.deleteParagraph(0));
		assertTrue(book.deleteParagraph(0));
		assertTrue(book.deleteParagraph(0));
		assertFalse(book.deleteParagraph(0));
	}

	@Test
	public void objectToString() {
		assertEquals("Book(title="+GameBookFactory.BOOK_TITLE+", paragraphs_count=5)", book.toString());
	}

}
