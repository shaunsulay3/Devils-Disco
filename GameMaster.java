/**
	The GameMaster class handles the game's Players and DanceFloor.
    It handles all game rules and essentials.
    It implements KeyListener.
	
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import java.io.PrintWriter;
import java.util.ArrayList;


import AbstractClasses.*;
import Background.BackgroundObject;
import Game.*;;

public class GameMaster extends RenderObject
{

    public static final int mvUP = 0;
    public static final int mvLEFT = 1;
    public static final int mvRIGHT = 2;
    public static final int mvDOWN = 3;


    private int myPlayerNum;
    private boolean skullActive = false;
    public static final int numOfTiles = 6;
    public static final int numOfReapers = 2;
    
    private int gamePhase = 1;
    private int framesElapsedPaused = 0;
    private int points = 1;
    private int tokensToWin = 3;
    private int colorChangeRandomize = 0;

    private boolean playerWon;
    private boolean paused = false;
    private boolean hardPaused = false;
    private boolean skullRoundDanceFloorChanged = false;

    private GameBackground gb;
    private DiscoBall db;
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private Skull skull;
    private GameForegroud gameForegroud;
    private ProgressBar[] progressBars;
    private DanceFloor danceFloor;
    private PrintWriter out;

    /**
        The constructor has two parameters.
        @param numOfPlayers the number of Players who will play the game
        @param dimension the dimensions of the game
        @param out the command sent to the server
    **/
    public GameMaster(int numOfPlayers, Dimension dimension, PrintWriter out)
    {
        this.out = out;

        class SetStaticVariables extends RenderObject{
            public SetStaticVariables(Dimension d){
                setStaticScreenScaling(d);
                super.tileNum = numOfTiles;
            }
        }
        new SetStaticVariables(dimension);

        db = new DiscoBall(new Point2D.Double((dimension.getWidth()/2) - 100, 30), 200);
        gb = new GameBackground();
        gameForegroud = new GameForegroud();
        progressBars = new ProgressBar[numOfPlayers];
        danceFloor = new DanceFloor(
            numOfTiles, 0.075,
            new Point2D.Double(dimension.getWidth()/2, (dimension.getHeight()/2) + (dimension.getHeight()*0.15))
        );

        for (int i = 0; i < numOfPlayers; i++){
            gameObjects.add( new Player(danceFloor.getTile(i)));
            Player p = (Player) gameObjects.get(i);
            progressBars[i] = p.getProgressBar();
        }
        for (int i  = 0; i < progressBars.length; i++){
            double d;
            if(i%2 == 0){
                d = (i * 50) + 75;
            }else{
                d = screenDimension.getWidth() - ((i/2) * 50) - 75 - progressBars[i].getWidth();
            }
            progressBars[i].setLoc(new Point2D.Double(d, screenDimension.getHeight() * 0.9));
        }
        for (int i = 0; i < numOfReapers; i++){
            gameObjects.add(new Reaper(danceFloor.getTile(15 + (i * 2)), 150));
        }
        
    }

    /**
        The isOnMatchingColor method checks if a Player is on the correctly-colored tile.
        @param color the winning color
        @param tile the tile the Player is stepping on
    **/
    private boolean isOnMatchingColor(Color color, DanceTile tile)
    {
        if (tile.getTint().equals(color)){
            return true;
        }
        return false;
    }

    /**
        The draw method draws the Players and the DanceFloor on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the specific objects
    **/
    public void draw(Graphics2D g2d)
    {
        db.draw(g2d);
        gameForegroud.middle.draw(g2d);
        gb.draw(g2d);
        if (skullActive){
            skull.draw(g2d);
        }
        
        danceFloor.draw(g2d);
        
        // makes sure players on top are drawn first
        int objectDrawn = 0;
        for (int i = 0; i < numOfTiles; i++){
            if (objectDrawn >= gameObjects.size()){
                break;
            }
            for (GameObject g: gameObjects){
                int tileNum = g.getDanceTile().getTileNum();
                if (tileNum < (i * numOfTiles) + numOfTiles && tileNum >= i*numOfTiles){
                    g.draw(g2d);
                    objectDrawn++;
                }
            }
        }
        if (skullActive){
            skull.drawLaser(g2d);
        }
        gameForegroud.draw(g2d);
        for (ProgressBar pb: progressBars){
            pb.draw(g2d);
        }
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        playerWon = false;
        if (skullActive){
            skull.update();
        }

        gb.update();
        gameForegroud.update();

        if (paused){
            framesElapsedPaused++;
            for (RenderObject ro: gameObjects){
                if (ro.getClass() == Player.class){
                   Player p = (Player) ro;
                    p.setMovementSubdivisions(framesElapsedPaused*7);
                }
            }
        }
        if (!hardPaused){
            db.update();

            for(RenderObject g: gameObjects){
                g.update();
            }
            for (ProgressBar pb: progressBars){
                if (pb.hasPlayerWon()){
                    playerWon = true;
                    pb.earnToken();
                    if (pb.getTokens() >= tokensToWin){
                        gamePhase = 3;
                    } else if (pb.getTokens() >= tokensToWin - 1){
                        gamePhase = 2;
                    }
                }
                pb.update(-0.008);
            }
            
        }
        
    }

    /**
        The changeWinningColor method changes the game's winning color.
        @param index the index of the winning color
    **/
    public void changeWinningColor(int index)
    {
        GameColors.changeWinningColor(index);
    }

    /**
        The move method makes GameObjects move on the DanceFloor.
        @param go the GameObject to be moved
        @param direction the direction the GameObject will move in
    **/
    private void move(GameObject go, int direction)
    {
        if (go.getDanceTile().getTileNum() + direction >= 0 && go.getDanceTile().getTileNum() + direction < (numOfTiles*numOfTiles)){
            DanceTile dt = getTileIfExist(go.getDanceTile().getTileNum(), direction);
            if( dt == null){
            }else if (go.isDoneMoving()){
                if (dt.getGameObject() == null){
                    go.moveTo(dt);
                    if (isOnMatchingColor(GameColors.getWinningColor(), dt) && go.getClass() == Player.class && !paused){
                        Player player = (Player) go;
                        gameForegroud.flashLights(player);
                        
                        player.getProgressBar().update(points);
                        
                        if (!skullActive){
                            sendServer("changeDanceFloor");
                        }else{
                            sendServer("changeDanceFloorSkullRound");
                        }

                    }
                }else if (go.getClass() == Player.class && dt.getGameObject().getClass() != Player.class){
                    Player c = (Player) go;
                    c.hurt();
                }
            }
        }     
    }

    /**
        The pause method makes the game slow down.
    **/
    public void pause()
    {
        paused = true;
        
        gameForegroud.holdFlash(true);
    }

    /**
        The hardPause method makes the game stop.
    **/
    public void hardPause()
    {
        paused = true;
        hardPaused = true;
    }

    /**
        The unpause method makes the game continue.
    **/
    public void unpause()
    {
        paused = false;
        hardPaused = false;
        if (skullRound){
            sendServer("changeDanceFloorSkullRound");
        }
        gameForegroud.holdFlash(false);
        framesElapsedPaused = 0;
        for (RenderObject ro: gameObjects){
            if (ro.getClass() == Player.class){
                Player p = (Player) ro;
                p.setMovementSubdivisions(2);
            }
        }
        for (ProgressBar pb: progressBars){
            pb.reset();
        }
    }

    /**
        The startEvent method controls when the big skull comes in.
        @param i dictates which event occurs
    **/
    public void startEvent(int i)
    {
        if (i == 0){
            skullActive = true;
            skull = new Skull(danceFloor);
        } else if (i == 1){
            for (ProgressBar pb: progressBars){
                if (pb.getTokens() != tokensToWin){
                    skull.killPlayer(pb.getPlayer());
                }
            }
        }
    }

    /**
        The moveObject method moves an object in a certain direction.
        @param objectIndex the object in the array to be moved
        @param direction the direction to move the object in
    **/
    public void moveObject(int objectIndex, int direction)
    {
        switch (direction){
            case mvUP:
                move(gameObjects.get(objectIndex), -numOfTiles);
                break;
            case mvDOWN:
                move(gameObjects.get(objectIndex), numOfTiles);
                break;
            case mvLEFT:
                move(gameObjects.get(objectIndex), -1);
                break;
            case mvRIGHT:
                move(gameObjects.get(objectIndex), 1);  
                break;
        }
    }

    /**
        The getPlayerWon method returns the Player who won.
        @return the winning Player
    **/
    public boolean getPlayerWon()
    {
        return playerWon;
    }

    /**
        The getTileIfExist method checks if the direction the Players are moving in has a DanceTile
        @param oldPlayerTile the DanceTile the Player is on
        @param direction the direction the Player will move in
        @return the DanceTile
    **/
    private DanceTile getTileIfExist(int oldPlayerTile, int direction)
    {
        boolean b;
        if (direction == 1){
            b = ((oldPlayerTile+1)%numOfTiles != 0);
        } else if (direction == -1){
            b = ((oldPlayerTile)%numOfTiles != 0);
        } else if (direction == numOfTiles){
            b = (oldPlayerTile + numOfTiles) < (numOfTiles*numOfTiles);
        } else if (direction == -numOfTiles){
            b = oldPlayerTile > numOfTiles-1;
        }else{
            b = false;
        }
        if (b){
            return danceFloor.getTile(oldPlayerTile + direction);
        }
        return null;

    }

    /**
        The getGamePhase method dictates which game phase the game is in.
        @return the integer signifying which phase the game is in
    **/
    public int getGamePhase()
    {
        return gamePhase;
    }

    /**
        The sendServer method sends a command to the server.
        @param command the message to be sent to the server
    **/
    private void sendServer(String command)
    {
        out.println(command);
        out.flush();
    }

    /**
        The changeDanceFloor method takes the DanceFloor array from the server and changes the DanceFloor.
        @param array the DanceFloor array from the server
    **/
    public void changeDanceFloor(int[] array)
    {
        if (!paused && !hardPaused){
            danceFloor.changeColors(array);
            GameColors.changeWinningColor(array[array.length-1]);
        }
    }

    /**
        The moveReaper method moves the Reapers across the DanceFloor.
        @param randomReaper the Reaper to be moved
        @param direction the direction the Reaper will move in
    **/
    public void moveReaper(double randomReaper, int direction)
    {
        if (!paused){
            ArrayList<Reaper> reapers = new ArrayList<Reaper>();
            for (GameObject g: gameObjects){
                if (g.getClass() == Reaper.class){
                    reapers.add((Reaper) g);
                }
            }
            Reaper reaper = reapers.get((int) (randomReaper * (reapers.size())));
            switch (direction) {
                case 0:
                    direction = numOfTiles;
                    break;
                case 1:
                    direction = -numOfTiles;
                    break;
                case 2:
                    direction = 1;
                    break;
                case 3:
                    direction = -1;
                    break;
            }
            move(reaper, direction);
        }
    }
}
