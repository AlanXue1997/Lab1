
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;

/*
 * To do list
 * 		修复coordination函数有可能出现的顺序错误——不仅处理子节点，也处理所有子节点之前的节点
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import javax.swing.JLabel;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class DrawPanel extends JPanel{
	private GraphDraft G;
	public static final int ratio = 100;           //200   130
	public static final int size = 20;                //40   26
	public static final double arrow_len = 15;        //30    19.5
	public static final double arrow_H = 10;         //20      13
	public static final double arrow_L = 4;           //8       5.2
	private Dimension theSize = new Dimension(2000, 2000); 
	public void display(GraphDraft G){
		this.G = G;
		this.repaint();
	}
	public void paint(Graphics g){
		super.paint(g);
		if(this.G!=null){
			Graphics2D g2d = (Graphics2D)g;
			g2d.setFont(new Font("宋体",Font.BOLD,10));
			//draw point
			for(int i=0;i<G.points.length;i++){
				if(G.points[i].st != null){
					if(G.point_state[i]==0){
						g2d.drawOval(G.points[i].x*ratio-size,G.points[i].y*ratio-size,size*2,size*2);
						int index_string = G.points[i].x*ratio - G.points[i].st.length()*6/2;
						g2d.drawString(G.points[i].st,index_string,G.points[i].y*ratio);
					}
					else if(G.point_state[i]==1){
						g2d.setColor(Color.red);
						g2d.drawOval(G.points[i].x*ratio-size,G.points[i].y*ratio-size,size*2,size*2);
						int index_string = G.points[i].x*ratio - G.points[i].st.length()*6/2;
						g2d.drawString(G.points[i].st,index_string,G.points[i].y*ratio);
						g2d.setColor(Color.black);
					}
					else{
						g2d.setColor(Color.green);
						g2d.drawOval(G.points[i].x*ratio-size,G.points[i].y*ratio-size,size*2,size*2);
						int index_string = G.points[i].x*ratio - G.points[i].st.length()*6/2;
						g2d.drawString(G.points[i].st,index_string,G.points[i].y*ratio);
						g2d.setColor(Color.black);
					}
				}
			}
			
			//draw line
			for(int i=0;i<G.edges.length;i++){
				if(G.edges[i]==null)
					break;
				int p1 = G.edges[i].x;
				int p2 = G.edges[i].y;
				if(G.edges[i].type==1){
					//int[] index = GetArrow(G.points[p1].x*ratio,G.points[p1].y*ratio,G.points[p2].x*ratio,G.points[p2].y*ratio);
					
					if(G.edge_state[p1][p2]==0){
						int[] loc = new_loc(p1,p2,G);
						drawAL(loc[0],loc[1],loc[2],loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+loc[2])/2), (int)((loc[1]+loc[3])/2));
					}
					else{
						int[] loc = new_loc(p1,p2,G);
						g2d.setColor(Color.red);
						drawAL(loc[0],loc[1],loc[2],loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+loc[2])/2), (int)((loc[1]+loc[3])/2));
						g2d.setColor(Color.black);
					}
				}
				else if(G.edges[i].type==2){
					if(G.edge_state[p1][p2]==0&&G.edge_state[p2][p1]==0){
						int[] loc = new_loc(p1,p2,G);
						drawAL(loc[0]+5,loc[1],loc[2]+5,loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+5+loc[2]+5)/2), (int)((loc[1]+5+loc[3]+5)/2));
						drawAL(loc[2]-5,loc[3],loc[0]-5,loc[1],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight2),(int)((loc[0]-5+loc[2]-5)/2), (int)((loc[1]-5+loc[3]-5)/2));
					}
					else if(G.edge_state[p1][p2]==1){
						int[] loc = new_loc(p1,p2,G);
						g2d.setColor(Color.red);
						drawAL(loc[0]+5,loc[1],loc[2]+5,loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+5+loc[2]+5)/2), (int)((loc[1]+5+loc[3]+5)/2));
						g2d.setColor(Color.black);
						drawAL(loc[2]-5,loc[3],loc[0]-5,loc[1],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight2),(int)((loc[0]-5+loc[2]-5)/2), (int)((loc[1]-5+loc[3]-5)/2));
					}
					else if(G.edge_state[p2][p1]==1){
						int[] loc = new_loc(p1,p2,G);
						drawAL(loc[0]+5,loc[1],loc[2]+5,loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+5+loc[2]+5)/2), (int)((loc[1]+5+loc[3]+5)/2));
						g2d.setColor(Color.red);
						drawAL(loc[2]-5,loc[3],loc[0]-5,loc[1],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight2),(int)((loc[0]-5+loc[2]-5)/2), (int)((loc[1]-5+loc[3]-5)/2));
						g2d.setColor(Color.black);
					}
				}
				else if(G.edges[i].type==3){
					if(G.edge_state[p1][p2]==0){
						int[] loc = new_loc(p1,p2,G);
						g2d.drawLine(loc[0],loc[1],loc[2],loc[3]);
					}
					else{
						int[] loc = new_loc(p1,p2,G);
						g2d.setColor(Color.red);
						g2d.drawLine(loc[0],loc[1],loc[2],loc[3]);
						g2d.setColor(Color.black);
					}
				}
				else if(G.edges[i].type==4){
					if(G.edge_state[p1][p2]==0&&G.edge_state[p2][p1]==0){
						int[] loc = new_loc(p1,p2,G);
						g2d.drawLine(loc[0]+5,loc[1],loc[2]+5,loc[3]);
						g2d.drawLine(loc[2]-5,loc[3]-5,loc[0]-5,loc[1]-5);
					}
					else if(G.edge_state[p1][p2]==1){
						int[] loc = new_loc(p1,p2,G);
						g2d.setColor(Color.red);
						g2d.drawLine(loc[0]+5,loc[1],loc[2]+5,loc[3]);
						g2d.setColor(Color.black);
						g2d.drawLine(loc[2]-5,loc[3],loc[0]-5,loc[1]);
					}
					else if(G.edge_state[p2][p1]==1){
						int[] loc = new_loc(p1,p2,G);
						g2d.drawLine(loc[0]+5,loc[1],loc[2]+5,loc[3]);
						g2d.setColor(Color.red);
						g2d.drawLine(loc[2]-5,loc[3],loc[0]-5,loc[1]);
						g2d.setColor(Color.red);
					}
				}
				else{
					if(G.edge_state[p1][p2]==0&&G.edge_state[p2][p1]==0){
						int[] loc = new_loc(p1,p2,G);
						drawAL(loc[0]+5,loc[1],loc[2]+5,loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+5+loc[2]+5)/2), (int)((loc[1]+5+loc[3]+5)/2));
						g2d.drawLine(loc[2]-5,loc[3]-5,loc[0]-5,loc[1]-5);
					}
					else if(G.edge_state[p1][p2]==1){
						int[] loc = new_loc(p1,p2,G);
						g2d.setColor(Color.red);
						drawAL(loc[0]+5,loc[1],loc[2]+5,loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+5+loc[2]+5)/2), (int)((loc[1]+5+loc[3]+5)/2));
						g2d.setColor(Color.black);
						g2d.drawLine(loc[2]-5,loc[3],loc[0]-5,loc[1]);
					}
					else if(G.edge_state[p2][p1]==1){
						int[] loc = new_loc(p1,p2,G);
						drawAL(loc[0]+5,loc[1],loc[2]+5,loc[3],g2d);
						g2d.drawString(String.valueOf(G.edges[i].weight),(int)((loc[0]+5+loc[2]+5)/2), (int)((loc[1]+5+loc[3]+5)/2));
						g2d.setColor(Color.red);
						g2d.drawLine(loc[2]-5,loc[3],loc[0]-5,loc[1]);
						g2d.setColor(Color.black);
					}
				}
			}
			
			
		}
	}
	
	 public Dimension getPreferredSize() {
         return this.theSize;
	 }
	 
	 public static void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2)  
	    {  
	  
	        double H = arrow_H; // 箭头高度  
	        double L = arrow_L; // 底边的一半  
	        int x3 = 0;  
	        int y3 = 0;  
	        int x4 = 0;  
	        int y4 = 0;  
	        double awrad = Math.atan(L / H); // 箭头角度  
	        double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度  
	        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);  
	        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);  
	        double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点  
	        double y_3 = ey - arrXY_1[1];  
	        double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点  
	        double y_4 = ey - arrXY_2[1];  
	  
	        Double X3 = new Double(x_3);  
	        x3 = X3.intValue();  
	        Double Y3 = new Double(y_3);  
	        y3 = Y3.intValue();  
	        Double X4 = new Double(x_4);  
	        x4 = X4.intValue();  
	        Double Y4 = new Double(y_4);  
	        y4 = Y4.intValue();  
	        // 画线  
	        g2.drawLine(sx, sy, ex, ey);  
	        //  
	        GeneralPath triangle = new GeneralPath();  
	        triangle.moveTo(ex, ey);  
	        triangle.lineTo(x3, y3);  
	        triangle.lineTo(x4, y4);  
	        triangle.closePath();  
	        //实心箭头  
	        g2.fill(triangle);  
	        //非实心箭头  
	        //g2.draw(triangle);  
	  
	    }  
	  
	    // 计算  
	 public static double[] rotateVec(int px, int py, double ang,  
	            boolean isChLen, double newLen) {  
	  
	        double mathstr[] = new double[2];  
	        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度  
	        double vx = px * Math.cos(ang) - py * Math.sin(ang);  
	        double vy = px * Math.sin(ang) + py * Math.cos(ang);  
	        if (isChLen) {  
	            double d = Math.sqrt(vx * vx + vy * vy);  
	            vx = vx / d * newLen;  
	            vy = vy / d * newLen;  
	            mathstr[0] = vx;  
	            mathstr[1] = vy;  
	        }  
	        return mathstr;  
	    }  

	 //得到边的两端的坐标
	 public static int[] new_loc(int p1,int p2,GraphDraft G){
		 int[] loc= new int[4];
		 int x1 = G.points[p1].x*ratio;
		 int y1 = G.points[p1].y*ratio;
		 int x2 = G.points[p2].x*ratio;
		 int y2 = G.points[p2].y*ratio;
		 //p1为空
		 if(G.points[p1].st==null &&G.points[p2].st!=null){
			 loc[0] = x1;
			 loc[1] = y1;
			 if(x1!=x2&&y1!=y2){
				 double theta = Math.atan(Math.abs(y1-y2) / Math.abs(x1-x2)); // 箭头角度 
				 if(x1<x2 && y1<y2){
					 loc[2] = (int) (x2 - Math.cos(theta)*size);
					 loc[3] = (int) (y2 - Math.sin(theta)*size);
				 }
				 else if(x1<x2&&y1>y2){
					 loc[2] = (int) (x2 - Math.cos(theta)*size);
					 loc[3] = (int) (y2 + Math.sin(theta)*size);
				 }
				 else if(x1>x2&&y1<y2){
					 loc[2] = (int) (x2 + Math.cos(theta)*size);
					 loc[3] = (int) (y2 - Math.sin(theta)*size);
				 }
				 else{
					 loc[2] = (int) (x2 + Math.cos(theta)*size);
					 loc[3] = (int) (y2 + Math.sin(theta)*size);
				 }
			 }
			 else if(x1 == x2){
				 if(y1<y2){
					 loc[2] = x2;
					 loc[3] = y2 - size;
				 }
				 else{
					 loc[2] = x2;
					 loc[3] = y2 + size;
				 }
			 }
			 else{
				 if(x1<x2){
					 loc[2] = x2 - size;
					 loc[3] = y2;
				 }
				 else{
					 loc[2] = x2 + size;
					 loc[3] = y2;
				 }
			 }
		 }
		 else if(G.points[p1].st!=null &&G.points[p2].st ==null){
			 loc[2] = x2;
			 loc[3] = y2;
			 if(x1!=x2&&y1!=y2){
				 double theta = Math.atan(Math.abs(y1-y2) / Math.abs(x1-x2)); // 箭头角度 
				 
				 if(x1<x2 && y1<y2){
					 loc[0] = (int) (x1 + Math.cos(theta)*size);
					 loc[1] = (int) (y1 + Math.sin(theta)*size);
				 }
				 else if(x1<x2&&y1>y2){
					 loc[0] = (int) (x1 + Math.cos(theta)*size);
					 loc[1] = (int) (y1 - Math.sin(theta)*size);
				 }
				 else if(x1>x2&&y1<y2){
					 loc[0] = (int) (x1 - Math.cos(theta)*size);
					 loc[1] = (int) (y1 + Math.sin(theta)*size);
				 }
				 else{
					 loc[0] = (int) (x1 - Math.cos(theta)*size);
					 loc[1] = (int) (y1 - Math.sin(theta)*size);
				 }
			 }
			 else if(x1 == x2){
				 if(y1<y2){
					 loc[0] = x1;
					 loc[1] = y1 + size;
				 }
				 else{
					 loc[0] = x1;
					 loc[1] = y1 - size;
				 }
			 }
			 else{
				 if(x1<x2){
					 loc[0] = x1 + size;
					 loc[1] = y1;
				 }
				 else{
					 loc[0] = x1 - size;
					 loc[1] = y1;
				 }
			 }
		 }
		 else if(G.points[p1].st==null && G.points[p2].st==null){
			 loc[0] = x1;
			 loc[1] = y1;
			 loc[2] = x2;
			 loc[3] = y2;
		 }
		 
		 else{
			 if(x1!=x2&&y1!=y2){
				 double theta = Math.atan(Math.abs(y1-y2) / Math.abs(x1-x2)); // 箭头角度 
				 
				 if(x1<x2 && y1<y2){
					 loc[0] = (int) (x1 + Math.cos(theta)*size);
					 loc[1] = (int) (y1 + Math.sin(theta)*size);
					 loc[2] = (int) (x2 - Math.cos(theta)*size);
					 loc[3] = (int) (y2 - Math.sin(theta)*size);
				 }
				 else if(x1<x2&&y1>y2){
					 loc[0] = (int) (x1 + Math.cos(theta)*size);
					 loc[1] = (int) (y1 - Math.sin(theta)*size);
					 loc[2] = (int) (x2 - Math.cos(theta)*size);
					 loc[3] = (int) (y2 + Math.sin(theta)*size);
				 }
				 else if(x1>x2&&y1<y2){
					 loc[0] = (int) (x1 - Math.cos(theta)*size);
					 loc[1] = (int) (y1 + Math.sin(theta)*size);
					 loc[2] = (int) (x2 + Math.cos(theta)*size);
					 loc[3] = (int) (y2 - Math.sin(theta)*size);
				 }
				 else{
					 loc[0] = (int) (x1 - Math.cos(theta)*size);
					 loc[1] = (int) (y1 - Math.sin(theta)*size);
					 loc[2] = (int) (x2 + Math.cos(theta)*size);
					 loc[3] = (int) (y2 + Math.sin(theta)*size);
				 }
			 }
			 else if(x1 == x2){
				 if(y1<y2){
					 loc[0] = x1;
					 loc[1] = y1 + size;
					 loc[2] = x2;
					 loc[3] = y2 - size;
				 }
				 else{
					 loc[0] = x1;
					 loc[1] = y1 - size;
					 loc[2] = x2;
					 loc[3] = y2 + size;
				 }
			 }
			 else{
				 if(x1<x2){
					 loc[0] = x1 + size;
					 loc[1] = y1;
					 loc[2] = x2 - size;
					 loc[3] = y2;
				 }
				 else{
					 loc[0] = x1 - size;
					 loc[1] = y1;
					 loc[2] = x2 + size;
					 loc[3] = y2;
				 }
			 }
		 }
		 
		 return loc;
	 }
}


public class GUI {

	JFrame frame;
	Picture G;
	GraphDraft g;
	DrawPanel panel;
	String path;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	boolean flag;
	int now_step;
	int[] walks;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextArea textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	
	public void showDirectedGraph(Picture G)
	{
		return;
	}
	
	public String queryBridgeWords(String word1,String word2)
	{
		return null;
	}
	
	public String gernerateNewText(String inputText)
	{
		return null;
	}
	
	public String calcShortestPath(String word1,String word2)
	{
		return null;
	}
	
	public String randomWalk()
	{
		return null;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1339, 871);
		//frame.setBounds(0,0,2000,2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("选择文件");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
			     if(jfc.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION ){
			      G = new Picture(jfc.getSelectedFile().getAbsolutePath());
			      path = jfc.getSelectedFile().getAbsolutePath();
			      
			      //获取图的信息，里面包含所有需要画的点的信息和边的信息
			      g = G.getGraphDraft();
			      panel.display(g);
			      walks = G.randomWalk();
			      now_step = 1;
			      flag = false;
			      
			      //这里是如何读取一个点的信息，x和y是坐标，这个坐标只是相对位置，实际要放在哪要具体计算。
			      //普通的点st存储字符串内容。有些点st=null，这样的点不需要显示出来，但他们的坐标在画边时有用
			      /*
			      for(int i = 0; i < g.points.length; ++i){
			    	  System.out.println("Point " + i + ": (" + g.points[i].x + "," + g.points[i].y + ") st = " + g.points[i].st);
			      }
			      
			      for(int i = 0; i < g.edges.length; ++i){
			    	  if(g.edges[i] == null) break;
			    	  System.out.println("Edges " + i + ": " + g.edges[i].x + "-->" + g.edges[i].y + " Type: " + g.edges[i].type);
			      }*/
			     }
			}
		});
		btnNewButton.setBounds(1194, 7, 127, 39);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("第一个单词：");
		lblNewLabel.setBounds(905, 57, 84, 27);
		frame.getContentPane().add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(1016, 57, 105, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("第二个单词");
		lblNewLabel_1.setBounds(1141, 62, 65, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(1233, 57, 88, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("计算最短路");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word1 = textField_1.getText().replace(" ","");
				String word2 = textField_2.getText().replace(" ","");
				if(word2!=null&&!word2.equals("")){
					int[] result = G.shortestPath(word1,word2);
					
					for(int i=0;i<g.points.length;i++){
						g.point_state[i] = 0;
						for(int j=0;j<g.points.length;j++){
							g.edge_state[i][j] = 0;
						}
					}
					
					if(result[0]==0){
						textField_5.setText(word1+" and "+word2+" are not reachable!");
					}
					else if(result[0] ==-1){
						textField_5.setText(word1+" doesn't exist!");
					}
					else if(result[0]==-2){
						textField_5.setText(word2+" doesn't exist!");
					}
					else if(result[0]==-3){
						textField_5.setText(word1+" and "+word2+" doesn't exist!");
					}
					else{
						
						String short_path = g.points[result[1]].st;      //word1
						g.point_state[result[1]] = 1;
						for(int i=2;i<=result[0];i++){
							g.edge_state[result[i-1]][result[i]] = 1;
							if(g.points[result[i]].st!=null){
								g.point_state[result[i]] = 1;
								short_path += " => " + g.points[result[i]].st;
							}
						}
						short_path += " 路径长度为： " + result[result[0]+1];
						textField_5.setText(short_path);
						panel.display(g);
					}
				}
				else{
					boolean have = false;
					for(int i=0;i<g.points.length;i++){
						if(g.points[i].st!=null&&g.points[i].st.equals(word1)){
							have = true;
						}
					}
					if(have){
						int[] result = G.shortestPath(word1);
						String s = "";
						for(int i=0;i<result.length;i++){
							if(g.points[i].st!=null){
								s += g.points[i].st + ":" + result[i] + "\n";
							}
						}
						textField_5.setText(s);
					}
					else{
						textField_5.setText(word1+" doesn't exist!");
					}
				}
			}
		});
		btnNewButton_1.setBounds(1188, 107, 133, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("查询桥接词");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word1 = textField_1.getText().replace(" ","");
				String word2 = textField_2.getText().replace(" ","");
				for(int i=0;i<g.points.length;i++){
					if(g.points[i].st!=null&&(g.points[i].st.equals(word1)||g.points[i].st.equals(word2))){
						g.point_state[i] = 2;
					}
					else{
						g.point_state[i] = 0;
					}
					for(int j=0;j<g.points.length;j++){
						g.edge_state[i][j] = 0;
					}
				}
				int[] result = G.findBridge(word1, word2);
				if(result[0]==-1){
					textField_5.setText(word1+" doesn't exist!");
				}
				else if(result[0]==-2){
					textField_5.setText(word2+" doesn't exist!");
				}
				else if(result[0]==-3){
					textField_5.setText(word1+" and "+word2+" doesn't exist!");
				}
				else if(result[0]==-4){
					textField_5.setText("There is no bridge between them!");
				}
				else if(result[0]>0){
					String text = "";
					for(int i=0;i<result[0];i++){
						text += g.points[result[i+1]].st;
						text += "  ";
						g.point_state[result[i+1]] = 1;
					}
					textField_5.setText(text);
					panel.display(g);
				}
				else{
					
				}
			}
		});
		btnNewButton_2.setBounds(966, 107, 133, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(905, 431, 297, 74);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("提交");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String line = textField_3.getText();
				String result = G.newText(line);
				textField_4.setText(result);
			}
		});
		btnNewButton_3.setBounds(1216, 454, 105, 51);
		frame.getContentPane().add(btnNewButton_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(895, 568, 423, 133);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("查询结果");
		lblNewLabel_2.setBounds(905, 279, 65, 33);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("新生成文本");
		lblNewLabel_3.setBounds(895, 529, 88, 27);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_4 = new JButton("随机游走");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s;
				if(flag){
					s = textField_4.getText();
				}
				else{
					s = "";
				}
				while(g.points[walks[now_step]].st==null){
					now_step += 1;
					if(now_step > walks[0]){
						walks = G.randomWalk();
						now_step = 1;
						s = "";
						System.out.println("hahha");
					}
				}
				s += " " + g.points[walks[now_step]].st;
				now_step += 1;
				flag = true;
				if(now_step > walks[0]){
					walks = G.randomWalk();
					now_step = 1;
					flag = false;
				}
				textField_4.setText(s);
			}
		});
		btnNewButton_4.setBounds(1160, 727, 161, 41);
		frame.getContentPane().add(btnNewButton_4);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 877, 795);
		frame.getContentPane().add(scrollPane);
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(993, 186, 328, 204);
		frame.getContentPane().add(scrollPane2);
		
		JButton btnNewButton_5 = new JButton("保存图片");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int index1 = path.lastIndexOf("/");
					int index2 = path.lastIndexOf(".");
					path = path.substring(0,index1+1) + path.substring(index1+1,index2+1) + "jpg";
					BufferedImage myImage = new Robot().createScreenCapture(  
					        new Rectangle(frame.getX()+5,frame.getY()+20,scrollPane.getWidth(),scrollPane.getHeight()));
					ImageIO.write(myImage, "jpg", new File(path));
					System.out.println(path);
					
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {  
		            e2.printStackTrace();  
		        }  
			}
		});
		btnNewButton_5.setBounds(966, 6, 117, 40);
		frame.getContentPane().add(btnNewButton_5);
		
		panel = new DrawPanel();
		panel.setBounds(25, 25, 3000,3000);
		frame.getContentPane().add(panel);
		
		scrollPane.setViewportView(panel);
		
		textField_5 = new JTextArea();
		textField_5.setBounds(993, 186, 328, 204);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		scrollPane2.setViewportView(textField_5);
		
		
		//Icon pic = new ImageIcon("/Users/jethro/javawork/p1.png");
	}
}

