package dataStructure;

import java.awt.font.NumericShaper.Range;
import java.util.ArrayList;
import java.util.List;

import algorithms.Graph_Algo;
/*import gui.Graph_GUI;
*/import utils.Point3D;

public class mytest {

	public static void main(String[] args) {
		/*EdgeData a=new EdgeData(1,2,1);
		EdgeData b=new EdgeData();
		NodeData c=new NodeData(5,null,1.0,null,1);
		NodeData c1=new NodeData(2,null,2.0,null,2);
		NodeData c3=new NodeData(2,null,333.0,null,333);
		NodeData d=new NodeData();
	//	System.out.println("edgeData a="+a);
		//System.out.println("edgeData b="+b);
		//System.out.println("nodeData c="+c);
		//System.out.println("nodeData d="+d);
		
		DGraph a1 =new DGraph();
		a1.addNode(c);
	//	a1.addNode(c1);
		a1.addNode(c3);
	a1.connect(2, 54, 4);
		System.out.println(a1.getNode(2));
	// System.out.println(a1.removeEdge(2, 54));
		System.out.println(a1.getEdge(2,54));
		System.out.println(a1);
		System.out.println(a1.edgeSize());   */
		NodeData test=new NodeData(5,null,1.0,null,1);
		NodeData test1=new NodeData(2,null,2.0,null,2);
		NodeData test2=new NodeData(4,null,333.0,null,333);
	
		DGraph a1 =new DGraph();
		DGraph a2 =new DGraph();
	//	a1.addNode(test);
		a1.addNode(test1);
		a1.addNode(test2);
	    a1.connect(2, 54, 4);
	 //  a1.connect(2, 51, 4);
	//    a1.connect(5, 51, 4);
	//  a2.addNode(test);
	//	a2.addNode(test1);
    //		a2.addNode(test2);
	//    a2.connect(2, 54, 4);
	 // a1.removeEdge(2, 54);
	 //   System.out.println(a1.toString());

	    Graph_Algo g=new Graph_Algo();
	    Graph_Algo g1=new Graph_Algo();
	

	//    System.out.println(a1.getV());
	    g.init(a1);
	    System.out.println(g.getV());
	    System.out.println(g.getE(2));
	    
	    System.out.println(g1.getV());
	    System.out.println(g1.getE(2));
	    
	    System.out.println(g1.copy().getV());
	    System.out.println(g1.copy().getE(2));
//	    
//			DGraph x=new DGraph();
//			Point3D p=new Point3D(1,2,3);
//			NodeData n=new NodeData(p,1, 0);
//			x.addNode(n);
//			p=new Point3D(3,2,1);
//			NodeData m=new NodeData(p,2, 0);
//			x.addNode(m);
//			p=new Point3D(3,2,1);
//			NodeData v=new NodeData(p,3,0);
//			x.addNode(v);
//			x.connect(n.getKey(), m.getKey(), 10);
//			x.connect(m.getKey(), n.getKey(), 8);
//			x.connect(n.getKey(), v.getKey(), 100);
//			x.connect(m.getKey(), v.getKey(), 1);
//			Graph_Algo g1 = new Graph_Algo();
//			//g1.init(x);
//			List<Integer> list=new ArrayList<>();
//			for(node_data node :x.getV())
//				list.add(node.getKey());
//					g.save("graph.txt");
//
//			System.out.println(g+"\n-------------\n"+x);
//			System.out.println(g.shortestPath(1, 2));
//			System.out.println(g.TSP(list).toString());
//		
//			g.save("graph.txt");
//			System.out.println(g+"\n-------------\n"+x);
//			System.out.println(g.TSP(list));
	    
		
			
	}

}
