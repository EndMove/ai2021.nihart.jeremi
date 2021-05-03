package gamebook.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
		Session s = new Session(null);
		GameBook b = s.getBook();
		assertEquals(GameBook.BOOK_TITLE, b.getTitle());
	}

	@Test
	public void getBook() {
		assertEquals(sess.getBook(), book);
	}

	@Test
	public void getCurrentParagraph() {
		assertEquals(sess.getCurrentParagraph(), book.getFirstParagraph());
	}

	@Test
	public void getCurrentHead() {
		Paragraph p = book.getFirstParagraph();
		assertEquals(sess.getCurrentHead(), book.getParagraphHead(p));
	}

	@Test
	public void getCurrentContent() {
		assertEquals(sess.getCurrentContent(), GameBookFactory.P1_CONTENT);
	}

	@Test
	public void isCurrentHasChoice() {
		assertTrue(sess.isCurrentHasChoice());
		sess.goTo(book.getParagraphByID(1));
		assertFalse(sess.isCurrentHasChoice());
	}

	@Test
	public void getCurrentChoices() {
		Paragraph p = book.getFirstParagraph();
		assertEquals(sess.getCurrentChoices(), p.getChoices());
	}

	@Test
	public void goBack() {
		Paragraph p = book.getParagraphByID(3);
		sess.goTo(p);
		sess.goBack();
		assertEquals(sess.getCurrentParagraph(), book.getFirstParagraph());
	}

	@Test
	public void goTo() {
		Paragraph p = book.getParagraphByID(3);
		sess.goTo(p);
		assertEquals(sess.getCurrentParagraph(), p);
	}

	@Test
	public void goToParagraphByChoiceKey() {
		sess.goToParagraphByChoiceKey(GameBookFactory.WALK);
		assertEquals(sess.getCurrentParagraph(), book.getParagraphByID(3));
	}

	@Test
	public void reset() {
		Paragraph p1 = book.getParagraphByID(3);
		Paragraph p2 = book.getParagraphByID(2);
		sess.goTo(p1);
		sess.goTo(p2);
		sess.reset();
		assertEquals(sess.getCurrentParagraph(), book.getFirstParagraph());
	}

}
