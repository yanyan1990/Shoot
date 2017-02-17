package cs.imu.edu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel{

	public static final int WIDTH = 400;
	public static final int HEIGHT = 654;
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage airplane;
	public static BufferedImage bossplane;
	public static BufferedImage bee;
	public static BufferedImage bigyellowbee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	private Hero hero = new Hero();
	private Enemy[] enemys = {};
	private Bullet[] bullets = {};
	
	static{
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bossplane = ImageIO.read(ShootGame.class.getResource("bossplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bigyellowbee = ImageIO.read(ShootGame.class.getResource("bigyellowbee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Enemy nextOne(){
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type >=0 && type <=1){
			return new BossPlane();
		}else if(type == 2){
			return new BigYellowBee();
		}else if(type >=3 && type <= 6){
			return new Bee();
		}else{
			return new Airplane();
		}
	}
	
	int flyEnteredIndex = 0;
	public void enterAction(){
		flyEnteredIndex ++;
		if(flyEnteredIndex%40 == 0){
			Enemy one = nextOne();
			enemys = Arrays.copyOf(enemys, enemys.length+1);
			enemys[enemys.length-1] = one;
		}
	}
	
	public void stepAction(){
		hero.step();
		for(int i = 0; i < enemys.length; i++){
			enemys[i].step();
		}
		for(int i = 0; i < bullets.length; i++){
			bullets[i].step();
		}
	}
	
	int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		Bullet[] bs = null;
		if(shootIndex%30 == 0){
			if((bs = hero.shoot())!= null){
				bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
				System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
			}
		}
	}
	
	public void outOfBoundsAction(){
		int index = 0;
		Enemy[] enemyLives = new Enemy[enemys.length];
		for(int i = 0; i < enemys.length; i++){
			Enemy enemy = enemys[i];
			if(!enemy.outOfBounds()){
				enemyLives[index] = enemy;
				index++;
			}
		}
		enemys = Arrays.copyOf(enemyLives, index);
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i = 0; i < bullets.length; i++){
			Bullet bullet = bullets[i];
			if(!bullet.outOfBounds()){
				bulletLives[index] = bullet;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
	}
	
	public void bangAction(){
		for(int i = 0; i < bullets.length; i++){
			Bullet bullet = bullets[i];
			bang(bullet);
		}
	}
	
	int score = 0;
	public void bang(Bullet bullet){
		int index = -1;
		for(int i = 0; i < enemys.length; i++){
			Enemy enemy = enemys[i];
			if(enemy.shootBy(bullet)){
				index = i;
				break;
			}
		}
		
		if(index != -1){
			Enemy one = enemys[index];
			if(one instanceof Plane){
				Plane plane = (Plane)one;
				score += plane.getScore();
			}
			if(one instanceof Award){
				Award award = (Award)one;
				int type = award.getType();
				switch(type){
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire(award);
					break;
				case Award.LIFE:
					hero.addLife(award);
					break;
				}
			}
			
			Enemy e = enemys[index];
			enemys[index] = enemys[enemys.length-1];
			enemys[enemys.length-1] = e;
			enemys = Arrays.copyOf(enemys, enemys.length-1);
		}
	}
	
	public void checkGameOverAction(){
		if(isGameOver()){
			state = GAME_OVER;
		}
	}
	
	public boolean isGameOver(){
		for(int i = 0; i < enemys.length; i++){
			Enemy enemy = enemys[i];
			if(hero.hit(enemy)){
				hero.substractLife(enemy);
				hero.substractDoubleFire(enemy);
				Enemy e = enemys[i];
				enemys[i] = enemys[enemys.length-1];
				enemys[enemys.length-1] = e;
				enemys = Arrays.copyOf(enemys, enemys.length-1);
			}
		}
		return hero.getLife()<=0 || hero.getDoubleFire() <= 0;
	}
	
	public void action(){
		MouseAdapter ma = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e){
				if(state == START){
					state = RUNNING;
				}
				if(state == GAME_OVER){
					score = 0;
					hero = new Hero();
					enemys = new Enemy[0];
					bullets = new Bullet[0];
					state = START;
				}
			}
			public void mouseExited(MouseEvent e){
				if(state == RUNNING){
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state == PAUSE){
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
		Timer timer = new Timer();
		int interval = 10;
		timer.schedule(new TimerTask(){
			public void run(){
				if(state == RUNNING){
					enterAction();
					shootAction();
					stepAction();
					outOfBoundsAction();
					bangAction();
					checkGameOverAction();
				}		
				repaint();
			}
		}, interval, interval);
	}
	
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);       
		paintHero(g);
		paintEnemys(g);
		paintBullets(g);
		paintScoreAndLife(g);
		paintState(g);
	}
	
	public void paintHero(Graphics g){
		g.drawImage(hero0, hero.x, hero.y, null);
	}
	
	public void paintEnemys(Graphics g){
		for(int i = 0; i < enemys.length; i++){
			Enemy enemy = enemys[i];
			g.drawImage(enemy.image, enemy.x, enemy.y, null);
		}
	}
	
	public void paintBullets(Graphics g){
		for(int i = 0; i < bullets.length; i++){
			Bullet bbullet = bullets[i];
			g.drawImage(bbullet.image, bbullet.x, bbullet.y, null);
		}
	}
	
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		g.drawString("SCORE:"+score, 10, 25);
		g.drawString("LIFE:"+hero.getLife(), 10, 45);
		g.drawString("Fire:"+hero.getDoubleFire(), 10, 65);
	}
	
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("Fly");
		ShootGame game = new ShootGame();
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.action();
	}
}
