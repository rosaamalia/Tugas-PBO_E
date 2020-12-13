package id.ac.its.rihan165;
import javax.swing.JOptionPane;

public class Main
 {
 public static void main(String[] args)
 {
	
	 String firstNumber =
	 JOptionPane.showInputDialog("Masukkan Panjang");
	 String secondNumber =
	 JOptionPane.showInputDialog("Masukkan Lebar");
	 
	
	  int number1 = Integer.parseInt(firstNumber);
	  int number2 = Integer.parseInt(secondNumber);
	 
	  int luas = number1 * number2;
	  int keliling = 2*number1 + 2*number2;
	
	  JOptionPane.showMessageDialog(null, "Luas Persegi Panjang: " + luas,
	  "Sum of Two Integers", JOptionPane.PLAIN_MESSAGE);
	  
	  JOptionPane.showMessageDialog(null, "Keliling Persegi Panjang " + keliling,
			  "Sum of Two Integers", JOptionPane.PLAIN_MESSAGE);
	 
 }
}