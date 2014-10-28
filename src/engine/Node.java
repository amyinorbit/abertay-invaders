package GameKit;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.geom.Point2D;

/*
* GameKit.Node - Most basic entity of GameKit. Used for every game entity
*
* Created on 2014-10-27 by Cesar Parent <http://cesarparent.com>
*/
public abstract class Node
{
	protected Scene parent;
	public boolean visible;
	
	private Rectangle frame;
	private Point position;
	private Point2D.Double anchorPoint;
	
	/*
	* Creates a node with an image from the Jar package
	* 
	* Position holds the coordinates used to draw the node on screen
	* Anchor point represents where the position Point is located on the
	* Node's area. 0,0 represents the top-left corner, 1,1 bottom right
	*/
	public Node()
	{
		frame = new Rectangle(0,0,0,0);
		position = new Point(0,0);
		anchorPoint = new Point2D.Double(0,0);
		parent = null;
		visible = true;
	}
	
	/*
	* Defines how the node is drawn. This method is called every frame
	* When the node is the child of a GameKit.Scene
	*/
	public void draw(Graphics graphics)
	{
		
	}
	
	/*
	* Default implementation for collision detection.
	* Will only be called for subclasses implementing Collidable
	*/
	public boolean shouldCollideWithNode(Node node)
	{
		return frame.intersects(node.getFrame());
	}
	
	/*
	***************************************************************************
	* GETTERS/SETTERS
	***************************************************************************
	*/
		
	public void setX(int x)
	{
		frame.x = x - (int)(anchorPoint.x*frame.width);
		position.x = x;
	}
	
	public void setY(int y)
	{
		frame.y = y - (int)(anchorPoint.y*frame.height);
		position.y = y;
	}
	
	public void setWidth(int w)
	{
		frame.width = w;
	}
	
	public void setHeight(int h)
	{
		frame.height = h;
	}
	
	public void setAnchorPoint(double x, double y)
	{
		frame.x = position.x - (int)(x*frame.width);
		frame.y = position.y - (int)(y*frame.height);
		anchorPoint.x = x;
		anchorPoint.y = y;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public Point2D.Double getAnchorPoint()
	{
		return anchorPoint;
	}
	
	/*
	* registers the node's parent. If the node already has a parent, and this
	* parent is not the object being registered, a RuntimeException will be
	* raised.
	*/
	public void setParent(Scene scene)
	{
		if(parent != null && !parent.equals(scene))
		{
			throw new RuntimeException(this+" already has a parent.");
		}
		parent = scene;
	}
		
	public Rectangle getFrame()
	{
		return frame;
	}
}