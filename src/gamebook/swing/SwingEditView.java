package gamebook.swing;

import javax.swing.*;

import static gamebook.swing.Theme.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;

import gamebook.supervisers.*;
import gamebook.swing.renderers.WrappedListCellRenderer;
/**
 * Implémente la vue d'édition en Swing.
 * */
@SuppressWarnings("serial")
public final class SwingEditView extends JPanel implements EditView {
	private final EditSuperviser superviser;
	
	private final JTextField bookTitle = new JTextField("Le titre du livre vient ici");
	{
		bookTitle.setForeground(PRIMARY_COLOR);
		bookTitle.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Theme.PRIMARY_COLOR));
		bookTitle.setFont(TITLE_FONT);
		bookTitle.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				bookTitle.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Theme.PRIMARY_COLOR));
				updateTitle();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				bookTitle.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Theme.PRIMARY_COLOR));
			}
		} );
	}
	
	private final JTextArea curParagraphTxt = new JTextArea("Le contenu du paragraphe vient ici !",5, 24);
	{
		curParagraphTxt.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Theme.PRIMARY_COLOR));
		curParagraphTxt.setFont(Theme.NORMAL_FONT);
		curParagraphTxt.setLineWrap(true);
		
		curParagraphTxt.addFocusListener(new FocusListener() {
			int paragraphIndex;
			
			@Override
			public void focusLost(FocusEvent e) {
				curParagraphTxt.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Theme.PRIMARY_COLOR));
				updateParagraphContent(paragraphIndex);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				paragraphIndex = paragraphsCb.getSelectedIndex();
				curParagraphTxt.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Theme.PRIMARY_COLOR));
			}
		});
	}
	
	private final JTextField choiceTf = new JTextField("Le contenu du choix courant vient ici !");
	{
		choiceTf.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Theme.PRIMARY_COLOR));
		choiceTf.setFont(Theme.NORMAL_FONT);
		choiceTf.addFocusListener(new FocusListener() {
			String oldKey;
			
			@Override
			public void focusLost(FocusEvent e) {
				choiceTf.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Theme.PRIMARY_COLOR));
				updateChoice(oldKey);
			}

			@Override
			public void focusGained(FocusEvent e) {
				choiceTf.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Theme.PRIMARY_COLOR));
				oldKey = choiceTf.getText();
			}
		} );
	}
	
	private final JComboBox<String> paragraphsCb = new JComboBox<>();
	{
		paragraphsCb.setRenderer(new WrappedListCellRenderer(Theme.BACKGROUND_COLOR, Theme.PRIMARY_COLOR));
		paragraphsCb.setModel(new DefaultComboBoxModel<>());
		paragraphsCb.setBorder(new LineBorder(new Color(0, 0, 0)));
		paragraphsCb.setFont(Theme.NORMAL_FONT);
		paragraphsCb.addItemListener((evt) -> {
			updateSelectedParagraph();
		});
	}
	
	private final JComboBox<String> choicesCb = new JComboBox<>();
	{
		choicesCb.setRenderer(new WrappedListCellRenderer(Theme.BACKGROUND_COLOR, Theme.PRIMARY_COLOR));
		choicesCb.setModel(new DefaultComboBoxModel<>());
		choicesCb.setBorder(new LineBorder(new Color(0, 0, 0)));
		choicesCb.setFont(Theme.NORMAL_FONT);
		choicesCb.addItemListener((evt) -> {
			updateSelectedChoice();
		});
	}
	
	private final JComboBox<String> targetsCb = new JComboBox<>();
	{
		targetsCb.setRenderer(new WrappedListCellRenderer(Theme.BACKGROUND_COLOR, Theme.PRIMARY_COLOR));
		targetsCb.setModel(new DefaultComboBoxModel<>());
		targetsCb.addItemListener((evt) -> {
			updateChoiceTarget();
		});
	}
	
	/**
	 * Create the edit view.
	 * @param superviser 
	 */
	public SwingEditView(EditSuperviser superviser) {
		setBackground(Theme.BACKGROUND_COLOR);
		setSize(Theme.PANEL_WIDTH, Theme.PANEL_HEIGHT);
		setFont(Theme.NORMAL_FONT);
		setLayout(new MigLayout("fillx","[fill]"));
		
		add(bookTitle, "span 3, wrap");
		
		setupParPanel();
		setupChoicePanel();
		
		this.superviser = superviser;
		this.superviser.setView(this);
	}

	private void setupParPanel() {
		JLabel paragraphLbl = new JLabel("Paragraphes");
		paragraphLbl.setFont(Theme.SUBTITLE_FONT);
		paragraphLbl.setForeground(Theme.PRIMARY_COLOR);
		add(paragraphLbl);
		
		add(paragraphsCb, "width 128:256:");
		
		JButton removeButton = newButton("Supprimer", evt -> superviser.onDeleteParagraph(paragraphsCb.getSelectedIndex()));
		add(removeButton, "split 2, width 96:96:96");
		
		JButton addButton = newButton("Ajouter", evt -> superviser.onNewParagraph());
		add(addButton, "width 96:96:96, wrap");
		
		add(curParagraphTxt, "span 3, width 128:256:, wrap");
	}
	
	private void setupChoicePanel() {
		JLabel curChcSectionLbl = new JLabel("Choix");
		curChcSectionLbl.setFont(Theme.SUBTITLE_FONT);
		curChcSectionLbl.setForeground(Theme.PRIMARY_COLOR);
		add(curChcSectionLbl, "");
		
		add(choicesCb,  "width 128:256:");

		JButton removeButton = newButton("Supprimer", evt -> superviser.onDeleteChoice((String)choicesCb.getSelectedItem()));
		add(removeButton, "split 2, width 96:96:96, left");
		
		JButton addButton = newButton("Ajouter",evt -> superviser.onNewChoice());
		add(addButton, "width 96:96:96, wrap");
		
		add(choiceTf, "span 3, width 128:256:, wrap");
		
		add(targetsCb, " span 2, wrap");
	}
	
	private JButton newButton(String label, ActionListener onAction) {
		JButton button = new JButton(label);
		button.addActionListener(onAction);
		button.setFont(NORMAL_FONT);
		return button;
	}

	@Override
	public void setParagraphs(List<String> paragraphs) {
		replaceContent((DefaultComboBoxModel<String>)paragraphsCb.getModel(), paragraphs);
		replaceContent((DefaultComboBoxModel<String>)targetsCb.getModel(), paragraphs);
	}
	
	private void replaceContent(DefaultComboBoxModel<String> model, Collection<String> newContent) {
		model.removeAllElements();
		model.addAll(newContent);
	}
	
	@Override
	public void setCurrentParagraphContent(String content) {
		if(content.equals(this.curParagraphTxt.getText())) {
			return;
		}
		if(!curParagraphTxt.hasFocus()) {
			SwingUtilities.invokeLater(()-> this.curParagraphTxt.setText(content));
		}
	}

	@Override
	public void setTitle(String newTitle) {
		if(newTitle == null || newTitle.equals(bookTitle.getText())) {
			return;
		}
		
		this.bookTitle.setText(newTitle);
	}

	@Override
	public void setSelectedParagraph(int index) {
		if(index < 0 ||  index >= paragraphsCb.getItemCount()) {
			return;
		}
		if(index == paragraphsCb.getSelectedIndex()) {
			return;
		}
		
		paragraphsCb.setSelectedIndex(index);
	}

	@Override
	public void setChoices(Collection<String> choices) {
		replaceContent((DefaultComboBoxModel<String>)choicesCb.getModel(),choices);
	}

	@Override
	public void setSelectedChoice(String key, int targetIndex) {
		if(key != null && !key.equals(choicesCb.getSelectedItem())) {
			choicesCb.setSelectedItem(key);
		}
		
		if(key != null && !key.equals(choiceTf.getText())) {
			choiceTf.setText(key);
		}
		
		if(targetIndex != targetsCb.getSelectedIndex()) {
			targetsCb.setSelectedIndex(targetIndex);
		}
	}

	private void updateSelectedParagraph() {
		if(paragraphsCb.getSelectedIndex() >= 0) {
			superviser.onSelectedParagraphChanged(paragraphsCb.getSelectedIndex());
		}
	}
	
	private void updateSelectedChoice() {
		superviser.onSelectedChoiceChanged((String)choicesCb.getSelectedItem());
	}
	
	private void updateTitle() {
		superviser.onTitleChanged(bookTitle.getText());
	}
	
	private void updateParagraphContent(int index) {
		if(index >= 0) {
			superviser.onParagraphContentChanged(index, curParagraphTxt.getText());
		}
	}
	
	private void updateChoice(String oldKey) {
		superviser.onChoiceLabelChanged(oldKey, choiceTf.getText());
	}
	
	private void updateChoiceTarget() {
		if(targetsCb.getSelectedIndex() >= 0) {
			superviser.onChoiceTargetChanged((String)choicesCb.getSelectedItem(), targetsCb.getSelectedIndex());
		}
	}

}
