package id.ac.its.fadhil212.guilingkaran;

import javax.swing.JOptionPane;

public class Addition {
	
	public static void main(String[] args)
	  {
	     String firstNumber = JOptionPane.showInputDialog("Masukkan Radius");
	     
	     double radius = Integer.parseInt(firstNumber);
	     
	     double luas = 3.14 * radius * radius;
	     double keliling = 2* 3.14 * radius;
	     
	     JOptionPane.showMessageDialog(null, "Luas Lingkaran adalah: " + luas, "Luas Lingkaran", JOptionPane.PLAIN_MESSAGE);
	     JOptionPane.showMessageDialog(null, "Keliling Lingkaran adalah: " + keliling, "Keliling Lingkaran", JOptionPane.PLAIN_MESSAGE);
	     
	  }
}
