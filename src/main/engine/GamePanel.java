package main.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.entities.Bad;
import main.entities.BossA;
import main.entities.Player;
import main.entities.Talis;
import main.entities.eTalis;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	public static int Width = 600;
	public static int Height = 400;
	
	private Thread thread;
	
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	public static long pauseStart;
	
	public static double paraTimer;
	public static double getPTimer(){
		return paraTimer;
	}
	
	public static double score;
	public static double graze;
	private int FPS;
	@SuppressWarnings("unused")
	private double averageFPS;
	
	public static void point(){
		score++;
	}
	
	public enum GameState{
		START, PLAY, END, PAUSE
	}
	
	public static GameState getState(){
		return state;
	}
	
	private static GameState state;
	public static BossA joo;

	public static Player lilly;
	public static ArrayList<Talis> shots;
	public static ArrayList<eTalis> eShot;
	public static ArrayList<Bad> eList;
	
	public GamePanel(){
		super();
		FPS = 60;
		setPreferredSize(new Dimension(Width, Height));
		setFocusable(true);
		requestFocus();
		score = 0;
		
		state = GameState.START;
		lilly = new Player();
		shots = new ArrayList<Talis>();
		eShot = new ArrayList<eTalis>();
		eList = new ArrayList<Bad>();
		joo = new BossA();
		eList.add(joo);
	}
	
	public static long getPause(){
		return pauseStart;
	}
	
	@Override
	public void keyPressed(KeyEvent Key) {
		int keyCode = Key.getKeyCode();
		switch(state){
		case PLAY:
			if(keyCode == KeyEvent.VK_P){
				state = GameState.PAUSE;
				pauseStart = System.nanoTime();
			}
			if(keyCode == KeyEvent.VK_LEFT){
				lilly.setLeft(true);
			}
			if(keyCode == KeyEvent.VK_RIGHT){
				lilly.setRight(true);
			}
			if(keyCode == KeyEvent.VK_UP){
				lilly.setUp(true);
			}
			if(keyCode == KeyEvent.VK_DOWN){
				lilly.setDown(true);
			}
			if(keyCode == KeyEvent.VK_SHIFT){
				lilly.setFocus(true);
				FPS = 1;
			}
			if(keyCode == KeyEvent.VK_Z){
				lilly.setFiring(true);
			}
			break;
		case END:
			break;
		case PAUSE:
			if(keyCode == KeyEvent.VK_P){
				state = GameState.PLAY;
			}
			break;
		case START:
			if(keyCode == KeyEvent.VK_S){
				state = GameState.PLAY;
				pauseStart = 0;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent Key) {
		int keyCode = Key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT){
			lilly.setLeft(false);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			lilly.setRight(false);
		}
		if(keyCode == KeyEvent.VK_UP){
			lilly.setUp(false);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			lilly.setDown(false);
		}
		if(keyCode == KeyEvent.VK_SHIFT){
			lilly.setFocus(false);
			FPS = 60;
		}
		if(keyCode == KeyEvent.VK_Z){
			lilly.setFiring(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent Key){
	}
	
	//Functions
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		
		addKeyListener(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
	
		running = true;
	
		image  = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 60;
		
		
		long targetTime = 1000 / FPS;
		
		
		
		while(running){
			
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime)/1000000;
			
			waitTime = targetTime - URDTimeMillis;
			
			try{
				thread.sleep(waitTime);
			}catch(Exception e){
				thread.interrupt();
			}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
			
		}
	}
	
	private void gameUpdate(){
		switch(state){
		case START:
			break;
		case PLAY:
			lilly.update();
			for(int i = 0; i < eList.size(); i++){
				eList.get(i).update(lilly);
				if(eList.get(i).isDead()){
					eList.remove(i);
					score += 500;
					i--;
				}
				
			}
			for(int i = 0; i < shots.size(); i++){
				boolean remove = shots.get(i).update();
				if(remove){
					shots.remove(i);
					i--;
					
				}
			}
			for(int j = 0; j < eShot.size(); j++){
				boolean remove = eShot.get(j).update();
				if(remove){
					eShot.remove(j);
					j--;
					
				}
			}
			
			for(int i = 0; i < shots.size(); i++){
				Talis b = shots.get(i);
				double bx = b.getX();
				double by = b.getY();
				double br = b.getR();
				for(int j = 0; j < eList.size(); j++){
				double ex = eList.get(j).getX();
				double ey = eList.get(j).getY();
				double er = eList.get(j).getR();
				
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx*dx + dy*dy);
		
				if(dist < br + er){
					eList.get(j).hit();
					shots.remove(i);
					i--;
					break;
				}
			}
			
				if(eList.isEmpty()){
					state = GameState.END;
				}
			}
			for(int k = 0; k < eShot.size(); k++){
					
					eTalis c = 	eShot.get(k);
					double px = c.getX();
					double py = c.getY();
					double projRadius = c.getR();
					
					double ex = lilly.getX();
					double ey = lilly.getY();
					double playerRadius = lilly.getR();
					
					double dx = px - ex;
					double dy = py - ey;
					double dist = Math.sqrt(dx*dx + dy*dy);
			
					if(dist < projRadius + playerRadius&&dist >= 2){
						graze++;
					}			
					if(dist < projRadius/2){
						score -= 150;
						eShot.remove(k);
						k--;
						break;
					}
					if(eList.size() == 0){
						for(int i = 0; i< eShot.size(); i++){
							eShot.remove(i);
							score += (1 + (0.1 * graze));
							i--;
						}
					}
				}
			break;
		case PAUSE:
			break;
		case END:
			break;
		default:
			break;
		}
	}

	private void gameRender(){
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 400, 400);
		g.setColor(Color.BLACK);
		
		g.fillRect(400, 0, 600, 400);
		
		switch(state){
		case START:
			g.drawString("This is a game", 50, 100);
			g.drawString("Press z to shoot", 50, 112);
			g.drawString("Press shift to go slower", 50, 124);
			g.drawString("Shoot at the blue dot. Don't touch ANYTHING.", 50, 136);
			g.drawString("Getting hit takes 150 points.", 50, 148);
			g.drawString("Get a high score please", 50, 160);
			g.drawString("Press s to start", 50, 172);
			break;
		case PLAY:
			for(int i = 0; i < shots.size(); i++){
				shots.get(i).draw(g);
			}
			for(int j = 0; j < eShot.size(); j++){
				eShot.get(j).draw(g);
			}
			for(int i = 0; i < eList.size(); i++){
				eList.get(i).draw(g);
			}
			lilly.draw(g);
			break;
		case PAUSE:
			g.drawString("paused", 1, 1);
			break;
		case END:
			g.drawString("Your score was:" + Double.toString((int)score), 150, 200);
			String howgood = null;
			if(score < 0){howgood = "F";};
			if(score > 1&&score < 499){howgood = "D";};
			if(score > 500&&score < 1000){howgood = "C";};
			if(score > 1001&&score < 2499){howgood = "B";};
			if(score > 2500&&score < 4999){howgood = "A";};
			if(score > 5000&&score < 7499){howgood = "S";};
			if(score > 7500&&score < 9999){howgood = "SS";};
			if(score > 10000){howgood = "SSS";};
			g.drawString(howgood, 150, 220);
			break;
		}
		g.setColor(Color.BLACK);
		g.fillRect(400, 0, 200, 400);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + Integer.toString((int)score), 450, 20);
		g.drawString("Graze:" + Integer.toString((int)graze), 450, 45);
		g.drawString("Ptim" + Double.toString(paraTimer), 450, 60);
		
	}
	
	private void gameDraw(){
		Graphics g2  = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
	}

}
