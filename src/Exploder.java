package CesarParent;

import GameKit.*;
import java.util.Random;
import java.awt.Color;

/*
* Exploder is a very simple particle emitter, that handles creating explosions.
*/
public class Exploder
{
	
	/*
	* Create an particle effect for a mothership explosion
	*/
	public static void explodeBoss(Scene scene, int x, int y)
	{
		Exploder.explode(scene, x, y, 10, 6, .1, 50, new Color(0xda, 0x86, 0x8a));
	}
	
	/*
	* Create an particle effect for a standard explosion
	* The color is picked depending on the class of node
	*/
	public static void explodeMedium(Scene scene, Node node, int x, int y)
	{
		Color color;
		if(node instanceof Martian)
		{
			color = new Color(0x79, 0x51, 0x73);
		}
		else if(node instanceof Mercurian)
		{
			color = new Color(0x72, 0xa6, 0x62);
		}
		else if(node instanceof Venusian)
		{
			color = new Color(0x00, 0x57, 0x72);
		}
		else if(node instanceof Asteroid)
		{
			color = new Color(0x81, 0x4a, 0x01);
		}
		else if(node instanceof Destroyer)
		{
			color = new Color(0xca, 0x75, 0x42);
		}
		else
		{
			color = Color.white;
		}
		Exploder.explode(scene, x, y, 10, 8, .3, 45, color);
	}
	
	/*
	* Create an particle effect for bullet explosion
	*/
	public static void explodeBullet(Scene scene, int x, int y)
	{
		Exploder.explode(scene, x, y, 4, 5, 0, 10, Color.red);
	}
	
	/*
	* Create an particle effect for the player's explosion
	*/
	public static void explodePlayer(Scene scene, int x, int y)
	{
		Exploder.explode(scene, x, y, 15, 10, 0.4, 120, new Color(0xcc, 0xcc, 0xcc));
	}
	
	/*
	* Generic explosion generator
	* Creates 20 particles, each shot at a random speed at a random angle
	* (rather than random x and y speed).
	*/
	private static void explode(Scene scene, int x, int y, int size, double speed, double gravity, int duration, Color color)
	{
		Media.instance().playAudioFile("explosion.wav");
		Random rnd = new Random();
		for(int i = 0; i < 40; ++i)
		{
			Particle test = new Particle(size, size, color);
			double v = speed*0.5 + (rnd.nextDouble()*speed*0.5);
			double angle =  Math.toRadians(rnd.nextFloat()*360.0);
			double vx = Math.cos(angle)*v;
			double vy = (Math.sin(angle)*v) - speed*gravity;
			test.setSpeed(vx, vy);
			test.setPosition(x,y);
			test.setDuration(duration);
			test.setGravity(gravity);
			scene.addChild(test);
		}
	}
}