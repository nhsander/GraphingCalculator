import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import graph3D.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Grapher3D extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int X = 0;
	private final int Y = 1;
	private final int Z = 2;

	JFrame window = new JFrame();

	private Point3D[] points;
	private int numPoints;

	private double margin = 100;
	private int windowInitialSize = 600;

	private double windowWidth;
	private double windowHeight;
	private double xMax;
	private double xMin;
	private double yMax;
	private double yMin;
	private double zMax;
	private double zMin;
	private Point3D minPoint = new Point3D();
	private Point3D scale = new Point3D();
	private Point3D valueScale = new Point3D();
	private Point3D center = new Point3D();
	private AxisLabel[] xAxisLabels;
	private AxisLabel[] yAxisLabels;
	private AxisLabel[] zAxisLabels;

	private double lastXRotate = -1;
	private double lastYRotate = -1;
	private double rotateStep = 0.1;
	private double lastXTranslate = -1;
	private double lastYTranslate = -1;

	private Line3D[] axes = new Line3D[3];

	public Grapher3D(double[] xCoords, double[] yCoords, double[] zCoords, String expression) {
		if (xCoords.length != yCoords.length || xCoords.length != zCoords.length || yCoords.length != zCoords.length)
			throw new IllegalArgumentException("Must have the same number of x, y, and z coordinates.");

		numPoints = xCoords.length;
		
		double currentXMax = xCoords[0];
		double currentXMin = xCoords[0];
		double currentYMax = yCoords[0];
		double currentYMin = yCoords[0];
		double currentZMax = zCoords[0];
		double currentZMin = zCoords[0];
		for (int i = 1; i < numPoints; i++){
			if (xCoords[i] > currentXMax)
				currentXMax = xCoords[i];
			if (xCoords[i] < currentXMin)
				currentXMin = xCoords[i];
			if (yCoords[i] > currentYMax)
				currentYMax = yCoords[i];
			if (yCoords[i] < currentYMin)
				currentYMin = yCoords[i];
			if (zCoords[i] > currentZMax)
				currentZMax = zCoords[i];
			if (zCoords[i] < currentZMin)
				currentZMin = zCoords[i];
		}

		xMax = currentXMax;
		xMin = currentXMin;
		yMax = currentYMax;
		yMin = currentYMin;
		zMax = currentZMax;
		zMin = currentZMin;
		

		valueScale.translate(xMax-xMin, yMax-yMin, zMax-zMin);
		center.translate((xMax+xMin)/2.0/valueScale.getX(), (yMax+yMin)/2.0/valueScale.getY(), (zMax+zMin)/2.0/valueScale.getZ());
		minPoint.translate(xMin/valueScale.getX(), yMin/valueScale.getY(), zMin/valueScale.getZ());
		scale.translate((windowWidth-2*margin)/(xMax*Math.sqrt(2)-xMin)*valueScale.getX(), (windowWidth-2*margin)/(yMax*Math.sqrt(2)-yMin)*valueScale.getY(), (windowWidth-2*margin)/(zMax*Math.sqrt(2)-zMin)*valueScale.getZ());
		
		points = new Point3D[numPoints];
		for (int i = 0; i < numPoints; i++){
			points[i] = new Point3D(xCoords[i], yCoords[i], zCoords[i], valueScale);
		}

		window.setSize(windowInitialSize,windowInitialSize);
		window.add(this);
		window.setVisible(true);
		window.addMouseListener(this);
		window.addMouseMotionListener(this);
		window.addMouseWheelListener(this);

		windowWidth = window.getWidth();
		windowHeight = window.getHeight();
		if (xMin >= 0 && yMin >= 0 && zMin >= 0){
			axes[X] = new Line3D(xMin, yMin, zMin, xMax*Math.sqrt(2), yMin, zMin, valueScale);
			axes[Y] = new Line3D(xMin, yMin, zMin, xMin, yMax*Math.sqrt(2), zMin, valueScale);
			axes[Z] = new Line3D(xMin, yMin, zMin, xMin, yMin, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin < 0 && yMin >= 0 && zMin >= 0){
			axes[X] = new Line3D(xMin*Math.sqrt(2), yMin, zMin, xMax*Math.sqrt(2), yMin, zMin, valueScale);
			axes[Y] = new Line3D(0, yMin, zMin, 0, yMax*Math.sqrt(2), zMin, valueScale);
			axes[Z] = new Line3D(0, yMin, zMin, 0, yMin, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin >= 0 && yMin < 0 && zMin >= 0){
			axes[X] = new Line3D(xMin, 0, zMin, xMax*Math.sqrt(2), 0, zMin, valueScale);
			axes[Y] = new Line3D(xMin, yMin*Math.sqrt(2), zMin, xMin, yMax*Math.sqrt(2), zMin, valueScale);
			axes[Z] = new Line3D(xMin, 0, zMin, xMin, 0, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin >= 0 && yMin >= 0 && zMin < 0){
			axes[X] = new Line3D(xMin, yMin, 0, xMax*Math.sqrt(2), yMin, 0, valueScale);
			axes[Y] = new Line3D(xMin, yMin, 0, xMin, yMax*Math.sqrt(2), 0, valueScale);
			axes[Z] = new Line3D(xMin, yMin, zMin*Math.sqrt(2), xMin, yMin, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin < 0 && yMin < 0 && zMin >= 0){
			axes[X] = new Line3D(xMin*Math.sqrt(2), 0, zMin, xMax*Math.sqrt(2), 0, zMin, valueScale);
			axes[Y] = new Line3D(0, yMin*Math.sqrt(2), zMin, 0, yMax*Math.sqrt(2), zMin, valueScale);
			axes[Z] = new Line3D(0, 0, zMin, 0, 0, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin >= 0 && yMin < 0 && zMin < 0){
			axes[X] = new Line3D(xMin, 0, 0, xMax*Math.sqrt(2), 0, 0, valueScale);
			axes[Y] = new Line3D(xMin, yMin*Math.sqrt(2), 0, xMin, yMax*Math.sqrt(2), 0, valueScale);
			axes[Z] = new Line3D(xMin, 0, zMin*Math.sqrt(2), xMin, 0, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin < 0 && yMin >= 0 && zMin < 0){
			axes[X] = new Line3D(xMin*Math.sqrt(2), yMin, 0, xMax*Math.sqrt(2), yMax, 0, valueScale);
			axes[Y] = new Line3D(0, yMin, 0, 0, yMax*Math.sqrt(2), 0, valueScale);
			axes[Z] = new Line3D(0, yMin, zMin*Math.sqrt(2), 0, yMin, zMax*Math.sqrt(2), valueScale);
		}
		else if (xMin < 0 && yMin < 0 && zMin < 0){
			axes[X] = new Line3D(xMin*Math.sqrt(2), 0, 0, xMax*Math.sqrt(2), 0, 0, valueScale);
			axes[Y] = new Line3D(0, yMin*Math.sqrt(2), 0, 0, yMax*Math.sqrt(2), 0, valueScale);
			axes[Z] = new Line3D(0, 0, zMin*Math.sqrt(2), 0, 0, zMax*Math.sqrt(2), valueScale);
		}
		
		double[] zScale = getZScale();
		
		xAxisLabels = new AxisLabel[numPoints];
		yAxisLabels = new AxisLabel[numPoints];
		zAxisLabels = new AxisLabel[11];
		
		for (int i = 0; i < numPoints; i++){
			xAxisLabels[i] = new AxisLabel(X, points[i].getX()*valueScale.getX(), minPoint, valueScale);
			yAxisLabels[i] = new AxisLabel(Y, points[i].getY()*valueScale.getY(), minPoint, valueScale);
		}
		for (int i = 0; i < 11; i++)
			zAxisLabels[i] = new AxisLabel(Z, zScale[i], minPoint, valueScale);
		
		rotateAll(Y, 18);
		rotateAll(Z, -15);
		rotateAll(X, 3);
	
	}



	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		
		double currentXMax = points[0].getX();
		double currentXMin = points[0].getX();
		double currentYMax = points[0].getY();
		double currentYMin = points[0].getY();
		double currentZMax = points[0].getZ();
		double currentZMin = points[0].getZ();
		for (int i = 1; i < numPoints; i++){
			if (points[i].getX() > currentXMax)
				currentXMax = points[i].getX();
			if (points[i].getX() < currentXMin)
				currentXMin = points[i].getX();
			if (points[i].getY() > currentYMax)
				currentYMax = points[i].getY();
			if (points[i].getY() < currentYMin)
				currentYMin = points[i].getY();
			if (points[i].getZ() > currentZMax)
				currentZMax = points[i].getZ();
			if (points[i].getZ() < currentZMin)
				currentZMin = points[i].getZ();
		}
		
		windowHeight = window.getHeight();

		xMax = currentXMax;
		xMin = currentXMin;
		yMax = currentYMax;
		yMin = currentYMin;
		zMax = currentZMax;
		zMin = currentZMin;

		for (int i = 0; i < 3; i++)
			g2.draw(axes[i].plotLine3D(scale, margin, windowHeight));
		for (int i = 0; i < xAxisLabels.length; i++){
			g2.draw(xAxisLabels[i].draw(axes[X], scale, center, margin, windowHeight));
			g2.drawString(xAxisLabels[i].getLabel(), xAxisLabels[i].getLabelX(axes[X], margin, scale), xAxisLabels[i].getLabelY(axes[X], margin, scale, windowHeight));
		}
		for (int i = 0; i < yAxisLabels.length; i++){
			g2.draw(yAxisLabels[i].draw(axes[Y], scale, center, margin, windowHeight));
			g2.drawString(yAxisLabels[i].getLabel(), yAxisLabels[i].getLabelX(axes[Y], margin, scale), yAxisLabels[i].getLabelY(axes[Y], margin, scale, windowHeight));
		}
		for (int i = 0; i < zAxisLabels.length; i++){
			g2.draw(zAxisLabels[i].draw(axes[Z], scale, center, margin, windowHeight));
			g2.drawString(zAxisLabels[i].getLabel(), zAxisLabels[i].getLabelX(axes[Z], margin, scale), zAxisLabels[i].getLabelY(axes[Z], margin, scale, windowHeight));
		}
		g2.setStroke(new BasicStroke(5));
		for (int i = 0; i < numPoints; i++){
			g2.draw(points[i].drawPoint(scale, margin, windowHeight));
		}
		
		int[][] labelPosition = getlabelPosition();
			
		g2.drawString("X", labelPosition[X][X], (int) (windowHeight - labelPosition[X][Y]));
		g2.drawString("Y", labelPosition[Y][X], (int) (windowHeight - labelPosition[Y][Y]));
		g2.drawString("Z", labelPosition[Z][X], (int) (windowHeight - labelPosition[Z][Y]));

	}
	
	private double[] getZScale() {
		double[] zScale = new double[11];
		double zMinCurrent = zMin;
		while (zMinCurrent/10 > 1)
			zMinCurrent = zMinCurrent - 10;
		double zScaleMin = (int)zMinCurrent;
		double zScaleMax = zMax;
		if (zMax > 10)
			zScaleMax = zMax + (10-(int)zMax%10);
		double zStep = (zScaleMax-zScaleMin)/10;
		if (zScaleMax-zScaleMin > 100)
			zStep = 10* (int)Math.ceil(zStep/10);
		for (int i = 0; i < 11; i++){
			zScale[i] = zScaleMin + i*zStep;
		}
		return zScale;
	}
	
	private int[][] getlabelPosition(){

		int xX;
		int xY;
		int yX;
		int yY;
		int zX;
		int zY;
		
		xX = (int) (axes[X].getEnd().getX()*scale.getX() + margin);
		xY = (int) (axes[X].getEnd().getY()*scale.getY() + margin);
		yX = (int) (axes[Y].getEnd().getX()*scale.getX() + margin);
		yY = (int) (axes[Y].getEnd().getY()*scale.getY() + margin);
		zX = (int) (axes[Z].getEnd().getX()*scale.getX() + margin);
		zY = (int) (axes[Z].getEnd().getY()*scale.getY() + margin);
		
		if (axes[X].getEnd().getX() > center.getX())
			xX =  xX + 10;
		else xX = xX - 10;
		if (axes[X].getEnd().getY() > center.getY())
			xY = xY + 10;
		else xY = xY - 10;
		if (axes[Y].getEnd().getX() > center.getX())
			yX = yX + 10;
		else yX = yX - 10;
		if (axes[Y].getEnd().getY() > center.getY())
			yY = yY + 10;
		else yY = yY - 10;
		if (axes[Z].getEnd().getX() > center.getX())
			zX = zX + 10;
		else zX = zX - 10;
		if (axes[Z].getEnd().getY() > center.getY())
			zY = zY + 10;
		else zY = zY - 10;
		
		
		int[][] positions = {{(int)xX,(int)xY},{(int)yX,(int)yY},{(int)zX,(int)zY}};
		
		return positions;
		
	}

	private void rotateAll(int axis, int direction){
		for (int i = 0; i < numPoints; i++)
			points[i].rotate(rotateStep*direction, axis, center);
		for (int i = 0; i < 3; i++)
			axes[i].rotate(rotateStep*direction, axis, center);
		for (int i = 0; i < xAxisLabels.length; i++)
			xAxisLabels[i].rotate(rotateStep*direction, axis, center);
		for (int i = 0; i < yAxisLabels.length; i++)
			yAxisLabels[i].rotate(rotateStep*direction, axis, center);
		for (int i = 0; i < zAxisLabels.length; i++)
			zAxisLabels[i].rotate(rotateStep*direction, axis, center);

		repaint();
	}

	private void translateAll(double xTranslate, double yTranslate){
		for (int i = 0; i < numPoints; i++)
			points[i].translate(xTranslate/scale.getX(), yTranslate/scale.getY(), 0);
		for (int i = 0; i < 3; i++)
			axes[i].translate(xTranslate/scale.getX(), yTranslate/scale.getY(), 0);
		center.translate(xTranslate/scale.getX(), yTranslate/scale.getY(), 0);
		for (int i = 0; i < xAxisLabels.length; i++)
			xAxisLabels[i].translate(xTranslate/scale.getX(), yTranslate/scale.getY(), 0);
		for (int i = 0; i < yAxisLabels.length; i++)
			yAxisLabels[i].translate(xTranslate/scale.getX(), yTranslate/scale.getY(), 0);
		for (int i = 0; i < zAxisLabels.length; i++)
			zAxisLabels[i].translate(xTranslate/scale.getX(), yTranslate/scale.getY(), 0);
		
		repaint();
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseReleased(MouseEvent e) {

		lastXTranslate = lastYTranslate = -1;

	}



	@Override
	public void mouseDragged(MouseEvent e) {

		double xDrag = e.getX();
		double yDrag = e.getY();

		if (SwingUtilities.isRightMouseButton(e)){

			if (lastXRotate != -1){
				if (Math.abs(xDrag-lastXRotate) >= Math.abs(yDrag - lastYRotate)){
					if (xDrag > lastXRotate)
						rotateAll(Y, -1);
					else rotateAll(Y, 1);
				}
				else 
					if (yDrag > lastYRotate)
						rotateAll(X, 1);
					else rotateAll(X, -1);
			}

			lastXRotate = xDrag;
			lastYRotate = yDrag;

		}

		if (SwingUtilities.isLeftMouseButton(e)){

			if (lastXTranslate != -1){
				translateAll(xDrag - lastXTranslate, -yDrag + lastYTranslate);
			}

			lastXTranslate = xDrag;
			lastYTranslate = yDrag;

		}


	}



	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double zoomAmount = e.getPreciseWheelRotation();
		scale.translate(-zoomAmount*scale.getX()/10, -zoomAmount*scale.getY()/10, -zoomAmount*scale.getX()/10);
		repaint();
	}



	public static void main(String[] args) {

		double[] xc = {0,1,2,3};
		double[] yc = {0,1,2,3};
		double[] zc = {0,10,20,30};

		new Grapher3D(xc,yc,zc,"a");
	}

}