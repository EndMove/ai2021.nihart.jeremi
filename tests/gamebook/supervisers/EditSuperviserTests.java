package gamebook.supervisers;

import java.util.List;

import org.junit.jupiter.api.*;

import gamebook.domains.*;
import gamebook.fakes.*;

import static gamebook.utils.MethodCall.of;

public class EditSuperviserTests {
	private EditSuperviser superviser;
	private FakeBookEditedEventHandler handler;
	private FakeEditView view;
	private GameBook book;
	
	@BeforeEach
	public void setup() {
		book = GameBookFactory.makeGameBook();
		view = new FakeEditView();
		handler = new FakeBookEditedEventHandler();
		superviser = new EditSuperviser(book, handler);
		
		superviser.setView(view);
	}
	
	//AI-2.1 : Test d'acceptation 1
	@Test
	public void refreshesHisViewOnNewViewSet() {
		view.verify("setTitle", GameBookFactory.BOOK_TITLE);
		view.verify("setParagraphs", List.of(
				GameBookFactory.P1_CONTENT,
				GameBookFactory.P2_CONTENT,
				GameBookFactory.P3_CONTENT,
				GameBookFactory.P4_CONTENT,
				GameBookFactory.P5_CONTENT
		));
		
		view.verify("setSelectedParagraph", 0);
		view.verifyInAnyOrder("setChoices", List.of(
				GameBookFactory.LOOK_BEHIND,
				GameBookFactory.KEEP_READING,
				GameBookFactory.WALK
		));
		
		view.verifyAny(
				of("setSelectedChoice", GameBookFactory.LOOK_BEHIND, 2),
				of("setSelectedChoice", GameBookFactory.KEEP_READING, 1),
				of("setSelectedChoice", GameBookFactory.WALK, 3)
		);

	}
	
	//AI-2.1 : Test d'acceptation 2
	@Test
	public void updatesTitle() {
		superviser.onTitleChanged("This is a new title");
		
		handler.verify("onBookEdited");
	}
	
	//AI-2.1 : Test d'acceptation 3
	@Test
	public void ignoresBlankTitle() {
		view.resetCallsTrace();
		
		superviser.onTitleChanged(" ");
		
		view.verify("setTitle", GameBookFactory.BOOK_TITLE);
		handler.verifyNoCall("onBookEdited");
	}
	
	//AI-2.2 : Test d'acceptation 1
	@Test
	public void refreshesChoicesOnSelectedParagraphChanged() {
		view.resetCallsTrace();
		
		superviser.onSelectedParagraphChanged(2);
		
		view.verify("setCurrentParagraphContent", GameBookFactory.P3_CONTENT);
		view.verifyInAnyOrder("setChoices", List.of(
				GameBookFactory.FOLLOW, GameBookFactory.IGNORE, GameBookFactory.WALK	
		));
		view.verifyAny(
				of("setSelectedChoice", GameBookFactory.FOLLOW, 4),
				of("setSelectedChoice", GameBookFactory.IGNORE, 1),
				of("setSelectedChoice", GameBookFactory.WALK, 3)
		);
	}
	
	//AI-2.2 : Test d'acceptation 2
	@Test
	public void refreshesChoicesOnParagraphWithNoChoiceSelected() {
		view.resetCallsTrace();
		
		superviser.onSelectedParagraphChanged(1);
		
		view.verify("setCurrentParagraphContent", GameBookFactory.P2_CONTENT);
		view.verifyInAnyOrder("setChoices", List.of());
		view.verify("setSelectedChoice", "", -1);
	}
	
	//AI-2.2 : Test d'acceptation 3
	@Test
	public void addsParagraphAtTheEnd() {
		view.resetCallsTrace();
		
		superviser.onNewParagraph();
		
		view.verify("setParagraphs", List.of(
				GameBookFactory.P1_CONTENT,
				GameBookFactory.P2_CONTENT,
				GameBookFactory.P3_CONTENT,
				GameBookFactory.P4_CONTENT,
				GameBookFactory.P5_CONTENT,
				"Nouveau paragraphe"
		));
		view.verify("setCurrentParagraphContent", "Nouveau paragraphe");
		view.verify("setSelectedParagraph", 5);
	}
	
	//AI-2.2 : Test d'acceptation 4
	@Test
	public void removesSelectedParagraph() {
		view.resetCallsTrace();
		
		superviser.onDeleteParagraph(3);
		
		view.verify("setParagraphs", List.of(
				GameBookFactory.P1_CONTENT,
				GameBookFactory.P2_CONTENT,
				GameBookFactory.P3_CONTENT,
				GameBookFactory.P5_CONTENT
		));
		
		view.verify("setCurrentParagraphContent", GameBookFactory.P5_CONTENT);
		view.verify("setSelectedParagraph", 3);
		handler.verify("onBookEdited");
	}
	
