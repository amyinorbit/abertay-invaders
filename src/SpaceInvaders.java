package CesarParent;

import GameKit.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JOptionPane;
import java.io.*;

class SpaceInvaders extends JFrame
{
	Scene currentScene;
	Controller ctrl;
	int sizeX;
	int sizeY;
	
	public static void main(String[] args) {
		try{
			
			SpaceInvaders game = new SpaceInvaders();
			game.init();
		}
		catch(Exception e)
		{
			String message = "Runtime error:\n\n"+e.getMessage();
			JOptionPane.showMessageDialog(null,message);
			System.exit(1);
		}
	}
	
	/*
	* Launch the application and presents the main menu
	*/
	public void init()
	{
		sizeX = Settings.instance().width;
		sizeY = Settings.instance().height;
		ctrl = new Controller();
		currentScene = new MenuScene(sizeX, sizeY, this);
		currentScene.addKeyListener(ctrl);
		currentScene.requestFocusInWindow();
		
		Container pane = getContentPane();
		pane.add(currentScene);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		currentScene.beginRendering();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	
	/*
	* Create a new GameScene from level 1 and presents it instead of the menu
	*/
	public void startNewGame()
	{
		currentScene.stopRendering();
		Container pane = getContentPane();
		pane.removeAll();
		currentScene = new GameScene(sizeX, sizeY, this);
		pane.add(currentScene, BorderLayout.CENTER);
		currentScene.addKeyListener(ctrl);
		currentScene.requestFocusInWindow();
		validate();
		repaint();
		currentScene.beginRendering();
	}
	
	/*
	* Stops the current GameScene, destroy it and present the main menu
	*/
	public void returnToMenu()
	{
		currentScene.stopRendering();
		Container pane = getContentPane();
		pane.removeAll();
		currentScene = new MenuScene(sizeX, sizeY, this);
		pane.add(currentScene, BorderLayout.CENTER);
		currentScene.addKeyListener(ctrl);
		currentScene.requestFocusInWindow();
		validate();
		repaint();
		currentScene.beginRendering();
	}
	
	/*
	* Tries to load a saved GameScene from the disk, and launches it if
	* it was read properly.
	*/
	public void loadGame()
	{
		GameScene scene = null;
		try
		{
			String path = System.getProperty("user.home") + File.separator + "invaders.ser";
			FileInputStream fi = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fi);
			scene = (GameScene)in.readObject();
			in.close();	
			fi.close();
		}
		catch(IOException ex)
		{
			String message = "Error loading savegame:\n\n"+ex.getMessage();
			JOptionPane.showMessageDialog(null,message);
			return;
		}
		catch(ClassNotFoundException cnf)
		{
			String message = "Error loading savegame:\n\n"+cnf.getMessage();
			JOptionPane.showMessageDialog(null,message);
			return;
		}
		
		currentScene.stopRendering();
		Container pane = getContentPane();
		pane.removeAll();
		currentScene = scene;
		pane.add(currentScene, BorderLayout.CENTER);
		((GameScene)currentScene).cont = this;
		((GameScene)currentScene).controls = this.ctrl;
		currentScene.addKeyListener(ctrl);
		currentScene.requestFocusInWindow();
		validate();
		repaint();
		currentScene.beginRendering();
	}
	
	/*
	* Saves the current GameScene to the disk
	*/
	public void saveGame()
	{
		if(currentScene instanceof GameScene)
		{
			try
			{
				String path = System.getProperty("user.home") + File.separator + "invaders.ser";
				FileOutputStream fo = new FileOutputStream(path);
				ObjectOutputStream out = new ObjectOutputStream(fo);
				out.writeObject(currentScene);
				out.close();
				fo.close();
			}
			catch(IOException ex)
			{
				String message = "Error saving game:\n\n"+ex.getMessage();
				JOptionPane.showMessageDialog(null,message);
				return;
			}
		}
	}
}