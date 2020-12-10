package id.ac.its.rihan165;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {
		//barang2 yang ada di window output
	 	//koordinat
		protected int x;
	    protected int y;
	    //gambar
	    protected int width;
	    protected int height;
	    protected boolean visible;
	    protected Image image;

	    //constructor
	    public Sprite(int x, int y) {

	        this.x = x;
	        this.y = y;
	        visible = true; //boolean visible agar terlihat
	    }

	    //image yg diupload
	    protected void getImageDimensions() {

	        width = image.getWidth(null);
	        height = image.getHeight(null);
	    }

	    protected void loadImage(String imageName) {

	        ImageIcon ii = new ImageIcon(imageName);
	        image = ii.getImage();
	    }

	    public Image getImage() {
	        return image;
	    }

	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public boolean isVisible() {
	        return visible;
	    }

	    public void setVisible(Boolean visible) {
	        this.visible = visible;
	    }
	    
	    //semua objek dianggep rect
	    public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }//return the bounding rectangle of the sprite image
}
