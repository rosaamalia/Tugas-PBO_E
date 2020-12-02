package id.ac.its.rihan165;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LabelFrame extends JFrame {
	private final JLabel label;
	
	public LabelFrame() {
		super("Testing Jlabel");
		setLayout(new FlowLayout());
		Icon foto = new ImageIcon(getClass().getResource("fotorihan.jpeg"));
		label = new JLabel();
		label.setText("Rihan Farih Bunyamin 05111940000165");
		label.setIcon(foto);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setToolTipText("hello rihan");
		add(label);
		
	}
}
