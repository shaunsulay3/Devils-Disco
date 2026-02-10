/**
	The GameBackground class is responsible for drawing the background of the game interface.
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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import AbstractClasses.RenderObject;
import Background.BackgroundObject;

public class GameBackground extends RenderObject{

    private ArrayList<RenderObject> backgroundObjects = new ArrayList<RenderObject>();

    /**
        The constructor instantiates and initializes all required fields.
    **/
    public GameBackground()
    {
        System.out.println(screenDimension.getWidth());
        String[] djPaths = new String[]{
            "./GameImages/dj-1.png","./GameImages/dj-2.png","./GameImages/dj-3.png","./GameImages/dj-4.png"
        };
        String[] crowdPaths = new String[]{
            "./GameImages/crowd-1.png","./GameImages/crowd-2.png","./GameImages/crowd-3.png",
            "./GameImages/crowd-4.png","./GameImages/crowd-5.png","./GameImages/crowd-6.png"
        };
        double crowdRelWidth = 0.25;
        int i = 0;
        while (i * (crowdRelWidth * screenScaling) + (crowdRelWidth*screenScaling/2)< screenDimension.getWidth()){
            backgroundObjects.add(new BackgroundObject(crowdPaths, crowdRelWidth, crowdRelWidth, 
            new Point2D.Double(((crowdRelWidth*screenScaling)/2) + (i * (crowdRelWidth * screenScaling)), screenDimension.getHeight()*0.25), 13));
            i++;
        }
        backgroundObjects.add(new BackgroundObject(djPaths, 0.25,0.25, 
            new Point2D.Double(screenDimension.getWidth()/2,screenDimension.getHeight()*0.28), 8));
    }

    /**
        The draw method draws the GameBackground on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the GameBackground
    **/
    public void draw(Graphics2D g2d)
    {
        for (RenderObject bo: backgroundObjects){
            bo.draw(g2d);
        }
        addFramesElapsed(1);
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        for (RenderObject bo: backgroundObjects){
            bo.update();
        }
    }
}
