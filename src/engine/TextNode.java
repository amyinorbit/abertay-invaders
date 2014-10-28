package GameKit;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.Font;
import java.awt.font.*;
import java.awt.Color;
import java.awt.RenderingHints;

/*
* GameKit.Node - Text based node.
*
* Created on 2014-10-28 by Cesar Parent <http://cesarparent.com>
*/
public class TextNode extends Node
{
	protected String text;
	protected Font font;
	protected FontRenderContext context;
	protected Color color;
	
	public TextNode(String string, String fontname, int size, Color color)
	{
		super();
		text = string;
		font = new Font(fontname, Font.PLAIN, size);
		context = new FontRenderContext(null, true, true);
		this.color = color;
		processText();
	}
	
	/*
	* Called when the string is changed. Computes the width and height
	* Of the rendered text.
	*/
	private void processText()
	{
		Rectangle2D bounds = font.getStringBounds(text, context);
		frame.width = (int) bounds.getWidth();
		frame.height = (int) bounds.getHeight();
	}
	
	/*
	* Change String
	*/
	public void setText(String string)
	{
		text = string;
		processText();
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		if(graphics instanceof Graphics2D)
		{
			((Graphics2D)graphics).setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		graphics.setFont(font);
		graphics.setColor(color);
		graphics.drawString(text, frame.x, frame.y+frame.height);
	}
}