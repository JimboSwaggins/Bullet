package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class eTalis {
	protected double x;
	protected double y;
	
	protected int r;
	
	protected double dx;
	protected double dy;
	
	@SuppressWarnings("unused")
	protected double speed;
	protected double rad;
	
	protected Color color1;
	
	public double getX(){return x;}
	public double getY(){return y;}
	public double getR(){return r;}
	
	
	public enum shotType{
		LINEAR, QUADRATIC
	}
	
	public eTalis(int angle, double x, double y, double speed, int radius, Color COLOR){
		
		this.x = x;
		this.y = y;
		r = radius;
		
		rad = Math.toRadians(angle);
		
		this.speed = speed;
		
		this.dx = Math.cos(rad) * speed;
		this.dy = Math.sin(rad) * speed;
		
		color1 = COLOR;
	}
	
	public eTalis(double angle, double x, double y, double speed, int radius, Color COLOR){
		this.x = x;
		this.y = y;
		r = radius;
		
		rad = Math.toRadians(angle);
		
		this.speed = speed;
		
		this.dx = Math.cos(rad) * speed;
		this.dy = Math.sin(rad) * speed;
		
		color1 = COLOR;
	}
	
	public boolean update(){
		x += dx;
		y += dy;
		
		if(x < -r || x > 400 + r||
				y < -r ||  y > 400 + r){
			return true;
		}
		
		
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
	}

}
