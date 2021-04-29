package gamebook.supervisers;

/**
 * La vue de vérification permet à l'auteur d'analyser certains aspects de son livre
 * */
public interface CheckView {
	/**
	 * Méthode appelée pour vider la vue des résultats précédents.
	 * */
	void clearResult();

	/**
	 * Définit le titre à afficher.
	 * */
	void setTitle(String title);

	/**
	 * Débute un nouveau cadre de résultat pour l'analyse {@code analysisName}.
	 * */
	void startResultFor(String analysisName);
	
	/**
	 * Définit la description du cadre de résultat actuel à {@code description}.
	 * */
	void setDescription(String description);

	/**
	 * Ajoute un élément du résultat d'analyse. Il peut s'agir du contenu
	 * d'un paragraphe par exemple.
	 * */
	void addResultItem(String content);

	/**
	 * Termine la construction du cadre de résultat et l'affiche à l'utilisateur.
	 * Si cette méthode n'est pas appelée, le cadre de résultat n'est pas affiché.
	 * */
	void endResult();

}
