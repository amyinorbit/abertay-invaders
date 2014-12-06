package GameKit;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;
import java.awt.geom.AffineTransform;

/*
* GameKit.Particle - Simple rectangle particle implementation
*
* Created on 2014-11-25 by Cesar Parent <http://cesarparent.com>
*/
public class Particle extends RectNode implements java.io.Serializable
{
	protected Color color;
	protected int duration;
	private Point2D.Double speed;
	protected double gravity;
	protected int lifetime;
	protected float opacity;
	protected float rotation;
	protected float rotationSpeed;
	
	/*
	* Create a new default particle. Particle is destroyed after Duration frames
	* By default, duration = 40.
	*
	* Particle moves according to its speed, and can fall if gravity > 0
	*/
	public Particle(int width, int height, Color color)
	{
		super(width, height, color);
		setWidth(width);
		setHeight(height);
		this.color = color;
		this.speed = new Point2D.Double(0,0);
		this.gravity = 0;
		this.setAnchorPoint(.5, .5);
		this.opacity = 1;
		this.setDuration(40);
		this.rotation = 0f;
		this.rotationSpeed = ((new Random()).nextFloat()-.5f)/2f;
	}
	
	/*
	* Set the particle's speed
	*/
	public void setSpeed(double vx, double vy)
	{
		this.speed.x = vx;
		this.speed.y = vy;
	}
	
	/*
	* Set the particle's gravity
	*
	*/
	public void setGravity(double g)
	{
		this.gravity = g;
	}
	
	/*
	* set the particle's time on screen, in number of frames
	*/
	public void setDuration(int time)
	{
		duration = lifetime = time;
	}
	
	/*
	* update the particle's position and opacity each frame.
	*/
	@Override
	public void update()
	{
		Point currentPos = getPosition();
		speed.y += gravity;
		setX(currentPos.x + (int)speed.x);
		setY(currentPos.y + (int)speed.y);
		lifetime --;
		rotation = (rotation + rotationSpeed) % (2.0f*(float)Math.PI);
		if(lifetime < duration/2)
		{
			opacity = (2.0f*lifetime)/(float)duration;
		}
		if(lifetime <= 0)
		{
			removeFromParent();
		}
	}
	
	/*
	* draw the particle on the screen.
	*/
	@Override
	public void draw(Graphics graphics)
	{
		((Graphics2D)graphics).setComposite(
			AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		AffineTransform old = ((Graphics2D)graphics).getTransform();
		((Graphics2D)graphics).rotate(rotation, getPosition().x, getPosition().y);
		super.draw(graphics);
		((Graphics2D)graphics).setTransform(old);
		((Graphics2D)graphics).setComposite(
			AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
}