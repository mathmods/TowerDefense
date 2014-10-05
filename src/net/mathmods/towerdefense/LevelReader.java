package net.mathmods.towerdefense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class LevelReader {
	public String[][] readLevel(int level){
		BufferedReader br = null;
		
		String[][] Output = new String[22][15];
		
		try {
			File file = new File(System.getProperty("user.dir") + "\\src\\net\\mathmods\\towerdefense\\", "level" + level + ".level");
			br = new BufferedReader(new FileReader(file));
			try {
				String sCL;
				boolean layout = false;
				while((sCL = br.readLine()) != null){
					if(sCL.contains("LevelName")){
						Output[0][0] = sCL.substring(10);
					}else if(sCL.contains("LevelNumber")){
						Output[1][0] = sCL.substring(12);
					}else if(sCL.contains("Waves")){
						Output[2][0] = sCL.substring(6);
					}else if(sCL.contains("EnemyPWave")){
						Output[3][0] = sCL.substring(11);
					}else if(sCL.contains("LevelLayout")){
						layout = true;
					}
					
					if(layout == true){
						//for(int x = 1; x != 22; x++){
							for(int y = 1; y < 15; y++){
								String soM = br.readLine();
								for (int x = 0; x<22;x++){
									Output[x][y] = soM.substring(x, x+1);
								}
							}
						//}
					}
					
				}br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(Output[0][0] != "" && Output[1][0] != ""){
			return Output;
		}
		
		return null;
		
	}
	
	public String[][] readLevel(){
		
		String[][] Output;
		Output = new String[22][15];
		File f = new File(System.getProperty("user.dir") + "\\src\\net\\mathmods\\towerdefense\\", "Data.level");
		try {
			if(!f.exists()){
				f.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write(1);
				bw.close();
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			int l = Integer.parseInt(br.readLine());
				
			Output = readLevel(l);
			br.close();
		
		}catch(FileNotFoundException e) {
			saveData(1);
			Output = readLevel(1);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		}
		return Output;
	}
	
	public String readName(String[][] s){
		return s[0][0];
	}
	
	public int readNumber(String[][] s){
		return Integer.parseInt(s[1][0]);
	}
	
	public int readWaves(String[][] s){
		return Integer.parseInt(s[2][0]);
	}
	
	public int readEPW(String[][] s){
		return Integer.parseInt(s[3][0]);
	}
	
	public SpawnPoint getSpawnPoint(String[][] s){
		for(int x=0;x<22;x++){
			for(int y=0;y<14;y++){
				if (s[x][y+1].equals("S")){
					return new SpawnPoint(x, y);
				}
			}
		}
		
		return null;
	}
	
	public SpawnPoint getEnding(String[][] s){
		for(int x=0;x<22;x++){
			for(int y=0;y<14;y++){
				if (s[x][y+1].equals("E")){
					return new SpawnPoint(x, y);
				}
			}
		}
		
		return null;
	}
	
	public int[][] decode(String[][] s){
		int[][] out = new int[22][14];
		for(int x=0;x<22;x++){
			for(int y=0;y<14;y++){
				if (s[x][y+1].equals("P") || s[x][y+1].equals("S") || s[x][y+1].equals("E")){
					out[x][y] = 1;
				}else if(s[x][y+1].equals("N")) {
					out[x][y] = 0;
				}else{
					System.err.println("Couldnt process the info: " + s[x][y+1]);
				}
			}
		}
		return out;
	}
	
	public void saveData(int l){
		File f = new File(System.getProperty("user.dir") + "\\src\\net\\mathmods\\towerdefense\\", "Data.level");
		try {
			
			if(!f.exists()){
				f.createNewFile();
			}
			
			Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			w.write(l + "");
			w.close();
			
			System.out.println("Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
