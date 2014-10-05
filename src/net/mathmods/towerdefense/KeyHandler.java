package net.mathmods.towerdefense;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private Screen screen;
	
	private Screen.KeyTyped key;
	
	public KeyHandler(Screen screen){
		this.screen = screen;
		this.key = this.screen.new KeyTyped();
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == 27){
			key.keyESC();
		}else if(keyCode == 32){
			key.keySPACE();
		}else if(keyCode == 10){
			key.keyENTER();
		}else{
			if(keyCode != 114)
			System.out.println(keyCode);
		}
	}
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == 114){
			key.toggleFPS();
		}
	}
	public void keyTyped(KeyEvent e) {
	}

}
