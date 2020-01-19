package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.NodeData;
import utils.Point3D;

class NodeDataTest {
	
	Point3D p=new Point3D(1,2,3);
	Point3D p1=new Point3D(3,2,1);
	Point3D p2=new Point3D(1,2,4);
	NodeData n=new NodeData(p,1, 0);
	NodeData m=new NodeData(p1,2, 0);
	NodeData v=new NodeData(p2,3,0);
	NodeData x =new NodeData();
	@Test
	void testNodeData() {
		
	}


	@Test
	void testGetKey() {
		assertEquals(n.getKey(),1);
	}

	@Test
	void testGetLocation() {
		assertEquals(n.getLocation(),p);
	}

	@Test
	void testSetLocation() {
		Point3D p3=new Point3D(1,2,3);
		x.setLocation(p);
		assertEquals(x.getLocation(),p3);
	}

	@Test
	void testGetWeight() {
		assertEquals(m.getWeight(),0);
	}

	@Test
	void testSetWeight() {
		x.setWeight(0);
		assertEquals(x.getWeight(),m.getWeight());
	}

	@Test
	void testGetInfo() {
		x.setInfo("same info");
		assertEquals(x.getInfo(),"same info");
	}

	@Test
	void testSetInfo() {
		NodeData y =new NodeData(); 
		y.setInfo("cheak info");
		assertEquals(y.getInfo(),"cheak info");
	}

	@Test
	void testGetTag() {
		x.setTag(0);
		assertEquals(x.getTag(),0);
	}

	@Test
	void testSetTag() {
		NodeData z =new NodeData(); 
		z.setTag(1);
		assertEquals(z.getTag(),1);
	}

	

}
