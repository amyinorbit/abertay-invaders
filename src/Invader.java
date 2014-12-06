package CesarParent;

import GameKit.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Random;

public abstract class Invader extends SpriteNode implements Collidable, java.io.Serializable {
	
	protected int lives;
	protected int points;
	protected int speed;
	protected int direction;
	protected int targetY;
	public int positionInSwarm;
	
	private float wobble;
	private int wobbleDir;
	
	public Invader(String image, int x, int y)
	{
		super(image);
		setPosition(x, y);
		targetY = y;
		
		speed = Settings.instance().invaderSpeed;
		direction = 1;
		
		Random rnd = new Random();
		wobble = rnd.nextInt(5);
		wobbleDir = rnd.nextBoolean()? -1 : 1;
	}
	
	/*
	* Update code. Called by the parent scene every frame.
	* If the invader is a boss, don't wiggle.
	*/
	@Override
	public void update()
	{
		if(parent == null) return;
		move();
		if(!(this instanceof Boss))
		{
			wobble();
		}
	}
	
	/*
	* Called once per frame. Handle the motion of the alien
	*
	* If the swarm has reached an edge (detected using the invader's position in the swarm),
	* the invader updates targetY.
	* If targetY is different to the current Y position, the invader doesn't move
	* sideways, but goes down until it reaches targetY.
	*/
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
	
	/*
	* If the invader is not an instance of Boss, make it wobble
	* Wobble is rendered by altering the sprite's width and height
	*/
	public void wobble()
	{
		wobble += wobbleDir;
		Rectangle frame = getFrame();
		if(wobble >= 5)
		{
			wobbleDir = -1;
		}
		else if(wobble <= 0)
		{
			wobbleDir = 1;
		}
		setWidth(frame.width + wobbleDir);
		setHeight(frame.height - wobbleDir);
	}
	
	/*
	* Called when the alien collided with another children of its parent scene.
	* if the node is the player, the invader dies instantly
	* if the node is one of the player's bullet, the alien looses a life, and dies
	* if its life count reaches 0.
	*/
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof Player)
		{
			die();
		}
		else if(node instanceof PlayerBullet)
		{
			//parent.addChild(new Explosion(getPosition().x, getPosition().y));
			if(--lives <= 0)
			{
				die();
			}
		}
	}
	
	/*
	* Triggers the death of the invader.
	* Adds a Label to the parent with the points earned, and tells the scene
	* how many points the player won.
	* The invader is then removed from the parent.
	*/
	protected void die()
	{
		if(parent == null) return;
		visible = true;
		Exploder.explodeMedium(parent, this, getPosition().x, getPosition().y);
		parent.addChild(new TimedLabel(getPosition().x, getPosition().y, ""+points, 30));
		((GameScene)parent).addScore(points);
		removeFromParent();
	}
}