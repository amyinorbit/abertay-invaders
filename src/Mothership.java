package CesarParent;

import GameKit.*;
import java.awt.Point;
import java.awt.Rectangle;

public class Mothership extends Boss implements java.io.Serializable
{
	public Mothership(int x, int y)
	{
		super("mothership.png", x, y, 3, 200);
		speed = Settings.instance().invaderSpeed + 3;
	}
	
	@Override
	protected void move()
	{
		if(parent == null) return;
		Point currentPosition = getPosition();
		Rectangle frame = getFrame();
		if(currentPosition.x < 60)
		{
			setX(60);
			targetY += 50;
			direction = -direction;
			speed ++;
		}
		else if(currentPosition.x > parent.getWidth() - 60)
		{
			setX(parent.getWidth()-60);
			targetY += 50;
			direction = -direction;
			speed ++;
		}
		
		if(currentPosition.y >= targetY)
		{
			setX(currentPosition.x + speed*direction);
		}
		else
		{
			setY(currentPosition.y + speed);
		}
		
		if(currentPosition.y > parent.getHeight())
		{
			((GameScene)parent).invaderReachedBottom();
			die();
		}
	}
	
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		else if(node instanceof PlayerBullet)
		{
			parent.addChild(new Explosion(getPosition().x, getPosition().y));
			if(--lives <= 0)
			{
				die();
			}
		}
	}
	
	@Override
	public void die()
	{
		if(parent == null) return;
		parent.addChild(new Explosion(getPosition().x, getPosition().y));
		((GameScene)parent).nextLevel();
		removeFromParent();
	}
}