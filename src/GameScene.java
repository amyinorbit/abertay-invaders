package CesarParent;

import GameKit.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;

public class GameScene extends Scene implements java.io.Serializable
{
	private Random rnd;
	
	private TextNode scoreNode; // label used to display the score
	private TextNode livesNode; // label used to display lives
	private TextNode frameRateNode; // when DEBUG set to true, outputs data on the scene
	
	public SpaceInvaders cont; // the main game JFrame, used to exit the game scene
	public Controller controls; // the controller object, traps keystrokes
	private int score; // the current score
	private Player player; // the player SpriteNode
	private ArrayList<Invader> opponents; // the swarm of ennemies
	private Boss destroyer; // destroyer that can appear accross the screen
	
	private int asteroidCountDown; // frames until the next asteroid is spawned
	private int destroyerCountDown; // frames until the next destroyer is spawned
	
	public int firstPosition; // slot of the left-most alien in the swarm
	public int lastPosition; // slot of the right-most alien in the swarm
	
	private boolean reachedBoss; // wether the game is currently in boss (mothership) mode
	private boolean finished; // triggers the display of the game-over menu
	private int level; // the current level.
	
	/*
	* Initialises the game scene, creates a swarm of ennemies, and start the game
	*/
	public GameScene(int sizeX, int sizeY, SpaceInvaders controller)
	{
		super(sizeX, sizeY, "background.png");
		// basic variables setup
		score = 0;
		cont = controller;
		controls = cont.ctrl;
		reachedBoss = false;
		finished = false;
		level = 1;
		rnd = new Random();
		asteroidCountDown = rnd.nextInt(300) + 300;
		destroyerCountDown = rnd.nextInt(500) + 400;
		opponents = new ArrayList<Invader>();
		
		// Children nodes setup
		// Score, lives and debug
		scoreNode = new TextNode("Score: 0",
			"KenPixel-mini",
			30,
			Color.white);
		scoreNode.setPosition(10, 0);
		scoreNode.setAnchorPoint(0,0);
		livesNode = new TextNode("3 <3",
			"KenPixel-mini",
			32,
			Color.white);
		livesNode.setPosition(sizeX-10, 0);
		livesNode.setAnchorPoint(1,0);
		addChild(scoreNode);
		addChild(livesNode);
		
		frameRateNode = new TextNode("0",
			"Courrier",
			32,
			Color.white);
		frameRateNode.setAnchorPoint(0,1);
		frameRateNode.setPosition(0, sizeY);
		addChild(frameRateNode);
		
		// player
		player = new Player(controls);
		player.setAnchorPoint(0.5,1);
		player.setPosition(sizeX/2, sizeY);
		addChild(player);
		// create the swarm
		initInvaders();
	}
	
	/*
	* Update is called once each frame, before collisions are processed
	* and children node drawn
	*
	* It calls either the main game logic, or the game over menu.
	*/
	@Override
	public void update()
	{
		if(!finished)
		{
			// if escape, save the game and go back to the main menu
			if(controls.escapeKey)
			{
				cont.saveGame();
				cont.returnToMenu();
			}
			mainGameLogic();
		}
		else
		{
			if(controls.escapeKey)
			{
				cont.returnToMenu();
			}
		}
		frameRateNode.setText(framerate+" fps");
	}
	
	/*
	* calls "update()" on each child (aliens, asteroids, bullets, destroyer, player)
	* triggers cloaking and shooting for objects that implement those interfaces
	*/
	private void mainGameLogic()
	{
		// update the first and last position occupied in the swarm
		firstPosition = firstPositionInSwarm();
		lastPosition = lastPositionInSwarm();
		livesNode.setText(player.getLives()+" <3"); // update the lives indicator
		if(!reachedBoss) // if the boss is not reached, randomly add destroyers and asteroids
		{
			handleDestroyers();
			handleAsteroids();
		}
		for(Node child : children) // update all the children of the scene
		{
			child.update();
			if(child instanceof CanCloak)
			{
				((CanCloak)child).triggerCloak();
			}
			if(child instanceof CanShoot)
			{
				((CanShoot)child).triggerShoot();
			}
		}
		// if the swarm has been defeated and no destroyer is visible, send a mothership
		if(opponents.size() == 0 && !reachedBoss && !children.contains(destroyer))
		{
			spawnMothership();
		}
	}
	
	/*
	* If the countdown to the next destroyer has reached zero, create one,
	* and restart a countdown
	*/
	private void handleDestroyers()
	{
		if(destroyerCountDown > 0)
		{
			destroyerCountDown --;
		}
		else
		{
			destroyer = new Destroyer();
			addChild(destroyer);
			destroyerCountDown = rnd.nextInt(400) + 900;
		}
	}
	
