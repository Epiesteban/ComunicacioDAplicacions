
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class AttendPetition extends Thread {
	Socket connectionSocket;
	private int elemRand = 0;
	private boolean iniciada;
	Random rand = new Random(System.currentTimeMillis());
	private String[] taula = { "D'Antons, Joseps i ases, n'hi ha per totes les cases.", "T'ofegues en un got d'aigua",
			"Aixo ja passa de taca d'oli", "Em treus de polleguera", "Al final sabras el pa que s'hi dona",
			"Nomes fas que arregplegar les cireres i escampar la farina", "Et quedaras per vestir sants",
			"Preneu i bebeu-ne tots, aquest es el calze de la meva sang. La sang de l'aliansa nova i eterna vesada per tots vosaltres en remissio dels pecats. Feu aixo que es el meu memorial",
			"No serveixes ni a Deu ni al dimoni", "No m'expliquis sopars de duro!", "El mes calent es a l'aiguera",
			"Vaig brut com una guilla", "Me'n vaig a fer de cos", "Has arribat a misses dites",
			"Amb un suc de mandarina tinc la meva gasolina, una torradeta amb mel, me la menjo i toco el cel. Super, super esmorza" };

	public AttendPetition(Socket s) {
		this.connectionSocket = s;
	}

	public void run() {
		String clientSentence;
		String returnSentence;

		BufferedReader inFromClient;

		try {
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			// a partir d'aqui fer canvis
			clientSentence = "";
			while (!clientSentence.toUpperCase().equals("BUENAS NOCHES") || !iniciada) {
				clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);

				if (!clientSentence.toUpperCase().equals("BUENAS NOCHES") || !iniciada) {
					elemRand = rand.nextInt(taula.length);
					returnSentence = ContestaBesugo(clientSentence, iniciada, taula, elemRand);
					if ((returnSentence.toUpperCase().equals("BUENOS DIAS")
							|| (returnSentence.toUpperCase().equals("BUENAS TARDES")))) {
						iniciada = true;
					}
					outToClient.writeBytes(returnSentence + "\n");
				}
			}
			connectionSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String ContestaBesugo(String input, boolean iniciada, String[] taula, int cont) {

		if ((input.toUpperCase().equals("BUENOS DIAS")) && (!iniciada)) {
			input = "Buenas Tardes";
		} else {
			if ((input.toUpperCase().equals("BUENAS TARDES")) && (!iniciada)) {
				input = "Buenos Dias";
			} else {
				if (iniciada) {
					input = taula[cont];
				}

				else {
					input = "";
				}
			}
		}

		return input;
	}
}