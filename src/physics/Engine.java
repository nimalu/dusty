package physics;

import java.util.ArrayList;
import java.util.List;

import mainPackage.Settings;
import renderPackage.ColorGradient;

public class Engine {
	
	private List<Particle> particles;
	private Emitter emitter;
	private int count, frames;
	
	public Engine(int count, int frames) {
		this.count = count;
		this.frames = frames;
		particles = new ArrayList<>();
		emitter = new Emitter(particles, Settings.minSize, Settings.maxSize, new ColorGradient(Settings.colorGradient));
	}
	

	private double emit = 0;
	public void tick(int frame) {
		emit+=((double)count)/frames;
		while(emit>=1) {
			emit -= 1;
			emitter.emit(Settings.startVelocity);
		}
		List<Particle> deaths = new ArrayList<>();
		for(Particle p : particles) {
			applyForceFields(p, frame);
			applyVector(p, 1);
			if(clip(p))
				deaths.add(p);
		}
		particles.removeAll(deaths);
	}

	private boolean clip(Particle p) {
		if(p.getPosX()<Settings.clipPosX1)
			return true;
		if(p.getPosX()>Settings.clipPosX2)
			return true;
		if(p.getPosX()<Settings.clipPosY1)
			return true;
		if(p.getPosX()>Settings.clipPosY2)
			return true;
		if(p.getPosX()<Settings.clipPosZ1)
			return true;
		if(p.getPosX()>Settings.clipPosZ2)
			return true;
		return false;
	}

	private void applyForceFields(Particle p, int frame) {
		for(ForceField f : Settings.forceFields) {
			p.getVelocity().add(f.getForceAt(p.getPosX(), p.getPosY(), p.getPosZ(), frame));
		}
	}

	private void applyVector(Particle p, double d) {
		p.setPosX(p.getPosX()+p.getVelocity().x*d);
		p.setPosY(p.getPosY()+p.getVelocity().y*d);
		p.setPosZ(p.getPosZ()+p.getVelocity().z*d);
	}

	public List<Particle> getParticles() {
		return particles;
	}



}
