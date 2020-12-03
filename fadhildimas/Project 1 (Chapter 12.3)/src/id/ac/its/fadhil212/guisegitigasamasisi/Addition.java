package id.ac.its.fadhil212.guisegitigasamasisi;

import javax.swing.JOptionPane;

public class Addition {
	
	public static void main(String[] args)
	  {
	     String firstNumber = JOptionPane.showInputDialog("Masukkan Panjang Sisi");
	     String secondNumber = JOptionPane.showInputDialog("Masukkan Tinggi");
	     
	     int sisi = Integer.parseInt(firstNumber);
	     int tinggi = Integer.parseInt(secondNumber);
	     
	     int luas = sisi * tinggi / 2;
	     int keliling = 3*sisi;
	     
	     JOptionPane.showMessageDialog(null, "Luas Segitiga Sama Sisi adalah: " + luas, "Luas Segitiga Sama sisi", JOptionPane.PLAIN_MESSAGE);
	     JOptionPane.showMessageDialog(null, "Keliling Segitiga Sama Sisi adalah: " + keliling, "Keliling Segitiga Sama sisi", JOptionPane.PLAIN_MESSAGE);
	     
	  }
}
