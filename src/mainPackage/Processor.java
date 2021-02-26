package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.imageio.ImageIO;

import physics.Engine;
import renderPackage.Renderer;

public class Processor implements Runnable{

	static void showVideo(String path) {
		System.out.println("Show Movie");
		String command = "ffplay \""+path+"\\export.mp4\"";
		System.out.println(command);
		try {
			exec(command);
		} catch (IOException | InterruptedException e) {e.printStackTrace();
		}
	}

	public static void deleteDir(File path) {
		for (File file : path.listFiles()) {
			file.delete();
		}
	}
	static void exportVideo(int fps, String path) {
		System.out.println("Export Movie");
		String command = "ffmpeg -framerate "+fps+" -i \""+path+"\\frame%d.png\" -c:v libx264 \""+path+"\\export.mp4\"";
		System.out.println(command);
		try {
			exec(command);
		} catch (IOException | InterruptedException e) {e.printStackTrace();
		}
	}
	static void exportimage(BufferedImage img, File path) {
		try {
				ImageIO.write(img, "png", path);
			} catch (IOException e) {e.printStackTrace();
		}
		System.out.println("Saved " + path.getName());
	}
	private static void exec(String command) throws IOException, InterruptedException {
		Runtime.getRuntime().exec(command);
	}

	private BufferedImage lastFrame;
	private Thread thread;
	private List<Image> images;
	public boolean processing = false;

	public void process(List<Image> images) {
		processing = true;
		Settings.print();
		thread = new Thread(this);
		thread.start();
		this.images = images;
	}

	@Override
	public void run() {
		String path = Settings.path;
		int particleCount = Settings.particleCount;
		int frames = Settings.frames;
		int[] resolution = Settings.resolution;
		boolean export = Settings.export;
		System.out.println("Start Rendering");
//		deleteDir(new File(path));
		Engine engine = new Engine(particleCount, frames);
		Renderer renderer = new Renderer(resolution[0], resolution[1], engine.getParticles());
		BufferedImage img;
		DecimalFormat f = new DecimalFormat("#0.00");

		long start , end, all=0, difference;
		for(int i = 0;i<frames;i++) {
			start = System.nanoTime();
			engine.tick(i);
			img = renderer.render();
			if(export)
				exportimage(img, new File(path+"\\frame"+String.format("%0"+String.valueOf(frames).length()+"d", i)+".png"));
			lastFrame = img;
			drawInformation(i, frames, new File(path));
			synchronized (images) {
				images.add(lastFrame);
			}
			end = System.nanoTime();
			difference = end-start;
			all+=difference;
			if(Settings.sleep!=0)
				try {Thread.sleep(Settings.sleep);
				} catch (InterruptedException e) {e.printStackTrace();
			}
		}
		System.out.println("Average rendertime per frame " + f.format(all/frames*0.000001) + "ms");
	    Toolkit.getDefaultToolkit().beep();
	    processing = false;
	}

	private void drawInformation(int frame, int frames, File path) {
		Graphics2D g = lastFrame.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		int length = (path.getAbsolutePath().length() + 6)*5;
		g.setColor(new Color(255,255,255,150));
		g.fillRect(5, 5, length+40, 200);
		g.setColor(Color.BLACK);
		g.drawString("Current Frame: " + (frame+1) + " /" + frames, 10, 30);
		g.drawString("Path: " + path.getAbsolutePath(), 10, 45);
		g.setColor(new Color(100, 255, 100));
		g.fillRect(10, 52, (int) ((1d*frame/frames)*length), 10);
		g.setColor(Color.BLACK);
		g.drawRect(10, 52, length, 10);
	}

	public BufferedImage getLastFrame() {
		return lastFrame;
	}

	public Thread getThread() {
		return thread;
	}


	

}
