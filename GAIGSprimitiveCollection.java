/*
   This file is part of JHAVE -- Java Hosted Algorithm Visualization
   Environment, developed by Tom Naps, David Furcy (both of the
   University of Wisconsin - Oshkosh), Myles McNally (Alma College), and
   numerous other contributors who are listed at the http://jhave.org
   site

   JHAVE is free software: you can redistribute it and/or modify it under
   the terms of the GNU General Public License as published by the Free
   Software Foundation, either version 3 of the License, or (at your
   option) any later version.

   JHAVE is distributed in the hope that it will be useful, but WITHOUT
   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
   FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
   for more details.

   You should have received a copy of the GNU General Public License
   along with the JHAVE. If not, see:
   <http://www.gnu.org/licenses/>.
*/

package exe.boothsMultiplication;
import java.util.*;

import exe.GAIGSdatastr;

/**
 * <p><code>GAIGSprimitiveCollection</code> provides the ability to draw 2D graphics for use in visualizations.
 * This class supports a variety of 2D graphics primitives, including lines, polygons, circles, ellipises, etc.
 * Creation of the primitives adheres to the other GAIGS classes and colors are specified with the standard color string.
 * </p>
 *
 * @author Shawn Recker
 * @author Adam Voss, Seperated out protected classes
 * @version 6/22/2010
 */

public class GAIGSprimitiveCollection implements GAIGSdatastr {

  protected interface Primitive extends GAIGSdatastr {
	  public static final double TEXT_HEIGHT = .03;
	  public static final int LINE_WIDTH = 12;
  }

  /**
   * The Current collection of graphical primitives
  */
  protected ArrayList<Primitive> primitives;

  /**
   * The Name of the collection of graphical primitives
  */
  protected String name;

  /**
   * Creates an empty primitive collection with no name
  */
  public GAIGSprimitiveCollection() {
    primitives = new ArrayList<Primitive>();
	  name = "";
  }

  /**
   * Creates an empty primitive collection with the specified name
  */
  public GAIGSprimitiveCollection(String name) {
    primitives = new ArrayList<Primitive>();
    this.name = name;
  }

  /**
   * Sets the name of the primitive collection
   * @param name  The name of the collection
  */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the primitive collection
   * @return The name of the primitive collection
  */
  public String getName() {
    return name;
  }

  /**
   * Creates and Returns the GAIGS XML code for the current state of the primitive collection
   * @return A String containing GAIGS XML code for the primitive collection
  */
  public String toXML() {
    String xml;
    xml = "<primitivecollection>\n\t<name>" + name +
      "</name>\n\t"+ "<bounds x1=\"0.0\" y1=\"0.0\" x2=\"1.0\" y2=\"1.0\"/>" +"\n\t";

    for(int i = 0; i < primitives.size(); ++i) {
      xml += primitives.get(i).toXML();
    }
    xml += "</primitivecollection>\n";
    return xml;
  }

  /**
   * Removes all primitives from the collection
  */
  public void clearPrimitives() {
    primitives.clear();
  }

  /**
   * Adds a circle to the primitive collection
   * @param cx The center x coordinate of the circle
   * @param cy The center y coordinate of the circle
   * @param r The radius of the circle
   * @param fillColor The internal color of the circle (use an empty string for no fill color)
   * @param outlineColor The color of the circle outline
   * @param labelColor The color of the text in the circle label
   * @param labelText The text to be drawn in the center of the circle
   * @param textHeight The Height of the text in the label
   * @param lineWidth The thickness of the outline of the circle
  */
  public void addCircle(double cx, double cy, double r, String fillColor, String outlineColor,
    String labelColor, String labelText, double textHeight, int lineWidth)
  {
    primitives.add(new GAIGScircle(cx, cy, r, fillColor, outlineColor, labelColor, labelText, textHeight, lineWidth));
  }

