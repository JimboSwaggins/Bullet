package main.engine;

import java.awt.Color;
import java.awt.Graphics2D;

public class Talis {
	private double x;
	private double y;
	
	private int r;
	
	private double dx;
	private double dy;
	
	@SuppressWarnings("unused")
	private double speed;
	private double rad;
	
	private Color color1;
	
	public double getX(){return x;}
	public double getY(){return y;}
	public double getR(){return r;}
	
	public Talis(double angle, int x, int y, double speed, int radius, Color COLOR){
		
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
		
		if(x < -r || x > GamePanel.Width + r||
				y < -r ||  y > GamePanel.Height + r){
			return true;
		}
		
		
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
	}
	
	
	
}
