package physics;

import java.util.List;

import renderPackage.ColorGradient;

public class Emitter {

	private List<Particle> particles;
	private double minSize;
	private double maxSize;
	private ColorGradient gradient;

	public Emitter(List<Particle> particles, double minSize, double maxSize, ColorGradient gradient) {
		this.particles = particles;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.gradient = gradient;
	}

	public void emit(Vector3 vec) {
		particles.add(new Particle(0, 0, 0, Emitter.randomDouble(minSize, maxSize), gradient.getColor((float) Math.random()), Vector3.RANDOMVECTOR().multiply(vec).multiply(0.6)));
	}
	
	public void emit(int i, double force) {
		emit(i, force, 0, 0, 0);
	}
	
	public void emit(int i, double force, double x, double y, double z) {
		for(int f = 0;f<i;f++) {
			particles.add(new Particle(x, y, z, Emitter.randomDouble(minSize, maxSize), gradient.getColor((float) Math.random()), Vector3.RANDOMVECTOR().multiply(force)));
		}
	}
	
	private static double randomDouble(double min, double max) {
		double r = Math.random();
		r *= max-min;
		return r+min;
	}

}
