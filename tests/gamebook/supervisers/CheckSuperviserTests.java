package gamebook.supervisers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gamebook.domains.BookEditedEventHandler;
import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;
import gamebook.domains.GameBookStatement;
import gamebook.domains.statements.*;
import gamebook.fakes.FakeCheckView;

public class CheckSuperviserTests {
	private GameBook book;
	
	private GameBookStatement stmt1, stmt2;

	private FakeCheckView view;
	private CheckSuperviser superviser;

	@BeforeEach
	public void setup() {
		book = GameBookFactory.makeGameBook();
		view = new FakeCheckView();
		
		//TODO : adaptez le constructeur du CheckSuperviser
		
		stmt1 = new ShortestWayToTheEnd();
		stmt2 = new TargetParagraphFrequency();
		
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
		//TODO : si objets factices heritant de Fake, appeler resetCallsTrace sur ces objets factices.
		
		superviser.onBookEdited();
		
		verifyStatementRefreshed();
	}


	///IT-3.1 : Test d'acceptation 2
	@Test
	public void refreshesViewOnBookEdited() {
		view.resetCallsTrace();
		//TODO : si objets factices heritant de Fake, appeler resetCallsTrace sur ces objets factices.
		
		superviser.onBookEdited();
		
		view.verify("clearResult");
		verifyViewRefreshedForStatement(stmt1);
		verifyViewRefreshedForStatement(stmt2);
	}
	///IT-3.1 : Test d'acceptation 3
	@Test
	public void refreshesViewWithNoResultMessageOnEmptyResult() {
		view.resetCallsTrace();
		//TODO : si objets factices heritant de Fake, appeler resetCallsTrace sur ces objets factices.
		
		superviser.onBookEdited();
		
		view.verify("addResultItem", "Aucun resultat");
	}
	///IT-3.1 : Test d'acceptation 4
	@Test
	public void updatesStatementsOnParse() {
		view.resetCallsTrace();
		//TODO : si objets factices heritant de Fake, appeler resetCallsTrace sur ces objets factices.
		
		superviser.onParse();
		
		verifyStatementRefreshed();
	}
	///IT-3.1 : Test d'acceptation 4
	@Test
	public void refreshesViewOnParse() {
		view.resetCallsTrace();
		//TODO : si objets factices heritant de Fake, appeler resetCallsTrace sur ces objets factices.
		
		superviser.onParse();
		
		view.verify("clearResult");
		verifyViewRefreshedForStatement(stmt1);
		verifyViewRefreshedForStatement(stmt2);
	}
	
	private void verifyStatementRefreshed() {
		//TODO : verifier la reception des appels adequats pour recalculer les releves sur base du gamebook
	}
	
	private void verifyViewRefreshedForStatement(GameBookStatement stmt) {
		view.verify("startResultFor");//TODO : ajouter les appels correspondant au stmt.		
		view.verify("setDescription");//TODO : ajouter les appels correspondant au stmt.		
		for(String result : java.util.List.<String>of()) {//TODO : ajouter les appels correspondant au stmt.		
			view.verify("addResultItem", result);
		}
	}
	
}
