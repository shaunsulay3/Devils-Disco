/**
	The GameObject class is responsible for the objects in the game on top of a DanceTile.
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

import java.awt.geom.Point2D;

import AbstractClasses.RenderObject;

public abstract class GameObject extends RenderObject
{
    protected int width = (int) (super.screenScaling * 0.17);
    protected int height = (int) (super.screenScaling* 0.17);
    protected double movementSubdivisions = 2;
    protected int stepCounter = 0;
    protected boolean spriteAlternate = false;
    protected boolean isDoneMoving = true;
    protected Point2D.Double toLoc;
    protected double movementLengthX;
    protected double movementLengthY;
    protected DanceTile danceTile;

    /**
        The constructor has one parameter.
        @param dt the DanceTile the GameObject is on
    **/
    public GameObject(DanceTile dt)
    {
        this.danceTile = dt;
        super.loc = danceTile.getPoint();
        this.toLoc = loc;
        dt.setGameObject(this);
    }

    /**
        The moveTo method moves the GameObject to another DanceTile
        @param dt the DanceTile where the GameObject will move
    **/
    public void moveTo(DanceTile dt)
    {
        this.danceTile.setGameObject(null);
        this.danceTile = dt;
        dt.setGameObject(this);        
        this.toLoc.setLocation(danceTile.getPoint());
        this.movementLengthX = toLoc.getX() - loc.getX();
        this.movementLengthY = toLoc.getY() - loc.getY();
        this.isDoneMoving = false;
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        if (stepCounter >= movementSubdivisions){
            this.loc.setLocation(toLoc);
            this.stepCounter = 0;
            this.isDoneMoving = true;
        }else if (isDoneMoving == false){
            loc = new Point2D.Double(
                loc.getX() + (movementLengthX/movementSubdivisions),
                loc.getY() + (movementLengthY/movementSubdivisions)
            );
            stepCounter++;
        }
    }

    /**
        The isDoneMoving method checks if the GameObject is done moving.
        @return true if the GameObject is done moving, false otherwise
    **/
    public boolean isDoneMoving()
    {
        return isDoneMoving;
    }

    /**
        The setDanceTile method assigns a DanceTile to the GameObject.
        @param dt the assigned DanceTile
    **/
    protected void setDanceTile(DanceTile dt)
    {
        danceTile = dt;
    }

    /**
        The getDanceTile method gets the assigned DanceTile of the GameObject.
        @return the assigned DanceTile
    **/
    public DanceTile getDanceTile()
    {
        return danceTile;
    }
}