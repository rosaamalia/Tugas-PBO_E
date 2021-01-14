package finalprojectPBO;

import java.awt.*;

public class Paddle {
 
    public int paddleNumber, score;
    public int x,y,lebar =50, panjang= 250;
 
    /**
     * construktor paddle
     * @param pong
     * @param paddleNumber jumlah player
     */
    public Paddle(Pong pong, int paddleNumber){
        this.paddleNumber = paddleNumber;
        if(paddleNumber ==1){
            this.x =0;
        }
        if(paddleNumber ==2){
            this.x = pong.lebar -lebar;
        }
 
        this.y = pong.panjang/2 - this.panjang/2;
    }
 
    public void render(Graphics g){
    	if(paddleNumber==1) {
    		g.setColor(Color.RED );
            g.fillRect(x,y,lebar,panjang);
    	}
    	else {
        g.setColor(Color.BLUE );
        g.fillRect(x,y,lebar,panjang);
    	}
    }
 
    /**
     *  method yang mengatur pergerakan paddle
     * @param up kondisi tombol yang dipakai untuk bergerak tombol up == true,
     *           dan sebaliknya
     */
    public void move(Boolean up){
        int speed = 20;
 
        if(up){
            if (y-speed > 0){
                y-=speed;
            }
            else{
                y=0;
            }
        }
        else{
            if(y + panjang + speed < Pong.pong.panjang){
                y+= speed;
            }
            else {
                y= Pong.pong.panjang - panjang;
            }
        }
        }
}