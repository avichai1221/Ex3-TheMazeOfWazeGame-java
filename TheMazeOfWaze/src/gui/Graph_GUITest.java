package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.NodeData;
import utils.Point3D;

class Graph_GUITest {

	@Test
	void testGraph_GUI() {
		NodeData test1=new NodeData(1,null,1.0,null,1);
		NodeData test2=new NodeData(2,null,22.0,null,2);
		NodeData test3=new NodeData(3,null,3.0,null,3);
		NodeData test4=new NodeData(4,null,7.0,null,1);
		NodeData test5=new NodeData(5,null,5.0,null,2);
		NodeData test6=new NodeData(6,null,8.0,null,3);
		NodeData test7=new NodeData(7,null,1.0,null,1);
		NodeData test8=new NodeData(8,null,12.0,null,2);
		NodeData test9=new NodeData(9,null,10.0,null,3);
		
		Point3D p1= new Point3D(500, 500);
		Point3D p2= new Point3D(600, 400);
		Point3D p3= new Point3D(520,670);
		Point3D p4= new Point3D(400, 100);
		Point3D p5= new Point3D(570, 170);
		Point3D p6= new Point3D(600, 200);
		Point3D p7= new Point3D(150,230);
		Point3D p8= new Point3D(300, 410);
		Point3D p9= new Point3D(400, 180);
		test1.setLocation(p1);
		test2.setLocation(p2);
		test3.setLocation(p3);
		test4.setLocation(p4);
		test5.setLocation(p5);
		test6.setLocation(p6);
		test7.setLocation(p7);
		test8.setLocation(p8);
		test9.setLocation(p9);

		DGraph a1 =new DGraph();
		
		a1.addNode(test1);
		a1.addNode(test2);
		a1.addNode(test3);
		a1.addNode(test4);
		a1.addNode(test5);
		a1.addNode(test6);
		a1.addNode(test7);
		a1.addNode(test8);
		a1.addNode(test9);
	    a1.connect(2, 5, 444);
	    a1.connect(2, 4, 14);
	    a1.connect(5, 9, 4);
	    a1.connect(4, 5, 30);
	    a1.connect(7, 2, 8);
	    a1.connect(3, 7, 11);
	    a1.connect(6, 1, 9);
	    a1.connect(5, 8, 3);
	    a1.connect(8, 6, 12);
	    
	 /*   a1.connect(5, 2, 444);
	    a1.connect(4, 2, 14);
	    a1.connect(9, 5, 4);
	    a1.connect(5, 4, 30);
	    a1.connect(2, 7, 8);
	    a1.connect(7, 3, 11);
	    a1.connect(1, 6, 9);
	    a1.connect(8, 5, 3);
	    a1.connect(6, 8, 12);*/
	   
	  
		Graph_GUI gg = new Graph_GUI(a1);
	
		gg.setVisible(true);
	}


}
