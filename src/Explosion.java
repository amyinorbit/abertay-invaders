package CesarParent;

import GameKit.*;


public class Explosion extends SpriteNode implements java.io.Serializable
{
	private int duration;
	
	public Explosion(int x, int y)
	{
		super("explosion.png");
		setPosition(x,y);
		duration = 0;
	}
	
	@Override
	public void update()
	{
		if(parent == null) return;
		if(++duration > 20)
		{
			removeFromParent();
		}
	}
}