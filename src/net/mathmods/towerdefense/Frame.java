package net.mathmods.towerdefense;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Frame extends JFrame {
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new Frame();
			}
		});
	}
	
	public Frame(){
		new JFrame();
		
		this.setTitle("Tower defense - by mathmods");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
		
		Screen screen = new Screen(this);
		this.add(screen);
	}
	
}
