package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Fairy extends Bad{
	public Fairy(int x, int y){
		this.x = x;
		this.y = y;
		
		rank = type.BADDIE;
		r = 5;
		
		shotTimer1 = System.nanoTime();
		
		health = 20;
		shot1Reload = 400;
		
		dead = false;
		
		thiscolor = Color.gray;
	}
	public void movPat(){
	if(x > 400 || y > 400){
		this.dead = true;
	}
		else{
			this.x++;
			y += 0.7;
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
