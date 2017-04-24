package main.entities;

import java.util.Timer;
import java.util.TimerTask;

public class Stage1 extends Stage{
	public static Timer stageTimer;
	public static TimerTask todo;
	public long startTime;
	public Stage1(){
		isRunning = false;
		stageTimer = new Timer();
		stageTimerTimer = new Timer();
		stagePoint = 0;
		todo = fairySpawn(400, 400, Fairy.AI.LEFT, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(fairySpawn(50, 800, Fairy.AI.CQUAD, Fairy.shotType.TARGET, 5), 0, 500);
		TimerTask meme = fairySpawn(600, 200, Fairy.AI.RQUAD, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(meme, 4000, 500);
		TimerTask three = fairySpawn(100, 700, Fairy.AI.LQUAD, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(three, 10000, 500);
		TimerTask four = fairySpawn(0, 750, Fairy.AI.ASIN, Fairy.shotType.SPIRAL, 5);
		stageTimer.schedule(four, 20000, 500);
		TimerTask five = fairySpawn(0, 0, Fairy.AI.BCOS, Fairy.shotType.SPIRAL, 5);
		stageTimer.schedule(five, 25000, 500);
	
	}	
	
}