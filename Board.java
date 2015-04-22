//Concurrent Modification Exception info/possible fixes:
//CopyOnWriteArrayList
//ConcurrentHashMap
//Create a clone, iterate over clone while only modifying original
//http://www.journaldev.com/378/how-to-avoid-concurrentmodificationexception-when-using-an-iterator
//http://www.noppanit.com/how-to-deal-with-java-util-concurrentmodificationexception-with-arraylist/
//http://www.coderanch.com/t/233932/threads/java/deal-Concurrent-Modification-Exception

/**Issues:

 * EnemyBullets disappear if the enemy dies **THIS IS A FEATURE**
   
   To do:
   
 * Boss: drawing and behaviour and new attacks
 * Collision between enemies and the player should result in the enemy 
       being destroyed and player losing hp
 * Player Bullet, Enemy Bullet, up, down extend Bullet. Bullet is abstract
 * Sound
 * Main Menu
    
**/
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;

public class Board extends JPanel implements Runnable, Commons
{
    Image background;
    String bg = "Images/Background.png";
    
    //private Dimension d;
    
    private Player player;
    private EnemyBullet enemybullet;
    
    boolean isRunning = true;  //program as a whole is running
    boolean inGame = true;    //The game specifically is running (Start button has been pressed)
    
    int score;
    
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private ArrayList<Sprite> enemies = new ArrayList<Sprite>();
    
    //Creates timers
    public static Timer asteroidTimer;  
    int asteroidTime;
    public static Timer enemyOneTimer;
    int enemyOneTime;
    public static Timer enemyTwoTimer;
    int enemyTwoTime;
    public static Timer bossTimer;
    int bossTime;
    
    private Thread animator;
    
    public Board()
    {
        //Here window is set up, opens at Menu Screen
        addKeyListener(new TAdapter()); // Starts key listener
        setFocusable(true); // Focuses keyboard and mouse to app window.
        
        //Once Start has been pushed, game initializes:
        //if(inGame)
        
        gameInitialize();
        setDoubleBuffered(true);
    } // end Board constructor
    
    public void gameInitialize()
    {
        ImageIcon ii = new ImageIcon(bg);
        background = ii.getImage(); // sets background image
        
        player = new Player();
        spriteCreator();
        
        score = 0; // Initializes Score
        
        if (animator == null || !inGame) //Thread is not made or game has not started
        {
            animator = new Thread(this); // Creates a new thead for the game
            animator.start();
        } // end if
    } // end game Initialize()
    
    public void drawPlayer(Graphics g) {
        if (player.isVisible()) // Draws the player as long as it is visible
        {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        } // end if

        if (player.isDying()) // Used for dying animation
        {
            player.die();
            inGame = false;
        } // end if
    } // end drawPlayer
    
    public void drawBullet(Graphics g)
    {
        // Copies bullet array list to prevent any "concurrent modification" errors.
        ArrayList bullets = player.getBullets();
        for ( int i = 0 ; i < bullets.size(); i++ )
        {
            Bullet b = (Bullet) bullets.get(i);
            g.drawImage(b.getImage(), b.getX(), b.getY(), this);
        } // end for        
    } // end drawBullet
    
    public void drawAsteroid(Graphics g)
    {
        // Copies bullet array list to prevent any "concurrent modification" errors.
        ArrayList<Asteroid> asteroids = getAsteroids();
        for(int i = 0; i < asteroids.size(); i++)
        {
            Asteroid a = (Asteroid)asteroids.get(i);
            if(a.isVisible())
            {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            } // end if
        } //end if
    }
    
    public ArrayList getAsteroids()
    {
        return asteroids;
    } // end method getAsteroids
    
    public void drawEnemies(Graphics g)
    {
        // Copies bullet array list to prevent any "concurrent modification" errors.
        ArrayList<Sprite> enemies = getEnemies();
        for(int i = 0; i < enemies.size(); i++)
        {
            Sprite e = (Sprite) enemies.get(i);
            g.drawImage(e.getImage(), e.getX(), e.getY(), this);
        } // end for
    } // end mothod drawEnemies
        
    public ArrayList getEnemies()
    {
        return enemies;
    }
    
    public void drawEnemyBullets(Graphics g)
    {
        ArrayList<Sprite> enemies = getEnemies();
        for(Sprite e : enemies)
        {
            // Copies bullet array list to prevent any "concurrent modification" errors.
            ArrayList<Sprite> enemybullets = e.getEnemyBullets();
            for(int i = 0; i < enemybullets.size(); i++)
            {
                Sprite eb = (Sprite) enemybullets.get(i);
                g.drawImage(eb.getImage(), eb.getX(), eb.getY(), this);
            } //end for
        }// end for
    } // end method drawEnemyBullets
    
