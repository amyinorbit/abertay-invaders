package CesarParent;

import GameKit.*;
import java.util.Random;
import java.awt.Color;

public class Martian extends Invader implements CanShoot, java.io.Serializable
{
	private int shootCountDown;
	private Random rnd;
	
	/*
	* Create a new Martian at x,y, and a given position in the swarm.
	*/
	public Martian(int x, int y, int position)
	{
		super("martian.png", x, y);
		points = 30;
		lives = 1;
		rnd = new Random();
		positionInSwarm = position;
		shootCountDown = rnd.nextInt(CanShoot.shootIntervalVar);
	}
	
	/*
	* Shoots a bullet when the shooting countdown reaches 0, and resets the countdown
	* to a random number.
	*/
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