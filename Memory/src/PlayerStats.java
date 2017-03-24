import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerStats {
	
	private int numberOfPairs;
	private int numberOfTurns;
	private int numberOfTotalTurns;
	private LinkedList<Card> turnedCards;
	private LinkedList<Card> collectedPairs;
	private ArrayList<Point> turnedLocations;
	private double accuracy;
	
	public PlayerStats() {
		numberOfPairs = 0;
		numberOfTurns = 0;
		numberOfTotalTurns = 0;
		turnedCards = new LinkedList<Card>();
		collectedPairs = new LinkedList<Card>();
		turnedLocations = new ArrayList<Point>();
		accuracy = 0.0;
	}

	// setter

	public void incrementNumberOfPairs() {
		this.numberOfPairs += 1;
	}
	
	public void incrementNumerOfTurns() {
		this.numberOfTurns += 1;
		this.numberOfTotalTurns +=1;
	}
	
	public void resetNumberOfTurns() {
		numberOfTurns = 0;
	}
	
	public void addCard(Card card) {
		synchronized(this) {
			turnedCards.add(card);
			this.notifyAll();
			
		}
	}
	
	public void addPair(Card pair) {
		collectedPairs.add(pair);
	}
	
	public void addLocation(int x, int y) {
		turnedLocations.add(new Point(x, y));
	}
	
	public void setAccuracy() {
		if (numberOfTotalTurns == 0) {
			accuracy = 0.0;
		} else {
			accuracy = (double) ((double) numberOfPairs / (double) (numberOfTotalTurns / 2) * 100.0);
		}
	}
	
	// getter
	
	public LinkedList<Card> getTurnedCards() {
		return turnedCards;
	}
	
	public LinkedList<Card> getCollectedPairs() {
		return collectedPairs;
	}
	
	public int getNumberOfPairs() {
		return numberOfPairs;
	}
	
	public int getNumberOfTurns() {
		return numberOfTurns;
	}
	
	public int getTotalNumberOfTurns() {
		return numberOfTotalTurns;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public ArrayList<Point> getLocations() {
		return turnedLocations;
	}
	
}
