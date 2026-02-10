/**
	The GameCanvas class is responsible for drawing the entire game.
    It extends JComponent.
	
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
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


import javax.swing.*;
import javax.swing.Timer;

import AbstractClasses.RenderObject;

public class GameCanvas extends JComponent implements KeyListener
{
    private int myPlayerNum;
    private int otherPlayerNum;
    private Dimension dimension;
    private GameMaster gm;
    private boolean roundAnimationFinished = false;
    private Timer gameTimer;
    private Timer pauseTimer;
    private Timer roundTimer;
    private boolean skullIsDone = false;
    private Timer skullTimer;
    private int roundNumber = 1;
    private RoundAnimation roundAnimation;
    private String startingDanceFloor;

    private BufferedReader in; // object to read data from socket
    private PrintWriter out; // object to write data into socket

    /**
        The constructor has one parameter.
        @param dimension the dimensions of the canvas
        @param clientSocket the socket of the user
    **/
    public GameCanvas(Dimension dimension, Socket clientSocket)
    {
        try{
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            myPlayerNum = Integer.parseInt(in.readLine());
            startingDanceFloor = in.readLine();

            if (myPlayerNum == 0){
                otherPlayerNum = 1;
            }else{
                otherPlayerNum = 0;
            }
            System.out.println("Player Number  " + myPlayerNum);
            gm = new GameMaster(2, dimension, out);
            doCommand(startingDanceFloor);

            roundAnimation = new RoundAnimation(1);


        }catch(IOException e){
            e.printStackTrace();
        }
        Thread receive= new Thread(new Runnable() {
            String msg ;
            @Override
            public void run() {
                try {
                    while (true){
                        msg = in.readLine();
                        if (msg != null){
                            System.out.println(msg);
                            doCommand(msg);
                        }else{
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receive.start();

        this.dimension = dimension;
        setFocusable(true);
        setPreferredSize(dimension);
        

        addKeyListener(this);
        ActionListener al = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource().equals(gameTimer)){
                    gm.update();
                    if (gm.getPlayerWon()){
                        gm.pause();
                        pauseTimer.start();
                    }
                    repaint();
                }
                if (e.getSource().equals(pauseTimer)){
                    gm.hardPause();
                    roundNumber++;
                    roundTimer.start();
                    if (gm.getGamePhase() == 3){
                        //roundAnimation.startAnimation(RoundAnimation.GAMEEND);
                    }else{
                        roundAnimation.startAnimation(roundNumber);
                    }
                    pauseTimer.stop();
                }
                if (e.getSource().equals(roundTimer)){
                    roundTimer.stop();
                    if (gm.getGamePhase() == 3){
                        //play KILL losing character animation
                        gm.startEvent(1);
                    } else if (gm.getGamePhase() == 2 && !skullIsDone){
                        gm.startEvent(0);
                        RenderObject.skullRound = true;
                        skullTimer.start();
                    }else{
                        gm.unpause();
                    }
                }
                if (e.getSource().equals(skullTimer)){
                    skullIsDone = true;
                    gm.unpause();
                    skullTimer.stop();
                }
            }
        };
        gameTimer = new Timer(20,al); //updates game every 20ms
        pauseTimer = new Timer(5000, al); //how long the game is paused before hard paused
        roundTimer = new Timer (4000,al); //how long game is hard paused before game is unpaused
        skullTimer = new Timer (5400,al); //how long game is hard paused (while skull animation starts) before game is unpaused

        gameTimer.start();
    }

    /**
        The paintComponent method allows the game interface to be drawn.
        @param g the Graphics object
    **/
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());

        if (gm != null){
            gm.draw(g2d);
            roundAnimation.draw(g2d);
        }
    }

    /**
        The doCommand method executes commands on GameMaster given by the server.
        @param command the command message
    **/
    public void doCommand(String command)
    {
        String[] split = command.split(" ");
        if(split[0].equals("changeWinningColor")){
            gm.changeWinningColor(Integer.parseInt(split[1]));
        }else if (split[0].equals("moveReaper")){
            gm.moveReaper(Double.parseDouble(split[1]), Integer.parseInt(split[2]));
        }else{
            switch (command) {
                case "mvUp":
                    gm.moveObject(otherPlayerNum, GameMaster.mvUP);
                    break;
                case "mvLeft":
                    gm.moveObject(otherPlayerNum, GameMaster.mvLEFT);
                    break;
                case "mvDown":
                    gm.moveObject(otherPlayerNum, GameMaster.mvDOWN);
                    break;
                case "mvRight":
                    gm.moveObject(otherPlayerNum, GameMaster.mvRIGHT);
                    break;
                default:
                    int[] array = new int[split.length];
                    for (int i = 0; i < split.length; i++){
                        array[i] = Integer.parseInt(split[i]);
                    }
                    gm.changeDanceFloor(array);
                    break;
            }
        }
    }

    /**
        The keyTyped method is not used in this program.
        It is defined because the KeyListener interface is implemented by this class.
    **/
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
        The keyPressed method allows players to move their Player objects using keyboard controls.
        @param e the KeyEvent object
    **/
    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()){            
            case KeyEvent.VK_D:
                out.println("mvRight");
                out.flush();
                gm.moveObject(myPlayerNum, GameMaster.mvRIGHT);
                break;
            case KeyEvent.VK_A:
                out.println("mvLeft");
                out.flush();
                gm.moveObject(myPlayerNum, GameMaster.mvLEFT);
                break;
            case KeyEvent.VK_S:
                out.println("mvDown");
                out.flush();
                gm.moveObject(myPlayerNum, GameMaster.mvDOWN);
                break;
            case KeyEvent.VK_W:
                out.println("mvUp");
                out.flush();
                gm.moveObject(myPlayerNum, GameMaster.mvUP);
                break;
        }
    }

    /**
        The keyReleased method is not used in this program.
        It is defined because the KeyListener interface is implemented by this class.
    **/
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}