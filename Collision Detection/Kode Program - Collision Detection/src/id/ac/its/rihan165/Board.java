package id.ac.its.rihan165;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
	private Timer timer;
    private SpaceShip spaceship;
    private List<Alien> aliens;
    private boolean ingame;
    //posisi awal pesawat
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
    	//receiving keyboard event
        addKeyListener(new TAdapter());
        setFocusable(true); //supaya keyListener bisa dipakar
        setBackground(Color.BLACK);
        ingame = true; //menandain sedang dalam game

        //size board
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        //size spaceship
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initAliens();
        
        //delay waktu firing
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    //creates a list of alien objects
    public void initAliens() {
        
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }
    
    //ither draw game sprites or write the game over message. This depends on the ingame variable.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //ketika mulai game
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
        //cek di isVisible apakah sudah pernah di destroy
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }
        //draw how many aliens are left
        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        //koordinat tulisannya 5,15
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
            //spaceship bergerak
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
    
    //receives input key
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
}

