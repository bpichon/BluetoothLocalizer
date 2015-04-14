package pichon.hm.edu.bluetoothlocalizer.client;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import hm.edu.pichon.bluetoothlocalizer.interfaces.TestConnectionRequest;
import hm.edu.pichon.bluetoothlocalizer.interfaces.TestConnectionResponse;

/**
 * Created by bernd on 02.04.2015.
 */
public class SendDataTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Socket socket = new Socket("192.168.178.10", 80);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            TestConnectionRequest testConnectionRequest = new TestConnectionRequest();
            objectOutputStream.writeObject(testConnectionRequest);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object responseRaw = objectInputStream.readObject();
            TestConnectionResponse response = (TestConnectionResponse) responseRaw;
            System.out.println(response.getMessage());
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();

        } catch(UnknownHostException e) {
            System.out.println("Unknown host: www.example.com");
        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
