package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import physics.Vector3;

public class StartVelocityGroup extends JPanel{
	JSpinner x, y, z;
	JLabel label;
	public StartVelocityGroup() {
		setLayout(null);
		label = new JLabel("Startvelocity");
		label.setBounds(0, 0, 100, 30);
		add(label);
		this.x= new JSpinner(new SpinnerNumberModel(0.0,-1000.0 ,1000.0,0.1));
		x.setBounds(70, 0, 40, 30);
		x.setValue(1d);
		add(x);
		this.y= new JSpinner(new SpinnerNumberModel(0.0,-1000.0 ,1000.0,0.1));
		y.setBounds(115, 0, 40, 30);
		y.setValue(1d);
		add(y);
		this.z= new JSpinner(new SpinnerNumberModel(0.0,-1000.0 ,1000.0,0.1));
		z.setBounds(160, 0, 40, 30);
		z.setValue(1d);
		add(z);
	}
	
	public void set(Vector3 vec) {
		x.setValue(vec.x);
		y.setValue(vec.y);
		z.setValue(vec.z);
	}
	
	public Vector3 get() {
		return new Vector3((double)x.getModel().getValue(),(double)y.getModel().getValue(),(double) z.getModel().getValue());
	}
}