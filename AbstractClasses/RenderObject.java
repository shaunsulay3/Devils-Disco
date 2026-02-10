/**
	The RenderObject class is an abstract class.
    It is extended by several classes to help with rendering objects for the game interface.
	
	@author Antonio Shaun L. Sulay III (236987) & Lucia Danielle P. Sulay (225985)
	@version 08 April 2024
	
	We have not discussed the Java language code in our program 
	with anyone other than our instructors or the teaching assistants 
	assigned to this course.

	We have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in our program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of our program.
**/

package AbstractClasses;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


import javax.imageio.ImageIO;

import Game.DanceTile;

public abstract class RenderObject
{
    /**
        The GameColors class is an inner class.
        It is responsible for generating the colors of the game interface.
    **/
    public class GameColors
    {
        public static final Color[] colors = new Color[]{
            Color.RED,
            new Color(57,230,90),
            new Color(203,48,107),
            new Color(207,255,4),
            new Color(106,52,133),
            new Color(19,226,236)
        };
        private static int winningColorIndex;

        /**
            The changeWinningColor method changes the winning color.
        **/
        public static void changeWinningColor(int index)
        {
            winningColorIndex = index;
        }

        /**
            The getWinningColor method returns the winning color.
            @return the winning color
        **/
        public static Color getWinningColor()
        {
            return colors[winningColorIndex];
        }

        /**
            The getNumColors method returns the number of colors.
            @return the number of colors
        **/
        public static int getNumColors()
        {
            return colors.length;
        }
    }

    public static boolean skullRound = false;
    protected Point2D.Double loc;
    protected Color tint = new Color(0, 0, 0, 0);
    protected static double screenScaling;
    protected static Dimension screenDimension;
    protected static int framesElapsed;
    protected int tileNum;
    
    /**
        The randomizeValue method returns a random integer value.
        @param value the base integer value
        @param low the lower bound of the value range
        @param high the upper bound of the value range
        @param plusMinus the allowance of the range
    **/
    protected static int randomizeValue(int value, int low, int high, int plusMinus){
        int i;
        do{
            i = (int) (value + ((plusMinus*2) * Math.random()) - plusMinus);
        }while(i > high || i < low);
            return i;
    }

    /**
        The other randomizeValue method returns a random double value.
        @param value the base double value
        @param low the lower bound of the value range
        @param high the upper bound of the value range
        @param plusMinus the allowance of the range
    **/
    protected static double randomizeValue(double value, double low, double high, double plusMinus){
        double i;
        do{
            i = (value + ((plusMinus*2) * Math.random()) - plusMinus);
        }while(i > high || i < low);
            return i;
    }
    
    /**
        The addFramesElapsed method handles frame count.
        @param i the number of frames elapsed to add to the initial value
    **/
    protected static void addFramesElapsed(int i)
    {
        framesElapsed += i;
    }

    /**
        The setStaticScreenScaling method sets the screen pixel size.
        @param d the dimensions of the screen
    **/
    protected static void setStaticScreenScaling(Dimension d)
    {
        screenDimension = d;
        if (d.getWidth() < d.getHeight()){
            screenScaling = d.getWidth();
        }else{
            screenScaling = d.getHeight();
        }
    }

    /**
        The draw method renders the RenderObject object on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the RenderObject instance
    **/
    public void draw(Graphics2D g2d)
    {

    }

    /**
        The update method updates all the instance fields.
    **/
    public void update(){

    }

    /**
        The getTint method returns the needed color tint.
        @return the color tint
    **/
    public Color getTint()
    {
        return tint;
    }

    /**
        The getLocation method returns the location of the instance on the canvas.
        @return the object's x and y coordinates
    **/
    public Point2D.Double getLocation()
    {
        return loc;
    }

    /**
        The getSpriteFile method returns the BufferedImage of a specific sprite.
        @param path the String pathname of the BufferedImage
        @return the BufferedImage
    **/
    protected BufferedImage getSpriteFile(String path)
    {
        try {
            BufferedImage sprite = ImageIO.read(new File(path));
            return sprite;
        } catch (IOException e) {
        }
        return null;
    }

    /**
        The tintImage method tints a BufferedImage.
        @param image the image to be tinted
        @return the tinted BufferedImage
    **/
    protected BufferedImage tintImage(BufferedImage image)
    {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(tint);
        g.fillRect(0,0,w,h);
        g.dispose();
        return dyed;
    }

    /**
        The getLoc method returns the location of the RenderObject instance.
        @return the x and y coordinates of the RenderObject object
    **/
    public Point2D.Double getLoc()
    {
        return loc;
    }
}
