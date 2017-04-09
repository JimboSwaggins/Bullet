package main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import main.engine.GamePanel;

public class pTalis extends eTalis{
	private double stx;
	private double t;
	private double sty;
	private Bad creator;
	public pTalis(int angle, double x, double y, double speed, int radius, Color COLOR, Bad creator) {
		super(angle, x, y, speed, radius, COLOR);
		stx = creator.getX();
		sty = creator.getY();
		this.creator = creator;
	}
	
	public boolean update(){
		this.t += 0.01;
		stx = creator.getX();
		stx = creator.getX();
		sty = creator.getY();
		this.x = stx + (Math.sin(2 * t))*60;
		this.y = sty + (Math.sin(3 * t))*60;
		return false;
	}
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
	}
	
}
