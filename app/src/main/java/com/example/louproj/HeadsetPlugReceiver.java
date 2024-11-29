package com.example.louproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class HeadsetPlugReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            if (state == 1) { // Headset plugged in
                // Get reference to DBHelper and delete last item from database
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteLastItem();
                Toast.makeText(context, "Last item deleted from database", Toast.LENGTH_SHORT).show();
            }
        }
    }
}