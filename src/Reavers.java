package CesarParent;

import GameKit.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;

class Reavers extends JFrame
{
	MainScene mainGame;
	
	public static void main(String[] args) {
		try{
			
			Reavers game = new Reavers();
			game.init();
			game.setSize(640, 480);
			game.setResizable(false);
			game.setVisible(true);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public void init()
	{
		Container pane = getContentPane();
		mainGame = new MainScene();
		mainGame.beginRendering();
		pane.add(mainGame, BorderLayout.CENTER);
	}
}