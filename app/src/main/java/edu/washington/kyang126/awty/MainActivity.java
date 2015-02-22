package edu.washington.kyang126.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private int count;
    private PendingIntent pendingIntent;
    private int time;
    private boolean alarmUp;
    private EditText getNumber;
    private EditText getTime;
    static final String STATE_P1 = "player1";
    static final String STATE_P2 = "player2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String old1 = "1";
        String old2 = "1";

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            old1 = savedInstanceState.getString(STATE_P1);
            old2 = savedInstanceState.getString(STATE_P2);
        }

        getNumber = (EditText) this.findViewById(R.id.editText2);
        getTime = (EditText) this.findViewById(R.id.editText3);

        final Button startButton = (Button) this.findViewById(R.id.button);
        count = 0;
        /* Retrieve a PendingIntent that will perform a broadcast */
        final Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

        alarmUp = (PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent,PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            startButton.setText("Stop");
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int number = 0;
                if(!getTime.getText().toString().matches("") && !getNumber.getText().toString().matches("")){

                    try {
                        number = Integer.parseInt(getNumber.getText().toString());
                        time = Integer.parseInt(getTime.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Please input a valid numbers", Toast.LENGTH_SHORT).show();
                    }

                }

                if (time > 0 && number > 0 || alarmUp ) {
                    count++;
                    //create the toast object, set display duration,
                    alarmIntent.putExtra("number", getNumber.getText().toString());
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

                    startButton.setText("Stop");

                    if (count == 1 && time > 0 && !alarmUp){

                        start();
                    }else if (count == 2 || alarmUp){
                        cancel();
                        count = 0;
                        startButton.setText("Start");
                        alarmUp = false;
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Check your input values again", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_P1, getNumber.getText().toString());
        savedInstanceState.putString(STATE_P2, getTime.getText().toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = time * 1000 * 5;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();

    }


    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
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
}
