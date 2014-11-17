package CesarParent;

import GameKit.*;
import java.util.Random;

public class Asteroid extends Bullet implements java.io.Serializable
{
	/*
	* Asteroid is created at a random x position, and falls down from the top of the screen 
	*/
	public Asteroid(int x)
	{
		super("asteroid.png");
		Random rnd = new Random();
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
			parent.addChild(new Explosion(getPosition().x, getPosition().y));
			((GameScene)parent).addScore(10);
			removeFromParent();
		}
		else if(node instanceof Player)
		{
			parent.addChild(new Explosion(getPosition().x, getPosition().y));
			removeFromParent();
		}
	}
}