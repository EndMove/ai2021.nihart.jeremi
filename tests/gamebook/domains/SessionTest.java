package gamebook.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
	
	private final Paragraph p1 = new Paragraph(1, "Je suis un paragraphe :D");
	private final Paragraph p2 = new Paragraph(2, "Je suis un paragraphe :D");
	private final Paragraph p3 = new Paragraph(3, "Je suis un paragraphe n°2");
	private final GameBook b = new GameBook(GameBookFactory.BOOK_TITLE, List.of(p1));
	private final Session s = new Session(b);

	@BeforeEach
	public void ini() {
		p2.addChoice("Choix", p1);
		b.addParagraphs(List.of(p1, p2, p3));
		s.goTo(p2);
	}
	
	@Test
	public void constructor() {
		Session test01 = new Session(null);
		GameBook book = test01.getBook();
		assertEquals("No title", book.getTitle());
	}

	@Test
	public void getBook() {
		assertEquals(b, s.getBook());
	}

	@Test
	public void getCurrentParagraph() {
		assertEquals(p2, s.getCurrentParagraph());
	}
	
	@Test
	public void getCurrentHead() {
		assertEquals(p2.getHead(), s.getCurrentHead());
	}
	
	@Test
	public void getCurrentContent() {
		assertEquals(p2.getContent(), s.getCurrentContent());
	}
	
	@Test
	public void getCurrentHasChoice() {
		assertTrue(s.isCurrentHasChoice());
		s.goTo(p3);
		assertFalse(s.isCurrentHasChoice());
	}
	
	@Test
	public void getCurrentChoices() {
		assertEquals(p2.getChoices(), s.getCurrentChoices());
	}

	@Test
	public void goBack() {
		s.goBack();
		assertEquals(p1, s.getCurrentParagraph());
	}

	@Test
	public void goTo() {
		s.goTo(p2);
		assertEquals(p2, s.getCurrentParagraph());
	}

	@Test
	public void goToParagraphByChoiceKey() {
		s.goToParagraphByChoiceKey("Choix");
		assertEquals(p1, s.getCurrentParagraph());
	}

	@Test
	public void reset() {
		s.reset();
		assertEquals(p1, s.getCurrentParagraph());
	}

}
