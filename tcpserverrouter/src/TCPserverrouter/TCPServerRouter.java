/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPserverrouter;

import java.io.*; 
import java.lang.Character;
import java.net.*; 
import java.util.ArrayList;
	  
public class TCPServerRouter { 
    
    //Build the Routing Table - here, if you so wish //
	//put ip imformation into routing table
    String[][] ipArray = new String[4][3];  
    String ipArray1 = "9999";

    // client IP
    String ipaddress = ""; 
    
	
    DatagramPacket receivePacket = null;   
    DatagramSocket serverSocket = null;
    DatagramSocket outgoingServerSocket = null;    
    byte[] receiveData = new byte[50000]; 
    byte[] sendData = new byte[50000]; 
    byte[] sendDataBackToClient;
 
    ArrayList<String> arr = new ArrayList<String>();
    InetAddress Client;  
    String ClientIP = "";
    
    InetAddress IPAddressForServer;
    int port;
    int ClientPort;
    
    /**
     * default constructor
     * @throws UnknownHostException 
     */
    
    public TCPServerRouter() throws UnknownHostException {    	
       
        
     
    }
       
    /**
     * receives data from clients and prepare it for forwarding
     * 
     * @return void
     */
    public void getAndBuildData() throws Exception {
     
       while(true) 
       {
    	    
             ServerSocket listener = null;
             Socket socket = null;
     try
    {
           listener = new ServerSocket(4712);
         socket = listener.accept();
           System.out.println("ENTREEEZ");
          
           int port =0;
 port = socket.getPort();
         InputStream in = socket.getInputStream();
      
         int len = toInt(in);
       
         System.out.println(len);
         File outputFile;
        String file_name = new String();
       
     
        
      if(port == 58584)
           {
                 try {
                       file_name = toString(in,len);
                       System.out.println(file_name); 

                       listener.close();
                       
                      Socket soc = new Socket("localhost", 21212);
                      OutputStream os = soc.getOutputStream();
                      toStream(os, file_name);
                      
              
                    }
    
    catch (IOException ex) {
          ex.printStackTrace();
    }   
           }
           else
           {
                try {
              
             System.out.println("ENTREEEZ");   
             String fname =toString(in,len); 
             System.out.print(fname);
             

             File myfile = new File(fname);
                 listener.close();
              
              Socket soc = new Socket("localhost", 58584);
             OutputStream os = soc.getOutputStream();
             
            FileInputStream inputStream = new FileInputStream(myfile); 
            byte[] mybytearray = new byte[(int) myfile.length()];
            
      BufferedInputStream bis = new BufferedInputStream(inputStream);
      
      bis.read(mybytearray, 0, mybytearray.length);
      System.out.println("Sending " + fname + "(" + mybytearray.length + " bytes)");
      os.write(mybytearray, 0, mybytearray.length);
   
         soc.close();

      }
    
    catch (IOException ex) {
         ex.printStackTrace();
    }   
           }
 
    }
     catch (Exception ex)
    {
      ex.printStackTrace();
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

  private static String toString(InputStream ins, int len) throws java.io.IOException 
  {
    String ret=new String();
    for (int i=0; i<len;i++) 
    { 
      ret+=(char)ins.read();
     
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

    while (  len-total_len_read >= 0) {
      len_read = ins.read(buffer);
      total_len_read += len_read;
     
      fos.write(buffer, 0, len_read);
      
     
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

 
    public static void main(String args[]) throws Exception { 
    	
    	TCPServerRouter udpServer = new TCPServerRouter();
        udpServer.getAndBuildData();
    }

    /**
     * determines which interface to bind to.
     * @param int link - the interface
     * @param byte message - the message to be sent
     * @param Object ipAddress - the ip address the message to be sent to.
     * @param String ports - the port number
     * 
     * @return void
     */
    public void Router( int link, DatagramPacket message, InetAddress ipAddress,
        String ports ) throws Exception {
        
        int port = Integer.parseInt( ports );

        switch(link) {
            case 0:
                this.sendMessage( message, ipAddress, port );
                break;
                          
            case 1:
                this.sendMessage( message, ipAddress, port );
                break;
            
            case 2:
                this.sendMessage( message, ipAddress, port );
                break;
            
            case 3:
                this.sendMessage( message, ipAddress, port );
                break;
            
            case 4:
                this.sendMessage( message, ipAddress, port );
                break;
            
            case 5:
                this.sendMessage( message, ipAddress, port );
                break;
                
            case 6:
                this.sendMessage( message, ipAddress, port );
                break;
                
            case 7:
                this.sendMessage( message, ipAddress, port );
                break;
            
            
            default : 
                System.out.println("No interface to bind to");
                break;
	}
        
    }
    
    /**
     * sends the message to the destination IP
     * @param byte message - the data to be sent
     * @param Object ipAddress - the destination ip address
     * @param int port - the port number
     *
     * @return void
     */
    public void sendMessage( DatagramPacket message,  InetAddress ipAddress, int port ) {
        
        try {
            outgoingServerSocket = new DatagramSocket(port);
        
        }catch( SocketException e ) {
            System.out.println( e.getMessage() );
        }
        
        try {
        	    
            DatagramPacket sendPacket = new DatagramPacket( message.getData(), message.getLength(), 
                ipAddress, port );
            outgoingServerSocket.send(sendPacket);


        }catch( IOException e ) {
            System.out.println( e.getMessage() );
        }
    }
    
}
