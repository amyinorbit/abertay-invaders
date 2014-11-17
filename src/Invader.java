package CesarParent;

import GameKit.*;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Invader extends SpriteNode implements Collidable, java.io.Serializable {
	
	protected int lives;
	protected int points;
	protected int speed;
	protected int direction;
	protected int targetY;
	public int positionInSwarm;
	
	public Invader(String image, int x, int y)
	{
		super(image);
		setPosition(x, y);
		targetY = y;
		speed = Settings.instance().invaderSpeed;
		direction = 1;
	}
	
	/*
	* Update code. Called by the parent scene every frame.
	* When an Invader reaches the end of a line (80px away from the edge),
	* targetY is incremented. While the invader is above targetY, it stops
	* moving sideways and moves downwards to reach its new line
	*/
	public void update()
	{
		if(parent == null) return;
		move();
	}
	
	protected void move()
	{
		if(parent == null) return;
		Point currentPosition = getPosition();
		Rectangle frame = getFrame();
		if(parent instanceof GameScene)
		{
			int leftEdge = 60 + (positionInSwarm-((GameScene)parent).firstPosition)*60;
			int rightEdge = (parent.getWidth()-60) - (((GameScene)parent).lastPosition-positionInSwarm)*60;
			if(currentPosition.x < leftEdge)
			{
				setX(60 + (positionInSwarm-((GameScene)parent).firstPosition)*60);
				targetY += 50;
				direction = -direction;
			}
			else if(currentPosition.x > rightEdge)
			{
				setX((parent.getWidth()-60) - (((GameScene)parent).lastPosition-positionInSwarm)*60);
				targetY += 50;
				direction = -direction;
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
	}
	
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof Player)
		{
			die();
		}
		else if(node instanceof PlayerBullet)
		{
			parent.addChild(new Explosion(getPosition().x, getPosition().y));
			if(--lives <= 0)
			{
				die();
			}
		}
	}
	
	protected void die()
	{
		if(parent == null) return;
		visible = true;
		parent.addChild(new TimedLabel(getPosition().x, getPosition().y, ""+points, 30));
		((GameScene)parent).addScore(points);
		removeFromParent();
	}
}