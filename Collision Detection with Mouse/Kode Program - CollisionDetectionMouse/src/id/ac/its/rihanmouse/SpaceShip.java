package id.ac.its.rihanmouse;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

   
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

        
        if (x < 1) {
            x = 1;
        }
       
        if (y < 1) {
            y = 1;
        }
        
        if(x>370) {
        	x=370;
        }
       
        if(y>270) {
        	y=270;
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 4));
    }
    
   
}
