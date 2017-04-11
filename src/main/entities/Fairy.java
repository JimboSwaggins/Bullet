package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Fairy extends Bad{
	private AI AI;
	public Fairy(int x, int y, AI move){
		this.x = x;
		this.y = y;
		
		rank = type.BADDIE;
		r = 5;
		this.AI = move;
		shotTimer1 = System.nanoTime();
		
		health = 20;
		shot1Reload = 400;
		
		dead = false;
		
		thiscolor = Color.gray;
	}
	
	enum AI{
		DOWN, LEFT, RIGHT, UP, RQUAD, LQUAD
	}
	
	
	public void movPat(){
	if(x > 800 || y > 800){
		this.dead = true;
	}
		switch(AI){
		case UP:
			y--;
			break;
		case DOWN:
			y++;
			break;
		case LEFT:
			x++;
			break;
		case RIGHT:
			x--;
			break;
		default:
			break;
				
		}
	}
	
	public void firingPat(){
		switch(AI){
		case RIGHT:
		default:
			break;
		}
	}
	
	public void update(Player player){
		movPat();
	}
	
	public void draw(Graphics2D g){
		g.setColor(thiscolor);
		g.fillOval((int)(this.x)-r, (int)(this.y)-r, 2*r, 2*r);
	
	//for every shottype add a shottimer w/ system.nanotime
	
	
	//should be square
	}
	
	
}
