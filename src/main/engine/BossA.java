package main.engine;

import java.awt.Color;

import main.engine.GamePanel.GameState;

public class BossA extends Bad{
	
	public BossA(){
		x = 200;
		y = 200;
		rank = type.BOSS;
		r = 4;
		
		secondStage = false;
		
		health = 180;
		startHealth = 180;
		firing = false;
		shotTimer1 = System.nanoTime();
		shotTimer2 = System.nanoTime();
		shotTimer3 = System.nanoTime();
		
		shot1Reload = 200;
		shot2Reload = 200;
		shot3Reload = 500;
		
		badTimer1 = System.nanoTime();
		badShot1 = 300;
		
		dead = false;
		
		thiscolor = Color.BLUE;
	}
	
	public void movPat(){
		if(health > 80){
			if(top){
				if(y-1 < 0){
					bottom = true;
					top = false;
				}else{
					y--;
				}
			}
			if(bottom){
				if(y+1 > GamePanel.Height){
					bottom = false;
					top = true;
				}
				else{
					y++;
				}
			}
		}
		if(secondStage == true){
			x += 0;
			y +=0;
		}
		if(health <= 80 && secondStage == false){
			health = 80;
			goToPoint(200, 200);
			for(int i = 0; i < GamePanel.eShot.size(); i++){
				GamePanel.eShot.remove(i);
				GamePanel.point();
			}
		}
	}
	
	public void update(Player player){
		movPat();

		firing = true;
		if(firing && GamePanel.getState() == GameState.PAUSE){
				
		}
		if(firing && GamePanel.getState() != GameState.PAUSE){
			long bShot1T = (System.nanoTime() - shotTimer1 - GamePanel.getPause()) / 1000000;
				long bShot2T =  (System.nanoTime() - shotTimer2 - GamePanel.getPause()) / 1000000;
				long bShot3T = (System.nanoTime() - shotTimer3 - GamePanel.getPause()) / 1000000;
				if(secondStage == false&&health > 80){
					if(bShot1T > shot1Reload&&top&&y<300){
						spiral(36, 0, 10, 1, 6, Color.RED, 6);
							shotTimer1 = System.nanoTime();
						}
						
						if(bShot2T > shot2Reload&&bottom&&y>50){
							targeted(3, 25, 3, 6, Color.BLUE, player);
							shotTimer2 = System.nanoTime();
						}
					}
					if(secondStage == true){
						if(bShot3T > shot3Reload){
							blast(10, 70, 3, 3, 3, Color.RED, player);
							shotTimer3 = System.nanoTime();
							shot3Reload = ((health+5)*10);
						}
					}
			}
		}
		
	}
