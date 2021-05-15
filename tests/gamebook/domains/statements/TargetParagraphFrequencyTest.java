package gamebook.domains.statements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;
import gamebook.domains.Paragraph;

/**
 * Introduction:
 * TargetParagraphFrequency est une classe permettant de comptabiliser le nombre de fois qu'un paragraphe
 * peut revenir dans les choix lors d'une lecture du livre jeu commencant au paragraphe un. Cette classe
 * comptabilise les choix en circuit sans ordre de parcourt pr�d�fini ormit le fait que la lecture commence
 * toujours au paragraphe un.
 * 
 * Cette classe de test permet de v�rifier que tout les cas et type de livre sont bien g�r�. 
 * 
 * @author J�r�mi Nihart
 */
class TargetParagraphFrequencyTest {
	
	private TargetParagraphFrequency tpf;
	
	@BeforeEach
	public void ini() {
		tpf = new TargetParagraphFrequency();
	}
	
	/**
	 * +++ TEST DES FONCTIONS DE BASES +++
	 */
	
	@Test
	public void getTitle() {
		assertEquals(tpf.getTitle(), "Nombre d'apparition des paragraphes dans les choix");
	}

	@Test
	public void getDecription() {
		tpf.parse(getBookDefault());
		assertEquals(tpf.getDescription(), "Rel�ve pour le livre '"+GameBookFactory.BOOK_TITLE+"' le nombre de fois que chaque � figure dans une destination.");
	}
	
	/**
	 * +++ PLAN DE TEST +++
	 * Ce plan de test comporte 5 tests de 5 livres jeu diff�rent dans 4
	 * situations diff�rentes pour certaine aillant des similitudes.
	 * 
	 * Les tests #01 et #02 v�rifient deux livres normaux avec des liaisons pour
	 * chaque paragraphe et un ou plusieur paragraphe final pour chaqu'un d'eux deux
	 * 
	 * Le test #03 permet de v�rifier que m�me en l'absence d'un paragraphe terminal
	 * l'algorithme arrive quand m�me � s'arreter et � parcourir tout les choix accessibles.
	 * 
	 * Le test #04 permet de v�rifier que l'algorithme ne comptabilise pas les paragraphes
	 * hors circuit et que dans le cas ou le livre fini au paragraphe un, il s'arr�te.
	 * 
	 * Le test #05 permet de v�rifier que l'algorithme ne boucle pas infiniment dans le
	 * cas ou tout les paragraphes pointent sur tout les paragraphe et que le livre jeu ne
	 * poss�de pas de fin d�finie.
	 * 
	 * Le test #06 permet de v�rifie qu'il n'y a pas d'erreur dans le cas ou un livre jeu est vide
	 * ou null.
	 */
	
