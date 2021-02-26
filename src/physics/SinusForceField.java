package physics;

public class SinusForceField extends ForceField{

	public SinusForceField(String name, double posX, double posY, double posZ, double strength, double falloff, double speed) {
		super(name, posX, posY, posZ, strength, falloff);
		this.speed = speed;
	}



	private double speed;

	

	public Vector3 getForceAt(double x, double y, double z, int frame) {
		Vector3 temp = new Vector3(posX, posY, posZ).minus(new Vector3(x, y, z));
		double length = temp.getLength();
		if(length<2)length=2;
		double force = strength/(Math.pow(length, falloff));
		return new Vector3(1, 1, 0).normalize().rotate(frame*speed).multiply(force);
	}



	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double value) {
		speed = value;
	}

}
