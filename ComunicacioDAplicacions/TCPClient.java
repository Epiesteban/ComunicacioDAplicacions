import java.io.*;
import java.net.*;
import java.lang.Exception;

class TCPClient {
	public static void main(String argv[]) throws Exception {
		final int MIN_PORT_NUMBER = 1024;
		final int MAX_PORT_NUMBER = 49151;
		String sentence;
		String modifiedSentence;
		String adresa = argv[0];
		
		try {
			int port = Integer.parseInt(argv[1]);
			try {
				if ((port < MIN_PORT_NUMBER) || (port > MAX_PORT_NUMBER))
					throw new IllegalArgumentException();
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

				Socket clientSocket = new Socket(adresa, port); // li estem dient que per crear la comunicació amb el
																// servidor li passem l'adreça ip que es localhost
																// perque
																// es el meu ordinador pel port 6789
				clientSocket.setSoTimeout(5000);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				sentence = inFromUser.readLine();

				do {

					try {
						outToServer.writeBytes(sentence + '\n');

						modifiedSentence = inFromServer.readLine();

						System.out.println("FROM SERVER: " + modifiedSentence);

						sentence = inFromUser.readLine();
					} catch (SocketTimeoutException e) {
						System.out.println(e);
						sentence = "BUENAS NOCHES";
						outToServer.writeBytes(sentence + "\n");
					}

				} while (!sentence.toUpperCase().equals("BUENAS NOCHES"));

				outToServer.writeBytes(sentence + '\n');

				System.out.println("Conversa finalitzada.");
				clientSocket.close();
			} catch (IllegalArgumentException e) {
				System.out.println("El port no es correcte. Error: " + e);
			}

		} catch (NumberFormatException e) {
			System.out.println("Existeix error en el format del valor. Error: " + e);
		}

	}
}