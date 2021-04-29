package gamebook.supervisers;

import java.util.Collection;
import java.util.List;

import gamebook.domains.*;

/**
 * Traite les demandes issues d'une {@code ReadView}.
 * � cette fin, elle contr�le d'autres objets parmi lesquels un livre-jeu et une session.
 */
public final class ReadSuperviser {
	protected static final String RESTART_KEY = "Recommencer l'aventure";

	private ReadView view;
	private final Session sess;
	private final GameBook book;
	
	/**
	 * Construit un ReadSuperviser en attente d'une vue.
	 */
	public ReadSuperviser(Session sess, GameBook book) {
		this.sess = (sess != null) ? sess : new Session(new GameBook(null, null));
		this.book = (book != null) ? book : new GameBook(null, null);
	}

	/**
	 * D�finit la vue dont le superviseur doit traiter les demandes.
	 * Met � jour cette vue avec les donn�es de lecture.
	 * 
	 * @throws NullPointerException si view est null
	 */
	public void setView(ReadView view) {
		this.view = view;
		refreshView();
	}
	
	/**
	 * Permet de rafr�chir la vue avec les donn�es du paragraph courant de la session.
	 * 
	 * @see 			Session#isCurrentHasChoice()
	 * @see				Session#getCurrentChoices()
	 * @see				Session#getCurrentHead()
	 * @see 			Session#getCurrentContent()
	 * @see				GameBook#getTitle()
	 * @author			J�r�mi Nihart
	 */
	public void refreshView() {
		// G�rer la r�cup�ration des choix du paragraphe actuel
		Collection<String> choices = (sess.isCurrentHasChoice()) ? sess.getCurrentChoices() : List.of(RESTART_KEY);
		// G�rer la mise � jour de la vue
		this.view.setTitle(book.getTitle());
		this.view.setParagraph(sess.getCurrentHead(), sess.getCurrentContent());
		this.view.setChoices(choices);
	}

	/** 
	 * R�agit aux choix de l'utilisateur en mettant � jour la session de lecture.
	 * <hr>
	 * Informations sur la CTT :
	 * <ul>
	 * 	<li><u>CTT</u> dans le meilleur des cas : <b>O(1)</b></li>
	 * 	<li><u>CTT</u> dans le pire des cas : <b>O(n)</b></li>
	 * </ul>
	 *
	 * Description de mon r�sonement :
	 * <p>Dans le cas ou le choix <code>RESTART_KEY</code> est press� cette
	 * fonction appelle {@link Session#reset()} qui boucle <b>O(n)</b> ensuite *
	 * est ex�cut�. La CTT dans ce cas est la pire <b>O(n)</b>.</p>
	 * 
	 * <p>Dans le cas ou un autre choix est press� cette fonction appelle
	 * {@link Session#goToParagraphByChoiceKey(String)} qui ajout un �l�ment �
	 * l'historique et actualise le paragraphe courrant de la session <b>O(1)</b>.
	 * Ensuite * est ex�cut�. La CTT dans ce cas d�pend de * et peut soit �tre la
	 * meilleur <b>O(1)</b> soit �tre la pire <b>O(n)</b></p>
	 * 
	 * <u>L�gende :</u>
	 * <p>* : {@link ReadSuperviser#refreshView()} � th�oriquement une CTT de <b>O(m)</b>
	 * En effet dans le cas ou la liste de choix qui est pass� en argument de
	 * <code>this.view.setChoices()</code> contient plus de 1 �l�ment la CTT est de <b>O(m)</b>
	 * Dans le cas ou la liste en argument contient de 0 � 1 choix la CTT est de <b>O(1)</b>.</p>
	 * <p><b>n</b> : est ne nombre d'�l�ment � supprimer de l'historique de la Session.</p>
	 * <p><b>m</b> : est le nombre de de choix disponibles pour le Paragraph courant.</p>
	 * <hr>
	 *
	 * @param		choiceKey le libell� du choix. Ce libell� sert � d�terminer
	 * 					      le paragraphe suivant � afficher.
	 *
	 * @see			ReadSuperviser#refreshView()
	 * @see 		Session#goToParagraphByChoiceKey(String)
	 * @see			Session#reset()
	 */
	public void onChoiceSelected(String choiceKey) {
		if (choiceKey.equals(RESTART_KEY)) {sess.reset();}
		else {sess.goToParagraphByChoiceKey(choiceKey);}
		refreshView();
	}
	
	/**
	 * Revient au paragraphe pr�c�dent, sauf si le lecteur est au d�but du livre. 
	 */
	public void onGoBack() {
		sess.goBack();
		refreshView();
	}
}
