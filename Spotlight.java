/**
	The Spotlight class is responsible for the game's spotlight effect.
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import AbstractClasses.RenderObject;

public class Spotlight extends RenderObject
{
    private Color winningColor;
    private Color myColor;
    private double size;
    private boolean isFlashing = false;

    private double xDistance;
    private double yDistance;
    private Point2D.Double centerLoc;
    private double d = Math.random() * (Math.PI * 2);
    private double innerSpeed;

    private double innerXDistance;
    private double innerYDistance;
    private Point2D.Double nukeLock;
    private double d1 = Math.random() * (Math.PI * 2);
    private double outerSpeed;

    /**
        The constructor has six parameters.
        @param nukeLock point where the Spotlight pivots around
        @param color the color of the lights
        @param xDistance the horizontal distance of the lights
        @param yDistance the vertical distance of the lights
        @param size the size of the lights
        @param outerSpeed the speed of the lights
    **/
    public Spotlight(Point2D.Double nukeLock, Color color, double xDistance, double yDistance,double size,double outerSpeed)
    {
        this.size = size;

        this.nukeLock = nukeLock;
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.outerSpeed = outerSpeed * 0.01;

        this.innerXDistance = xDistance * 0.6;
        this.innerYDistance = yDistance * 0.6;
        this.innerSpeed = this.outerSpeed;


        centerLoc = new Point2D.Double((Math.sin(d) * innerXDistance) + nukeLock.getX(), (Math.cos(d) * innerYDistance) + nukeLock.getY());
        loc = new Point2D.Double((Math.sin(d) * xDistance) + centerLoc.getX(), (Math.cos(d) * yDistance) + centerLoc.getY());

        if (winningColor == null){
            winningColor = GameColors.getWinningColor();
        }
        makeNewColor(200);
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        if (!(winningColor.equals(GameColors.getWinningColor()))){
            winningColor = GameColors.getWinningColor();
            makeNewColor(200);
        }

        centerLoc.x = (Math.sin(d1) * innerXDistance) + nukeLock.getX();
        centerLoc.y =(Math.cos(d1) * innerYDistance) + nukeLock.getY();
        if (d1 + innerSpeed >= Math.PI * 2 || d1 + innerSpeed <= -(Math.PI * 2)){
            d1 = 0;
            innerSpeed = (innerSpeed * Math.random()) + (innerSpeed/2);
        }
        d1 += innerSpeed;

        loc.x = (Math.sin(d) * xDistance) + centerLoc.getX();
        loc.y =(Math.cos(d) * yDistance) + centerLoc.getY();
        if (d + outerSpeed >= Math.PI * 2 || d + innerSpeed <= -(Math.PI * 2)){
            d = 0;
            outerSpeed = (outerSpeed * Math.random() * 0.7) + (outerSpeed/2);
        }
        d += outerSpeed;
    }

    /**
        The draw method draws the Spotlight on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the Spotlight
    **/
    public void draw(Graphics2D g2d)
    {
        if (isFlashing){
            size = size * 4;
            Ellipse2D.Double c = new Ellipse2D.Double(loc.getX() - (size/2),loc.getY() - (size/2), size,size);
            g2d.setColor(myColor);
            g2d.fill(c);
            size = size/4;
        }else{
            Ellipse2D.Double c = new Ellipse2D.Double(loc.getX() - (size/2), loc.getY()- (size/2), size,size);
            g2d.setColor(myColor);
            g2d.fill(c);
        }
        /*Ellipse2D.Double c1 = new Ellipse2D.Double(centerLoc.getX(), centerLoc.getY(), 100,100);
        g2d.setColor(Color.GRAY);
        g2d.fill(c1);
        Ellipse2D.Double c2 = new Ellipse2D.Double(nukeLock.getX(), nukeLock.getY(), 100,100);
        g2d.setColor(Color.PINK);
        g2d.fill(c2);*/
    }

    /**
        The makeNewColor method makes new colors for the Spotlight.
        @param variation an integer that dictates a common color value
    **/
    public void makeNewColor(int variation)
    {
        Color c = GameColors.getWinningColor();
        myColor = ( new Color(
            randomizeValue(c.getRed(), 0,255, variation),
            randomizeValue(c.getGreen(),0,255, variation),
            randomizeValue(c.getBlue(), 0,255,variation),
            randomizeValue(55, 40,70,25)
        ));
    }

    /**
        The brighten method brightens the Spotlight.
        @param increase the amount to increase the brightness by
    **/
    public boolean brighten(int increase)
    {
        if (
            myColor.getRed() + increase > 255 ||
            myColor.getGreen() + increase > 255 ||
            myColor.getBlue() + increase > 255 
        ){
            //isFlashing = false;
            return false;
        }
        myColor = new Color(
            myColor.getRed() + increase,
            myColor.getGreen() + increase,
            myColor.getBlue() + increase,
            myColor.getAlpha()
        );
        isFlashing = true;
        return true;
    }

    /**
        The changeColor method changes the Spotlight's color.
        @param color the new color
    **/
    public void changeColor(Color color)
    {
        myColor = color;
    }

    /**
        The getColor method returns the Spotlight's color.
        @return the Spotlight's color
    **/
    public Color getColor()
    {
        return myColor;
    }

    /**
        The setIsFlashing method makes the Spotlight flash.
        @param b true if the Spotlight is flashing, false otherwise
    **/
    public void setIsFlashing(boolean b)
    {
        isFlashing = b;
    }
}
