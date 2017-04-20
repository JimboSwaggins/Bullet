package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Fairy extends Bad{
	private AI AI;
	private shotType pattern;
	
	public Fairy(int x, int y, AI move, shotType fpat){
		this.x = x;
		this.y = y;
		
		rank = type.BADDIE;
		r = 10;
		this.AI = move;
		this.pattern = fpat;
		shotTimer1 = System.nanoTime();
		
		health = 100;
		shot1Reload = 400;
		
		this.scoreValue = 200;
		dead = false;
		
		thiscolor = Color.gray;
	}
	
	public enum AI{
		DOWN, LEFT, RIGHT, UP, RQUAD, LQUAD
	}
	
	public enum shotType{
		TARGET
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
		case RQUAD:
			x = 450 * Math.sin(y * 0.003) + 40;
			y++;
		case LQUAD:
			x = 450 * Math.sin(y * 0.003) + 40;
		default:
			break;
				
		}
	}
	
	public void firingPat(Player player){
		long bShot1T = (System.nanoTime() - shotTimer1) / 1000000;
		if(bShot1T > shot1Reload){
			switch(pattern){
			case TARGET:
				targeted(3, 5, Color.RED, player);
				break;
			default:
				break;
			}
			shotTimer1 = System.nanoTime();
		}
	}
	
	public void update(Player player){
		movPat();
		firingPat(player);
	}
	
	public void draw(Graphics2D g){
		g.setColor(thiscolor);
		g.fillOval((int)(this.x)-r, (int)(this.y)-r, 2*r, 2*r);
	}
	
	
}
