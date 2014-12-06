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
	private boolean returnUnpressed;
	SpaceInvaders cont;
	
	private static final int NEW_GAME = 0;
	private static final int LOAD_GAME = 1;
	
	/*
	* Setup a main menu with a title text node, and three menu items text
	* nodes
	*/
	public MenuScene(int sizeX, int sizeY, SpaceInvaders controller)
	{
		super(sizeX, sizeY, "background.png");
		cont = controller;
		TextNode title = new TextNode("SPACE INVADERS",
			"kenpixel_mini",
			72,
			Color.white);
		title.setPosition(sizeX/2, 100);
		addChild(title);
		TextNode newGame = new TextNode("new game",
			"kenpixel_mini",
			40,
			Color.white);
		newGame.setPosition(sizeX/2, sizeY/2);
		TextNode resumeGame = new TextNode("resume game",
			"kenpixel_mini",
			40,
			Color.white);
		resumeGame.setPosition(sizeX/2, sizeY/2+64);
		TextNode quitGame = new TextNode("quit",
			"kenpixel_mini",
			40,
			Color.white);
		quitGame.setPosition(sizeX/2, sizeY/2+128);
		
		addChild(newGame);
		addChild(quitGame);
		addChild(resumeGame);
		
		items = new TextNode[] {newGame, resumeGame, quitGame};
		controls = cont.ctrl;
		currentChoice = NEW_GAME;
		keyUnPressed = true;
		returnUnpressed = true;
	}
	
	/*
	* Called every frame. Updates the highlighted item depending on user
	* input
	*/
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
		
		if(controls.returnKey && returnUnpressed)
		{
			dispatchAction();
			returnUnpressed = false;
		}
		else
		{
			returnUnpressed = true;
		}
		
		for(int i = 0; i < items.length; ++i)
		{
			if(i == currentChoice)
			{
				items[i].color = new Color(0xcc, 0x44, 0x44);
			}
			else
			{
				items[i].color = Color.white;
			}
		}
	}
	
	/*
	* Call the right action on the main game controller depending on the
	* currently highlighted item in the menu
	*/
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
	
	/*
	* Proper modulo (as opposed to %, remainder) maths operation.
	*/
	private int modulo(int x, int y)
	{
		int r = x % y;
		return (r < 0) ? r + y : r;
	}
}