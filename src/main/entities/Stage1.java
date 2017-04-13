package main.entities;

public class Stage1 {
	@SuppressWarnings("unused")
	private long systemTime;
	@SuppressWarnings("unused")
	private long startTime;
	
	public Stage1(){
		this.startTime = System.nanoTime();
	}
	
	public void begin(long whatTime){
		systemTime = System.nanoTime();
	}
}
