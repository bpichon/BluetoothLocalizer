package pichon.hm.edu.bluetoothlocalizer.client;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pichon.hm.edu.bluetoothlocalizer.R;


public class MainActivity extends ActionBarActivity implements Button.OnClickListener {

    private BluetoothAdapter scanner;
    private Handler handler;
    private TextView debugOutput;
    private BroadcastReceiver broadcastReceiver;
    private List<BluetoothDevice> discoveredDevices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debugOutput = (TextView) findViewById(R.id.debugOutline);
        debugOutput.setMovementMethod(new ScrollingMovementMethod());

        Button testConnectionButton = (Button) findViewById(R.id.startLocalizationButton);
        testConnectionButton.setOnClickListener(this);

        scanner = BluetoothAdapter.getDefaultAdapter();
        broadcastReceiver = new BluetoothDiscoverBroadcastReceiver();

        // Registriere Broadcast Action Receives
        final IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, intentFilter);

        // Scanndurchlauf starten
        scanner.startDiscovery(); // TODO: what if null. Give error.

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0){ // TODO: to constant
                    debugOutput.setText(debugOutput.getText() + "\n" + ((String) msg.obj));
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        SendDataTask task = new SendDataTask();
        task.execute();
    }

    private class BluetoothDiscoverBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            final Message m = new Message();
            m.what = 0;

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("a", "Gerät gefunden: " + device.getName());
                m.obj = "Gerät gefunden: " + device.getName();
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d("a", "discovery ended");
                m.obj = "Suche beended.";
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d("a", "discovery Started");
                m.obj = "Suche gestartet.";
            }
            handler.handleMessage(m);
        }
    }
}
