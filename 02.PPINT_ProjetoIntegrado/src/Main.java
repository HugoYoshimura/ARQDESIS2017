import javax.swing.JFrame;

import View.TelaLogin;
import View.TelaPrincipal;



public class Main {

	public static void main(String args[]) throws Exception {

		/*
		TelaLogin obj = new TelaLogin();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setSize(250, 175);
		obj.setVisible(true);
		obj.setLocationRelativeTo(null);
		
		/**/
		/**/
		TelaPrincipal obj = new TelaPrincipal(4);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//obj.setUndecorated(true);
		//obj.setPreferredSize(new Dimension(640, 480));
		//obj.setMinimumSize(new Dimension(640, 480));
		//obj.setSize(750, 600);
		obj.setVisible(true);
		obj.setLocationRelativeTo(null);
		/*
		*/
		
		
	}
}
