/**
 * File name    : GameBookStatement.java
 *
 * Description  : Interface d'implémentation des classes de statement du livre
 * 				  permettent une vérification de la cohérence.
 *
 * Version      : 1.0
 * Since        : 1.2
 * Date         : 13/05/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit3
 */
package gamebook.domains;

import java.util.Collection;

/**
 * GameBookStatement
 *
 * Interface de méthodes permettent d'implémenter les controllers de statut du livre. 
 *
 * @version     1.0
 *
 * @author      Jérémi Nihart
 */
public interface GameBookStatement {
	
	/** 
	 * Indique au controller de démarrer une vérification des données.
	 * 
	 * @param 		book Objet {@link GameBook} avec lequel travailer.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public void parse(GameBook book);
	
	/** 
	 * Récupérer le titre du controller.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public String getTitle();
	
	/** 
	 * Récupérer la description du controller.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public String getDecription();
	
	/** 
	 * Récupèrer les résultats du controller.
	 * 
	 * @return 		Liste des résultats formaté du controller.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public Collection<String> getResults();
	
}