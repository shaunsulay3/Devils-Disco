/**
	The Player class represents a single player of the game.
    It extends the GameObject class.
	
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
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import AbstractClasses.RenderObject;

import java.awt.*;

public class Player extends GameObject
{
    private boolean isHurt = false;

    private BufferedImage activeSprite;
    private BufferedImage sprite;
    private BufferedImage sprite2;

    protected ProgressBar progressBar;

    /**
        The constructor has one parameter.
        sprites are initialized to their respective images.
        A new progressBar is instantiated for the Player.
        @param dt the Player's DanceTile
    **/
    public Player(DanceTile dt)
    {
        super(dt);
        sprite = getSpriteFile("./GameImages/Dancer1.0.png");
        sprite2 = getSpriteFile("./GameImages/Dancer1.1.png");
        activeSprite = sprite;
        progressBar = new ProgressBar(this);

    }

    /**
        The draw method draws the Player on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the specific Player
    **/
    public void draw(Graphics2D g2d)
    {

        //g2d.draw(new Rectangle((int) loc.getX() - (width/2), (int) loc.getY() - height, width, height));
        if (isHurt){
            g2d.drawImage(tintImage(activeSprite), (int) loc.getX() - (width/2),(int) loc.getY() - height, width, height, null);
        }else{
            g2d.drawImage(activeSprite, (int) loc.getX() - (width/2),(int) loc.getY() - height, width, height, null);
        }
    }

    /**
        The moveTo method moves the Player to a DanceTile.
        @param dt the DanceTile to move the Player to
    **/
    @Override
    public void moveTo(DanceTile dt)
    {
        super.moveTo(dt);
        if (spriteAlternate){
            activeSprite = sprite;
        }else{
            activeSprite = sprite2;
        }
        spriteAlternate = !spriteAlternate;
    }

    /**
        The update method updates all the instance fields.
    **/
    @Override
    public void update()
    {
        if (isHurt){
            if (tint.getAlpha() == 0){
                tint = Color.RED;
                getProgressBar().update(-1);
            }else{
                if (tint.getAlpha() - 8 <= 0){
                    Color c = new Color(0, 0, 0, 0);
                    tint = c;
                    isHurt = false;
                    getProgressBar().changeColor(c);
                }else{
                    Color c = new Color(
                        tint.getRed(),
                        tint.getGreen(),
                        tint.getBlue(),
                        tint.getAlpha() - 8
                    );
                    tint = c;
                    getProgressBar().changeColor(c);
                }
            }
            
        }
        super.update();
    }

    /**
        The getProgressBar method returns the Player's progressBar.
        @return the Player's progressBar
    **/
    public ProgressBar getProgressBar()
    {
        return progressBar;
    }

    /**
        The setMovementSubdivisions method dictates how many times a Player moves before moving to another DanceTile.
        @param ms the amount of times the Player moves
    **/
    public void setMovementSubdivisions(int ms)
    {
        this.movementSubdivisions = ms;
    }

    /**
        The hurt method designates the Player as hurt.
    **/
    public void hurt()
    {
        isHurt = true;
    }
}
