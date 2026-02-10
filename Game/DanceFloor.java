/**
	The DanceFloor class is responsible for drawing the game's dance floor.
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import AbstractClasses.RenderObject;

public class DanceFloor extends RenderObject{
    private ArrayList<DanceTile> tiles;
    private int winningColorAmount = 2;

    /**
        The constructor has three parameters.
        @param numOfTiles the number of tiles in the dance floor
        @param relativeSize the relative size of the dance floor
        @param loc the location of the dance floor on the canvas
    **/
    public DanceFloor(int numOfTiles, double relativeSize, Point2D.Double loc)
    {
        tiles = new ArrayList<DanceTile>();
        double tileSize = relativeSize * screenScaling;
        double floorSize = tileSize * numOfTiles;

        for (int i = 0; i < numOfTiles; i++)
        { 
            for (int j = 0; j < numOfTiles; j++)
            {
                tiles.add(new DanceTile( new Point2D.Double(
                    (j * tileSize) + (loc.getX() - (floorSize/2)),
                    (i * tileSize) + (loc.getY() - (floorSize/2))
                    ), tileSize, (i * numOfTiles) + j, Color.WHITE));
            }
            //changeColors();
        }
    }

    /**
        The changeColors method is responsible for changing the colors of the dance floor.
        @param array the array sent by the server containing all color indexes of the dance floor
    **/
    public void changeColors(int[] array)
    {

        for (int i = 0; i < array.length - 1;i++){
            if (array[i] != 9){
                tiles.get(i).changeColor(GameColors.colors[array[i]]);

            }else{
                tiles.get(i).changeColor(Color.BLACK);
            }
        }
    }
    /*public void changeColors(){
        if (skullRound){
            winningColorAmount = 1;
        }
        int[] winningTiles = new int[winningColorAmount];
        
        for (int i = 0; i < winningColorAmount; i++){
                int randomTile;
            do{
                randomTile = (int) (Math.random() * (tiles.size()-1));
            } while (tiles.get(randomTile).isBurning());
            tiles.get(randomTile).changeColor(GameColors.getWinningColor());
            winningTiles[i] = randomTile;
        }
        for (int i = 0; i < tiles.size(); i++){
            boolean hasWinningTile = false;
            for (int j: winningTiles){
                if (j == i){
                    hasWinningTile = true;
                }
            }
            if (hasWinningTile){
                continue;
            }else{
                if (!skullRound){
                    tiles.get(i).changeColor(GameColors.getNoneWinningColor());
                }else{
                    tiles.get(i).changeColor(Color.BLACK);
                }

            }
        }
        

    }*/

    /**
        The draw method draws the dance floor on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the dance floor
    **/
    public void draw(Graphics2D g2d)
    {
        for (DanceTile dt: tiles)
        {
            dt.draw(g2d);
        }
    }

    /**
        The getNumOfTiles method returns the number of tiles on the dance floor.
        @return the number of tiles
    **/
    public int getNumOfTiles()
    {
        return tiles.size();
    }

    /**
        The getTile method returns a specific dance tile on the dance floor.
        @return the DanceTile object
    **/
    public DanceTile getTile(int index)
    {
        return tiles.get(index);
    }
}
