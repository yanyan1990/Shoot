package cs.imu.edu;


public class BossPlane extends Enemy implements Plane{
	private int speed;
	private int score;
	
	public BossPlane(){
		super();
		speed = 2;
		score = 0;
	}
	
	public int getScore(){
		return 10;
	}
	
	public void step(){
		y += speed;
	}
	
	public boolean outOfBounds(){
		return this.y >= ShootGame.HEIGHT;
	}
}