    public void drawPlayerInfo(Graphics g)
    {
        // Draws player HP and Score
        Font font = new Font("Verdana", Font.PLAIN, 15); // creates a new font object
        FontMetrics metr = this.getFontMetrics(font); // ges specific info on the font
        
        g.setColor ( Color.WHITE );
        g.setFont(font);
        g.drawString ( "HP: " + Integer.toString(player.getHP()) , 25, 25 );
        g.drawString ( "SCORE: " + Integer.toString(score) , 25, 50 );
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g; // creates new graphics2d object
        g2d.drawImage(background, 0, 0, null ); // draws the background
        
        if (inGame)
        {
            drawPlayer(g);
            drawPlayerInfo(g);
            drawBullet(g);
            drawAsteroid(g);
            drawEnemies(g);
            drawEnemyBullets(g);
        }
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    } // end method paint
    
    public void gameOver() //Stops running, animation cycle, and displays game over message
    {
        Graphics g = this.getGraphics();
        inGame = false;
        
        String over = "GAME OVER";
        
        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);
        
        g.setColor ( Color.RED );
        g.setFont(font);
        g.drawString ( over ,
                     (Commons.BOARD_WIDTH - metr.stringWidth ( over )) / 2,
                     Commons.BOARD_HEIGHT / 2);
    }
    
    public void animationCycle()//handles all actions between objects
    {
        ArrayList<Bullet> bullets = player.getBullets();
        ArrayList<Asteroid> asteroids = getAsteroids();
        ArrayList<Sprite> enemies = getEnemies();
        
        // Player
        player.act();
        if(player.getHP( ) <= 0)
        {
            player.die();
            gameOver();
        }
        
        // Bullet        
        for ( int i = 0 ; i < bullets.size(); i++ )
        {
            Bullet b = (Bullet) bullets.get(i);
            //bullet moves off screen, dies
            if(b.getX() > Commons.BOARD_WIDTH)
            {
                b.die();
            }
            //handles bullet collision with enemies and asteroids
            if ( b.isVisible() == true )
            {
               b.act();
               for(Sprite e: enemies)
               {
                   if(b.getX() >= e.getX() && 
                      b.getX() <= (e.getX()+ENEMY1_WIDTH) && 
                      b.getY() >= e.getY() && 
                      b.getY() <= (e.getY()+ENEMY1_HEIGHT))
                   {
                       e.setHP(e.getHP()-10);
                       b.die();
                       //bullets.remove(i);
                   }
               }
               for(Asteroid a: asteroids)
               {
                   if(b.getX() >= a.getX() && 
                      b.getX() <= (a.getX()+ASTEROID_WIDTH) && 
                      b.getY() >= a.getY() && 
                      b.getY() <= (a.getY()+ASTEROID_HEIGHT))
                   {
                       a.setHP(a.getHP()-10);
                       b.die();
                       //bullets.remove(i);
                   }
               }
            }
            else
            {
                bullets.remove(i);
                i--;
            }
        }


        // Asteroid
        for(int i = 0; i < asteroids.size(); i++)
        {
            Asteroid a = (Asteroid) asteroids.get(i);
            a.act();
            //checks if asteroid health is below 0
            if(a.isVisible() && a.getHP() <= 0 )
            {
                 a.die();
                 score += 5;
            }
            //handles player collision with asteroids
            if(a.isVisible() &&
              (player.getX()) >= (a.getX()-50) && 
              (player.getX()) <= (a.getX()+100) &&
              (player.getY()) >= (a.getY()-50) &&
              (player.getY()) <= (a.getY()+100) )
            {
                a.die();
                player.setHP(player.getHP() - 10 );
            } 
            if(!a.isVisible())
            {
                asteroids.remove(i);
            }
        }
        
        //Enemies
        
        for(int i = 0; i < enemies.size(); i++)
        {
            Sprite e = (Sprite) enemies.get(i);
            ArrayList<Sprite> enemybullets = e.getEnemyBullets();
            
            e.act();
            
            //controls rate at which each Enemy1 shoots
            e.ectr++;
            //Causes all instances of Enemy1 to shoot
            if((e instanceof Enemy1) && (e.ectr >= 90))
            {
                e.shoot();
                e.ectr = 0;
            }
            //causes all instances of Enemy2 to shoot
            if((e instanceof Enemy2) && (e.ectr >= 90))
            {
                e.shoot();
                e.ectr = 0;
            }
            //handles collision between Enemy Bullets and the Player
            for ( int j = 0; j < enemybullets.size(); j++ )
            {
                Sprite b = (Sprite) enemybullets.get(j);
                b.act();
                if(b.getX() >= player.getX() && 
                   b.getX() <= (player.getX()+PLAYER_WIDTH) && 
                   b.getY() >= player.getY() && 
                   b.getY() <= (player.getY()+PLAYER_HEIGHT))
                {
                    player.setHP(player.getHP()-5);
                    b.die();
                    enemybullets.remove(j);
                }
            }
            //handles death of enemies as a result of being shot
            //removes enemy from enemies array and increases score
            if(e.getHP() <= 0)
            {
                 e.die();
                 enemies.remove(i);
                 i--;
                 score += 10;
            }
        } 
    } 
    
    //SpriteCreator is responsible for creating and setting Timers for the Asteroids and Enemies
    public void spriteCreator()
    {
        //randomly sets time for spawning of Objects
        long asteroidTime = 2000 + (int)(Math.random() * 4000);
        long enemyOneTime = 3000 + (int)(Math.random() * 5000);
        long enemyTwoTime = 3000 + (int)(Math.random() * 5000);
        
        //Creates timers and sets them to repeat.
        asteroidTimer = new Timer((int)asteroidTime, asteroidPerformer);
        asteroidTimer.setRepeats(true);
        asteroidTimer.start();
        
        //Enemy Timers have initial delays so they don't start immediately
        enemyOneTimer = new Timer((int)enemyOneTime, enemyOnePerformer);
        enemyOneTimer.setInitialDelay(12000);
        enemyOneTimer.setRepeats(true);
        enemyOneTimer.start();
        
        enemyTwoTimer = new Timer((int)enemyTwoTime, enemyTwoPerformer);
        enemyTwoTimer.setInitialDelay(30000);
        enemyTwoTimer.setRepeats(true);
        enemyTwoTimer.start();
        
        //Boss was not implemented, but this would have began his timer
        //bossTimer = new Timer(225000, bossPerformer);
        //bossTimer.setRepeats(false);
        //bossTimer.start();
    }
    
    //These ActionListeners tell the Timers what action is to be performed when the Timer reaches
    //the set time.
    //Enemies and Asteroids spawn along the right side of the screen. The position on the Y-axis is randomly 
    //genereated for each enemy and asteroid.
    
    public ActionListener asteroidPerformer = new ActionListener() {       
       public void actionPerformed(ActionEvent e)
       {
           ArrayList<Asteroid> asteroids = getAsteroids();
           int START_X = 800;
           int START_Y = 0 + (int)(Math.random()*750);
           Asteroid asteroid = new Asteroid(START_X, START_Y);
           asteroids.add(asteroid);
           asteroidTime = 2000 + (int)(Math.random() * 4000);
        }
    };
    
    public ActionListener enemyOnePerformer = new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
           ArrayList<Sprite> enemies = getEnemies();
           int START_X = 800;
           int START_Y = 0 + (int)(Math.random()*750);
           Enemy1 enemy1 = new Enemy1(START_X, START_Y);
           enemies.add(enemy1);
           enemyOneTime = 3000 + (int)(Math.random() * 5000);
        }
    };
    
    public ActionListener enemyTwoPerformer = new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
           ArrayList<Sprite> enemies = getEnemies();
           int START_X = 800;
           int START_Y = 0 + (int)(Math.random()*750);
           Enemy2 enemy2 = new Enemy2(START_X, START_Y);
           enemies.add(enemy2);
           enemyTwoTime = 3000 + (int)(Math.random() * 5000);
        }
    };
    
    //public ActionListener bossPerformer = new ActionListener(){
    //    public void actionPerformed(ActionEvent e)
    //    {
    //       boss = new Boss();
    //       enemies.add(boss);
    //    }
    //};
    
    //Using this site: http://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html
   
    //The run method is used every 2 milliseconds and handles the repainting and animation cycle.
    public void run()
    {
        long beforeTime, timeDiff, sleep;
        
        beforeTime = System.currentTimeMillis();

        while (inGame)
        {
            repaint();
            animationCycle();
            
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            
            if (sleep < 0) // Sleep for 2 milliseconds if there is no wait time
                sleep = 2;
            try {
                animator.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        } // end while
        gameOver();
    }
    
    //used for key inputs. Uses the key methods from the player class.
    private class TAdapter extends KeyAdapter
    {
        
        public void keyReleased(KeyEvent e)
        {
            player.keyReleased(e);
        }
        
        public void keyPressed(KeyEvent e)
        {
            System.out.println(e);
            player.keyPressed(e);
            
            int x = player.getX();
            int y = player.getY();
            
            //spacebar makes player shoot
            if (inGame)
            {
                if (e.getKeyCode() == 32 )
                {                 
                       player.shoot();
                }
            }
        }
    }
    
}