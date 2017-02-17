package cs.imu.edu;

import java.util.Random;

public abstract class Enemy extends FlyingObject {
	public Enemy(){
		if(this instanceof Airplane)
			image = ShootGame.airplane;
		else if(this instanceof Bee)
			image = ShootGame.bee;
		else if(this instanceof BigYellowBee)
			image = ShootGame.bigyellowbee;
		else if(this instanceof BossPlane)
			image = ShootGame.bossplane;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH-this.width+1);
		y = -this.height;
	}
	
	public boolean shootBy(Bullet bullet){
		int x1 = this.x;
		int x2 = this.x + this.width;
		int y1 = this.y;
		int y2 = this.y + this.height;
		int x = bullet.x;
		int y = bullet.y;
		return x>=x1 && x<=x2
				&&
			   y>=y1 && y<=y2;
	}
}
