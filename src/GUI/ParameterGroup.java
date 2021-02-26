package GUI;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParameterGroup extends JPanel implements ChangeListener{
	private JSlider slider;
	private JLabel label;
	private JSpinner spinner;
	private String name;

	
	public ParameterGroup(String name, int min, int max, int value) {
		this.name = name;
		slider = new JSlider(min, max, value);
		slider.addChangeListener(this);
		slider.setAutoscrolls(true);
		slider.setValue(value);
		label = new JLabel(name);
		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(50, 20));
		spinner.setValue(value);
		spinner.addChangeListener(this);
		
		this.setLayout(null);
		add(label);
		label.setBounds(0, 0, 50, 30);
		add(spinner);
		spinner.setBounds(50, 0, 60, 30);
		add(slider);
		slider.setBounds(110, 0, 150, 30);
		
		this.setPreferredSize(new Dimension(370, 50));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource().equals(slider)) {
			spinner.setValue(slider.getValue());
		}else if(e.getSource().equals(spinner)) {
			if(((int)spinner.getValue())>slider.getMaximum()) {
				slider.setMaximum((int)spinner.getValue());
			}
			slider.setValue((int)spinner.getValue());
		}
		
	}

	@Override
	public void setEnabled(boolean enabled) {
		spinner.setEnabled(false);
		slider.setEnabled(false);
		label.setEnabled(false);
		super.setEnabled(enabled);
	}
	
	public int getValue() {
		return slider.getValue();
	}
	
	public String getName() {
		return name;
	}

}
