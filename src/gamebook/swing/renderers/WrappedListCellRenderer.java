package gamebook.swing.renderers;

import java.awt.*;
import java.util.StringJoiner;

import javax.swing.*;

/**
 * D�finis une cellule contenant du texte.
 * Quand le texte est trop long, la cellule le d�coupe de sorte que la longueur du texte
 * compte moins de 20 caract�res tout en conservant les mots entiers.
 * */
@SuppressWarnings("serial")
public class WrappedListCellRenderer extends JLabel implements ListCellRenderer<String> {
	private final Color focusForeground;
	private final Color focusBackground;
	
	/**
	 * Initialise une cellule avec deux couleurs appliqu�s quand la cellule est s�lectionn�e.
	 * */
	public WrappedListCellRenderer(Color focusForeground, Color focusBackground) {
		super();
        setOpaque(true);
		this.focusForeground = focusForeground;
		this.focusBackground = focusBackground;
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setToolTipText(value);
		setText(WordWrapper.wraps(value, 20));
		
		if(isSelected || cellHasFocus) {
			this.setForeground(focusForeground);
			this.setBackground(focusBackground);
		} else {
			this.setForeground(Color.BLACK);
			this.setBackground(Color.WHITE);
		}
		
		return this;
	}


}
