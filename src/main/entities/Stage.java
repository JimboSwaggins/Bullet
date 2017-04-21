package main.entities;

import java.util.Timer;
import java.util.TimerTask;

import main.engine.GamePanel;
import main.entities.Fairy.AI;
import main.entities.Fairy.shotType;

public class Stage{
	protected int stagePoint;
	protected boolean isRunning;
	protected Timer stageTimerTimer;
	
	public TimerTask fairySpawn(int x, int y, AI movPat, shotType shotPat, int num){
		TimerTask spFairy = new TimerTask(){
			int i = 0;
			public void run() {
				if(i < num){
					GamePanel.eList.add(new Fairy(x, y, movPat, shotPat));
					i++;
				}else{
					this.cancel();
					}
				}
			};
		return spFairy;
	}
	
	protected boolean running;
}
		