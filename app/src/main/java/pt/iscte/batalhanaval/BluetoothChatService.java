package pt.iscte.batalhanaval;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;

/**
 * Created by pedro on 24/05/2017.
 */

public class BluetoothChatService {


    private int mState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    public BluetoothChatService(Context context, Handler handler){

    }

    public synchronized int getState() {
        return mState;
    }


    public synchronized void start() {

    }


    public synchronized void stop() {

    }

    public void write(byte[] out) {

    }

    public void connect(BluetoothDevice device, boolean secure) {
    }
}
