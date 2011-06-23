package exe.boothsMultiplication;

import exe.GAIGSdatastr;
import exe.boothsMultiplication.GAIGSprimitiveCollection.Primitive;

/**
 * <p>
 * <code>GAIGSprimitiveCollection</code> provides the ability to draw 2D
 * graphics for use in visualizations. This class supports a variety of 2D
 * graphics primitives, including lines, polygons, circles, ellipises, etc.
 * Creation of the primitives adheres to the other GAIGS classes and colors are
 * specified with the standard color string.
 * </p>
 * 
 * @author Shawn Recker
 * @author Adam Voss, Seperated out protected classes
 * @version 6/22/2010
 */
public class GAIGSpolygon implements GAIGSdatastr, Primitive {
	public int nSides;
	public double[] ptsX;
	public double[] ptsY;
	public String fcolor;
	public String ocolor;
	public String lcolor;
	public String label;
	public double height;
	public int width;
	private String name;
	private GAIGSpolygon t; 

	/**
	 * Creates a polygon.
	 * @param nSides The number of sides to the polygon
	 * @param ptsX Array containing the x coordinate values for the polygon
	 * @param ptsY Array containing the y coordinate values for the polygon
	 * @param fillColor The internal color of the polygon (use an empty string for no fill color)
	 * @param outlineColor The color of the circle polygon
	 * @param labelColor The color of the text in the circle label
	 * @param labelText The text to be drawn in the center of the circle
	 * @param textHeight The Height of the text in the label
	 * @param lineWidth The thickness of the outline of the polygon
	 */
	public GAIGSpolygon(int nSides, double ptsX[], double ptsY[],
			String fillColor, String outlineColor, String labelColor, String labelText,
			double textHeight, int lineWidth) {
		this.nSides = nSides;
		this.ptsX = new double[nSides];
		this.ptsY = new double[nSides];
		for (int i = 0; i < nSides; ++i) {
			this.ptsX[i] = ptsX[i];
			this.ptsY[i] = ptsY[i];
		}
		this.fcolor = fillColor;
		this.ocolor = outlineColor;
		this.lcolor = labelColor;
		this.label = labelText;
		this.height = textHeight;
		this.width = lineWidth;
	}
	/**
	 * Creates a polygon
	 * @param nSides The number of sides to the polygon
	 * @param ptsX Array containing the x coordinate values for the polygon
	 * @param otsY Array containing the y coordinate values for the polygon
	 * @param fillColor The internal color of the polygon (use an empty string for no fill color)
	 * @param outlineColor The color of the circle polygon
	 * @param labelColor The color of the text in the circle label
	 * @param labelText The text to be drawn in the center of the circle
	 */
	public GAIGSpolygon(int nSides, double ptsX[], double ptsY[], String fillColor, String outlineColor,
			String labelColor, String labelText){
		this(nSides, ptsX, ptsY, fillColor, outlineColor, labelColor, labelText, TEXT_HEIGHT, LINE_WIDTH);
	}

	@Override
	public String toXML() {
		GAIGSpolygon pl = this;
		String xml = "<primitivecollection>\n\t<name>" + name +
		"</name>\n\t"+ "<bounds x1=\"0.0\" y1=\"0.0\" x2=\"1.0\" y2=\"1.0\"/>" +"\n\t" +"<polygon nSides=\"" + pl.nSides;
		for(int j=0; j<pl.nSides ; ++j) {
			xml += "\" ptsX"+ j + "=\"" + pl.ptsX[j] + "\" ptsY"+ j + "=\"" + pl.ptsY[j];
		}
		xml += "\" fcolor=\"" + pl.fcolor + "\" " +
		"ocolor=\"" + pl.ocolor + "\" text=\"" + pl.label + "\" lcolor=\"" + pl.lcolor + "\" height=\"" +
		pl.height + "\" width=\"" + pl.width + "\"/>\n" + "</primitivecollection>\n";
		return xml;
	}

	private String computeBounds() {
		double x1 = Double.MAX_VALUE;
		double y1 = Double.MAX_VALUE;
		double x2 = Double.MIN_VALUE;
		double y2 = Double.MIN_VALUE;

		for(int j = 0; j < t.nSides; ++j) {
			x1 = (x1 < t.ptsX[j] ? x1 : t.ptsX[j]);
			y1 = (y1 < t.ptsY[j] ? y1 : t.ptsY[j]);
			x2 = (x2 > t.ptsX[j] ? x1 : t.ptsX[j]);
			y2 = (y2 > t.ptsY[j] ? y1 : t.ptsY[j]);
		}

		return "<bounds x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2 + "\"/>";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}