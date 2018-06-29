package blazingforest.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display extends Canvas {
	
	private static final long serialVersionUID = 333527589333544306L;
	
	public Display(int width, int height, Main parent) {
		JFrame frame = new JFrame("BFLearn");
		
		Dimension size = new Dimension(width, height);
		frame.setSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
		frame.setPreferredSize(size);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.add(parent);
		frame.setVisible(true);
		
		parent.start();
	}
	
}
