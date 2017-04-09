package main.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.engine.GamePanel;

public class BossA extends Bad{
	
	public BossA(){
		x = 200;
		y = 200;
		rank = type.BOSS;
		r = 8;
		
		secondStage = false;
		
		health = 180;
		startHealth = 180;
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
							shotTimer2 = System.nanoTime();
							shot2Reload = ((health+5)*10);
						}
					}
		}
	public void draw(Graphics2D g){
		g.setColor(thiscolor);
		g.fillOval((int)(x)-r, (int)(y)-r, 2*r, 2*r);
		
		g.setStroke(new BasicStroke(3));
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.ORANGE);
		long a = (350*this.getCurrHealth()/this.getStartHealth());
		g.fillRect(10, 10, (int)a, 10);
		g.setColor(Color.RED);
		g.fillRect((10+(350*80/180)), 10, 4, 10);
			
	}
	
	}
