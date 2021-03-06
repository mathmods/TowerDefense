package net.mathmods.towerdefense;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {

	Thread thread = new Thread(this);
	
	Frame frame;
	
	User user;
	
	Wave wave;
	
	LevelReader lr = new LevelReader();
	
	int level = 1;
	
	public int hand = -1;
	
	public Tower[][] towermap = new Tower[22][14];
	public static int[][] map;
	public EnemyMove[] enemymap = new EnemyMove[200];
	
	private int fps = 0;
	public boolean showfps = false;
	
	public int scene;
	
	public EnemyMove[] towerAttacking = new EnemyMove[300];
	public Tower[] attackingBy = new Tower[300];
	
	public static double towerHeight;
	public static double towerWidth;
	
	public boolean running = false;
	
	public int page = 1;
	
	private boolean holdTower = false;
	private Tower tower = null;
	private int xOnScreen;
	private int yOnScreen;
	public static boolean debugging = false;
	public boolean pause = false;
	
	public Screen(Frame f){
		frame = f;
		
		frame.addKeyListener(new KeyHandler(this));
		
		frame.addMouseListener(new MousePress(this));
		frame.addMouseMotionListener(new MousePress(this));
		
		towerHeight = this.frame.getHeight() / 18;
		
		towerWidth = this.frame.getWidth() / 28;
		
		thread.start();
	}
	
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		
		if(scene == 0){
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		}else if(scene > 0){
		//BackGround
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		//PlayGrid
			for(int x = 0; x<22; x++){
				for(int y = 0; y<14; y++){
					if (map[x][y] == 1){
						g.setColor(Color.CYAN);
						g.fillRect((int)towerWidth + (x * (int)towerWidth), (int)towerHeight + (y * (int)towerHeight), (int)towerWidth, (int)towerHeight);
					}else{
						g.setColor(new Color(100, 228, 80));
						g.fillRect((int)towerWidth + (x * (int)towerWidth), (int)towerHeight + (y * (int)towerHeight), (int)towerWidth, (int)towerHeight);
						g.setColor(Color.BLACK);
						g.drawRect((int)towerWidth + (x * (int)towerWidth), (int)towerHeight + (y * (int)towerHeight), (int)towerWidth, (int)towerHeight);
					}
				}
			}
			for(int x = 0; x<22; x++){
				for(int y = 0; y<14; y++){
					if(towermap[x][y] != null){
						g.drawImage(Tower.towerList[towermap[x][y].id].texture, (int)towerWidth + (x * (int)towerWidth), (int)towerHeight + (y * (int)towerHeight), null);
						g.setColor(new Color(62, 82, 70, 100));
						g.fillOval((int)towerWidth + (x * (int)towerWidth) + (int)towerWidth/4 - (int)towerWidth*2, (int)towerHeight + (y * (int)towerHeight) + (int)towerHeight/4 - (int)towerHeight*2, towermap[x][y].range * (int)(towerWidth + towerWidth / 2), towermap[x][y].range * (int)(towerHeight + towerHeight / 2));
					}
				}
			}
			for(int i = 0; i<towerAttacking.length; i++){
				if(towerAttacking[i] != null){
					g.setColor(Color.RED);
					g.drawLine((int)(attackingBy[i].x * towerWidth + towerWidth + (towerWidth / 2)), (int)(attackingBy[i].y * towerHeight + towerHeight + (towerHeight / 2)), towerAttacking[i].xPos + (int)(towerWidth / 2), towerAttacking[i].yPos + (int)(towerHeight / 2));
				}
			}
		//Health, Money and Options
			g.setColor(Color.BLACK);
			g.drawRect(12, (15*(int)towerHeight) + 12, (int)(towerWidth*2 + towerWidth / 2), (int)(towerHeight*18 - (15*(int)towerHeight) - 24) / 3);
			g.drawRect(12, (15*(int)towerHeight) + 12 + (int)((towerHeight*18 - (15*(int)towerHeight) - 24) / 3), (int)(towerWidth*2 + towerWidth / 2), (int)(towerHeight*18 - (15*(int)towerHeight) - 24) / 3);
			g.drawRect(12, (15*(int)towerHeight) + 12 + (int)(((towerHeight*18 - (15*(int)towerHeight) - 24) / 3) * 2), (int)(towerWidth*2 + towerWidth / 2), (int)(towerHeight*18 - (15*(int)towerHeight) - 24) / 3);
			g.setFont(new Font("TimesRoman", Font.PLAIN, (int)towerWidth/2));
			g.drawString("Health: " + user.player.health, 16, (15*(int)towerHeight) + (int)towerHeight / 3 * 2 + 12);
			g.drawString("Money: " + user.player.money, 16, (15*(int)towerHeight) + (int)((towerHeight*18 - (15*(int)towerHeight) - 24) / 3) + (int)towerHeight / 3 * 2 + 12);
			g.drawString("Options", 16, (15*(int)towerHeight) + (int)((towerHeight*18 - (15*(int)towerHeight) - 24) / 3) * 2 + (int)towerHeight / 3 * 2 + 12);
		//Tower scroll button left
			g.drawRect((int)towerWidth*3-1, (15*(int)towerHeight) + 12, (int)towerWidth-10, (int)(towerHeight*18) - (15*(int)towerHeight) - 24);
			g.setFont(new Font("TimesRoman", Font.PLAIN, (int)towerWidth + 5));
			g.drawString("<", (int)towerWidth*3 + 3, (15*(int)towerHeight) + 12 + ((int)(towerHeight*18) - (15*(int)towerHeight) + 15) / 2);
			if(page == 1){
				g.setColor(new Color(255, 0, 0, 100));
				g.fillRect((int)towerWidth*3-1, (15*(int)towerHeight) + 12, (int)towerWidth-10, (int)(towerHeight*18) - (15*(int)towerHeight) - 24);
			}
		//Tower scroll button right
			g.setColor(Color.BLACK);
			g.drawRect((int)(towerWidth + towerWidth / 2 + (towerWidth*2 + towerWidth / 2) + (18*(int)towerWidth) + towerWidth / 4), (15*(int)towerHeight) + 12, (int)towerWidth-10, (int)(towerHeight*18) - (15*(int)towerHeight) - 24);
			g.drawString((">"), (int)(towerWidth + towerWidth / 2 + (towerWidth*2 + towerWidth / 2) + (18*(int)towerWidth) + towerWidth / 4) + 3, (15*(int)towerHeight) + 12 + ((int)(towerHeight*18) - (15*(int)towerHeight) + 15) / 2);
			if(Tower.towerList[(18 * 2) * page] == null){
				g.setColor(new Color(255, 0, 0, 100));
				g.fillRect((int)(towerWidth + towerWidth / 2 + (towerWidth*2 + towerWidth / 2) + (18*(int)towerWidth) + towerWidth / 4), (15*(int)towerHeight) + 12, (int)towerWidth-10, (int)(towerHeight*18) - (15*(int)towerHeight) - 24);
			}
		//Tower list
			for(int x = 0; x < 18; x++){
				for(int y = 0; y < 2; y++){
					if(Tower.towerList[x * 2 + y] != null){
						g.drawImage(Tower.towerList[x * 2 + y].texture, (int)(towerWidth + towerWidth / 2 + (towerWidth*2 + towerWidth / 2) + (x*(int)towerWidth)), (15*(int)towerHeight) + 12 + (y * (int)towerHeight), null);
						if(Tower.towerList[x * 2 + y].cost > this.user.player.money){
							g.setColor(new Color(255, 0, 0, 100));
							g.fillRect((int)(towerWidth + towerWidth / 2 + (towerWidth*2 + towerWidth / 2) + (x*(int)towerWidth)), (15*(int)towerHeight) + 12 + (y * (int)towerHeight), (int)towerWidth, (int)towerHeight);
						}
					}
					g.setColor(Color.BLACK);
					g.drawRect((int)(towerWidth + towerWidth / 2 + (towerWidth*2 + towerWidth / 2) + (x*(int)towerWidth)), (15*(int)towerHeight) + 12 + (y * (int)towerHeight), (int)towerWidth, (int)towerHeight);
				}
			}
			for(int i = 0; i < enemymap.length; i++){
				if(enemymap[i] != null){
					g.drawImage(enemymap[i].enemy.texture, enemymap[i].xPos, enemymap[i].yPos, null);
				}
			}
			if(holdTower)g.drawImage(tower.texture, xOnScreen - (int)towerWidth/2, yOnScreen - (int)towerHeight/2, null);
			if(holdTower)g.drawImage(tower.texture, 0, 0, null);
		}else{
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
			g.setColor(Color.BLACK);
			g.drawRect(frame.getWidth() / 2 - (int)towerWidth*3, (int) (frame.getHeight() / 3 + towerHeight*5), 300, 75);
			g.drawRect(frame.getWidth() / 2 - (int)towerWidth*3, (int) (frame.getHeight() / 3 + towerHeight*3), 300, 75);
			g.setFont(new Font("TimesRoman", Font.PLAIN, (int)towerWidth * (int)1.5));
			g.drawString("Quit", (frame.getWidth() / 2 - (int)towerWidth*3) + (int)towerWidth * (int)1.5, (int) (frame.getHeight() / 3 + towerHeight*5 + 50));
			g.drawString("Main Menu", (frame.getWidth() / 2 - (int)towerWidth*3) + (int)towerWidth * (int)1.5, (int) (frame.getHeight() / 3 + towerHeight*3 + 50));
		}
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.setColor(Color.BLACK);
		if(showfps)g.drawString(fps + "", 10, 10);
	}
	
	public void loadGame(){
		user = new User(this);
		wave = new Wave(this);
		running = true;
	}
	
	public void save(){
		lr.saveData(level);
	}
	
	public void startGame(User user){
		user.createPlayer();
		
		map = lr.decode(lr.readLevel());
		
		this.scene = level; //level 1
	}
	
	public void run() {
		System.out.println("Success");
		
		loadGame();
		
		long lastFrame = System.currentTimeMillis();
		int frames = 0;
		
		while(running){
			repaint();
			update();
			
			if(scene > level){
				level = scene;
			}
			
			frames++;
			
			if(System.currentTimeMillis() - 1000 >= lastFrame){
				fps = frames;
				frames = 0;
				lastFrame = System.currentTimeMillis();
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		if(wave.wavespawning){
			if(!pause)wave.spawnEnemies();
		}
		for(int i = 0; i < enemymap.length; i++){
			if(enemymap[i] != null){
				if(!pause)enemymap[i].update();
				if(enemymap[i].health == -1){
					user.player.health -= enemymap[i].enemy.strenght;
					enemymap[i] = null;
				}else if(enemymap[i].health == 0){
					user.player.money += (int)(new Random().nextInt(enemymap[i].enemy.strenght));
					enemymap[i] = null;
				}
			}
		}
		for(int x = 0; x < 22; x++){
			for(int y = 0; y < 14; y++){
				if(towermap[x][y] != null){
					if(!pause){
						towermap[x][y].update(enemymap);
					if(towermap[x][y].attacking != null){
						towerAttacking[x * y] = towermap[x][y].attacking;
						attackingBy[x * y] = towermap[x][y];
					}else{
						towerAttacking[x * y] = null;
						attackingBy[x * y] = null;
					}
					}
				}
			}
		}
	}
	
	public void spawnEnemy(){
		for(int i = 0; i < enemymap.length; i++){
			if(enemymap[i] == null){
				enemymap[i] = new EnemyMove(Enemy.enemylist[0], lr.getSpawnPoint(lr.readLevel(level)));
				break;
			}
		}
	}
	
	public class KeyTyped{
		public void keyESC(){
			save();
			scene = -1;
			pause = true;
		}
		
		public void keyENTER(){
			if(!wave.wavespawning)wave.nextWave();
		}
		
		public void keySPACE(){
			if(scene == 0){
				startGame(user);
			}else if(scene == -1){
				scene = level;
				pause = false;
			}
		}
		
		public void toggleFPS(){
			if(showfps){
				showfps = false;
			}else{
				showfps = true;
			}
		}
		
		public void buttonQuit(){
			running = false;
			save();
			frame.dispose();
		}
		
		public void buttonMainMenu(){
			save();
			towermap = new Tower[22][14];
			map = new int[22][14];
			enemymap = new EnemyMove[200];
			scene = 0;
		}
		
		public void selectTower(int id){
			if(Tower.towerList[id].cost <= user.player.money){
				hand = Tower.towerList[id].id;
				holdTower = true;
				tower = Tower.towerList[id];
				if(debugging)System.out.println("Selected: " + Tower.towerList[id]);
			}else{
				if(debugging)System.err.println("Couldnt select: " + Tower.towerList[id]);
			}
		}
		
		public void placedTower(int x, int y){
			if(map[x][y] == 0){
				if(towermap[x][y] == null){
					if(hand != -1){
						if(user.player.money >= Tower.towerList[hand].cost){
							user.player.money -= Tower.towerList[hand].cost;
							towermap[x][y] = (Tower) Tower.towerList[hand].clone();
							towermap[x][y].x = x;
							towermap[x][y].y = y;
							if(debugging)System.out.println("Placed: " + Tower.towerList[hand]);
							holdTower = false;
							tower = null;
							hand = -1;
						}else if(debugging){
							DisposeTower();
							System.err.println("Couldnt place because of money");
						}
					}else if(debugging){
						System.err.println("Couldnt place because of hand");
					}
				}else if(debugging){
					System.err.println("Couldnt place because of occupied");
				}
			}else if(debugging){
				System.err.println("Couldnt place because of path");
			}
		}
		
		public void MoveWithTowerInHand(int x, int y){
			xOnScreen = x;
			yOnScreen = y;
		}
		
		public void DisposeTower(){
			if(debugging)System.out.println("Disposed");
			hand = -1;
			holdTower = false;
			tower = null;
		}
		
	}

}
