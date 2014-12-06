package CesarParent;

import GameKit.*;

public class InvaderBullet extends Bullet implements java.io.Serializable
{
	/*
	* Spawns a Invader Bullet at x,y
	*/
	public InvaderBullet(int x, int y)
	{
		super();
		speed = Settings.instance().alienBulletSpeed;
		setPosition(x,y);
	}
	
	/*
	* Called when the bullet has collided with another node
	* The Bullet is removed from parent if the node is not another Invader
	* bullet or an Invader.
	* when the bullet is destroyed, an explosion sprite is added to the scene.
	*/
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof Invader || node instanceof InvaderBullet) return;
		Exploder.explodeBullet(parent, getPosition().x, getPosition().y);
		removeFromParent();
	}
}