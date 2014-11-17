package CesarParent;

import GameKit.*;

public class Venusian extends Invader implements  java.io.Serializable
{
	public Venusian(int x, int y, int position)
	{
		super("venusian.png", x, y);
		points = 20;
		lives = 1;
		positionInSwarm = position;
	}
}