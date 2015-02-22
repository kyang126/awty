package edu.washington.kyang126.awty;

/**
 * Created by Kevin on 2/21/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String number = intent.getStringExtra("number");


        Toast.makeText(context,"" + number + ": Are we there yet?", Toast.LENGTH_SHORT).show();
      //  Log.i("testing repeater", "please work");
    }
}