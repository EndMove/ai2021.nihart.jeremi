/**
 * File name    : BookEditedEventHandler.java
 *
 * Description  : Interface d'impl�mentation des �v�nements de modification
 *                du livre.
 *
 * Version      : 1.0
 * Since        : 1.1
 * Date         : 08/05/2021
 *
 * Author       : J�r�mi Nihart <j.nihart@student.helmo.be>
 * Link 		: https://server.endmove.eu/~endmove/HELMo/2020_2021/AIit2
 */
package gamebook.domains;

/**
 * BookEditedEventHandler
 *
 * Interface de m�thode permetant de signaler des modification du livre. 
 *
 * @version     1.0
 *
 * @author      J�r�mi Nihart
 */
public interface BookEditedEventHandler {
	
	/** 
	 * M�thode d'�v�nement, est appell� lorsque le livre est modifi�.
	 *
	 * @since       1.0
	 *
	 * @author      J�r�mi Nihart
	 */
	public void onBookEdited();
}