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
 * comptabilise les choix en circuit sans ordre de parcourt prédéfini ormit le fait que la lecture commence
 * toujours au paragraphe un.
 * 
 * Cette classe de test permet de vérifier que tout les cas et type de livre sont bien géré. 
 * 
 * @author Jérémi Nihart
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
		assertEquals(tpf.getDescription(), "Relève pour le livre '"+GameBookFactory.BOOK_TITLE+"' le nombre de fois que chaque § figure dans une destination.");
	}
	
	/**
	 * +++ PLAN DE TEST +++
	 * Ce plan de test comporte 5 tests de 5 livres jeu différent dans 4
	 * situations différentes pour certaine aillant des similitudes.
	 * 
	 * Les tests #01 et #02 vérifient deux livres normaux avec des liaisons pour
	 * chaque paragraphe et un ou plusieur paragraphe final pour chaqu'un d'eux deux
	 * 
	 * Le test #03 permet de vérifier que même en l'absence d'un paragraphe terminal
	 * l'algorithme arrive quand même à s'arreter et à parcourir tout les choix accessibles.
	 * 
	 * Le test #04 permet de vérifier que l'algorithme ne comptabilise pas les paragraphes
	 * hors circuit et que dans le cas ou le livre fini au paragraphe un, il s'arrête.
	 * 
	 * Le test #05 permet de vérifier que l'algorithme ne boucle pas infiniment dans le
	 * cas ou tout les paragraphes pointent sur tout les paragraphe et que le livre jeu ne
	 * possède pas de fin définie.
	 * 
	 * Le test #06 permet de vérifie qu'il n'y a pas d'erreur dans le cas ou un livre jeu est vide
	 * ou null.
	 */
	
	/**
	 * Test #01 d'un cas normal et ses résultats avec le livre
	 * de base du programme.
	 */
	@Test
	public void normalBookResultsAndParseCheck() {
		tpf.parse(getBookDefault());
		List<String> expectedResults = List.of(
				"Le §1 apparaît (0x) : " + GameBookFactory.P1_CONTENT,
				"Le §2 apparaît (3x) : " + GameBookFactory.P2_CONTENT,
				"Le §3 apparaît (1x) : " + GameBookFactory.P3_CONTENT,
				"Le §4 apparaît (2x) : " + GameBookFactory.P4_CONTENT,
				"Le §5 apparaît (2x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #02 d'un autre cas normal et ses résultats avec un
	 * livre jeu classic.
	 */
	@Test
	public void anOtherNormalBookResultsAndParseCheck() {
		tpf.parse(getBookNormal());
		List<String> expectedResults = List.of(
				"Le §1 apparaît (1x) : " + GameBookFactory.P1_CONTENT,
				"Le §2 apparaît (2x) : " + GameBookFactory.P2_CONTENT,
				"Le §3 apparaît (1x) : " + GameBookFactory.P3_CONTENT,
				"Le §4 apparaît (1x) : " + GameBookFactory.P4_CONTENT,
				"Le §5 apparaît (1x) : " + GameBookFactory.P5_CONTENT
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
				"Le §1 apparaît (2x) : " + GameBookFactory.P1_CONTENT,
				"Le §2 apparaît (1x) : " + GameBookFactory.P2_CONTENT,
				"Le §3 apparaît (1x) : " + GameBookFactory.P3_CONTENT,
				"Le §4 apparaît (1x) : " + GameBookFactory.P4_CONTENT,
				"Le §5 apparaît (0x) : " + GameBookFactory.P5_CONTENT
				// Le p5 ne fait pas partie du circuit du livre par définition
				// un paragraphe hors circuit ne doit pas être comptabilisé.
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #04 d'un cas d'erreurs et cas limite, livre ou le paragraphe un n'est
	 * pas relié aux autres et ou il est terminal.
	 */
	@Test
	public void notLinkedWithPOneBookResultsAndParseCheck() {
		tpf.parse(getBookNotLinkedWithPOne());
		List<String> expectedResults = List.of(
				"Le §1 apparaît (0x) : " + GameBookFactory.P1_CONTENT,
				"Le §2 apparaît (0x) : " + GameBookFactory.P2_CONTENT,
				"Le §3 apparaît (0x) : " + GameBookFactory.P3_CONTENT,
				"Le §4 apparaît (0x) : " + GameBookFactory.P4_CONTENT,
				"Le §5 apparaît (0x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #05 d'un cas avec un surplus de combinaisons et possibilitées.
	 */
	@Test
	public void allPConnectedBookResultsAndParseCheck() {
		tpf.parse(getBookAllPConnected());
		List<String> expectedResults = List.of(
				"Le §1 apparaît (5x) : " + GameBookFactory.P1_CONTENT,
				"Le §2 apparaît (5x) : " + GameBookFactory.P2_CONTENT,
				"Le §3 apparaît (5x) : " + GameBookFactory.P3_CONTENT,
				"Le §4 apparaît (5x) : " + GameBookFactory.P4_CONTENT,
				"Le §5 apparaît (5x) : " + GameBookFactory.P5_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * Test #06 d'un cas ou le livre jeu est vide.
	 */
	@Test
	public void emptyBookResultsAndParseCheck() {
		// livre jeu déjà crée
		tpf.parse(getBookEmpty());
		List<String> expectedResults = List.of(
				"Le §1 apparaît (0x) : " + Paragraph.PARAGRAPH_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
		// livre jeu null
		tpf.parse(null);
		expectedResults = List.of(
				"Le §1 apparaît (0x) : " + Paragraph.PARAGRAPH_CONTENT
		);
		checkResults(tpf.getResults(), expectedResults);
	}
	
	/**
	 * +++ METHODES UTILITAIRES +++
	 */
	
	/**
	 * Méthode de vérification des résultats.
	 * @param result Résultat.
	 * @param expectedResult Résultat attendu par l'algorithme.
	 */
	private void checkResults(List<String> results, List<String> expectedResults) {
		if (results.size() == expectedResults.size()) {
			for (int i = 0; results.size() > i; i++) {
				assertEquals(results.get(i), expectedResults.get(i));
			}
		} else {
			fail("Les résultats ne correspondent pas !");
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
	 * Craie un livre jeu non relié au paragraphe
	 * un ou les choix et paragraphes son inaccessible 
	 * @return Livre jeu non relié
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
	 * relié les un aux-autres.
	 * @return Livre jeu tout relié
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
	 * Craie un livre jeu vide, par définition un livre
	 * jeu ne peux pas être vide, le gamebook va donc
	 * générer un patragraphe factice.
	 * @return Livre jeu vide
	 */
	private GameBook getBookEmpty() {
		return new GameBook("EndMoveBook 05", null); 
	}

}
