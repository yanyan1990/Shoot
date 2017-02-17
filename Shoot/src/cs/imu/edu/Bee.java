package cs.imu.edu;

import java.util.Random;

public class Bee extends Enemy implements Award{
	private int xSpeed;
	private int ySpeed;
	private int awardType;
	
	public Bee(){
		super();
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2);
	}
	
	public int getType(){
		return awardType;
	}
	
	public void step(){
		x += xSpeed;
		y += ySpeed;
		if(x >= ShootGame.WIDTH - this.width){
			xSpeed = -1;
		}
		if(x <= 0){
			xSpeed = 1;
		}
	}
	
	public boolean outOfBounds(){
		return this.y >= ShootGame.HEIGHT;
	}
}
