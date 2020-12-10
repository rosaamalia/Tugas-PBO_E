package id.ac.its.rihan165;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {
	//perpindahan
	private int dx;
    private int dy;
    //jumlah objek missile
    private List<Missile> missiles;
    
	public SpaceShip(int x, int y) {
		super(x, y);
		initCraft();
	}
	
	private void initCraft() {
        
        missiles = new ArrayList<>();
        loadImage("spaceship.png");
        getImageDimensions();
    }

    public void move() {

        x += dx;
        y += dy;
        
        //ketika koordinat udah dipaling kiri
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    //tombol keyboard
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }
    
    //missile
    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }
    
    //kalau tombol dilepas, perubahan=0
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

}
