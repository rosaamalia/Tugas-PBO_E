package id.ac.its.rihan165;

import javax.swing.JFrame;
import java.awt.EventQueue;

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
