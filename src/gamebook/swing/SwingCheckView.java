package gamebook.swing;

import javax.swing.*;

import gamebook.supervisers.*;
import gamebook.swing.renderers.WordWrapper;
import net.miginfocom.swing.MigLayout;

import static gamebook.swing.Theme.*;

/**
 * Implémente la vue de vérification en Swing.
 * */
@SuppressWarnings("serial")
public class SwingCheckView extends JPanel implements CheckView {
	private final JLabel titleTf = new JLabel("Analyse du livre Toto");
	{
		titleTf.setForeground(PRIMARY_COLOR);
		titleTf.setFont(TITLE_FONT);
	}
	
	private final JPanel resultsPanel = new JPanel();
	{
		resultsPanel.setLayout(new MigLayout("fill","[]","[nogrid]"));
		resultsPanel.setBackground(Theme.BACKGROUND_COLOR);
	}
	
	private JPanel resultPanel = new JPanel();

	
	private final JButton parseBtn = new JButton("Analyser");

	/**
	 * Construit une instance de la vue à l'aide de son {@code superviser}.
	 * */
	public SwingCheckView(CheckSuperviser superviser) {
		setBackground(Theme.BACKGROUND_COLOR);
		setLayout(new MigLayout("fillx"));
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		
		add(titleTf, "grow");
		add(parseBtn, "width 64:96:96, wrap");
		add(resultsPanel, "span 2");
		
		parseBtn.addActionListener(evt -> superviser.onParse());
		superviser.setView(this);
	}


	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setTitle(String title) {
		titleTf.setText(title);
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void clearResult() {
		resultsPanel.removeAll();
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void startResultFor(String name) {
		resultPanel = new JPanel();
		resultPanel.setLayout(new MigLayout());
		JLabel parseName = new JLabel(name);
		parseName.setFont(SUBTITLE_FONT);
		resultPanel.add(parseName,"wrap");
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void setDescription(String description) {
		JLabel parseDesc = new JLabel(description);
		parseDesc.setFont(NORMAL_FONT);
		resultPanel.add(parseDesc,"wrap");
	}


	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void addResultItem(String content) {
		JLabel parseItem = new JLabel(WordWrapper.wraps(content, 32));
		parseItem.setToolTipText(content);
		parseItem.setFont(NORMAL_FONT);
		resultPanel.add(parseItem,"wrap");
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void endResult() {
		resultsPanel.add(resultPanel, "width 256:512:512, wrap");
	}

}
