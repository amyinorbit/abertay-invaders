package GameKit;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import java.util.HashMap;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.font.*;
import java.awt.FontFormatException;

/*
* Abstraction class to load media (image and audio) files.
* When an image is requested, Media checks if the file has already been loaded
* 	if yes, it returns the pre loaded file
*	otherwise, the file is loaded and added to the library
*/
public class Media
{
	private HashMap<String, AudioInputStream> audioStreams;
	private HashMap<String, BufferedImage> images;
	private HashMap<String, Font> fonts;
	
	private static Media instance = null;
	
	/*
	 * Create a new instance of Media. Can only be called by instance()
	 */
	protected Media()
	{
		audioStreams = new HashMap<String, AudioInputStream>();
		images = new HashMap<String, BufferedImage>();
		fonts = new HashMap<String, Font>();
	}
	
	/*
	 * Return the singleton instance. This prevents the user to load
	 * multiple instances pointing to the same files.
	 */
	public static Media instance()
	{
		if(instance == null)
		{
			instance = new Media();
		}
		return instance;
	}
	
	/*
	 * Preload Image files in the cache
	 */
	public void preloadImages(String[] images)
	{
		for(int i = 0; i < images.length; ++i)
		{
			imageNamed(images[i]);
		}
	}
	
	/*
	 * Preload Audio files in the cache
	 */
	public void preloadAudio(String[] files)
	{
		for(int i = 0; i < files.length; ++i)
		{
			audioStreamNamed(files[i]);
		}
	}
	
	/*
	 * Return a Font object, loaded from a .ttf file or the cache
	 */
	public Font fontNamed(String filename, int size)
	{
		if(fonts.containsKey(filename+size))
		{
			return fonts.get(filename+size);
		}
		Font font = null;
		try
		{
			InputStream in = getClass().getResourceAsStream("/"+filename+".ttf");
			Font base = Font.createFont(Font.TRUETYPE_FONT, in);
			font = base.deriveFont(Font.PLAIN, size);
		}
		catch(FontFormatException e)
		{
			System.err.println("FONT FORMAT ERROR");
			font = new Font(filename, Font.PLAIN, size);
		}
		catch(IOException e)
		{
			font = new Font(filename, Font.PLAIN, size);
		}
		fonts.put(filename+size, font);
		return font;
	}
	
	/*
	 * Return a buffered image loaded from a file or the cache
	 */
	public BufferedImage imageNamed(String filename)
	{
		if(images.containsKey(filename))
		{
			return images.get(filename);
		}
		BufferedImage img = loadImageFromFile(filename);
		images.put(filename, img);
		return img;
	}
	
	/*
	 * Load an image from the disk and return a BufferedImage
	 */
	private BufferedImage loadImageFromFile(String filename)
	{
		BufferedImage image = null;
		try
		{
			URL url = getClass().getResource("/"+filename);
			image = ImageIO.read(url);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"Error while loading image "+filename+"\n\n"+e.getMessage());
			System.exit(1);
		}
		catch(IllegalArgumentException e)
		{
			JOptionPane.showMessageDialog(null,
				"Error while loading image "+filename+"\n\n"+e.getMessage());
			System.exit(1);
		}
		return image;
	}
	
	
	/*
	 * Play an audio file.
	 */
	public void playAudioFile(String filename)
	{
		try
		{
			AudioInputStream audioIn = audioStreamNamed(filename);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}
	    catch(LineUnavailableException e)
		{
			System.err.println("Audio System unsupported");
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"Error loading file "+filename+"\n\n"+e.getMessage());
			System.exit(1);
		}
		catch(IllegalArgumentException e)
		{
			JOptionPane.showMessageDialog(null,
				"Error loading file "+filename+"\n\n"+e.getMessage());
			System.exit(1);
		}
	}
	
	/*
	 * Return a AudioInputStream object either from the disk or the cache
	 */
	private AudioInputStream audioStreamNamed(String filename)
	{
		if(audioStreams.containsKey(filename))
		{
			return audioStreams.get(filename);
		}
		URL url = getClass().getResource("/"+filename);
		AudioInputStream audioIn = null;
		try
		{
			audioIn = AudioSystem.getAudioInputStream(url);
		}
		catch(UnsupportedAudioFileException e)
		{
			JOptionPane.showMessageDialog(null,
				"Unsupported audio file type: "+e.getMessage());
			System.exit(1);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"Error loading file "+e.getMessage());
			System.exit(1);
		}
		return audioIn;
	}
}