	//AI-2.2 : Test d'acceptation 5
	@Test
	public void ignoresRemovalOnOneParagraphLeft() {		
		//GIVEN : un superviser et un livre ou on a supprimé 4§
		superviser.onDeleteParagraph(1);
		superviser.onDeleteParagraph(1);
		superviser.onDeleteParagraph(1);
		superviser.onDeleteParagraph(1);
		
		//WHEN : j'essaie de supprimer le dernier §
		view.resetCallsTrace();
		handler.resetCallsTrace();
		superviser.onDeleteParagraph(1);
		
		//THEN : cette demande est ignorée
		view.verifyNoCall("setSelectedParagraph", 1);
		handler.verifyNoCall("onBookEdited");
	}
	
	//AI-2.2 : Test d'acceptation 6
	@Test
	public void updatesParagraphContent() {
		view.resetCallsTrace();
		
		superviser.onParagraphContentChanged(0, "New content");
		
		view.verify("setParagraphs", List.of(
				"New content",
				GameBookFactory.P2_CONTENT,
				GameBookFactory.P3_CONTENT,
				GameBookFactory.P4_CONTENT,
				GameBookFactory.P5_CONTENT
		));
		handler.verify("onBookEdited");
	}
	
	//AI-2.2 : Test d'acceptation 7
	@Test
	public void ignoresBlankParagraphContent() {
		view.resetCallsTrace();
		
		superviser.onParagraphContentChanged(0, " ");
		
		view.verify("setCurrentParagraphContent", GameBookFactory.P1_CONTENT);
		handler.verifyNoCall("onBookEdited");
	}
	
	//AI-2.3 : Test d'acceptation 1
	@Test
	public void updatesOnSelectedChoiceChanged() {
		view.resetCallsTrace();

		superviser.onSelectedChoiceChanged(GameBookFactory.WALK);
		
		view.verify("setSelectedChoice", GameBookFactory.WALK, 3);
	}
	
	//AI-2.3 : Test d'acceptation 2
	@Test
	public void addsChoices() {
		view.resetCallsTrace();
		
		superviser.onNewChoice();
		
		view.verifyInAnyOrder("setChoices", List.of(
				GameBookFactory.LOOK_BEHIND,
				GameBookFactory.KEEP_READING,
				GameBookFactory.WALK,
				"Nouveau choix"
		));
		view.verify("setSelectedChoice", "Nouveau choix", 0);
		handler.verify("onBookEdited");
	}
	
	//AI-2.3 : Test d'acceptation 3
	@Test
	public void removesChoices() {
		view.resetCallsTrace();
		
		superviser.onDeleteChoice(GameBookFactory.KEEP_READING);
		
		view.verifyInAnyOrder("setChoices", List.of(
				GameBookFactory.LOOK_BEHIND,
				GameBookFactory.WALK
		));
		handler.verify("onBookEdited");
	}
	
	
	//AI-2.3 : Test d'acceptation 4
	@Test
	public void editsChoiceLabel() {
		view.resetCallsTrace();
		
		superviser.onChoiceLabelChanged(GameBookFactory.KEEP_READING, "Je ne lis plus");
		
		view.verifyInAnyOrder("setChoices", List.of(
				GameBookFactory.LOOK_BEHIND,
				"Je ne lis plus",
				GameBookFactory.WALK
		));
		view.setSelectedChoice("Je ne lis plus", 1);
		handler.verify("onBookEdited");
	}
	
	//AI-2.3 : Test d'acceptation 5
	@Test
	public void ignoreBlankLabel() {
		view.resetCallsTrace();
		
		superviser.onChoiceLabelChanged(GameBookFactory.KEEP_READING, "");
		
		view.verifyNoCall("setChoices", List.of(
				GameBookFactory.LOOK_BEHIND,
				"Je ne lis plus",
				GameBookFactory.WALK
		));
		view.verify("setSelectedChoice",GameBookFactory.KEEP_READING, 1);
		handler.verifyNoCall("onBookEdited");
	}
	
	//AI-2.3 : Test d'acceptation 6
	@Test
	public void updatesChoiceTarget() {
		view.resetCallsTrace();
		
		superviser.onChoiceTargetChanged(GameBookFactory.KEEP_READING, 0);
		
		handler.verify("onBookEdited");
	}
}
