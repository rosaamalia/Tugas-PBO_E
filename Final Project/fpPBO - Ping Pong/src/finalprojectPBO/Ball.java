package finalprojectPBO;

import java.awt.*;
import java.util.Random;

public class Ball {
    public int x,y,lebar = 40, panjang =40;
    public int motionX, motionY;
    public Random random;
    private Pong pong;
    private Pong id;
    public int amountOfHits;
    
    /**
     * contruktor untuk membuat bola saat
     * game mau dimulai
     *  pong permintaan pong membuat bola
     */
    public Ball(Pong pong){
        this.pong = pong;
        this.random = new Random();
        spawn();
    }
 
    public void update(Paddle paddle1, Paddle paddle2){
   
        int speed = 8;
        this.x += motionX * speed;
        this.y += motionY * speed;
 
        if(this.y + panjang  - motionY  > pong.panjang || this.y + motionY < 0){
            if (this.motionY <0){
                this.y = 0;
                this.motionY = random.nextInt(5);
 
                if(motionY == 0) motionY = 1;
 
            }
            else {
                this.motionY = -random.nextInt(5);
                this.y = pong.panjang - panjang;
                if (motionY == 0) motionY = -1;
                
            }
        }
        if(checkCollision(paddle1) == 1){
            this.motionX = 1 + (amountOfHits/5);
            this.motionY = -2 + random.nextInt(5);
            if(motionY == 0) motionY =1;
           
            amountOfHits++;
            
        }
        else if(checkCollision(paddle2) == 1){
            this.motionX = -1 - (amountOfHits/5);
            this.motionY = -2 + random.nextInt(5);
            if(motionY == 0) motionY = 1;
        
            amountOfHits++;
        }
 
        if (checkCollision(paddle1) == 2){
        	
            paddle2.score++;
            spawn();
        }
        else if (checkCollision(paddle2) == 2){
        	
            paddle1.score++;
            spawn();
        }
        
   
    }
 
    /**
     * method yang membuat bola dispawn saat memulai game
     * atau sehabis mencetak skor
     */
    public void spawn(){
        this.amountOfHits =0;
        this.x = pong.lebar/2 - this.lebar/2;
        this.y = pong.panjang/2 - this.panjang/2;
 
        this.motionY = -2 + random.nextInt(5);
 
        if(motionY==0) motionY =1;
        if(random.nextBoolean()) motionX =1;
        else motionX = -1;
    }
 
    public  int checkCollision(Paddle paddle){
        if (this.x < paddle.x + paddle.lebar && this.x + lebar > paddle.x
                && this.y < paddle.y + paddle.panjang && this.y + panjang > paddle.y) {
	        	Sound.PING.play();
	        	return 1;// memantul
        	} 
        else if ((paddle.x > x && paddle.paddleNumber ==1)
                || (paddle.x < x - lebar && paddle.paddleNumber ==2)) {
        	Sound.SCORE.play();
        	return 2; //skor
        }
        return  0;//nothing
    }
 
    /**
     *  Method untuk membuat tampilan bola
     */
    
   public void render(Graphics g) {
		switch(pong.warna) {
		default:
			 g.setColor(Color.YELLOW );
		     g.fillOval(x,y,lebar,panjang);
			break;
		case 1:
			 g.setColor(Color.WHITE );
		     g.fillOval(x,y,lebar,panjang);
			break;
		case 2:
			 g.setColor(Color.GREEN );
		     g.fillOval(x,y,lebar,panjang);
			break;
		}
     
}
}