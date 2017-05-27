package pt.iscte.batalhanaval;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

import io.palaima.smoothbluetooth.SmoothBluetooth;

/**
 * Created by jfaustino on 27-05-2017.
 */

public class ListDevices  extends ListActivity{
    private BluetoothAdapter meuBluetoothadapter;
    static String deviceMac;

    ArrayAdapter<String> ArrayBluetooth;

    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

        ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String info = ((TextView) v).getText().toString();
        String mac = info.substring(info.length() - 17);
        Intent returnPosition = new Intent();
        returnPosition.putExtra(deviceMac, mac);

        setResult(RESULT_OK, returnPosition);
        finish();



    }
}
