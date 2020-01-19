package dataStructure;

import java.io.Serializable;

import utils.Point3D;
/**
 * 
 * node (vertex) in a (directional) weighted graph.
 * @author avichai and tal
 *
 */
public class NodeData implements node_data,Serializable {

	private static final long serialVersionUID = 1L;
	private int key; 
	private Point3D p; 
	private double w; 
	private String info; 
	private int tag; 
	
	/*
	 *  Default Contractor 
	 */
	public NodeData() {
		this.key=0; 
		this.w=0; 
		this.info=""; 
		this.tag=0;
	}
	
	
	/*
	 * Contractor with all parameters
	 */
	public NodeData(int k, Point3D p1, double weight, String i, int t) {
		this.key=k;
		this.p=p1; 
		this.w=weight; 
		this.info=i; 
		this.tag=t;
	}
	
	/*
	 * Contractor with location,key and weight
	 */
	public NodeData( Point3D p1, int k, double weight) {
		this.key=k;
		this.p=p1; 
		this.w=weight; 
	}
	/*
	 * Contractor with location and key
	 */
	public NodeData( Point3D p1, int k) {
		this.key=k;
		this.p=p1; 
	}
	/**
	 * Return the key (id) associated with this node.
	 * @return
	 */
	@Override
	public int getKey() {
		return this.key;
	}
	/** Return the location (of applicable) of this node, if
	 * none return null.
	 * 
	 * @return
	 */
	@Override
	public Point3D getLocation() {
	return this.p;
	}
	/** Allows changing this node's location.
	 * 
	 * @param p - new new location  (position) of this node.
	 */
	@Override
	public void setLocation(Point3D p) {
		this.p=p;
	}
	/**
	 * Return the weight associated with this node.
	 * @return
	 */
	@Override
	public double getWeight() {
		return this.w;
	}
	/**
	 * Allows changing this node's weight.
	 * @param w - the new weight
	 */
	@Override
	public void setWeight(double w) {
		this.w=w;
		
	}
	/**
	 * return the remark (meta data) associated with this node.
	 * @return
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	/** 
	 * Allow setting the "tag" value for temporal marking an node - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		this.tag=t;
		
	}
	/*
	 * toString function
	 */
	public String toString()
	{
		return ""+key;
	}
}
