package GameKit;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Toolkit;
import java.net.URL;
import java.awt.Rectangle;
import java.awt.Graphics;

/*
* GameKit.SpriteNode - Special Node holding a sprite loaded from an image
*
* Created on 2014-10-28 by Cesar Parent <http://cesarparent.com>
*/
public class SpriteNode extends Node
{

	protected BufferedImage image;
	
	/*
	* Creates a SpriteNode. The image must be in the Jar file
	* The Node's width and height will be those of the image
	*/
	public SpriteNode(String imageNamed)
	{
		super();
		try
		{
			URL url = getClass().getResource("/"+imageNamed);
			image = ImageIO.read(url);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		frame.width = image.getWidth();
		frame.height = image.getHeight();
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		graphics.drawImage(this.image,
			frame.x,
			frame.y,
			null);
	}
}