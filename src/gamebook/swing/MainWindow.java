package gamebook.swing;

import java.awt.*;
import javax.swing.*;
import static gamebook.swing.Theme.*;

/**
 * La fen�tre principale est compos�e d'un panneau � onglet (tab pannel).
 * Chaque vue correspond � un onglet du panneau.
 * */
@SuppressWarnings("serial")
public final class MainWindow extends JFrame {

	/**
	 * Construit une fen�tre principal compos� des vues {@code reader, edit, verify}
	 * */
	public MainWindow(JPanel read, JPanel edit, JPanel verify) {
		super("AI 2021 : lecteur et �diteur d'un livre jeu");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setBackground(BACKGROUND_COLOR);
		setFont(NORMAL_FONT);
		
		JTabbedPane tabsPane = new JTabbedPane();

		tabsPane.setFont(NORMAL_FONT);
		tabsPane.add("Lire", read);
		tabsPane.add("Editer", edit);
		tabsPane.add("Verifier", verify);
		this.setContentPane(tabsPane);
	}
}
