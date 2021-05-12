package gamebook.supervisers;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gamebook.domains.GameBook;
import gamebook.domains.GameBookFactory;
import gamebook.domains.Session;
import gamebook.fakes.FakeReaderView;

public class ReadSuperviserTests {
	private ReadSuperviser superviser;
	private FakeReaderView view;
	
	@BeforeEach
	public void setup() {
		GameBook b = GameBookFactory.makeGameBook();
		Session s = new Session(b);
		superviser = new ReadSuperviser(s, b);
		view = new FakeReaderView();
		superviser.setView(view);
	}
	
	//AI-1.1 : Test d'acceptation 1
	@Test
	public void refreshesHisViewOnNewViewSet() {
		//EXPECT : quand le superviseur reçoit sa vue, il lui fournit les données du §1.
		view.verify("setTitle", GameBookFactory.BOOK_TITLE);
		view.verify("setParagraph", "Paragraphe 1", GameBookFactory.P1_CONTENT);
		view.verifyInAnyOrder("setChoices", List.of(GameBookFactory.LOOK_BEHIND, GameBookFactory.KEEP_READING, GameBookFactory.WALK));
	}
	
	//AI-1.1 : Test d'acceptation 2
	@Test
	public void switchesOfParagraphsOnChoiceSelected() {
		//GIVEN : §1 et ses choix sont affichés
		//WHEN : l'utilisateur choisit de se dégourdir les jambes
		superviser.onChoiceSelected(GameBookFactory.WALK);
		//THEN : le superviseur met à jour sa vue avec le contenu de §4
		view.verify("setParagraph", "Paragraphe 4", GameBookFactory.P4_CONTENT);
		view.verifyInAnyOrder("setChoices",List.of(GameBookFactory.FOLLOW, GameBookFactory.IGNORE));
	}
	
	//AI-1.1 : Test d'acceptation 3
	@Test
	public void switchesOnSeveralChoicesSelected() {
		//GIVEN : §1 et ses choix sont affichés
		//WHEN  : l'utilisateur passe de §1 -> §4, puis choisit de "Suivre cet individu"
		superviser.onChoiceSelected(GameBookFactory.WALK);
		superviser.onChoiceSelected(GameBookFactory.FOLLOW);
		//THEN : le superviseur met à jour sa vue avec le contenu de §5
		view.verify("setParagraph", "Paragraphe 5", GameBookFactory.P5_CONTENT);
	}
	
	//AI-1.2 : Test d'acceptation 1
	@Test
	public void reachesTheEnd() {
		//WHEN : l'utilisateur passe de §1 -> §2 (§ de fin)
		superviser.onChoiceSelected(GameBookFactory.KEEP_READING);
		//THEN : le superviseur propose de recommencer la lecture
		view.verify("setChoices", List.of("Recommencer l'aventure"));
	}
	
	//AI-1.2 : Test d'acceptation 2
	@Test
	public void goesBackToFirstParagraphOnRestart() {
		//GIVEN: une trace des appels vidée
		view.resetCallsTrace();
		//WHEN : l'utilisateur passe de §1 -> §2, puis recommence la lecture
		superviser.onChoiceSelected(GameBookFactory.KEEP_READING);
		superviser.onChoiceSelected(ReadSuperviser.RESTART_KEY);
		//THEN : le superviseur met à jour sa vue avec le contenu de §1
		view.verify("setParagraph", "Paragraphe 1", GameBookFactory.P1_CONTENT);
		view.verifyInAnyOrder("setChoices", List.of(GameBookFactory.LOOK_BEHIND, GameBookFactory.KEEP_READING, GameBookFactory.WALK));
	}
	
	//AI-1.3 : Test d'acceptation 1
	@Test
	public void doesNotGoBackOnDisplayingFirstNode() {
		//GIVEN: une trace des appels vidée
		view.resetCallsTrace();
		//WHEN: l'utilisateur souhaite revenir en arrière
		superviser.onGoBack();
		//THEN : le superviseur met à jour sa vue avec le contenu de §1
		view.verify("setParagraph", "Paragraphe 1", GameBookFactory.P1_CONTENT);
		view.verifyInAnyOrder("setChoices", List.of(GameBookFactory.LOOK_BEHIND, GameBookFactory.KEEP_READING, GameBookFactory.WALK));
	}
	
	//AI-1.3 : Test d'acceptation 2
	@Test
	public void goesOneParagraphBackAtTheTime() {
		//GIVEN : l'utilisateur fait le parcours §1 -> §4 -> §5
		superviser.onChoiceSelected(GameBookFactory.WALK);
		superviser.onChoiceSelected(GameBookFactory.FOLLOW);
		//WHEN : on efface la trace des appels avant de revenir en arrière 1 fois
		view.resetCallsTrace();
		superviser.onGoBack();
		//THEN : le superviseur met à jour sa vue avec le contenu de §4
		view.verify("setParagraph", "Paragraphe 4", GameBookFactory.P4_CONTENT);
		view.verifyInAnyOrder("setChoices",List.of(GameBookFactory.FOLLOW, GameBookFactory.IGNORE));
	}
	
	//AI-1.3 : Test d'acceptation 3
	@Test
	public void goesToTheStart() {
		//GIVEN : l'utilisateur fait le parcours §1 -> §4 -> §5
		superviser.onChoiceSelected(GameBookFactory.WALK);
		superviser.onChoiceSelected(GameBookFactory.FOLLOW);
		//WHEN : on efface la trace des appels avant de revenir en arrière 2 fois
		view.resetCallsTrace();
		superviser.onGoBack();
		superviser.onGoBack();
		//THEN : le superviseur met à jour sa vue avec le contenu de §4
		view.verify("setParagraph", "Paragraphe 1", GameBookFactory.P1_CONTENT);
		view.verifyInAnyOrder("setChoices", List.of(GameBookFactory.LOOK_BEHIND, GameBookFactory.KEEP_READING, GameBookFactory.WALK));
	}
}
