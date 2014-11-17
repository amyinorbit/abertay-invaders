package CesarParent;

import java.io.*;
import java.util.Arrays;

/*
* Settings provides a way to read the settings written in a game.cfg file
* Settings is a singleton, and can only be accessed through Settings.instance()
*/
public class Settings
{
	private static Settings instance = null;
	
	int playerSpeed = 6;
	int invaderSpeed = 1;
	int shootDelay = 30;
	int playerBulletSpeed = 10;
	int alienBulletSpeed = 8;
	int width = 800;
	int height = 600;
	int highScore = 0;
	
	/*
	* Loads the game.cfg file from the jar package, and parses it line by line.
	*/
	protected Settings()
	{
		try
		{
			String path = System.getProperty("user.home") + File.separator + "invaders.cfg";
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = br.readLine()) != null) {
				parseLineFromCFG(line);
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("No existing config file. Creating default file");
			readDefaultFile();
		}
	}
	
	/*
	* Return the singleton instance of Settings. Settings should always be accessed using instance()
	*/
	public static Settings instance()
	{
		if(instance == null)
		{
			instance = new Settings();
		}
		return instance;
	}
	
	/*
	* Parses the content of the default config file bundled in the har
	*/
	protected void readDefaultFile()
	{
		try
		{
			InputStream in = getClass().getResourceAsStream(File.separator +"game.cfg");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = reader.readLine()) != null) {
				parseLineFromCFG(line);
			}
			reader.close();
		}
		catch(IOException e)
		{
			throw new RuntimeException("Error reading default config file:\n\n"+e.getMessage()+"");
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error reading default config file.");
		}
	}
	
	/*
	* Parses a line of config file and changes the settings accordingly.
	*/
	protected void parseLineFromCFG(String line)
	{
		String[] parts = line.split("\\s*(=|:)\\s*", 2);
		if(parts.length < 2)
		{
			throw new RuntimeException("Invalid parameter in Config file:\n\n"+line+"");
		}
		else if(parts[0].equals("playerSpeed"))
		{
			playerSpeed = Integer.parseInt(parts[1]);
		}
		else if(parts[0].equals("playerBulletSpeed"))
		{
			playerBulletSpeed = Integer.parseInt(parts[1]);
		}
		else if(parts[0].equals("alienBulletSpeed"))
		{
			alienBulletSpeed = Integer.parseInt(parts[1]);
		}
		else if(parts[0].equals("windowWidth"))
		{
			width = Integer.parseInt(parts[1]);
		}
		else if(parts[0].equals("windowHeight"))
		{
			height = Integer.parseInt(parts[1]);
		}
		else if(parts[0].equals("highScore"))
		{
			highScore = Integer.parseInt(parts[1]);
		}
	}
	
	public void writeSettingFile()
	{
		String path = System.getProperty("user.home") + File.separator + "invaders.cfg";
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write("playerSpeed: "+playerSpeed); writer.newLine();
			writer.write("playerBulletSpeed: "+playerBulletSpeed); writer.newLine();
			writer.write("alienBulletSpeed: "+alienBulletSpeed); writer.newLine();
			writer.write("windowWidth: "+width); writer.newLine();
			writer.write("windowHeight: "+height); writer.newLine();
			writer.write("highScore: "+highScore); writer.newLine();
			writer.close(); 
		}
		catch(IOException e)
		{
			throw new RuntimeException("Unable to write configuration file");
		}
	}
}