import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Card {
	
		private GameManager gm;
		
		private int currentWidth;
		private int currentHeight;
		
		private JButton button;
		private int posX, posY;
		private String name;
		private CardState state;
		
		private ImageIcon frontOfCard;
		private ImageIcon backOfCard;
		
		private Player player;
		
		// constructor
		public Card(String pathToFront, String description, String pathToBack, GameManager gm) {
			this.gm = gm;
			button = new JButton();
			button.setName(description);
			setState(CardState.BACK_VISIBLE);
			frontOfCard = setImage(pathToFront);
			backOfCard = setImage(pathToBack);
			name = description;
			setIcon();
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (state == CardState.ALREADY_TAKEN) {
						button.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
						return;
					}
					player = gm.getCurrentPlayer();
					if (player != null) {
						player.getPlayerStats().addCard(getThis());
						player.getPlayerStats().addLocation(getX(), getY());
						player.getPlayerStats().setAccuracy();
						if (state == CardState.BACK_VISIBLE) {
							player.getPlayerStats().incrementNumerOfTurns();
							if (player.getNumberOfTurnedCards() >= 2) {
								gm.notifyAboutFinishedTurn();
							}
						}
					}
					turnCard();		
					resizeImage();
				}
			});;
		}
		
		
		// all setters
		
		public void setIcon() {
			if (state == CardState.FRONT_VISIBLE || state == CardState.ALREADY_TAKEN) {
				button.setIcon(frontOfCard);
			}
			else {
				button.setIcon(backOfCard);
			}
			return;
		}
		
		public ImageIcon setImage(String path) {
			
			URL imgURL = getClass().getResource(path);
			
			if (imgURL != null) {
				return new ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
		}
		
		public void setState(CardState state) {
			this.state = state;
		}
		
		public void setX(int posX) {
			this.posX = posX;
		}
				
		public void setY(int posY) {
			this.posY = posY;
		}
		
		public void setCurrentWidht(int newWidth) {
			currentWidth = newWidth;
		}
		
		public void setCurrentHeight(int newHeight) {
			currentHeight = newHeight;
		}
		
		// all getters
		
		public Card getThis() {
			return this;
		}
		
		public JButton getButton() {
			return this.button;
		}
		
		Image getCurrentImage() {
			if (state == CardState.FRONT_VISIBLE)
				return frontOfCard.getImage();
			else if (state == CardState.BACK_VISIBLE)
				return backOfCard.getImage();
			else
				return frontOfCard.getImage();
		}
		
		public Image getFront() {
			return frontOfCard.getImage();
		}
		
		public Image getBack() {
			return backOfCard.getImage();
		}
		
		public CardState getState() {
			return state;
		}
		
		public int getX() {
			return posX;
		}
		
		public int getY() {
			return posY;
		}
		
		public String getName() {
			return name;
		}
		
		// all logic
		
		public void resizeImage() {
			
			if (button.getIcon() == null) {
				return;
			}
			
			int width = button.getWidth();
			int height = button.getHeight();
			
			if (width == 0 || height == 0) {
				width = 100;
				height = 100;
			} else {
				width = button.getWidth();
				height = button.getHeight();
			}
			
			Image oldImage = ((ImageIcon) button.getIcon()).getImage();
			Image newImg = oldImage.getScaledInstance(width, height, Image.SCALE_FAST);
			button.setIcon(new ImageIcon(newImg));
	
		}
		
		public void turnCard() {
			if (state == CardState.BACK_VISIBLE) {
				setState(CardState.FRONT_VISIBLE);
				setIcon();
			}
				
			else if (state == CardState.FRONT_VISIBLE) {
				setState(CardState.BACK_VISIBLE);
				setIcon();
			}		
		}
	}

