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
		stageTimer.schedule(fairySpawn(400, 400, Fairy.AI.LEFT, Fairy.shotType.TARGET, 5), 0, 500);
		TimerTask meme = fairySpawn(400, 400, Fairy.AI.RIGHT, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(meme, 1500, 500);
		TimerTask three = fairySpawn(200, 200, Fairy.AI.DOWN, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(three, 2000, 500);
	}
	
}