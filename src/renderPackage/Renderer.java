package renderPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import physics.Particle;
import physics.Vector3;

public class Renderer {
	public Renderer(int width, int height, List<Particle> particles) {
		this.width = width;
		this.height = height;
		this.particles = particles;
	}
	double FOV = 1;
	int width, height;
	List<Particle> particles;
	
	public BufferedImage render() {
		BufferedImage frame = createImage(width, height);
		Graphics2D g = frame.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.translate(width/2, height/2);
		
		particles.sort(new Comparator<Particle>() {

			@Override
			public int compare(Particle p1, Particle p2) {
				if(p1.getPosZ()<p2.getPosZ())
					return 1;
				return -1;
			}
		});
		
		float near = 800;  // distance from eye to near plane
	      
		double size;
		Vector3 trans;
		for(Particle p : particles) {
			trans = new Vector3(p.getPosX(), p.getPosY(), p.getPosZ());
			trans.rotate(Math.toRadians(-45), new Vector3(0, 1, 0));
			double x0 = trans.x;
			double y0 = trans.y;
			double z0 = trans.z;

		     // compute an orthographic projection
		    double x1 = x0 + z0;
		    double y1 = -x0 + y0 + z0;

			// now adjust things to get a perspective projection
		    double z1 = z0 - x0 - y0;
			x1 = x1*near/(z1+near);
			y1 = y1*near/(z1+near);

			
			size = (p.getSize()*near/(z1+near));
			if(size<=5 && size >=0) {
				g.setColor(new Color(p.getColor().getRed(), p.getColor().getGreen(),p.getColor().getBlue(), (int) (size*51) ));
			}else {
				g.setColor(p.getColor());
			}

		    /* Construct a shape and draw it */
		    Ellipse2D.Double shape = new Ellipse2D.Double(x1-0.5*size, -y1-0.5*size, size,size);
		    g.fill(shape);
		}
		g.dispose();
		return frame;
	}
	
	
	private BufferedImage createImage(int w, int h) {
		return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	}

}
