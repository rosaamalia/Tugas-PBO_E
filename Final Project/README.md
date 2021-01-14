# Final Project - Ping Pong Game
Pada project ini kami membuat game Ping Pong menggunakan Java

Nama Kelompok:
1. Rihan Farih Bunyamin - 05111940000165
2. Fadhil Dimas Sucahyo - 05111940000212
3. Rosa Amalia - 05111940000106

## Class Diagram

## Penjelasan Class
**Pong.java** 

Sebagai class utama(main class)yang berfungsi mengendalikan objek yang telah dibuat

**Ball.java**
```
public int x,y,lebar = 40, panjang =40;
    public int motionX, motionY;
    public Random random;
    private Pong pong;
    private Pong id;
    public int amountOfHits;
 ```
berisi perintah untuk deklarasi . "lebar" dan "panjang" disini berfungsi untuk menentukan ukuran dari bola itu dimana pada program ini diberi ukuran 40.

```
public Ball(Pong pong){
        this.pong = pong;
        this.random = new Random();
        spawn();
    }
```

Konstruktor untuk membuat bola saat game mau dimulai , random adalah arah dari bola(akan dijelaskan selanjutnya), dan spawn merupakan munculnya bola tiap kali mencetak skor

```
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
```

berfungsi untuk apa yang terjadi pada bola ketika menyentuh paddle maupun dinding pembatas dari game, lebih rincinya akan dijelaskan dibawah ini:

```
  int speed = 8;
        this.x += motionX * speed;
        this.y += motionY * speed;
  ```      
 speed bertujuan untuk memberi kecepatan pada bola , sedangkan line selanjutnya berfungsi sebagai pergerakan dimana berpengaruh dalam pemantulan
 
 ```
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
```
berfungsi agar bola tidak keluar dari pembatas dari game


```
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
```
jika bola memantul akan diarahkan ke arah yang berlawanan ( misalnya bola datang dari atas maka akan dipantulkan ke arah bawah)

```
        if (checkCollision(paddle1) == 2){
        	
            paddle2.score++;
            spawn();
        }
        else if (checkCollision(paddle2) == 2){
        	
            paddle1.score++;
            spawn();
        }
    }
```

ketika setelah bola setelah dipantulkan dan bola tersebut "masuk" ke lawan maka skor akan ditambahkan serta bola di spawn kembali

```
public void spawn(){
        this.amountOfHits =0;
        this.x = pong.lebar/2 - this.lebar/2;
        this.y = pong.panjang/2 - this.panjang/2;
 
        this.motionY = -2 + random.nextInt(5);
 
        if(motionY==0) motionY =1;
        if(random.nextBoolean()) motionX =1;
        else motionX = -1;
    }
```
method yang membuat bola dispawn saat memulai game atau sehabis mencetak skor

```
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
```
Berfungsi untuk mengecek apakah bola terebut mencetak skor atau tidak, apabila tidak mencetak skor (mengenai paddle) maka ia akan memantul . Apabila ia memantul maupun mencetak skor ia akan mengeluarkan suara tertentu

```
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
```
Berfungsi untuk membuat tampilan bola serta warna bola tersebut, pilihan warna bola tergantung dari nilai "warna" yang terletak di class pong

**Paddle.java**

**Renderer.java**


**Score.java**

**Sound.java**
```
public class Sound {
	public static final AudioClip END = Applet.newAudioClip(Sound.class.getResource("end.wav"));
	public static final AudioClip PING = Applet.newAudioClip(Sound.class.getResource("ping.wav"));
	public static final AudioClip SCORE = Applet.newAudioClip(Sound.class.getResource("scoreup.wav"));

}
```
Berfungsi untuk Menambahkan Audio/Sound pada program. Terdapa 3 sound yang tersedia: END (pada saat game selesai) , PING ( pada saat bola mengenai paddle / pada saat mengatur warna maupun skor di option) dan SCORE (pada saat mencetak skor)

**TimeCounter.java**
```
public class TimeCounter implements ActionListener {

    private Timer clock;
    public int hours = 0;
    public int min = 0;
    public int sec = 0;

    public TimeCounter(int hours, int min, int sec) {
        this.hours = hours;
        this.min = min;
        this.sec = sec;

        clock = new Timer(1000, this);
        clock.start();
    }

    public String result()
    {
        return hours + ":" + min + ":" + sec;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        {
            if(e.getSource() == clock) {
                sec++;
            }
            if(sec == 60)
            {
                min++;
                sec = 0;
            }
            if(min == 60)
            {
                hours++;
                min = 0;
            }}}

    public void actionPerformed() {

    }

	public void stop() {
		clock.stop();
		// TODO Auto-generated method stub
		
	}

	public void start() {
		// TODO Auto-generated method stub
		clock.start();
	}

	
}
```
Sebagai Class yang berfungsi untuk menampiklan waktu saat bermain , Format penampilan adalah Jam:Menit: Detik dimana perhitungan waktu dimulai saat ```clock.start()``` dan berhenti saat fungsi ```stop()``` dipanggil.

## Fitur-Fitur Utama Pada Aplikasi

## Perubahan Yang Dilakukan dan Referensi

## Link Youtube Demo
