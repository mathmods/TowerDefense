package net.mathmods.towerdefense;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Tower implements Cloneable {

	private String textureFile = "";
	public Image texture;
	
	public static final Tower[] towerList = new Tower[200];
	public static final Tower towerLightning = new TowerLightning(0, 10, 3, 1).getTexture("lightning");
	
	public int id;
	public int cost;
	public int range;
	public int DAMAGENEUTRAL = 1;
	public int DAMAGEFAST = 2;
	public int DAMAGESLOW = 3;
	public int ATTACKDAMAGE;
	public int x;
	public int y;
	
	public Tower(int id, int cost, int range, int damage){
		if(towerList[id] != null){
			System.err.println("[TOWERERROR] 2 towers with same id!");
		}else{
			towerList[id] = this;
			this.id = id;
			this.cost = cost;
			this.range = range;
			this.ATTACKDAMAGE = damage;
		}
	}
	
	public Tower getTexture(String texture){
		this.textureFile = "res/towers/" + texture +".png";
		Image i;
		i = new ImageIcon(textureFile).getImage();
		this.texture = i.getScaledInstance((int)Screen.towerWidth, (int)Screen.towerHeight, Image.SCALE_SMOOTH);
		
		return null;
	}
	
	public EnemyMove[] calculateEnemys(EnemyMove[] enemies, int x, int y){
		
		EnemyMove[] eIR = new EnemyMove[enemies.length];
		
		int towerX = x;
		int towerY = y;
		
		int towerRadius = this.range;
		int enemyRadius = 1;
		
		int enemyX;
		int enemyY;
		
		for(int i = 0; i<enemies.length; i++){
			if(enemies[i] != null){
				enemyX = (int) (enemies[i].xPos / Screen.towerWidth);
				enemyY = (int) (enemies[i].yPos / Screen.towerHeight);
				
				int dx = enemyX - towerX;
				int dy = enemyY - towerY;
				
				int dRadius = towerRadius + enemyRadius;
				if((dx * dx) + (dy * dy) < (dRadius * dRadius)){
					eIR[i] = enemies[i];
				}
			}
		}
		
		return eIR;
	}
	
	int attackDelay;
	int Delay = 0;
	EnemyMove attacking = null;
	
	public void update(EnemyMove[] e){
		if(this.ATTACKDAMAGE == DAMAGENEUTRAL){
			attackDelay = 2000;
		}else if(this.ATTACKDAMAGE == DAMAGEFAST){
			attackDelay = 1500;
		}else if(this.ATTACKDAMAGE == DAMAGESLOW){
			attackDelay = 3500;
		}
		if(Delay < attackDelay){
			Delay++;
		}else{
			Delay = 0;
			EnemyMove[] ETA = calculateEnemys(e, x, y);
			Random r = new Random();
			EnemyMove next = ETA[r.nextInt(e.length)];
			int run = 0;
			while(next == null && run < e.length){
				next = ETA[r.nextInt(e.length)];
				run++;
			}
			
			attacking = next;
			
			if(next != null){
				if(this.ATTACKDAMAGE == DAMAGENEUTRAL){
					next.health -= 10;
				}else if(this.ATTACKDAMAGE == DAMAGEFAST){
					next.health -= 5;
				}else if(this.ATTACKDAMAGE == DAMAGESLOW){
					next.health -= 20;
				}
			}
		}
		
		if(Delay >= 250){
			attacking = null;
		}
	}
	
	protected Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
