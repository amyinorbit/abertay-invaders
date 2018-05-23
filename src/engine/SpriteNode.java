package GameKit;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.net.URL;
import java.awt.Rectangle;
import java.awt.Graphics;
import javax.swing.JOptionPane;

/*
* GameKit.SpriteNode - Special Node holding a sprite loaded from an image
*
* Created on 2014-10-28 by Amy Parent <http://amyparent.com>
*/
public class SpriteNode extends Node implements java.io.Serializable
{

	transient protected BufferedImage image;
	
	/*
	* Creates a SpriteNode. The image must be in the Jar file
	* The Node's width and height will be those of the image
	*/
	public SpriteNode(String imageNamed)
	{
		super();
		image = Media.instance().imageNamed(imageNamed);
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		if(!visible) return;
		Rectangle frame = getFrame();
		graphics.drawImage(this.image,
			frame.x,
			frame.y,
			frame.width,
			frame.height,
			null);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		ImageIO.write(image, "png", out);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		image = ImageIO.read(in);
	}
}