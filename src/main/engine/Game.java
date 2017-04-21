package main.engine;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args){
		
		JFrame window = new JFrame("Title");
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new GamePanel());
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
