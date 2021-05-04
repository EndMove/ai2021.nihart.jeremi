package gamebook.domains;

import java.util.List;

/**
 * Déclare les données relatives au livre donné en exemple.
 * Ces données sont notamment utilisées à des fins de tests pour l'itération 1.
 * */
public class GameBookFactory {
	public static final String BOOK_TITLE = "À la poursuite du Colonel Moutarde";
	
	public static final String P1_CONTENT = "Vous êtes en train de parcourir un blog passionnant depuis de longues heures quand vous entendez le parquet craquer derrière vous.";
	public static final String P2_CONTENT = "Discrètement une personne s’est glissée dans votre dos. Vous êtes assassiné par le Colonel Moutarde avec un Chandelier.";
	public static final String P3_CONTENT = "Vous tournez la tête et avez le temps de voir une personne massive fuir par la porte de derrière, laissant une forte odeur de pipe dans son sillage.";
	public static final String P4_CONTENT = "Vous vous étirez et vous levez pour marcher un peu. La lumière de la lampe vous montre une personne avec un complet veston orangé d’un goût douteux. Il tient un chandelier d’argent à la main et commence à s’enfuir.";
	public static final String P5_CONTENT = "La mystérieuse personne court moins vite que vous, probablement à cause de ses poumons qui contiennent plus de goudron qu’une bretelle d’autoroute. Vous la rattrapez dans le jardin et la plaquez dans un parterre de roses. Le colonel Moutarde meurt quelques instants plus tard en murmurant « Monde de merde ». Vous avez éliminé un dangereux tueur. Youpi, c’est gagné ! ";
	
	public static final String IGNORE = "Vous remettre à lire comme si rien ne s’était passé";
	public static final String FOLLOW = "Poursuivre cette personne";
	public static final String WALK = "Se dégourdir les jambes";
	public static final String KEEP_READING = "Continuer à lire";
	public static final String LOOK_BEHIND = "Regarder derrière";
	
	/**
	 * Constructor for the 100 percent.
	 */
	private GameBookFactory() {}
	
	/** 
	 * Craie un objet GameBook initialisé.
	 *
	 * @return      Objet {@link GameBook} initialisé.
	 *
	 * @see         GameBook
	 * @author      Jérémi Nihart
	 */
	public static final GameBook makeGameBook() {
		List<Paragraph> paragraphs = makeParagraphs();
		makeChoices(paragraphs);
		return new GameBook(BOOK_TITLE, paragraphs);
	}
	
	/** 
	 * Craie la liste des paragraphes de base du livre.
	 *
	 * @return      Une liste de paragraphes.
	 *
	 * @see         Paragraph
	 * @author      Jérémi Nihart
	 */
	public static final List<Paragraph> makeParagraphs() {
		return List.of(
			new Paragraph(P1_CONTENT),
			new Paragraph(P2_CONTENT),
			new Paragraph(P3_CONTENT),
			new Paragraph(P4_CONTENT),
			new Paragraph(P5_CONTENT)
		);
	}
	
	/** 
	 * Définir les choix d'un groupe de paragraphes.<br>
	 * <u>TTC : O(n)</u>
	 *
	 * @param       target Objet {@link Paragraph} sur lequel pointe le choix.
	 * @param       title Titre du choix (contenu).
	 * @param       paragraphs Intération d'objet {@link Paragraph} auquels ajouter les choix.
	 *
	 * @see         Paragraph
	 * @see			Paragraph#addChoice(String, Paragraph)
	 * @author      Jérémi Nihart
	 */
	private static final void setChoices(Paragraph target, String title, Paragraph...paragraphs) {
		for(Paragraph p : paragraphs) {
			p.addChoice(title, target);
		}
	}
	
	/** 
	 * Craie les choix de base des paragraphes.
	 *
	 * @param       paragraphs List des paragraphes du livre.
	 *
	 * @see         Paragraph
	 * @see			GameBookFactory#setChoices(Paragraph, String, Paragraph...)
	 * @author      Jérémi Nihart
	 */
	public static final void makeChoices(List<Paragraph> paragraphs) {
		setChoices(paragraphs.get(1), IGNORE, paragraphs.get(2), paragraphs.get(3));
		setChoices(paragraphs.get(4), FOLLOW, paragraphs.get(2), paragraphs.get(3));
		setChoices(paragraphs.get(3), WALK, paragraphs.get(0), paragraphs.get(2));
		setChoices(paragraphs.get(1), KEEP_READING, paragraphs.get(0));
		setChoices(paragraphs.get(2), LOOK_BEHIND, paragraphs.get(0));
	}
}
