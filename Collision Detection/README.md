# Collision Detection
Disini akan menjelaskan tentang Program Collision di game Java 2D

Banyak game perlu menangani tabrakan, terutama game arcade. Sederhananya, kita perlu mendeteksi ketika dua objek bertabrakan di layar.

Dalam contoh kode berikutnya, kami akan memperluas contoh sebelumnya. Kami menambahkan sprite Alien baru. Kami akan mendeteksi dua jenis tabrakan: saat rudal menghantam kapal asing dan saat pesawat ruang angkasa kami bertabrakan dengan alien.

**Shooting aliens**

Dalam contoh, kami memiliki pesawat luar angkasa dan alien. Kita bisa memindahkan pesawat ruang angkasa di papan dengan menggunakan tombol kursor. Rudal yang menghancurkan alien diluncurkan dengan tombol spasi.


**Sprite.java** 
```
package com.zetcode;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

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

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
```

Kode yang dapat dibagikan oleh semua sprite (a craft, alien, dan missile) ditempatkan di kelas Sprite.

```
public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
}
```

Metode getBounds() mengembalikan persegi panjang pembatas dari gambar sprite. Kami membutuhkan batasan dalam deteksi tabrakan.

**SpaceShip.java**
```
package com.zetcode;

import java.awt.event.KeyEvent;
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
        loadImage("src/resources/spaceship.png");
        getImageDimensions();
    }

    public void move() {

        x += dx;
        y += dy;

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

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

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
```

Kelas ini mewakili spacecraft

```
private List<Missile> missiles;
```

Semua missile yang ditembakkan oleh spacecraft disimpan dalam daftar missiles.

```
public void fire() {
    missiles.add(new Missile(x + width, y + height / 2));
}
```

**Board.java**
```
package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private SpaceShip spaceship;
    private List<Alien> aliens;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;

    private final int[][] pos = {
        {2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239},
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
    };

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {
        
        aliens = new ArrayList<>();

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), 
                        missile.getY(), this);
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateShip();
        updateMissiles();
        updateAliens();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {

        if (spaceship.isVisible()) {
            
            spaceship.move();
        }
    }

    private void updateMissiles() {

        List<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateAliens() {

        if (aliens.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {

            Alien a = aliens.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
                aliens.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = spaceship.getBounds();

        for (Alien alien : aliens) {
            
            Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2)) {
                
                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
            }
        }

        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Alien alien : aliens) {

                Rectangle r2 = alien.getBounds();

                if (r1.intersects(r2)) {
                    
                    m.setVisible(false);
                    alien.setVisible(false);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
}
```

Ini adalah kelas Board.
```
private final int[][] pos = {
    {2380, 29}, {2500, 59}, {1380, 89},
    {780, 109}, {580, 139}, {680, 239},
    {790, 259}, {760, 50}, {790, 150},
    {980, 209}, {560, 45}, {510, 70},
    {930, 159}, {590, 80}, {530, 60},
    {940, 59}, {990, 30}, {920, 200},
    {900, 259}, {660, 50}, {540, 90},
    {810, 220}, {860, 20}, {740, 180},
    {820, 128}, {490, 170}, {700, 30}
};
```

Ini adalah posisi awal alien ships.

```
public void initAliens() {
    
    aliens = new ArrayList<>();

    for (int[] p : pos) {
        aliens.add(new Alien(p[0], p[1]));
    }
}
```
Metode initAliens() membuat daftar objek alien. Alien mengambil posisi awal mereka dari array pos.

