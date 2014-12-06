package CesarParent;

import GameKit.*;
import java.awt.Color;

/*
* Timedlabel is a GameKit LabelNode that removes itself from its parent
* Scene automatically after 30 frames.
*/
public class TimedLabel extends TextNode implements java.io.Serializable
{
	private int duration;
	
	/*
	* Create a new TimedLabel at x,y
	*/
	public TimedLabel(int x, int y, String message, int size)
	{
		super(message, "kenpixel_mini", size, Color.white);
		setPosition(x,y);
		duration = 0;
	}
	
	/*
	* Called once per frame. Increment the duration counter, and remove the
	* label from its parent if the counter reached 30.
	*/
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