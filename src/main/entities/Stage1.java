package main.entities;

import main.engine.GamePanel;

public class Stage1 {
	private long startTime;
	
	public Stage1(){
		this.startTime = System.currentTimeMillis();
	}
	
	public boolean begin(){
		long curt = (System.currentTimeMillis() - this.startTime);
		if(curt%333 <= 1000/60 && curt <= 8000){
			GamePanel.eList.add(new Fairy(90, 450, Fairy.AI.RIGHT, Fairy.shotType.TARGET));
		}
		if(curt%500 <= 1000/60 && curt > 8000 && curt < 12000){
			GamePanel.eList.add(new Fairy(90, 450, Fairy.AI.LEFT, Fairy.shotType.TARGET));
		}
		if(curt >= 60000){
			return true;
		}
		return false;
	}

}
