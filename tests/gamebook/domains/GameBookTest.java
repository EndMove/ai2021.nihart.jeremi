package gamebook.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class GameBookTest {
	
	private final Paragraph p1 = new Paragraph(1, "Je suis un texte de taille moyenne, les gens m'aiment.");
	private final Paragraph p2 = new Paragraph(2, "Je suis un texte long et interminable.....");
	private final Paragraph p3 = new Paragraph(3, "Mon nom est paragraphe 3");
	private final Paragraph p4 = new Paragraph(4, "ET MOI PH 4 !");
	private final Paragraph p5 = new Paragraph(5, "Nous sommes tous des paragraphes calmez vous ...");
	private final Paragraph p6 = new Paragraph(6, "Toi silence !");
	
	GameBook b = new GameBook("Je suis un livre", List.of(p1, p2, p3, p4));

	@Test
	public void constructor() {
		GameBook b = new GameBook(null, null);
		assertEquals("No title", b.getTitle());
		assertTrue(b.getParagraphFirst() instanceof Paragraph);
	}

	@Test
	public void getTitle() {
		assertEquals("Je suis un livre", b.getTitle());
		
	}

	@Test
	public void getParagraphByID() {
		assertTrue(p3.equals(b.getParagraphByID(3)));
	}

	@Test
	public void getFirstParagraph() {
		assertEquals(p1, b.getParagraphFirst());
	}

	@Test
	public void setTitle() {
		b.setTitle("Oupss :(");
		assertEquals("Oupss :(", b.getTitle());
	}

	@Test
	public void addParagraphs() {
		b.addParagraphs(List.of(p5, p6));
		assertEquals(p5, b.getParagraphByID(5));
		assertEquals(p6, b.getParagraphByID(6));
	}

	@Test
	public void GameBooktoString() {
		//System.out.println("@Test: BooktoString:\n" + b.toString());
		assertEquals("Book(title=Je suis un livre, paragraphs_count=4)", b.toString());
	}
}
