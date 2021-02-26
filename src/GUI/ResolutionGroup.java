package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class ResolutionGroup extends JPanel{
	JSpinner width, height;
	JLabel res, mal;
	public ResolutionGroup() {
		this.width= new JSpinner();
		width.setSize(50, width.getHeight());
		width.setValue(1280);
		this.height= new JSpinner();
		height.setSize(50, height.getHeight());
		height.setValue(720);
		res = new JLabel("Resolution");
		mal = new JLabel("x");
		setLayout(null);
		
		res.setBounds(0, 0, 50, 30);
		add(res);
		width.setBounds(60, 0, 70, res.getHeight());
		add(width);
		mal.setBounds(137, 0, 10, res.getHeight());
		add(mal);
		height.setBounds(150, 0, 70, res.getHeight());
		add(height);
	}
	public int[] getResolution() {
		return new int[] {(int) width.getValue(),(int) height.getValue()};
	}

}
