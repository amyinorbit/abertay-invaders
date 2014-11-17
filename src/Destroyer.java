package CesarParent;

import GameKit.*;
import java.util.Random;

public class Destroyer extends Boss implements CanCloak, java.io.Serializable
{
	private int cloakCountDown;
	private Random rnd;
	
	public Destroyer()
	{
		super("destroyer.png", 0, 80, 2, 200);
		speed = 2;
		rnd = new Random();
		cloakCountDown = rnd.nextInt(200);
	}
	
	
	@Override
	public void move()
	{
		if(parent == null) return;
		setX(getPosition().x + speed);
		if(getFrame().x > parent.getWidth())
		{
			removeFromParent();
		}
	}
	
	public void triggerCloak()
	{
		if(cloakCountDown > 0)
		{
			cloakCountDown--;
		}
		else if(cloakCountDown <= 0 && visible)
		{
			visible = true;
			cloakCountDown = rnd.nextInt(200) + 100;
		}
		else if(cloakCountDown <= 0 && !visible)
		{
			visible = true;
			cloakCountDown = rnd.nextInt(200) + 200;
		}
	}
}