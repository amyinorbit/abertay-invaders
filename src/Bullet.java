package CesarParent;

import GameKit.*;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public abstract class Bullet extends RectNode implements Collidable {
	
	protected int speed;
	protected Random rnd;
	
	/*
	* Create a bullet
	*
	*/
	public Bullet()
	{
		super(8, 20, Color.red);
		Media.instance().playAudioFile("shoot.wav");
		speed = 0;
		rnd = new Random();
	}
	
	/*
	* Remove the bullet from its scene in case of collision
	*/
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		removeFromParent();
	}
	
	/*
	* Called every frame. moves the bullet and removes it from its parent
	* scene if it exited the screen.
	*/
	@Override
	public void update()
	{
		if(parent == null) return;
		emit();
		setY(getPosition().y + speed);
		if(getPosition().y < 0 || getPosition().y > parent.getHeight())
		{
			removeFromParent();
		}
	}
	
	/*
	* Creates a trail of particles behind the bullet
	*/
	protected void emit()
	{
		Particle emission = new Particle(5,5,Color.orange);
		emission.setSpeed(rnd.nextFloat()*4.0-2.0, speed/5);
		emission.setPosition(getPosition().x, getPosition().y);
		emission.setDuration(5+rnd.nextInt(5));
		parent.addChild(emission);
	}
}