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
	
	private boolean firing;
	private long bossTimer1;
	private long bossTimer2;
	private long bossTimer3;
	private long bossTimer4;
	private long bossShot1;
	private long bossShot2;
	private long bossShot3;
	private long bossShot4;
	private Color color1;
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
	
	public tracker(int x, int y, Color color, int reloadA, int reloadB){
		this.x = x;
		this.y = y;
		this.rank = type.BOSS;
		r = 4;
		
		
		health = 200;
		firing = false;
		bossTimer1 = System.nanoTime();
		bossTimer2 = System.nanoTime();
		bossShot1 = reloadA;
		bossShot2 = reloadB;
		
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
		
		health = 100;
		firing = false;
		bossTimer1 = System.nanoTime();
		bossTimer2 = System.nanoTime();
		bossShot1 = 200;
		bossShot2 = 200;
		
		badTimer1 = System.nanoTime();
		badShot1 = 300;
		
		dead = false;
		
		color1 = Color.BLUE;
	}
	public int getHealth(){
		return this.health;
	}
	private boolean top = true;
	private boolean bottom = false;	
	public void movPat(){
		if(health >= 80){
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
		
		if(health <= 80 && secondStage == false){
			health = 80;
			if(405 < x&&x < 400 &&405 < y&& y< 400){
				secondStage = true;
			}else{setHasTarget();}
		}
		if(health <= 80&&secondStage == true){
			spiral(36, 0, 10, 1, 6, Color.RED);
		}
	}
	
	
	public void setHasTarget(){
	    	double deltaX = 200 - x;
	        double deltaY = 200 - y;
	        double direction = Math.atan2(deltaY, deltaX); // Math.atan2(deltaY, deltaX) does the same thing but checks for deltaX being zero to prevent divide-by-zero exceptions
	        int speed = 5;
	        x = (getX() + (speed * Math.cos(direction)));
	        y = (getY() + (speed * Math.sin(direction)));
	}


	public void hit(){
		this.health--;
		if(health <= 0){
			dead = true;
		}
	}
	
	public void update(Player player){
		movPat();
		
		
		rot += 13;
		if(rot > 360){
			rot -= 360;
		}
		firing = true;
		
		if(firing){
			
			switch(rank){
				case BOSS:
					long bShot1T = (System.nanoTime() - bossTimer1) / 1000000;
					long bShot2T =  (System.nanoTime() - bossTimer2) / 1000000;
					if(bShot1T > bossShot1&&top&&y<300){
						spiral(36, 0, 10, 1, 6, Color.RED);
						bossTimer1 = System.nanoTime();
					}
					if(bShot2T > bossShot2&&bottom&&y>50){
						targeted(3, 45, 5, 6, Color.BLUE, player);
						bossTimer2 = System.nanoTime();
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
