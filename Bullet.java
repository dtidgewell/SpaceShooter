import javax.swing.ImageIcon;

public class Bullet extends Sprite implements Commons
{
    private final String bullet = "Images/Bullet.png";
    
    public Bullet(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(bullet));
        setImage(ii.getImage());
        setX(x + 24);
        setY(y + 24);
    }
    
    //moves bullet across screen
    public void act()
    {
        x += 6;
        if ( x > BOARD_WIDTH )
        {
            die();
        }
    }
}
