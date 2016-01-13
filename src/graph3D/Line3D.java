package graph3D;

import java.awt.geom.Line2D;

public class Line3D {
	
	private Point3D start;
	private Point3D end;

	public Line3D(Point3D point1, Point3D point2) {
		start = point1;
		end = point2;
	}
	
	public Line3D(double x1, double y1, double z1, double x2, double y2, double z2, Point3D scale){
		start = new Point3D(x1, y1, z1, scale);
		end = new Point3D(x2, y2, z2, scale);
	}

	public void setStart(Point3D point){
		start = point;
	}

	public void setEnd(Point3D point){
		end = point;
	}
	
	public Point3D getStart(){
		return start;
	}
	
	public Point3D getEnd(){
		return end;
	}
	
	public void rotate(double theta, int axis, Point3D center){
		start.rotate(theta, axis, center);
		end.rotate(theta, axis, center);
	}
	
	public void translate(double x, double y, double z){
		start.translate(x, y, z);
		end.translate(x, y, z);
	}
	
	public Line2D plotLine3D(Point3D scale, double margin, double windowHeight){
		double xStart = start.getX()*scale.getX() + margin;
		double yStart = windowHeight - start.getY()*scale.getY() - margin;
		double xEnd = end.getX()*scale.getX() + margin;
		double yEnd = windowHeight - end.getY()*scale.getY() - margin;
		return new Line2D.Double(xStart, yStart, xEnd, yEnd);
	}

}
