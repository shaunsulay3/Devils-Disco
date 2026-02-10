/**
	The GameForeground class is responsible for drwing the game's foreground.
    The game's foreground includes the spotlights that flash when the game is run.
    it extends the RenderObject class.
	
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
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import AbstractClasses.RenderObject;
import Game.Player;

public class GameForegroud extends RenderObject
{
    ArrayList<Spotlight> spotlights = new ArrayList<Spotlight>();
    private boolean spotlightsAreFlashing = false;
    private boolean holdFlash = false;
    private SideLight left = new SideLight(SideLight.LEFT);
    private SideLight right = new SideLight(SideLight.RIGHT);
    public SideLight middle = new SideLight(SideLight.MIDDLE);
    
    /**
        The constructor instantiates and initializes all required fields.
    **/
    public GameForegroud()
    {
        for (int i = 0; i < 11; i++){
            spotlights.add(new Spotlight(
                new Point2D.Double(screenDimension.getWidth()*0.45, screenDimension.getHeight()*0.57),
                Color.ORANGE,
                randomizeValue(120, 90, 210, 100),
                randomizeValue(120, 60, 170, 100),
                150,
                randomizeValue(0, -2, 2, 5.5)
            ));
        }

        /*for(Spotlight sl: spotlights){
            sl.changeTint(new Color(156,150,140,20), 120);
        }*/
        
    }

    /**
        The update method updates all the instance fields.
    **/
    public void update()
    {
        for(Spotlight sl: spotlights){
            sl.update();
        }
        if (spotlightsAreFlashing){
            flashLights(Flash.player);
        }
        if (holdFlash){
            int i = 0;
            if (Math.random() > 0.84){
                i = 1;
            }
            left.setFadeSpeed(i);
            right.setFadeSpeed(i);
            middle.setFadeSpeed(i);
        }
        left.update();
        right.update();
        middle.update();
    }

    /**
        The flashLights method is responsible for the light effects of the game.
        @param p the Player whose movement dictates when the lights flash
    **/
    public void flashLights(Player p)
    {
        flashSpotlights();
        if (p != null){
            if (p.getProgressBar().getLoc().getX() > screenDimension.getWidth()/2){
                right.flash(GameColors.getWinningColor());
            }else{
                left.flash(GameColors.getWinningColor());
            }
            middle.flash(GameColors.getWinningColor());
        }
    }

    /**
        The holdFlash method determines whether to hold the flash or not for lighting effects.
        @param b true if flash should be held, false otherwise
    **/
    public void holdFlash(boolean b)
    {
        holdFlash = b;
        if (!holdFlash){
            left.setFadeSpeed(4);
            right.setFadeSpeed(4);
            middle.setFadeSpeed(4);
            for (Spotlight sl: spotlights){
                sl.setIsFlashing(false);
            }
        }
    }

    /**
        The draw method draws the GameForeGround on the canvas.
        @param g2d the Graphics2D object which is responsible for drawing the GameForeground
    **/
    public void draw(Graphics2D g2d)
    {
        for(Spotlight sl: spotlights){
            sl.draw(g2d);
        }
        left.draw(g2d);
        right.draw(g2d);
    }

    /**
        The inner class Flash is responsible for the specific flashes for the game's lighting effects.
    **/
    class Flash
    {
        static Player player;
        static int fElapsed = 0;
        static int maxfElapsed = 100;
        static boolean bright;
        static int flashSpeed = 20;
        static ArrayList<Color> oldColors = new ArrayList<Color>();
    }

    /**
        The flashSpotlights method is responsible for flashing spotlights.
    **/
    private void flashSpotlights()
    {
        if (!spotlightsAreFlashing){
            for(Spotlight sl: spotlights){
                Flash.oldColors.add(sl.getColor());
            }
        }
        spotlightsAreFlashing = true;
        if (Flash.fElapsed == 0){
            boolean b = false;
            for(Spotlight sl: spotlights){
                if (sl.brighten(Flash.flashSpeed)){
                    b = true;
                }else if (!holdFlash){
                    sl.setIsFlashing(false);
                }
            }
            if (!b){
                Flash.fElapsed++;
            }
            
        }else{
            if (Flash.fElapsed > 2){
                if (holdFlash){
                    for (Spotlight g: spotlights){
                        if (g.getColor().getAlpha() - 1 >= 0 && Math.random() > 0.8){
                            Color c = g.getColor();
                            g.changeColor(new Color(
                                c.getRed(),
                                c.getGreen(),
                                c.getBlue(),
                                c.getAlpha() - 1
                            ));
                        }
                    }
                }else{
                    int i = 0;
                    for (Spotlight g: spotlights){
                        g.changeColor(Flash.oldColors.get(i));
                        i++;
                    }
                    Flash.fElapsed = 0;
                    spotlightsAreFlashing = false;
                    Flash.oldColors.clear();
                }
            }else{
                Flash.fElapsed++;
            }
        }
    }
}
