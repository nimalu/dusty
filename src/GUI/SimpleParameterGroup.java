package GUI;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class SimpleParameterGroup extends JPanel{
	private double value;
	private String name;
	private JLabel label;
	private JTextField textField;

	
	public SimpleParameterGroup(String name, double value) {
		setLayout(null);
		this.name = name;
		label = new JLabel(name);
		label.setBounds(0, 0, 50, 20);
		add(label);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent ke){
				char c = ke.getKeyChar();
				if((!(Character.isDigit(c))) && (c != '.') && (c != '-')){
					ke.consume();
				}
			}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		textField.setColumns(8);
		textField.setBounds(50, 0, 70, 20);
		add(textField);
		textField.setText(String.valueOf(value));
		this.setPreferredSize(new Dimension(120, 30));
		
	}

	
	public double getValue() {
		value = Double.parseDouble(textField.getText());
		return value;
	}
	
	public String getName() {
		return name;
	}


	public void setValue(double d) {
		value = d;
		textField.setText(d+"");
	}


	public JTextComponent getTextField() {
		return textField;
	}
	
	

}
