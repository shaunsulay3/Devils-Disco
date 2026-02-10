/**
	The ProgressBar class is responsible for drawing each Player object's progress bar.
    If a Player's progress bar becomes full, the Player wins the round.
	
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
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import AbstractClasses.RenderObject;

public class ProgressBar extends RenderObject
{
    private static boolean locked = false;
    private boolean playerWon = false;
    private boolean tokenLeftSide;

    private int width = 75;
    private int points = 65;
    private int token = 0;
    private int tokenSize = 30;
    private double progress = 0;
    private double fullBar = 350;

    private BufferedImage tokenSprite;
    private Player player;

    /**
        The constructor has one parameter.
        @param p the assigned Player of the progressBar
    **/
    public ProgressBar(Player p)
    {
        this.player = p;
    }

    /**
        The setLoc method sets the progressBar's location.
        @param loc the x and y coordinates of the progressBar
    **/
    public void setLoc(Point2D.Double loc)
    {
        super.loc = loc;
        tokenLeftSide = loc.getX() > screenDimension.getWidth()/2;
    }

    /**
        The update method updates the progressBar.
        @param addProgress the amount to add to the progressBar
    **/
    public void update(double addProgress)
    {
        if (progress + (addProgress * points) > 0 && !(locked)){
            if (progress + addProgress >= fullBar){
                progress = fullBar;
                locked = true;
            }else{
                progress += (addProgress * points);
            }
        }
    }

    /**
        The reset method resets the progressBar.
    **/
    public void reset()
    {
        locked = false;
        playerWon = false;
        progress = 0;
    }

    /**
        The draw method draws the progressBar on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the progressBar
    **/
    public void draw(Graphics2D g2d)
    {
        Rectangle2D.Double bckg = new Rectangle2D.Double(loc.getX(),loc.getY() - fullBar,width, fullBar);
        g2d.setColor(Color.GRAY);
        g2d.fill(bckg);
        if (playerWon){
            Rectangle2D.Double r = new Rectangle2D.Double(loc.getX(),loc.getY() - fullBar,width, fullBar);
            g2d.setColor(Color.GREEN);
            g2d.fill(r);
        }else{
            Rectangle2D.Double r = new Rectangle2D.Double(loc.getX(),loc.getY() - progress,width, progress);
            g2d.setColor(Color.GREEN);
            g2d.fill(r);
            g2d.setColor(tint);
            g2d.fill(r);
        }
        for (int i = 0; i < token; i++){
            Ellipse2D.Double g;
            if (!tokenLeftSide){
                g = new Ellipse2D.Double(loc.getX()+width+25, loc.getY() - 25 - (i * 70),tokenSize,tokenSize);
            }else{
                g = new Ellipse2D.Double(loc.getX() - 25 - tokenSize, loc.getY() - 25 - (i * 70),tokenSize,tokenSize);
            }
            g2d.setColor(Color.YELLOW);
            g2d.draw(g);
        }

    }

    /**
        The earnToken method gives the Player a token for every round won.
    **/
    public void earnToken()
    {
        token++;
    }

    /**
        The getWidth method returns the width of the progressBar.
        @return the width of the progressBar
    **/
    public int getWidth()
    {
        return width;
    }

    /**
        The hasPlayerWon method checks if a Player has won the round by checking if the progressBar is full.
        @return true if a Player has filled up their progressBar, false otherwise
    **/
    public boolean hasPlayerWon()
    {
        if (progress >= fullBar){
            playerWon = true;
            progress = 0;
            return true;
        }
        return false;
    }

    /**
        The changeColor method changes the color of the progressBar.
        @param c the new color
    **/
    public void changeColor(Color c)
    {
        tint = c;
    }

    /**
        The getTokens method returns the number of tokens a Player has won.
        @return the number of tokens
    **/
    public int getTokens()
    {
        return token;
    }

    /**
        The getPlayer method returns the progressBar's assigned Player.
        @return the Player object
    **/
    public Player getPlayer()
    {
        return player;
    }
}
