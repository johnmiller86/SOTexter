package com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import alarmServices.DeviceBootReceiver;

/**
 * @author Tisay Huynh && John D. Miller
 * @version 1.0.2
 * @since 05/02/2016
 */
public class ScheduleActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;

    private TimePicker mMorningPicker;
    private TimePicker mNightPicker;
    private Integer mMornHour;
    private Integer mMornMin;
    private Integer mNightHour;
    private Integer mNightMin;


    private Boolean mIsDating;
    private Button mSubmitBtn;
    private String format="";

    private SharedPreferences mSharedPreference;
    private String mMorningTime;
    private String mNightTime;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String key_isDating= "isDatingK";
    public static final String SchedulePREFERENCES = "UserSchedulePrefs" ;
    public static final String key_morningSchedule = "morningScheduleK";
    public static final String key_nightSchedule = "nightScheduleK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initialize();
        checkIsDating();
        listeners();
    }


    private  void checkIsDating () {
        mSharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mIsDating = mSharedPreference.getBoolean(key_isDating, false);
    }

    private void initialize () {
        mMorningPicker = (TimePicker) findViewById(R.id.morning_timePicker);
        mNightPicker = (TimePicker) findViewById(R.id.night_timePicker);
        mSubmitBtn = (Button) findViewById(R.id.button);

        //preferences initialize
        mMorningTime = "hh:mm:ss";
        mNightTime = "hh:mm:ss";
        getHours ();
    }

    public void getHours () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mMornHour = mMorningPicker.getHour();
            mMornMin = mMorningPicker.getMinute();
        } else {
            mMornHour = mMorningPicker.getCurrentHour();
            mMornMin = mMorningPicker.getCurrentMinute();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNightHour = mNightPicker.getHour();
            mNightMin = mNightPicker.getMinute();
        } else {
            mNightHour = mNightPicker.getCurrentHour();
            mNightMin = mNightPicker.getCurrentMinute();
        }

    }
    private void listeners () {

        mMorningPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
                getHours ();
            }
    });

        mNightPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
                getHours ();
            }
        });


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String morningTime = Integer.toString(mMornHour)+":"+ Integer.toString(mMornMin);
                    String nightTime = Integer.toString(mNightHour)+":" +Integer.toString(mNightMin);

                    //saves both time into UserSchedulePref
                    mSharedPreference = getSharedPreferences(SchedulePREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreference.edit();
                    editor.putString(key_morningSchedule, morningTime);
                    editor.putString(key_nightSchedule, nightTime);
                    editor.commit();


                if (mIsDating) {
                    //CALLS TO DEVICEBOOTRECEIVER FOR MASTER ALARMMANAGER FOR ALL OTHER ALARMRECEIVERS
                     /* Retrieve a PendingIntent that will perform a broadcast */
                    Intent alarmIntent = new Intent(ScheduleActivity.this, DeviceBootReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(ScheduleActivity.this, 10, alarmIntent, 0);

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                            0, pendingIntent);
                }
                    //opens up main activity after user enters info
                    Intent myIntent = new Intent(ScheduleActivity.this, MainActivity.class);
                    ScheduleActivity.this.startActivity(myIntent);

                    //sends user message
                    Toast.makeText(ScheduleActivity.this, "Awesome!  May texting commence!!", Toast.LENGTH_LONG)
                            .show();

            }
        });
    }

    public String convertChoseTimetoString(Integer hour, Integer min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        String time = Integer.toString(hour)+":"+Integer.toString(min)+":"+format;
        return time;
    }
}
