import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameSetUp {
	
	// setup parameters
	private GameManager gm;
	private int numberOfRows = 0;
	private int numberOfColumns = 0;
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	// frame
	private JFrame mainFrame;
	
	// label
	private JLabel jlabAskNumberOfRows;
	private JLabel jlabAskNumberOfColumns;
	private JLabel jlabAskNamesOfPlayers;
	private JLabel jlabConfirmSetUp;
	
	// combobox
	private JComboBox<Integer> jcmbAskNumberOfRows;
	private JComboBox<Integer> jcmbAskNumberOfColumns;
	
	// textfields
	private JTextField jtfNameOfPlayer;
	
	// buttons
	private JButton jbAddPlayer;
	private JButton jbRemovePlayer;
	private JButton jbConfirmSetUp;
	
	public GameSetUp(GameManager gm) {
		
		this.gm = gm;
		
		// main frame
		mainFrame = new JFrame("Welcome");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new GridLayout(4, 2));

		// create labels
		Font font = new Font("Courier", Font.BOLD, 25);
		jlabAskNumberOfRows = new JLabel(" Please select the number of rows the field should have: ", SwingConstants.LEFT);
		jlabAskNumberOfRows.setFont(font);
		jlabAskNumberOfColumns = new JLabel(" Please select the number of columns the field should have: ", SwingConstants.LEFT);
		jlabAskNumberOfColumns.setFont(font);
		jlabAskNamesOfPlayers = new JLabel(" Please enter a name and press Enter to add / remove a player: ", SwingConstants.LEFT);
		jlabAskNamesOfPlayers.setFont(font);
		jlabConfirmSetUp = new JLabel(" Please click \"Start\" if you are ready to play", SwingConstants.LEFT);
		jlabConfirmSetUp.setFont(font);
		
		// create comboboxes
		jcmbAskNumberOfRows = new JComboBox<Integer>();
		jcmbAskNumberOfRows.setFont(new Font("Courier", Font.BOLD, 25));
		jcmbAskNumberOfRows.addItem(1);
		jcmbAskNumberOfRows.addItem(2);
		jcmbAskNumberOfRows.addItem(3);
		jcmbAskNumberOfRows.addItem(4);
		jcmbAskNumberOfRows.addItem(5);
		jcmbAskNumberOfRows.addItem(6);
		jcmbAskNumberOfRows.addItem(7);
		jcmbAskNumberOfRows.addItem(8);
		jcmbAskNumberOfRows.addItem(9);
		jcmbAskNumberOfRows.setSelectedItem(4);

		jcmbAskNumberOfColumns = new JComboBox<Integer>();
		jcmbAskNumberOfColumns.setFont(new Font("Courier", Font.BOLD, 25));
		jcmbAskNumberOfColumns.addItem(1);
		jcmbAskNumberOfColumns.addItem(2);
		jcmbAskNumberOfColumns.addItem(3);
		jcmbAskNumberOfColumns.addItem(4);
		jcmbAskNumberOfColumns.addItem(5);
		jcmbAskNumberOfColumns.addItem(6);
		jcmbAskNumberOfColumns.addItem(7);
		jcmbAskNumberOfColumns.addItem(8);
		jcmbAskNumberOfColumns.addItem(9);
		jcmbAskNumberOfColumns.addItem(10);
		jcmbAskNumberOfColumns.setSelectedItem(4);

		// create text field
		jtfNameOfPlayer = new JTextField();
		jtfNameOfPlayer.setFont(new Font("Courier", Font.BOLD, 25));
		jtfNameOfPlayer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					for (Iterator<String> it = playerNames.iterator(); it.hasNext();) {
					
						if (it.next().equals(jtfNameOfPlayer.getText())) {
							it.remove();
							jtfNameOfPlayer.setText("");
							return;
						}	
					}
					playerNames.add(jtfNameOfPlayer.getText());
					jtfNameOfPlayer.setText("");
				}
			}
		});

		// create buttons
		jbAddPlayer = new JButton("Add");
		jbAddPlayer.setFont(new Font("Courier", Font.BOLD, 25));
		jbRemovePlayer = new JButton("Remove");
		jbRemovePlayer.setFont(new Font("Courier", Font.BOLD, 25));
		jbConfirmSetUp = new JButton("Start");
		jbConfirmSetUp.setFont(new Font("Courier", Font.BOLD, 25));
		jbConfirmSetUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setNumberOfRows((int) jcmbAskNumberOfRows.getSelectedItem()); 
				setNumberOfColumns((int) jcmbAskNumberOfColumns.getSelectedItem());
				
				if (playerNames.size() < 1) {
					playerNames.add("Player 1");
				}								
				mainFrame.dispose();
				gm.continueSeutp();
			}
		});
		
		// build frame
		mainFrame.add(jlabAskNumberOfRows);
		mainFrame.add(jcmbAskNumberOfRows);
		mainFrame.add(jlabAskNumberOfColumns);
		mainFrame.add(jcmbAskNumberOfColumns);
		mainFrame.add(jlabAskNamesOfPlayers);
		mainFrame.add(jtfNameOfPlayer);
		mainFrame.add(jlabConfirmSetUp);
		mainFrame.add(jbConfirmSetUp);
		
		mainFrame.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(dimension.width/2-mainFrame.getSize().width/2, dimension.height/2-mainFrame.getSize().height/2);
		mainFrame.setVisible(true);

	}
	
	// setters
	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	
	// getters
	
	public int getNumberOfRows() {
		return this.numberOfRows;
	}
	
	public int getNumberOfColumns() {
		return this.numberOfColumns;
	}
	
	public ArrayList<String> getPlayerNames() {
		return this.playerNames;
	}
	
}
