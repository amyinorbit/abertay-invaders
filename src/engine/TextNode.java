package GameKit;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.Font;
import java.awt.font.*;
import java.io.*;
import java.awt.Color;
import java.awt.RenderingHints;

/*
* GameKit.Node - Text based node.
*
* Created on 2014-10-28 by Cesar Parent <http://cesarparent.com>
*/
public class TextNode extends Node implements java.io.Serializable
{
	protected String text;
	protected Font font;
	public Color color;
	public Color background;
	
	private String fontname;
	private int size;
	
	public TextNode(String string, String fontname, int size, Color fg)
	{
		super();
		text = string;
		font = Media.instance().fontNamed(fontname, size);
		//font = new Font(fontname, Font.PLAIN, size);
		this.fontname = fontname;
		this.size = size;
		color = fg;
		background = null;
		processText();
	}
	
	public TextNode(String string, String fontname, int size, Color fg, Color bg)
	{
		this(string, fontname, size, fg);
		this.background = bg;
	}
	
	/*
	* Called when the string is changed. Computes the width and height
	* Of the rendered text.
	*/
	private void processText()
	{
		FontRenderContext context = new FontRenderContext(null, true, true);
		Rectangle2D bounds = font.getStringBounds(text, context);
		setWidth((int) bounds.getWidth());
		setHeight((int) bounds.getHeight());
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
		if(!visible) return;
		if(graphics instanceof Graphics2D)
		{
			((Graphics2D)graphics).setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		graphics.setFont(font);
		Rectangle frame = getFrame();
		if(background != null)
		{
			graphics.setColor(background);
			graphics.fillRect(frame.x-10, frame.y+10, frame.width+15, frame.height);
		}
		graphics.setColor(color);
		graphics.drawString(text, frame.x, frame.y+frame.height);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		font = Media.instance().fontNamed(this.fontname, this.size);
	}
}