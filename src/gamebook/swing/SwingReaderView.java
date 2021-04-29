package gamebook.swing;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.*;

import gamebook.supervisers.*;
import net.miginfocom.swing.MigLayout;

import static gamebook.swing.Theme.*;

/**
 * Implémente la vue de lecture en Swing.
 * */
@SuppressWarnings("serial")
public final class SwingReaderView extends JPanel implements ReadView {
	private final JButton goBackBtn = new JButton("<-");
	{
		goBackBtn.setFont(NORMAL_FONT);
		goBackBtn.setSize(48,48);
		goBackBtn.addActionListener(evt -> notifyGoBack());
	}
	
	private final JLabel bookTitleLbl = new JLabel();
	{
		bookTitleLbl.setFont(TITLE_FONT);
		bookTitleLbl.setForeground(PRIMARY_COLOR);
		bookTitleLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PRIMARY_COLOR));
		bookTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private final ParagraphView paragraphView = new ParagraphView();
	
	private final JPanel choicesPnl = new JPanel();
	{
		choicesPnl.setLayout(new MigLayout("fillx", "[fill]"));
	}
	
	private final ReadSuperviser superviser;
	
	
	/**
	 * Create the panel (use it while building the real system).
	 */
	public SwingReaderView(ReadSuperviser superviser) {
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setBackground(BACKGROUND_COLOR);
		setLayout(new MigLayout("fillx","[fill]"));
	
		add(bookTitleLbl, "span");
		add(goBackBtn,"split 2");	
		add(paragraphView,"grow, wrap");
		add(choicesPnl, "growx, growy");
		
		this.superviser = superviser;
		superviser.setView(this);
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setTitle(String bookTitle) {
		bookTitleLbl.setText(bookTitle);
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setParagraph(String head, String content) {
		this.paragraphView.setParagraph(head, content);
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setChoices(Collection<String> choices) {
		choicesPnl.removeAll();
		for(String choice : choices) {
			choicesPnl.add(newButton(choice), "growx, span");
		}
	}
	
	private JButton newButton(String text) {
		JButton btnNewButton = new JButton(text);
		btnNewButton.setActionCommand(text);
		btnNewButton.setFont(NORMAL_FONT);
		btnNewButton.addActionListener(this::notifyChoiceSelected);
		return btnNewButton;
	}
	
	private void notifyChoiceSelected(ActionEvent evt) {
		if(superviser != null) {
			superviser.onChoiceSelected(evt.getActionCommand());
		}
	}
	
	private void notifyGoBack() {
		if(superviser != null) {
			superviser.onGoBack();
		}
	}

	/**
	 * Représente la paire (en-tête/contenu) d'un paragraphe.
	 * */
	private static class ParagraphView extends JPanel {
		private final JLabel paragraphHeadLbl = new JLabel();
		{
			paragraphHeadLbl.setForeground(PRIMARY_COLOR);
			paragraphHeadLbl.setFont(SUBTITLE_FONT);
		}
		
		private final JTextPane paragraphPn = new JTextPane();
		{
			paragraphPn.setEditable(false);
			paragraphPn.setFont(NORMAL_FONT);
		}
		
		/**
		 * Construit une vue d'un paragraphe vide.
		 * */
		public ParagraphView() {
			setLayout(new MigLayout("fillx","[fill]"));
			setBackground(Theme.BACKGROUND_COLOR);
			
			add(paragraphHeadLbl,"growx, wrap");
			add(paragraphPn,"growx, wrap");
		}
		
		/**
		 * Définit l'en-tête et le contenu du paragraphe.
		 * */
		public void setParagraph(String head, String content) {
			this.paragraphHeadLbl.setText(head);
			this.paragraphPn.setText(content);
		}
	}
}
