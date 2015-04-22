import java.awt.Image;
import java.util.ArrayList;

public class Sprite implements Commons
{
    private boolean visible;
    private Image image;
    protected int x;
    protected int y;
    protected int dy;
    protected boolean dying;
    protected int hp;
    protected int initialY;
    protected int ectr;
    protected ArrayList enemybullets;
    
    public Sprite()
    {
        visible = true;
    }
    
    public void act()
    {
       System.out.print("Act has not been overridden yet.");
    }
    
    public void shoot()
    {
        System.out.print("Shoot has not been overridden yet.");
    }
    
    public void die()
    {
        visible = false;
    }
    
    public boolean isVisible()
    {
        return visible;
    }
    
    protected void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public void setImage(Image image)
    {
        this.image = image;
    }
    
    public Image getImage()
    {
        return image;
    }
    
    public void setHP(int hp)
    {
        this.hp = hp;
    }
    
    public int getHP()
    {
        return hp;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setDying(boolean dying)
    {
        this.dying = dying;
    }
    
    public boolean isDying()
    {
        return this.dying;
    }
    
    public ArrayList getEnemyBullets()
    {
        return enemybullets;
    }
}