package CesarParent;

import GameKit.*;

public abstract class Boss extends Invader implements CanShoot, java.io.Serializable
{
	private int shootCountDown;
	
	public Boss(String image, int x, int y, int lives, int points)
	{
		super(image, x, y);
		this.lives = lives;
		this.points = points;
		shootCountDown = 120;
	}
	
	public void triggerShoot()
	{
		if(parent == null) return;
		if(shootCountDown > 0)
		{
			shootCountDown--;
		}
		else
		{
			shootCountDown = 120;
			parent.addChild(new InvaderBullet(getPosition().x, getPosition().y+10));
		}
	}
}