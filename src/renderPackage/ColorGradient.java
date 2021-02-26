package renderPackage;

import java.awt.Color;

public class ColorGradient {
	private Color[] colors;

	public ColorGradient(Color... colors) {
		this.colors = colors;
	}
	
	public Color getColor(float d) {
		float d1 = 1f/colors.length;
		float d2 = d/d1 - 1;
		int c1 = (int) d2;
		int c2 = c1+1;
		return mixColors(colors[c1], colors[c2], d-c1*d1);
	}
	
	private Color mixColors(Color c1, Color c2, float ratio) {
		float r  = (float) ratio;
	    float ir = (float) 1.0 - r;

	    float rgb1[] = new float[3];
	    float rgb2[] = new float[3];    

	    c1.getColorComponents (rgb1);
	    c2.getColorComponents (rgb2);    

	    Color color = new Color (rgb1[0] * r + rgb2[0] * ir, 
	                             rgb1[1] * r + rgb2[1] * ir, 
	                             rgb1[2] * r + rgb2[2] * ir);
	    
	    return color;
	}



}
