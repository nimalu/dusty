package physics;

public class Turbulence extends ForceField{


	public Turbulence(String name, double posX, double posY, double posZ, double strength, double falloff) {
		super(name, posX, posY, posZ, strength, falloff);
	}

	public Vector3 getForceAt(double x, double y, double z, int frame) {
		Vector3 temp = new Vector3(posX, posY, posZ).minus(new Vector3(x, y, z));
		double length = temp.getLength();
		double force = strength/(Math.pow(length, falloff));
		return Vector3.RANDOMVECTOR().multiply(force);
	}


}

