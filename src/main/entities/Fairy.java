package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Fairy extends Bad{
	public Fairy(int x, int y){
		this.x = x;
		this.y = y;
		
		rank = type.BADDIE;
		r = 10;
		
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
		this.x += 0.25;
		y += 7 * Math.cos(0.2*x);
	}
	}
	public void update(Player player){
		movPat();
	}
	
	public void draw(Graphics2D g){
		g.setColor(thiscolor);
		g.fillRect((int)this.x - 20, (int)this.y -10, 40, 20);
	
	//for every shottype add a shottimer w/ system.nanotime
	
	
	//should be square
	}
}
