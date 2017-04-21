package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.engine.GamePanel;

public abstract class Bad {
	protected double x;
	protected double y;
	protected int r;
	
	protected double rot; 
	
	protected boolean firing;
	protected long shotTimer1;
	protected long shotTimer2;
	protected long shotTimer3;
	
	protected long shot1Reload;
	protected long shot2Reload;
	protected long shot3Reload;
	protected Color thiscolor;
	protected boolean secondStage;
	
	protected int scoreValue;
	
	protected long badTimer1;
	protected long badShot1;
	
	protected int health;
	
	public enum type{BOSS, BADDIE;}
	
	protected type rank;
	
	protected int startHealth;
	public int getStartHealth(){return startHealth;}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public int getR(){return r;}
	
	protected boolean dead;
	public boolean isDead(){return dead;}
	
	public int getCurrHealth(){return this.health;}
	protected boolean top = true;
	protected boolean bottom = false;	
	public void movPat(){
	//TODO Add unimplemented methods
	}

	
	protected void goToPoint(int x, int y){
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
	public void hit(int damage){
		this.health -= damage;
		if(health <= 0){
			dead = true;
			GamePanel.setScore(this.scoreValue);
		}
	}
	
	
	public void hit(){
		this.health--;
		if(health <= 0){
			dead = true;
			GamePanel.setScore(this.scoreValue);
		}
	}

	
	public void update(Player player){
		///TODO Add unimplemented methods
	}
	
	//Irregular Angle Spiral Shot Constructor
	protected void spiral (double amount, double speed, int radius, Color COLOR, double rotChange){
		rot += rotChange;
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis(this.rot - ((360/amount) * i), x, y, speed, radius, COLOR));
		}
	}
	
	
	//Multiple Angle Targeted Shot Constructor
	protected void targeted (int amount, double change, double speed, int radius, Color COLOR, Player player){
		double start = (amount * change)/2;
		float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		rot = 0;
	    if(angle < 0){
	        angle += 360;
	    }
		
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis((angle - start) + (change * (i + 0.5)),  x, y, speed, radius, COLOR));
		}
	}
	//Single Targeted Shot Constructor
	protected void targeted (double speed, int radius, Color COLOR, Player player){
		rot = 0;
		float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		GamePanel.eShot.add(new eTalis(angle, x, y, speed, radius, COLOR));
	}
	
	
	//Constrained Random Scatter Shot Constructor
	protected void blast(int amount, int range, double speed, int variance, int radius, Color COLOR, Player player){
		rot = 0;
		for(int i = 0; i < amount; i++){
			Random rand = new Random();
			float angle = (float) Math.toDegrees(Math.atan2(player.y - y, player.x - x));
			GamePanel.eShot.add(new eTalis((rand.nextInt(range - 0) + (angle - (range / 2))), x, y, (rand.nextInt(variance + 1) + 1) + 1, radius, COLOR));
		}
	}
	
	protected void parametric(int speed, int radius, Color COLOR, Bad BAD){
		GamePanel.eShot.add(new pTalis(0, x, y, speed, radius, COLOR, BAD, 1));
	}
	
	public void draw(Graphics2D g){
		
	}
}
