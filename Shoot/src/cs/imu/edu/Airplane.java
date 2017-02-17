package cs.imu.edu;


public class Airplane extends Enemy implements Plane{
	private int speed;
	private int score;
	
	public Airplane(){
		super();
		speed = 2;
		score = 0;
	}
	
	public int getScore(){
		return 5;
	}
	
	public void step(){
		y += speed;
	}
	
	public boolean outOfBounds(){
		return this.y >= ShootGame.HEIGHT;
	}
}
