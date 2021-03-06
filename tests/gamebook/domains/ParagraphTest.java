package gamebook.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParagraphTest {
	
	private final Paragraph p0 = new Paragraph(GameBookFactory.P1_CONTENT);
	private final Paragraph p1 = new Paragraph(GameBookFactory.P2_CONTENT);
	private final Paragraph p2 = new Paragraph(GameBookFactory.P3_CONTENT);
	private final Paragraph p3 = new Paragraph(GameBookFactory.P4_CONTENT);
	private final Paragraph p4 = new Paragraph(GameBookFactory.P5_CONTENT);
	
	@BeforeEach
	public void ini() {
		GameBookFactory.makeChoices(List.of(p0, p1, p2, p3, p4));
	}

	@Test
	public void constructor() {
		Paragraph pOne = new Paragraph(null);
		Paragraph pTwo = new Paragraph(GameBookFactory.P1_CONTENT);
		assertEquals(pOne.getContent(), Paragraph.PARAGRAPH_CONTENT);
		assertEquals(pTwo.getContent(), GameBookFactory.P1_CONTENT);
	}

	@Test
	public void getContent() {;
		assertEquals(p3.getContent(), GameBookFactory.P4_CONTENT);
	}

	@Test
	public void getChoices() {
		List<String> choices = p0.getChoices();
		assertTrue(choices.size() == 3);
		assertTrue(choices.contains(GameBookFactory.WALK));
		assertFalse(choices.contains(GameBookFactory.FOLLOW));
	}

	@Test
	public void getParagraphByChoiceKey() {
		assertEquals(p0.getParagraphByChoiceKey(GameBookFactory.KEEP_READING), p1);
	}

	@Test
	public void setContent() {
		String newContent = "New content of this paragraph";
		assertTrue(p4.setContent(newContent));
		assertEquals(p4.getContent(), newContent);
		assertFalse(p4.setContent(" "));
		assertFalse(p4.setContent(null));
	}
	
	@Test
	public void setContentWithSpecifiedParagraph() {
		String newContent = "New content of this paragraph";
		assertTrue(Paragraph.setContent(p4, newContent));
		assertEquals(p4.getContent(), newContent);
	}

	@Test
	public void addChoice() {
		String newChoiceKey = "S'envoler rapidement";
		p1.addChoice(newChoiceKey, p2);
		List<String> c = p1.getChoices();
		assertTrue(c.contains(newChoiceKey));
		p2.addChoice(" ", p2);
		c = p2.getChoices();
		assertTrue(c.contains(Paragraph.PARAGRAPH_CHOICE));
		p3.addChoice(null, p2);
		c = p3.getChoices();
		assertTrue(c.contains(Paragraph.PARAGRAPH_CHOICE));
	}

	@Test
	public void deleteChoice() {
		p0.deleteChoice(GameBookFactory.KEEP_READING);
		assertEquals(p0.getChoices().size(), 2);
	}
	
	@Test
	public void deleteChoiceByParagraph() {
		assertEquals(p2.getChoices().size(), 3);
		p2.deleteChoiceByParagraph(p4);
		assertEquals(p2.getChoices().size(), 2);
	}

	@Test
	public void updateChoiceKey() {
		assertTrue(p3.updateChoiceKey(GameBookFactory.FOLLOW, GameBookFactory.WALK));
		List<String> c = p3.getChoices();
		assertTrue(c.contains(GameBookFactory.WALK));
		assertFalse(c.contains(GameBookFactory.FOLLOW));
		assertFalse(p3.updateChoiceKey(GameBookFactory.FOLLOW, GameBookFactory.WALK));
		assertFalse(p3.updateChoiceKey(GameBookFactory.WALK, GameBookFactory.WALK));
		assertFalse(p3.updateChoiceKey(GameBookFactory.WALK, null));
		assertFalse(p3.updateChoiceKey(GameBookFactory.WALK, " "));
	}

	@Test
	public void updateChoiceParagraph() {
		p2.updateChoiceParagraph(GameBookFactory.WALK, p1);
		assertEquals(p2.getParagraphByChoiceKey(GameBookFactory.WALK), p1);
	}

	@Test
	public void hasChoice() {
		assertTrue(p0.hasChoice());
		assertFalse(p4.hasChoice());
	}
}
