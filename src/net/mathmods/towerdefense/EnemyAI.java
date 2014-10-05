package net.mathmods.towerdefense;

public class EnemyAI {

	public int lastX;
	public int lastY;
	public int nextX;
	public int nextY;
	public int X;
	public int Y;
	
	LevelReader lr = new LevelReader();
	int[][] map;
	
	public EnemyAI(EnemyMove enemy){
		X = enemy.routePosX;
		Y = enemy.routePosY;
		lastX = X;
		lastY = Y;
		map = lr.decode(lr.readLevel());
	}
	
	public SpawnPoint getNextRoute(){
		
		try{
		if(map[X + 1][Y] == 1 && X + 1 != lastX){
			nextX = X + 1;
			nextY = Y;
			X = X + 1;
			lastX = X - 1;
			lastY = Y;
		}else if(map[X - 1][Y] == 1 && X - 1 != lastX){
			nextX = X - 1;
			nextY = Y;
			X = X - 1;
			lastX = X + 1;
			lastY = Y;
		}else if(map[X][Y + 1] == 1 && Y + 1 != lastY){
			nextX = X;
			nextY = Y + 1;
			Y = Y + 1;
			lastX = X;
			lastY = Y - 1;
		}else if(map[X][Y - 1] == 1 && Y - 1 != lastY){
			nextX = X;
			nextY = Y - 1;
			Y = Y - 1;
			lastX = X;
			lastY = Y + 1;
		}
		}catch(ArrayIndexOutOfBoundsException e){
			return new SpawnPoint(-1, -1);
		}
		
		return new SpawnPoint(nextX, nextY);
	}
	
}
