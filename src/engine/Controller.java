package GameKit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
* GameKit.Controller - Simple controls abstraction class
* Polls keyboard and, hopefully, controllers, and stores basic key presses
* as properties
*
* Created on 2014-10-28 by Cesar Parent <http://cesarparent.com>
*/
public class Controller implements KeyListener, java.io.Serializable
{
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	public boolean spacebar;
	public boolean returnKey;
	public boolean escapeKey;
	
	
	/*
	* Resets every property of a controller object
	*
	*/
	public void flush()
	{
		up = false;
		down = false;
		left = false;
		right = false;
		spacebar = false;
		returnKey = false;
		escapeKey = false;
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				left = true;
				break;
			
			case KeyEvent.VK_RIGHT:
				right = true;
				break;
			
			case KeyEvent.VK_UP:
				up = true;
				break;
			
			case KeyEvent.VK_DOWN:
				down = true;
				break;
			
			case 32:
				spacebar = true;
				break;
				
			case 27:
				escapeKey = true;
				break;
				
			case 10:
				returnKey = true;
				break;
				
			default:
				break;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case 37:
				left = false;
				break;
			
			case 39:
				right = false;
				break;
			
			case KeyEvent.VK_UP:
				up = false;
				break;
		
			case KeyEvent.VK_DOWN:
				down = false;
				break;
			
			case 32:
				spacebar = false;
				break;
				
			case 27:
				escapeKey = false;
				break;
				
			case 10:
				returnKey = false;
				break;
				
			default:
				break;
		}
	}
}