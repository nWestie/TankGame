package Bodies;

import javax.swing.ImageIcon;

import mainGame.gCols;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
public class Tank {
	protected AffineTransform prev;
	protected Area bounds, tranBounds;
	protected PObj pBody, pTurret;
	protected final double maxVel = 80;
	protected final double accConst = 3;
	protected long fireDel = 400;
	double[] pos, vel; // vel in form [mag, rad]
	protected int lastDir = 0;
	private static ImageIcon iBody = new ImageIcon(ClassLoader.getSystemResource("plrTankBody.png"));                
	private static ImageIcon iTurret = new ImageIcon(ClassLoader.getSystemResource("plrTankTurret.png"));  
	protected int health;
	protected long lastFire;
	public Tank(double x, double y, double mag, double rad, int health, boolean start) {
		this.health = health;
		          
		pos = new double[] {x, y};
		vel = new double[] {mag, rad};
		pBody = new PObj(pos, vel, start);
		pTurret = new PObj(pos, vel, start);
		pBody.rPos(vel[1]);
		pTurret.rPos(vel[1]);
		pBody.scale(.6);
		pTurret.scale(.6);
		bounds = new Area(new Rectangle(-41, -36, 82, 72));
		tranBounds = bounds;
		lastFire = System.currentTimeMillis();
	}

	public void fire(ArrayList<Bullet> shots) {
		if(System.currentTimeMillis()>lastFire+fireDel) {
			lastFire = System.currentTimeMillis();
			shots.add(new Bullet(pTurret.pos()[0]+25*Math.cos(pTurret.rPos()),pTurret.pos()[1]+25*Math.sin(pTurret.rPos()), pTurret.rPos(), 0, true));
		}
	}
	public void rotate(double rad) {
		vel[1] += rad;
		pBody.rPos(vel[1]);
		rotTurret(rad);
	}
	public void rotTurret(double rad) {
		pTurret.rPos(pTurret.rPos()+rad);
	}
	public void addVel(double mag) {
		vel[0] += mag;
		vel[0] = vel[0] >= maxVel ? maxVel : vel[0];
		vel[0] = vel[0] <= -maxVel ? -maxVel : vel[0];
		pBody.vel(vel);
		pTurret.vel(vel);
	}
	public void acc(boolean dir) {
		if(dir) addVel(3*accConst);
		else addVel(-3*accConst);
	}
	public void draw(Graphics2D g2d) {
		prev = g2d.getTransform();
	//health bar
		g2d.setColor(gCols.health);
		g2d.fillRect((int)pos[0]-25,(int)pos[1]-45,health/4,5);
	//reload bar
		g2d.setColor(gCols.reload);
		g2d.fillRect((int)pos[0]-26, (int)pos[1]-37, (int)Math.min((System.currentTimeMillis()-lastFire)/10,50), 5);
	//tank sprites
		g2d.transform(pBody.trans());
		iBody.paintIcon(null, g2d, -41, -36);
		g2d.setTransform(prev);
		g2d.transform(pTurret.trans());
		iTurret.paintIcon(null, g2d, -23,-20);
		g2d.setTransform(prev);
	}
	public boolean takeDamage(int damage) {
		health -= damage;
		return health < 0;
	}
	public void tStep() {
		addVel(vel[0]>0?-2*accConst:(vel[0]<0?2*accConst:0));
		pBody.tStep();
		pTurret.tStep();
		tranBounds = bounds.createTransformedArea(pBody.trans());
	}
	public void start() {
		pBody.start();
		pTurret.start();
	}
	public Area tightBounds() {
		return tranBounds;
	}
	public Rectangle rectBounds() {
		return tranBounds.getBounds();
	}
	public void pos(double[] pos) {
		this.pos = pos;
		pBody.pos(pos);
		pTurret.pos(pos);
	}
	public void collide() {
		vel[0] = -vel[0];
//		if((vel[0]>0&&lastDir==-1)||(vel[0]<0&&lastDir==1))return;//do not stop if trying to reverse out of collision
//		else lastDir = vel[0]>0?1:-1; //direction collision occured on
//		vel[0] = 0;
//		pBody.vel(vel);
//		pTurret.vel(vel);
		//takeDamage(3);
	}
	/**
	 * @return the pos
	 */
	public double[] pos() {
		return pos;
	}
	/**
	 * @return the vel
	 */
	public double[] vel() {
		return vel;
	}

}
