package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainPackage.ImageLoader;
import mainPackage.Settings;
import physics.ForceField;
import physics.SimpleForceField;
import physics.SinusForceField;
import physics.Turbulence;
import physics.Wind;

public class ForceFieldGroup extends JPanel implements ActionListener{
	private SimpleParameterGroup x,y,z,strength, falloff, other1;
	private JTextField name;
	private ForceField selectedItem;
	private ButtonGroup typeGroup;
	private JCheckBox simpleForceField, wind, turbulence, sinus;
	private JButton updateName;
	private Window window;

	public ForceFieldGroup(Window window) {
		this.window = window;
		this.setLayout(null);
		
		name = new JTextField();
		name.setBounds(0, 0, 100, 20);
		this.add(name);
		
		updateName = new JButton(new ImageIcon(ImageLoader.getImage("right").getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
		updateName.setBounds(105, 0, 20, 20);
		updateName.setBorder(null);
		updateName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedItem==null)return;
				selectedItem.setName(name.getText());
				window.updateList();
			}
		});
		this.add(updateName);
		
		simpleForceField = new JCheckBox("SimpleForceField");
		simpleForceField.setBounds(0, 30, 120, 20);
		simpleForceField.addActionListener(this);
		wind = new JCheckBox("Wind");
		wind.setBounds(0, 60, 120, 20);
		wind.addActionListener(this);
		turbulence = new JCheckBox("Turbulence");
		turbulence.setBounds(0, 90, 120, 20);
		turbulence.addActionListener(this);
		sinus = new JCheckBox("SinusForceField");
		sinus.setBounds(0, 120, 120, 20);
		sinus.addActionListener(this);

		this.add(simpleForceField);
		this.add(wind);
		this.add(turbulence);
		this.add(sinus);
		
		typeGroup = new ButtonGroup();
		typeGroup.add(turbulence);
		typeGroup.add(simpleForceField);
		typeGroup.add(sinus);
		typeGroup.add(wind);
		
		x = new SimpleParameterGroup("PosX", 0);
		this.add(x);
		x.setLocation(140, 0);
		x.setSize(x.getPreferredSize());

		y = new SimpleParameterGroup("PosY", 0);
		this.add(y);
		y.setLocation(140, 40);
		y.setSize(y.getPreferredSize());

		z = new SimpleParameterGroup("PosZ", 0);
		this.add(z);
		z.setLocation(140, 80);
		z.setSize(z.getPreferredSize());

		strength = new SimpleParameterGroup("strength", 0);
		this.add(strength);
		strength.setLocation(140, 120);
		strength.setSize(strength.getPreferredSize());

		falloff = new SimpleParameterGroup("falloff", 0);
		this.add(falloff);
		falloff.setLocation(140, 160);
		falloff.setSize(falloff.getPreferredSize());
		
		other1 = new SimpleParameterGroup("Speed", 0);
		other1.setBounds(290, 0, 120, 30);
		this.add(other1);
		
		this.repaint();
	}


	
	
	
	private void loadParameters() {
		x.setValue(selectedItem.getPosX());
		y.setValue(selectedItem.getPosY());
		z.setValue(selectedItem.getPosZ());
		falloff.setValue(selectedItem.getFalloff());
		strength.setValue(selectedItem.getStrength());
		name.setText(selectedItem.getName());
		
		if(selectedItem instanceof SinusForceField) {
			this.add(other1);
			other1.setValue(((SinusForceField) selectedItem).getSpeed());
		}else {
			this.remove(other1);
		}
		this.repaint();
	}
	
	public void saveParameters() {
		saveParameters(selectedItem);
	}
	public void saveParameters(ForceField f) {
		if(selectedItem==null)return;
		f.setPosX(x.getValue());
		f.setPosY(y.getValue());
		f.setPosZ(z.getValue());
		f.setStrength(strength.getValue());
		f.setFalloff(falloff.getValue());
		
		if(selectedItem instanceof SinusForceField) {
			((SinusForceField)f).setSpeed(other1.getValue());
		}
	}

	public void setForceField(ForceField forceField) {
		saveParameters();
		this.selectedItem = forceField;
	}

	public void update() {
		loadParameters();
		updateType();
	}

	private void updateType() {
		if(selectedItem instanceof SimpleForceField) {
			simpleForceField.setSelected(true);
		}else if(selectedItem instanceof Wind) {
			wind.setSelected(true);
		}else if(selectedItem instanceof Turbulence) {
			turbulence.setSelected(true);
		}else if(selectedItem instanceof SinusForceField) {
			sinus.setSelected(true);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(selectedItem==null)return;
		if(e.getSource().equals(simpleForceField)) {
			if(selectedItem instanceof SimpleForceField)return;
			SimpleForceField newOne = new SimpleForceField("", 0, 0, 0, 0, 0);
			copyDefaults(selectedItem, newOne);
			Settings.forceFields.set(Settings.forceFields.indexOf(selectedItem), newOne);
			selectedItem = newOne;
		}else if(e.getSource().equals(wind)) {
			if(selectedItem instanceof Wind)return;
			Wind newOne = new Wind("", 0, 0, 0, 0, 0);
			copyDefaults(selectedItem, newOne);
			Settings.forceFields.set(Settings.forceFields.indexOf(selectedItem), newOne);
			selectedItem = newOne;
		}else if(e.getSource().equals(sinus)) {
			if(selectedItem instanceof SinusForceField)return;
			SinusForceField newOne = new SinusForceField("", 0, 0, 0, 0, 0,0);
			copyDefaults(selectedItem, newOne);
			Settings.forceFields.set(Settings.forceFields.indexOf(selectedItem), newOne);
			selectedItem = newOne;
		}else if(e.getSource().equals(turbulence)) {
			if(selectedItem instanceof Turbulence)return;
			Turbulence newOne = new Turbulence("", 0, 0, 0, 0, 0);
			copyDefaults(selectedItem, newOne);
			Settings.forceFields.set(Settings.forceFields.indexOf(selectedItem), newOne);
			selectedItem = newOne;
		}
		window.updateList();
		loadParameters();
	}
	
	private void copyDefaults(ForceField from, ForceField To) {
		To.setPosX(from.getPosX());
		To.setPosY(from.getPosY());
		To.setPosZ(from.getPosZ());
		To.setFalloff(from.getFalloff());
		To.setStrength(from.getStrength());
		To.setName(from.getName());
	}


}