	/**
	 * Test #01 d'un cas normal et ses r�sultats avec le livre
	 * de base du programme.
	 */
	@Test
	public void normalBookResultsAndParseCheck() {
		tpf.parse(getBookDefault());
		List<String> expectedResults = List.of(
				"Le �1 appara�t (0x) : " + GameBookFactory.P1_CONTENT,
				"Le �2 appara�t (3x) : " + GameBookFactory.P2_CONTENT,
				"Le �3 appara�t (1x) : " + GameBookFactory.P3_CONTENT,
				"Le �4 appara�t (2x) : " + GameBookFactory.P4_CONTENT,
				"Le �5 appara�t (2x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #02 d'un autre cas normal et ses r�sultats avec un
	 * livre jeu classic.
	 */
	@Test
	public void anOtherNormalBookResultsAndParseCheck() {
		tpf.parse(getBookNormal());
		List<String> expectedResults = List.of(
				"Le �1 appara�t (1x) : " + GameBookFactory.P1_CONTENT,
				"Le �2 appara�t (2x) : " + GameBookFactory.P2_CONTENT,
				"Le �3 appara�t (1x) : " + GameBookFactory.P3_CONTENT,
				"Le �4 appara�t (1x) : " + GameBookFactory.P4_CONTENT,
				"Le �5 appara�t (1x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #03 d'un cas d'erreur, livre sans fin avec un paragraphe non inclut.
	 */
	@Test
	public void withOutEndBookResultsAndParseCheck() {
		tpf.parse(getBookWithoutEnd());
		List<String> expectedResults = List.of(
				"Le �1 appara�t (2x) : " + GameBookFactory.P1_CONTENT,
				"Le �2 appara�t (1x) : " + GameBookFactory.P2_CONTENT,
				"Le �3 appara�t (1x) : " + GameBookFactory.P3_CONTENT,
				"Le �4 appara�t (1x) : " + GameBookFactory.P4_CONTENT,
				"Le �5 appara�t (0x) : " + GameBookFactory.P5_CONTENT
				// Le p5 ne fait pas partie du circuit du livre par d�finition
				// un paragraphe hors circuit ne doit pas �tre comptabilis�.
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #04 d'un cas d'erreurs et cas limite, livre ou le paragraphe un n'est
	 * pas reli� aux autres et ou il est terminal.
	 */
	@Test
	public void notLinkedWithPOneBookResultsAndParseCheck() {
		tpf.parse(getBookNotLinkedWithPOne());
		List<String> expectedResults = List.of(
				"Le �1 appara�t (0x) : " + GameBookFactory.P1_CONTENT,
				"Le �2 appara�t (0x) : " + GameBookFactory.P2_CONTENT,
				"Le �3 appara�t (0x) : " + GameBookFactory.P3_CONTENT,
				"Le �4 appara�t (0x) : " + GameBookFactory.P4_CONTENT,
				"Le �5 appara�t (0x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #05 d'un cas avec un surplus de combinaisons et possibilit�es.
	 */
	@Test
	public void allPConnectedBookResultsAndParseCheck() {
		tpf.parse(getBookAllPConnected());
		List<String> expectedResults = List.of(
				"Le �1 appara�t (5x) : " + GameBookFactory.P1_CONTENT,
				"Le �2 appara�t (5x) : " + GameBookFactory.P2_CONTENT,
				"Le �3 appara�t (5x) : " + GameBookFactory.P3_CONTENT,
				"Le �4 appara�t (5x) : " + GameBookFactory.P4_CONTENT,
				"Le �5 appara�t (5x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #06 d'un cas ou le livre jeu est vide.
	 */
	@Test
	public void emptyBookResultsAndParseCheck() {
		// livre jeu d�j� cr�e
		tpf.parse(getBookEmpty());
		List<String> expectedResults = List.of(
				"Le �1 appara�t (0x) : " + Paragraph.PARAGRAPH_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
		// livre jeu null
		tpf.parse(null);
		expectedResults = List.of(
				"Le �1 appara�t (0x) : " + Paragraph.PARAGRAPH_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * +++ METHODES UTILITAIRES +++
	 */
	
	/**
	 * M�thode de v�rification des r�sultats.
	 * @param result R�sultat.
	 * @param expectedResult R�sultat attendu par l'algorithme.
	 */
	private void checkResults(List<String> results, List<String> expectedResults) {
		if (results.size() == expectedResults.size()) {
			for (int i = 0; results.size() > i; i++) {
				assertEquals(results.get(i), expectedResults.get(i));
			}
		} else {
			fail("Les r�sultats ne correspondent pas !");
		}
	}
	
	/**
	 * Craie le livre jeu de base du programme (cas narmal)
	 * @return Livre jeu de base du programme
	 */
	private GameBook getBookDefault() {
		return GameBookFactory.makeGameBook();
	}
	
	/**
	 * Craie un livre jeu normal
	 * @return Livre jeu normal
	 */
	private GameBook getBookNormal() {
		List<Paragraph> p = GameBookFactory.makeParagraphs();
		GameBookFactory.setChoices(p.get(0), "vers p1", p.get(4));
		GameBookFactory.setChoices(p.get(1), "vers p2", p.get(0), p.get(3));
		GameBookFactory.setChoices(p.get(2), "vers p3", p.get(0));
		GameBookFactory.setChoices(p.get(3), "vers p4", p.get(2));
		GameBookFactory.setChoices(p.get(4), "vers p5", p.get(3));
		return new GameBook("EndMoveBook 01", p);
	}
	
	/**
	 * Craie un livre jeu sans fin
	 * @return Livre jeu sans fin
	 */
	private GameBook getBookWithoutEnd() {
		List<Paragraph> p = GameBookFactory.makeParagraphs();
		GameBookFactory.setChoices(p.get(0), "vers p1", p.get(2), p.get(3));
		GameBookFactory.setChoices(p.get(1), "vers p2", p.get(0));
		GameBookFactory.setChoices(p.get(2), "vers p3", p.get(3));
		GameBookFactory.setChoices(p.get(3), "vers p4", p.get(1));
		return new GameBook("EndMoveBook 02", p);
	}
	
	/**
	 * Craie un livre jeu non reli� au paragraphe
	 * un ou les choix et paragraphes son inaccessible 
	 * @return Livre jeu non reli�
	 */
	private GameBook getBookNotLinkedWithPOne() {
		List<Paragraph> p = GameBookFactory.makeParagraphs();
		GameBookFactory.setChoices(p.get(1), "vers p2", p.get(2), p.get(3));
		GameBookFactory.setChoices(p.get(3), "vers p4", p.get(2));
		GameBookFactory.setChoices(p.get(4), "vers p5", p.get(2), p.get(3));
		return new GameBook("EndMoveBook 03", p);
	}
	
	/**
	 * Craie un livre jeu ou tous les paragraphes sont
	 * reli� les un aux-autres.
	 * @return Livre jeu tout reli�
	 */
	private GameBook getBookAllPConnected() {
		List<Paragraph> p = GameBookFactory.makeParagraphs();
		GameBookFactory.setChoices(p.get(0), "vers p1", p.get(0), p.get(1), p.get(2), p.get(3), p.get(4));
		GameBookFactory.setChoices(p.get(1), "vers p2", p.get(0), p.get(1), p.get(2), p.get(3), p.get(4));
		GameBookFactory.setChoices(p.get(2), "vers p3", p.get(0), p.get(1), p.get(2), p.get(3), p.get(4));
		GameBookFactory.setChoices(p.get(3), "vers p4", p.get(0), p.get(1), p.get(2), p.get(3), p.get(4));
		GameBookFactory.setChoices(p.get(4), "vers p5", p.get(0), p.get(1), p.get(2), p.get(3), p.get(4));
		return new GameBook("EndMoveBook 04", p);
	}
	
	/**
	 * Craie un livre jeu vide, par d�finition un livre
	 * jeu ne peux pas �tre vide, le gamebook va donc
	 * g�n�rer un patragraphe factice.
	 * @return Livre jeu vide
	 */
	private GameBook getBookEmpty() {
		return new GameBook("EndMoveBook 05", null); 
	}

}