class GraphDraft{
	public Point[] points;
	public int[] point_state;
	public Edge[] edges;
	public int[][] edge_state;
	
	GraphDraft(Point[] points, Edge[] edges){
		this.points = points;
		this.edges = edges;
		point_state = new int[points.length];
		edge_state = new int[points.length][points.length];
	}
}

class Edge{
	public int x, y, weight, weight2;
	public int type;
	Edge(int x, int y, int weight, int weight2, int type){
		this.type = type;
		this.weight = weight;
		this.weight2 = weight2;
		this.x = x; this.y = y;
	}
	Edge(int x, int y, int weight, int type){
		this.type = type;
		this.weight = weight;
		this.weight2 = 0;
		this.x = x; this.y = y;
	}
}

class Point{
	public int x, y;
	public String st;
	
	Point(int x, int y, String st){
		this.x = x; this.y = y;
		this.st = st;
	}
	
	public boolean isDummy(){
		return  st == null;
	}
}

class Picture{
	
	private int[] in_degree = new int[1000];
	private int[] selfedge = new int[1000];
	private int[] out_degree = new int[1000];
	private int[] out_degree_save = new int[1000];
	private int[][] edge = new int[1000][1000];
	private int[][] internal_map;
	private boolean[][] processed;
	private Table table = new Table();
	private GraphDraft graphDraft;
	int N, dummy;//N for head[], K for point[][]. After initializing, N denotes number of vertexes, K denotes number of edges.
	
