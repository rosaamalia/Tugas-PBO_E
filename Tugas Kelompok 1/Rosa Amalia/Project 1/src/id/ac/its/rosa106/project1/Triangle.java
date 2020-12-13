package id.ac.its.rosa106.project1;

import javax.swing.JOptionPane;
import java.lang.Math;

public class Triangle {
	public static void main(String[] args)
	{
		//segitiga siku-siku
		
		String alas = JOptionPane.showInputDialog("Masukkan alas segitiga");
		String tinggi = JOptionPane.showInputDialog("Masukkan tinggi segitiga");
		
		int a = Integer.parseInt(alas);
		int t = Integer.parseInt(tinggi);
		double r = Math.sqrt(((a*a)+(t*t)));
		
		double luas = (0.5)*a*t;
		double keliling = a + t + r;
		
		JOptionPane.showMessageDialog(null, "Luas segitiga: " + luas + "\r\nKeliling Segitiga: " + keliling, "Luas dan Keliling Segitiga", JOptionPane.PLAIN_MESSAGE);
	}
}
