/**
	The GameServer class manages the game server's functionality.
    It contains the main method that instantiates and starts the server.
	
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.Timer;

public class GameServer
{
    private static PrintWriter[] out = new PrintWriter[2];
    private static boolean skullRound = false;
    private static int winningColor = (int) (Math.random() * 6);
    public static void main(String[] args)
    {
        final ServerSocket serverSocket ;
        final Socket[] clientSocket = new Socket[2];
        final BufferedReader[] in = new BufferedReader[2];
        final Scanner sc = new Scanner(System.in);
        int changeInterval = 3000;
        Timer changeColor;

        try {
            serverSocket = new ServerSocket(4444);

            clientSocket[0] = serverSocket.accept();
            out[0] = new PrintWriter(clientSocket[0].getOutputStream());
            in[0] = new BufferedReader (new InputStreamReader(clientSocket[0].getInputStream()));
            out[0].println(0);
            out[0].flush();
            System.out.println("Player 1 Connected");

            clientSocket[1] = serverSocket.accept();
            out[1] = new PrintWriter(clientSocket[1].getOutputStream());
            in[1] = new BufferedReader (new InputStreamReader(clientSocket[1].getInputStream()));
            out[1].println(1);
            out[1].flush();
            System.out.println("Player 2 Connected");

            String startingDanceFloor = getNewDanceFloor(false);
            out[0].println(startingDanceFloor);
            out[1].println(startingDanceFloor);
            out[0].flush();
            out[1].flush();


            Thread sender= new Thread(new Runnable() {
                String msg; //variable that will contains the data writter by the user
                @Override   // annotation to override the run method
                public void run() {

                }
            });
            Thread receive= new Thread(new Runnable() {
                String p1msg;
                String p2msg;
                @Override
                public void run() {
                    try {
                        while (true){

                            if (in[0].ready()){
                                p1msg = in[0].readLine();
                                doCommand(0,p1msg);
                            }

                            if (in[1].ready()){
                                p2msg = in[1].readLine();
                                doCommand(1,p2msg);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            ActionListener al = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Math.random() > 0.8){
                        doCommand(2, "changeWinningColor");
                    }
                    
                }
            };
            ActionListener al2 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Math.random() > 0.2){
                        doCommand(2, "moveReaper");
                    }
                    
                }
            };
            changeColor =  new Timer(changeInterval,al);
            Timer moveReaper = new Timer(1000,al2);
            sender.start();
            receive.start();
            changeColor.start();
            moveReaper.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
        The doCommand method gets the inputs from clients and dictates what to do next.
        @param fromPLayerNum the Player who sent the message to the server
        @param command the message of the Player
    **/
    private static void doCommand(int fromPlayerNum, String command)
    {
        switch (command) {
            case "mvUp":
            case "mvLeft":
            case "mvDown":
            case "mvRight":
                out[getOpposingPlayer(fromPlayerNum)].println(command);
                out[getOpposingPlayer(fromPlayerNum)].flush();
                break;
            case "changeDanceFloor":
                String newDanceFLoor = getNewDanceFloor(false);
                out[0].println(newDanceFLoor);
                out[0].flush();
                out[1].println(newDanceFLoor);
                out[1].flush();
                break;
            case "changeDanceFloorSkullRound":
                String newSkullDanceFLoor = getNewDanceFloor(true);
                skullRound = true;
                out[0].println(newSkullDanceFLoor);
                out[0].flush();
                out[1].println(newSkullDanceFLoor);
                out[1].flush();
                break;
            case "changeWinningColor":
                if (!skullRound){
                    winningColor = (int) (Math.random() * 6);
                    doCommand(2, "changeDanceFloor");
                }else{
                    winningColor = 0;
                }
                break;
            case "moveReaper":
                double d = Math.random();
                int direction;
                if (d < 0.25){
                    direction = 0;
                } else if (d < 0.5){
                    direction = 1;
                } else if (d < 0.75){
                    direction = 2;
                }else{
                    direction = 3;
                }
                String c = command + " " + Math.random() + " " + direction;
                out[0].println(c);
                out[0].flush();
                out[1].println(c);
                out[1].flush();
                break;
            default:
                System.out.println("COMMAND DOES NOT EXIST");
                break;
        }
    }

    /**
        The getOpposingPlayer method gets the opponent.
        @param i the assigned integer of the user
        @return the assigned integer of the opponent
    **/
    private static int getOpposingPlayer(int i)
    {
        if (i == 0){
            return 1;
        }
        return 0;
    }

    /**
        The getNewDanceFloor method changes the DanceFloor using an array.
        @param skullRound true if it is the round where the skull appears, false otherwise
        @return the new DanceFloor array
    **/
    private static String getNewDanceFloor(boolean skullRound)
    {
        String danceArray = "";
        int numDanceTiles = GameMaster.numOfTiles * GameMaster.numOfTiles;
        int winningTile1;
        int winningTile2;
        if (!skullRound){
            winningTile1 = (int) (Math.random() * numDanceTiles);
            do{
                winningTile2 = (int) (Math.random() * numDanceTiles);
            }while (winningTile2 == winningTile1);
        }else{
            do{
                winningTile1 = (int) (Math.random() * numDanceTiles);
            }while(!(
                winningTile1 >= GameMaster.numOfTiles &&
                winningTile1 < numDanceTiles - GameMaster.numOfTiles - 1 &&
                winningTile1 % GameMaster.numOfTiles != 0 &&
                (winningTile1 + 1) % GameMaster.numOfTiles != 0
            ));
            winningTile2 = winningTile1;
        }
        for (int i = 0; i < numDanceTiles; i++){
            if (i == winningTile1 || i == winningTile2){
                if (!skullRound){
                    danceArray += winningColor + " ";
                }else{
                    danceArray += "0 ";
                }
            }else{
                if (!skullRound){
                    int randomColor;
                    do{
                        randomColor = (int) (Math.random() * 6);
                    } while (randomColor == winningColor);
                    danceArray += randomColor + " ";
                }else{
                    danceArray += "9 ";
                }
            }
        }
        if (!skullRound){
            danceArray += winningColor + " ";
        }else{
            danceArray += "0 ";
        }
        return danceArray;  
    }
}
