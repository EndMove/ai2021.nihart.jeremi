package gamebook.supervisers;



/**
 * Traite les demandes issues d'une {@code CheckView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu.
 * */
public class CheckSuperviser {
	private CheckView view;

	/**
	 * Construit une instance du superviser � l'aide d'un gamebook
	 * et de plusieurs {@code parsers}
	 * */
	public CheckSuperviser() {

	}

	public void setView(CheckView view) {
		this.view = view;
		this.view.setTitle("LE TITRE VIENT ICI");
		this.view.startResultFor("ANALYSE 1");
		this.view.setDescription("DESCRIPTION DE L'ANALYSE 1");
		this.view.addResultItem("UN RESULTAT");		
		this.view.addResultItem("UN SECOND RESULTAT");
		this.view.endResult();
		this.view.startResultFor("ANALYSE 2");
		this.view.setDescription("DESCRIPTION DE L'ANALYSE 2");
		this.view.addResultItem("UN SEUL RESULTAT");		
		this.view.endResult();
	}

	/**
	 * Met � jour la vue en lui donnant pour titre celui du livre
	 * et en construisant les cadres de r�sultat des analyses effectu�s.
	 * */
	public void onParse() {

	}


}
