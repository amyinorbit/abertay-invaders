package CesarParent;

import GameKit.*;
import java.awt.Color;

public class TimedLabel extends TextNode implements java.io.Serializable
{
	private int duration;
	
	public TimedLabel(int x, int y, String message, int size)
	{
		super(message, "KenPixel-mini", size, Color.white);
		//super("explosion.png");
		setPosition(x,y);
		duration = 0;
	}
	
	@Override
	public void update()
	{
		if(parent == null) return;
		if(++duration > 30)
		{
			removeFromParent();
		}
	}
}