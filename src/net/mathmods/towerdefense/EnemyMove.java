package net.mathmods.towerdefense;

public class EnemyMove {

	Enemy enemy;
	
	int xPos;
	int yPos;
	
	int routePosX;
	int routePosY;
	
	int health;
	
	EnemyAI AI;
	
	public EnemyMove(Enemy enemy, SpawnPoint sp){
		this.enemy = enemy;
		routePosX = sp.getX();
		routePosY = sp.getY();
		xPos = sp.getX() * (int)Screen.towerWidth + (int)Screen.towerWidth;
		yPos = sp.getY() * (int)Screen.towerHeight + (int)Screen.towerHeight;
		this.health = enemy.health;
		AI = new EnemyAI(this);
	}
	
	int currentDelay = 0;
	int moveRate = 500;
	
	public EnemyMove update(){
		EnemyMove current = this;
		if(current.health <= 0){
			current.health = 0;
			return current;
		}
		
		SpawnPoint sp = new SpawnPoint(routePosX, routePosY);
		
		if(currentDelay < moveRate){
			currentDelay++;
		}else{
			currentDelay = 0;
			sp = AI.getNextRoute();
			if(sp.getX() == -1 && sp.getY() == -1){
				current.health = -1;
				return current;
			}
		}
		
		if(sp.getX() != -1 && sp.getY() != -1){
			routePosX = sp.getX();
			routePosY = sp.getY();
			xPos = sp.getX() * (int)Screen.towerWidth + (int)Screen.towerWidth;
			yPos = sp.getY() * (int)Screen.towerHeight + (int)Screen.towerHeight;
		}
		
		return current;
	}
	
}
