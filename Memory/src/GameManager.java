import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;

public class GameManager {

	private Field field;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<String> playerNames;

	private int numberOfRows;
	private int numberOfColumns;
	private int numberOfPairs;

	private Player currentPlayer;
	private int currentPlayerIndex;

	private LinkedList<Card> turnedCards;
	
	private boolean gameSetUp;
	private boolean gameNotFinished;
	private boolean turnNotFinished;
	private boolean gameNotQuit;

	public GameManager() {

		initializeGame();
		setupGame();
		// manage game
		while (gameNotFinished) {
			nextPlayer();
			prepareTurn();
			startTurn();
			while (turnNotFinished) {
				waitForPlayerToMakeTurn();
				getTurnedCards();
				if (checkMatches(turnedCards)) {
					if (!checkCardsOnField()) {
						finishGame();
					} 
					else {
						cleanUp(turnedCards);
						prepareTurn();
						startTurn();
					}
				} 
				else {
					cleanUp(turnedCards);
					finishTurn(turnedCards);
				}
			}
		}
		field.getFrame().dispose();
		currentPlayer.getFrame().dispose();
		waitForPlayerToEndGame();
	}

	// logic
	
	public void waitForPlayerToEndGame() {
		GameEnd ge = new GameEnd(this, players);
		gameNotQuit = true;
		synchronized (this) {
			while (gameNotQuit) {
				try {
					this.wait();
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void notifyAboutFinishedGame() {
		synchronized (this) {
			gameNotQuit = false;
			this.notifyAll();
		}
	}
	
	public void waitForPlayerToMakeTurn() {
		synchronized(this) {
			while(turnNotFinished) {
				try {
					this.wait();
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void notifyAboutFinishedTurn() {
		synchronized(this) {
			turnNotFinished = false;
			this.notifyAll();
		}
	}

	public boolean checkNumberOfTurnedCards() {
		boolean exeededLimit = false;
		synchronized (this) {
			while (!exeededLimit) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
				if (currentPlayer.getPlayerStats().getNumberOfTurns() >= 2) {
					return true;
				}

			}
		}
		return false;
	}

	public void initializeGame() {
		// get information for gamesetup form player
		GameSetUp gs = new GameSetUp(this);
		synchronized(this) {
			while (!gameSetUp) {
				try {
					this.wait();
				} catch (InterruptedException e) {}
			}
		}
		// set size of field
		numberOfRows = gs.getNumberOfRows();
		numberOfColumns = gs.getNumberOfColumns();
		numberOfPairs = numberOfRows * numberOfColumns / 2;
		// create players
		playerNames = gs.getPlayerNames();
		for (int i = 0; i < playerNames.size(); i++) {
			players.add(new Player(playerNames.get(i), this));
		}
		// create cards
		ArrayList<Card> cards = new CardPreparer(this).createCards(numberOfPairs);
		// create field
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		field.INITIAL_HEIGHT = 250 * numberOfRows;
		field.INITIAL_WIDTH = 250 * numberOfColumns;
		if (field.INITIAL_HEIGHT > dimension.height) {
			field.INITIAL_HEIGHT = dimension.height-100;
			field.INITIAL_WIDTH = dimension.width-100;
		}
		if (field.INITIAL_WIDTH > dimension.width) {
			field.INITIAL_HEIGHT = dimension.height-100;
			field.INITIAL_WIDTH = dimension.width-100;
		}
		field = new Field(numberOfRows, numberOfColumns, cards);
	}
	
	public void continueSeutp() {
		synchronized (this) {
			gameSetUp = true;
			this.notifyAll();
		}
	}

	public void setupGame() {
		gameNotFinished = true;
		turnNotFinished = true;
	}

	public void prepareTurn() {
		currentPlayer.getPlayerStats().resetNumberOfTurns();
		for (Card card : field.getCards()) {
			if (card.getState() == CardState.FRONT_VISIBLE) {
				card.turnCard();
				card.resizeImage();
			}
		}
		currentPlayer.show();
		resetTurnedCards();
	}

	public void startTurn() {
		turnNotFinished = true;
	}

	public boolean checkMatches(LinkedList<Card> turnedCards) {

		if (turnedCards.isEmpty()) {
			return false;
		}

		int index = turnedCards.size();

		Card card1 = turnedCards.get(index - 1);
		Card card2 = turnedCards.get(index - 2);

		if (card1.equals(card2) == false && card1.getName().equals(card2.getName())) {
			card1.setState(CardState.ALREADY_TAKEN);
			card2.setState(CardState.ALREADY_TAKEN);

			card1.getButton().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
			card2.getButton().setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));

			currentPlayer.getPlayerStats().incrementNumberOfPairs();
			currentPlayer.getPlayerStats().addPair(turnedCards.peekLast());

			return true;
		}
		return false;
	}

	public boolean checkCardsOnField() {
		for (Card card : field.getCards()) {
			if (card.getState() == CardState.BACK_VISIBLE || card.getState() == CardState.FRONT_VISIBLE) {
				return true;
			}
		}
		return false;
	}

	public void cleanUp(LinkedList<Card> turnedCards) {
		
		synchronized(this) {
			try {
				this.wait(2000);
			} catch (InterruptedException e) {}
		}
		
		currentPlayer.getPlayerStats().resetNumberOfTurns();
		currentPlayer.hide();

		int index = turnedCards.size();

		Card card1 = turnedCards.get(index - 1);
		Card card2 = turnedCards.get(index - 2);

		for (Card card : field.getCards()) {
			if (card.getName().equals(card1.getName()) || card.getName().equals(card2.getName())) {
				if (card.getState() == CardState.FRONT_VISIBLE) {
					card.setState(CardState.BACK_VISIBLE);
					card.turnCard();
					card.resizeImage();
				} else if (card.getState() == CardState.ALREADY_TAKEN) {
					card.getButton().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.red));
				}
			}
		}
	}

	public void finishTurn(LinkedList<Card> turnedCards) {
		turnNotFinished = false;
	}

	public void finishGame() {
		turnNotFinished = false;
		gameNotFinished = false;
	}

	// setter
	public void resetTurnedCards() {
		if (turnedCards != null) {
			turnedCards.clear();
		}
	}

	public void setCurrentPlayer(Player cp) {
		currentPlayer = cp;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	// getter
	public void getTurnedCards() {
		turnedCards = currentPlayer.getPlayerStats().getTurnedCards();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void nextPlayer() {
		if (currentPlayer == null) {
			setCurrentPlayerIndex(0);
		} else {
			setCurrentPlayerIndex((currentPlayerIndex + 1) % players.size());
		}
		setCurrentPlayer(players.get(currentPlayerIndex));
	}

}
