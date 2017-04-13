package main.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.engine.GamePanel;

public class Player {

	int x;
	int y;
	private int r;
	
	
	private double rot; 
	
	private double rotSpeed;
	private int dx;
	private int dy;
	private int speed;
	
	private int lives;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean focus;
	private boolean isRot;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	//Bombs
	private int bombs;
	public int getBombs(){return this.bombs;}
	public void setBombs(int Bombs){this.bombs +=  Bombs;}
	
	
	private Color color1;
	public void setLeft(boolean a){this.left = a;}
	public void setRight(boolean a){this.right = a;}
	public void setUp(boolean a){this.up = a;}
	public void setDown(boolean a){this.down = a;}
	public void setFocus(boolean a){this.focus = a;}
	public void setFiring(boolean a){this.firing = a;}
	public void setRot(boolean a){this.isRot = a;}
	public String getRot(){return Double.toString(this.rot);}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getR(){return r;}
	
	
	
	public Player(){
		rot = 270;
		x = 400;
		y = 800;
		lives = 999;
		r = 8;
		
		dx = 0;
		dy = 0;
		speed = 5;
		
		bombs = 3;
		rotSpeed = 7;
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = 50;
		
		color1 = Color.BLACK;
	}
	
	public void update(){
		if(focus){
			speed = 2;
		}
		else{
			speed = 10;
			
		}
		
		if(isRot){
			if((this.rot + rotSpeed) > 360){
				this.rot = 0;
			}
			else{
				this.rot += rotSpeed ;
			}
		}
		if(right){
			dx += speed;
		}
		if(left){
			dx -= speed;
		}
		if(up){
			dy -= speed;
		}
		if(down){
			dy += speed;
		}
		
		x+= dx;
		y+= dy;
		
		if(x < r) x = r;
		if(y < r) y = r;
		if(x > 800 - r) x = 800 - r;
		if(y > 800 - r) y = 800 - r;
		
		dx = 0;
		dy = 0;
	
		
		if(firing){
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			if(elapsed > firingDelay){
				spiral(1, 0, 0, 15, 4, Color.BLACK );
				firingTimer = System.nanoTime();
				
			}
		}

	}
	
	public void useBomb(){
		if(this.bombs > 0){
			for(int i = 0; i < GamePanel.eList.size(); i++){
				GamePanel.eList.get(i).hit(100);
			}
			for(int i = 0; i < GamePanel.eShot.size(); i++){
				GamePanel.eShot.clear();
			}
			bombs--;
			new memeSpiral(this);
		}
		else{
			System.out.println("LOL NOP");
		}
	}
	
	private void spiral (int amount, double start, double change, double speed, int radius, Color COLOR){
		for(int i = 0; i < amount; i++){
			GamePanel.shots.add(new Talis(this.rot - (start + (change*i)), x, y, speed, radius, COLOR));
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval(x-r, y-r, 2*r, 2*r);
		
		g.setStroke(new BasicStroke(3));
		g.setStroke(new BasicStroke(1));
	}
	
	public void setLives(int lives){
		this.lives = lives;
	}
	
	public int getLives(){
		return lives;
	}

	
}

