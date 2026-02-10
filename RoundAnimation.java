/**
	The RoundAnimation class handles the animation for a round.
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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import AbstractClasses.RenderObject;

public class RoundAnimation extends RenderObject
{
    private boolean gameOver = false;
    private int fElapsed = 0;
    private int pauseFramesElapsed = 0;
    private boolean playAnimation = false;
    private int roundNumber;
    private double width = 500;
    private double height = width * 0.6;
    private int speed = 2;
    private Point2D.Double stop = new Point2D.Double(screenDimension.getWidth()/2, screenDimension.getHeight()/2);

    /**
        The constructor has one parameter.
        @param roundNumber the number of the round
    **/
    public RoundAnimation(int roundNumber)
    {
        this.roundNumber = roundNumber;
        loc = new Point2D.Double(-300, screenDimension.getHeight()/2);
    }

    /**
        The draw method draws the animation of the round.
        @param g2d the Graphics2D object which is responsible for drawing the round
    **/
    public void draw(Graphics2D g2d)
    {
        if (playAnimation){
            if (loc.getX() > screenDimension.getWidth() + width){
                playAnimation = false;
            }
            if (loc.getX() >= stop.getX() && pauseFramesElapsed < 100){
                pauseFramesElapsed++;
                fElapsed = 13;
            }else{
                loc = new Point2D.Double(loc.getX() + (speed * (fElapsed*0.4)), loc.getY());
            }
            Rectangle2D.Double r = new Rectangle2D.Double(loc.getX() - (width/2),loc.getY() - (height/2), width, height);
            g2d.setColor(new Color(0,0,0,120));
            g2d.fill(r);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 70)); 
            if (!gameOver){
                g2d.drawString("ROUND " + roundNumber, (int) (loc.getX() - (width/2.4)),(int) (loc.getY()));
            } else {
                g2d.drawString("GAME OVER", (int) (loc.getX() - (width/2.4)),(int) (loc.getY()));
            }
            fElapsed++;
        }else{
            loc = new Point2D.Double(-300, screenDimension.getHeight()/2);
            fElapsed = 0;
            pauseFramesElapsed = 0;
        }
    }

    /**
        The startAnimation method starts the round's animation.
        @param roundNumber the number of the round
    **/
    public void startAnimation(int roundNumber)
    {
        this.roundNumber = roundNumber;
        if (roundNumber == 0){
            gameOver = true;
        }
        playAnimation = true;
    }
}