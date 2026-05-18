import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener {
    private int score;
    private boolean yellowColor;
    private int marioX;
    private int marioY;
    private int luigiX;
    private int luigiY;
    private BufferedImage background;
    private BufferedImage mario;
    private BufferedImage luigi;

    public DisplayPanel() {
        score = 0;
        yellowColor = true;
        marioX = 50;
        marioY = 435;
        luigiX = 100;
        luigiY = 435;
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            mario = ImageIO.read(new File("src/marioright.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            luigi = ImageIO.read(new File("src/luigiright.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(mario, marioX, marioY, null);
        g.drawImage(luigi, luigiX, luigiY, null);

        // set font and color of text
        g.setFont(new Font("Arial", Font.BOLD, 16));
        if (yellowColor) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawString("Score: " + score, 50, 30);
    }

    @Override
    public void mouseClicked(MouseEvent e) { } // unimplemented
    // unimplemented because if you move your mouse while clicking, this method isn't
    // called, so mouseReleased is best

    @Override
    public void mousePressed(MouseEvent e) { } // unimplemented

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            yellowColor = !yellowColor;
            marioX = (int) e.getPoint().getX();
            marioY = (int) e.getPoint().getY();
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { } // unimplemented

    @Override
    public void mouseExited(MouseEvent e) { } // unimplemented

    @Override
    public void keyTyped(KeyEvent e) { } // unimplemented

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {  // A key; VK_A equals 65
            marioX -= 5;
            try {
                mario = ImageIO.read(new File("src/marioleft.png"));
            } catch (IOException error) { }
        }
        if (keyCode == KeyEvent.VK_D) {  // D key; VK_D equals 65
            marioX += 5;
            try {
                mario = ImageIO.read(new File("src/marioright.png"));
            } catch (IOException error) { }
        }
        if (keyCode == KeyEvent.VK_W) {
            marioY -= 5;
        }
        if (keyCode == KeyEvent.VK_S) {
            marioY += 5;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            luigiX -= 5;
            try {
                luigi = ImageIO.read(new File("src/luigileft.png"));
            } catch (IOException error) { }
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            luigiX += 5;
            try {
                luigi = ImageIO.read(new File("src/luigiright.png"));
            } catch (IOException error) { }
        }
        if (keyCode == KeyEvent.VK_UP) {
            luigiY -= 5;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            luigiY += 5;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }  // unimplemented
}
