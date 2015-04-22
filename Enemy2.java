import javax.swing.ImageIcon;

import java.util.ArrayList;
public class Enemy2 extends Sprite implements Commons
{
    private final String enemy2 = "Images/EnemyTwo.png";
    
    private ArrayList<Sprite> enemybullets = new ArrayList<Sprite>();
    
    public Enemy2(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(enemy2));
        setImage(ii.getImage());
        setX(x);
        setY(y);
        setHP(70);
        initialY = y;
        ectr = 0;
    }
    
    //creates 3 bullets which move in different directions and adds to enemybullets ArrayList
    public void shoot()
    {
        EnemyBullet b = new EnemyBullet(getX()-30, getY()-10);
        UpEnemyBullet ub = new UpEnemyBullet(getX()-30, getY() - 10);
        DownEnemyBullet db = new DownEnemyBullet(getX()-30, getY() -10);
        enemybullets.add(b);
        enemybullets.add(ub);
        enemybullets.add(db);
    }
    
    //returns enemybullets array
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
        //Add something that makes it change direction(up/down) every certain number of pixels
        //also make it change direction if it is on course to hit an asteroid
        //Add shit here about when it shoots? Maybe once every 100 or 150 pixels
    }
}
