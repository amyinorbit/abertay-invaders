package CesarParent;

import GameKit.*;
import java.util.Random;

public class Martian extends Invader implements CanShoot, java.io.Serializable
{
	private int shootCountDown;
	private Random rnd;
	
	public Martian(int x, int y, int position)
	{
		super("martian.png", x, y);
		points = 30;
		lives = 1;
		rnd = new Random();
		positionInSwarm = position;
		shootCountDown = rnd.nextInt(CanShoot.shootIntervalVar);
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
			shootCountDown = rnd.nextInt(CanShoot.shootIntervalVar) + CanShoot.minShootInterval;
			parent.addChild(new InvaderBullet(getPosition().x, getPosition().y+10));
		}
	}
}