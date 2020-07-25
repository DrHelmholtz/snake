package Server;

/**
 * Class, that launch the server, that send to the leaderboards the old scores
 * @author Boldi
 *
 */
public class ServerMain {

	public static void main(String[] args) {
	
			Thread t = new Thread(new Server());
			t.start();
		}
}
