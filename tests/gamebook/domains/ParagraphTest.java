package gamebook.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParagraphTest {
	
	private final Paragraph p1 = new Paragraph(1, "Je suis un texte de taille moyenne, les gens m'aiment.");
	private final Paragraph p2 = new Paragraph(2, "Je suis un texte long et interminable.....");
	private final Paragraph p3 = new Paragraph(3, "héhé je suis le paragraphe");
	private final Paragraph p4 = new Paragraph(4, "Je suis un texte long et interminable.....");
	
	@BeforeEach
	public void ini_AndAddChoice() {
		p1.addChoice(GameBookFactory.LOOK_BEHIND, p3);
		p1.addChoice(GameBookFactory.KEEP_READING, p2);
		p2.addChoice(GameBookFactory.WALK, p4);
		p2.addChoice(GameBookFactory.LOOK_BEHIND, p3);
		p2.addChoice(GameBookFactory.KEEP_READING, p2);
	}
	
	@Test
	public void constructor() {
		Paragraph test01 = new Paragraph(-1565, "...");
		Paragraph test02 = new Paragraph(0, null);
		assertEquals(0, test01.getID());
		assertEquals(0, test02.getID());
		assertEquals("No content", test02.getContent());
	}
	
	@Test
	public void getID() {
		assertEquals(2, p2.getID());
		assertEquals(4, p4.getID());
	}

	@Test
	public void getHead() {
		assertEquals("Paragraphe 2", p2.getHead());
		assertEquals("Paragraphe 1", p1.getHead());
	}

	@Test
	public void getContent() {
		assertEquals("Je suis un texte long et interminable.....", p2.getContent());
		assertEquals("héhé je suis le paragraphe", p3.getContent());
	}

	@Test
	public void getAndAddChoices() {
		p1.addChoice(GameBookFactory.WALK, p4);
		Collection<String> tmp = p1.getChoices();
		assertTrue(tmp.contains(GameBookFactory.WALK));
		assertTrue(tmp.contains(GameBookFactory.KEEP_READING));
		assertTrue(tmp.contains(GameBookFactory.LOOK_BEHIND));
	}
	
	@Test
	public void getParagraphByChoiceKey() {
		assertEquals(p2.getParagraphByChoiceKey(GameBookFactory.WALK), p4);
		assertEquals(p2.getParagraphByChoiceKey(GameBookFactory.LOOK_BEHIND), p3);
	}
	
	@Test
	public void hasChoice() {
		assertTrue(p1.hasChoice());
		assertFalse(p4.hasChoice());
	}

	@Test
	public void paragraphToString() {
		//System.out.println("@Test: ParagraphToString:\n" + p1.toString());
		assertEquals("Paragraph(id=1, content=Je suis un texte de taille moyenne, les gens m'aiment., choices_count=2)", p1.toString());
	}
}
