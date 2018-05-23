package GameKit;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Date;

/*
* GameKit.Scene - Simple scene extending JFrame
* Calls the updateWithTimeInterval(interval) every tick
*
* Created on 2014-10-27 by Amy Parent <http://amyparent.com>
*/
public class Scene extends JPanel implements ActionListener, java.io.Serializable
{
	protected ArrayList<Node> children;
	private ArrayList<Node> deletionBuffer;
	private ArrayList<Node> additionBuffer;
	private Timer frameTimer;
	protected Node background;
	protected int collidables;
	private int interval;
	private long lastFrame;
	private int fpsCount;
	protected double framerate;
	
	public Scene(int sizeX, int sizeY)
	{
		setPreferredSize(new Dimension(sizeX, sizeY));
		children = new ArrayList<Node>();
		deletionBuffer = new ArrayList<Node>();
		additionBuffer = new ArrayList<Node>();
		// set a 16ms (~1/60th of a second timer)
		frameTimer = new Timer(16, this);
		setFocusable(true);
		interval = 0;
		fpsCount = 15;
		lastFrame = new Date().getTime();
	}
	
	/*
	* Creates a Scene with a background Sprite Node
	*/
	public Scene(int sizeX, int sizeY, String backgroundImage)
	{
		this(sizeX, sizeY);
		background = new SpriteNode(backgroundImage);
		background.setAnchorPoint(0,0);
		background.setPosition(0,0);
		addChild(background);
	}
	
	/*
	* Creates a Scene with a background colour
	*/
	public Scene(int sizeX, int sizeY, Color color)
	{
		this(sizeX, sizeY);
		background = new RectNode(sizeX, sizeY, color);
		background.setAnchorPoint(0,0);
		background.setPosition(0,0);
		addChild(background);
	}
	
	/*
	* Adds the passed GameKit Node to the children list of the scene
	* The scene calls draw() on each of its children every frame
	*/
	public void addChild(Node child)
	{
		child.setParent(this);
		additionBuffer.add(child);
	}
	
	/*
	* If the given object is a child of the Scene, make it an orphan
	*/
	public void removeChild(Node child)
	{
		deletionBuffer.add(child);
		child.removeParent();
	}
	
	/*
	* Main Update method. Called every frame, before drawing the scene.
	* Every logic should be implemented here
	*/
	protected void update()
	{
		for(Node child : children)
		{
			child.update();
		}
	}
	
	/*
	* Draws every single children of the scene onto the screen
	*/
	protected void paintComponent(Graphics graphics)
	{
		graphics.clearRect(0, 0, getWidth(), getHeight());
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
		collidables = 0;
		for(Node child : children)
		{
			if(child instanceof Collidable)
			{
				collidables++;
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
	public void stopRendering()
	{
		frameTimer.stop();
	}
	
	/*
	* Called every time a JEvent is performed
	* 	- the custom update code is executed
	* 	- Collidable are then checked for collisions
	* 	- The Scene is drawn to the screen
		- newly added children are added to the children arraylist
	*	- children flagged for removal during the frame are removed
	*/
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == frameTimer)
		{
			if(++fpsCount >= 15)
			{
				long time = new Date().getTime();
				interval = (int)(time -lastFrame);
				lastFrame = time;
				framerate = (double)(15000.0/(double)interval);
				fpsCount = 0;
			}
			
			update();
			collideChildren();
			repaint();
			children.addAll(additionBuffer);
			additionBuffer.clear();
			children.removeAll(deletionBuffer);
			deletionBuffer.clear();
		}
	}
}