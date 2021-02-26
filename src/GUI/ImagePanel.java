package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import mainPackage.Settings;

public class ImagePanel extends JPanel{
	BufferedImage img;
	
	public ImagePanel(Dimension dimension) {
		setDoubleBuffered(true);
		setPreferredSize(dimension);
	}
	public void setImage(BufferedImage img) {
		this.img = img;
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Settings.BackgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, null);
	}

}
