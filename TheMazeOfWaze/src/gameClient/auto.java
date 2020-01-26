package gameClient;

import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Server.Game_Server;
import Server.game_service;
import Server.robot;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class auto implements Runnable{
	int k=0;

	int counter=0;
	game_service game;
	Thread tr;
	DGraph dgraph;
	Graph_Algo Algo = new Graph_Algo();
	Range rx, ry;
	List<Fruit> fru;
	List<Robot> rob;
	final static double epsilon = 0.0000001;
	static boolean KMLbool = false;
	static String KmlName;
	static int numgame;
	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";


	public auto(int game_number)

	{
		
		//game_service game = Game_Server.getServer(0); // you have [0,23] games
		game = Game_Server.getServer(game_number);
		numgame = game_number;
		//read from game server
		dgraph = new DGraph();
		dgraph.init(game.getGraph());
		Algo.init(dgraph);
		//bring the min and max x and y
		rx = FindXmaxmin(dgraph);
		ry = FindYmaxmin(dgraph);
		//this thread is in charge of the game
		tr = new Thread(this, "Atr");
		tr.start();

	}



	@Override
	public void run() {

		drawGraph(dgraph);
		int numR=getnumR(game);
		DrawFruit(game);
		changeRobot(numR);
		DrawRobots(game.getRobots());
		int ans=JOptionPane.showConfirmDialog(null, "you want KML file??", "message", JOptionPane.YES_OPTION);
		if(ans == 0) 
		{
			String input = JOptionPane.showInputDialog("enter your name file");
			if(input != null && input != "")
				startKML(input);

		}

		game.startGame();
		StdDraw.setFont();
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.text( rx.get_max()-0.002, ry.get_max()-0.0005,"time to end: "+game.timeToEnd()/1000);
		while(game.isRunning()) {

			StepForRobot();
			refreshGame();
			try {


				Thread.sleep(100);

			} catch (InterruptedException e)
			{
				e.printStackTrace();

			}

		}

		if(KMLbool) {

			try {
				KML_Logger.closeFile(KmlName);

			}catch (IOException e) {

				e.printStackTrace();

			}

		}

		String message = "";
		int i = 1;
		for(Robot r : rob)
			message += "\trobot " + (i++) + " score: " + r.value +"\n";
		JOptionPane.showMessageDialog(null, message);
		yourResults();
		System.exit(0);




	}

	private void refreshGame() {

		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		StdDraw.setScale();
		StdDraw.picture(0.5, 0.5, "map.png");
		drawGraph(dgraph);
		DrawRobots(game.move());
		/*
			if(k%3==0||k%3==0)
			{
				if(counter<240&&counter>230&&k%3==0)
				{
				DrawRobots(game.move());
				k++;
				}
				else if (k%3==0)
				{
					DrawRobots(game.move());
					k=k+1;
				}
			}
			k++;*/



		DrawFruit(game);
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.text(rx.get_max()-0.002, ry.get_max()-0.0005,"time to end: "+ game.timeToEnd() / 1000);
		StdDraw.show();

	}


	/**
	 *take the robot to the best fruit with shortestPath.
	 * */
	private void StepForRobot() { 

		Iterator<Robot> itr = rob.iterator();
		while(itr.hasNext()) {

			Robot r = itr.next();
			Fruit fDest = null;
			double maxSum = 0;
			node_data v = null;
			Iterator<Fruit> it = fru.iterator();
			while(it.hasNext()) {


				Fruit f = it.next();
				double sum =0;
				int dest = f.e.getSrc();
				List<node_data> list = Algo.shortestPath(r.src, dest);
				list.add(dgraph.getNode(f.e.getDest()));



				for(int i = 0;i<list.size()-1;i++)
					sum += dgraph.getEdge(list.get(i).getKey(), list.get(i+1).getKey()).getWeight();
				sum = f.getValue() / sum;

				if(sum > maxSum && !f.isDest) {


					maxSum = sum;
					v = list.get(1); 
					fDest = f;

				}

			}

			if(fDest != null) {



				fDest.setDest(true);
				game.chooseNextEdge(r.id, v.getKey());

			}

		}

	}



	/**
	 * find the best spot for all the robot to start.
	 * */
	public void changeRobot(int numR) 
	{
		//	game.addRobot(4);

		edge_data e=new EdgeData();
		int numans=0;
		int i=0;
		double x=0;
		double y=Double.MAX_VALUE;

		while(i<numR) {

			double c=0; 
			for(Fruit f: fru) {
				e=f.e;
				x=f.value/e.getWeight();
				if(x>c && x<y) {

					c=x; 
					numans=e.getSrc();
				}
			}
			y=c;
			game.addRobot(numans);
			i++;
		}
	}




	/**
	 *init and draw the robot.
	 * */
	public void DrawRobots(List<String> robots) {
		counter++;
		rob=new ArrayList<>();
		List<String> r=game.getRobots();
		String RJ=r.toString();
		try {
			JSONArray j =new JSONArray(RJ);
			int i=0; 
			int c=0;
			while(i<j.length()) {
				String help="";
				JSONObject n=(JSONObject) j.get(i);
				JSONObject jr = n.getJSONObject("Robot");
				String s=jr.getString("pos");
				String p[]=new String[3];
				for (int k = 0; k < s.length(); k++) {
					if(s.charAt(k)!=',') {
						help=help+s.charAt(k);
					}
					if(c<3&&s.charAt(k)==',') {
						p[c]=help; 
						c++;
						help="";
					}
				}
				p[c]=help; 
				c=0;
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				Point3D p1=new Point3D(x,y,z);
				int id=jr.getInt("id");
				double value=jr.getDouble("value");
				int src=jr.getInt("src");
				int dest=jr.getInt("dest");
				double speed=jr.getDouble("speed");
				Robot ro=new Robot(src,p1,id,dest,value,speed);
				rob.add(ro);
				i++;

				if(KMLbool) {

					try {
						KML_Logger.write(KmlName, ro.p.x(), ro.p.y(), "Robot", game.timeToEnd());

					} catch (FileNotFoundException e) {
						e.printStackTrace();

					}

				}
			}


		}catch(Exception error) {
			error.printStackTrace();

			System.out.println("catch");

		}
		int n=1;
		for (Robot r1: rob) {
			StdDraw.setPenRadius(0.030);
			StdDraw.setPenColor(Color.black);
			StdDraw.point(r1.p.x(), r1.p.y());
			StdDraw.text(rx.get_max() - 0.001 - 0.0075*n, ry.get_max()-0.0005, 
					"robot "+ (n++) + " score: " + r1.value);
		}


	}



	/**
	 *init and draw the fruit.
	 * */
	private void DrawFruit(game_service game) {

		fru=new ArrayList<Fruit>();
		List<String> f= game.getFruits();
		String FJ=f.toString(); 

		try {
			JSONArray j =new JSONArray(FJ);
			int i=0; 
			int c=0;
			while(i<j.length()) {
				String help="";
				JSONObject n=(JSONObject) j.get(i);
				JSONObject jf = n.getJSONObject("Fruit");
				String s=jf.getString("pos");
				String p[]=new String[3];
				for (int k = 0; k < s.length(); k++) {
					if(s.charAt(k)!=',') {
						help=help+s.charAt(k);
					}
					if(c<3&&s.charAt(k)==',') {
						p[c]=help; 
						c++;
						help="";
					}
				}
				p[c]=help; 
				c=0;
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				Point3D p1=new Point3D(x,y,z);
				double value=jf.getDouble("value");
				int type=jf.getInt("type");
				Fruit fr=new Fruit(value,p1,type,findEdgeFruit(p1, type));
				fru.add(fr);
				i++;
				if(KMLbool) {

					String objType = "";
					if(fr.type == 1)
						objType="Apple";
					else 
						objType="Banana";

					try {
						KML_Logger.write(KmlName, fr.p.x(), fr.p.y(), objType, game.timeToEnd());

					} catch (FileNotFoundException e) {

						e.printStackTrace();

					}

				}

			}

		}catch(Exception error) {
			error.printStackTrace();

			System.out.println("catch");

		}
		for (Fruit f1: fru) {
			String fruit_icon = ""; 
			if(f1.type==1)
				fruit_icon="./apple.png";
			if(f1.type==-1)
				fruit_icon="./banana.png";


			StdDraw.picture(f1.p.x(), f1.p.y(), fruit_icon);
		}
	}





	/**
	 *find where is the fruit on the edge.
	 * */
	public edge_data findEdgeFruit(Point3D pf,int type) {
		edge_data e=new EdgeData();

		Collection<node_data> nc = dgraph.getV();
		for (node_data n: nc ) {
			Point3D p1=n.getLocation(); 
			Collection<edge_data> ec = dgraph.getE(n.getKey());
			for (edge_data e1: ec) {
				Point3D p2 = dgraph.getNode(e1.getDest()).getLocation();
				if(p1.distance3D(pf)+pf.distance3D(p2)-p1.distance3D(p2)<epsilon)
					return e1;

			}

		}
		return e;
	}

	//return the number of robots.

	public int getnumR(game_service game) {
		int rn=0;
		String g=game.toString();
		JSONObject l;
		try {
			l=new JSONObject(g);
			JSONObject t = l.getJSONObject("GameServer");
			rn=t.getInt("robots");
		}
		catch (JSONException e) {e.printStackTrace();}
		return rn;
	}


	/**
	 *draw the graph.
	 * */
	private void drawGraph(graph G) {


		{
			StdDraw.setXscale(rx.get_min(),rx.get_max());
			StdDraw.setYscale(ry.get_min(),ry.get_max());
			StdDraw.setPenColor(Color.BLACK);
			for(node_data myNode:G.getV())
			{
				double x0=myNode.getLocation().x();
				double y0=myNode.getLocation().y();
				if(G.getE(myNode.getKey())!=null)
				{
					for(edge_data edge:G.getE(myNode.getKey()))
					{
						//size and color pen
						StdDraw.setPenRadius(0.0039); 
						StdDraw.setPenColor(Color.BLACK);

						double x1=G.getNode(edge.getDest()).getLocation().x();
						double y1=G.getNode(edge.getDest()).getLocation().y();

						//edges
						StdDraw.line(x0, y0, x1, y1);
						StdDraw.setPenRadius(0.02);

						//direction 
						StdDraw.setPenColor(Color.orange);
						StdDraw.point(x0*0.1+x1*0.9, y0*0.1+y1*0.9);

						//draw nodes
						StdDraw.setPenColor(Color.BLUE);
						StdDraw.point(x1, y1);

						//nodes key
						StdDraw.setPenColor(Color.BLACK);
						StdDraw.text(x0,y0 + epsilon, myNode.getKey()+"");


					}
				}
				StdDraw.setPenColor(Color.BLUE);
				StdDraw.point(x0, y0);

			}
		}
	}


	/**
	 *return the min and max of x point.
	 * */
	public Range FindXmaxmin(graph g) {

		double xmin=Double.MAX_VALUE; 
		double xmax=Double.MIN_VALUE; 
		Collection<node_data> nc = g.getV();
		for(node_data n: nc) {

			double px=n.getLocation().x();

			if(px>xmax) {
				xmax=px;
			}

			if(px<xmin) {
				xmin=px;
			}
		}

		return new Range(xmin-0.001,xmax+0.001);
	}


	/**
	 *return the min and max of y point.
	 * */
	public Range FindYmaxmin(graph g) {

		double ymin=Double.MAX_VALUE; 
		double ymax=Double.MIN_VALUE; 
		Collection<node_data> nc = g.getV();
		for(node_data n: nc) {

			double py=n.getLocation().y();

			if(py>ymax) {
				ymax=py;
			}

			if(py<ymin) {
				ymin=py;
			}
		}

		return new Range(ymin-0.001,ymax+0.001);
	}


	/**
	 *Pushes to String extension of .kml.
	 * */
	public static void startKML(String file_name) { 

		if(!file_name.endsWith(".kml") && !file_name.endsWith(".KML"))
			file_name += ".kml";
		KmlName = file_name;
		KML_Logger.createFile(file_name, numgame);
		KMLbool = true;

	}


	public static boolean KMLbool() {

		return KMLbool;

	}





/*
 * Information about the games - relation to myself or everyone.
 */
	public static void yourResults()
	{

		
		String[] options = {"In relation to myself", "In relation to everyone"};
		Object panel = JOptionPane.showInputDialog(null, " I want to see where I am stand", "Message", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if (panel == "In relation to myself")
		{
			String forID = "";
			int ID;
			forID = JOptionPane.showInputDialog(null, " What is your ID ?");
			try {
				ID = Integer.parseInt(forID);

				String forPrintAns = "";
				Class.forName("com.mysql.jdbc.Driver");
				Connection connect = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connect.createStatement();
				String query = "SELECT * FROM Logs WHERE UserID =" + ID + " ORDER BY levelID , moves;";
				ResultSet result = statement.executeQuery(query);

				//my level now
				while (result.next()) {
					forPrintAns += result.getInt("UserID") + "," + result.getInt("levelID") + "," + result.getInt("moves") + "," + result.getInt("score")+"E";
				}

				String forPrintRusults = "";
				String[] arr;
				int numOfGames = 0;

				int level = 0;
				int moves = -1;
				int grade = 0;

				boolean flag=false;
				int maxGrade=Integer.MIN_VALUE;
				int saveMove=0;
				String for1="",for3="",for5="",for9="",for11="",for13="",for16="",for20="",for19="",for23="";

				int index = 3;
				for (int i = 0; i < forPrintAns.length(); i++)
				{
					if (forPrintAns.charAt(i) == 'E')
					{
						numOfGames++;
						String help = forPrintAns.substring(index, i);
						index = i;
						arr = help.split(",");
						//  System.out.println(Arrays.deepToString(arr));

						switch(level) {
						case 0:
							if (grade >= 125 && moves <= 290)
							{
								if(!flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=true;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								}
								forPrintRusults= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 1:

							if (grade >= 436&& moves <= 580)
							{
								if(flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=false;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for1= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;

						case 3:

							if (grade >= 713&& moves <= 580)
							{ 
								if(!flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=true;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								}  
								for3= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 5:

							if (grade >= 570&& moves <= 500)
							{ 
								if(flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=false;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								}
								for5= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 9:

							if (grade >= 480&& moves <= 580)
							{ 

								if(!flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=true;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for9= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 11:

							if (grade >= 1050&& moves <= 580)
							{ 
								if(flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=false;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for11= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 13:

							if (grade >= 310&& moves <= 580)
							{ 
								if(!flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=true;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for13= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 16:

							if (grade >= 235&& moves <= 290)
							{ 
								if(flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=false;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for16= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 19:

							if (grade >= 250&& moves <= 580)
							{ 
								if(!flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=true;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for19= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 20:

							if (grade >= 200&& moves <= 290)
							{ 
								if(flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=false;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								} 
								for20= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						case 23:

							if (grade >= 1000&& moves <= 1140)
							{ 
								if(!flag)
								{
									maxGrade=Integer.MIN_VALUE;
									saveMove=0;
									flag=true;
								}
								if(maxGrade<grade)
								{
									maxGrade=grade;
									saveMove=moves;
								}  
								for23= "Level: " + level + " moves: " + saveMove + "  Grade: " + maxGrade + "\n";

							}

							break;
						}

						level = Integer.parseInt(arr[1]);
						moves = Integer.parseInt(arr[2]);
						grade = Integer.parseInt(arr[3]);
						
					}

				}
				forPrintRusults +="\n"+for1+"\n"+for3+"\n"+for5+"\n"+for9+"\n"+for11+"\n"+for13+"\n"+for16+"\n"+for20+"\n"+for19+"\n"+for23+"\n";
	
				forPrintRusults += "your level is " + level + " and so far you played " + numOfGames + " games.";
				JOptionPane.showMessageDialog(null, forPrintRusults);
				result.close();
				statement.close();
				connect.close();
			} catch (SQLException e)
			{
				e.getMessage();
				e.getErrorCode();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		if (panel == "In relation to everyone")
		{
			String forPrintAns = "";
			String StringID = JOptionPane.showInputDialog(null, "What is your ID ? ");
			try {
				int NumberID = Integer.parseInt(StringID);
				Connection connect  =DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
				Statement statement = connect.createStatement();
				String query  = "SELECT * FROM Logs ORDER BY levelID , score;";
				ResultSet result = statement.executeQuery(query);
				
				int myPlace = 1; 
				int level = 0; 
				int grade = 0; 
				
				String forPrintRusults = "";
				List<String> IDList = new ArrayList<>();
			

				while (result.next())
				{
					forPrintAns += ("Id: " + result.getInt("UserID") + "," + result.getInt("levelID") + "," + result.getInt("score") + "," + result.getDate("time") + "E");
				}

				int index = 0;
				boolean weCanPrint = false; 
				boolean notExistID = true;
				
				for (int i = 0; i < forPrintAns.length(); i++) 
				{
					if (forPrintAns.charAt(i) == 'E')
					{
						notExistID = true;
						String help = forPrintAns.substring(index, i);
						index = i;
						String[] arr = help.split(",");
						for (int j = 0; j < IDList.size(); j++)
						{
							if (IDList.get(j).equals(arr[0])) //return id
								notExistID = false;
							
						}
						
						if (notExistID)
						{
							IDList.add(arr[0]);
							myPlace++;
						}
						
						if (Integer.parseInt(arr[1]) != level && weCanPrint) //we finished to check this level 
						{
							forPrintRusults += "Level: " + level + " Grade: " + grade + "  your Place is:" + myPlace + "\n";
							myPlace = 1;
							level = Integer.parseInt(arr[1]);
							weCanPrint = false;
							IDList.clear();
						}
					
						if (arr[0].contains("" + NumberID)) //the id it's my id
							{
							level = Integer.parseInt(arr[1]);
							weCanPrint = true;
							grade = Integer.parseInt(arr[2]);
							myPlace = 1;
							IDList.clear();
						}

					}

				}

				JOptionPane.showMessageDialog(null, forPrintRusults);

			} catch (SQLException e)
			{
				e.getMessage();
				
				e.getErrorCode();
			} 
		}
	}

}

