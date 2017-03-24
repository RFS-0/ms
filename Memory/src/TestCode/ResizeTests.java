package TestCode;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ResizeTests {

public static void main(String[] args) {
		
		JFrame jf1 = new JFrame();
		JButton jb1 = new JButton("testimage1");	
		ResizeTests ap1 = new ResizeTests();
		ImageIcon ic1 = ap1.setImage("picture_1.jpg", "testimage1");
		
		jb1.setIcon(ic1);
		jf1.add(jb1);
		jf1.setSize(500, 500);
		jf1.setVisible(true);
		
		// resize it
		JFrame jf2 = new JFrame();
		JButton jb2 = new JButton("testimage2");	
		ResizeTests ap2 = new ResizeTests();
		ImageIcon ic2 = ap2.setImage("picture_1.jpg", "testimage2");
		
		Image scaled = ic2.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ic2 = new ImageIcon(scaled);
		jb2.setIcon(ic2);
		jf2.add(jb2);
		jf2.setSize(500, 500);
		jf2.setVisible(true);
		
	}
	
	public ImageIcon setImage(String path, String description) {
		
		URL imgURL = getClass().getResource(path);
		
		//System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation());
		
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
