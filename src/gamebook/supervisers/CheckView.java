package gamebook.supervisers;

/**
 * La vue de v�rification permet � l'auteur d'analyser certains aspects de son livre
 * */
public interface CheckView {
	/**
	 * M�thode appel�e pour vider la vue des r�sultats pr�c�dents.
	 * */
	void clearResult();

	/**
	 * D�finit le titre � afficher.
	 * */
	void setTitle(String title);

	/**
	 * D�bute un nouveau cadre de r�sultat pour l'analyse {@code analysisName}.
	 * */
	void startResultFor(String analysisName);
	
	/**
	 * D�finit la description du cadre de r�sultat actuel � {@code description}.
	 * */
	void setDescription(String description);

	/**
	 * Ajoute un �l�ment du r�sultat d'analyse. Il peut s'agir du contenu
	 * d'un paragraphe par exemple.
	 * */
	void addResultItem(String content);

	/**
	 * Termine la construction du cadre de r�sultat et l'affiche � l'utilisateur.
	 * Si cette m�thode n'est pas appel�e, le cadre de r�sultat n'est pas affich�.
	 * */
	void endResult();

}
