package main.entities;

import java.util.Timer;
import java.util.TimerTask;

import main.engine.GamePanel;
import main.entities.Fairy.AI;

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
		stageTimer.schedule(meme, 1500, 500);
		TimerTask three = fairySpawn(100, 700, Fairy.AI.LQUAD, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(three, 2000, 500);
		TimerTask four = fairySpawn(0, 800, Fairy.AI.ASIN, Fairy.shotType.TARGET, 5);
		stageTimer.schedule(four, 2000, 500);
	}
	
}