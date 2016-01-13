import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//Team 3 Calculator Project
//Ryley Davis, Sunni Utt, Nathan Sanders

//Project Start Date - 10/15/12
//	Began Basic Calculator Function (Arithmetic Expression Evaluation)
//  Began Base for Calculator GUI
//
//

public class Calculator implements ActionListener 
{
	final static int ACC_MODE = 0;
	final static int CAL_MODE = 1;
	final static int GRP_MODE = 2;
	final static int GRP3D_MODE = 3;
	private static int mode = -1;
	private boolean isCashMode = false;
	private double sum;
	private static Vector<String> resultList;
	private static JList<String> resultListDisplay;
	private static ArrayList<String> logList = new ArrayList<String>();
	private static ArrayList<String> graphLogList = new ArrayList<String>();
	private static ArrayList<String> graph3DLogList = new ArrayList<String>();

	JMenu helpMenu;
	JMenuItem helpItem;
	JDialog helpWindow;
	JRadioButtonMenuItem accMode;
	JRadioButtonMenuItem calMode;
	JRadioButtonMenuItem grpMode;
	JRadioButtonMenuItem grp3DMode;
	JCheckBoxMenuItem cashMode;
	JButton deleteButton;
	JButton clearButton;
	JButton recallButton;
	static JTextField inputField = new JTextField("");
	JLabel xInputLabel;
	static JTextField xInput;
	JLabel xMaxLabel;
	JTextField xMaxInput;
	JLabel xStepLabel;
	JTextField xStepInput;
	JLabel yInputLabel;
	JTextField yInput;
	JLabel yMaxLabel;
	JTextField yMaxInput;
	JLabel yStepLabel;
	JTextField yStepInput;


	JLabel instruction;

