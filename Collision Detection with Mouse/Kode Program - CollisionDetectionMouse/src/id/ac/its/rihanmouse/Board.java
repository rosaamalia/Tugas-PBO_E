package id.ac.its.rihanmouse;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt .Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
	private Timer timer;
    private SpaceShip spaceship;
    private List<Alien> aliens;
    private boolean ingame;
   
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    //board 
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;

    //initial positions of alien ships
    private final int[][] pos = {
        {2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239},
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
    };

    public Board() {

        initBoard();
    }

    private void initBoard() {
    	
        setBackground(Color.BLACK);
        ingame = true; 
        
        //mouse handler
        MouseHandler handler = new MouseHandler();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
		
		this.hideCursor();
		
        //size board
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        //size spaceship
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initAliens();
        
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    //hideCursor
    public void hideCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		this.setCursor(blankCursor);
	}
    
    //init alien
    public void initAliens() {
        
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }
    
    //draw game sprites or write the game over message. This depends on the ingame variable.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // start game
        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    //draws game sprites on the window.
    private void drawObjects(Graphics g) {

        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), 
                        missile.getY(), this);
            }
        }
        
        //draw all aliens, drawn only if they have not been previously destroyed
       
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }
        //draw how many aliens are left
        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        
    }
    
    //draws a game over message in the middle of window
    //displayed at the end of the game(all alien ships destroyed or collide w/alien)
    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    //1 action=1 game cycle
    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateShip();
        updateMissiles();
        updateAliens();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {

        if (spaceship.isVisible()) {
            //spaceship move
            spaceship.move();
        }
    }
    
    //moves available missiles
    private void updateMissiles() {

        List<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }
    
    //check if there are any alien left in aliens list, if empty then finished
    //if not empty, move all it's items. the destroyed aliens removed fr list
    private void updateAliens() {

        if (aliens.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {

            Alien a = aliens.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
            }
        }
    }

    //checks for possible collisions
    //check if the craft object collides w/ any alien objects
    public void checkCollisions() {

        Rectangle r3 = spaceship.getBounds();

        for (Alien alien : aliens) {
            
            Rectangle r2 = alien.getBounds();

            //check sp c alien
            if (r3.intersects(r2)) {
                
                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
                //game over
            }
        }
        
        //check missiles and alien collide
        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Alien alien : aliens) {

                Rectangle r2 = alien.getBounds();

                if (r1.intersects(r2)) {
                    
                    m.setVisible(false);
                    alien.setVisible(false);
                }
            }
        }
    }
 
 //internal class MouseHandler
 private class MouseHandler implements MouseListener, MouseMotionListener {

	@Override//moving follow cursor when mouse dragged
	public void mouseDragged(MouseEvent e) {
		spaceship.x = e.getX() ;
		spaceship.y = e.getY() ;
		
	}

	@Override //move follow cursor
	public void mouseMoved(MouseEvent e) {
		spaceship.x = e.getX() ;
		spaceship.y = e.getY() ;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override //when mouse pressed missile fire
	public void mousePressed(MouseEvent e) {
		spaceship.fire();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
}
    
    
}
