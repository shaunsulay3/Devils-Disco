/**
	The SideLight class is responsible for flashing a light at the side of the screen.
    It is flashed whenever a Player steps on the correct tile.
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
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import AbstractClasses.RenderObject;

public class SideLight extends RenderObject
{
    public static final int LEFT = 0;
    public static final int MIDDLE = 1;
    public static final int RIGHT = 2;

    private Point2D.Double gradientStart;
    private Point2D.Double gradientEnd;
    private int fadeSpeed = 4;

    private Color fadedTint;

    private boolean isFlashing = false;
    private Rectangle2D.Double rectangle;
    private int width = 400;

    /**
        The constructor has one parameter.
        @param side an integer that dictates either LEFT, RIGHT, or MIDDLE; dictates where the light will be flashed
    **/
    public SideLight(int side)
    {
        if (side == LEFT){
            rectangle = new Rectangle2D.Double(0,0,width,screenDimension.getHeight());
            gradientStart = new Point2D.Double(0, screenDimension.getHeight()/2);
            gradientEnd = new Point2D.Double(width, screenDimension.getHeight()/2);
        }else if(side == MIDDLE){
            rectangle = new Rectangle2D.Double(0,0,screenDimension.getWidth(),screenDimension.getHeight() * 0.37);
            gradientStart = new Point2D.Double(screenDimension.getWidth()/2,screenDimension.getHeight() * 0.37);
            gradientEnd = new Point2D.Double(screenDimension.getWidth()/2,0);
            fadeSpeed = 3;
        }else{
            rectangle = new Rectangle2D.Double(screenDimension.getWidth() - width, 0, width,screenDimension.getHeight());
            gradientStart = new Point2D.Double(screenDimension.getWidth(), screenDimension.getHeight()/2);
            gradientEnd = new Point2D.Double(screenDimension.getWidth() - width, screenDimension.getHeight()/2);
        }
        tint = Color.BLUE;
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        if (tint.getAlpha() - fadeSpeed >= 0){
            tint = new Color(
                tint.getRed(),
                tint.getGreen(),
                tint.getBlue(),
                tint.getAlpha() - fadeSpeed
            );
        }else{
            tint = new Color(0,0,0,0);
            isFlashing = false;
        }
    }

    /**
        The flash method is responsible for flashing the light.
        @param color the color of the light
    **/
    public void flash(Color color)
    {
        tint = color;
        fadedTint = new Color(tint.getRed(),tint.getGreen(),tint.getBlue(),0);
        isFlashing = true;
    }

    /**
        The draw method draws the light on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the light
    **/
    public void draw(Graphics2D g2d)
    {
        if (isFlashing){
            g2d.setPaint(new GradientPaint(
                gradientStart,
                tint,
                gradientEnd,
                fadedTint
            ));
            g2d.fill(rectangle); 
        }
    }

    /**
        The setFadeSpeed method handles how slowly the light fades out.
        @param i the speed at which the light fades out
    **/
    public void setFadeSpeed(int i)
    {
        fadeSpeed = i;
    }
}
