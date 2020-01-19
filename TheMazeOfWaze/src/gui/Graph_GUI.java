package gui;
import algorithms.Graph_Algo;
import utils.Point3D;
import java.awt.*;
import java.util.ArrayList;

import utils.*;
import dataStructure.*;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Graph_GUI extends JFrame implements ActionListener

{
	DGraph Dgraph;


	public Graph_GUI (DGraph d)
	{

		this.Dgraph=d;
		initGUI();
				
	}
	
	 
/*
 * create window,exit on window,menu and Buttons on menu
 */
	private void initGUI() 
	{
		this.setSize(700, 700);                           //create window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //create exit on window


		/////////////create menu on window////////////////
		MenuBar menuBar = new MenuBar();                       
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		/////////////add Buttons to the menu////////////////////////
		MenuItem save = new MenuItem("save");
		save.addActionListener(this);

		MenuItem TSP = new MenuItem("TSP");
		TSP.addActionListener(this);

		MenuItem SP = new MenuItem("shortest Path");
		SP.addActionListener(this);

		MenuItem isConnected = new MenuItem("isConnected");
		isConnected.addActionListener(this);

		MenuItem fromFile = new MenuItem("from file");
		fromFile.addActionListener(this);

		MenuItem SPD = new MenuItem("shortest Path Dist");
		SPD.addActionListener(this);

		menu.add(save);
		menu.add(TSP);
		menu.add(SP);
		menu.add(isConnected);
		menu.add(fromFile);
		menu.add(SPD);


	}
/*
 * draw the graph
 */
	public void paint(Graphics g)
	{
		super.paint(g);

		for(node_data node :Dgraph.getV()) // draw Data Nodes
		{
			g.setColor(Color.BLUE);         //blue color for Circle
			g.fillOval((int)node.getLocation().x(),(int)node.getLocation().y(), 8, 8); //draw the Circle
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString(String.valueOf(node.getKey()),(int)node.getLocation().x(),(int)node.getLocation().y());  //draw the key value
		}

		////////////////

		//////////////// draw the Edges nodes//////////////////

		for(node_data node1 :Dgraph.getV())
		{
			if(Dgraph.getE(node1.getKey())!=null)
			{
				for(edge_data edge1: Dgraph.getE(node1.getKey())) 
				{ 
					if(Dgraph.getNode(edge1.getDest())!=null) {
						g.setColor(Color.RED);     // red color for lines
						g.drawLine((int) Dgraph.getNode(edge1.getDest()).getLocation().x()+5, (int) Dgraph.getNode(edge1.getDest()).getLocation().y()+5,
								(int) Dgraph.getNode(edge1.getSrc()).getLocation().x()+5, (int) Dgraph.getNode(edge1.getSrc()).getLocation().y()+5);
						String Weight = String.valueOf(edge1.getWeight());
						//////////////////////////draw the weight of edge
						g.setColor(Color.red);
						g.drawString(Weight, (int)((Dgraph.getNode(edge1.getSrc()).getLocation().x() + Dgraph.getNode(edge1.getDest()).getLocation().x()) / 2),
								(int) (Dgraph.getNode(edge1.getSrc()).getLocation().y() + Dgraph.getNode(edge1.getDest()).getLocation().y()) / 2);


						/////////draw Circle for direction 
						double X = ((Dgraph.getNode(edge1.getDest()).getLocation().x()+2) * 8 + (Dgraph.getNode(edge1.getSrc()).getLocation().x())+2 )/ 9;
						double  Y = ((Dgraph.getNode(edge1.getDest()).getLocation().y()+2 )* 8 + (Dgraph.getNode(edge1.getSrc()).getLocation().y())+2) / 9;
						g.setColor(Color.YELLOW);  // yellow color for the circle of direction
						g.fillOval((int)X,(int)Y, 7, 7);

					}

				}}

		}


	}

  /*
  * Determines what action to perform on each tab on menu
  */
	@Override
	public void actionPerformed(ActionEvent e) {
     String str = e.getActionCommand();
		
      Graph_Algo a=new Graph_Algo();
      a.init(Dgraph);
      JFrame newWindow = new JFrame();
      
		if(str.equals("isConnected"))
		{
			JOptionPane.showMessageDialog(newWindow,a.isConnected());
			repaint();
		}
		if(str.equals("save"))
		{
			a.save("itsWorksGood.txt");
			repaint();
		}
		if(str.equals("TSP"))
		{
			ArrayList<Integer>arr=new ArrayList<Integer>();
			String NumOfNodes = JOptionPane.showInputDialog(newWindow,"how much Nodes do you want to check?");
			
			for (int i = 0; i <Integer.parseInt(NumOfNodes); i++)
			{
				String NumForList = JOptionPane.showInputDialog(newWindow,"choose a node for check");
				arr.add(Integer.parseInt(NumForList));
			}
			
			JOptionPane.showMessageDialog(newWindow, a.TSP(arr));  
			repaint();
			
		}
		
		if(str.equals("from file"))
		{
			String FileName = JOptionPane.showInputDialog(newWindow,"what is the name of the file?");
            a.init(FileName);
		}
		if(str.equals("shortest Path"))
		{
			 
			 String Source = JOptionPane.showInputDialog(newWindow,"Source Node");
			 String Dest = JOptionPane.showInputDialog(newWindow,"Destination Node");
			JOptionPane.showMessageDialog(newWindow, a.shortestPath(Integer.parseInt(Source),Integer.parseInt(Dest)).toString());  
			repaint();
		}
		
		
		if(str.equals("shortest Path Dist"))
		{
			String Source = JOptionPane.showInputDialog(newWindow,"Source Node");
			String Dest = JOptionPane.showInputDialog(newWindow,"Destination Node");
			JOptionPane.showMessageDialog(newWindow, a.shortestPathDist(Integer.parseInt(Source), Integer.parseInt(Dest)));  
			repaint();
		}
		

	}

	
	
	
	
	
}
