import javax.swing.ImageIcon;

public class DownEnemyBullet extends Sprite implements Commons
{
    private final String downEnemyBullet = "Images/DownEnemyBullet.png";
   
    public DownEnemyBullet(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(downEnemyBullet));
        setImage(ii.getImage());
        setX(x + 24);
        setY(y + 24);
    }
    //The old man the boat
    public void act()
    {
        x -= 6;
        y += 3;
        if ( x < 0  || y > -5)
        {
            die();
        }
    }
}