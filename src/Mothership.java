package CesarParent;

import GameKit.*;
import java.awt.Point;
import java.awt.Rectangle;

public class Mothership extends Boss implements java.io.Serializable
{
	/*
	* Create a new Mothership at x,y.
	*/
	public Mothership(int x, int y)
	{
		super("mothership.png", x, y, 3, 200);
		Media.instance().playAudioFile("mothership.wav");
		speed = Settings.instance().invaderSpeed + 3;
	}
	
	/*
	* Moves the Mothership sideways.
	* When the mothership reaches the edge of the field, it changes its targetY
	* and increments its speed by one.
	* If targetY is further down that the mothership's current y position, it
	* stops going sideways and goes downwards.
	*/
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
	
	/*
	* Removes the mothership from its parent scene, add the points to
	* the player's score, and triggers the level change.
	*/
	@Override
	public void die()
	{
		if(parent == null) return;
		Exploder.explodeBoss(parent, getPosition().x, getPosition().y);
		((GameScene)parent).nextLevel();
		removeFromParent();
	}
}