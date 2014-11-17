package CesarParent;

import GameKit.*;

public class InvaderBullet extends Bullet implements java.io.Serializable
{
	public InvaderBullet(int x, int y)
	{
		super("invaderbullet.png");
		speed = Settings.instance().alienBulletSpeed;
		setPosition(x,y);
	}
	
	@Override
	public void didCollideWithNode(Node node)
	{
		if(parent == null) return;
		if(node instanceof Invader || node instanceof InvaderBullet) return;
		parent.addChild(new Explosion(getPosition().x, getPosition().y));
		removeFromParent();
	}
}