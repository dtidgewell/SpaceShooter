import java.awt.event.KeyEvent;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons
{
    private final int START_X = (PLAYER_WIDTH/2);
    private final int START_Y = BOARD_HEIGHT/2;
    
    private final String player = "Images/Player.png";
    
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    //private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    //private ArrayList<Sprite> enemies = new ArrayList<Sprite>();
    
    
    public Player()
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(player)); 
        
        setImage(ii.getImage());
        
        setX(START_X);
        setY(START_Y);
        setHP(100);
    }
    
    //stops player form moving off screen 
    public void act()
    {
        y += dy;
        if (y <= 3)
        {
            y = 3;
        }   
        if (y >= 547)
        {
            y = 547;
        }
    }
    
    //creates new Bullet and adds to bullets array
    public void shoot()
    {
        Bullet b = new Bullet(getX() + 25 , getY() );
        bullets.add(b);
    }
    
    //used to access the bullets ArrayList
    public ArrayList getBullets()
    {
        return bullets;
    }  
    
    //pressing up or down makes the player move up or down
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        System.out.println(key);
        if (key == KeyEvent.VK_UP)
        {
            dy = -4;
        }
        
        if (key == KeyEvent.VK_DOWN)
        {
            dy = 4;
        }
    }
    
    //stops moving when keys are released
    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP)
        {
            dy = 0; 
        } 
        if (key == KeyEvent.VK_DOWN)
        {
            dy = 0;
        }
    }
}