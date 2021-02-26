package mainPackage;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import physics.ForceField;
import physics.Turbulence;
import physics.Vector3;

public class Settings {
	public static final String VERSION = "1.0";
	public static final long sleep = 0;
	public static final double minSize = 2;
	public static final double maxSize = 4;
	public static Color[] colorGradient = new Color[] {Color.WHITE, new Color(100,100,245,255)};
	public static List<ForceField> forceFields = new ArrayList<>();
	
	public static final Color BackgroundColor = new Color(0, 0, 0);
	public static final double clipPosX1 = -1000;
	public static final double clipPosX2 = 1000;
	public static final double clipPosY1 = -1000;
	public static final double clipPosY2 = 1000;
	public static final double clipPosZ1 = -1000;
	public static final double clipPosZ2 = 1000;
	public static int[] resolution;
	public static int frames;
	public static int particleCount;
	public static String path = "";
	public static boolean export;
	public static Vector3 startVelocity;

	static{
//		forceFields.add(new SinusForceField("SinusForceField1", 0, 0, -200, 1, 1,1));
		forceFields.add(new Turbulence("Turbolence",0, 1000, 0, 0.1, 0.3));
//		forceFields.add(new Turbulence("Turbolence2",-100, 0, 0, 1, 1));
//		forceFields.add(new Turbulence("Turbolence3",40, -60, 30, 0.1, 0.5));
//		forceFields.add(new Wind("Wind1", 0, 0, 1, 0.01, 1));
	}
	
	public static void print() {
		for(Field j : Settings.class.getFields()) {
			try {
				System.out.println(j.getName() + ": "+j.get(null));
			} catch (IllegalArgumentException e) {e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
