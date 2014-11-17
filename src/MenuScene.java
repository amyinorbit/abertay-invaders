package CesarParent;

import GameKit.*;
import java.util.ArrayList;
import java.awt.Color;

public class MenuScene extends Scene
{
	private TextNode[] items;
	private Controller controls;
	private int currentChoice;
	private boolean keyUnPressed;
	SpaceInvaders cont;
	
	private static final int NEW_GAME = 0;
	private static final int LOAD_GAME = 1;
	
	public MenuScene(int sizeX, int sizeY, SpaceInvaders controller)
	{
		super(sizeX, sizeY, "background.png");
		cont = controller;
		TextNode title = new TextNode("SPACE INVADERS",
			"KenPixel-mini",
			72,
			Color.white);
		title.setPosition(sizeX/2, 100);
		addChild(title);
		TextNode newGame = new TextNode("new game",
			"KenPixel-mini",
			40,
			Color.white,
			new Color(0x54, 0x43, 0x4b));
		newGame.setPosition(sizeX/2, sizeY/2);
		TextNode resumeGame = new TextNode("resume game",
			"KenPixel-mini",
			40,
			Color.white,
			new Color(0x54, 0x43, 0x4b));
		resumeGame.setPosition(sizeX/2, sizeY/2+64);
		TextNode quitGame = new TextNode("quit",
			"KenPixel-mini",
			40,
			Color.white,
			new Color(0x54, 0x43, 0x4b));
		quitGame.setPosition(sizeX/2, sizeY/2+128);
		
		addChild(newGame);
		addChild(quitGame);
		addChild(resumeGame);
		
		items = new TextNode[] {newGame, resumeGame, quitGame};
		controls = cont.ctrl;
		currentChoice = NEW_GAME;
		keyUnPressed = true;
	}
	
	@Override
	public void update()
	{
		if(controls.up && !controls.down)
		{
			if(keyUnPressed)
			{
				currentChoice = modulo((currentChoice-1), 3);
			}
			keyUnPressed = false;
		}
		else if(controls.down && !controls.up)
		{
			if(keyUnPressed)
			{
				currentChoice = modulo((currentChoice+1), 3);
			}
			keyUnPressed = false;
		}
		else
		{
			keyUnPressed = true;
		}
		
		if(controls.returnKey)
		{
			dispatchAction();
		}
		
		for(int i = 0; i < items.length; ++i)
		{
			if(i == currentChoice)
			{
				items[i].color = Color.black;
				items[i].background = Color.white;
			}
			else
			{
				items[i].color = Color.white;
				// 987a88 #54434b
				items[i].background = new Color(0x54, 0x43, 0x4b);
			}
		}
	}
	
	private void dispatchAction()
	{
		if(currentChoice == 0)
		{
			cont.startNewGame();
		}
		else if(currentChoice == 1)
		{
			cont.loadGame();
		}
		else if(currentChoice == 2)
		{
			System.exit(0);
		}
	}
	
	private int modulo(int x, int y)
	{
		int r = x % y;
		return (r < 0) ? r + y : r;
	}
}