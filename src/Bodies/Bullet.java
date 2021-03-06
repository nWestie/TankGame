package Bodies;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Bullet {
	//lookup tables for bullet types		Player, Machine Gun, Tank		
	private final static int[] speed 	= new int[] {400, 400, 250};
	private final static int[] time 	= new int[] {1500, 1000, 1000};
	private final static double[] scale = new double[] {.4, .2, .4};
	private final static int[] damage 	= new int[] {10, 3, 7};
	
	static ImageIcon Ibody = new ImageIcon(ClassLoader.getSystemResource("bullet.png"));
	private AffineTransform prev;
	private PObj p;
	double[] pos, vel; // vel in form [mag, rad]
	private int type;
	private long endTime;

	public Bullet(double x, double y, double rad, int type, boolean start) {
		this.type = type;
		pos = new double[] {x, y};
		vel = new double[] {speed[type], rad};
		p = new PObj(pos, vel, start);
		p.rPos(vel[1]);
		p.scale(scale[type]);
		if(start) start();
	}
	public void draw(Graphics2D g2d) {
		prev = g2d.getTransform();
		g2d.transform(p.trans());
		Ibody.paintIcon(null, g2d, 0, -7);
		g2d.setTransform(prev);
	}
	public Point2D.Double tip() {
		Point2D.Double tip = new Point2D.Double(39, 0);
		p.trans().transform(tip, tip);
		return tip;
	}
	public int damage() {
		return damage[type];
	}
	public long endTime() {
		return endTime;
	}
	public void start() {
		endTime = System.currentTimeMillis()+time[type];
		p.start();
	}
	public int type() {
		return type;
	}
	public void tStep() {
		p.tStep();
	}
}
