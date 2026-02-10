/**
	The GameStarter class contains the main method that starts the game from the Player's side.
    It does this by instantiating a GameFrame object and calling the setUpGUI() method.
	
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
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

public class GameStarter
{
  public static void main(String[] args)
  {
    final Socket clientSocket; // socket used by client to send and recieve data from server
    final Scanner sc = new Scanner(System.in); // object to read data from user's keybord
    try {

        clientSocket = new Socket("127.0.0.1",4444);

        GameCanvas s = new GameCanvas(new Dimension(1200, 800), clientSocket);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Devil's Disco");
        frame.add(s);
        frame.pack();
        frame.setVisible(true);

    }catch(IOException e){
        e.printStackTrace();
    }
  }
}