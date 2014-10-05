package net.mathmods.towerdefense;

public class Wave {

	Screen s;
	
	int waveNumber = 0;
	int EnemiesTR = 0;
	int EnemiesPR = 10;
	
	boolean wavespawning = false;
	
	public Wave(Screen screen){
		s = screen;
	}
	
	private int currentDelay = 0;
	private int spawnRate = 1000;
	
	public void spawnEnemies(){
		if(EnemiesTR < waveNumber * EnemiesPR){
			if(currentDelay < spawnRate){
				currentDelay++;
			}else{
				currentDelay = 0;
				
				System.out.println("[Wave] Spawned Enemy");
				
				EnemiesTR++;
				s.spawnEnemy();
			}
		}else{
			wavespawning = false;
		}
	}
	
	public void nextWave(){	
		waveNumber++;
		EnemiesTR = 0;
		wavespawning = true;
		
		System.out.println("[Wave] Wave " + waveNumber + " incomming");
		
		for(int i = 0; i < s.enemymap.length; i++){
			s.enemymap[i] = null;
		}
		
	}
	
}
