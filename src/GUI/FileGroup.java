package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileGroup extends JPanel{
	private JCheckBox saveToDisk;
	public FileGroup() {
		saveToDisk = new JCheckBox("Save");
		saveToDisk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(saveToDisk.isSelected()) {
					button.setEnabled(true);
					textField.setEnabled(true);
				}else {
					button.setEnabled(false);
					textField.setEnabled(false);
				}
			}
		});
		textField = new JTextField();
		textField.setColumns(30);
//		textField.setText("C:\\Niklas\\Temp\\Test");
		button = new JButton("Choose Location");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("C:\\Niklas\\Temp\\Test");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(FileGroup.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                textField.setText(fc.getSelectedFile().getAbsolutePath());
	            }
			}
		});
		button.setEnabled(false);
		textField.setEnabled(false);
		setLayout(null);
		saveToDisk.setBounds(0, 0, 20, 20);
		button.setBounds(30, 0, 120, 20);
		textField.setBounds(160, 0, 300, 20);
		add(saveToDisk);
		add(button);
		add(textField);
	}
	private JTextField textField;
	private JButton button;
	public String getString() {
		return textField.getText();
	}
	public boolean getExport() {
		return saveToDisk.isSelected();
	}
	

	
	
}
