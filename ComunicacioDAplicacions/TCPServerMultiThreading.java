
import java.net.*;

class TCPServerMultiThreading {
	public static void main(String argv[]) throws Exception {
		final int MIN_PORT_NUMBER = 1024;
		final int MAX_PORT_NUMBER = 49151;
		try {
			int port = Integer.parseInt(argv[0]);
			try {
			if ((port < MIN_PORT_NUMBER) || (port > MAX_PORT_NUMBER))
				throw new IllegalArgumentException();
			ServerSocket welcomeSocket = new ServerSocket(port);
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				new AttendPetition(connectionSocket).start();
			}
			} catch (IllegalArgumentException e) {
				System.out.println("El port no es correcte. Error: "+e);
			}
		} catch (NumberFormatException e) {
			System.out.println("El valor no és compatible amb un port. Error: "+e);
		}
	}
}