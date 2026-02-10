/**
	The Reaper class is responsible for drawing the game's grim reapers scattered throughout the dance floor.
    Reapers randomly move around the floor.
    If a Player bumps into a reaper, their progress falls.
	
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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Reaper extends GameObject{

    private boolean shouldMove;
    private int moveIndex;
    private int initInterval;
    private int moveInterval;
    private int spriteIndex;
    private BufferedImage[] sprite;

    /**
        The constructor has two parameters.
        @param dt the Reaper's DanceTile
        @param moveInterval the interval by which the Reaper moves around the dance floor
    **/
    public Reaper(DanceTile dt, int moveInterval)
    {
        super(dt);
        super.movementSubdivisions = 15;
        super.height = (int) (super.height * 0.75);
        super.width = (int) (super.width * 0.75);
        this.initInterval = moveInterval;
        this.spriteIndex = 0;
        sprite = new BufferedImage[] {
            getSpriteFile("./GameImages/reaper_blade_3 (0_0).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (0_1).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (0_2).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (1_0).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (1_1).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (1_2).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (2_0).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (2_1).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (2_2).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (3_0).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (3_1).png"),
            getSpriteFile("./GameImages/reaper_blade_3 (3_2).png"),
        };
    }

    /**
        The shouldMove method identifies whether the Reaper should move.
        @return true if the Reaper should move, false otherwise
    **/
    public boolean shouldMove()
    {
        return shouldMove;
    }

    /**
        The update method updates all the instance fields.
    **/
    @Override
    public void update()
    {
        if (moveIndex > moveInterval){
            shouldMove = true;
        }
        super.update();
        if (moveIndex % 9 == 0){
            if ((spriteIndex + 1) % 3 == 0){
                spriteIndex = spriteIndex - 2;
            }else{
                spriteIndex++;
            }
        }
        moveIndex++;
        super.update();
        /*System.out.println(moveInterval + " " + moveIndex + " " + shouldMove + " " + spriteIndex
        + " " + moveIndex%50
        );*/
    }

    /**
        The draw method draws the Reaper on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the Reaper
    **/
    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(tintImage(sprite[spriteIndex]), (int) loc.getX() - (width/2),(int) (loc.getY() - (height * 0.8)), width, height, null);
    }

    /**
        The moveTo method moves the Reaper across the dance floor.
        @param danceTile the danceTile the Reaper should move to
    **/
    @Override
    public void moveTo(DanceTile danceTile){
        if (loc.getX() == danceTile.getPoint().getX()){
            if (loc.getY() < danceTile.getPoint().getY()){
                spriteIndex = 0;
            }else{
                spriteIndex = 9;
            }
        }else{
            if (loc.getX() < danceTile.getPoint().getX()){
                spriteIndex = 6;
            }else{
                spriteIndex = 3;
            }
        }
        super.moveTo(danceTile);
        moveIndex = 0;
        shouldMove = false;
        moveInterval = (int) ((Math.random() + 0.5) *  initInterval);
    }
}