	public Picture(String path)
	{
		N = 0;
		System.out.println(path);
		File file = new File(path);
		InputStream in = null;
		try{
			in = new FileInputStream(path);
			//in = new FileInputStream(path);
			StringBuilder stringBuilder = new StringBuilder();
			char ch;
			int n;
			int last_num = -1, now_num;
			while(true){
				ch = characterTransform(in.read());
				//System.out.print(ch);
				if(ch == 0){//file end
					if(stringBuilder.length() > 0){
						now_num = table.add(stringBuilder.toString(), N);
						if(now_num == N) N++;
						if(last_num == now_num) {
							selfedge[now_num]++;
						}
						else{
							tree_insert(last_num, now_num);
						}
					}
					break;
				}
				else if(ch == 32){
					if(stringBuilder.length() > 0){
						now_num = table.add(stringBuilder.toString(), N);
						//System.out.println(now_num);
						if(now_num == N) N++;
						if(last_num == now_num) {
							selfedge[now_num]++;
						}
						else{
							tree_insert(last_num, now_num);
						}
						last_num = now_num;
						stringBuilder.delete(0, stringBuilder.length());
					}
				}
				else if(ch != 1){
					stringBuilder.append(ch);
				}
			}
			in.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		/*
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				System.out.print(edge[i][j] + " ");
			}
			System.out.println();
		}*/
		for(int i = 0; i < N; ++i){
			out_degree_save[i] = out_degree[i];
		}
		sugiyama();
		//test();
	}
	
	private void test(){
		int[] a;
		/*
		a = findBridge("seek", "to"); System.out.println(a[0]);
		a = findBridge("to", "explore"); System.out.println(a[0]);
		a = findBridge("explore", "new"); System.out.println(a[0]);
		a = findBridge("new", "and"); System.out.println(a[0]);
		a = findBridge("and", "exciting"); System.out.println(a[0]);
		a = findBridge("exciting", "synergies"); System.out.println(a[0]);
		//System.out.println(newText("Seek to explore new and exciting synergies"));
		a = shortestPath("worlds", "civilizations");System.out.println(a[0]); 
		a = shortestPath("st", "strange"); System.out.println(a[0]);
		a = shortestPath("st", "strang"); System.out.println(a[0]);
		a = shortestPath("civilizations", "to"); System.out.println(a[0]);
		*/
		
		a = randomWalk(); System.out.println(a[0]);
		for(int i = 1; i <= a[0]; ++i){
			System.out.print(table.get(a[i]) + " ");
		}
		System.out.println();
	}
	
	public GraphDraft getGraphDraft(){
		return graphDraft;
	}
	
	//array[0]的值
	//大于等于0，表示桥接词的个数，0表示没有
	//-1:word1 doesn't exist
	//-2:word2 doesn't exist
	//-3:word1 and word2 don't exist
	//-4:not bridge
	public int[] findBridge(String st1, String st2){
		int[] array = new int[100];
		if(!Option.CaseSensitive){
			st1 = st1.toLowerCase();
			st2 = st2.toLowerCase();
		}
		int x = table.search(st1);
		int y = table.search(st2);
		if(x == -1 && y == -1){
			array[0] = -3;
			return array;
		}
		else if(x == -1){
			array[0] = -1;
			return array;
		}
		else if(y == -1){
			array[0] = -2;
			return array;
		}
		else{
			for(int i = 0; i < N; ++i){
				if(edge[x][i] > 0 && edge[i][y] > 0){
					array[++array[0]] = i;
				}
			}
			if(!Option.AvoidSelfedge){
				if(selfedge[x] > 0 && edge[x][y] > 0){
					array[++array[0]] = x;
				}
				if(selfedge[y] > 0 && edge[x][y] > 0){
					array[++array[0]] = y;
				}
			}
		}
		return array;
	}
	
	public String newText(String st){
		Random r = new Random();
		if(st == null || st.isEmpty()) return "";
		String[] strings = st.split(" ");
		int[] words;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(strings[0]);
		for(int i = 1; i < strings.length; ++i){
			stringBuilder.append(' ');
			words = findBridge(strings[i-1], strings[i]);
			if(words[0] > 0){
				stringBuilder.append(table.get(words[r.nextInt(words[0])+1]));
				stringBuilder.append(' ');
			}
			stringBuilder.append(strings[i]);
		}
		return stringBuilder.toString();
	}
	
	public int[] shortestPath(String st1){
		int x;
		int[] array = new int[1000];
		x = table.search(st1);
		if(x == -1){
			array[0] = -1;
			return array;
		}
		else{
			int[] father = new int[dummy];
			int[] count = new int[dummy];
			boolean[] vis = new boolean[dummy];
			for(int i = 0; i < dummy; ++i) { count[i] = 1000; vis[i] = false; }
			count[x] = 0;
			int seed = x;
			int n_seed = 1000;
			int n_count = 1000;
			boolean find = true;
			while(find){
				vis[seed] = true;
				System.out.println("Seed = "+ seed);
				if(seed < N){
					for(int i = 0; i < dummy; ++i){
						if(internal_map[seed][i] > 0 && count[i] > count[seed] + edge[seed][i]){
							count[i] = count[seed] + edge[seed][i];
							father[i] = seed;
						}					
					}
				}
				else{
					for(int i = 0; i < dummy; ++i){
						if(internal_map[seed][i] > 0) {	
							if(count[i] > count[seed]){
								count[i] = count[seed];
								father[i] = seed;
							}
							break;
						}
					}
				}
				find = false;
				for(int i = 0; i < dummy; ++i){
					if(!vis[i]){
						if(count[i] < n_count){
							n_count = count[i];
							n_seed = i;
							find = true;
						}
					}
				}
				seed = n_seed;
				n_count = 1000;
			}
			/*
			for(int i = 0; i < N; ++i){
				System.out.println("Point " + table.get(i) + ": " + count[i] + " " + father[i]);
			}*/
			
			return count;
		}
	}
	
	//0: not reachable
	//-1:word1 doesn't exist
	//-2:word2 doesn't exist
	//-3:word1 and word2 don't exist
	
	public int[] shortestPath(String st1, String st2){
		int x, y;
		int[] array = new int[1000];
		x = table.search(st1);
		y = table.search(st2);
		if(x == -1 && y == -1){
			array[0] = -3;
			return array;
		}
		else if(x == -1){
			array[0] = -1;
			return array;
		}
		else if(y == -1){
			array[0] = -2;
			return array;
		}
		else if(x == y){
			array[0] = 1;
			array[1] = x;
			return array;
		}
		else{
			int[] father = new int[dummy];
			int[] count = new int[dummy];
			boolean[] vis = new boolean[dummy];
			for(int i = 0; i < dummy; ++i) { count[i] = 1000; vis[i] = false; }
			count[y] = 0;
			int seed = y;
			int n_seed = 1000;
			int n_count = 1000;
			boolean find = true;
			while(find){
				vis[seed] = true;
				if(seed == x) break;
				find = false;
				//System.out.println("Seed = "+ seed);
				if(seed < N){
					for(int i = 0; i < dummy; ++i){
						if(internal_map[i][seed] > 0 && count[i] > count[seed] + edge[i][seed]){
							count[i] = count[seed] + edge[i][seed];
							father[i] = seed;
						}						
					}
				}
				else{
					for(int i = 0; i < dummy; ++i){
						if(internal_map[i][seed] > 0){
							if(count[i] > count[seed]){
								count[i] = count[seed];
								father[i] = seed;
							}
							break;
						}
					}
				}
				find = false;
				for(int i = 0; i < dummy; ++i){
					if(!vis[i]){
						if(count[i] < n_count){
							n_count = count[i];
							n_seed = i;
							find = true;
						}
					}
				}
				seed = n_seed;
				n_count = 1000;
			}
			/*
			for(int i = 0; i < N; ++i){
				System.out.println("Point " + table.get(i) + ": " + count[i] + " " + father[i]);
			}*/
			if(count[x] == 1000){
				array[0] = 0;
				return array;
			}
			int k = x;
			array[++array[0]] = x;
			while(x != y){
				x = father[x];
				//if(x < N) array[++array[0]] = x;
				array[++array[0]] = x;
			}
			array[array[0]+1] = count[k];
			return array;
		}
	}
	
	public int[] randomWalk(){
		Random r = new Random();
		int[] array = new int[1000];
		boolean[][] vis = new boolean[dummy][dummy];
		array[++array[0]] = r.nextInt(N);
		for(int i = 0; i < N; ++i) out_degree[i] = out_degree_save[i];
		int f_ck = -1;
		while(out_degree[array[array[0]]] > 0 || array[array[0]] >= N){
			if(array[array[0]] < N){
				int past = 0;
				for(int i = 0; i < dummy; ++i){
					//System.out.print("get one: ");
					 if(internal_map[array[array[0]]][i] == 1 && !vis[array[array[0]]][i]){
						 if(r.nextInt(out_degree[array[array[0]]]-past) == 0){
							 vis[array[array[0]]][i] = true;
							 out_degree[array[array[0]]]--;
							 array[++array[0]] = i;
							 //System.out.print("breaked... ");
							 break;
						 }
						 else{
							 past++;
						 }
					 }
					 //System.out.println(">>> " + past);
				}
			}
			else{
				for(int i = 0; i < dummy; ++i){
					if(internal_map[array[array[0]]][i] == 1){
						array[++array[0]] = i;
						break;
					}
				}
			}
			if(f_ck == array[array[0]]){
				System.out.println("hehe");
				break;
			}
			else{
				f_ck = array[array[0]];
			}
			//System.out.println(table.get(array[array[0]]) + ": " + out_degree[array[array[0]]]);
		}
		int past = 0;
		out_degree[array[array[0]]] = out_degree_save[array[array[0]]];
		for(int i = 0; i < dummy; ++i){
			 if(internal_map[array[array[0]]][i] == 1){
				 if(r.nextInt(out_degree[array[array[0]]]-past) == 0){
					 out_degree[array[array[0]]]--;
					 array[++array[0]] = i;
					 break;
				 }
			 }
		}
		while(array[array[0]] >= N){
			for(int i = 0; i < dummy; ++i){
				if(internal_map[array[array[0]]][i] == 1){
					array[++array[0]] = i;
					break;
				}
			}
		}
		return array;
	}
	
	private int[] layer, order, order2, orders, orders2;
	private int[][] layers;
	private void sugiyama(){
		//1.Greedy cycle removal
		int[] mark = new int[1000];
		int[][] map = new int[1000][1000];
		
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				if(edge[i][j] > 0){
					map[i][j] = 1;
				}
				else{
					map[i][j] = 0;
				}
			}
		}
		boolean flag = true;
		while(flag){
			flag = false;
			boolean found = true;
			while(found){
				found = false;
				for(int i = 0; i < N; ++i){
					if(mark[i] == 0 && out_degree[i] == 0){
						found = true;
						mark[i] = 2;
						for(int j = 0; j < N; ++j){
							if(map[j][i] > 0 && out_degree[j] > 0) {
								out_degree[j]--;
							}
						}
					}
				}
			}
			found = true;
			while(found){
				found = false;
				for(int i = 0; i < N; ++i){
					if(mark[i] == 0 && in_degree[i] == 0){
						found = true;
						mark[i] = 1;
						for(int j = 0; j < N; ++j){
							if(map[i][j] > 0  && in_degree[j] > 0){
								in_degree[j]--;
							}
						}
					}
				}
			}
			int max_difference = -2000, max_vertex = 0;
			for(int i = 0; i < N; ++i){
				if(mark[i] == 0){
					flag = true;
					if(out_degree[i] - in_degree[i] > max_difference){
						max_difference = out_degree[i] - in_degree[i];
						max_vertex = i;
					}
				}
			}
			if(flag){
				mark[max_vertex] = 1;
				for(int j = 0; j < N; ++j){
					if(map[j][max_vertex] == 1 && mark[j] == 0){
						map[j][max_vertex] = 0;
						map[max_vertex][j] = 1;
						in_degree[j]++;
						out_degree[j]--;
						in_degree[max_vertex]--;
						out_degree[max_vertex]++;
					}
				}
				for(int j = 0; j < N; ++j){
					if(map[max_vertex][j] > 0 && in_degree[j] > 0){
						in_degree[j]--;
					}
				}
				//in_degree[max_vertex] = 0;
				//out_degree[max_vertex] = 0;
			}
		}
		/*
		for(int i = 0; i < N; ++i) if(mark[i] == 1) System.out.print(i + " ");
		System.out.println();
		for(int i = 0; i < N; ++i) if(mark[i] == 2) System.out.print(i + " ");
		System.out.println();
		*/
		/*
		int[][] map = new int[1000][1000];
		for(int i = 0; i < N; ++i){
			for(int j = i; j < N; ++j){
				if(edge[i][j] > 0){
					if(mark[i] == 2 && mark[j] == 1) map[j][i] = 1;
					else map[i][j] = 1;
				}
				else if(edge[j][i] > 0){
					if(mark[j] == 2 && mark[i] ==1) map[i][j] = 1;
					else map[j][i] = 1;
				}
			}
		}*/
		
