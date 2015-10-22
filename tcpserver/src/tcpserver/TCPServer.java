
package tcpserver;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*; 
import java.lang.*;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
  
class TCPServer implements ActionListener {

  private final int port = 21212;

  private Socket socket = null;
  
   ServerSocket listener = null;
     File fileChosen;
   
   String fileName = "no File requested";
   UpdateFiles myFrame ;
  
  public TCPServer()
  {
	
      myFrame = new UpdateFiles();
       myFrame.updateButton_one.addActionListener(this);
	myFrame.sendServerFile.addActionListener(this);
        myFrame.receiveData.addActionListener(this);
      myFrame.setVisible(true);
  
    
  }

  public void receivingData() throws IOException
  {
       
     
     try
    {
         listener= new ServerSocket(port);
            System.out.println("listen");
         socket = listener.accept();
         InputStream in = socket.getInputStream();
         int len = toInt(in);
             System.out.println("ENTREEEZ");   
        setFile(toString(in,len));
         socket.close(); 
       listener.close();
       
    }
     catch (Exception ex)
    {
      System.out.println("fuck one");
    }
  }
  public void setFile(String s)
  {
      fileName = s;
  }
  
  public void sendingData() throws IOException
  {
    try {
             
      Socket socket = new Socket("localhost", 4712);
      OutputStream os = socket.getOutputStream();
   
      FileInputStream inputStream = new FileInputStream(fileChosen); 

            toStream(os,fileChosen.getPath());
                      os.close();
                     socket.close();
      }
    catch (IOException ex) {
        System.out.println("fuck one");
    }
  }
  //add listener in it
	public void actionPerformed(ActionEvent event){
    	myFrame.chooser = new JFileChooser();
    	
		JButton clickedButton = (JButton)event.getSource();
		if(clickedButton.getName() == "sending")
	    {	
	    	//start sending data
			try {
				sendingData();
                                clickedButton.setEnabled(false);
                                myFrame.receiveData.setEnabled(true);
			} catch (IOException e) 
                        {
				// TODO Auto-gener9ated catch block
				System.out.println("non recu server");
			}
	    }
		
		if(clickedButton.getName() == "Start")
		{
			//start sending data
			try {
				receivingData();
                                myFrame.fileNameFromRouter.setText(fileName);
                                clickedButton.setEnabled(false);
                                myFrame.sendServerFile.setEnabled(true);
                                myFrame.repaint();
                               
			} catch (IOException e) 
                        {
				// TODO Auto-gener9ated catch block
				System.out.println("non recu server");
			}
		}
		
	    if(clickedButton.getName() == "fileOneSelect")
	    {
	    	//could add filter -- fieNameExtensionFileter
			int returnVal = myFrame.chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
                            fileChosen = myFrame.chooser.getSelectedFile();
				String pathOfFile = myFrame.chooser.getSelectedFile().getPath();
				myFrame.nameOfFile = myFrame.chooser.getSelectedFile().getName();
                                myFrame.textField_one.setText(pathOfFile);	
		}
			
	  }
        }
     private static byte[] toByteArray(int in_int) {
    byte a[] = new byte[4];
    for (int i=0; i < 4; i++) {

      int  b_int = (in_int >> (i*8) ) & 255;
      byte b = (byte) ( b_int );
 
      a[i] = b;
     }
    return a;
  }

  private static int toInt(byte[] byte_array_4) {
    int ret = 0;  
    for (int i=0; i<4; i++) {
      int b = (int) byte_array_4[i];
      if (i<3 && b<0) {
        b=256+b;
      }
      ret += b << (i*8);
    }
    return ret;
  }

  public static int toInt(InputStream in) throws java.io.IOException {
    byte[] byte_array_4 = new byte[4];

    byte_array_4[0] = (byte) in.read();
    byte_array_4[1] = (byte) in.read();
    byte_array_4[2] = (byte) in.read();
    byte_array_4[3] = (byte) in.read();

    return toInt(byte_array_4);
  }

  public static String toString(InputStream ins) throws java.io.IOException {
    int len = toInt(ins);
    return toString(ins, len);
  }

  private static String toString(InputStream ins, int len) throws java.io.IOException {
    String ret=new String();
    for (int i=0; i<len;i++) {
      ret+=(char) ins.read();
    }
    return ret;
  }
  
  public static void toStream(OutputStream os, int i) throws java.io.IOException {
    byte [] byte_array_4 = toByteArray(i);
    os.write(byte_array_4);
  }

  public static void toStream(OutputStream os, String s) throws java.io.IOException {
    int len_s = s.length();
    toStream(os, len_s);
    for (int i=0;i<len_s;i++) {
      os.write((byte) s.charAt(i));
    }
    os.flush();
  }

  private static byte[] toByteArray(InputStream ins, int an_int) throws 
    java.io.IOException,  
    Exception{

    byte[] ret = new byte[an_int];

    int offset  = 0;
    int numRead = 0;
    int outstanding = an_int;

    while (
       (offset < an_int)
         &&
      (  (numRead = ins.read(ret, offset, outstanding)) > 0 )
    ) {
      offset     += numRead;
      outstanding = an_int - offset;
    }
    if (offset < ret.length) {
      throw new Exception("Could not completely read from stream, numRead="+numRead+", ret.length="+ret.length); // ???
    }
    return ret;
  }

  private static void toFile(InputStream ins, FileOutputStream fos, int len, int buf_size) throws 
        java.io.FileNotFoundException, 
        java.io.IOException {

    byte[] buffer = new byte[buf_size];

    int       len_read=0;
    int total_len_read=0;

    while ( total_len_read + buf_size <= len) {
      len_read = ins.read(buffer);
      total_len_read += len_read;
      fos.write(buffer, 0, len_read);
    }

    if (total_len_read < len) {
      toFile(ins, fos, len-total_len_read, buf_size/2);
    }
  }

  private static void toFile(InputStream ins, File file, int len) throws 
        java.io.FileNotFoundException, 
        java.io.IOException {

    FileOutputStream fos=new FileOutputStream(file);

    toFile(ins, fos, len, 1024);
  }

  public static void toFile(InputStream ins, File file) throws 
        java.io.FileNotFoundException, 
        java.io.IOException {

    int len = toInt(ins);
    toFile(ins, file, len);
  }

  public static void toStream(OutputStream os, File file) 
      throws java.io.FileNotFoundException,
             java.io.IOException{

    toStream(os, (int) file.length());

    byte b[]=new byte[1024];
    InputStream is = new FileInputStream(file);
    int numRead=0;

    while ( ( numRead=is.read(b)) > 0) {
        
      os.write(b, 0, numRead);
    }
    os.flush();
  }
   
 public static void main(String args[])
   {
       TCPServer u = new TCPServer();
   }
} 
      




