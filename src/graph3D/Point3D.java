package graph3D;

import java.awt.geom.Line2D;

public class Point3D {
	
	private double x;
	private double y;
	private double z;
	
	private final int X = 0;
	private final int Y = 1;
	private final int Z = 2;

	public Point3D() {
		x = y = z = 0;
	}
	
	public Point3D(double xCoord, double yCoord, double zCoord, Point3D scale){
		x = xCoord/scale.getX();
		y = yCoord/scale.getY();
		z = zCoord/scale.getZ();
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public void translate(double xTrans, double yTrans, double zTrans){
		x += xTrans;
		y += yTrans;
		z += zTrans;
	}
	
	public void rotate(double theta, int axis, Point3D center){

		double[][] rotationMatrix = new double[3][3];
		
		if (axis == X) {
			rotationMatrix[0][0] = 1;
			rotationMatrix[0][1] = 0;
			rotationMatrix[0][2] = 0;
			rotationMatrix[1][0] = 0;
			rotationMatrix[1][1] = Math.cos(theta);
			rotationMatrix[1][2] = -Math.sin(theta);
			rotationMatrix[2][0] = 0;
			rotationMatrix[2][1] = Math.sin(theta);
			rotationMatrix[2][2] = Math.cos(theta);
		}
		else if (axis == Y) {
			rotationMatrix[0][0] = Math.cos(theta);
			rotationMatrix[0][1] = 0;
			rotationMatrix[0][2] = -Math.sin(theta);
			rotationMatrix[1][0] = 0;
			rotationMatrix[1][1] = 1;
			rotationMatrix[1][2] = 0;
			rotationMatrix[2][0] = Math.sin(theta);
			rotationMatrix[2][1] = 0;
			rotationMatrix[2][2] = Math.cos(theta);
		}
		else if (axis == Z) {
			rotationMatrix[0][0] = Math.cos(theta);
			rotationMatrix[0][1] = -Math.sin(theta);
			rotationMatrix[0][2] = 0;
			rotationMatrix[1][0] = Math.sin(theta);
			rotationMatrix[1][1] = Math.cos(theta);
			rotationMatrix[1][2] = 0;
			rotationMatrix[2][0] = 0;
			rotationMatrix[2][1] = 0;
			rotationMatrix[2][2] = 1;
		}
		
		x = x - center.getX();
		y = y - center.getY();
		z = z - center.getZ();

		double xRotated = x*rotationMatrix[0][0] + y*rotationMatrix[0][1] + z*rotationMatrix[0][2];
		double yRotated = x*rotationMatrix[1][0] + y*rotationMatrix[1][1] + z*rotationMatrix[1][2];
		double zRotated = x*rotationMatrix[2][0] + y*rotationMatrix[2][1] + z*rotationMatrix[2][2];
		
		
		
		x = xRotated + center.getX();
		y = yRotated + center.getY();
		z = zRotated + center.getZ();
		
	}
	
	public Line2D drawPoint(Point3D scale, double margin, double windowHeight){
		double xCoord = (x)*scale.getX() + margin;
		double yCoord = windowHeight - (y)*scale.getY() - margin;
		return new Line2D.Double(xCoord,yCoord,xCoord,yCoord);
	}	

}