		/*
		for(int i = 0;i  < N; ++i){
			for(int j = 0; j < N; ++j){
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}*/
		
		//2.The Longest-Path Algorithm
		layers = new int[1000][100];
		internal_map = new int[1000][1000];
		layer = new int[1000];
		int current_layer = 0;
		flag = true;
		//int NNN = 1000;
		dummy = N;
		while(flag){
			//if(NNN-- < 0) break;
			current_layer++;
			flag = false;
			for(int i = 0; i < N; ++i){
				if(layer[i] == 0){
					flag = true;
					boolean found = true;
					for(int j = 0; j < N; ++j){
						if(map[j][i] == 1)
							if(layer[j] == 0 || layer[j] == current_layer) { found = false; break; }
					}
					if(found){
						layer[i] = current_layer;
						layers[current_layer][++layers[current_layer][0]] = i;
						//System.out.println(current_layer + " " + layers[current_layer][0] + " " + layers[current_layer][layers[current_layer][0]] + " " + i);
						for(int j = 0; j < N; ++j){
							if(map[j][i] == 1){
								if(layer[j] == current_layer-1){
									internal_map[j][i] = 1;
								}
								else{
									layers[layer[j]+1][++layers[layer[j]+1][0]] = dummy;
									layer[dummy] = layer[j]+1;
									internal_map[j][dummy++] = 1;
									for(int k = layer[j]+2; k < current_layer; ++k){
										layers[k][++layers[k][0]] = dummy;
										layer[dummy] = k;
										internal_map[dummy-1][dummy] = 1;
										dummy++;
									}
									internal_map[dummy-1][i] = 1;
								}
							}
						}
					}
				}
			}
			/*
			System.out.print("Layer " + current_layer + ": ");
			for(int i = 1; i <= layers[current_layer][0]; ++i) System.out.print(layers[current_layer][i] + " ");
			System.out.println();
			*/
		}
		
