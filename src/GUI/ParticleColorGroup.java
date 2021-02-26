package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainPackage.Settings;

public class ParticleColorGroup extends JPanel implements ActionListener{
	private ColorButton color1;
	private ColorButton color2;
	private JLabel label;

	public ParticleColorGroup() {
		setLayout(null);
		color1 = new ColorButton(Settings.colorGradient[0]);
		color1.setBounds(70, 0, 50, 30);
		color1.addActionListener(this);
		color2 = new ColorButton(Settings.colorGradient[1]);
		color2.addActionListener(this);
		color2.setBounds(125, 0, 50, 30);
		label = new JLabel("Color");
		label.setBounds(0, 0, 60, 30);
		add(color1);
		add(color2);
		add(label);
	}
	
	
	public Color[] getColors() {
		return new Color[]{color1.getColor(), color2.getColor()};
	}
	
	private class ColorButton extends JButton{

		private Color color;

		public ColorButton(Color color) {
			this.color = color;
		}
		
		@Override
		public void paint(Graphics g) {
			g.setColor(new Color(color.getRGB()));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color c) {
			if(c!=null)
				this.color = c;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color start;
		if(e.getSource().equals(color1)) {
			start = color1.getColor();
			start=(JColorChooser.showDialog(this, 
		            "Choose Color", start));
			if(start!=null)
				color1.setColor(start);
		}
		else if(e.getSource().equals(color2)) {
			start = color2.getColor();
			color2.setColor(JColorChooser.showDialog(this, 
		            "Choose Color", start));
		}
		this.repaint();
	}

}
