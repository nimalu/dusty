package mainPackage;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import GUI.MovingIcon;
import GUI.TranslucentPane;
import GUI.Window;

public class Dusty {

	private static Processor processor;
	private static Window window;
	public static State state = State.RENDERING;

	public static void main(String[] args) {
		ImageLoader.loadImages();
		startAnimation();
		processor = new Processor();
		window = new Window(processor);
		while(true) {
			waitForWindow();
			setSettings();
			if(Settings.export && !new File(Settings.path).exists()) {
				JOptionPane.showMessageDialog(null, "You first need to choose a directory!", "Error", JOptionPane.ERROR_MESSAGE);
				state = State.RENDERING;
				continue;
			}else{
				renderProcess();
			}
			
		}
		
	}

	private static void startAnimation() {
		 JWindow frame = new JWindow();
         frame.setAlwaysOnTop(true);
         frame.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 if (e.getClickCount() == 2) {
                     SwingUtilities.getWindowAncestor(e.getComponent()).dispose();
                 }
             }
         });
         frame.setBackground(new Color(0,0,0,0));
         frame.setContentPane(new TranslucentPane());
         MovingIcon icon = new MovingIcon();
         frame.add(icon);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
//		 playSound();
         for(int i = 0;i<150;i++) {
        	 icon.animate(i);
        	 try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {e1.printStackTrace();
			}
         }
         frame.setAlwaysOnTop(false);
         frame.dispose();
	}

	public static void renderProcess() {
		System.out.println("Starting RenderProcess");
		List<Image> images = new ArrayList<>();
		window.addImageViewer(Settings.resolution[0], Settings.resolution[1]);
		state = State.RENDERING;
		processor.process(images);
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(processor.processing) {
			synchronized (images) {
				if(images.isEmpty())continue;
				window.showImage((BufferedImage) images.get(0));
				images.remove(0);
			}
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS : " + frames);
				frames = 0;
			}
		}
		System.out.println("Finished RenderProcess");
	}

	public static void setSettings() {
		Settings.frames = window.frames.getValue();
		Settings.particleCount = window.particleCount.getValue();
		Settings.path = window.fileGroup.getString();
		Settings.resolution = window.reso.getResolution();
		Settings.export = window.fileGroup.getExport();
		Settings.startVelocity = window.startVelocity.get();
		Settings.colorGradient = window.colorGroup.getColors();
	}

	public static void waitForWindow() {
		System.out.println("WaitForWindow");
		if(!state.equals(State.SETTINGS)) {
			state = State.SETTINGS;
			window.openSettingsFrame();
			synchronized (processor) {
				try {
					processor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public static void playSound() {
		try {
			AudioInputStream stream;
			Clip clip;
			
			stream = AudioSystem.getAudioInputStream(Dusty.class.getResourceAsStream("/Jingle.wav"));
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (LineUnavailableException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {e.printStackTrace();
		}
	        
	}

}
