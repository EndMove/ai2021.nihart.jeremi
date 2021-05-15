package gamebook.domains.statements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;

class ShortestWayToTheEndTest {
	
	private ShortestWayToTheEnd swtte;
	
	@BeforeEach
	public void ini() {
		swtte = new ShortestWayToTheEnd();
	}

	@Test
	void parseAndResults() {
		// Test du livre jeu du programme.
		swtte.parse(getBookDefault());
		List<String> expectedResults = List.of(
				"Le §1 : " + GameBookFactory.P1_CONTENT,
				"Le §2 : " + GameBookFactory.P2_CONTENT
		);
		checkResults(swtte.getResults(), expectedResults);
		// Test livre jeu null.
		swtte.parse(null);
		expectedResults = List.of(
				"Aucun resultat"
		);
		checkResults(swtte.getResults(), expectedResults);
	}

	@Test
	void getTitle() {
		assertEquals(swtte.getTitle(), "Chemin terminale le plus rapide");
	}

	@Test
	void getDecription() {
		// Test du livre jeu du programme.
		swtte.parse(getBookDefault());
		assertEquals(swtte.getDescription(), "Le livre '"+GameBookFactory.BOOK_TITLE+"' traverse 2 § pour atteindre le 2.");
		// Test livre jeu vide.
		swtte.parse(null);
		assertEquals(swtte.getDescription(), "Le livre '"+GameBook.BOOK_TITLE+"' traverse 0 § pour atteindre le 0.");
	}
	
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
	 * Craie le livre jeu de base du programme.
	 * @return Livre jeu de base du programme.
	 */
	private GameBook getBookDefault() {
		return GameBookFactory.makeGameBook();
	}

}
