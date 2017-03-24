import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Player implements Comparator<Player> {
	
	// data
	private GameManager gm;
	private String name;
	private PlayerStats stats;
	
	// gui
	private JFrame playerFrame;
	
	private JLabel jlabNumberOfPairs;
	private JLabel jlabNumberOfTurns;
	private JLabel jlabAccuracy;
	
	// only for comparator
	public Player() {}
	
	
	public Player(String name, GameManager gm) {
		this.gm = gm;
		this.name = name;
		stats = new PlayerStats();
		
		playerFrame = new JFrame(name);
		playerFrame.setLayout(new GridLayout(3, 2));
		
		jlabNumberOfPairs = new JLabel(" Number of Pairs: " + stats.getNumberOfPairs());
		jlabNumberOfPairs.setFont(new Font("Courier", Font.BOLD, 25));
		jlabNumberOfTurns = new JLabel(" Number of Turns: " + stats.getNumberOfTurns());
		jlabNumberOfTurns.setFont(new Font("Courier", Font.BOLD, 25));
		jlabAccuracy = new JLabel(" Accuracy: " + stats.getAccuracy() + "%");
		jlabAccuracy.setFont(new Font("Courier", Font.BOLD, 25));
		
		playerFrame.add(jlabNumberOfPairs);
		playerFrame.add(jlabNumberOfTurns);
		playerFrame.add(jlabAccuracy);
		
		playerFrame.setSize(500, 300);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		playerFrame.setLocation(dimension.width-playerFrame.getSize().width, 0);
		playerFrame.setVisible(false);
				
	}
	
	// setter
	
	public void resetTurnedCards() {
		stats.resetNumberOfTurns();
	}
	
	public void incrementTurnedCards() {
		stats.incrementNumerOfTurns();;
	}
	
	public void hide() {
		playerFrame.dispose();
	}
	
	// getter
	
	public PlayerStats getPlayerStats() {
		return stats;
	}
	
	public int getNumberOfTurnedCards() {
		return stats.getNumberOfTurns();
	}
	
	public String getName() {
		return name;
	}
	
	public JFrame getFrame() {
		return playerFrame;
	}
	
	public void updateFrame() {
		playerFrame.repaint();
	}
	
	public void show() {
		
		jlabNumberOfPairs.setText("Number of Pairs: " + stats.getNumberOfPairs());
		jlabNumberOfTurns.setText("Number of Turns: " + stats.getTotalNumberOfTurns());
		jlabAccuracy.setText("Accuracy: " + stats.getAccuracy());
		
		playerFrame.add(jlabNumberOfPairs);
		playerFrame.add(jlabNumberOfTurns);
		playerFrame.add(jlabAccuracy);
		
		playerFrame.setSize(500, 300);
		
		playerFrame.setVisible(true);
	}

	@Override
	public int compare(Player p1, Player p2) {
		return p2.getPlayerStats().getNumberOfPairs() - p1.getPlayerStats().getNumberOfPairs();
	}

}
