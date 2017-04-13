package main.entities;

public class memeSpiral {
	private int x;
	public int getX(){return this.x;}
	private int y;
	public int getY(){return this.y;}
	
	public memeSpiral(Player player){
		this.x = player.getX();
		this.y = player.getY();
		
		
	}
}
