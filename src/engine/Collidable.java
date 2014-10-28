package GameKit;

/*
* GameKit.Collidable - Nodes implementing Collidable will be collided by their
* parent scene every frame
*
* Created on 2014-10-28 by Cesar Parent <http://cesarparent.com>
*/
public interface Collidable
{
	public void didCollideWithNode(Node node);
	public boolean shouldCollideWithNode(Node node);
}