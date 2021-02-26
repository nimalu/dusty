package physics;

public class Wind extends ForceField{

	public Wind(String name, double posX, double posY, double posZ, double strength, double falloff) {
		super(name, posX, posY, posZ, strength, falloff);
	}

	@Override
	public Vector3 getForceAt(double x, double y, double z, int frame) {
		return new Vector3(getPosX(), getPosY(), getPosZ()).multiply(strength);
	}

}
