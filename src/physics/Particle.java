package physics;

import java.awt.Color;

public class Particle {
	public Particle(double posX, double posY, double posZ,double size, Color color, Vector3 vector) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.size = size;
		this.lifetime = 1;
		this.color = color;
		this.velocity = vector;
	}
	private double posX,posY, posZ,size, lifetime;
	private Color color;
	private Vector3 velocity;
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public double getLifetime() {
		return lifetime;
	}
	public void setLifetime(double lifetime) {
		this.lifetime = lifetime;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Vector3 getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector3 vector) {
		this.velocity = vector;
	}
	public double getPosZ() {
		return posZ;
	}
	public void setPosZ(double d) {
		posZ = d;
	}

}
