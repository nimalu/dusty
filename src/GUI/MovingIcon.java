package GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JLabel;

import mainPackage.ImageLoader;

public class MovingIcon extends JLabel{

	private Image icon;
	private double scale;
	private double alpha;
	private float opacity=0f;
	public MovingIcon() {
		this.icon = ImageLoader.getImage("icon");
		scale = 1d;
		setPreferredSize(new Dimension(800, 800));
		alpha=0.0;
	}
	public void animate(int i) {
		scale=Math.sqrt(i)*0.07;
		alpha=Math.sin(i*0.1)*20 * (50d/(i+1));
		if(i>140)
			opacity-=0.1f;
		else if(i<50)
			opacity+=0.2f;
		if(opacity<0)
			opacity=0;
		if(opacity>1)
			opacity=1f;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
        g.setColor(new Color(200, 0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(400, 400);
		g.scale(scale, scale);
		g.rotate(Math.toRadians(alpha));
		g.translate(-200, -200);
//		g.setColor(Color.red);
//		g.fillOval(0, 0, 400, 400);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(icon, 0, 0, 400, 400, null);
	}

}
