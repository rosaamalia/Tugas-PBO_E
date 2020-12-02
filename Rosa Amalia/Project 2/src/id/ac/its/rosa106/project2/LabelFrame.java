package id.ac.its.rosa106.project2;

import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LabelFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel label;
	
	public LabelFrame()
	{
		super("Biodata Mahasiswa");
		setLayout(new FlowLayout()); //set frame
		
		//Icon foto = new ImageIcon(getClass().getResource("images/foto.jpg"));
		Icon foto = new ImageIcon(new ImageIcon("image/foto.jpg").getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
		
		label = new JLabel();
		label.setText("Rosa Amalia - 05111940000106");
		label.setIcon(foto);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setToolTipText("Foto Rosa Amalia");
		add(label);
	}
}
