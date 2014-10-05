package net.mathmods.towerdefense;

public class User {

	private Screen screen;
	
	Player player;
	
	int startingMoney;
	int startingHealth;
	
	public User(Screen s){
		screen = s;
		
		startingMoney = 50;
		startingHealth = 100;
		
		this.screen.scene = 0;
	}
	
	public void createPlayer(){
		this.player = new Player(this);
	}
	
}
