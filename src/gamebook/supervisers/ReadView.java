package gamebook.supervisers;

import java.util.Collection;

/**
 * Une vue d'une lecture permet à l'utilisateur de lire son livre jeu.
 * */
public interface ReadView {
	
	/**
	 * Modifie le titre du livre-jeu.
	 * @param bookTitle le titre du livre jeu.
	 * */
	void setTitle(String bookTitle);

	/**
	 * Modifie le paragraphe présenté.
	 * 
	 * @param head l'en-tête du paragraphe. Correspond typiquement à un texte de la forme {@code Paragraphe #}.
	 * @param content le contenu du paragraphe.
	 * */
	void setParagraph(String head, String content);

	/**
	 * Définit les choix qui se présentent à l'utilisateur.
	 * 
	 * @param une collection de choix sans ordre précis.
	 * */
	void setChoices(Collection<String> choices);

}