```
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (ingame) {

        drawObjects(g);

    } else {

        drawGameOver(g);
    }

    Toolkit.getDefaultToolkit().sync();
}
```
Di dalam metode paintComponent(), kita menggambar sprite game atau menulis pesan game over. Ini tergantung pada variabel ingame.
```
private void drawObjects(Graphics g) {

    if (spaceship.isVisible()) {
        g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                this);
    }
...
}  
```
Metode drawObjects() menggambar sprite game di jendela. Pertama, kita menggambar sprite craft.
```
for (Alien alien : aliens) {
    if (alien.isVisible()) {
        g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
    }
}
```
Dalam loop ini kita menggambar semua alien; mereka ditarik hanya jika belum pernah dihancurkan sebelumnya. Ini diperiksa dengan metode isVisible().
```
g.setColor(Color.WHITE);
g.drawString("Aliens left: " + aliens.size(), 5, 15);
```
Di sudut kiri atas window program, kami menggambar berapa banyak alien yang tersisa.
```
private void drawGameOver(Graphics g) {

    String msg = "Game Over";
    Font small = new Font("Helvetica", Font.BOLD, 14);
    FontMetrics fm = getFontMetrics(small);

    g.setColor(Color.white);
    g.setFont(small);
    g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
            B_HEIGHT / 2);
}
```
DrawGameOver () menggambar pesan game over di tengah jendela. Pesan tersebut ditampilkan di akhir permainan, baik saat kita menghancurkan semua kapal alien maupun saat kita bertabrakan dengan salah satunya.
```
@Override
public void actionPerformed(ActionEvent e) {

    inGame();

    updateShip();
    updateMissiles();
    updateAliens();

    checkCollisions();

    repaint();
}
```
Setiap peristiwa aksi mewakili satu siklus permainan. Logika permainan difaktorkan ke dalam metode tertentu. Misalnya, updateMissiles() memindahkan semua missile yang tersedia.
```
private void updateAliens() {

    if (aliens.isEmpty()) {

        ingame = false;
        return;
    }

    for (int i = 0; i < aliens.size(); i++) {

        Alien a = aliens.get(i);
        
        if (a.isVisible()) {
            a.move();
        } else {
            aliens.remove(i);
        }
    }
}  
```
Di dalam metode updateAliens (), pertama-tama kami memeriksa apakah ada objek alien yang tersisa di daftar alien. Permainan selesai jika daftarnya kosong. Jika tidak kosong, kita pergi melalui daftar dan memindahkan semua itemnya. Alien yang dihancurkan dihapus dari daftar.
```
public void checkCollisions() {

    Rectangle r3 = spaceship.getBounds();

    for (Alien alien : aliens) {
        
        Rectangle r2 = alien.getBounds();

        if (r3.intersects(r2)) {
            
            spaceship.setVisible(false);
            alien.setVisible(false);
            ingame = false;
        }
    }
...
}
```
Metode checkCollisions() memeriksa kemungkinan tabrakan. Pertama, kami memeriksa apakah objek kerajinan bertabrakan dengan salah satu objek alien. Kami mendapatkan persegi panjang objek dengan metode getBounds(). Metode intersects() memeriksa apakah dua persegi panjang berpotongan.
```
List<Missile> ms = spaceship.getMissiles();

for (Missile m : ms) {

    Rectangle r1 = m.getBounds();

    for (Alien alien : aliens) {

        Rectangle r2 = alien.getBounds();

        if (r1.intersects(r2)) {
            
            m.setVisible(false);
            alien.setVisible(false);
        }
    }
}
```
Kode ini memeriksa tabrakan antara rudal dan alien.
**Alien.java**
```
package com.zetcode;

public class Alien extends Sprite {

    private final int INITIAL_X = 400;

    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage("src/resources/alien.png");
        getImageDimensions();
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }

        x -= 1;
    }
}
```
Ini adalah kelas Alien
```
public void move() {

    if (x < 0) {
        x = INITIAL_X;
    }

    x -= 1;
}
```
Alien kembali ke layar di sisi kanan setelah mereka menghilang di sisi kiri.
**Missile.java**
```
package com.zetcode;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 2;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("src/resources/missile.png");
        getImageDimensions();        
    }

    public void move() {
        
        x += MISSILE_SPEED;
        
        if (x > BOARD_WIDTH)
            visible = false;
    }
}
```
Ini adalah kelas Missile
```
public void move() {
    
    x += MISSILE_SPEED;
    
    if (x > BOARD_WIDTH)
        visible = false;
}
```
missile hanya bergerak ke satu arah. Mereka menghilang setelah mencapai batas jendela kanan.
```
package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class CollisionEx extends JFrame {

    public CollisionEx() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            CollisionEx ex = new CollisionEx();
            ex.setVisible(true);
        });
    }
}
```
dan ini adalah Main class dari program

Tampilan In-game

![In Game](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Collision%20Detection/Images/ingame.png)

Tampilan Game Over

![Game Over](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Collision%20Detection/Images/gameover.png)




