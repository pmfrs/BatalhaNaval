package pt.iscte.batalhanaval;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Set;

/**
 * Created by jfaustino on 27-05-2017.
 */

public class ListDevices  extends ListActivity{
    private BluetoothAdapter meuBluetoothadapter;
    static String mac_address;

    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        meuBluetoothadapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devicesP = meuBluetoothadapter.getBondedDevices();

        if(devicesP.size()> 0){
            for(BluetoothDevice device : devicesP){
                String nomeBT = device.getName();
                String macAdd = device.getAddress();
                ArrayBluetooth.add(nomeBT + "\n" + macAdd);
            }
        }
        setListAdapter(ArrayBluetooth);
    }
}
