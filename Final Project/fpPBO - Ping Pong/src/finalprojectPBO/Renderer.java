package finalprojectPBO;



import javax.swing.*;
import java.awt.*;
 
public class Renderer extends JPanel {
    private static  final long serialVersionUID = 1L;
 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Pong.pong.render((Graphics2D) g);
    }
}
