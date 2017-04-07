package main.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.engine.GamePanel.GameState;
import main.entities.*;

public class Bad {
	private double x;
	private double y;
	private int r;
	
	private double rot; 
	
	private boolean firing;
	private long shotTimer1;
	private long shotTimer2;
	private long shotTimer3;
	
	private long shot1Reload;
	private long shot2Reload;
	private long shot3Reload;

	private Color thiscolor;
	private boolean secondStage;
	
	
	private long badTimer1;
	private long badShot1;
	
	private int health;
	
	public enum type{
		BOSS, BADDIE;
	}
	
	private type rank;
	
	public double getX(){return x;}
	public double getY(){return y;}
	public int getR(){return r;}
	
	private boolean dead;
	public boolean isDead(){return dead;}
	
	//custom enemies lolff
	public Bad(int x, int y, Color color, int reloadA, int reloadB){
		this.x = x;
		this.y = y;
		this.rank = type.BOSS;
		r = 4;
		
		
		health = 200;
		firing = false;
		shotTimer1 = System.nanoTime();
		shotTimer2 = System.nanoTime();
		
		
		shot1Reload = reloadA;
		shot2Reload = reloadB;
		
		dead = false;
		
		thiscolor = color;
	}
	
	//Default Enemy Constructor
	public Bad(int x, int y, type level){
		this.x = x;
		this.y = y;
		this.rank = level;
		r = 4;
		
		secondStage = false;
		
		health = 180;
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
	public int getHealth(){
		return this.health;
	}
	private boolean top = true;
	private boolean bottom = false;	
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

	
	private void goToPoint(int x, int y){
		double dx = (x - this.x);
		double dy = (y - this.y);
		
		double angle = (Math.atan2(dy, dx));
		if((x - 3) <= this.x||this.x <= (x + 3)){
			this.x += Math.cos(angle) * 1;
		}
		if((y - 3) <= this.y||this.y <= (y + 3)){
			this.y += Math.sin(angle) * 1;
		}
		if((int)this.x == x+1||(int)this.x == x-1&&(int)this.y == y+1||(int)this.y == y-1){
			this.secondStage = true;
			this.x = x;
			this.y = y;
		}
	}
	
	public void hit(){
		this.health--;
		if(health <= 0){
			dead = true;
		}
	}

	
	public void update(Player player){
		movPat();

		firing = true;
		if(firing && GamePanel.getState() == GameState.PAUSE){
			
		}
		if(firing && GamePanel.getState() != GameState.PAUSE){
			switch(rank){
				case BOSS:
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
					break;
				case BADDIE:
					long badShot1T = (System.nanoTime() - badTimer1) / 1000000;
					if(badShot1T > badShot1){
						targeted(5, 5, Color.RED, player);
						badTimer1 = System.nanoTime();
					}
					break;
				default:
					System.out.println("NoClassDefined");
					break;
			}
		}
		
	}
	//Irregular Angle Spiral Shot Constructor
	private void spiral (int amount, double start, double change, double speed, int radius, Color COLOR, int rotChange){
		rot += rotChange;
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis(this.rot - (start + (change*i)), x, y, speed, radius, COLOR));
		}
	}
	
	
	//Multiple Angle Targeted Shot Constructor
	private void targeted (int amount, double change, double speed, int radius, Color COLOR, Player player){
		double start = (amount * change)/2;
		float angle = (float) Math.toDegrees(Math.atan2(player.getY() - y, player.getX() - x));
		rot = 0;
	    if(angle < 0){
	        angle += 360;
	    }
		
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis((angle - start) + (change * (i + 0.5)),  x, y, speed, radius, COLOR));
		}
	}
	//Single Targeted Shot Constructor
	private void targeted (double speed, int radius, Color COLOR, Player player){
		rot = 0;
		float angle = (float) Math.toDegrees(Math.atan2(player.getY() - y, player.getX() - x));
		GamePanel.eShot.add(new eTalis(angle, x, y, speed, radius, COLOR));
	}
	
	
	//Constrained Random Scatter Shot Constructor
	public void blast(int amount, int range, double speed, int variance, int radius, Color COLOR, Player player){
		rot = 0;
		for(int i = 0; i < amount; i++){
			Random rand = new Random();
			float angle = (float) Math.toDegrees(Math.atan2(player.getY() - y, player.getX() - x));
			GamePanel.eShot.add(new eTalis((rand.nextInt(range - 0) + (angle - (range / 2))), x, y, (rand.nextInt(variance + 1) + 1) + 1, radius, COLOR));
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(thiscolor);
		g.fillOval((int)(x)-r, (int)(y)-r, 2*r, 2*r);
		
		g.setStroke(new BasicStroke(3));
		g.setStroke(new BasicStroke(1));
	}
}
