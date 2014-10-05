package net.mathmods.towerdefense;

public class Player {

	int health;
	int money;
	
	public Player(User user) {
		health = user.startingHealth;
		money = user.startingMoney;
	}

}
