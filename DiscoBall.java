/**
	The DiscoBall class is responsible for drawing the game's disco ball.
    The DiscoBall's color dictates which DanceTile color the Players should step on.
	
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

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

import AbstractClasses.RenderObject;

public class DiscoBall extends RenderObject
{
    private double x,y, size;
    
    /**
        The constructor has two parameters.
        @param loc the x and y coordinates of the DiscoBall
        @param size the size of the DiscoBall
    **/
    public DiscoBall(Point2D.Double loc, int size)
    {
        this.x = loc.getX();
        this.y = loc.getY();
        this.size = size;
    }
    
    /**
        The draw method draws the DiscoBall on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the DiscoBall
    **/
    public void draw(Graphics2D g2d)
    {
        Ellipse2D.Double ball = new Ellipse2D.Double(x, y, size, size);
        g2d.setColor(GameColors.getWinningColor());
        g2d.fill(ball);

        Line2D.Double string = new Line2D.Double(x + (size/2), 0, x + (size/2), y);
        g2d.setColor(Color.WHITE);
        g2d.draw(string);

        for (double i = y+20; i < y+size; i += 20)
        {
            double x1 = ((40*x)/50);
            double x2 = ((260*x)/50);
            Line2D.Double horizontal = new Line2D.Double(x1, i, x2, i);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(horizontal);
        }

        double y1 = y+60;
        double y2 = y+120;
        for (double j = ((x+(size/2)-20)); j > x-40; j-= 20)
        {
            Path2D.Double vertical1 = new Path2D.Double();
            vertical1.moveTo((x+(size/2)), y);
            vertical1.curveTo(j, y1, j, y2, (x+(size/2)), y+size+10);
            g2d.draw(vertical1);
            y1 -= 10;
            y2 += 10;
        }

        double y3 = y+60;
        double y4 = y+120;
        for (double k = ((x+(size/2)-20)); k < x+size+40; k+= 20)
        {
            Path2D.Double vertical2 = new Path2D.Double();
            vertical2.moveTo((x+(size/2)), y);
            vertical2.curveTo(k, y3, k, y4, (x+(size/2)), y+size+10);
            g2d.draw(vertical2);
            y3 -= 10;
            y4 += 10;
        }
    }
}

