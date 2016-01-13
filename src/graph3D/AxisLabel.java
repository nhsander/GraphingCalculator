package graph3D;

import java.awt.geom.Line2D;
import java.math.BigDecimal;

public class AxisLabel {

	private final int X = 0;
	private final int Y = 1;
	private final int Z = 2;

	private Point3D point;
	private double label;

	public AxisLabel(int Axis, double number, Point3D minPoint, Point3D scale){

		label = number;
		double xPos = 0;
		double yPos = 0;
		double zPos = 0;
		
		if (minPoint.getX() > 0)
			xPos = minPoint.getX();
		if (minPoint.getY() > 0)
			yPos = minPoint.getY();
		if (minPoint.getZ() > 0)
			zPos = minPoint.getZ();
		

		if (Axis == X)
			point = new Point3D(number, yPos, zPos, scale);
		else if (Axis == Y)
			point = new Point3D(xPos, number, zPos, scale);
		else if (Axis == Z)
			point = new Point3D(xPos, yPos, number, scale);
		else throw new IllegalArgumentException("Axis must have a value of 0, 1, or 2, for x, y, and z, respectively");

	}

	public String getLabel(){

		BigDecimal value = BigDecimal.valueOf(label);
		value = value.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		return value.toString();
	}
	
	public int getLabelX(Line3D axis, double margin, Point3D scale){

		double xDraw = point.getX()*scale.getX() + margin;

		double axisX = axis.getStart().getX()-axis.getEnd().getX();
		double axisY = axis.getStart().getY()-axis.getEnd().getY();
		
		double xStep = Math.abs(20*(axisY)/Math.sqrt(Math.pow(axisX, 2) + Math.pow(axisY, 2)));

		if (axisX >= 0 && axisY >= 0)
			return (int) (xDraw + xStep) - 10;
		else if (axisX < 0 && axisY >= 0)
			return (int) (xDraw - xStep) - 20;
		else if (axisX >= 0 && axisY < 0)
			return (int) (xDraw - xStep) - 20;
		else if (axisX < 0 && axisY < 0)
			return (int) (xDraw + xStep) - 10;
		
		else return 0;
	}
	
	public int getLabelY(Line3D axis, double margin, Point3D scale, double windowHeight){

		double yDraw = windowHeight - (point.getY()*scale.getY() + margin);

		double axisX = axis.getStart().getX()-axis.getEnd().getX();
		double axisY = axis.getStart().getY()-axis.getEnd().getY();
		
		double yStep = Math.abs(20*(axisX)/Math.sqrt(Math.pow(axisX, 2) + Math.pow(axisY, 2)))/1.2;

		return (int) (yDraw + yStep);
	}

	public Line2D draw(Line3D axis, Point3D scale, Point3D center, double margin, double windowHeight){

		double xDraw = point.getX()*scale.getX() + margin;
		double yDraw = windowHeight - (point.getY()*scale.getY() + margin);

		double axisX = axis.getStart().getX()-axis.getEnd().getX();
		double axisY = axis.getStart().getY()-axis.getEnd().getY();
		
		double yStep = Math.abs(5*(axisX)/Math.sqrt(Math.pow(axisX, 2) + Math.pow(axisY, 2)));
		double xStep = Math.abs(5*(axisY)/Math.sqrt(Math.pow(axisX, 2) + Math.pow(axisY, 2)));

		if (axisX >= 0 && axisY >= 0)
			return new Line2D.Double(xDraw, yDraw, xDraw + xStep, yDraw + yStep);
		else if (axisX < 0 && axisY >= 0)
			return new Line2D.Double(xDraw, yDraw, xDraw - xStep, yDraw + yStep);
		else if (axisX >= 0 && axisY < 0)
			return new Line2D.Double(xDraw, yDraw, xDraw - xStep, yDraw + yStep);
		else if (axisX < 0 && axisY < 0)
			return new Line2D.Double(xDraw, yDraw, xDraw + xStep, yDraw + yStep);
		
		else return null;

	}

	public void rotate(double theta, int axis, Point3D center){
		point.rotate(theta, axis, center);
	}

	public void translate(double xTrans, double yTrans, double zTrans){
		point.translate(xTrans, yTrans, zTrans);
	}

}