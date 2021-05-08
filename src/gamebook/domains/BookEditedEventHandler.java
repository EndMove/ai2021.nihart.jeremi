/**
 * File name    : BookEditedEventHandler.java
 *
 * Description  : Interface d'implémentation des évènements de modification
 *                du livre.
 *
 * Version      : 1.0
 * Since        : 1.1
 * Date         : 08/05/2021
 *
 * Author       : Jérémi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit2
 */
package gamebook.domains;

/**
 * BookEditedEventHandler
 *
 * Interface de méthode permetant de signaler des modification du livre. 
 *
 * @version     1.0
 *
 * @author      Jérémi Nihart
 */
public interface BookEditedEventHandler {
	
	/** 
	 * Méthode d'évènement, est appellé lorsque le livre est modifié.
	 *
	 * @since       1.0
	 *
	 * @author      Jérémi Nihart
	 */
	public void onBookEdited();
}