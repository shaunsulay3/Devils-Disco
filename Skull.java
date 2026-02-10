/**
	The Skull class is responsible for drawing the game's interface when a Player has won the entire game.
    It activates when a Player is one point away from winning.
    It extends the BackgroundObject class.
	
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import Background.BackgroundObject;
import Game.DanceFloor;
import Game.DanceTile;
import Game.Player;

public class Skull extends BackgroundObject {

    private int fElapsed = 0;
    private boolean killPlayer = false;
    private boolean hasArrived = false;
    private int laserWidth = 10;
    private boolean hasLasered = false;
    private double speed = 2;
    private double originalHeight;
    private double heightGrowthSpeed = 1;
    private Point2D.Double destination;
    private DanceFloor danceFloor;
    private Line2D.Double laser1;
    private Line2D.Double laser2;
    private int numDanceTiles;
    private Point2D.Double leftEye;
    private Point2D.Double rightEye;
    private int laserSpeed = 8;


    /**
        The constructor has one parameter.
        @param df the DanceFloor object of the game
    **/
    public Skull(DanceFloor df)
    {
        super(new String[]{"./GameImages/Skull_0.png"},0.4,0.45,new Point2D.Double(screenDimension.getWidth()/2,-screenDimension.getHeight()*0.5),3);
        destination = new Point2D.Double(screenDimension.getWidth()/2,screenDimension.getHeight() * 0);
        leftEye = new Point2D.Double(destination.getX() - (width * 0.25), destination.getY() + (height * 0.48));
        rightEye = new Point2D.Double(destination.getX() + (width * 0.25), destination.getY() + (height * 0.48));
        originalHeight = height;
        danceFloor = df;
        numDanceTiles =(int) Math.sqrt(danceFloor.getNumOfTiles());
        laser1 = new Line2D.Double(leftEye, danceFloor.getTile((int) (numDanceTiles - 1)).getPoint());
        laser2 = new Line2D.Double(rightEye, danceFloor.getTile((int) (numDanceTiles - 1)).getPoint());
    }

    /**
        The update method updates all the instance fields.
    **/
    @Override
    public void update()
    {
        if (loc.getY() + speed < destination.getY()) {
            loc.setLocation(loc.getX(), loc.getY() + speed);
        }else{
            loc.setLocation(destination);
            hasArrived = true;
        }
        if (hasArrived){
            if (!hasLasered){
                double plusX = 0;
                double plusY = 0;
                if (laser1.getX2() > danceFloor.getTile(numDanceTiles-1).getPoint().getX()){
                    hasLasered = true;
                } else if (laser1.getY2() < danceFloor.getTile(0).getPoint().getY()){
                    plusX = laserSpeed;
                    burnTile(true, 0, laser1.getP2());
                } else if(laser1.x2 <= danceFloor.getTile(danceFloor.getNumOfTiles() - numDanceTiles).getPoint().getX()){
                    burnTile(false, 0, laser1.getP2());
                    plusY = -laserSpeed;
                } else if (laser1.y2 >= danceFloor.getTile(danceFloor.getNumOfTiles()-1).getPoint().getY()){
                    burnTile(true, numDanceTiles-1, laser1.getP2());
                    plusX = -laserSpeed;
                } else {
                    burnTile(false, numDanceTiles-1, laser1.getP2());
                    plusY = laserSpeed;
                }
                laser1 = new Line2D.Double(leftEye, new Point2D.Double(laser1.getX2() + plusX,laser1.getY2()+plusY));
                laser2 = new Line2D.Double(rightEye, new Point2D.Double(laser2.getX2() + plusX,laser2.getY2()+plusY));

            }
            if (framesElapsed % 3 == 0){
                if (height > originalHeight * 1.02 || height < originalHeight){
                    heightGrowthSpeed = -heightGrowthSpeed;
                }
                height += heightGrowthSpeed;
            }
        }
        
        super.update();
    }

    /**
        The draw method draws the final game interface on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the final game interface
    **/
    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawImage(
            sprites[spriteIndex],
            (int) loc.getX() - (width/2),
            (int) loc.getY(), 
            width,
            height,
            null);
        if (killPlayer){
            drawLaser(g2d);
        }
    }

    /**
        The drawLaser method draws lasers on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the lasers
    **/
    public void drawLaser(Graphics2D g2d)
    {
        if (hasArrived && (!hasLasered || killPlayer)){
            g2d.setStroke(new BasicStroke(laserWidth,BasicStroke.JOIN_ROUND,BasicStroke.CAP_ROUND));
            g2d.setPaint(new GradientPaint(laser1.getP1(),Color.ORANGE,laser1.getP2(),Color.RED));
            g2d.draw(laser1);
            g2d.setPaint(new GradientPaint(laser2.getP1(),Color.ORANGE,laser2.getP2(),Color.RED));
            g2d.draw(laser2);
        }
    }

    /**
        The burnTile method makes the DanceTiles burn.
        @param row true if a row is burning, false otherwise
        @param rowCol the rows and columns of the DanceTiles to be burned
        @param point the x and y coordinates of the DanceTiles to be burned
    **/
    private void burnTile(boolean row, int rowCol, Point2D point)
    {
        if (!row){
            for (int i = 0; i < numDanceTiles; i++){
                DanceTile dt = danceFloor.getTile(rowCol + (i * numDanceTiles));
                if (dt.getPoint().getY() - 30 < point.getY() &&
                point.getY() < dt.getPoint().getY() + 30){
                    dt.burn();
                }
            }
        }else{
            for (int i = 0; i < numDanceTiles; i++){
                DanceTile dt = danceFloor.getTile((rowCol * numDanceTiles) + i);
                if (dt.getPoint().getX() - 30 < point.getX() &&
                    point.getX() < dt.getPoint().getX() + 30){
                    dt.burn();
                }
            }
        }
    }

    /**
        The hasLasered method checks if the lasers have been activated.
        @return true if lasers have been activated, false otherwise
    **/
    public boolean hasLasered()
    {
        return hasLasered;
    }

    /**
        The killPlayer method allows the losing Player to be killed.
        @param p the losing Player
    **/
    public void killPlayer(Player p)
    {
        Point2D.Double playerLoc = p.getLoc();
        laser1 = new Line2D.Double(leftEye, playerLoc);
        laser2 = new Line2D.Double(rightEye, playerLoc);
        killPlayer = true;
    }
}
