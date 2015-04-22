import javax.swing.ImageIcon;

public class Asteroid extends Sprite implements Commons
{
    private final String asteroid = "Images/Asteroid.png";
    
    public Asteroid(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(asteroid));
        setImage(ii.getImage());
        setX(x);
        setY(y);
        setHP(50);
    }
    
    public void act()
    {
        x -= 3;
        if ( x < -100 )
        {
            die();
        }
    }
    
    
}