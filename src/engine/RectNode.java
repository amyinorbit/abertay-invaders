package GameKit;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/*
* GameKit.RectNode - Node represented by a rectangle;
*
* Created on 2014-10-28 by Cesar Parent <http://cesarparent.com>
*/
public class RectNode extends Node
{
	protected Color color;
	
	public RectNode(int width, int height, Color color)
	{
		super();
		frame.width = width;
		frame.height = height;
		this.color = color;
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		graphics.setColor(color);
		graphics.fillRect(frame.x, frame.y, frame.width, frame.height);
	}
	
}