		//log
		/*
		for(int i = 0; i < N; ++i){
			System.out.println(table.get(i) + ": " + layer[i]);
		}
		for(int i = N; i < dummy; ++i){
			System.out.println(i + ": " + layer[i]);
		}*/
		
		//System.out.println(N);
		//3.Vertex ordering
		//System.out.println("currentlayer = " + current_layer);
		for(int l = 2; l < current_layer; ++l){
			int first_change = 1;
			while(first_change < dummy){
				int i = first_change+1;
				first_change = dummy;
				for(; i <= layers[l][0]; ++i){
					int Cuv = 0, Cvu = 0;
					int flag_u = 0, flag_v = 0;
					for(int j = 1; j <= layers[l-1][0]; ++j){
						if(internal_map[layers[l-1][j]][layers[l][i-1]] == 1) Cuv += flag_v;
						if(internal_map[layers[l-1][j]][layers[l][i]] == 1) Cvu += flag_u;
						if(internal_map[layers[l-1][j]][layers[l][i]] == 1) flag_v++;
						if(internal_map[layers[l-1][j]][layers[l][i-1]] == 1) flag_u++;
					}
					//if(layers[l][i-1] == 9 && layers[l][i] == 13) System.out.println("Cuv = " + Cuv + ", Cvu = " + Cvu);
					//System.out.println(layers[l][i-1] + " " + layers[l][i]);
					if(Cuv > Cvu){
						int temp = layers[l][i-1];
						layers[l][i-1] = layers[l][i];
						layers[l][i] = temp;
						if(first_change == dummy) first_change = i;
					}
				}
			}
		}
		/*
		for(int i = 1; i < current_layer; ++i){
			System.out.print("Layers " + i + ": ");
			for(int j = 1; j <= layers[i][0]; ++j){
				System.out.print(layers[i][j] + " ");
			}
			System.out.println();
		}
		/*
		for(int i = 0; i < N; ++i){
			System.out.println(i + ": " + table.get(i));
		}*/
		
