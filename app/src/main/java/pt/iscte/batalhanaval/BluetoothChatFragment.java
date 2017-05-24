package pt.iscte.batalhanaval;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BluetoothChatFragment extends Fragment {

    private static final String TAG = "BluetoothChatFragment";

    //Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    //Name of the connected device
    private String mConnectedDeviceName = null;

    //Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;

    //String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;

    //Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;

    //Member object for the chat services
    private BluetoothChatService mChatService = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //If the adapter is null, then Bluetooth is not supported
        if(mBluetoothAdapter == null){
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available",Toast.LENGTH_LONG).show();

            activity.finish();
        }
    }

    public void onStart(){
        super.onStart();
        //If bleutooth is not on, request it
        //setupChat() will then be called during onActivityResult
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else if(mChatService == null){
            setupChat();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mChatService != null){
            mChatService.stop();
        }
    }

    public void onResume(){
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if(mChatService != null){
            //Only if the state is STATE_NONE, do we know that we haven't started already
            if(mChatService.getState() == BluetoothChatService.STATE_NONE){
                //Start BT chat services
                mChatService.start();
            }
        }
    }

    //public View onCreateView
    //public void OnView Created

    private void setupChat(){

        /**
         *
         * TODO: CHECK IF NEEDED
         *
        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);

        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                View view = getView();
                if (null != view) {
                    TextView textView = (TextView) view.findViewById(R.id.edit_text_out);
                    String message = textView.getText().toString();
                    sendMessage(message);
                }
            }
        });
         *
         */

        //Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(getActivity(),mHandler);

        //Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    //Makes this device discoverable for 5 minutes
    private void ensureDiscoverable(){
        if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
            Intent discoverableIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    //Send a message
    private void sendMessage(String message){
        //Check that we're actually connected before trying anything
        if(mChatService.getState() != BluetoothChatService.STATE_CONNECTED){
            Toast.makeText(getActivity(),"You're not connected to a device",Toast.LENGTH_LONG).show();
            return;
        }

        //Gets the message bytes and tells the BluetoothChat Service to write
        byte[] send = message.getBytes();
        mChatService.write(send);
    }

    /**
     * The action listener for the EditText widget, to listen for the return key
     * TODO: Check if is needed
    private TextView.OnEditorActionListener mWriteListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };
    */

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     *TODO: CHECK
    private void setStatus(int resId) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }
    */


    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     * TODO: CHECK
    private void setStatus(CharSequence subTitle) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }
    */

    //The Handler that get information back from the BluetootchChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = getActivity();
            switch (msg.what){
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1){
                        case BluetoothChatService.STATE_CONNECTED:
                            //TODO: setStatus("connected to ${device name}");
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            //TODO: setStatus("connecting...");
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                            //TODO: setStatus("listening...");
                            break;
                        case BluetoothChatService.STATE_NONE:
                            //TODO: setStatus("not connected");
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf =(byte[]) msg.obj;
                    //Construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me: " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    //construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf,0,msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName+": "+readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    //save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if(null != activity){
                        Toast.makeText(activity, "Connected to "+mConnectedDeviceName,Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if(null!=activity){
                        Toast.makeText(activity,msg.getData().getString(Constants.TOAST),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_CONNECT_DEVICE_SECURE:
                //When DeviceListActivity returns with a device to connect
                if(resultCode == Activity.RESULT_OK){
                    connectDevice(data,true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                //When deviceListActivity returns with a device to connect
                if(resultCode == Activity.RESULT_OK){
                    connectDevice(data,false);
                }
            case REQUEST_ENABLE_BT:
                //When the request to enable Bluetooth returns
                if(resultCode==Activity.RESULT_OK){
                    //Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    Toast.makeText(getActivity(),"Bluetooth was not enabled. Leaving Multiplayer.",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
        }
    }

    //Establish a connection to other device
    private void connectDevice(Intent data, boolean secure) {
        //Get the device MAC adress
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        //Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        //Attempt to connect to the device
        mChatService.connect(device,secure);
    }

    //public void onCreateOptionsMenu
    //public void onOptionsItemSelected

}
