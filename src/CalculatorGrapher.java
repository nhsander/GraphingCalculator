import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.lang.Math;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


//Team 3 Calculator Project
//Ryley Davis, Sunni Utt, Nathan Sanders

public class CalculatorGrapher extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	JFrame graphWindow;
	
	//Arrays of values
	private double[] xValues;
	private double[] yValues;
	
	//Max and min values used in calculations of coordinates and values
	private double xMax;
	private double xCoordMin;
	private double xCoordMax;
	private double yMax;
	private double yCoordMin;
	private double yMin;
	private double yCoordMax;
	
	//Margin of the window
	private int margin = 80;
	
	//Used in calculations
	private int windowWidth;
	private int windowHeight;
	
	//Used when calling parseForCalculation
	private String expression;
	
	//GUI stuff for mouse click window
	private JFrame pointWindow = new JFrame("Point");
	private JPanel pointPanel = new JPanel();
	private JLabel xValueLabel = new JLabel();
	private JLabel yValueLabel = new JLabel();
	
	public CalculatorGrapher(double[] x, double[] y, String exp){
		
		//Initialize things
		xValues = x;
		yValues = y;
		expression = exp;
		
		//Add panel to window
		graphWindow = new JFrame("Graph of y = " + expression);
		graphWindow.setSize(600, 600);
		graphWindow.setMinimumSize(new Dimension(300,300));
		graphWindow.add(this, BorderLayout.CENTER);
		
		//Start Gui
		graphWindow.addMouseListener(this);
		graphWindow.setVisible(true);
		
	}
	
	//Convert numerical y value to coordinate on window
	private int getYCoord(double value){
		return (int) (windowHeight - (value-yCoordMin)/(yCoordMax-yCoordMin)*(windowHeight-2*margin) - margin);
	}
	
	//Convert x coordinate on window to numerical value
	private double getXValue(int c){
		double coord = (double) c;
		return (coord-margin)/(windowWidth-2*margin)*(xCoordMax-xCoordMin)+xCoordMin;
	}
	
	//Convert nume rical x value to coordinate on window
	private int getXCoord(double value){
		return (int) ((windowWidth-2*margin)*(value-xCoordMin)/(xCoordMax-xCoordMin) + margin);
	}

	//Make array of y axis values
	private double[] getYScale() {
		Vector<Double> yScale = new Vector<Double>();
		int yMag;
		if (Math.log10((yMax-yMin)/2) >= 0)
			yMag = (int) Math.ceil(Math.log10((yMax-yMin)/2));
		else yMag = (int) Math.floor(Math.log10((yMax-yMin)/2));
		double yStep;
		if ((yMax-yMin)/2/Math.pow(10,yMag) > 5)
			yStep = Math.pow(10,yMag);
		else if ((yMax-yMin)/2/Math.pow(10,yMag) > 1)
			yStep = 2*Math.pow(10,yMag);
		else 
			yStep = Math.pow(10,yMag-1);

		double currentVal;
		
		yCoordMin = currentVal = Math.floor(yMin/Math.pow(10,yMag))*Math.pow(10,yMag);
		yCoordMax = Math.floor(yMax/Math.pow(10,yMag))*Math.pow(10,yMag);
		
		if (yCoordMin == yCoordMax){
			yCoordMin -= yStep;
			yCoordMax += yStep;
		}
		
		yScale.add(currentVal);
		while (currentVal <= yMax){
			currentVal += yStep;
			yScale.add(currentVal);
		}
		
		yCoordMax = currentVal;
		
		double[] yScaleDouble = new double[yScale.size()];
		
		for (int i = 0; i < yScale.size(); i++){
			yScaleDouble[i] = yScale.get(i).doubleValue();
		}
		
		return yScaleDouble;
	}
	
	//Make array of x axis values
	private double[] getXScale(){
		return xValues;
	}

	//Paint the graph
	public void paint(Graphics g){
		
		//Get the min and max values of x and y
		double[] xListCopy = new double[xValues.length];
		double[] yListCopy = new double[yValues.length];
		for (int i = 0; i < xListCopy.length; i++){
			xListCopy[i] = xValues[i];
			yListCopy[i] = yValues[i];
		}
		Arrays.sort(xListCopy);
		Arrays.sort(yListCopy);
		xMax = xListCopy[xListCopy.length-1];
		xCoordMin = xListCopy[0];
		xCoordMax = xMax;
		yMax = yListCopy[yListCopy.length-1];
		yMin = yListCopy[0];
		
		//Make X and Y scales
		double[] xAxisList = getXScale();
		double[] yAxisList = getYScale();		
		
		//Initialize window info
		windowHeight = graphWindow.getHeight()-40;
		windowWidth = graphWindow.getWidth();
		
		//Make 2d Graphics object
		Graphics2D g2 = (Graphics2D) g;
		
		//Write expression
		g2.drawString("y = " + expression, windowWidth/2, margin/2);
		
		//draw Axes
		g2.draw(new Line2D.Float(margin, windowHeight-margin, windowWidth-margin, windowHeight-margin));
		g2.draw(new Line2D.Float(margin, margin, margin, windowHeight-margin));
		
		g2.drawString("X Axis", windowWidth/2, windowHeight-margin/2);
		g2.drawString("y-Axis", 0, windowHeight/2);
		
		//Make String array with values of xAxis and yAxis
		String[] xValuesString = new String[xAxisList.length];
		String[] yValuesString = new String[yAxisList.length];
		for (int i = 0; i < xAxisList.length; i++){
			BigDecimal bigValue = new BigDecimal(xAxisList[i]);
			bigValue = bigValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			xValuesString[i] = bigValue.toString();
		}
		for (int i = 0; i < yAxisList.length; i++){
			BigDecimal bigValue = new BigDecimal(yAxisList[i]);
			bigValue = bigValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			yValuesString[i] = bigValue.toString();
		}
		
		//Make X and Y Tick Marks and draw values
		for (int i = 0; i < xAxisList.length; i++){
			g2.draw( new Line2D.Double(getXCoord(xAxisList[i]), windowHeight-margin+5, getXCoord(xAxisList[i]), windowHeight-margin));
			g2.drawString(xValuesString[i], getXCoord(xAxisList[i]), windowHeight-margin+20);
		}
		for (int i = 0; i < yAxisList.length; i++){
			g2.draw(new Line2D.Float(margin-5, getYCoord(yAxisList[i]), margin, getYCoord(yAxisList[i])));
			g2.drawString(yValuesString[i], margin/2, getYCoord(yAxisList[i]));
		}
		
		//Draw points
		g2.setStroke(new BasicStroke(5));
		for (int i = 0; i < xValues.length; i++){
			g2.draw(new Line2D.Float(getXCoord(xValues[i]),getYCoord(yValues[i]),getXCoord(xValues[i]),getYCoord(yValues[i])));
		}

		//Draw line for x=0 and y=0 if applicable
		g2.setStroke(new BasicStroke(1));
		g2.setPaint(Color.gray);
		if (yCoordMin < 0 && yCoordMax > 0){
			g2.draw( new Line2D.Double(getXCoord(xCoordMin), getYCoord(0), getXCoord(xCoordMax), getYCoord(0)));
		}
		if (xCoordMin < 0 && xCoordMax > 0){
			g2.draw( new Line2D.Double(getXCoord(0), getYCoord(yCoordMin), getXCoord(0), getYCoord(yCoordMax)));			
		}
			
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		showPointWindow(me);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		pointWindow.setVisible(false);
		
	}

	//Make and show window with point values
	private void showPointWindow(MouseEvent me) {
		
		//Get x and y values
		double xCoord = me.getPoint().getX();
		double x = getXValue((int)xCoord);
		double y = Calculator.parseForCalculation(expression, x,0);
		
		//Convert to shortened BigDecimal
		BigDecimal bigX = new BigDecimal(x);
		bigX = bigX.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bigY = new BigDecimal(y);
		bigY = bigY.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
		//get window position
		int xPos = (int)(me.getPoint().getX() + graphWindow.getLocation().getX());
		int yPos = (int)(me.getPoint().getY() + graphWindow.getLocation().getY());
		
		//Make Window
		pointPanel.setLayout(new GridLayout(1,2));
		xValueLabel.setText("x = " + bigX.toString());
		yValueLabel.setText("y = " + bigY.toString());
		pointPanel.add(xValueLabel);
		pointPanel.add(yValueLabel);
		pointWindow.add(pointPanel);
		pointWindow.setSize(new Dimension(100,75));
		pointWindow.setLocation(xPos, yPos);
		pointWindow.setVisible(true);
		
	}

}