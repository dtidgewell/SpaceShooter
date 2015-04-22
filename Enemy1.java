import javax.swing.ImageIcon;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.ArrayList;

public class Enemy1 extends Sprite implements Commons
{
    private final String enemy1 = "Images/EnemyOne.png";
    
    private ArrayList<EnemyBullet> enemybullets = new ArrayList<EnemyBullet>();
    
    public Enemy1(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(enemy1));
        setImage(ii.getImage());
        setX(x);
        setY(y);
        setHP(70);
        initialY = y;
        ectr = 0;
    }
    
    public void shoot()
    {
        EnemyBullet b = new EnemyBullet(getX()-25, getY());
        enemybullets.add(b);
    }
    
    public ArrayList getEnemyBullets()
    {
        return enemybullets;
    }
    
    public void act()
    {
        x -= 3;
        if ( x < 0 )
        {
            die();
        }
        //also make it change direction if it is on course to hit an asteroid
        //Add stuff here about when it shoots? Maybe once every 100 or 150 pixels
    }
    
}