package GameKit;

import java.awt.Graphics;
import java.awt.Rectangle;

/*
* GameKit.Node - Most basic entity of GameKit. Used for every game entity
*
* Created on 2014-10-27 by Cesar Parent <http://cesarparent.com>
*/
public abstract class Node
{
	protected Scene parent;
	protected Rectangle frame;
	public boolean visible;
	
	/*
	* Creates a node with an image from the Jar package
	* 
	*/
	public Node()
	{
		this.frame = new Rectangle(0,0,0,0);
		this.parent = null;
		this.visible = true;
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
	
	public int x()
	{
		return frame.x;
	}
	
	public int y()
	{
		return frame.y;
	}
	
	public int w()
	{
		return frame.width;
	}
	
	public int h()
	{
		return frame.height;
	}
}