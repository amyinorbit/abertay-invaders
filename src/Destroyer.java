package CesarParent;

import GameKit.*;
import java.util.Random;

public class Destroyer extends Boss implements CanCloak, java.io.Serializable
{
	private int cloakCountDown;
	private Random rnd;
	
	/*
	* Creates a destroyer in the top left corner of the field
	*/
	public Destroyer()
	{
		super("destroyer.png", 0, 80, 2, 200);
		speed = 2;
		rnd = new Random();
		cloakCountDown = rnd.nextInt(200);
	}
	
	/*
	* Moves the destroyer, called every frame
	* The destroyer moves rightwards and disappears when it reaches the right
	* edge of the screen
	*/
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
	
	/*
	* Decides wether the destroyer shoudl be cloaked or visible
	* A countdown is created, and the destroyer changes its cloaking state
	* when the countdown reaches zero
	*/
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