	public Calculator() 	
	{

		System.out.println("Names: Sunni Utt, Ryley Davis, Nathan Sanders");

		//GUI Elements - Main Window
		JFrame calculatorWindow  = new JFrame("Team 3 Calculator");

		//Help Window
		helpWindow = new JDialog();
		
		//Panels
		JPanel buttonPanel		 = new JPanel();
		JPanel southPanel 		 = new JPanel();
		JPanel inputPanel		 = new JPanel();
		JPanel northPanel		 = new JPanel();
		JPanel xPanel			 = new JPanel();
		JPanel yPanel			 = new JPanel();
		JPanel helpPanel		 = new JPanel();
		JTextArea helpText		 = new JTextArea();
		buttonPanel.setLayout(new GridLayout(1,3));
		southPanel.setLayout(new GridLayout(2,1));
		inputPanel.setLayout(new GridLayout(1,2));
		xPanel.setLayout(new GridLayout(1,5));
		yPanel.setLayout(new GridLayout(1,5));
		//North Panel
		instruction 	 = new JLabel("");
		//South Panel
		deleteButton 	 = new JButton("Delete");
		clearButton 	 = new JButton("Clear");
		recallButton 	 = new JButton("Recall");
		//Input and Error Messages
		xInputLabel		 = new JLabel("for x = ");
		xInput			 = new JTextField();
		xMaxLabel		 = new JLabel("to x = ");
		xMaxInput		 = new JTextField();
		xStepLabel		 = new JLabel("x step = ");
		xStepInput		 = new JTextField();
		yInputLabel		 = new JLabel("for y = ");
		yInput			 = new JTextField();
		yMaxLabel		 = new JLabel("to y = ");
		yMaxInput		 = new JTextField();
		yStepLabel		 = new JLabel("y step = ");
		yStepInput		 = new JTextField();
		//Result Panel
		resultList = new Vector<String>();
		resultListDisplay = new JList<String>(resultList);
		JScrollPane resultPanel	 = new JScrollPane(resultListDisplay);
		//Mode Selection Area
		JMenuBar menuBar		 = new JMenuBar();
		JMenu modeMenu 			 = new JMenu("Mode");
		helpMenu			 = new JMenu("Help");
		helpItem		   	 = new JMenuItem("Mode Help");
		ButtonGroup modeButtonGroup = new ButtonGroup();
		accMode		 = new JRadioButtonMenuItem("Accumulator");
		calMode		 = new JRadioButtonMenuItem("Calculator");
		grpMode		 = new JRadioButtonMenuItem("Grapher");
		grp3DMode	 = new JRadioButtonMenuItem("Grapher 3D");
		cashMode	 = new JCheckBoxMenuItem("Cash");



		//Generate GUI
		Container pane = calculatorWindow.getContentPane();
		//Build Layout

		helpPanel.setLayout(new BorderLayout());
		helpPanel.add(helpText, BorderLayout.CENTER);
		
		pane.setLayout(new BorderLayout());
		buttonPanel.add(deleteButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(recallButton);
		inputPanel.add(inputField);
		xPanel.add(xInputLabel);
		xPanel.add(xInput);
		xPanel.add(xMaxLabel);
		xPanel.add(xMaxInput);
		xPanel.add(xStepLabel);
		xPanel.add(xStepInput);
		yPanel.add(yInputLabel);
		yPanel.add(yInput);
		yPanel.add(yMaxLabel);
		yPanel.add(yMaxInput);
		yPanel.add(yStepLabel);
		yPanel.add(yStepInput);
		inputPanel.add(xPanel);
		inputPanel.add(yPanel);
		southPanel.add(inputPanel);
		southPanel.add(buttonPanel);
		northPanel.add(instruction);
		pane.add(northPanel, BorderLayout.NORTH);
		pane.add(resultPanel, BorderLayout.CENTER);
		pane.add(southPanel, BorderLayout.SOUTH);
		helpWindow.getContentPane().add(helpPanel);

		//Add Menu Bar
		helpMenu.add(helpItem);
		modeMenu.add(accMode);
		modeMenu.add(calMode);
		modeMenu.add(grpMode);
		modeMenu.add(grp3DMode);
		modeMenu.add(cashMode);
		menuBar.add(modeMenu);
		menuBar.add(helpMenu);
		calculatorWindow.setJMenuBar(menuBar);

		//Set GUI Settings
		modeButtonGroup.add(accMode);
		modeButtonGroup.add(calMode);
		modeButtonGroup.add(grpMode);
		modeButtonGroup.add(grp3DMode);
		inputField.setPreferredSize(new Dimension(400,50));
		xInputLabel.setHorizontalAlignment(JLabel.RIGHT);
		xMaxLabel.setHorizontalAlignment(JLabel.RIGHT);
		xStepLabel.setHorizontalAlignment(JLabel.RIGHT);
		yInputLabel.setHorizontalAlignment(JLabel.RIGHT);
		yMaxLabel.setHorizontalAlignment(JLabel.RIGHT);
		yStepLabel.setHorizontalAlignment(JLabel.RIGHT);
		helpWindow.setSize(450,460);
		helpWindow.setLocation(100,150);
		helpWindow.setTitle("Mode Descriptions");
		calculatorWindow.setSize(900,600);
		calculatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calculatorWindow.setVisible(true);
		
		helpText.setEditable(false);
		helpText.setText("Accumulator Mode:\n\t Use to sequentially add or subtract values. " +
				"\n\t Growing Result shown in Log " +
				"\n\t Enable Cash Mode to force Cash Format" +
				"\n\nCalculator Mode:" +
				"\n\t Expressions according to PEMDAS \n\t" +
				" Built-In Values -> pi,e,x" +
				"\n\t Built-In Functions -> cos, sin, abs" +
				"\n\t No Unary Operators for functions or Parenthesis" +
				"\n\nGrapher" +
				"\n\t 2D Scatter Plot Grapher" +
				"\n\t Same expression rules as Calculator" +
				"\n\t Maximum Twenty Points -> Positive Step" +
				"\n\t Expression Must have X" +
				"\n\nGrapher3D" +
				"\n\t 3D Scatter Plot Grapher" +
				"\n\t Same Expression rules as Grapher" +
				"\n\t Maximum 80 Points in each direction (Total Max of 6400 points)" +
				"\n\t Expression Must have X and Y" +
				"\n\t Controls:" +
				"\n\t\t Left Click -> Drag" +
				"\n\t\t Right Click -> Rotate" +
				"\n\t\t Mouse Wheel -> Zoom");

		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		recallButton.addActionListener(this);
		inputField.addActionListener(this);
		xInput.addActionListener(this);
		xMaxInput.addActionListener(this);
		xStepInput.addActionListener(this);
		yInput.addActionListener(this);
		yMaxInput.addActionListener(this);
		yStepInput.addActionListener(this);
		accMode.addActionListener(this);
		calMode.addActionListener(this);
		grpMode.addActionListener(this);
		grp3DMode.addActionListener(this);
		accMode.setSelected(true);
		cashMode.addActionListener(this);
		helpItem.addActionListener(this);


		Date date = new Date();
		log("Started at "+date.toString());
		setMode(ACC_MODE);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// Change modes
		if (ae.getSource() == accMode)
			setMode(ACC_MODE);
		if (ae.getSource() == calMode)
			setMode(CAL_MODE);
		if (ae.getSource() == grpMode)
			setMode(GRP_MODE);
		if (ae.getSource() == grp3DMode)
			setMode(GRP3D_MODE);

		if (ae.getSource() == cashMode)
			isCashMode = cashMode.isSelected();

		//  Clear Button processing
		if (ae.getSource() == clearButton)
		{
			sum = 0;
			inputField.setText("");
			xInput.setText("");
			xMaxInput.setText("");
			xStepInput.setText("");
			yInput.setText("");
			yMaxInput.setText("");
			yStepInput.setText("");
		}

		//  Delete Button processing
		if (ae.getSource() == deleteButton){
			try{
				inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
			}
			//Catches exception if trying to delete when nothing is typed
			catch(java.lang.StringIndexOutOfBoundsException e){}
		}

		//Recall Button Processing
		if(ae.getSource() == recallButton)
		{
			if(mode==CAL_MODE){
				if(logList.isEmpty()){
					logError("No expressions have been evaluated yet");
				}
				else{
					inputField.setText(logList.get(logList.size()-3));
					xInput.setText(logList.get(logList.size()-1));
				}
			}
			else if (mode==GRP_MODE){
				if(graphLogList.isEmpty()){
					logError("No expressions have been graphed yet");
				}
				else{
					inputField.setText(graphLogList.get(graphLogList.size()-4));
					xInput.setText(graphLogList.get(graphLogList.size()-3));
					xMaxInput.setText(graphLogList.get(graphLogList.size()-2));
					xStepInput.setText(graphLogList.get(graphLogList.size()-1));
				}
			}
			else{
				if(graph3DLogList.isEmpty()){
					logError("No expressions have been graphed yet");
				}
				else{
					inputField.setText(graph3DLogList.get(graph3DLogList.size()-7));
					xInput.setText(graph3DLogList.get(graph3DLogList.size()-6));
					xMaxInput.setText(graph3DLogList.get(graph3DLogList.size()-5));
					xStepInput.setText(graph3DLogList.get(graph3DLogList.size()-4));
					yInput.setText(graph3DLogList.get(graph3DLogList.size()-3));
					yMaxInput.setText(graph3DLogList.get(graph3DLogList.size()-2));
					yStepInput.setText(graph3DLogList.get(graph3DLogList.size()-1));
				}
			}
			resultListDisplay.ensureIndexIsVisible(resultList.size()-1);
		}

		//  Enter Button processing
		if (ae.getSource() == inputField || ae.getSource() == xInput || ae.getSource() == xMaxInput || ae.getSource() == xStepInput || ae.getSource() == yInput || ae.getSource() == yMaxInput || ae.getSource() == yStepInput)
		{
			if(mode == ACC_MODE)
			{
				String entry = inputField.getText().replace(" ", "");
				try
				{
					double value = Double.parseDouble(entry);
					if(cashCheck(entry))
						accum(value);
					else
						logError("Not is Cash format. Please provide cash input.");
					inputField.setText("");
				}
				catch (Exception e)
				{
					logError("Not a number");
				}
			}

			if(mode == CAL_MODE)
			{
				String entry = inputField.getText().trim();
				try{
					double stuff = parseCalX(xInput.getText().trim(), inputField.getText().trim());
					double result = parseForCalculation(entry, stuff, 0);
						logList.add(entry);
						logList.add(String.valueOf(result));

						if(!entry.contains("x")){
							logList.add("");
							log(entry + " = " + logList.get(logList.size()-2) + "; No X Value");
				
						}
						else{
							logList.add(String.valueOf(stuff));
							log(entry + " = " + logList.get(logList.size()-2) + " for X = " + stuff);
							
						}

						inputField.setText("");
						xInput.setText("");
				}
				catch(IllegalArgumentException iae){}
			}
			if(mode == GRP_MODE){
				String entry = inputField.getText().trim();
				try{
					String expression = inputField.getText().trim();
					Vector<Double> stuff = parseGraphX(xInput.getText().trim(),xMaxInput.getText().trim(),xStepInput.getText().trim(), expression);
					double[] xVals = new double[stuff.size()];
					double[] yVals = new double[stuff.size()];
					for(int i = 0; i < stuff.size(); i++){
						xVals[i] = stuff.get(i).doubleValue();
						yVals[i] = parseForCalculation(entry, xVals[i],0);
					}
					new CalculatorGrapher(xVals, yVals, expression);
					xInput.setText("");
					inputField.setText("");
					xMaxInput.setText("");
					xStepInput.setText("");
					yInput.setText("");
					yMaxInput.setText("");
					yStepInput.setText("");
				}
				catch(IllegalArgumentException iae){}
			}
			if(mode == GRP3D_MODE){
				try{
					String expression = inputField.getText().trim();
					Vector<Double> xStuff = parseGraphX(xInput.getText().trim(),xMaxInput.getText().trim(),xStepInput.getText().trim(), expression);
					Vector<Double> yStuff = parseGraphY(yInput.getText().trim(),yMaxInput.getText().trim(),yStepInput.getText().trim(), expression);
					log("Now graphing " + expression + " in the range X = " + xInput.getText().trim() + ":" + xStepInput.getText().trim() + ":" + xMaxInput.getText().trim() + " and Y = " + yInput.getText().trim() + ":" + yStepInput.getText().trim() + ":" + yMaxInput.getText().trim());
					double[] xVals = new double[xStuff.size()*yStuff.size()];
					double[] yVals = new double[xStuff.size()*yStuff.size()];
					double[] zVals = new double[xStuff.size()*yStuff.size()];
					for(int i = 0; i < xStuff.size(); i++){
						for (int j = 0; j < yStuff.size(); j++){
							xVals[j+i*yStuff.size()] = xStuff.get(i).doubleValue();
							yVals[j+i*yStuff.size()] = yStuff.get(j).doubleValue();
							zVals[j+i*yStuff.size()] = parseForCalculation(expression, xStuff.get(i).doubleValue(), yStuff.get(j).doubleValue());
						}
					}
					new Grapher3D(xVals, yVals, zVals, expression);
					xInput.setText("");
					inputField.setText("");
					xMaxInput.setText("");
					xStepInput.setText("");
					yInput.setText("");
					yMaxInput.setText("");
					yStepInput.setText("");
				}
				catch(IllegalArgumentException iae){}
			}
			resultListDisplay.ensureIndexIsVisible(resultList.size()-1);
		}
		if(ae.getSource() == helpItem){
			helpWindow.setVisible(true);
		}
	}

	private void accum(double value) 
	{
		BigDecimal bigValue = new BigDecimal(value);
		BigDecimal bigSum = new BigDecimal(sum);
		String op = value < 0 ? "":"+";
		if(isCashMode)
			log(NumberFormat.getCurrencyInstance().format(bigSum)+op+
					NumberFormat.getCurrencyInstance().format(bigValue)+"="+
					NumberFormat.getCurrencyInstance().format(bigValue.add(bigSum)));
		else
			log(Double.toString(sum)+op+Double.toString(value)+"="+Double.toString(sum+value));
		sum += value;	
	}

	private static boolean isOp(char c){

		if(c=='+'||c=='-'|| c=='/'||c=='*'||c=='^'||c=='R'||c=='r') return true;
		return false;
	}

	//Takes x value and function and checks for errors
	private double parseCalX(String xString, String function)
	{
		Double xDouble = 0.0;
		try
		{	
			if(function.contains("x") || function.contains("X")){
				if(xString.isEmpty()) throw new NullPointerException();
				xDouble = Double.parseDouble(xString);
			}
			return xDouble;
		}
		catch(NullPointerException npE)
		{
			logError("Please enter an X value");
			throw new IllegalArgumentException();
		}
		catch (NumberFormatException nfe)
		{
			if(xString.equalsIgnoreCase("e"))
				return Math.E;
			if(xString.equalsIgnoreCase("pi"))
				return Math.PI;
			if(xString.equalsIgnoreCase("-e"))
				return -Math.E;
			if(xString.equalsIgnoreCase("-pi"))
				return -Math.PI; 
			logError("Invalid X Value");
			throw new IllegalArgumentException();
		}
	}

	private Vector<Double> parseGraphY(String fromY, String toY, String stepY, String function)
	{
		double dFrom;
		double dTo;
		double dStep;
		Vector<Double> yArray = new Vector<Double>();
		if(function.contains("y") || function.contains("Y"))
		{
			try{dFrom = Double.parseDouble(fromY);}
			catch (Exception e)
			{
				logError("Please enter a valid 'from Y' value.");
				throw new IllegalArgumentException();
			}
			try{dTo = Double.parseDouble(toY);}
			catch (Exception e)
			{
				logError("Please enter a valid 'to Y' value.");
				throw new IllegalArgumentException();
			}
			try{dStep = Double.parseDouble(stepY);}
			catch (Exception e)
			{
				logError("Please enter a valid 'Y step' value.");
				throw new IllegalArgumentException();
			}
			if(dFrom >= dTo)
			{
				logError("'from Y' value is greater than or equal to 'to Y' value");
				throw new IllegalArgumentException();
			}
			if(dStep <= 0){
				logError("'Y step' must be a positive value");
				throw new IllegalArgumentException();
			}
			while(dFrom <= dTo)
			{
				yArray.add(dFrom);
				dFrom += dStep;
			}
			if(yArray.size() > 80)
			{
				logError("Too many y values (more than 80 generated)");
				throw new IllegalArgumentException();
			}
			graph3DLogList.add(fromY);
			graph3DLogList.add(toY);
			graph3DLogList.add(stepY);
			return yArray;
		}
		else {
			logError("Y in Expression Required");
			throw new IllegalArgumentException();
		}
	}

	private Vector<Double> parseGraphX(String fromX, String toX, String stepX, String function)
	{
		double dFrom;
		double dTo;
		double dStep;
		Vector<Double> xArray = new Vector<Double>();
		if(function.contains("x") || function.contains("X"))
		{
			try{dFrom = Double.parseDouble(fromX);}
			catch (Exception e)
			{
				logError("Please enter a valid 'from X' value.");
				throw new IllegalArgumentException();
			}
			try{dTo = Double.parseDouble(toX);}
			catch (Exception e)
			{
				logError("Please enter a valid 'to X' value.");
				throw new IllegalArgumentException();
			}
			try{dStep = Double.parseDouble(stepX);}
			catch (Exception e)
			{
				logError("Please enter a valid 'X step' value.");
				throw new IllegalArgumentException();
			}
			if(dFrom >= dTo)
			{
				logError("'from X' value is greater than or equal to 'to X' value");
				throw new IllegalArgumentException();
			}
			if(dStep <= 0){
				logError("'X step' must be a positive value");
				throw new IllegalArgumentException();
			}
			while(dFrom <= dTo)
			{
				xArray.add(dFrom);
				dFrom += dStep;
			}
			if(xArray.size() > 80)
			{
				logError("Too many x values (more than 80 generated)");
				throw new IllegalArgumentException();
			}
			if (mode == GRP_MODE){
				graphLogList.add(function);
				graphLogList.add(fromX);
				graphLogList.add(toX);
				graphLogList.add(stepX);
				log("Now Graphing " + function + " in the range X = " + fromX + ":" + stepX + ":" + toX);
			}
			else if (mode == GRP3D_MODE){
				graph3DLogList.add(function);
				graph3DLogList.add(fromX);
				graph3DLogList.add(toX);
				graph3DLogList.add(stepX);
			}
			return xArray;
		}
		else{
			logError("X in Expression Required");
			throw new IllegalArgumentException();
		}
		/*xArray.add(0.0);
		return xArray;*/
	}	static double parseForCalculation(String entry, double xValue, double yValue) throws IllegalArgumentException{
		//ArrayLists for Calculation
		ArrayList<String> valueStringList = new ArrayList<String>();
		ArrayList<Double> valueList	= new ArrayList<Double>();
		ArrayList<String> opList 	= new ArrayList<String>();
		ArrayList<String> functionList = new ArrayList<String>();
		//String xVal = null;

		//Check for Expression
		if(entry.isEmpty()){
			logError("Expression Needed");
			throw new IllegalArgumentException();
		}

		//Check for No X with X given
		if(!entry.contains("x") && !xInput.getText().trim().isEmpty()){
			logError("No X Given in Expression");
			throw new IllegalArgumentException();
		}

		//Begin Parsing Expression
		int i;
		int openCount = 0;
		int closeCount = 0;
		boolean valueHit = false;
		boolean unaryHit = false;
		boolean postParenCheck = false;
		boolean inc_Error = false;
		String valueString = "";
		String functionString = "";
		for(i = 0; i < entry.length(); i++){
			if(isOp(entry.charAt(i))){
				if(postParenCheck){
					postParenCheck = false;
					opList.add(String.valueOf(entry.charAt(i)));
				}
				else if(valueHit){
					opList.add(String.valueOf(entry.charAt(i)));
					valueStringList.add(valueString);
					valueHit = false;
					unaryHit = false;
					inc_Error = false;
					valueString = "";
				}
				else if(unaryHit){
					inc_Error = true;
					valueString = valueString.concat(String.valueOf(entry.charAt(i)));
				}
				else{
					if(entry.charAt(i) == '+' || entry.charAt(i) == '-'){
						unaryHit = true;
						valueString = valueString.concat(String.valueOf(entry.charAt(i)));
					}
					else{
						valueString = valueString.concat(String.valueOf(entry.charAt(i)));
						inc_Error = true;
					}
				}
			}
			else if(entry.charAt(i) == '('){
				openCount = openCount + 1;
				if(valueHit){
						//Function Attempt
						if(valueString.trim().equals("cos")||valueString.trim().equals("sin")||valueString.trim().equals("abs")){
							if(valueString.endsWith(" ")){
								logError("Missing Operator at: " + valueString + "(");
								throw new IllegalArgumentException();
							}
							int temp_i = i+1;
							int temp_open_count = 0;
							int temp_close_count = 0;
							openCount = openCount - 1;
							valueString = valueString.concat(String.valueOf(entry.charAt(i)));
							while((temp_close_count <= temp_open_count) && (temp_i < entry.length())){
								if(entry.charAt(temp_i) == '('){
										temp_open_count = temp_open_count + 1;
										functionString = functionString.concat(String.valueOf(entry.charAt(temp_i)));
										valueString = valueString.concat(String.valueOf(entry.charAt(temp_i)));
								}
								else if(entry.charAt(temp_i) == ')'){
										temp_close_count = temp_close_count + 1;
										if(temp_close_count > temp_open_count) {
											valueString = valueString.concat(String.valueOf(entry.charAt(temp_i)));
											temp_i = temp_i - 1;
										}
										else{
											functionString = functionString.concat(String.valueOf(entry.charAt(temp_i)));
											valueString = valueString.concat(String.valueOf(entry.charAt(temp_i)));
										}
								}
								else{
									functionString = functionString.concat(String.valueOf(entry.charAt(temp_i)));
									valueString = valueString.concat(String.valueOf(entry.charAt(temp_i)));
								}
								temp_i = temp_i + 1;
							}
							if(temp_i == entry.length()){
								logError("Missing Close Paren at End");
								throw new IllegalArgumentException();
							}
							else{
								valueStringList.add(valueString);
								functionList.add(functionString.trim());
								valueHit = false;
								unaryHit = false;
								inc_Error = false;
								postParenCheck = true;
								valueString = "";
								functionString = "";
							}	
							i = temp_i;
						}
						else if(valueString.endsWith("cos")||valueString.endsWith("sin")||valueString.endsWith("abs")){
							logError("Missing Operator at  " + valueString);
							throw new IllegalArgumentException();
						}
						else{  //Else is Function Attempt Branch
							logError("Missing Operator at: " + valueString + "(");
							throw new IllegalArgumentException();
						}
				}
				else if(inc_Error){
					if(valueString.startsWith(" ")){
						logError("Missing Operand at: " + opList.get(opList.size()-1) + valueString);
						throw new IllegalArgumentException();
					}
					else{
						if(opList.size() > 0){
							logError("Invalid Operator at: " + opList.get(opList.size()-1) + valueString);
							throw new IllegalArgumentException();
						}
						else{
							logError("Invalid Unary Operator at: " + valueString);
							throw new IllegalArgumentException();
						}
					}
				}
				else if(unaryHit){
					logError("Unary Operators not allowed on Parenthesis Expression: " + valueString + "(" );
					throw new IllegalArgumentException();
				}
				else if(postParenCheck){
					logError("Missing Operator at: )" + valueString + "(" );
					throw new IllegalArgumentException();
				}
				else{
					opList.add(String.valueOf(entry.charAt(i)));
					valueHit = false;
					unaryHit = false;
					inc_Error = false;
					valueString = "";
				}
			}
			else if(entry.charAt(i) == ')'){
				closeCount = closeCount + 1;
				if(postParenCheck){
					if(closeCount <= openCount) {
						opList.add(String.valueOf(entry.charAt(i)));
					}
					else{
						logError("Close Paren without Beginning at Close Paren Number: " + closeCount);
						throw new IllegalArgumentException();
					}
				}
				else if(closeCount > openCount){
					logError("Close Paren without Beginning at Close Paren Number: " + closeCount);
					throw new IllegalArgumentException();
				}
				else if(unaryHit && !valueHit){
					logError("Incomplete Operand at: " + opList.get(opList.size()-1) + valueString + ")");
					throw new IllegalArgumentException();
				}
				else if(!valueHit){
					logError("Missing Operand at: " + opList.get(opList.size()-1) + valueString + ")");
					throw new IllegalArgumentException();
				}
				else{
					valueStringList.add(valueString);
					opList.add(String.valueOf(entry.charAt(i)));
					valueHit = false;
					unaryHit = false;
					inc_Error = false;
					postParenCheck = true;
					valueString = "";
				}
			}
			else if(entry.charAt(i) == ' '){
				if(inc_Error){
					if(valueString.startsWith(" ")){
						logError("Missing Operand at: " + opList.get(opList.size()-1) + valueString);
						throw new IllegalArgumentException();
					}
					else{
						if(opList.size() > 0){
							logError("Invalid Operator at: " + opList.get(opList.size()-1) + valueString);
							throw new IllegalArgumentException();
						}
						else{
							logError("Invalid Unary Operator at: " + valueString);
							throw new IllegalArgumentException();
						}
					}
				}
				else if(unaryHit && !valueHit){
					if(valueString.startsWith(" ")){
						logError("Missing Operand at: " + opList.get(opList.size()-1) + valueString);
						throw new IllegalArgumentException();
					}
					else{
						if(opList.size() > 0){
							if(opList.get(opList.size()-1).equals("(")){
								logError("Missing Operand at: " + opList.get(opList.size()-1) + valueString);
								throw new IllegalArgumentException();
							}
							else{
								logError("Invalid Operator at: " + opList.get(opList.size()-1) + valueString);
								throw new IllegalArgumentException();
							}
						}
						else{
							logError("Missing Operand at Start");
							throw new IllegalArgumentException();
						}
					}
				}
				else{
					valueString = valueString.concat(String.valueOf(entry.charAt(i)));
				}
			}
			else{
				if(inc_Error){
					if(valueString.startsWith(" ")){
						logError("Invalid Unary Operator at: " + opList.get(opList.size()-1) + valueString);
						throw new IllegalArgumentException();
					}
					else{
						if(opList.size() > 0){
							logError("Invalid Operator at: " + opList.get(opList.size()-1) + valueString);
							throw new IllegalArgumentException();
						}
						else{
							logError("Invalid Unary Operator at: " + valueString);
							throw new IllegalArgumentException();
						}
					}
				}
				else if(postParenCheck){
					logError("Missing Operator at: )" + entry.charAt(i));
					throw new IllegalArgumentException();
				}
				else{
					valueString = valueString.concat(String.valueOf(entry.charAt(i)));
					valueHit = true;
				}
			}
		}
		if(!valueHit && !postParenCheck){
			logError("Missing Operand at End: " + opList.get(opList.size()-1) + valueString);
			throw new IllegalArgumentException();
		}
		else if(openCount > closeCount){
			logError("Open Paren without Close -> Open Parens: " + openCount + " Close Parens: " + closeCount);
			throw new IllegalArgumentException();
		}
		else if(!valueString.isEmpty()){
			valueStringList.add(valueString);
		}

		int fCount = 0;
		//Attempt to convert values to doubles -> Error Catch with NumberFormatExeption and Custom Code
		for(int j = 0; j < valueStringList.size(); j++){
			String valuePreTrimAdd = valueStringList.get(j);
			String valueToAdd	   = valuePreTrimAdd.trim();
			try{
				valueList.add(Double.valueOf(valueToAdd));
			}
			catch(NumberFormatException nfe){
				if(valueToAdd.equalsIgnoreCase("PI")){
					valueList.add(Math.PI);
					continue;
				}
				else if(valueToAdd.equalsIgnoreCase("e")){
					valueList.add(Math.E);
					continue;
				}
				else if(valueToAdd.equalsIgnoreCase("-PI")){
					valueList.add(-Math.PI);
					continue;
				}
				else if(valueToAdd.equalsIgnoreCase("-e")){
					valueList.add(-Math.E);
					continue;
				}
				else if(valueToAdd.equalsIgnoreCase("x")){
					valueList.add(xValue);
					continue;
				}
				else if(valueToAdd.equalsIgnoreCase("-x")){
					valueList.add(-xValue);
					continue;
				}
				else if(valueToAdd.equalsIgnoreCase("y")){
					if(mode == GRP3D_MODE){
						valueList.add(yValue);
						continue;
					}
					else{
						logError("Invalid Operand: " + valueToAdd);
						throw new IllegalArgumentException();
					}
				}
				else if(valueToAdd.equalsIgnoreCase("-y")){
					if(mode == GRP3D_MODE){
						valueList.add(-yValue);
						continue;
					}
					else{
						logError("Invalid Operand: " + valueToAdd);
						throw new IllegalArgumentException();
					}
				}
				else if(valueToAdd.startsWith("cos(")){
					String xText = xInput.getText().trim();
					try{
						if(!xText.isEmpty()){
							xInput.setText("");
						}
						valueList.add(Math.cos(parseForCalculation(functionList.get(fCount),xValue, yValue)));
						fCount = fCount+1;
						if(!xText.isEmpty()){
							xInput.setText(xText);
						}
					}
					catch(IllegalArgumentException ae){
						resultList.set(resultList.size() - 1, resultList.get(resultList.size() -1).concat(" -> In Function cos()"));
						xInput.setText(xText);
						throw new IllegalArgumentException();
					}
				}
				else if(valueToAdd.startsWith("sin(")){
					String xText = xInput.getText().trim();
					try{
						if(!xText.isEmpty()){
							xInput.setText("");
						}
						valueList.add(Math.sin(parseForCalculation(functionList.get(fCount),xValue, yValue)));
						fCount = fCount+1;
						if(!xText.isEmpty()){
							xInput.setText(xText);
						}
					}
					catch(IllegalArgumentException ae){
						resultList.set(resultList.size() - 1, resultList.get(resultList.size() -1).concat(" -> In Function sin()"));
						xInput.setText(xText);
						throw new IllegalArgumentException();
					}
				}
				else if(valueToAdd.startsWith("abs(")){
					String xText = xInput.getText().trim();
					try{
						if(!xText.isEmpty()){
							xInput.setText("");
						}
						valueList.add(Math.abs(parseForCalculation(functionList.get(fCount),xValue, yValue)));
						fCount = fCount+1;
						if(!xText.isEmpty()){
							xInput.setText(xText);
						}
					}
					catch(IllegalArgumentException ae){
						resultList.set(resultList.size() - 1, resultList.get(resultList.size() -1).concat(" -> In Function abs()"));
						xInput.setText(xText);
						throw new IllegalArgumentException();
					}
				}
				else if(valueToAdd.contains(" ")){
					logError("Missing Operator at: " + valueToAdd);
					throw new IllegalArgumentException();
				}
				else{
					logError("Invalid Operand: " + valueToAdd);
					throw new IllegalArgumentException();
				}
			}
		}
		
//		System.out.println(valueList);
//		System.out.println(opList);
//		
		double rVal = 0;
		
		try{
			rVal = calculateValue(valueList,opList);
			BigDecimal calcValue = BigDecimal.valueOf(rVal);
			calcValue = calcValue.setScale(2, BigDecimal.ROUND_HALF_DOWN);

			return calcValue.doubleValue();
		}
		catch(NumberFormatException fe){
			if(Double.isInfinite(rVal)){
				logError("Overflow");
				throw new IllegalArgumentException();
			}
			else{
				logError("Result Not a Real Number");
				throw new IllegalArgumentException();
			}
		}
	}	

	//Calculates a Calculator Expression and returns the value
	//Takes the following args
	//List of Values (in order from left to right)
	//List of operators (in order from left to right)
	private static double calculateValue(List<Double> valueList, List<String> opList)
	{
		double addedValue = 0;
		int opPlace		  = 0;

		//Follow PEMDAS - Parenthesis
		while(opPlace < opList.size()){
			if(opList.get(opPlace).equals("(")){
				int tempOpPlace = opPlace + 1;  //Tracker for end parens
				int tempParenDepth = 0;
				int parenDepth = 0; 			//Used for tracking embedded parens
				while(!opList.get(tempOpPlace).equals(")") || (tempParenDepth > 0)) {
					if(opList.get(tempOpPlace).equals("(")){
						parenDepth = parenDepth + 1;
						tempParenDepth = tempParenDepth + 1;
					}
					else if(opList.get(tempOpPlace).equals(")"))tempParenDepth = tempParenDepth - 1;
					tempOpPlace = tempOpPlace + 1;
				}
				//Changes in Sublists are reflected in mainList, so don't need to set new value
				//Two removes from opList to get rid of parenthesis
				calculateValue(valueList.subList(opPlace, tempOpPlace - 2*(parenDepth)), opList.subList(opPlace + 1, tempOpPlace));
				opList.remove(opPlace);
				opList.remove(opPlace);
			}
			else opPlace++;
		}

		opPlace = 0;
		//Follow PEMDAS - Exponent/Root
		while(opPlace < opList.size()){
			if(opList.get(opPlace).equals("^")){
				addedValue = (Math.pow(valueList.get(opPlace), valueList.get(opPlace + 1)));
				opList.remove(opPlace);
				valueList.remove(opPlace);
				valueList.set(opPlace, addedValue);
			}
			else if(opList.get(opPlace).equalsIgnoreCase("r")){
				addedValue = (Math.pow(valueList.get(opPlace), 1.0/(valueList.get(opPlace + 1))));
				opList.remove(opPlace);
				valueList.remove(opPlace);
				valueList.set(opPlace, addedValue);
			}
			else opPlace++;
		}
		opPlace = 0;
		//Follow PEMDAS - Multiply/Divide
		while(opPlace < opList.size()){
			if(opList.get(opPlace).equals("*")){
				addedValue = (valueList.get(opPlace) * valueList.get(opPlace + 1));
				opList.remove(opPlace);
				valueList.remove(opPlace);
				valueList.set(opPlace, addedValue);
			}
			else if(opList.get(opPlace).equals("/")){
				addedValue = (valueList.get(opPlace) / valueList.get(opPlace + 1));
				opList.remove(opPlace);
				valueList.remove(opPlace);
				valueList.set(opPlace, addedValue);
			}
			else opPlace++;
		}
		opPlace = 0;
		//Follow PEMDAS - Add/Subtract
		while(opPlace < opList.size()){
			if(opList.get(opPlace).equals("+")){
				addedValue = (valueList.get(opPlace) + valueList.get(opPlace + 1));
				opList.remove(opPlace);
				valueList.remove(opPlace);
				valueList.set(opPlace, addedValue);
			}
			else if(opList.get(opPlace).equals("-")){
				addedValue = (valueList.get(opPlace) - valueList.get(opPlace + 1));
				opList.remove(opPlace);
				valueList.remove(opPlace);
				valueList.set(opPlace, addedValue);
			}
			else opPlace++;
		}
		return valueList.get(0);
	}

	private static void log(String output)
	{
		resultList.add(output);
		resultListDisplay.setListData(resultList);
		resultListDisplay.repaint();
	}

	private static void logError(String output)
	{
		resultList.add("ERROR: "+output);
		resultListDisplay.setListData(resultList);
		resultListDisplay.repaint();	
	}

	private boolean cashCheck(String entry)
	{	
		if(!isCashMode)
			return true;

		int index = entry.indexOf('.');

		if(index == -1)
			return true;
		if(index+3 == entry.length())
			return true;
		return false;
	}

	private void setMode(int newMode)
	{
		if(newMode == mode)
			return;

		sum = 0;
		inputField.setText("");
		xInput.setText("");
		xMaxInput.setText("");
		xStepInput.setText("");
		yInput.setText("");
		yMaxInput.setText("");
		yStepInput.setText("");

		mode = newMode;
		recallButton.setEnabled(mode == CAL_MODE || mode == GRP_MODE || mode == GRP3D_MODE);
		cashMode.setEnabled(mode == ACC_MODE);
		xInput.setEnabled(mode == CAL_MODE || mode == GRP_MODE || mode == GRP3D_MODE);
		xMaxInput.setEnabled(mode == GRP_MODE || mode == GRP3D_MODE);
		xStepInput.setEnabled(mode == GRP_MODE || mode == GRP3D_MODE);
		yInput.setEnabled(mode == GRP3D_MODE);
		yMaxInput.setEnabled(mode == GRP3D_MODE);
		yStepInput.setEnabled(mode == GRP3D_MODE);


		if(mode == ACC_MODE)
		{
			instruction.setText("Format: operator (+ or -) followed by operand (number); (+) not required.  See Help for Additional Details.");
			xInputLabel.setForeground(Color.GRAY);
			xMaxLabel.setForeground(Color.GRAY);
			xStepLabel.setForeground(Color.GRAY);
			yInputLabel.setForeground(Color.GRAY);
			yMaxLabel.setForeground(Color.GRAY);
			yStepLabel.setForeground(Color.GRAY);
			log("Accumulator Mode Started");
		}
		else if(mode == CAL_MODE)
		{
			instruction.setText("Format: Allowed Operators -> (+,-,*,/,^,r). See Help for Additional Details");
			xInputLabel.setForeground(Color.BLACK);
			xMaxLabel.setForeground(Color.GRAY);
			xStepLabel.setForeground(Color.GRAY);
			yInputLabel.setForeground(Color.GRAY);
			yMaxLabel.setForeground(Color.GRAY);
			yStepLabel.setForeground(Color.GRAY);
			log("Calculator Mode Started");
		}
		else if(mode == GRP_MODE)
		{
			instruction.setText("Format: Allowed Operators -> (+,-,*,/,^,r). See Help for Additional Details");
			xInputLabel.setForeground(Color.BLACK);
			xMaxLabel.setForeground(Color.BLACK);
			xStepLabel.setForeground(Color.BLACK);
			yInputLabel.setForeground(Color.GRAY);
			yMaxLabel.setForeground(Color.GRAY);
			yStepLabel.setForeground(Color.GRAY);
			log("Graphing Mode Started");
		}
		else
		{
			instruction.setText("Format: Allowed Operators -> (+,-,*,/,^,r). See Help for Additional Details");
			xInputLabel.setForeground(Color.BLACK);
			xMaxLabel.setForeground(Color.BLACK);
			xStepLabel.setForeground(Color.BLACK);
			yInputLabel.setForeground(Color.BLACK);
			yMaxLabel.setForeground(Color.BLACK);
			yStepLabel.setForeground(Color.BLACK);
			log("Graphing3D Mode Started");
		}
		resultListDisplay.ensureIndexIsVisible(resultList.size()-1);
	}

	public static void main(String[] args) 
	{
		new Calculator();
	}

}