  /**
   * Adds a polygon to the primitive collection
   * @param nSides The number of sides to the polygon
   * @param ptsX Array containing the x coordinate values for the polygon
   * @param otsY Array containing the y coordinate values for the polygon
   * @param fillColor The internal color of the polygon (use an empty string for no fill color)
   * @param outlineColor The color of the circle polygon
   * @param labelColor The color of the text in the circle label
   * @param labelText The text to be drawn in the center of the circle
   * @param lineWidth The thickness of the outline of the polygon
  */
  public void addPolygon(int nSides, double ptsX[], double ptsY[], String fillColor, String outlineColor,
    String labelColor, String labelText, double textHeight, int lineWidth)
  {
    primitives.add(new GAIGSpolygon(nSides, ptsX, ptsY, fillColor, outlineColor, labelColor, labelText, textHeight, lineWidth));
  }

  /**
   * Adds a line to the primitive collection
   * @param x Array of 2 containing the x coordinates for the start point and end point
   * @param y Array of 2 containing the y coordinates for the start point and end point
   * @param color The color of the line
   * @param lcolor The color of the text in the label
   * @param label The text to printed near the line
   * @param textHeight The Height of the text in the label
   * @param lineWidth The thickness of the line
  */
  public void addLine(double x[], double y[], String color, String lcolor, String label, double textHeight, int lineWidth)
  {
    primitives.add(new GAIGSline(x, y, color,lcolor, label, textHeight, lineWidth));
  }

  /**
   * Adds a line to the primitive collection
   * @param x Array of 2 containing the x coordinates for the start point and end point
   * @param y Array of 2 containing the y coordinates for the start point and end point
   * @param color The color of the line
   * @param lcolor The color of the text in the label
   * @param label The text to printed near the line
   * @param dashSize The length of the dash in the line
   * @param textHeight The Height of the text in the label
   * @param lineWidth The thickness of the line dashes
  */
  //TODO make this a class
  public void addDashedLine(double x[], double y[], String color, String lcolor,
    String label, double dashSize, double textHeight, int lineWidth)
  {

    double dist = Math.sqrt((x[1] - x[0])*(x[1] - x[0]) + (y[1] - y[0])*(y[1] - y[0]));
    double size = dashSize;

    int numLines = ((int)Math.ceil(dist / size)) / 2;

    double [] xvals = new double[2];
    double [] yvals = new double[2];

    for(int i = 0; i < numLines; i+=2) {
      xvals[0] = x[0] + (i / (2*(double)numLines)) * x[1];
      yvals[0] = y[0] + (i / (2*(double)numLines)) * y[1];
      xvals[1] = x[0] + ((i+1) / (2*(double)numLines)) * x[1];
      yvals[1] = y[0] + ((i+1) / (2*(double)numLines)) * y[1];
      primitives.add(new GAIGSline(xvals, yvals, color, lcolor, (i == numLines/2 ? label : ""), textHeight, lineWidth));
    }

  }

  /**
   * Adds a line to the primitive collection ending in an arrow
   * @param x Array of 2 containing the x coordinates for the start point and end point
   * @param y Array of 2 containing the y coordinates for the start point and end point
   * @param color The color of the line
   * @param lcolor The color of the text in the label
   * @param label The text to printed near the line
   * @param headSize The size of the arrow head
   * @param textHeight The Height of the text in the label
   * @param lineWidth The thickness of the line
  */
  //TODO make this a class
  public void addArrow(double x[], double y[], String color, String lcolor,
    String label, double headSize, double textHeight, int lineWidth)
  {
    primitives.add(new GAIGSline(x, y, color, lcolor, "", textHeight, lineWidth));

    double size = headSize;

    double [] x1 = {x[1], 0};
    double [] y1 = {y[1], 0};
    double [] x2 = {x[1], 0};
    double [] y2 = {y[1], 0};

    double theta = Math.atan((y[1] - y[0])/(x[1] - x[0]));
    double end1 = theta + Math.toRadians(30);
    double end2 = theta - Math.toRadians(30);

    x1[1] = x[1] - size * Math.cos(end1);
    x2[1] = x[1] - size * Math.cos(end2);
    y1[1] = y[1] - size * Math.sin(end1);
    y2[1] = y[1] - size * Math.sin(end2);

    double [] xvals = {x[1], x1[1], x2[1]};
    double [] yvals = {y[1], y1[1], y2[1]};

    primitives.add(new GAIGSpolygon(3, xvals, yvals, color, color, lcolor, label, textHeight, lineWidth));

  }

