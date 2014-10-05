package net.mathmods.towerdefense;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MousePress implements MouseListener, MouseMotionListener {

	private Frame frame;
	
	private Screen screen;
	
	private Screen.KeyTyped mouse;
	
	public MousePress(Screen s) {
		screen = s;
		frame = s.frame;
		this.mouse = this.screen.new KeyTyped();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getXOnScreen();
		int y = e.getYOnScreen();
		
		if(x >= (frame.getWidth() / 2) - 300 / 2 && x <= (frame.getWidth() / 2) + 300 / 2){
			if(y >= (int) (frame.getHeight() / 3 + Screen.towerHeight*5) && y <= (int)(frame.getHeight() / 3 + Screen.towerHeight*5) + 75){
				if(screen.scene == -1){
					mouse.buttonQuit();
					return;
				}
			}
		}
		if(x >= (frame.getWidth() / 2) - 300 / 2 && x <= (frame.getWidth() / 2) + 300 / 2){
			if(y >= (int) (frame.getHeight() / 3 + Screen.towerHeight*3) && y <= (int)(frame.getHeight() / 3 + Screen.towerHeight*3) + 75){
				if(screen.scene == -1){
					mouse.buttonMainMenu();
					return;
				}
			}
		}
		if(screen.scene > 0)
		for(int xx = 0; xx < 18; xx++){
			for(int yy = 0; yy < 2; yy++){
				if(x >= (int)(Screen.towerWidth + Screen.towerWidth / 2 + (Screen.towerWidth*2 + Screen.towerWidth / 2) + (xx*(int)Screen.towerWidth)) && x <= (int)(Screen.towerWidth + Screen.towerWidth / 2 + (Screen.towerWidth*2 + Screen.towerWidth / 2) + (xx*(int)Screen.towerWidth)) + Screen.towerWidth){
					if(y >= (15*(int)Screen.towerHeight) + 12 + yy * (int)Screen.towerHeight && y <= (15*(int)Screen.towerHeight) + 12 + (yy * (int)Screen.towerHeight) + Screen.towerHeight){
						if (Tower.towerList[(xx + yy * 18) * screen.page] != null){
							if(screen.scene > 0)mouse.selectTower(Tower.towerList[(xx + yy * 18) * screen.page].id);
							return ;
						}else{
							if(Screen.debugging)System.err.println("Failed to get tower with id: " + (xx + yy * 18) * screen.page);
						}
					}
				}
			}
		}
		if(screen.scene > 0)
		for(int xx = 0; xx < 22; xx++){
			for(int yy = 0; yy < 14; yy++){
				if(x >= (int)Screen.towerWidth + (xx * (int)Screen.towerWidth) && x <= (int)(Screen.towerWidth + (xx * (int)Screen.towerWidth)) + Screen.towerWidth){
					if(y >= (int)Screen.towerHeight + (yy * (int)Screen.towerHeight) && y <= (int)(Screen.towerHeight + (yy * (int)Screen.towerHeight)) + Screen.towerHeight){
						if((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){
							mouse.placedTower(xx, yy);
							return ;
						}
					}
				}
			}
		}
		
		if((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK){
			mouse.DisposeTower();
			return ;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getXOnScreen();
		int y = e.getYOnScreen();
		if(screen.hand != -1){
			mouse.MoveWithTowerInHand(x, y);
		}
	}

}
