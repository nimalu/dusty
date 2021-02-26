package GUI;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TranslucentPane extends JPanel  {

	public TranslucentPane() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }
}
