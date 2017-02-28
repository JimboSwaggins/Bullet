package main.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class tracker {
	private int x;
	private int y;
	private int r;
	
	private double rot; 
	
	private boolean firing;
	private long firingTimer;
	private long firingTimer2;
	private long firingDelay;
	private long firingD2;
	private Color color1;
	
	private int health;
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getR(){return r;}
	
	private boolean dead;
	public boolean isDead(){return dead;}
	public tracker(int x, int y){
		this.x = x;
		this.y = y;
		r = 4;
		
		
		health = 50;
		firing = false;
		firingTimer = System.nanoTime();
		firingTimer2 = System.nanoTime();
		firingDelay = 200;
		firingD2 = 200;
		
		dead = false;
		
		color1 = Color.BLUE;
	}
	
	private boolean top = true;
	private boolean bottom = false;	
	public void movPat(){
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
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			long elapsed2 =  (System.nanoTime() - firingTimer2) / 1000000;
			if(elapsed > firingDelay&&top&&y<300){
				spiral(36, 0, 10, 1, 6, Color.RED);
				firingTimer = System.nanoTime();
			}
			if(elapsed2 > firingD2&&bottom){
				targeted(1, 0, 7, 6, Color.BLACK, player);
				firingTimer2 = System.nanoTime();
			}
		}
		
	}
	
	private void spiral (int amount, double start, double change, double speed, int radius, Color COLOR){
		for(int i = 0; i < amount; i++){
			GamePanel.eShot.add(new eTalis(this.rot - (start + (change*i)), x, y, speed, radius, COLOR));
		}
	}
	
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
	
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval(x-r, y-r, 2*r, 2*r);
		
		g.setStroke(new BasicStroke(3));
		g.setStroke(new BasicStroke(1));
	}
}
