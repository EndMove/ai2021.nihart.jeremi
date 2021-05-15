/**
 * File name    : GameBookStatement.java
 *
 * Description  : Interface d'impl�mentation des classes de statement du livre
 * 				  permettent une v�rification de la coh�rence.
 *
 * Version      : 1.0
 * Since        : 1.2
 * Date         : 13/05/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
 */
package gamebook.domains;

import java.util.Collection;

/**
 * GameBookStatement
 *
 * Interface de m�thodes permettent d'impl�menter les controllers de statut du livre.
 * 
 * <hr>
 * <b>Postconditions des donn�es pour : <i>B. Relev� du nombre de fois que chaque �
 *    figure dans une destination</i></b>
 * <ul>
 *  <li>Titre : Indique ce que fait le controller.</li>
 *  <li>Description : Mentionne le titre du livre trait�.</li>
 *  <li>R�sultat(s) : Num�ro du paragraphe dans le livre jeu, suivi du nombre
 *      d'apparition, suivi du contenu du paragraphe.</li>
 * </ul>
 * 
 * <hr>
 *
 * @version     1.0
 *
 * @author      J�r�mi Nihart
 */
public interface GameBookStatement {
	
	/** 
	 * Indique au controller de d�marrer une v�rification des donn�es.
	 * 
	 * @param 		book Objet {@link GameBook} avec lequel travailer.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public void parse(GameBook book);
	
	/** 
	 * R�cup�rer le titre du controller.
	 * 
	 * @return 		Le titre du controller.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public String getTitle();
	
	/** 
	 * R�cup�rer la description du controller.
	 * 
	 * @return 		La description du controller.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public String getDescription();
	
	/** 
	 * R�cup�rer les r�sultats du controller.
	 * 
	 * @return 		Liste des r�sultats format� du controller.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public Collection<String> getResults();
	
}