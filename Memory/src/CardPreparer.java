import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardPreparer {
	
	private GameManager gm;
	private String pathToDirectory;
	private Random random;
	private String backOfCard = "back.jpg";	
	
	// content only set for testpurposes
	private ArrayList<String> filenames = new ArrayList<String>();
	private ArrayList<String> descriptions = new ArrayList<String>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	public CardPreparer(GameManager gm) {
		this.gm = gm;
		setFilenames();
		setDescriptions();
		setBack(backOfCard);
		random = new Random();
	}
	
	// can be rewritten to get newly created directory as input
	public void setFilenames() {
		filenames.add("ms_Ananas.jpg");
		filenames.add("ms_Apfel.jpg");
		filenames.add("ms_Banane.jpg");
		filenames.add("ms_Beeren.jpg");
		filenames.add("ms_Bohne.jpg");
		filenames.add("ms_Broccoli.jpg");
		filenames.add("ms_Brot.jpg");
		filenames.add("ms_CremeFraiche.jpg");
		filenames.add("ms_Eier.jpg");
		filenames.add("ms_Fisch.jpg");
		filenames.add("ms_Glace.jpg");
		filenames.add("ms_Hackfleisch.jpg");
		filenames.add("ms_Haferflocken.jpg");
		filenames.add("ms_Huhn.jpg");
		filenames.add("ms_Joghurt.jpg");
		filenames.add("ms_Karotte.jpg");
		filenames.add("ms_Kartoffel.jpg");
		filenames.add("ms_Kaese.jpg");
		filenames.add("ms_Kokosnuss.jpg");
		filenames.add("ms_Konfituere.jpg");
		filenames.add("ms_Mayonnaise.jpg");
		filenames.add("ms_Meringue.jpg");
		filenames.add("ms_Milch.jpg");
		filenames.add("ms_Mozarella.jpg");
		filenames.add("ms_Nacho.jpg");
		filenames.add("ms_NormalerTeig.jpg");
		filenames.add("ms_Orange.jpg");
		filenames.add("ms_Pasta.jpg");
		filenames.add("ms_Pastetli.jpg");
		filenames.add("ms_Peperoni.jpg");
		filenames.add("ms_Pilz.jpg");
		filenames.add("ms_Ratte.jpg");
		filenames.add("ms_Reis.jpg");
		filenames.add("ms_Salat.jpg");
		filenames.add("ms_Schlagrahm.jpg");
		filenames.add("ms_Schokolade.jpg");
		filenames.add("ms_Senf.jpg");
		filenames.add("ms_Spaghetti.jpg");
		filenames.add("ms_SuesserTeig.jpg");
		filenames.add("ms_Tomate.jpg");
		filenames.add("ms_Tomatensauce.jpg");
		filenames.add("ms_Unfall.jpg");
		filenames.add("ms_Verband.jpg");
		filenames.add("ms_Zitrone.jpg");
		filenames.add("ms_Zwiebel.jpg");
	}
	
	public void setDescriptions() {
		descriptions.add("ms_Ananas.jpg");
		descriptions.add("ms_Apfel.jpg");
		descriptions.add("ms_Banane.jpg");
		descriptions.add("ms_Beeren.jpg");
		descriptions.add("ms_Bohne.jpg");
		descriptions.add("ms_Broccoli.jpg");
		descriptions.add("ms_Brot.jpg");
		descriptions.add("ms_CremeFraiche.jpg");
		descriptions.add("ms_Eier.jpg");
		descriptions.add("ms_Fisch.jpg");
		descriptions.add("ms_Glace.jpg");
		descriptions.add("ms_Hackfleisch.jpg");
		descriptions.add("ms_Haferflocken.jpg");
		descriptions.add("ms_Huhn.jpg");
		descriptions.add("ms_Joghurt.jpg");
		descriptions.add("ms_Karotte.jpg");
		descriptions.add("ms_Kartoffel.jpg");
		descriptions.add("ms_Kaese.jpg");
		descriptions.add("ms_Kokosnuss.jpg");
		descriptions.add("ms_Konfituere.jpg");
		descriptions.add("ms_Mayonnaise.jpg");
		descriptions.add("ms_Meringue.jpg");
		descriptions.add("ms_Milch.jpg");
		descriptions.add("ms_Mozarella.jpg");
		descriptions.add("ms_Nacho.jpg");
		descriptions.add("ms_NormalerTeig.jpg");
		descriptions.add("ms_Orange.jpg");
		descriptions.add("ms_Pasta.jpg");
		descriptions.add("ms_Pastetli.jpg");
		descriptions.add("ms_Peperoni.jpg");
		descriptions.add("ms_Pilz.jpg");
		descriptions.add("ms_Ratte.jpg");
		descriptions.add("ms_Reis.jpg");
		descriptions.add("ms_Salat.jpg");
		descriptions.add("ms_Schlagrahm.jpg");
		descriptions.add("ms_Schokolade.jpg");
		descriptions.add("ms_Senf.jpg");
		descriptions.add("ms_Spaghetti.jpg");
		descriptions.add("ms_SuesserTeig.jpg");
		descriptions.add("ms_Tomate.jpg");
		descriptions.add("ms_Tomatensauce.jpg");
		descriptions.add("ms_Unfall.jpg");
		descriptions.add("ms_Verband.jpg");
		descriptions.add("ms_Zitrone.jpg");
		descriptions.add("ms_Zwiebel.jpg");
	}
	
	public void setBack(String backOfCard) {
		this.backOfCard = backOfCard;
	}
	
	public ArrayList<Card> createCards(int numberOfPairs) {
		
		int index;
		
		if (filenames.size() != descriptions.size()) {
			System.out.println("filenames and descriptions not of equal length");
			return null;
		}
		
		for (int i = 0; i < numberOfPairs; i++) {
			index = random.nextInt(filenames.size());
			for (int j = 0; j < 2; j++) {
				Card newCard = new Card(filenames.get(index), descriptions.get(index), backOfCard, gm);				
				cards.add(newCard);
			}
			filenames.remove(index);
			descriptions.remove(index);
		}
		shuffle();
		return cards;
	}
	
	public void shuffle() {
		Collections.shuffle(cards, new Random(System.nanoTime()));
	}

}
