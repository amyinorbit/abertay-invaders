package CesarParent;

import GameKit.*;
import java.util.Random;
import java.awt.Color;
public class Asteroid extends Bullet implements java.io.Serializable
{
	/*
	* Asteroid is created at a random x position, and falls down from the top of the screen 
	*/
	public Asteroid(int x)
	{
		super();
		color = new Color(0x81, 0x4a, 0x01);
		setWidth(32);
		setHeight(32);
		speed = rnd.nextInt(3)+2;
		setPosition(x,0);
	}
	
	/*
	* Asteroid is destroyed only by PlayerBullet and Player instances.
	* When destroyed, the asteroid is removed from its parent scene and
	* spawns an explosion SpriteNode
	*/
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof PlayerBullet)
		{
			Exploder.explodeMedium(parent, this, getPosition().x, getPosition().y);
			parent.addChild(new TimedLabel(getPosition().x, getPosition().y, "10", 30));
			((GameScene)parent).addScore(10);
			removeFromParent();
		}
		else if(node instanceof Player)
		{
			Exploder.explodeMedium(parent, this, getPosition().x, getPosition().y);
			removeFromParent();
		}
	}
	
	/*
	* Emits a trail of rectangle particles behind the asteroid
	*/
	@Override
	protected void emit()
	{
		int size = 6+ rnd.nextInt(6);
		if(rnd.nextFloat() >.5)
		{
			Color color = (rnd.nextFloat() > .5)? Color.orange : Color.red;
			Particle test = new Particle(size, size, color);
			int xOffset = -16 + rnd.nextInt(33);
			test.setSpeed(0,speed/2);
			test.setPosition(getPosition().x+xOffset,getPosition().y+10);
			test.setDuration(15+rnd.nextInt(30));
			test.setGravity(-0.1);
			parent.addChild(test);
		}
	}
}