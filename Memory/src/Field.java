import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Field {
	
	public static int INITIAL_HEIGHT = 1000;
	public static int INITIAL_WIDTH = 1000;
	public static int RESCALING_FACTOR = 100;
	
	private JFrame field;
	private int numberOfRows;
	private int numberOfColumns;
	private int fieldWidth;
	private int fieldHeight;
	
	private ArrayList<Card> cards;
		
	public Field(int numberOfRows, int numberOfColumns, ArrayList<Card> cards) {
			
		this.setNumberOfRows(numberOfColumns);
		this.setNumberOfColumns(numberOfRows);
		this.cards = cards;
		
		field = new JFrame("Memory MS");
		field.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		field.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fieldWidth = field.getWidth();
		fieldHeight = field.getHeight();
		
		
		for (Card card: cards) {
			field.add(card.getButton());
		}	
		
		field.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension d = e.getComponent().getSize();
				
				if (d.height >= fieldHeight + RESCALING_FACTOR ||
					d.height <= fieldHeight - RESCALING_FACTOR ||
					d.width >= fieldWidth + RESCALING_FACTOR ||
					d.width <= fieldWidth - RESCALING_FACTOR) {					
					resizeCards();
					field.repaint();
				}											
			}		
		});
		
		field.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		field.setLocation(dimension.width/2-field.getSize().width/2, dimension.height/2-field.getSize().height/2);
		
		resizeCards();
		field.setVisible(true);	
	}
	
	// logic
	
	public void resizeCards() {
		for (Card card: cards) {
			card.resizeImage();
		}
	}
	
	// setter
	
	public void setResizable(boolean bool) {
		field.setResizable(bool);
	}
	
	public void setNumberOfRows(int numerOfRows) {
		this.numberOfRows = numerOfRows;
	}
	
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	
	// getter
	
	public JFrame getFrame() {
		return field;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
}
