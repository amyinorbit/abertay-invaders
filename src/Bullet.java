package CesarParent;

import GameKit.*;
import java.awt.Color;
import java.awt.Point;

public abstract class Bullet extends SpriteNode implements Collidable {
	
	protected int speed;
	
	/*
	* Create a bullet
	*
	*/
	public Bullet(String imageNamed)
	{
		super(imageNamed);
		speed = 0;
	}
	
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		removeFromParent();
	}
	
	@Override
	public void update()
	{
		setY(getPosition().y + speed);
		if(getPosition().y < 0 || getPosition().y > parent.getHeight())
		{
			removeFromParent();
		}
	}
}