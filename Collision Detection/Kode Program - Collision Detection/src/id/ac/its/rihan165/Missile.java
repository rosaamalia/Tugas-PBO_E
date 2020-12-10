package id.ac.its.rihan165;

public class Missile extends Sprite {
	
	private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 2;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("missiles.png");
        getImageDimensions();        
    }

    //missiles move in one direction only
    public void move() {
        
    	//koordinat x bertambah missile bergerak
        x += MISSILE_SPEED;
        
        if (x > BOARD_WIDTH)//disappear after reach right window border
            visible = false;
    }
}