  /**
   * Adds an ellipse to the primitive collection.  Does not support a filled ellipse.
   * @param x The lower right hand x coordinate of the ellipse bounds
   * @param y The lower right hand y coordinate of the ellipse bounds
   * @param stAngle The starting angle in radians of the ellipse
   * @param endAngle The ending angle in radians of the ellipse
   * @param xR The radius value along the x axis
   * @param yR The radius value along the y axis
   * @param color The color of the outline of the ellipse
   * @param lcolor The color of the text in the label
   * @param label The text for the label to appear in the center of the ellipse
   * @param textHeight The Height of the text in the label
   * @param lineWidth The width of the outline of the circle
   */
  //TODO make this a constructor
  public void addEllipse(double x, double y, double stAngle, double endAngle, double xR,
    double yR, String color, String lcolor, String label, double textHeight, int lineWidth)
  {
    primitives.add(new GAIGSellipse(x,y,stAngle,endAngle,xR,yR,color,lcolor,label,textHeight, lineWidth));
  }

  /**
      * Adds a circle to the primitive collection
      * @param cx The center x coordinate of the circle
      * @param cy The center y coordinate of the circle
      * @param r The radius of the circle
      * @param fillColor The internal color of the circle (use an empty string for no fill color)
      * @param outlineColor The color of the circle outline
      * @param labelColor The color of the text in the circle label
      * @param labelText The text to be drawn in the center of the circle

  */
      public void addCircle(double cx, double cy, double r, String fillColor, String outlineColor,
                            String labelColor, String labelText)
  {
    primitives.add(new GAIGScircle(cx, cy, r, fillColor, outlineColor, labelColor, labelText, GAIGScircle.TEXT_HEIGHT, GAIGScircle.LINE_WIDTH));
  }

  /**
      * Adds a polygon to the primitive collection
      * @param nSides The number of sides to the polygon
      * @param ptsX Array containing the x coordinate values for the polygon
      * @param otsY Array containing the y coordinate values for the polygon
      * @param fillColor The internal color of the polygon (use an empty string for no fill color)
      * @param outlineColor The color of the circle polygon
      * @param labelColor The color of the text in the circle label
      * @param labelText The text to be drawn in the center of the circle

  */
    //TODO make this a constructor
      public void addPolygon(int nSides, double ptsX[], double ptsY[], String fillColor, String outlineColor,
                             String labelColor, String labelText)
  {
    primitives.add(new GAIGSpolygon(nSides, ptsX, ptsY, fillColor, outlineColor, labelColor, labelText, GAIGSpolygon.TEXT_HEIGHT, GAIGSpolygon.LINE_WIDTH));
  }

  /**
      * Adds a line to the primitive collection
      * @param x Array of 2 containing the x coordinates for the start point and end point
      * @param y Array of 2 containing the y coordinates for the start point and end point
      * @param color The color of the line
      * @param lcolor The color of the text in the label
      * @param label The text to printed near the line
  */
    //TODO make this a constructor
      public void addLine(double x[], double y[], String color, String lcolor, String label)
  {
    primitives.add(new GAIGSline(x, y, color,lcolor, label, GAIGSline.TEXT_HEIGHT, GAIGSline.LINE_WIDTH));
  }

