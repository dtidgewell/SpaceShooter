import javax.swing.JFrame;

public class Panel extends JFrame
{
    public Panel()
    {
        add(new Board());
        setTitle("Space Shooter");   //What happened to Space Sooter? Bring back Space Sooter 2014
        setSize(800, 600); // Sets height and width from commons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // sets window to middle of screen
        setResizable(false);
        setVisible(true);
    } // end method Initialize
    
    public static void main (String[] args)
    {
        new Panel();
    } // end method main
} // end class Panel