package CesarParent;

import GameKit.*;
import java.awt.Point;

public class Player extends SpriteNode implements Collidable, java.io.Serializable
{
	private int lives;
	private Controller controls;
	private int fireCountDown;
	private int speed;
	
	public Player(Controller controls)
	{
		super("player.png");
		lives = 3;
		fireCountDown = 0;
		this.controls = controls;
		speed = Settings.instance().playerSpeed;
	}
	
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
	
	public void fire()
	{
		parent.addChild(new PlayerBullet(getPosition().x,getPosition().y-40));
		fireCountDown = Settings.instance().shootDelay;
	}
	
	public void didCollideWithNode(Node node)
	{
		if(node instanceof PlayerBullet) return;
		if(node instanceof Mothership)
		{
			die();
		}
		--lives;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void removeLife()
	{
		--lives;
	}
	
	public void die()
	{
		if(parent == null) return;
		parent.addChild(new Explosion(getPosition().x, getPosition().y));
		((GameScene)parent).gameOver();
		removeFromParent();
	}
}