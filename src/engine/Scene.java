package GameKit;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
* GameKit.Scene - Simple scene extending JFrame
* Calls the updateWithTimeInterval(interval) every tick
*
* Created on 2014-10-27 by Cesar Parent <http://cesarparent.com>
*/
public class Scene extends JPanel implements ActionListener
{
	protected ArrayList<Node> children;
	private Timer frameTimer;
	protected Node background;
	
	public Scene()
	{
		children = new ArrayList<Node>();
		frameTimer = new Timer(16, this);
		setFocusable(true);
	}
	
	/*
	* Creates a Scene with a background Sprite Node
	*/
	public Scene(String backgroundImage)
	{
		this();
		background = new SpriteNode(backgroundImage);
		addChild(background);
	}
	
	/*
	* Creates a Scene with a background colour
	*/
	public Scene(Color color)
	{
		this();
		setBackground(color);
	}
	
	/*
	* Adds the passed GameKit Node to the children list of the scene
	* The scene calls draw() on each of its children every frame
	*/
	public void addChild(Node child)
	{
		child.setParent(this);
		children.add(child);
	}
	
	/*
	* Main Update method. Called every frame, before drawing the scene.
	* Every logic should be implemented here
	*/
	protected void update()
	{
		
	}
	
	/*
	* Draws every single children of the scene onto the screen
	*/
	protected void paintComponent(Graphics graphics)
	{
		for(Node child : children)
		{
			child.draw(graphics);
		}
	}
	
	/*
	* Will loop through the Scene's children that implement Collidable
	* and call their collision handlers
	*/
	protected void collideChildren()
	{
		for(Node child : children)
		{
			if(child instanceof Collidable)
			{
				collideWithChildren(child);
			}
		}
	}
	
	/*
	* attempts to collide a node with the rest of the children
	*/
	protected void collideWithChildren(Node toCollide)
	{
		for(Node child : children)
		{
			if(child instanceof Collidable && child != toCollide)
			{
				if(((Collidable)toCollide).shouldCollideWithNode(child))
				{
					((Collidable)toCollide).didCollideWithNode(child);
				}
			}
		}
	}
	
	/*
	* Starts the render loop for the scene
	*/
	public void beginRendering()
	{
		frameTimer.start();
	}
	
	/*
	* Stops the scene timer
	*/
	public void stopUpdate()
	{
		frameTimer.stop();
	}
	
	/*
	* Called every time a JEvent is performed
	*/
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == frameTimer)
		{
			update();
			collideChildren();
			repaint();
		}
	}
}