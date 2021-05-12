package gamebook.supervisers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;
import gamebook.domains.GameBookStatement;
import gamebook.fakes.FakeCheckView;
import gamebook.fakes.FakeGameBookStatement;

public class CheckSuperviserTests {
	private GameBook book;
	
	private FakeGameBookStatement stmt1, stmt2;
	
	private String swtteTitle, tpfTitle;
	private String swtteDescription, tpfDescription;
	private List<String> swtteResults, tpfResults;
	
	private FakeCheckView view;
	private CheckSuperviser superviser;

	@BeforeEach
	public void setup() {
		book = GameBookFactory.makeGameBook();
		view = new FakeCheckView();
		
		// STMT1
		swtteTitle = "Chemin terminale le plus rapide";
		swtteDescription = "Le livre 'Titre' traverse %2 § pour atteindre le %2.";
		swtteResults = List.of(
			"Le §1 : Paragraphe n1...",
			"Le §2 : Paragraphe n2..."
		);
		stmt1 = new FakeGameBookStatement(swtteTitle, swtteDescription, swtteResults);
		
		// STMT2
		tpfTitle = "Nombre d'apparition des paragraphes dans les choix";
		tpfDescription = "Relève pour le livre 'Titre' le nombre de fois que chaque § figure dans une destination.";
		tpfResults = List.of(
			"Le §1 apparaît (0x) : Paragraphe n1...",
			"Le §2 apparaît (3x) : Paragraphe n2...",
			"Le §3 apparaît (1x) : Paragraphe n3...",
			"Le §4 apparaît (2x) : Paragraphe n4...",
			"Le §5 apparaît (2x) : Paragraphe n5..."
		);
		stmt2 = new FakeGameBookStatement(tpfTitle, tpfDescription, tpfResults);
		
		superviser = new CheckSuperviser(book, stmt1, stmt2);
		superviser.setView(view);
	}
	
	@Test
	public void implementsBookEditedEventHandler() {
		assertTrue(superviser instanceof BookEditedEventHandler);
	}

	///IT-3.1 : Test d'acceptation 1
	@Test
	public void asksTheStatementsToParseBookOnViewSet() {
		verifyStatementRefreshed();
	}
	
	///IT-3.1 : Test d'acceptation 1
	@Test
	public void refreshesViewOnViewSet() {
		verifyViewRefreshedForStatement(stmt1);
		verifyViewRefreshedForStatement(stmt2);
	}
	
	///IT-3.1 : Test d'acceptation 2
	@Test
	public void updatesStatementsOnBookEdited() {
		view.resetCallsTrace();
		
		stmt1.resetCallsTrace();
		stmt2.resetCallsTrace();
		
		superviser.onBookEdited();
		
		verifyStatementRefreshed();
	}


	///IT-3.1 : Test d'acceptation 2
	@Test
	public void refreshesViewOnBookEdited() {
		view.resetCallsTrace();
		
		stmt1.resetCallsTrace();
		stmt2.resetCallsTrace();
		
		superviser.onBookEdited();
		
		view.verify("clearResult");
		verifyViewRefreshedForStatement(stmt1);
		verifyViewRefreshedForStatement(stmt2);
	}
	///IT-3.1 : Test d'acceptation 3
	@Test
	public void refreshesViewWithNoResultMessageOnEmptyResult() {
		view.resetCallsTrace();
		
		stmt1.resetCallsTrace();
		stmt2.resetCallsTrace();
		
		superviser.onBookEdited();
		
		view.verify("addResultItem", "Aucun resultat");
	}
	///IT-3.1 : Test d'acceptation 4
	@Test
	public void updatesStatementsOnParse() {
		view.resetCallsTrace();
		
		stmt1.resetCallsTrace();
		stmt2.resetCallsTrace();
		
		superviser.onParse();
		
		verifyStatementRefreshed();
	}
	///IT-3.1 : Test d'acceptation 4
	@Test
	public void refreshesViewOnParse() {
		view.resetCallsTrace();
		
		stmt1.resetCallsTrace();
		stmt2.resetCallsTrace();
		
		superviser.onParse();
		
		view.verify("clearResult");
		verifyViewRefreshedForStatement(stmt1);
		verifyViewRefreshedForStatement(stmt2);
	}
	
	private void verifyStatementRefreshed() {
		stmt1.verify("parse", book);
		stmt2.verify("parse", book);
	}
	
	private void verifyViewRefreshedForStatement(GameBookStatement stmt) {
		view.verify("startResultFor", stmt.getTitle());		
		view.verify("setDescription", stmt.getDecription());	
		for(String result : stmt.getResults()) {	
			view.verify("addResultItem", result);
		}
	}
	
}
