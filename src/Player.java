package CesarParent;

import GameKit.*;
import java.awt.Point;

public class Player extends SpriteNode implements Collidable, java.io.Serializable
{
	private int lives;
	private Controller controls;
	private int fireCountDown;
	private int speed;
	
	/*
	* Creates a player instance.
	*/
	public Player(Controller controls)
	{
		super("player.png");
		lives = 3;
		fireCountDown = 0;
		this.controls = controls;
		speed = Settings.instance().playerSpeed;
	}
	
	/*
	* Called every frame before the drawing.
	* Update the position of the player based on the controls.
	* if the player's life count reaches 0, the player is killed.
	* If the firing countdown has reached 0 and the spaceabr is pressed,
	* trigger fire.
	*/
	@Override
	public void update()
	{
		if(parent == null) return;
		if(lives <= 0)
		{
			die();
			return;
		}
		fireCountDown = fireCountDown == 0? 0 : fireCountDown-1;
		Point pos = getPosition();
		if(controls.left && !controls.right)
		{
			setX(pos.x-speed);
		}
		else if(controls.right && !controls.left)
		{
			 setX(pos.x+speed);
		}
		
		if(controls.spacebar && fireCountDown == 0)
		{
			fire();
		}
		int leftEdge = getPosition().x - getFrame().width/2;
		int rightEdge = getPosition().x + getFrame().width/2;
		if(leftEdge < 0)
		{
			setX(getFrame().width/2);
		}
		else if(rightEdge > parent.getWidth())
		{
			setX(parent.getWidth() - getFrame().width/2);
		}
	}
	
	/*
	* Adds a new player bullet at the player's position and reset the fire countdown
	*/
	public void fire()
	{
		parent.addChild(new PlayerBullet(getPosition().x,getPosition().y-40));
		fireCountDown = Settings.instance().shootDelay;
	}
	
	/*
	* If the player collides with anything but one of its bullets, remove a life.
	* If the player collides with a mothership, die instantly.
	*/
	@Override
	public void didCollideWithNode(Node node)
	{
		if(node instanceof PlayerBullet) return;
		if(node instanceof Mothership)
		{
			die();
		}
		--lives;
	}
	
	/*
	* Returns the life count of the player.
	*/
	public int getLives()
	{
		return lives;
	}
	
	/*
	* remove a life from the player's count
	*/
	public void removeLife()
	{
		--lives;
	}
	
	/*
	* Triggers the player's death. An explosion is added to the scene, and a
	* message sent to trigger the game over menu.
	*/
	public void die()
	{
		if(parent == null) return;
		lives = 0;
		Media.instance().playAudioFile("gameover.wav");
		Exploder.explodePlayer(parent, getPosition().x, getPosition().y);
		((GameScene)parent).gameOver();
		removeFromParent();
	}
}