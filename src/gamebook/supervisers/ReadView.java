package gamebook.supervisers;

import java.util.Collection;

/**
 * Une vue d'une lecture permet � l'utilisateur de lire son livre jeu.
 * */
public interface ReadView {
	
	/**
	 * Modifie le titre du livre-jeu.
	 * @param bookTitle le titre du livre jeu.
	 * */
	void setTitle(String bookTitle);

	/**
	 * Modifie le paragraphe pr�sent�.
	 * 
	 * @param head l'en-t�te du paragraphe. Correspond typiquement � un texte de la forme {@code Paragraphe #}.
	 * @param content le contenu du paragraphe.
	 * */
	void setParagraph(String head, String content);

	/**
	 * D�finit les choix qui se pr�sentent � l'utilisateur.
	 * 
	 * @param une collection de choix sans ordre pr�cis.
	 * */
	void setChoices(Collection<String> choices);

}
