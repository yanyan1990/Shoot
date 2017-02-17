package cs.imu.edu;
import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {
	private int life;
	private int doubleFire;
	private BufferedImage[] images;
	private int index;
	
	public Hero(){
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
		life = 3;
		doubleFire = 100;
		images = new BufferedImage[]{ ShootGame.hero0, ShootGame.hero1 };
		index = 0;
	}
	
	public void step(){
		index++;
		int a = index/10;
		int b = a%2;
		image = images[b];
	}
	
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire >= 200){      //三倍火力
			Bullet[] bs = new Bullet[3];
			bs[0] = new Bullet(this.x + 1*xStep, this.y - yStep);
			bs[1] = new Bullet(this.x + 2*xStep, this.y - yStep);
			bs[2] = new Bullet(this.x + 3*xStep, this.y - yStep);
			doubleFire -= 10;
			return bs;
		}else if(doubleFire >= 150){       //双倍火力
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x + 1*xStep, this.y - yStep);
			bs[1] = new Bullet(this.x + 3*xStep, this.y - yStep);
			doubleFire -= 5;
			return bs;
		}else if(doubleFire > 0){                             //单倍火力
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x + 2*xStep, this.y - yStep);
			return bs;
		}else{
			doubleFire = 0;
			return null;
		}
	}
	
	public void moveTo(int x, int y){
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	public boolean outOfBounds(){
		return false;
	}
	
	public void addLife(Award award){
		if(award instanceof Bee){
			life++;
		}else{
			life += 3;
		}
	}
	
	public int getLife(){
		return life;
	}
	
	public void substractLife(Enemy enemy){
		if(enemy instanceof Airplane){
			life--;
		}else if(enemy instanceof BossPlane){
			life -= 3;
		}else if(enemy instanceof Bee){
			life--;
		}else{
			life -= 3;
		}
	}
	
	public void addDoubleFire(Award award){
		if(award instanceof Bee){
			doubleFire += 20;
		}else{
			doubleFire += 40;
		}
	}
	
	public void substractDoubleFire(Enemy enemy){
		if(enemy instanceof Bee){
			doubleFire -= 30;
		}else{
			doubleFire -= 50;
		}
	}
	
	public int getDoubleFire(){
		return doubleFire;
	}
	
	public boolean hit(Enemy enemy){
		int x1 = enemy.x - this.width/2;
		int x2 = enemy.x + enemy.width + this.width/2;
		int y1 = enemy.y - this.height/2;
		int y2 = enemy.y + enemy.height + this.height/2;
		int x = this.x + this.width/2;
		int y = this.y + this.height/2;
		return x>=x1 && x<=x2
				&&
			   y>=y1 && y<=y2;
	}
}
