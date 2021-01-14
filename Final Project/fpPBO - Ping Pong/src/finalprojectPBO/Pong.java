package finalprojectPBO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Pong implements ActionListener, KeyListener{
 
    public static Pong pong;
    public int lebar =  1280, panjang = 720 ; //lebar-panjang panel
    public Renderer renderer;
    public Paddle player1, player2;
    public Ball ball;
    public boolean bot = false, selectingDifficulty;
    public boolean w, s, up, down;
    public int gameStatus = 0, scoreLimit = 3, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over, 4=credits ,5=option
    public int botDifficulty, botMoves, botCooldown = 0;
    public int warna = 1;
    public Random random;
    public JFrame jframe;
  
	private TimeCounter timeCounter;
	private Score writeScore;
	 
    /**
     * contructor
     */
    public Pong(){
        Timer timer = new Timer(20,this);
        random = new Random();
        jframe = new JFrame("PING PONG");
        renderer = new Renderer();
        writeScore = new Score();
 
        jframe.setSize(lebar + 15, panjang + 35);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(renderer);
        jframe.addKeyListener(this);
        
        timer.start();
    }
    /**
     * constructor saat mau mulai main
     */
    public void start(){
        gameStatus =2;
        player1 = new Paddle(this, 1);
        player2 = new Paddle(this, 2);
        ball = new Ball(this);
    }
 
 
    public void update(){
        //cek skor setiap pemain
        if (player1.score >= scoreLimit){
        	Sound.END.play();
            playerWon =1;
            gameStatus = 3;
        }
 
        if(player2.score >= scoreLimit){
        	Sound.END.play();
            playerWon = 2;
            gameStatus = 3;
        }
 
        //pergerakan paddle1
        if(w) player1.move(true);
        if(s) player1.move(false);
 
 
        if(!bot){
            //membaca pergerakan paddle2
            if(up) player2.move(true);
            if(down) player2.move(false);
 
        } else {
 
            if(botCooldown > 0){
                botCooldown--;
                if (botCooldown == 0) botMoves=0;
            }
 
            if(botMoves <10){
                if(player2.y + player2.panjang/2 < ball.y){
                    player2.move(false);
                    botMoves++;
                }
                if(player2.y + player2.panjang/2 > ball.y){
                    player2.move(true);
                    botMoves++;
                }
 
                if(botDifficulty == 0) botCooldown = 25;
                if(botDifficulty == 1) botCooldown = 15;
                if(botDifficulty == 2) botCooldown = 8;
            }
        }
        ball.update(player1,player2);
    }
 
    /**
     * method yang membuat UI
     * 
     */
    public void render(Graphics2D g){
 
        //untuk background
        g.setColor(Color.BLACK);
        g.fillRect(0,0, lebar, panjang);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
        //tampilan menu
        if(gameStatus == 0){
        	
        	timeCounter = new TimeCounter(0, 0, 0);
        	
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Arial", 1, 75));
            g.drawString("PING PONG",lebar/2 - 230, 200);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("____________________________", lebar/2 - 250, panjang/2 - 125);
 
            if(!selectingDifficulty){
            	 
                g.setColor(Color.PINK);
                g.setFont(new Font("Arial", 1, 30));
                g.drawString("TEKAN SPACE UNTUK BERMAIN/PAUSE", lebar/2 - 320, panjang/2 - 25);
                g.drawString("TEKAN SHIFT UNTUK BERMAIN DENGAN BOT", lebar/2 - 360, panjang/2 + 25);
                g.drawString("TEKAN C UNTUK Credits", lebar/2 - 200, panjang/2 + 75);
                g.drawString("TEKAN O UNTUK Option", lebar/2 - 200, panjang/2 + 125);
            }
            
        }
        
        if(selectingDifficulty){
        
            g.setColor(Color.ORANGE);
            String string = botDifficulty == 0 ? "MUDAH" : (botDifficulty == 1 ? "MEDIUM" : "SULIT");
 
            g.setFont(new Font("Arial", 1, 30));
            if(botDifficulty == 0 )
                g.drawString("<< BOT DIFFICULTY: " + string + " >>",lebar/2 - 257, panjang/2 - 25);
            else if(botDifficulty == 1)
                g.drawString("<< BOT DIFFICULTY: " + string + " >>",lebar/2 - 257, panjang/2 - 25);
            else if(botDifficulty == 2)
                g.drawString("<< BOT DIFFICULTY: " + string + " >>",lebar/2 - 257, panjang/2 - 25);
            g.drawString("PRESS SPACE TO PLAY/PAUSE", lebar/2 - 260 , panjang/2 + 25);
        }
 
        //membuat arena permainan
        if(gameStatus == 1 || gameStatus == 2){
        	
            g.setColor(Color.GREEN);
 
            g.setStroke(new BasicStroke(5f));
            g.drawLine(lebar/2, 0,lebar/2,panjang);
 
            g.setStroke(new BasicStroke(2f));
            g.drawOval(lebar/2 -125, panjang /2-120, 250,250);
 
            //untuk skor
            g.setFont(new Font("Arial", 1, 50));
            g.drawString(String.valueOf(player1.score), lebar/2 - 90, 100);
            g.drawString(String.valueOf(player2.score), lebar/2 + 65, 100);
            g.setFont(new Font("Arial", 1, 10));
            
            // penunjuk waktu
            String ts;
            ts = "Time: " + timeCounter.result();
            g.setFont(new Font("Arial", 1, 12));
            g.drawString(ts, lebar-150, 50);
            
            //High Score (waktu tercepat)
            String highScoreSebelumnya = writeScore.readFile("data/HighScore.txt");
            g.setFont(new Font("Arial", 1, 18));
            
            if(highScoreSebelumnya == null)
            {
            	highScoreSebelumnya = "0:0:0";
            	g.drawString("Best Time: " + highScoreSebelumnya, lebar-330, 50);
            } else {
            	g.drawString("Best Time: " + highScoreSebelumnya, lebar-330, 50);
            }

            player1.render(g);
            player2.render(g);
            ball.render(g);
        }
 
        // ketika game di pause
        if(gameStatus == 1){
        
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSE", lebar/2 - 89, panjang/2+20);
        }
 
        //maka ini saat menang
        if(gameStatus == 3){
        	timeCounter.stop();
        	
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Arial", 1, 75));
            g.drawString("PONG PING",lebar/2 - 230, 200);
       
            if(bot && playerWon == 2) {
            	g.drawString("BOT MENANG!", lebar/2-283, 300);
            }
            else {
            	g.drawString("PLAYER " + playerWon + " MENANG!", lebar/2-390, 330);
            }
            
            g.setColor(Color.CYAN);
            g.setFont(new Font("Arial", 1, 25));
        	String ts;
            ts = "Your Time: " + timeCounter.result();
            g.drawString(ts, lebar/2 - 300, panjang/2 + 50);
            
            g.drawString("TEKAN ESC UNTUK KEMBALI KE MAIN MENU", lebar/2 - 300, panjang/2 + 130);
            
            //membaca high score permainan sebelumnya
            String highScoreSebelumnya = writeScore.readFile("data/HighScore.txt");
            g.drawString("Best Time: " + highScoreSebelumnya, lebar/2+60, panjang/2+50);
            
            //jika tidak ada rekor waktu sebelumnya
            if(highScoreSebelumnya == null)
            {
            	writeScore.writeFile("data/HighScore.txt", timeCounter.result() + "\n");
            }
            
            //mendapatkan masing-masing satuan waktu (jam, menit, detik)
            String [] words = highScoreSebelumnya. split(":", 3);
            
            int jam = Integer.parseInt(words[0]);
            int menit = Integer.parseInt(words[1]);
            int detik = Integer.parseInt(words[2]);
            
            //membandingkan waktu dengan high score sebelumnya
            if(jam > timeCounter.hours)
            {
            	//simpan waktu bermain sebagai score
                writeScore.writeFile("data/HighScore.txt", timeCounter.result() + "\n");
            }
            else if (menit > timeCounter.min)
            {
            	//simpan waktu bermain sebagai score
                writeScore.writeFile("data/HighScore.txt", timeCounter.result() + "\n");
            }
            else if (detik > timeCounter.sec)
            {
            	//simpan waktu bermain sebagai score
                writeScore.writeFile("data/HighScore.txt", timeCounter.result() + "\n");
            }
        }
        
     // ketika credit
        if(gameStatus == 4){
        	
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString("CREDIT", lebar/2 - 89, panjang/3+20);
            g.drawString("Rosa Amalia - 05111940000106", lebar/4 -60, panjang/2 - 25);
            g.drawString("Rihan Farih Bunyamin - 05111940000165", lebar/4 - 60, panjang/2 + 25);
            g.drawString("Fadhil Dimas Sucahyo - 05111940000212", lebar/4 -60, panjang/2 + 70);
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("TEKAN ESC UNTUK KEMBALI KE MENU", lebar/ 3 , panjang/2 + 130);  
        }
        
     // ketika option
        if(gameStatus == 5){
        	
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 40));
            g.drawString("OPTION", lebar/2 - 89, panjang/3+20);
            
            String string = warna == 0 ? "KUNING" : (warna == 1 ? "PUTIH" : "HIJAU");
            
            g.setFont(new Font("Arial", 1, 30));
            if(warna == 0 )
                g.drawString("<< WARNA BOLA: " + string + " >>",lebar/2 - 219, panjang/2 - 25);
            else if(warna == 1)
                g.drawString("<< WARNA BOLA: " + string + " >>",lebar/2 - 219, panjang/2 - 25);
            else if(warna == 2)
                g.drawString("<< WARNA BOLA: " + string + " >>",lebar/2 - 219, panjang/2 - 25);
            
            g.drawString(" SCORE LIMIT: " + scoreLimit + "     (up/down) ",lebar/2-219, panjang/2 + 40);
            
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("TEKAN ESC UNTUK KEMBALI KE MENU", lebar/ 3 , panjang/2 + 130);   
        }
    }
 
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(gameStatus ==2) update();
        renderer.repaint();
    }
 
    public static void main(String [] args){
        pong = new Pong();
        
    }
 
    @Override
    public void keyPressed(KeyEvent e){
        int id = e.getKeyCode();
 
        if(id == KeyEvent.VK_W) w = true;
        else if(id == KeyEvent.VK_S) s = true;
        else if(id == KeyEvent.VK_UP) {
        	up = true;
        	if(gameStatus == 5) {
        		Sound.PING.play();
        		scoreLimit ++;
        	}
        }
        else if(id == KeyEvent.VK_DOWN) {
        	down = true;
        	if ( gameStatus == 5 && scoreLimit>1) {
        		Sound.PING.play();
        		scoreLimit--;
        	}
        }
        else if(id == KeyEvent.VK_RIGHT){
        	 Sound.PING.play();
            if(selectingDifficulty){
                if(botDifficulty < 2) botDifficulty++;
                else botDifficulty = 0;
               
 
            } else if(gameStatus == 5) {
            	 if(warna < 2) warna++;
                 else warna = 0;            }
        }
        else if(id == KeyEvent.VK_LEFT){
        	 Sound.PING.play();
            if (selectingDifficulty) {
                if(botDifficulty > 0) botDifficulty --;
                else  botDifficulty = 2;
              
            }
            else if ( gameStatus == 5 ) {
            	 if(warna > 0) warna --;
                 else  warna = 2;
            }
        }
        else if(id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3 || gameStatus==4 || gameStatus==5)) 
        {
        	gameStatus = 0;
        }
        else if (id == KeyEvent.VK_SHIFT && gameStatus == 0 ){
            bot = true;
            selectingDifficulty = true;
        }
        else if (id == KeyEvent.VK_SPACE){
            if(gameStatus == 0 || gameStatus == 3){
                if(!selectingDifficulty) bot = false;
                else  selectingDifficulty = false;
 
                start();
            }
            else if(gameStatus == 1) {
            	gameStatus =2;
            	 timeCounter.start();}
            
            else if(gameStatus == 2) {
            	gameStatus =1;
            	timeCounter.stop();
            }
        
        }
        else if(id == KeyEvent.VK_C
                && (gameStatus == 0 || gameStatus == 3)) gameStatus = 4;
        else if(id == KeyEvent.VK_O
                && (gameStatus == 0 || gameStatus == 3)) gameStatus = 5;
    }
 
    @Override
    public void keyReleased (KeyEvent e){
        int id = e.getKeyCode();
 
        if(id == KeyEvent.VK_W) w =false;
        else if (id == KeyEvent.VK_S) s = false;
        else if (id == KeyEvent.VK_UP) up = false;
        else if (id == KeyEvent.VK_DOWN) down = false;
    }
 
    @Override
    public void keyTyped (KeyEvent e){}
}