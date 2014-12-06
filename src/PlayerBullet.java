package CesarParent;

import GameKit.*;

public class PlayerBullet extends Bullet implements java.io.Serializable
{
	/*
	* Creates a player bullet at coordinates x,y
	*/
	public PlayerBullet(int x, int y)
	{
		super();
		speed = -Settings.instance().playerBulletSpeed;
		setPosition(x,y);
	}
	
	/*
	* If the bullet collides with anything but the player or another player bullet,
	* Destroy the bullet and add an explosion to the scene.
	*/
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof Player || node instanceof PlayerBullet) return;
		Exploder.explodeBullet(parent, getPosition().x, getPosition().y);
		removeFromParent();
	}
}