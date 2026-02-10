/**
	The DanceTile class is responsible for drawing one tile of the game's dance floor.
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

package Game;

import javax.swing.*;

import AbstractClasses.*;
import Background.BackgroundObject;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class DanceTile extends RenderObject
{
    private RenderObject gameObject = null;
    private boolean burning = false;
    private double size;
    private int tileNum;
    private BufferedImage sprite;
    private BackgroundObject fire;
    
    /**
        The constructor has four parameters.
        sprite is instantiated from the GameImages folder.
        @param p the location of the tile
        @param size the size of the tile
        @param tileNum the number of the tile
        @param color the color of the tile
    **/
    public DanceTile(Point2D.Double p, double size, int tileNum, Color color)
    {
        sprite = getSpriteFile("DanceTileSprite.png");
        loc = p;
        this.tileNum = tileNum;
        this.size = size;
        super.tint = color;
    }
    
    /**
        The draw method draws the tile on the canvas
        @param g2d the Graphics2D object which is responsible for drawing the specific tile
    **/
    public void draw(Graphics2D g2d)
    {
        Rectangle2D.Double r = new Rectangle2D.Double(loc.getX(), loc.getY(), size, size);
        g2d.setColor(Color.BLACK);
        g2d.fill(r);
        Rectangle2D.Double inner = new Rectangle2D.Double(loc.getX() + (size * 0.075), loc.getY() + (size * 0.075), size * 0.9, size * 0.9);
        g2d.setColor(tint);
        if (burning){
            g2d.setColor(Color.GRAY);
        }
        g2d.fill(inner);
        if (burning){
            setGameObject(fire);
            fire.update();
            fire.draw(g2d);
        }
    }

    /**
        The getPoint method returns the center point of the tile.
        @return x and y coordinates of the DanceTile
    **/
    public Point2D.Double getPoint()
    {
        return new Point2D.Double(loc.getX() + (size/2),loc.getY() + (size/2));
    }

    /**
        The getTileNum method returns the tile number.
        @return the number of the tile
    **/
    public int getTileNum()
    {
        return tileNum;
    }

    /**
        The changeColor method changes the color of the DanceTile.
        @param color the new color
    **/
    public void changeColor(Color color)
    {
        super.tint = color;
    }

    /**
        The burn method sets the DanceTile on fire.
    **/
    public void burn()
    {
        String[] s = new String[]{
            "./GameImages/fire-1.png",
            "./GameImages/fire-2.png",
            "./GameImages/fire-3.png",
            "./GameImages/fire-4.png",
            "./GameImages/fire-5.png",
            "./GameImages/fire-6.png",
            "./GameImages/fire-7.png",
        };
        fire = new BackgroundObject(s, 0.07, 0.1, new Point2D.Double(getPoint().getX(), getPoint().getY() - (size*0.3)), 5);
        setGameObject(fire);
        burning = true;
    }

    /**
        The isBurning method checks if the DanceTile is on fire.
        @return true if the DanceTile is burning, false otherwise
    **/
    public boolean isBurning()
    {
        return burning;
    }

    /**
        The getGameObject method returns the game object of the RenderObject class.
        @return the game object
    **/
    public RenderObject getGameObject()
    {
        return gameObject;
    }

    /**
        The setGameObject method sets the game object to a specified game object.
        @param ro the game object to be assigned to the gameObject field
    **/
    public void setGameObject(RenderObject ro)
    {
        gameObject = ro;
    }
}