package cs.imu.edu;

public class Bullet extends FlyingObject {
	private int speed;
	
	public Bullet(int x, int y){
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
		speed = 5;
	}
	
	public void step(){
		y -= speed;
	}
	
	public boolean outOfBounds(){
		return this.y <= -this.height;
	}
}
