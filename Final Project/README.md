# Final Project - Ping Pong Game
Pada project ini kami membuat game Ping Pong menggunakan Java

Nama Kelompok:
1. Rihan Farih Bunyamin - 05111940000165
2. Fadhil Dimas Sucahyo - 05111940000212
3. Rosa Amalia - 05111940000106

## Class Diagram
![Kelas Diagram](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Kelas%20Diagram/Diagram%20Kelas%20-%20FP%20PBO%20PING%20PONG.jpg)

## Penjelasan Class
**Pong.java** 

Sebagai class utama(main class)yang berfungsi mengendalikan objek yang telah dibuat
```
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
```
Code diatas berfungsi sebagai deklarasi variabel/objek yang ada. ` public int lebar , panjang` bertujuan untuk mengatur panjang dan lebar panel . `gameStatus` adalah kondisi status game pada aplikasi , dimana pada kondisi default berada pada kondisi 0 yaitu main menu.

Sedangkan `scoreLimit` adalah batas skor yang dibutuhkan untuk menang . selain itu terdapat pula kondisi-kondisi lain yang ditetapkan pada saat default seperti fungsi `warna`,`botCooldown` dan sebagainya.

```
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
```
Berfungsi Sebagai Contructor , `jframe = new JFrame` berfungsi untuk memberikan nama "PING PONG" pada panel diatas , lalu `renderer` yang berfungsi untuk merender dan line dibawahnya mengatur size dan visiblility pada panel

```
public void start(){
        gameStatus =2;
        player1 = new Paddle(this, 1);
        player2 = new Paddle(this, 2);
        ball = new Ball(this);
    }
```
pada saat `gamestatus=2` yaitu saat bermain, maka game akan menampilkan 2 player serta bolanya.

```
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
```
program akan mengecek melalui `update()` pada saat permainan apakah skor telah melebihi `scoreLimit` dimana apabila terlewati maka pemain tersebut akan menang (`gameStatus=3`)

```
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
```

Mengatur mengenai pergerakan paddle baik paddle 1 maupun 2 , disini terlihat bahwa melalui `botCooldown` maka pergerakan paddle yang digerakan komputer akan dapat diatur tingkat responsivitasnya tergantung kesulitannya (`botDifficulty`) 

```
public void render(Graphics2D g){
 
        //untuk background
        g.setColor(Color.BLACK);
        g.fillRect(0,0, lebar, panjang);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
```
Disini `render(Graphics2D g)` berfungsi untuk mengerender , dalam hal ini pada code diatas untuk background. Dimana warna background diatur warna hitam

```
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
```
Sedangkan untuk code diatas mengatur untuk tampilan menu utama yang ditunjukan pada `gamestatus==0`. Pada saat memasuki menu utama penghitung waktu `timeCounter` akan direset dari 0. Lalu program akan menampilkan judul aplikasi. 

Karena pada kondisi deafult `selectingDifficulty` bernilai false maka ia akan menampilkan opsi pilihan bermain, credits maupun option . Ketika kita memilih bermain dengan bot maka secara otomatis akan memicu `SelectingDifficulty` bernilai benar sehingga menampilkan pilihan tingkat kesulitan bot.

```
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
 
```
Method di atas berfungsi untuk membuat arena permainan. Selain paddle dan bola, arena permainan juga menunjukkan skor selama permainan dan waktu terbaik yang pernah dicapai selama permainan. Best time diambil dari `highScoreSebelumnya` yang berasal dari file HighScore.txt. Jika belum ada best time yang tercatat dari permainan sebelumnya, maka best time di arena permainan akan mulai dari `0:0:0`.

```
        if(gameStatus == 1){
        
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSE", lebar/2 - 89, panjang/2+20);
        }
```
Method di atas akan berjalan saat `gameStatus = 1` dan berfungsi untuk menjeda permainan.

```
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
```
Saat `gameStatus = 3`, limit score sudah terpenuhi dan akan menampilkan tampilan pemenang. Di method ini `timeCounter.stop()` akan berjalan dan `timeCounter` akan berhenti mencatat waktu permainan.

