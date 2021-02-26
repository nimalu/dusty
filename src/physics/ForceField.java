package physics;

public abstract class ForceField {
	String name;
	public ForceField(String name,double posX, double posY, double posZ, double strength, double falloff) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.strength = strength;
		this.falloff = falloff;
	}



	protected double posX;
	protected double posY;
	protected double posZ;
	protected double strength, falloff;
	
	
	
	public abstract Vector3 getForceAt(double x, double y, double z, int frame);

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public double getStrength() {
		return strength;
	}

	public double getFalloff() {
		return falloff;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public void setFalloff(double falloff) {
		this.falloff = falloff;
	}

	public void print() {
		System.out.println(name + "("
				+ posX+", "
				+ posY+", "
				+ posZ+")");
	}
}
