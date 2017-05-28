package pt.iscte.batalhanaval;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimerTask;



import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    //private String TAG = "GameActivity";
    private Button goBtn;
    private Button quitBtn;
    private Button findBtn;
    private Button connectBtn;
    private TextView help, debug;
    public static final int ENABLE_BT__REQUEST = 1;
    public static final int ENABLE_BT__REQUEST2 = 2;
    private List<Integer> mBuffer = new ArrayList<>();
    private List<String> mResponseBuffer = new ArrayList<>();
    private ArrayAdapter<String> mResponsesAdapter;
    private static String position;
    private BluetoothAdapter meuBluetoothadapter;
    static String deviceMac;

    private int myBoatsDisplay = 99, plays = 0, successfulShots = 0, successfulShots2 = 0;

    private Boolean multiplayer, myTurn = true;

    private int[] myPlays, othersPlays;
    private Player p2, mainPlayer;

    private int numbShots, dificulty;

    /*****************************
     * New Bluetooth
     *****************************/
    private SmoothBluetooth mSmoothBluetooth;


    /*****************************
     * FROM BLUETOOTHCHATFRAGMENT, TRYING TO MERGE BOTH
     *****************************/

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

    /****************
     * DONE PASTE
     ******************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String intentString = getIntent().getStringExtra("BOATS_DISPLAY");
        String intentString2 = getIntent().getStringExtra("MULTIPLAYER");

        try {
            myBoatsDisplay = Integer.parseInt(intentString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "An error occured.", Toast.LENGTH_SHORT).show();
            Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
            GameActivity.this.startActivity(registerIntent);
        }

        if (intentString2.equals("ON")) multiplayer = true;
        else multiplayer = false;

        myPlays = new int[100];
        othersPlays = new int[100];

        for (int i = 0; i < 100; i++) {
            myPlays[i] = 0;
            othersPlays[i] = 0;
        }

        help = (TextView) findViewById(R.id.helpTxt);
        debug = (TextView) findViewById(R.id.debug);

        goBtn = (Button) findViewById(R.id.go);
        goBtn.setOnClickListener(this);

        findBtn = (Button) findViewById(R.id.findBtn);
        findBtn.setOnClickListener(this);

        quitBtn = (Button) findViewById(R.id.quit);
        quitBtn.setOnClickListener(this);

        connectBtn = (Button) findViewById(R.id.connect);
        connectBtn.setOnClickListener(this);

        cleanGrid();

        mainPlayer = new Player(myBoatsDisplay, -1);

        Random rand = new Random();

        if (!multiplayer) {

            String x = getIntent().getStringExtra("DIFICULTY");
            int n = Integer.parseInt(x);
            dificulty = n;
            int botDisplay = rand.nextInt(5)+1;
            p2 = new Player(botDisplay, myBoatsDisplay);

            help.setText("My: " + myBoatsDisplay + " His: "+botDisplay);

            goBtn.setVisibility(View.INVISIBLE);
            myTurn = true;

        } else {

            /*******************************
             *      MULTIPLAYER STUFF
             ******************************/
            connectBtn.setVisibility(View.VISIBLE);
            findBtn.setVisibility(View.VISIBLE);

            //Get local Bluetooth adapter
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            //If the adapter is null, then Bluetooth is not supported
            if(mBluetoothAdapter == null){
                //FragmentActivity activity = getActivity();
                Toast.makeText(GameActivity.this, "Bluetooth is not available",Toast.LENGTH_LONG).show();

                this.finish();
            }

            ensureDiscoverable();

            //This here is another approach
            //mSmoothBluetooth = new SmoothBluetooth(this);
            //mSmoothBluetooth.setListener(mListener);

            /**************************
             *     FINISH MULTI
             *************************/
        }

        debug.setText("Pontuação: \nTu: " + successfulShots + "/14\nAdversário: " + successfulShots2 + "/14");

        //Loading the boats to paint as gray on player's grid
        int[] boats = mainPlayer.getMyBoats();
        for (int i = 0; i < boats.length; i++) {
            othersPlays[boats[i]] = 5;
        }
    }

    public void onClick(View v) {

        if (v.equals(goBtn)) {

            setupGrid(R.string.yourTurn, myPlays);
            myTurn = true;
            goBtn.setVisibility(View.INVISIBLE);
            //sendMessage("Testing 1, zzzzZZzz");

        } else if (v.equals(quitBtn)) {

            quitGame();

        } else if (v.equals(connectBtn)) {

            /*Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(GameActivity.this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
            mSmoothBluetooth.tryConnection();*/

        } else if (v.equals(findBtn)) {

            //mSmoothBluetooth.tryConnection();


        } else {

            if (!myTurn) return;

            int pressedId = v.getId();

            if (myPlays[pressedId] != 0) return;

            numbShots++;

            if(!multiplayer){
                boolean result = p2.getShot(pressedId);
                markShot(1, pressedId, result);

                if (numbShots > 2) {
                    setupGrid(R.string.othersTurn, othersPlays);
                    myTurn = false;
                    SimulatePC();
                }
                return;
            }

            myTurn = false;
            help.setText("Aguardando feedback tiro.");
            sendMessage(BTMessages.sendShot(pressedId));

        }
    }

    private void SimulatePC() {
        Log.d("GameActivity","Simulating PC");
        for (int i = 0; i < 3; i++) {
            int pcShot = -1;
            while (pcShot == -1) {
                pcShot = p2.shot(dificulty);
            }
            boolean answer = mainPlayer.getShot(pcShot);
            markShot(2, pcShot, answer);
            //p2.receiveFeedback(pcShot,answer);
        }
        numbShots = 0;
        goBtn.setVisibility(View.VISIBLE);
        help.setText(R.string.pressPlay);
    }

    public void markShot(int matrix, int shot, boolean success) {
        int[] playerGrid = othersPlays;

        if (matrix == 1) playerGrid = myPlays;

        //String cellId = "button" + shot;
        //int resId = getResources().getIdentifier(cellId, "id", getPackageName());
        TextView aux = (TextView) findViewById(shot);

        if (!success) {
            aux.setBackgroundResource(R.drawable.shape_button_blue);
            playerGrid[shot] = 2;//2 means miss
        } else {
            aux.setBackgroundResource(R.drawable.shape_button_red);
            playerGrid[shot] = 1; //1 means success

            if (matrix == 1) successfulShots++;
            else if (matrix == 2) successfulShots2++;

            debug.setText("Potuação: \n\nTu: " + successfulShots + "/14\nAdversário: " + successfulShots2 + "/14");

            if (successfulShots > 13)  endGame("Parabéns foi o vencedor do jogo!");
            else if(successfulShots2 > 13) endGame("O seu adversário ganhou, o jogo terminou.");

        }
    }

    private void setupGrid(int message, int[] grid) {
        help.setText(message);
        int i;
        for (i = 0; i < 100; i++) {
            TextView aux = (TextView) findViewById(i);

            switch (grid[i]) {
                case 0:
                    //No shots on that spot
                    aux.setBackgroundResource(R.drawable.shape_button);
                    break;
                case 1:
                    //Successful shots
                    aux.setBackgroundResource(R.drawable.shape_button_red);
                    break;
                case 2:
                    //Unsuccessful shots
                    aux.setBackgroundResource(R.drawable.shape_button_blue);
                    break;
                case 5:
                    //There's a bot there not shot
                    aux.setBackgroundResource(R.drawable.shape_button_gray);
                    break;
            }
        }
    }

    private void cleanGrid() {
        int i;
        for (i = 0; i < 100; i++) {
            String cellId = "";
            if (i > 9) cellId = "button" + i;
            else cellId = "button0" + i;

            int resId = getResources().getIdentifier(cellId, "id", getPackageName());
            TextView aux = (TextView) findViewById(resId);
            aux.setBackgroundResource(R.drawable.shape_button);
            aux.setOnClickListener(this);
            aux.setId(i);
            //aux.setText(""+aux.getId());
        }
    }

    private void quitGame() {
        AlertDialog.Builder alertD = new AlertDialog.Builder(GameActivity.this);
        alertD.setMessage("Tem a certeza que quer desistir do jogo?").setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
                        GameActivity.this.startActivity(registerIntent);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog check = alertD.create();
        check.setTitle("Desistir do jogo");
        check.show();
    }

    private void endGame(String message) {
        AlertDialog.Builder alertD = new AlertDialog.Builder(GameActivity.this);
        alertD.setMessage(message).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
                        GameActivity.this.startActivity(registerIntent);
                    }
                });
        AlertDialog check = alertD.create();
        check.setTitle("Fim do jogo");
        check.show();
    }

    public void onBackPressed() {
        quitGame();
    }




    /**********************************************************************************************
     *
     *                               MULTIPLAYER, NOT OK
     *
     **********************************************************************************************/


    //Our function to threat received messaged
    private void messageReceived(String message){
        int[] code = BTMessages.decodMessage(message);
        boolean answer = false;
        switch (code[0]){
            //Receção de tiro
            case 1:
                int hisShot = code[1];

                answer = mainPlayer.getShot(hisShot);
                sendMessage(BTMessages.sendShotFeedback(code[1],answer));

                markShot(1,code[1],answer);
                break;
            //Receção feedback tiro
            case 2:

                if(code[2] == 1) answer = true;
                markShot(2, code[1], answer);

                if(numbShots > 2){
                    setupGrid(R.string.othersTurn, othersPlays);
                    help.setText("É a vez do teu adversário!");
                    sendMessage(BTMessages.yourTurn());
                    return;
                }
                help.setText("Podes dar um tiro!");
                myTurn = true;
                break;
            //Envio de Random
            case 3:

                if(code[3] > BTMessages.getMyRand()) {
                    setupGrid(R.string.othersTurn, othersPlays);
                    help.setText("É a vez do teu adversário!");
                    sendMessage(BTMessages.yourTurn());
                }
                else if(code[3]==BTMessages.getMyRand()) sendMessage(BTMessages.sendFirstContact());

                break;
            //Dá a vez de jogar
            case 4:
                numbShots = 0;
                setupGrid(R.string.yourTurn, myPlays);
                myTurn = true;
                help.setText("Podes dar um tiro!");
                break;
        }
    }

    /********
     *  Multiplayer, following Google's example
     */


    public void onStart(){
        super.onStart();
        if(!multiplayer) return;

        /*******
        * MULTIPLAYER
        */
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
        if(!multiplayer) return;

        /*******
        * MULTIPLAYER
        */
        if(mChatService != null){
            mChatService.stop();
       }
   }

   public void onResume(){
       super.onResume();
       if(!multiplayer) return;

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        //        if(mChatService != null){
        //Only if the state is STATE_NONE, do we know that we haven't started already

       if(mChatService.getState() == BluetoothChatService.STATE_NONE){
        //Start BT chat services
           mChatService.start();
        }
    }


    private void setupChat(){
        //Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(GameActivity.this, mHandler);

        //Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

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
            Toast.makeText(GameActivity.this,"You're not connected to a device",Toast.LENGTH_LONG).show();
            return;
        }

        //Gets the message bytes and tells the BluetoothChat Service to write
        byte[] send = message.getBytes();
        mChatService.write(send);
    }

    //The Handler that get information back from the BluetootchChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        FragmentActivity activity = GameActivity.this;
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
                //mConversationArrayAdapter.add("Me: " + writeMessage);
                break;

            case Constants.MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                //construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf,0,msg.arg1);
                messageReceived(readMessage);
                //mConversationArrayAdapter.add(mConnectedDeviceName+": "+readMessage);
                //debug.setText(readMessage);
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
                    Toast.makeText(GameActivity.this,"Bluetooth was not enabled. Leaving multiplayer.",Toast.LENGTH_SHORT).show();
                    this.finish();
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


    /********************************************
     *
     *      MULTIPLAYER - Second approach
     *
     ******************************************/


    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_BT__REQUEST) {
            if (resultCode == RESULT_OK) {
                mSmoothBluetooth.tryConnection();
            }
        }

        if (requestCode == ENABLE_BT__REQUEST2) {
            if (resultCode == RESULT_OK) {
                position = data.getExtras().getString(ListDevices.deviceMac);
                SmoothBluetooth.ConnectionCallback connectionCallback = null;
                connectionCallback.connectTo(new Device("Player2",position,true));

            }
        }
    }*/

    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            Toast.makeText(GameActivity.this, "Bluetooth not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onBluetoothNotEnabled() {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, ENABLE_BT__REQUEST);
        }

        @Override
        public void onConnecting(Device device) {
            //mStateTv.setText("Connecting to");
            //mDeviceTv.setText(device.getName());
            Toast.makeText(GameActivity.this, "Connecting to " + device.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnected(Device device) {
            //    mStateTv.setText("Connected to");
            //    mDeviceTv.setText(device.getName());
            //    mConnectionLayout.setVisibility(View.GONE);
            //    mDisconnectButton.setVisibility(View.VISIBLE);
            Toast.makeText(GameActivity.this, "Connected to " + device.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDisconnected() {
            // mStateTv.setText("Disconnected");
            // mDeviceTv.setText("");
            // mDisconnectButton.setVisibility(View.GONE);
            // mConnectionLayout.setVisibility(View.VISIBLE);
            Toast.makeText(GameActivity.this, "Disconnected!!!! ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectionFailed(Device device) {
            // mStateTv.setText("Disconnected");
            // mDeviceTv.setText("");
            Toast.makeText(GameActivity.this, "Failed to connect to " + device.getName(), Toast.LENGTH_SHORT).show();
            if (device.isPaired()) {
                mSmoothBluetooth.doDiscovery();
            }
        }

        @Override
        public void onDiscoveryStarted() {
            Toast.makeText(GameActivity.this, "Searching", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onDiscoveryFinished() {

        }

        @Override
        public void onNoDevicesFound() {
            Toast.makeText(GameActivity.this, "No device found", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList,
                                   final SmoothBluetooth.ConnectionCallback connectionCallback) {
            ArrayAdapter<String> ArrayBluetooth;

             /*   final MaterialDialog dialog = new MaterialDialog.Builder(GameActivity.this)
                        .title("Devices")
                        .adapter(new DevicesAdapter(GameActivity.this, deviceList),null)
                        .build();

                RecyclerView.Recycler listView = dialog.getListView();
                if (listView != null) {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            connectionCallback.connectTo(deviceList.get(position));
                            dialog.dismiss();
                        }

                    });
                }

                dialog.show();*/
                //Intent abreLista = new Intent(GameActivity.this, ListDevices.class);
                // startActivityResult(abreLista, ENABLE_BT__REQUEST2);
                //startActivityForResult(abreLista, ENABLE_BT__REQUEST2);
                Log.d("----------------------",";;;;;;;;;;;;;;;;;;;");
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(GameActivity.this);
                //builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Choose one Paired Connection:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GameActivity.this, android.R.layout.select_dialog_singlechoice);
                ArrayBluetooth = new ArrayAdapter<String>(GameActivity.this, android.R.layout.select_dialog_singlechoice);
                meuBluetoothadapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> devicesP = meuBluetoothadapter.getBondedDevices();
                Log.d("Inside Listener"," size " +devicesP.size());
                if(devicesP.size()> 0){
                    for(BluetoothDevice device : devicesP){
                        String nomeBT = device.getName();
                        String macAdd = device.getAddress();
                        ArrayBluetooth.add(nomeBT + "\n" + macAdd);
                    }
                }

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                String strName = arrayAdapter.getItem(which);
                Log.d("Inside Listener"," size " +strName);
                String mac = strName.substring(strName.length() - 17);
                Log.d("Inside Listener"," size " +mac);

                connectionCallback.connectTo(new Device("Player2",mac,true));
                dialog.dismiss();

                //builderInner.show();
                }
            });
            builderSingle.show();
        }

        private void startActivityOnResult(Intent abreLista, int enableBt_request2) {

        }

        @Override
        public void onDataReceived(int data) {
            mBuffer.add(data);
            if (data == 62 && !mBuffer.isEmpty()) {
                //if (data == 0x0D && !mBuffer.isEmpty() && mBuffer.get(mBuffer.size()-2) == 0xA0) {
                StringBuilder sb = new StringBuilder();
                for (int integer : mBuffer) {
                    sb.append((char) integer);
                }
                mBuffer.clear();
                mResponseBuffer.add(0, sb.toString());
                mResponsesAdapter.notifyDataSetChanged();
            }
        }
    };
}
