package GameKit;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/*
* GameKit.RectNode - Node represented by a rectangle;
*
* Created on 2014-10-28 by Amy Parent <http://amyparent.com>
*/
public class RectNode extends Node implements java.io.Serializable
{
	protected Color color;
	
	public RectNode(int width, int height, Color color)
	{
		super();
		setWidth(width);
		setHeight(height);
		this.color = color;
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		if(!visible) return;
		Rectangle frame = getFrame();
		graphics.setColor(color);
		graphics.fillRect(frame.x, frame.y, frame.width, frame.height);
	}
	
}