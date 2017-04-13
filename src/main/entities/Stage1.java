package main.entities;

public class Stage1 {
	private long systemTime;
	private long startTime;
	
	public Stage1(){
		this.startTime = System.nanoTime();
	}
	
	public void begin(long whatTime){
		systemTime = System.nanoTime();
	}
}
