package net.mathmods.towerdefense;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy implements Cloneable {

	public static final Enemy[] enemylist = new Enemy[200];
	public static final Enemy neutral = new EnemyNeutral(30, 10, 2, 0).getTexture("neutral");
	
	public int health;
	public int strenght;
	public int speed;
	public int id;
	int lastx = 0;
	int lasty = 0;
	
	public String texturelocation;
	public Image texture;
	
	public Enemy(int health, int strenght, int speed, int id){
		if(enemylist[id] != null){
			System.err.println("[ERROR] 2 enemys with same id");
		}else{
			enemylist[id] = this;
			this.health = health;
			this.speed = speed;
			this.strenght = strenght;
			this.id = id;
		}
	}
	
	public Enemy getTexture(String name){
		texturelocation = "res/enemys/" + name + ".png";
		Image i;
		i = new ImageIcon(texturelocation).getImage();
		this.texture = i.getScaledInstance((int)Screen.towerWidth, (int)Screen.towerHeight, Image.SCALE_SMOOTH);
		return null;
	}
	
}