  /**
      * Adds a line to the primitive collection
      * @param x Array of 2 containing the x coordinates for the start point and end point
      * @param y Array of 2 containing the y coordinates for the start point and end point
      * @param color The color of the line
      * @param lcolor The color of the text in the label
      * @param label The text to printed near the line
      * @param dashSize The length of the dash in the line

  */
    //TODO make this a constructor
      public void addDashedLine(double x[], double y[], String color, String lcolor,
                                String label, double dashSize)
  {

    double dist = Math.sqrt((x[1] - x[0])*(x[1] - x[0]) + (y[1] - y[0])*(y[1] - y[0]));
    double size = dashSize;

    int numLines = ((int)Math.ceil(dist / size)) / 2;

    double [] xvals = new double[2];
    double [] yvals = new double[2];

    for(int i = 0; i < numLines; i+=2) {
      xvals[0] = x[0] + (i / (2*(double)numLines)) * x[1];
      yvals[0] = y[0] + (i / (2*(double)numLines)) * y[1];
      xvals[1] = x[0] + ((i+1) / (2*(double)numLines)) * x[1];
      yvals[1] = y[0] + ((i+1) / (2*(double)numLines)) * y[1];
      primitives.add(new GAIGSline(xvals, yvals, color, lcolor, (i == numLines/2 ? label : ""), GAIGSline.TEXT_HEIGHT, GAIGSline.LINE_WIDTH));
    }

  }

  /**
      * Adds a line to the primitive collection ending in an arrow
      * @param x Array of 2 containing the x coordinates for the start point and end point
      * @param y Array of 2 containing the y coordinates for the start point and end point
      * @param color The color of the line
      * @param lcolor The color of the text in the label
      * @param label The text to printed near the line
      * @param headSize The size of the arrow head
  */
    //TODO make this a constructor
      public void addArrow(double x[], double y[], String color, String lcolor,
                           String label, double headSize)
  {
    primitives.add(new GAIGSline(x, y, color, lcolor, "", GAIGSline.TEXT_HEIGHT, GAIGSline.LINE_WIDTH));

    double size = headSize;

    double [] x1 = {x[1], 0};
    double [] y1 = {y[1], 0};
    double [] x2 = {x[1], 0};
    double [] y2 = {y[1], 0};

    double theta = Math.atan((y[1] - y[0])/(x[1] - x[0]));
    double end1 = theta + Math.toRadians(30);
    double end2 = theta - Math.toRadians(30);

    x1[1] = x[1] - size * Math.cos(end1);
    x2[1] = x[1] - size * Math.cos(end2);
    y1[1] = y[1] - size * Math.sin(end1);
    y2[1] = y[1] - size * Math.sin(end2);

    double [] xvals = {x[1], x1[1], x2[1]};
    double [] yvals = {y[1], y1[1], y2[1]};

    primitives.add(new GAIGSpolygon(3, xvals, yvals, color, color, lcolor, label, GAIGSpolygon.TEXT_HEIGHT,GAIGSpolygon.LINE_WIDTH));

  }

  /**
      * Adds an ellipse to the primitive collection.  Does not support a filled ellipse.
      * @param x The lower right hand x coordinate of the ellipse bounds
      * @param y The lower right hand y coordinate of the ellipse bounds
      * @param stAngle The starting angle in radians of the ellipse
      * @param endAngle The ending angle in radians of the ellipse
      * @param xR The radius value along the x axis
      * @param yR The radius value along the y axis
      * @param color The color of the outline of the ellipse
      * @param lcolor The color of the text in the label
      * @param label The text for the label to appear in the center of the ellipse
      * @param textHeight The Height of the text in the label
  */
    //TODO make this a constructor
      public void addEllipse(double x, double y, double stAngle, double endAngle, double xR,
                             double yR, String color, String lcolor, String label)
  {
    primitives.add(new GAIGSellipse(x,y,stAngle,endAngle,xR,yR,color,lcolor,label,GAIGSellipse.TEXT_HEIGHT,GAIGSellipse.LINE_WIDTH));
  }
}

