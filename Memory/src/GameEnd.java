import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class GameEnd {
	
	private GameManager gm;
	private JFrame frame;
	
	private ArrayList<Player> players;
	
	public GameEnd(GameManager gm, ArrayList<Player> players) {
		this.gm = gm;
		this.players = players;
		
		frame = new JFrame("Ranking");
		Font font = new Font("Courier", Font.BOLD, 25);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2, 1));
		
		Object[] columnNames = {
				"Rang",
				"Anzahl Pärchen",
				"Anzahl Züge",
				"Genauigkeit in %"
		};
		
		Object[][] data = new Object[players.size()][4];
		
		Collections.sort(players, new Player());
		
		int row = 0;	
		for (Player p: players) {
				data[row][0] = new Integer(row+1);
				data[row][1] = new Integer(p.getPlayerStats().getNumberOfPairs());
				data[row][2] = new Integer(p.getPlayerStats().getTotalNumberOfTurns());
				data[row][3] = new Double(p.getPlayerStats().getAccuracy());
				row += 1;
		}
		
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
		tableRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		tableRenderer.setVerticalAlignment(JLabel.CENTER);
		JTable table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, tableRenderer);
		table.setFont(font);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(50);
		
		JTableHeader header = table.getTableHeader();
		header.setFont(font);
		
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gm.notifyAboutFinishedTurn();
				frame.dispose();
			}
		});
		
		frame.add(header);
		frame.add(table);
		frame.setSize(1200, 450);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width/2-frame.getSize().width/2, dimension.height/2-frame.getSize().height/2);
		frame.setVisible(true);
		
	}
	
}
