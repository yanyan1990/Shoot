package cs.imu.edu;

import java.util.Random;

public class BigYellowBee extends Enemy implements Award{
	private int xSpeed;
	private int ySpeed;
	private int awardType;
	
	public BigYellowBee(){
		super();
		xSpeed = 2;
		ySpeed = 3;
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
			xSpeed = -2;
		}
		if(x <= 0){
			xSpeed = 2;
		}
	}
	
	public boolean outOfBounds(){
		return this.y >= ShootGame.HEIGHT;
	}
}