		//4。X coordination
		orders = new int[1000];//record most right position indeed
		orders2 = new int[1000];
		order = new int[1000];
		order2 = new int[1000];
		int[] true_order = new int[1000];
		for(int i = 1; i <= layers[1][0]; ++i){
			coordination(layers[1][i], 1, current_layer-1);
		}
		for(int i = layers[1][0]; i > 0; --i){
			coordination2(layers[1][i], 1, current_layer-1);
		}
		int max = 0;
		for(int i = 0; i < current_layer; ++i) if(orders2[i] > max) max = orders2[i];
		max++;
		for(int i = 0; i < dummy; ++i){
			true_order[i] = (max + order[i] - order2[i])/2;
		}
		
		//log
		/*
		for(int i = 0; i < N; ++i){
			System.out.println(i + ": (" + true_order[i] + "," + layer[i] + ")");
		}
		for(int i = N; i < dummy; ++i){
			System.out.println(i + ": (" + true_order[i] + "," + layer[i] + ")");
		}
		
		for(int i = 0; i < N; ++i){
			System.out.println(i + ": (" + order[i] + "," + layer[i] + ")");
		}
		for(int i = N; i < dummy; ++i){
			System.out.println(i + ": (" + order[i] + "," + layer[i] + ")");
		}
		for(int i = 0; i < N; ++i){
			System.out.println(i + ": (" + (max-order2[i]) + "," + layer[i] + ")");
		}
		for(int i = N; i < dummy; ++i){
			System.out.println(i + ": (" + (max-order2[i]) + "," + layer[i] + ")");
		}
		*/
		Point[] points = new Point[dummy];
		for(int i = 0; i < N; ++i){
			points[i] = new Point(true_order[i], layer[i], table.get(i));
		}
		for(int i = N; i < dummy; ++i){
			points[i] = new Point(true_order[i], layer[i], null);
		}
		Edge[] edges = new Edge[N*N+dummy];
		int k = 0;
		
