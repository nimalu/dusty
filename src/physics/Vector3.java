package physics;

public class Vector3 {
	public static Vector3 RANDOMVECTOR() {
		return new Vector3(Math.random()*2-1, Math.random()*2-1, Math.random()*2-1).normalize();
	}

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double x,y,z;

	public Vector3 minus(Vector3 v) {
		return new Vector3(x-v.x, y-v.y, z-v.z);
	}

	public double getLength() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	public Vector3 normalize() {
		this.multiply(1/this.getLength());
		return this;
	}

	public Vector3 multiply(double d) {
		x*=d;
		y*=d;
		z*=d;
		return this;
	}

	public void add(Vector3 f) {
		x+=f.x;
		y+=f.y;
		z+=f.z;
	}

	public Vector3 rotate(double a) {
		double axisX = 0, axisY=0;
		double angle = Math.toRadians(a);
		double tY=y-axisX,tX=x-axisY;
	    double cosa=Math.cos(angle);
	    double sina=Math.sin(angle);
	    x=tX*cosa + tY*sina + axisX;
	    y=-tX*sina + tY*cosa + axisY;
		return this;
	}
	
	public void rotate(double theta, Vector3 axis) {
		double u = axis.x;
		double v = axis.y;
		double w = axis.z;
		double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(theta)) 
			    + x*Math.cos(theta)
			    + (-w*y + v*z)*Math.sin(theta);
		double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(theta))
			    + y*Math.cos(theta)
			    + (w*x - u*z)*Math.sin(theta);
		double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(theta))
			    + z*Math.cos(theta)
			    + (-v*x + u*y)*Math.sin(theta);
		x= xPrime;
		y=yPrime;
		z=zPrime;
	}

	public Vector3 clamp(int i) {
		if(getLength()>i) {
			return this.normalize().multiply(i);
		}
		return this;
	}

	public Vector3 multiply(Vector3 vec) {
		x*=vec.x;
		y*=vec.y;
		z*=vec.z;
		return this;
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}

}
