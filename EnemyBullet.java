import javax.swing.ImageIcon;

public class EnemyBullet extends Sprite implements Commons
{
    private final String bullet = "Images/EnemyBullet.png";
    
    public EnemyBullet(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(bullet));
        setImage(ii.getImage());
        setX(x + 24);
        setY(y + 24);
    }
    //The old man the boat
    public void act()
    {
        x -= 6;
        if ( x < 0 )
        {
            die();
        }
    }
}
