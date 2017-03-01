package main.engine;

import java.awt.Color;
import java.awt.Graphics2D;

public class eTalis {
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
	public shotType rank;
	public int specInt;
	private int tick = 0;
	public enum shotType{
		LINEAR, QUADRATIC, FORWARDSIN
	}
	
	public eTalis(double angle, double x, double y, double speed, int radius, Color COLOR, int specInt, int type){
		this.x = x;
		this.y = y;
		r = radius;
		switch(type){
			case 0:
				this.rank = shotType.FORWARDSIN;
				break;
			case 1:
				this.rank = shotType.QUADRATIC;
				break;
			default:
				this.rank = shotType.LINEAR;
				break;
		}
		this.specInt = specInt;
		rad = Math.toRadians(angle);
		this.speed = speed;
		
		this.dx = Math.cos(rad) * speed;
		this.dy = Math.sin(rad) * speed;
		
		
		
		color1 = COLOR;
	}
	
	
	public eTalis(int angle, double x, double y, double speed, int radius, Color COLOR){
		this.rank = shotType.LINEAR;
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
		this.rank = shotType.LINEAR;
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
		
		switch(this.rank){
			case LINEAR:
			
			x += dx ;
			y += dy;
			break;
			case QUADRATIC:
			break;
			case FORWARDSIN:
				this.dx = Math.cos(rad) * speed * Math.abs(0.5 * Math.sin(tick/Math.PI));
				this.dy = Math.sin(rad) * speed * Math.abs(0.5* Math.sin(tick/Math.PI));
				x += dy;
				y += dx;
				break;
			default:
				x += dy;
				y += dx;
				break;
		}
		tick++;
		if(tick > 99999999){
			tick = 0;
		}
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
