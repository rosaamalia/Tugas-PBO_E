package id.ac.its.rihan.spaceshipmouse;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
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

    public void move(MouseEvent e) {
    	dx = e.getX();
    	dy = e.getY();
        x = dx;
        y = dy;
        
        //handler ketika kursor berada di luar aplikasi
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

    public void buttonPressed(MouseEvent e) {

        int key = e.getButton();

        if (key == 1) {
            fire();
        }

    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }


}
