package main.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class tracker {
	private double x;
	private double y;
	private int r;
	
	private double rot; 
	
	private long shotTimer1;
	private long shotTimer2;
	private long shotTimer3;
	private long shot1ReloadTime;
	private long shot2reloadTime;
	private long shot3ReloadTime;
	private Color color1;
	private boolean secondStage;

	
	private int health;
	public int getHealth(){
		return this.health;
	}
	
	public enum type{
		BOSS, BADDIE;
	}
	
	private type rank;
	
	public double getX(){return x;}
	public double getY(){return y;}
	public int getR(){return r;}
	
	private boolean dead;
	public boolean isDead(){return dead;}
	
	public tracker(int x, int y, Color color, int reloadA, int reloadB){
		this.x = x;
		this.y = y;
		this.rank = type.BOSS;
		r = 4;
		
		
		health = 200;
		shotTimer1 = System.nanoTime();
		shotTimer2 = System.nanoTime();
		
		
		shot1ReloadTime = reloadA;
		shot2reloadTime = reloadB;
		
		dead = false;
		
		color1 = color;
	}
	
	//Default Enemy Constructor
	public tracker(int x, int y, type level){
		this.x = x;
		this.y = y;
		this.rank = level;
		r = 4;
		
		secondStage = false;
		
		health = 180;
		shotTimer1 = System.nanoTime();
		shotTimer2 = System.nanoTime();
		shotTimer3 = System.nanoTime();
		
		shot1ReloadTime = 200;
		shot2reloadTime = 200;
		shot3ReloadTime = 500;
		
		shotTimer1 = System.nanoTime();
		shot1ReloadTime = 300;
		
		dead = false;
		
		color1 = Color.BLUE;
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
		rot +=5;
		switch(rank){
			case BOSS:
				long bShot1T = (System.nanoTime() - shotTimer1) / 1000000;
				long bShot2T =  (System.nanoTime() - shotTimer2) / 1000000;
				long bShot3T = (System.nanoTime() - shotTimer3) / 1000000;
				if(secondStage == false&&health > 80){
					if(bShot1T > shot1ReloadTime&&top&&y<300){
						spiral(36, 0, 10, 1, 6, Color.RED);
						shotTimer1 = System.nanoTime();
					}
					if(bShot2T > shot2reloadTime&&bottom&&y>50){
						targeted(3, 45, 5, 6, Color.BLUE, player);
						shotTimer2 = System.nanoTime();
					}
				}
				if(secondStage == true){
					if(bShot3T > shot3ReloadTime){
						blast(10, 70, 3, 3, 3, Color.RED, player);
						shotTimer3 = System.nanoTime();
						shot3ReloadTime = ((health+5)*10);
					}
				}
				break;
			case BADDIE:
				long badShot1T = (System.nanoTime() - shotTimer1) / 1000000;
				if(badShot1T > shot1ReloadTime){
					targeted(5, 5, Color.RED, player);
					shotTimer1 = System.nanoTime();
				}
				break;
			default:
				System.out.println("NoClassDefined");
				break;
		}
	}
	//Irregular Angle Spiral Shot Constructor
	private void spiral (int amount, double start, double change, double speed, int radius, Color COLOR){
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis(this.rot - (start + (change*i)), x, y, speed, radius, COLOR));
		}
	}
	
	
	//Multiple Angle Targeted Shot Constructor
	private void targeted (int amount, double change, double speed, int radius, Color COLOR, Player player){
		double start = (amount * change)/2;
		float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));

	    if(angle < 0){
	        angle += 360;
	    }
		
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis((angle - start) + (change * (i + 0.5)),  x, y, speed, radius, COLOR));
		}
	}
	//Single Targeted Shot Constructor
	private void targeted (double speed, int radius, Color COLOR, Player player){
		float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		GamePanel.eShot.add(new eTalis(angle, x, y, speed, radius, COLOR));
	}
	
	
	//Constrained Random Scatter Shot Constructor
	public void blast(int amount, int range, double speed, int variance, int radius, Color COLOR, Player player){
		for(int i = 0; i < amount; i++){
			Random rand = new Random();
			float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));
			GamePanel.eShot.add(new eTalis((rand.nextInt(range - 0) + (angle - (range / 2))), x, y, (rand.nextInt(variance + 1) + 1) + 1, radius, COLOR));
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval((int)(x)-r, (int)(y)-r, 2*r, 2*r);
		
		g.setStroke(new BasicStroke(3));
		g.setStroke(new BasicStroke(1));
	}
}
