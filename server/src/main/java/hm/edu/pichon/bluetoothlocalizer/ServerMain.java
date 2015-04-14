package hm.edu.pichon.bluetoothlocalizer;

import java.io.IOException;


public class ServerMain {

	public static void main(String... args) throws IOException, InterruptedException {
		SaveDataServer server = new SaveDataServer();
		server.start();
		server.join();
	}	
}
