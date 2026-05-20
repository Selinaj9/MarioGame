import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private int score;
    private boolean yellowColor;
    private int marioX;
    private int marioY;
    private int luigiX;
    private int luigiY;
    private double goombaX;
    private BufferedImage background;
    private BufferedImage mario;
    private BufferedImage luigi;
    private BufferedImage goomba;
    private BufferedImage coin;
    private boolean[] pressedKeys;
    private Timer timer;
    private boolean gameOver;
    private ArrayList<Point> coins;
    private int cointime;

    public DisplayPanel() {
        score = 0;
        coins = new ArrayList<>();
        yellowColor = true;
        gameOver = false;
        marioX = 50;
        marioY = 435;
        luigiX = 100;
        luigiY = 435;
        goombaX = -50;
        timer = new Timer(10,this);
        pressedKeys = new boolean[128];
        cointime = 0;
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
        try {
            goomba = ImageIO.read(new File("src/goomba.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            coin = ImageIO.read(new File("src/coin.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 32));
            if (score == 20) {
                g.drawString("GAME OVER, YOU WIN!", 350, 240);
            } else {
                g.drawString("GAME OVER, YOU LOSE :(", 350, 240);
            }
        } else {
            g.drawImage(mario, marioX, marioY, null);
            g.drawImage(luigi, luigiX, luigiY, null);
            g.drawImage(goomba, (int) goombaX, 470, null);
        }

        for (Point c : coins) {
            g.drawImage(coin, c.x, c.y, null);
        }

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
        if (e.getButton() == MouseEvent.BUTTON1) {
            yellowColor = !yellowColor;
            luigiX = (int) e.getPoint().getX();
            luigiY = (int) e.getPoint().getY();
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
        pressedKeys[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    private void moveMario() {
        if (pressedKeys[KeyEvent.VK_A]) {
            marioX -= 5;
            try {
                mario = ImageIO.read(new File("src/marioleft.png"));
            } catch (IOException error) { }
        }
        if (pressedKeys[KeyEvent.VK_D]) {
            marioX += 5;
            try {
                mario = ImageIO.read(new File("src/marioright.png"));
            } catch (IOException error) { }
        }
        if (pressedKeys[KeyEvent.VK_W]) {
            marioY -= 5;
        }
        if (pressedKeys[KeyEvent.VK_S]) {
            marioY += 5;
        }
        repaint();
    }

    private void moveLuigi() {
        if (pressedKeys[KeyEvent.VK_LEFT]) {
            luigiX -= 5;
            try {
                luigi = ImageIO.read(new File("src/luigileft.png"));
            } catch (IOException error) { }
        }
        if (pressedKeys[KeyEvent.VK_RIGHT]) {
            luigiX += 5;
            try {
                luigi = ImageIO.read(new File("src/luigiright.png"));
            } catch (IOException error) { }
        }
        if (pressedKeys[KeyEvent.VK_UP]) {
            luigiY -= 5;
        }
        if (pressedKeys[KeyEvent.VK_DOWN]) {
            luigiY += 5;
        }
    }

    private void moveGoomba() {
        goombaX += 0.5;
        if (goombaX > 1010) {
            goombaX = -50;
        }
    }

    private void createCoin() {
        int x = (int) (Math.random() * 960);
        int y = (int) (Math.random() * 470 + 10);
        Point point = new Point(x,y);
        coins.add(point);
        repaint();
    }

    private Rectangle marioRect() {
        int imgH = mario.getHeight();
        int imgW = mario.getWidth();
        Rectangle r = new Rectangle(marioX, marioY, imgW, imgH);
        return r;
    }

    private Rectangle luigiRect() {
        int imgH = luigi.getHeight();
        int imgW = luigi.getWidth();
        Rectangle r = new Rectangle(luigiX, luigiY, imgW, imgH);
        return r;
    }

    private Rectangle goombaRect() {
        int imgH = goomba.getHeight();
        int imgW = goomba.getWidth();
        Rectangle r = new Rectangle((int) goombaX, 470, imgW, imgH);
        return r;
    }

    private Rectangle coinRect(Point point) {
        int imgH = coin.getHeight();
        int imgW = coin.getWidth();
        Rectangle r = new Rectangle(point.x, point.y, imgW, imgH);
        return r;
    }

    private boolean checkForMarioGoombaCollision() {
        Rectangle marioR = marioRect();
        Rectangle luigiR = luigiRect();
        Rectangle goombaR = goombaRect();
        return marioR.intersects(goombaR) || luigiR.intersects(goombaR);
    }

    private void checkForMarioCoinCollision() {
        Rectangle marioR = marioRect();
        Rectangle luigiR = luigiRect();
        for (int i = 0; i < coins.size(); i++) {
            Rectangle coinR = coinRect(coins.get(i));
            if (marioR.intersects(coinR) || luigiR.intersects(coinR)) {
                score++;
                coins.remove(i);
                i--;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cointime++;
        moveMario();
        moveLuigi();
        moveGoomba();
        if (cointime == 50) {
            createCoin();
            cointime = 0;
        }
        checkForMarioCoinCollision();
        if (checkForMarioGoombaCollision() || score == 20) {
            gameOver = true;
            timer.stop();
        }
        repaint();
    }
}