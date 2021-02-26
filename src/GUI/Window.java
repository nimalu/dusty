package GUI;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mainPackage.Dusty;
import mainPackage.ImageLoader;
import mainPackage.Processor;
import mainPackage.Settings;
import physics.ForceField;
import physics.SimpleForceField;

public class Window implements ActionListener{
	private JFrame frame;
	public ParameterGroup frames;
	public ParameterGroup particleCount;
	private JButton submit;
	public FileGroup fileGroup;
	public ResolutionGroup reso;
	private Processor processor;
	private ImagePanel imgViewer;
	private JSeparator sep, sep2;
	private KeyListener listener = new KeyListener();
	private ForceFieldGroup forceFieldGroup;
	private DefaultListModel<ForceField> listModel;
	private JList<ForceField> forcefieldList;
	private JButton plus, minus;
	public StartVelocityGroup startVelocity;
	private JSeparator sep3;
	public ParticleColorGroup colorGroup;
	
	public Window(Processor processor) {
		this.processor = processor;   
		Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
		init();
		createComponents();
	}
	
	public void init(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {e.printStackTrace();
		}
		frame = new JFrame();
		frame.setTitle("Dusty Version "+ Settings.VERSION);
		frame.setIconImage(ImageLoader.getImage("icon"));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.requestFocus();
		frame.setAlwaysOnTop(false);
	}

	private void createComponents() {
		frames = new ParameterGroup("Frames", 1, 10000, 3000);
		frames.setBounds(10, 10, 300, 40);

		reso = new ResolutionGroup();
		reso.setBounds(310, 10, 300, 40);
		
		startVelocity = new StartVelocityGroup();
		startVelocity.setBounds(10, 70, 300, 40);
		
		particleCount = new ParameterGroup("Particles", 1,10000,1000);
		particleCount.setBounds(310, 70, 300, 40);
		
		colorGroup = new ParticleColorGroup();
		colorGroup.setBounds(10, 120, 300, 40);
		
		sep = new JSeparator();
		sep.setBounds(10, 55, 555, 10);
		sep2 = new JSeparator();
		sep2.setBounds(10, 162, 555, 10);
		sep3 = new JSeparator();
		sep3.setBounds(10, 400, 555, 10);
		
		listModel = new DefaultListModel<ForceField>();
		updateList();
		forcefieldList = new JList<>(listModel);
		forcefieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		forcefieldList.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent le) {
		    	if(forcefieldList.isSelectionEmpty())return;
		        forceFieldGroup.setForceField(listModel.get(forcefieldList.getSelectedIndex()));
		        forceFieldGroup.update();
		      }
		    });
		forcefieldList.setBounds(10, 170, 130, 200);
		
		plus = new JButton("+");
		plus.setBounds(10, 375, 65, 20);
		plus.addActionListener(this);
		
		minus = new JButton("-");
		minus.setBounds(75, 375, 65, 20);
		minus.addActionListener(this);
		
		forceFieldGroup = new ForceFieldGroup(this);
		forceFieldGroup.setBounds(150, 170, 600, 230);
		
		fileGroup = new FileGroup();
		fileGroup.setBounds(10, 410, 500, 40);
		
		
		submit = new JButton("Render");
		submit.setSize(200, 30);
		submit.setLocation(200, 450);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				forceFieldGroup.saveParameters();
				synchronized (processor) {
					processor.notifyAll();
				}
			}
		});

	}
	
	private void addComponents() {
		frame.setSize(600,530);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.add(frames);
		frame.add(particleCount);
		frame.add(reso);
		frame.add(sep);
		frame.add(sep2);
		frame.add(sep3);
		frame.add(forceFieldGroup);
		frame.add(fileGroup);
		frame.add(submit);
		frame.add(forcefieldList);
		frame.add(minus);
		frame.add(plus);
		frame.add(startVelocity);
		frame.add(colorGroup);
	}

	public void showImage(BufferedImage lastImage) {
		imgViewer.setImage(lastImage);
		imgViewer.repaint();
	}
	
	public void updateList() {
		listModel.removeAllElements();
		for(ForceField f : Settings.forceFields) {
			if(!listModel.contains(f))
				listModel.addElement(f);
		}
	}
	
	private class KeyListener implements AWTEventListener{
		public void eventDispatched(AWTEvent ev) {
            if (ev instanceof KeyEvent) {
                KeyEvent key = (KeyEvent) ev;
                if (key.getID() == KeyEvent.KEY_PRESSED && key.getKeyCode()==KeyEvent.VK_ESCAPE) {
                    System.out.println("ESCAPE");
                    Dusty.waitForWindow();
                    key.consume();
                }
            }
        }
	}

	private void removeComponents() {
		frame.getContentPane().removeAll();
		frame.repaint();
	}
	public void addImageViewer(int width, int height) {
		removeComponents();
		imgViewer = new ImagePanel(new Dimension(width, height));
		imgViewer.setDoubleBuffered(true);
		frame.setLayout(new FlowLayout());
		frame.add(imgViewer);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
	}
	public void openSettingsFrame() {
		if(frame.getContentPane().getComponents().length!=0)
			removeComponents();
		addComponents();
		frame.repaint();
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(minus)) {
			Settings.forceFields.remove(listModel.get(forcefieldList.getSelectedIndex()));
			forceFieldGroup.setForceField(null);
		}else if(e.getSource().equals(plus))
			Settings.forceFields.add(new SimpleForceField(
					"ForceField"+(Settings.forceFields.size()+1), 1, 1, 1, 1, 1));
		updateList();
	}


	
	

}