		processed = new boolean[dummy][dummy];
		
		for(int i = 0; i < N; ++i){
			for(int j = i; j < N; ++j){
				if(internal_map[i][j] > 0 || internal_map[j][i] > 0){
					if(edge[i][j] > 0){
						internal_map[i][j] = 1;
						if(edge[j][i] > 0){
							edges[k++] = new Edge(i, j, edge[i][j], edge[j][i], 2);
							internal_map[j][i] = 1;
						}
						else{
							edges[k++] = new Edge(i, j, edge[i][j], 1);
							internal_map[j][i] = 0;
						}
					}
					else{
						internal_map[j][i] = 1;
						internal_map[i][j] = 0;
						edges[k++] = new Edge(j, i, edge[j][i], 1);
					}
				}
			}
			for(int j = N; j < dummy; ++j){
				if(internal_map[i][j] > 0){
					Point p;
					if(!processed[j][i]){
						p = findDirection(i, j);
						processed[i][j] = true;
						processed[j][i] = true;
						edge[i][j] = p.x;
						edge[j][i] = p.y;
					}
					else{
						p = new Point(edge[i][j], edge[j][i], "balabala");
					}
					if(p.x > 0 && p.y > 0){
						edges[k++] = new Edge(j, i, edge[j][i], edge[i][j], 5);
						internal_map[i][j] = 1;
						internal_map[j][i] = 1;
					}
					else if(p.x > 0){
						edges[k++] = new Edge(i, j, edge[i][j], 3);
						internal_map[i][j] = 1;
						internal_map[j][i] = 0;
					}
					else if(p.y > 0){
						edges[k++] = new Edge(j, i, edge[j][i], 1);
						internal_map[i][j] = 0;
						internal_map[j][i] = 1;
					}
					else{
						System.out.print("Confused1?????????/doge ");
						System.out.print("(" + i + "<" + table.get(i) + ">," + j + "<" + table.get(j) + ">) ");
						System.out.println(" p.x = " + p.x + ", p.y = " + p.y + " " + p.st);
					}
				}
				else if(internal_map[j][i] > 0){
					Point p;
					if(!processed[i][j]){
						p = findDirection2(i, j);
						processed[i][j] = true;
						processed[j][i] = true;
						edge[i][j] = p.x;
						edge[j][i] = p.y;
					}
					else{
						p = new Point(edge[i][j], edge[j][i], null);
					}
					if(p.x > 0 && p.y > 0){
						edges[k++] = new Edge(j, i, edge[j][i], edge[i][j], 5);
						internal_map[i][j] = 1;
						internal_map[j][i] = 1;
					}
					else if(p.x > 0){
						edges[k++] = new Edge(i, j, edge[i][j], 3);
						internal_map[i][j] = 1;
						internal_map[j][i] = 0;
					}
					else if(p.y > 0){
						edges[k++] = new Edge(j, i, edge[j][i], 1);
						internal_map[i][j] = 0;
						internal_map[j][i] = 1;
					}
					else{
						System.out.print("Confused2?????????/doge ");
						System.out.print("(" + i + "<" + table.get(i) + ">," + j + "<" + table.get(j) + ">) ");
						System.out.println(" p.x = " + p.x + ", p.y = " + p.y + " " + p.st);
					}
					/*
					if(p.x > 0 && p.y > 0){
						edges[k++] = new Edge(j, i, edge[j][i], edge[i][j], 5);
						internal_map[j][i] = 1;
						internal_map[i][j] = 1;
					}
					else if(p.x > 0){
						edges[k++] = new Edge(j, i, edge[j][i], 1);
						internal_map[j][i] = 1;
						internal_map[i][j] = 0;
					}
					else if(p.y > 9){
						edges[k++] = new Edge(i, j, edge[i][j], 3);
						internal_map[j][i] = 0;
						internal_map[i][j] = 1;
					}
					else{
						System.out.println("Confused2?????????/doge");
					}*/
				}
			}
		}/*
		for(int i = 0; i < N; ++i){
			for(int j = N; j < dummy; ++j){
				if(internal_map[j][i] > 0 && processed[j][i]){
					
				}
			}
		}*/
		for(int i = N; i < dummy; ++i){
			for(int j = i; j < dummy; ++j){
				if(internal_map[i][j] > 0 && internal_map[j][i] > 0){
					edges[k++] = new Edge(i, j, edge[i][j], edge[j][i], 4);
				}
				else if(internal_map[i][j] > 0){
					edges[k++] = new Edge(i, j, edge[i][j], 3);
				}
				else if(internal_map[j][i] > 0){
					edges[k++] = new Edge(j, i, edge[j][i], 3);
				}
			}
		}
		for(int i = 0; i < N; ++i){
			if(selfedge[i] > 0){
				edges[k++] = new Edge(i, i, selfedge[i], 1);
			}
		}
		
		/*
		for(int i = 0; i < k; ++i){
			if(edges[i].type == 1 || edges[i].type == 3){
				if(edge[edges[i].x][edges[i].y] <= 0){
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
			else{
				if(edge[edges[i].x][edges[i].y] <= 0 || edge[edges[i].y][edges[i].x] <= 0){
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
		}*/
		
