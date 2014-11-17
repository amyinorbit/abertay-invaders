package CesarParent;

import GameKit.*;
import java.util.Random;

public class Mercurian extends Invader implements CanCloak, java.io.Serializable
{
	private int cloakCountDown;
	private Random rnd;
	
	public Mercurian(int x, int y, int position)
	{
		super("mercurian.png", x, y);
		points = 40;
		lives = 1;
		rnd = new Random();
		positionInSwarm = position;
		cloakCountDown = rnd.nextInt(200);
	}
	
	public void triggerCloak()
	{
		if(cloakCountDown > 0)
		{
			cloakCountDown--;
		}
		else if(cloakCountDown <= 0 && visible)
		{
			visible = false;
			cloakCountDown = rnd.nextInt(100) + 100;
		}
		else if(cloakCountDown <= 0 && !visible)
		{
			visible = true;
			cloakCountDown = rnd.nextInt(100) + 200;
		}
	}
}