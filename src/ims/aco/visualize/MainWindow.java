package ims.aco.visualize;

import ims.acs.main.City;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 6229043799675304947L;
	
	private JPanel contentPane;
	private DrawingPanel panel;

	public MainWindow(HashMap<Integer, City> gradovi) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(0, 0, 950,900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		this.setSize(xSize,ySize); 
		
		panel = new DrawingPanel(gradovi);
		contentPane.add(panel, BorderLayout.CENTER);
	}

	public DrawingPanel getDrawingPanel() {
		return panel;
	}
	
}
