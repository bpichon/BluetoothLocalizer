package hm.edu.pichon.bluetoothlocalizer;

import java.io.IOException;
import java.util.ArrayList;


public class ServerMain {

	public static void main(String... args) throws IOException, InterruptedException {
        testSQLite();
        /*SaveDataServer server = new SaveDataServer();
		server.start();
		server.join();*/
	}

    private static void testSQLite() {
        DatabaseHelper z = DatabaseHelper.getInstance();
        z.insertDevice("testDeviceId_0", 20);
        z.printAll();
    }
}
