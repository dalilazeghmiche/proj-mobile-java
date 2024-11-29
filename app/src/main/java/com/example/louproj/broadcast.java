package com.example.louproj;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.CheckBox;

public class broadcast extends AppCompatActivity {

    CheckBox bluetoothCheckbox, wifiCheckbox, chargerCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        // Initialize CheckBoxes
        bluetoothCheckbox = findViewById(R.id.bluetooth);
        wifiCheckbox = findViewById(R.id.wifi);
        chargerCheckbox = findViewById(R.id.charger);

        // Register BroadcastReceiver for Bluetooth state changes
        registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        // Register BroadcastReceiver for WiFi state changes
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));

        // Register BroadcastReceiver for Charger state changes
        registerReceiver(chargerReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        // Set initial state of checkboxes
        updateBluetoothCheckbox();
        updateWifiCheckbox();
        updateChargerCheckbox();
    }

    // BroadcastReceiver to listen for Bluetooth state changes
    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                // Update the Bluetooth checkbox state
                updateBluetoothCheckbox();
            }
        }
    };

    // BroadcastReceiver to listen for WiFi state changes
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                // Update the WiFi checkbox state
                updateWifiCheckbox();
            }
        }
    };

    // BroadcastReceiver to listen for Charger state changes
    private BroadcastReceiver chargerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                // Update the Charger checkbox state
                updateChargerCheckbox();
            }
        }
    };

    // Method to update the state of the Bluetooth checkbox based on Bluetooth connection status
    private void updateBluetoothCheckbox() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothCheckbox.setChecked(true); // Bluetooth is enabled, check the checkbox
        } else {
            bluetoothCheckbox.setChecked(false); // Bluetooth is disabled, uncheck the checkbox
        }
    }

    // Method to update the state of the WiFi checkbox based on WiFi connection status
    private void updateWifiCheckbox() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            wifiCheckbox.setChecked(true); // WiFi is enabled, check the checkbox
        } else {
            wifiCheckbox.setChecked(false); // WiFi is disabled, uncheck the checkbox
        }
    }
    // Method to update the state of the Charger checkbox based on charger connection status
    private void updateChargerCheckbox() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        chargerCheckbox.setChecked(isCharging); // Set charger checkbox based on charging status
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceivers when the activity is destroyed
        unregisterReceiver(bluetoothReceiver);
        unregisterReceiver(wifiReceiver);
        unregisterReceiver(chargerReceiver);
    }

    public void back (View v){
        Intent ab = new Intent(this, MainActivity.class);
        startActivity(ab);

    }
}