		graphDraft = new GraphDraft(points, edges);
	}
	
	private Point findDirection2(int x, int y){
		if(y < N){
			return new Point(edge[x][y], edge[y][x], null);
		}
		else{
			for(int i = 0; i < dummy; ++i){
				if(internal_map[i][y] > 0){
					//System.out.println(y + ", " + i + " " + N);
					Point p = findDirection2(x, i);
					processed[y][i] = true;
					edge[y][i] = p.x;
					edge[i][y] = p.y;
					if(p.x > 0 && p.y > 0){
						internal_map[y][i] = 1;
						internal_map[i][y] = 1;
					}
					else if(p.x > 0){
						internal_map[y][i] = 1;
						internal_map[i][y] = 0;
					}
					else{
						internal_map[y][i] = 0;
						internal_map[i][y] = 1;
					}
					return p;
				}
			}
			return new Point(edge[x][y], edge[y][x], null);
		}
	}
	
	private Point findDirection(int x, int y){
		//System.out.println("Method findDirection encountered " + x + "," + y);
		if(y < N){
			return new Point(edge[x][y], edge[y][x], null);
		}
		else{
			for(int i = 0; i < dummy; ++i){
				if(internal_map[y][i] > 0){
					//System.out.println(y + ", " + i + " " + N);
					Point p = findDirection(x, i);
					processed[y][i] = true;
					edge[y][i] = p.x;
					edge[i][y] = p.y;
					if(p.x > 0 && p.y > 0){
						internal_map[y][i] = 1;
						internal_map[i][y] = 1;
					}
					else if(p.x > 0){
						internal_map[y][i] = 1;
						internal_map[i][y] = 0;
					}
					else{
						internal_map[y][i] = 0;
						internal_map[i][y] = 1;
					}
					return p;
				}
			}
			return new Point(edge[x][y], edge[y][x], null);
		}
	}
	
	private int coordination(int point, int father, int max_layer){
		int current_coordination;
		int current_layer = layer[point];
		//System.out.print("PPPPPPPP----point = " + point);
		//System.out.println(" father = " + father);
		if(orders[current_layer] < father){
			current_coordination = father;
		}
		else{
			current_coordination = orders[current_layer]+1;
		}
		if(current_layer < max_layer){
			int nxt_layer = current_layer+1;
			int max_left = 1000;
			int max_son = 0;
			for(int i = 1; i <= layers[nxt_layer][0]; ++i){
				if(internal_map[point][layers[nxt_layer][i]] == 1){
					max_son = i;
				}
			}
			for(int i = 1; i <= max_son; ++i){
				if(order[layers[nxt_layer][i]] == 0){
					coordination(layers[nxt_layer][i], current_coordination, max_layer);
				}
				if(max_left > order[layers[nxt_layer][i]]){
					max_left = order[layers[nxt_layer][i]];
				}
			}
			if(max_left < 1000 && max_left > current_coordination) current_coordination = max_left;
		}
		//System.out.print("PPPPPPPP----coordination = " + current_coordination);
		orders[current_layer] = current_coordination;
		order[point] = current_coordination;
		//System.out.println("Point " + point + " is at (" + order[point] + "," + layer[point] + ")");
		return current_coordination;
	}
	
	private int coordination2(int point, int father, int max_layer){
		//System.out.println(point);
		int current_coordination;
		int current_layer = layer[point];
		//System.out.print("PPPPPPPP----point = " + point);
		//System.out.println(" father = " + father);
		if(orders2[current_layer] < father){
			current_coordination = father;
		}
		else{
			current_coordination = orders2[current_layer]+1;
		}
		if(current_layer < max_layer){
			int nxt_layer = current_layer+1;
			int max_left = 1000;
			int max_son = 1;
			for(int i = layers[nxt_layer][0]; i > 0; --i){
				if(internal_map[point][layers[nxt_layer][i]] == 1){
					max_son = i;
				}
			}
			for(int i = layers[nxt_layer][0]; i >= max_son; --i){
				//System.out.println("Coordination2 encountered " + layers[nxt_layer][i]);
				if(order2[layers[nxt_layer][i]] == 0){
					coordination2(layers[nxt_layer][i], current_coordination, max_layer);
				}
				if(max_left > order2[layers[nxt_layer][i]]){
					max_left = order2[layers[nxt_layer][i]];
				}
			}
			if(max_left < 1000 && max_left > current_coordination) current_coordination = max_left;
		}
		//System.out.print("PPPPPPPP----coordination = " + current_coordination);
		orders2[current_layer] = current_coordination;
		order2[point] = current_coordination;
		//System.out.println("Point " + point + " is at (" + order2[point] + "," + layer[point] + ")");
		return current_coordination;
	}
	
	private void tree_insert(int x, int y){
		if(x < 0) return;
		edge[x][y]++;
		if(edge[x][y] == 1){
			out_degree[x]++;
			in_degree[y]++;
		}
	}
	
	//0:eof, 1:omit
	private char characterTransform(int ch){
		//System.out.println(ch);
		if (ch < 0) return 0;
		else if(ch == 10 || ch == 13) return 32;
		else if(ch <= 31) return 1;
		else if(ch <= 47) return 32;
		else if(ch <= 57) return (char) ch;
		else if(ch <= 64) return 32;
		else if(ch <= 90) {
			if(Option.CaseSensitive) return (char) ch;
			else return (char) (ch+32);
		}
		else if(ch <= 96) return 32;
		else if(ch <= 122) return (char) ch;
		else if(ch <= 176) return 32;
		else return 1;
	}
}

class Table{
	private String[] strings_hash = new String[1000];
	private String[] strings = new String[1000];
	private int[] order = new int[1000];
	
	public Table(){
		
	}
	
	public int add(String st, int num){ // This method will return the same value with search if st already exists. 
		int k = hash(st);
		//System.out.println("k = " + k);
		//System.out.println("strings_hash[k] = " + strings_hash[k] + ' ' + st);
		while(strings_hash[k] != null && !strings_hash[k].equals(st)){
			k++;
			if(k == 1000) k = 0;//!!!!!!!!!!!!!!!!!!!!!!!Attention!!!!!!!!!!!!!!!!!! Limited to 1000
		}
		//System.out.println("k = " + k);
		if(strings_hash[k] == null){
			strings_hash[k] = st;
			strings[num] = st;
			order[k] = num;
			return num;
		}
		else{
			return order[k];
		}
	}
	
	public int search(String st){
		int k = hash(st);
		while(strings_hash[k] != null && !strings_hash[k].equals(st)){
			k++;
			if(k == 1000) k = 0;//!!!!!!!!!!!!!!!!!!!!!!!Attention!!!!!!!!!!!!!!!!!! Limited to 1000
		}
		if(strings_hash[k] == null) return -1;
		else return order[k];
	}
	
	public String get(int num){
		return strings[num];
	}
	
	private int hash(String st){
		//int x = 0;
		//for(int i = 0; i < st.length(); ++i) x += st.charAt(i);
		return Math.abs(st.hashCode() % 1000);
	}
}

class Option{
	public Option(){}
	
	public static boolean CaseSensitive = false;
	public static boolean AvoidSelfedge = false;
	
}
