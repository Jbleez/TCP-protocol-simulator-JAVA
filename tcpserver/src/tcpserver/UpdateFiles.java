
package tcpserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*; 
import java.util.ArrayList;

public class UpdateFiles extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 200;
	private static final int FRAME_X_ORIGIN = 550;
	private static final int FRAME_Y_ORIGIN = 250;
	
	private static final int BUTTON_WIDTH = 0;//300;
	private static final int BUTTON_HEIGHT = 0;//30;
	
	//JButton setting
	protected JButton updateButton_one;
	protected JButton sendServerFile;
	protected JButton receiveData;
	
	protected JTextArea textField_one;
        protected JTextArea fileNameFromRouter;
	protected JLabel labelForTitle;
	
	//file 
	JFileChooser chooser;
	String choosertitle;
	
	//File manager
	String nameOfFile = null;
	
	public UpdateFiles(){
		
		//JFrame frame = new JFrame();
		//layout manager
		//Container contentPane = frame.getContentPane();
                Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
                
		//window
		setTitle ("Distributed Computing Project One");
		setSize  (FRAME_WIDTH, FRAME_HEIGHT);
		setLocation (FRAME_X_ORIGIN, FRAME_Y_ORIGIN);
		
                 fileNameFromRouter = new JTextArea(1,20);
		contentPane.add(fileNameFromRouter);
		//label
		labelForTitle = new JLabel("Please update the files:");
		contentPane.add(labelForTitle);
		
               
                
		//text areas and related buttons
		textField_one = new JTextArea(4,40);
		contentPane.add(textField_one);
		updateButton_one = new JButton("Select File");
		updateButton_one.setName("fileOneSelect");
		updateButton_one.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		contentPane.add( updateButton_one);
           
		//buttons
		sendServerFile = new JButton("Send");
		sendServerFile.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		sendServerFile.setName("sending");
		contentPane.add(sendServerFile);
		
                sendServerFile.setEnabled(false);
		
		//buttons
		receiveData = new JButton("Start");
		receiveData.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		receiveData.setName("Start");
		contentPane.add(receiveData);
	
		
		//exit
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
}
        
   