	/*
	* If the countdown to the next asteroid has reached zero, create one,
	* and restart a countdown
	*/
	private void handleAsteroids()
	{
		if(asteroidCountDown > 0)
		{
			asteroidCountDown --;
		}
		else
		{
			addChild(new Asteroid(rnd.nextInt(getWidth()-40)+20));
			asteroidCountDown = rnd.nextInt(300) + 300;
		}
	}
	
	/*
	* Spawn a mothership
	*/
	public void spawnMothership()
	{
		addChild(new TimedLabel(getWidth()/2, getHeight()/2, "Mothership incoming", 50));
		reachedBoss = true;
		addChild(new Mothership(60, 80));
	}
	
	/*
	* Create a 6*4 swarm of aliens
	* The top row is made of mercurians
	* The second of martians,
	* The two bottom ones of Venusians
	*/
	public void initInvaders()
	{
		for(int i = 0; i < 4; ++i)
		{
			for(int j = 0; j < 6; ++j)
			{
				if(i == 0)
				{
					opponents.add(new Mercurian((j*60)+60, (i*50)+100, j));
				}
				else if(i == 1)
				{
					opponents.add(new Martian((j*60)+60, (i*50)+100, j));
				}
				else if(i >= 2)
				{
					opponents.add(new Venusian((j*60)+60, (i*50)+100, j));
				}
			}
		}
		for(Invader invader : opponents)
		{
			addChild(invader);
		}
	}
	
	/*
	* Returns the slot (x) occupied by the leftmost alien in the swarm
	* Positions range between 0 and 5, and are used for aliens to go down
	* at the right time and place
	*/
	protected int firstPositionInSwarm()
	{
		int position = 10;
		for(Invader invader : opponents)
		{
			if(invader.positionInSwarm < position)
			{
				position = invader.positionInSwarm;
			}
		}
		return position;
	}
	
	/*
	* Returns the slot (x) occupied by the rightmost alien in the swarm
	*/
	protected int lastPositionInSwarm()
	{
		int position = 0;
		for(Invader invader : opponents)
		{
			if(invader.positionInSwarm > position)
			{
				position = invader.positionInSwarm;
			}
		}
		return position;
	}
	
	/*
	* Called by aliens when they reach the bottom of the scene
	* Removes a life from the player
	*/
	public void invaderReachedBottom()
	{
		player.removeLife();
	}
	
	/*
	* Add `points` to the curent score
	*/
	public void addScore(int points)
	{
		score += points;
		scoreNode.setText("score: "+score);
	}
	
	/*
	* Triggered by the death of a mothership.
	* The speed of the alien swarm is incremented, and the delay between
	* player bullets slightly reduced.
	* Then, a new swarm is spawned
	*/
	public void nextLevel()
	{
		opponents.clear();
		Settings.instance().invaderSpeed = ++level;
		Settings.instance().shootDelay -=(int).05*60;
		initInvaders();
		reachedBoss = false;
	}
	
	/*
	* Display the score of the lost game, the high score,
	* And prompts for a return to the main menu
	*/
	public void gameOver()
	{
		if(score > Settings.instance().highScore)
		{
			Settings.instance().highScore = score;
		}
		
		TextNode outcome = new TextNode("you died",
				"KenPixel-mini",
				64,
				Color.white);
		outcome.setPosition(getWidth()/2, 80);
		addChild(outcome);

		String scoreStr = "Final Score: "+score;
		TextNode scoreText = new TextNode(scoreStr,
			"KenPixel-mini",
			32,
			Color.white);
		scoreText.setPosition(getWidth()/2, getHeight()/2+60);
		addChild(scoreText);
		
		TextNode highScore = new TextNode("High Score: "+Settings.instance().highScore,
			"KenPixel-mini",
			32,
			Color.white);
		highScore.setPosition(getWidth()/2, getHeight()/2+120);
		addChild(highScore);
		
		TextNode pressSpace = new TextNode("press <esc> to continue",
			"KenPixel-mini",
			24,
			Color.white);
		pressSpace.setPosition(getWidth()/2, getHeight()-40);
		addChild(pressSpace);
		Settings.instance().invaderSpeed = 1;
		Settings.instance().shootDelay = 30;
		Settings.instance().writeSettingFile();
		finished = true;
	}
	
	/*
	* Removes the removed child from the opponent list as well as from the children array.
	*/
	@Override
	public void removeChild(Node child)
	{
		super.removeChild(child);
		opponents.remove(child);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		Settings.instance().invaderSpeed = level;
	}
	
}