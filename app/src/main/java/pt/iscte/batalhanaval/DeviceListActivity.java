package pt.iscte.batalhanaval;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DeviceListActivity extends AppCompatActivity {

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
    }
}
