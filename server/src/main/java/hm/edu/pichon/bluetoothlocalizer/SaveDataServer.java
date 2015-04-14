package hm.edu.pichon.bluetoothlocalizer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import hm.edu.pichon.bluetoothlocalizer.interfaces.PositionRequest;
import hm.edu.pichon.bluetoothlocalizer.interfaces.Request;
import hm.edu.pichon.bluetoothlocalizer.interfaces.SaveDeviceListRequest;
import hm.edu.pichon.bluetoothlocalizer.interfaces.TestConnectionRequest;
import hm.edu.pichon.bluetoothlocalizer.interfaces.TestConnectionResponse;


public class SaveDataServer extends Thread {

	ServerSocket serverSocket;
	
	public SaveDataServer() throws IOException {
		serverSocket = new ServerSocket(80);
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Object requestRaw = objectInputStream.readObject();
				Request request = (Request) requestRaw;
				
				processRequest(request, socket);
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void processRequest(Request request, Socket socket) {
		if (request.isGET()) {
			if (request instanceof PositionRequest) {
				// TODO: berechne und sende position.
				
			}
			else if (request instanceof TestConnectionRequest) {
				try {
					TestConnectionResponse r = new TestConnectionResponse();
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
					objectOutputStream.writeObject(r);
					objectOutputStream.flush();
					socket.close();
                    System.out.println("TestConnectionPaket auf Server angekommen.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		} else if (request.isPUT()) {
			if (request instanceof SaveDeviceListRequest) {
				// m�chte daten speichern.
				SaveDeviceListRequest saveDeviceListRequest = (SaveDeviceListRequest) request;
				System.out.println("deviceList count: " + saveDeviceListRequest.deviceList.size());
				// TODO: speichere Daten in db
			}
		}
	}
	
	public static void readDB() {
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/soccer", "root", "");
			
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM USER");
			
			while (r.next()) {
				System.out.println(r.getString("first_name") + ", " + r.getString("email"));
			}
			
			c.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