Waktu saat permainan akan dibandingkan dengan waktu yang pernah dicatat sebelumnya di file HighScore.txt. Waktu yang diambil dari file HighScore.txt di variabel `highScoreSebelumnya` displit dan dikonversi menjadi bertipe integer sehingga dapat dibandingkan dengan waktu saat permainan. Jika waktu saat permainan tadi ternyata lebih singkat dari best time waktu yang pernah tercatat, maka waktu tersebut akan dicatat sebagai rekor baru ke file HighScore.txt melalui method `writeScore.writeFile`.

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
```
public class Paddle {
 
    public int paddleNumber, score;
    public int x,y,lebar =50, panjang= 250;
 ```
 deklarasi variabel/objek dan mengatur ukuran dari paddle
 
 ```
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
```
Menginisiasi/constructor paddle, menentukan jumlah pemain (```paddleNumber```) dan posisi dari paddle (```this.x``` , ```this.y```)

```
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
```
Menrender dari paddle sekaligus memberikan warna pada tabble ( Merah dan Biru)

```
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
```
sebagai method yang mengatur pergerakan paddle
```if(up)``` berfungsi apabila kita menekan tombol up paddle akan naik keatas (pojok atas bernilai 0) dan apabila y=0 ia tidak bisa lagi keatas jika tidak menekan up maka yang akan jalan ialah else dibawah

**Renderer.java**
```
public class Renderer extends JPanel {
    private static  final long serialVersionUID = 1L;
 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Pong.pong.render((Graphics2D) g);
    }
}
```
Berfungsi untuk mengrender dan sebagai dasar untuk pembuatan panel

**Score.java**

Kelas ```Score``` berfungsi untuk memanajemen waktu terbaik sebagai high score di permainan.
```
public String readFile(String filePath)
	{
		File inputFile;
		BufferedReader inputReader;
		String fileText = "";
		
		try {
			inputFile = new File(filePath);
			
			inputReader = new BufferedReader(new FileReader(inputFile));
			fileText = inputReader.readLine();
			String line;
			
			inputReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileText;
	}
```
Fungsi ```readFile``` untuk membaca waktu terbaik yang sudah pernah tercatat di permainan sebelumnya di file ```HighScore.txt``` dan mengoper isi dari file (waktu terbaik yang pernah dicatat). Jika belum dan file kosong, maka nilai ```null``` yang akan dioper.

```
public void writeFile(String filePath, String Score)
	{
		File outputFile;
		BufferedWriter outputWriter;
		
		try {
			outputFile = new File(filePath);
			outputWriter = new BufferedWriter(new FileWriter(outputFile));
			
			//masukkan string Score
			outputWriter.write(Score);
			
			outputWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
```
Fungsi ```writeFile``` untuk menyimpan waktu terbaik dan mencatatnya di file ```HighScore.txt``` dan akan digunakan untuk permainan selanjutnya. Jadi, meskipun permainan sudah selesai (Exit), waktu terbaik akan tetap tercatat dan dapat digunakan untuk permainan selanjutnya.

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
- Menu/Halaman Utama

![Menu](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/01_Menu.png)


- Pilihan level

Level permainan yang dapat dipilih saat bermain dengan komputer (1 player)

![Level](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/02_Level.png)


- Pilihan Warna Bola dan Batas Score

Pilihan warna bola dan batas score yang disediakan dapat dipilih sebelum bermain (dengan 2 player atau pun 1 player)

![Warna Bola dan Score](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/03_Pilihan%20Warna%20Bola.png)


- Waktu Terbaik

High score dengan sistem waktu terbaik yang terus dapat tercatat meski sudah mengakhiri permainan

![Permainan Berlangsung](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/05_Permainan%20Berlangsung.png)
![Pause](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/06_Permainan%20Pause.png)
![Game Over](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/07_Permainan%20Selesai.png)


- Credit

![Credit](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Final%20Project/Gambar/04_Credit.png)

## Perubahan Yang Dilakukan dan Referensi

- Penambahan Fitur TimeCounter Untuk Menghitung waktu yang telah berjalan (Elapsed Time)

- Penambahan Fitur Sound untuk menambahkan suara pada saat game/aplikasi berjalan

- Penambahan Fitur agar dapat memilih untuk mengganti warna bola

- Merubah Warna Paddle sehingga warna paddle tiap player berbeda

- Penambahan Fitur Credits untuk menampilkan nama pembuat aplikasi

- Penambahan Fitur Best Time sebagai High score untuk waktu terbaik

## Referensi
[Referensi](https://successfulthomas.blogspot.com/2020/12/belajar-java-ceria-9.html)

## Link Youtube Demo
