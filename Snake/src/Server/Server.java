package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Server, send values from the database
 * @author Boldi
 *
 */
public class Server implements Runnable {

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(4000);
			System.out.println("Waiting for connection...");
			while (true) {
				socket = serverSocket.accept();
				System.out.println("Connected");

				ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream dis = new ObjectInputStream(socket.getInputStream());
				List<Points> points = Database.getDatabase();
				for (Points p : points) {
					dos.writeObject(p);
					dos.flush();
				}
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// socket close
				socket.close();
				serverSocket.close();
				System.out.println("server closed!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
