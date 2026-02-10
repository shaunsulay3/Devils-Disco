/**
	The BackgroundObject class represents a single background object of the game.
    It extends the RenderObject class.
	
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

package Background;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import AbstractClasses.RenderObject;

public class BackgroundObject extends RenderObject{

    protected BufferedImage[] sprites;
    protected int spriteIndex;
    protected int updateInterval;
    protected Point2D.Double loc;
    protected int width;
    protected int height;

    /**
        The constructor has five parameters.
        @param imgPath the String pathname
        @param relativeWidth the relative width
        @param relativeHeight the relative height
        @param loc the x and y coordinates for the position
        @param updateInterval how many frames passed until the next animation
    **/
    public BackgroundObject(String[] imgPath, double relativeWidth, double relativeHeight, Point2D.Double loc, int updateInterval)
    {

        this.width = (int) (super.screenScaling * relativeWidth);
        this.height = (int) (super.screenScaling * relativeHeight);
        this.loc = loc;
        this.updateInterval = updateInterval;
        this.sprites = new BufferedImage[imgPath.length];
        for(int i = 0; i < imgPath.length; i++){
            sprites[i] = getSpriteFile(imgPath[i]);
        }
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        if (super.framesElapsed % updateInterval == 0){
            spriteIndex++;
            if (spriteIndex >= sprites.length){
                spriteIndex = 0;
            }
        }
    }

    /**
        The draw method draws the instance on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the specific object
    **/
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(
            sprites[spriteIndex],
            (int) loc.getX() - (width/2),
            (int) loc.getY() - (height/2), 
            width,
            height,
            null);
    }

    /**
        The getWidth method returns the width of the instance.
        @return the width of the instance
    **/
    public int getWidth()
    {
        return width;
    }

    /**
        The getHeight method returns the height of the instance.
        @return the height of the instance
    **/
    public int getHeight()
    {
        return height;
    }
}
