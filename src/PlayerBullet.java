package CesarParent;

import GameKit.*;

public class PlayerBullet extends Bullet implements java.io.Serializable
{
	public PlayerBullet(int x, int y)
	{
		super("playerbullet.png");
		speed = -Settings.instance().playerBulletSpeed;
		setPosition(x,y);
	}
	
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof Player || node instanceof PlayerBullet) return;
		removeFromParent();
	}
}