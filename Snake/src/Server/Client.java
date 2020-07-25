package Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client, it will communicate to the server
 * @author Boldi
 *
 */
public class Client implements Runnable{
	protected static List<Points> lp;
	private static boolean done=false;
	@Override
	public void run() {
		Socket socket = new Socket();
		InetSocketAddress socketAddress;
		done=false;
		try {
			socketAddress = new InetSocketAddress(InetAddress.getLocalHost(),4000);			
			socket.connect(socketAddress, 10000);

			ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream dis = new ObjectInputStream(socket.getInputStream());
			Points p=new Points();
			boolean check=true;
			
			while(check)
			{	
				try{
					p = (Points)dis.readObject();
					lp.add(p);
				   } catch(EOFException ex){
				       check=false;
				   }	
			}			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {				
				socket.close();
				done=true;
				System.out.println("closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	/**
	 * Get values from the database
	 * @return List for the leaderboards
	 */
	public static List<Points> getPoints(){
		lp=new ArrayList<>();
		Thread t = new Thread(new Client());
		t.start();
		while(!done){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return lp;
	}
	
}
