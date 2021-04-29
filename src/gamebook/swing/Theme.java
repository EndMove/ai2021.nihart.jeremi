package gamebook.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Définit l'aspect général des vues.
 * */
final class Theme {
	static {
		 try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	//Dimensions
	public static final int PANEL_HEIGHT = 512;
	public static final int PANEL_WIDTH = 1024;
	
	//Police de caractères
	public static final Font TITLE_FONT = new Font("Segoe UI Light", Font.PLAIN, 24);
	public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 20);
	public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
	
	//Couleurs
	public static final Color BACKGROUND_COLOR = SystemColor.window;
	public static final Color PRIMARY_COLOR = Color.decode("0xD90B43");
}
