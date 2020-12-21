# Collision Detection with Mouse Handling
Berdasarkan karakteristik games berdimensi 2D, untuk menangani collision yang berlebihan maka diperlukannya event handling. Kata event disini berarti suatu peristiwa yang dipicu oleh pengguna pasa suatu komponen GUI. Contoh event handling disini yaitu:
- **Keyboard handling** : 
	Pada Keyboard handling, pergerakan spaceship menggunakan keyboard. bisa dilihat pada tugas sebelumnya dengan link berikut: 
	[Collision Detection](https://github.com/rosaamalia/Tugas-PBO_E/tree/main/Collision%20Detection)
- **Mouse handling** ;
	Pada mouse handling, Tugas aplikasi game sebelumnya yaitu game Spaceship dilakukan beberapa modifikasi agar aplikasi bisa digunakan menggunakan mouse handling. Mouse Handling sendiri merupakan sebuah metode dengan menggunakan mouse sebagai alat gerak pada sebuah event yang diberikan oleh pengguna. Penjelasan cara kerja program dengan menggunakan mouse handling dapat dilihat di bawah ini.

## Cara Kerja Program

### Board

Pada class Board terdapat beberapa method yang diubah seperti:

- Method `Initboard`
	Method ini bertugas untuk menginisialisasi Board Windows. Pada method ini terdapat beberapa modifikasi pada bagian:
```
	addMouseListener(new TAdapter());
        addMouseMotionListener(new MAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
	...
```
Perubahan dapat dilihat pada bagian `MouseHandler` yang menggantikan fungsi `add(KeyListener)` pada aplikasi Collision Detection sebelumnya. Selanjutnya, membuat objek `MouseHandler` yang kemudian akan digunakan juga untuk menambah `MouseListener`.

- class `TAdapter`
	pada class `TAdapter` diganti dari `KeyListener` menjadi `MouseAdapter` sehingga bisa membaca pergerakan mouse
```
 private class TAdapter extends MouseAdapter {
    	
        @Override
        public void mousePressed(MouseEvent e) {
            spaceship.buttonPressed(e);
        }
    }
    private class MAdapter implements MouseMotionListener {

    	@Override
		public void mouseMoved(MouseEvent e) {
    		spaceship.move(e);
    		
    	}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

    }
```
Perbedaan class di atas (dengan menggunakan Mouse Handling) dengan class pada Aplikasi yang menggunkan Keyboard Handling adalah jika pada Keyboard Handling menggeser koordinar dari objek tidak dilakukannya perhitungan melalui perubahan (dx/dy) dengan menggunakan method `KeyPressed`. Sedangkan, pada Mouse Handling dapat langsung mengambil koordinat x dan y dari mouse (seperti apa yang di gambarkan pada method `MouseMoved` dan `MouseDragged`).

### Spaceship

- Method `Move`
Pada metode `Move` akan mengmbil koordinat mouse
```
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
```
- Method `buttonPressed`
```
public void buttonPressed(MouseEvent e) {

        int key = e.getButton();

        if (key == 1) {
            fire();
        }

    }
```
Pada metode `buttonPressed` ,Apabila kursor atau mouse mengarahkan keluar dari Board maka spaceship tidak akan muncul tetapi akan berhenti pada berbatasan jendela. Dan ketika mouse meng-click (`MousePressed`) maka spaceship akan menembakan peluru.

# Class Diagram
![Class Diagram](https://github.com/rosaamalia/Tugas-PBO_E/blob/main/Collision%20Detection%20with%20Mouse/Images/Diagram%20Kelas.jpg)

# Video Jalan program
[Link Video Jalan Program]()