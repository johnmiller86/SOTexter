package alarmServices;

/**
 * Created by Tisa on 4/28/2016.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeviceBootReceiver extends BroadcastReceiver {

    private static final String ROMPREFERENCES = "MyPrefs";
    public static final String key_morningEnabled = "morningEnabledK";
    public static final String key_nightEnabled = "nightEnabledK";
    public static final String key_randomEnabled = "randomEnabledK";
    public static final String key_importantEnabled = "importantEnabledK";
    //TIMES USER IS AWAKE
    public static final String key_morningSchedule = "morningScheduleK";
    public static final String key_nightSchedule = "nightScheduleK";

    //ANNIVERSARY AND BIRTHDAYS
    public static final String key_birthday = "birthdayK";
    public static final String key_anniversary = "anniversaryK";

    private Boolean mMorningEnabled;
    private Boolean mNightEnabled;
    private Boolean mRandomEnabled;
    private Boolean mImportantEnabled;
    private String mMorningTime;
    private String mMorningHour;
    private String mMorningMinute;
    private String mNightTime;
    private String mNightHour;
    private String mNightMinute;
    private String mAnniversary;
    private String mAnniversaryMonth;
    private String mAnniversaryDay;
    private String mBirthday;
    private String mBirthdayMonth;
    private String mBirthdayDay;

    private static final int randomMilliSecs =900000;


    @Override
    //when device reboots, this is called
    public void onReceive(Context context, Intent intent) {

        Calendar SysCal = Calendar.getInstance();

            //get shared preference stuff
            readPreferences(context);

           //* DEPENDING UPON IF TYPES OF TEXT ENABLED, TURN ON ALARM HERE DURING REBOOTS *//*
            if (mMorningEnabled) {
                 //* Retrieve a PendingIntent that will perform a broadcast *//*
                Intent alarmIntent = new Intent(context, MorningTextAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);


                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int interval = 1000*60* 60 *24;  //ms * secs * mins * hour
                //randomize interval - don't want to be suspiccced
                interval = randomizeInterval(interval);

                 //* Set the alarm to start at 10:30 AM *//*
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                //calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mMorningHour));
                calendar.set(Calendar.MINUTE, Integer.parseInt(mMorningMinute));

                saveMorningTime(context, interval, Integer.parseInt(mMorningHour), Integer.parseInt(mMorningMinute));

                long calTime = calendar.getTimeInMillis();
                long sysTime = SysCal.getTimeInMillis();
                //if selected time past currenttime, add a day to calendar
                if (calendar.getTimeInMillis() > SysCal.getTimeInMillis()) {
                   calendar.add(Calendar.DATE, 1);
                }

                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
                Toast.makeText(context, "Morning Text Scheduled.", Toast.LENGTH_SHORT).show();
            }

            if (mNightEnabled) {
                 //* Retrieve a PendingIntent that will perform a broadcast *//*
                Intent alarmIntent = new Intent(context, NightTextAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, 0);


                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int interval = 1000*60* 60 *24;  //ms * secs * mins * hour
                //randomize interval - don't want to be suspiccced
                interval = randomizeInterval(interval);

                 //* Set the alarm to start at 10:30 AM *//*
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                //calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mNightHour));
                calendar.set(Calendar.MINUTE, Integer.parseInt(mNightMinute));

                saveNightTime(context, interval, Integer.parseInt(mNightHour), Integer.parseInt(mNightMinute));

                long calTime = calendar.getTimeInMillis();
                long sysTime = SysCal.getTimeInMillis();

                //if selected time past currenttime, add a day to calendar
                if (calendar.getTimeInMillis() > SysCal.getTimeInMillis()) {
                    calendar.add(Calendar.DATE, 1);
                }

                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
                Toast.makeText(context, "Night Text Scheduled.", Toast.LENGTH_SHORT).show();
            }

            if (mRandomEnabled) {
                 //* Retrieve a PendingIntent that will perform a broadcast *//*
                Intent alarmIntent = new Intent(context, RandomTextAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, alarmIntent, 0);


                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int interval = 1000*60* 60 *24;  //ms * secs * mins * hour
                //randomize interval - don't want to be suspiccced
                interval = randomizeInterval(interval);

                 //* Set the alarm to start at 10:30 AM *//*
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                // SET UP RANDOM HOUR AND MINUTE
                int tempHour;
                Random generator = new Random();
                if ((Integer.parseInt(mNightHour) - Integer.parseInt(mMorningHour))>0) {
                    tempHour = generator.nextInt(Integer.parseInt(mNightHour) - Integer.parseInt(mMorningHour)) + Integer.parseInt(mMorningHour);
                } else {
                    tempHour = 0;
                }
                int tempMinute = generator.nextInt(60);

                //calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.set(Calendar.HOUR_OF_DAY, tempHour);
                calendar.set(Calendar.MINUTE, tempMinute);

                saveRandomTime(context, interval, tempHour, tempMinute);

                //if selected time past currenttime, add a day to calendar
                if (calendar.getTimeInMillis() > SysCal.getTimeInMillis()) {
                    calendar.add(Calendar.DATE, 1);
                }

                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
                Toast.makeText(context, "Random Text Scheduled.", Toast.LENGTH_SHORT).show();
            }

            if (mImportantEnabled) {
                //* ------------------------------SEPARATE ALARM CLOCK FOR BIRTHDAYSSSSSSSSSSSS ------------------------*//*
                 //* Retrieve a PendingIntent that will perform a broadcast *//*
                Intent alarmIntent = new Intent(context, BirthdayTextAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 3, alarmIntent, 0);


                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int interval = 1000*60* 60 *24; //checks every to see if it today matches dates in ImportantAlarmReceiver

                 //* Set the alarm to start at 10:30 AM *//*
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.MONTH, Integer.parseInt(mBirthdayMonth) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mBirthdayDay));
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mMorningHour));
                calendar.set(Calendar.MINUTE, Integer.parseInt(mMorningMinute));

                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
                Toast.makeText(context, "Checking if today iS a birthday!", Toast.LENGTH_SHORT).show();

                 //* ------------------------------SEPARATE ALARM CLOCK FOR ANNIVERSARY ------------------------*//*
                 //* Retrieve a PendingIntent that will perform a broadcast *//*
                Intent alarmIntent2 = new Intent(context, AnniversaryTextAlarmReceiver.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 4, alarmIntent, 0);


                AlarmManager manager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                 //* Set the alarm to start at 10:30 AM *//*
                Calendar calendar2 = GregorianCalendar.getInstance();
                calendar2.setTimeInMillis(System.currentTimeMillis());
                calendar2.set(Calendar.MONTH, Integer.parseInt(mAnniversaryMonth) - 1);
                calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mAnniversaryDay));
                calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mMorningHour));
                calendar2.set(Calendar.MINUTE, Integer.parseInt(mMorningMinute));

                manager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), interval, pendingIntent2);
                Toast.makeText(context, "Checking if today iS an Anniversary!", Toast.LENGTH_SHORT).show();
            }


    }

    public void readPreferences (Context contx) {
        // get current user preferences
        SharedPreferences userPrefs = contx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mMorningEnabled = userPrefs.getBoolean(key_morningEnabled, false);
        mNightEnabled = userPrefs.getBoolean(key_nightEnabled, false);
        mRandomEnabled = userPrefs.getBoolean(key_randomEnabled, false);
        mImportantEnabled = userPrefs.getBoolean(key_importantEnabled, false);


        mBirthday = userPrefs.getString(key_birthday, "");
        String [] tempBirthday = mBirthday.split("/");
        mBirthdayMonth = tempBirthday[0];
        mBirthdayDay = tempBirthday[1];
        mAnniversary = userPrefs.getString(key_anniversary, "");
        String [] tempAnniversary = mAnniversary.split("/");
        mAnniversaryMonth = tempAnniversary[0];
        mAnniversaryDay = tempAnniversary[1];

        // get current user preferences
        SharedPreferences userSchedulePrefs = contx.getSharedPreferences("UserSchedulePrefs", Context.MODE_PRIVATE);
        mMorningTime = userSchedulePrefs.getString(key_morningSchedule, "");
        String [] tempMorning = mMorningTime.split(":");
        mMorningHour = tempMorning[0];
        mMorningMinute = tempMorning[1];

        mNightTime = userSchedulePrefs.getString(key_nightSchedule, "");
        String [] tempNight = mNightTime.split(":");
        mNightHour = tempNight[0];
        mNightMinute = tempNight[1];
    }

    public int randomizeInterval(int interval){
        Random generator = new Random();
        //get number to add or subtract from set minutes
        int x = generator.nextInt(randomMilliSecs);

        //get subtraction or addition
        int operation = generator.nextInt(2);

        if (operation == 0 ){
            interval = interval + x;
        } else {
            interval = interval - x;
        }
        return interval;
    }

    public void saveMorningTime (Context context, int interval, int morningHour, int morningMinute) {
        int time2 = 1000*60*morningMinute*morningHour;

        //get interval time
        int hours = (int)TimeUnit.MILLISECONDS.toHours(interval);
        interval -= TimeUnit.HOURS.toMillis(hours);
        int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(interval);

        int nextHour;
        int nextMinute;
        String am_pm;
        int carryOver=0;
        //add minute to mMinute
        //73 minute
        nextMinute = minutes + morningMinute;
        if (nextMinute>59){
            nextMinute=nextMinute - 60;
            carryOver ++;
        }
        //add hours to morningHour
            nextHour = hours + morningHour +carryOver;
        if (nextHour>23){
            nextHour = nextHour - 24;
        }
        if (nextHour < 12) {
            am_pm = "am";
        } else {
            am_pm ="pm";
            nextHour -=12;
        }

        if(nextHour ==0){
            nextHour = 12;
        }

        String nextTime = Long.toString(nextHour)+":"+Long.toString(nextMinute)+am_pm;

        SharedPreferences mSharedPreference = context.getSharedPreferences("UserSchedulePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString("nextMorningK", nextTime);
        editor.commit();
    }

    public void saveNightTime (Context context, int interval, int nightHour, int nightMinute) {
        int time2 = 1000*60*nightMinute*nightHour;

        //get interval time
        int hours = (int)TimeUnit.MILLISECONDS.toHours(interval);
        interval -= TimeUnit.HOURS.toMillis(hours);
        int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(interval);

        int nextHour;
        int nextMinute;
        String am_pm;
        int carryOver=0;
        //add minute to mMinute
        //73 minute
        nextMinute = minutes + nightMinute;
        if (nextMinute>59){
            nextMinute=nextMinute - 60;
            carryOver ++;
        }
        //add hours to morningHour
        nextHour = hours + nightHour +carryOver;
        if (nextHour>23){
            nextHour = nextHour - 24;
        }
        if (nextHour < 12) {
            am_pm = "am";
        } else {
            am_pm ="pm";
            nextHour -=12;
        }
        if(nextHour ==0){
            nextHour = 12;
        }

        String nextTime = Long.toString(nextHour)+":"+Long.toString(nextMinute)+am_pm;

        SharedPreferences mSharedPreference = context.getSharedPreferences("UserSchedulePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString("nextNightK", nextTime);
        editor.commit();
    }

    public void saveRandomTime (Context context, int interval, int randomHour, int randomMinute) {
        int time2 = 1000*60*randomMinute*randomHour;

        //get interval time
        int hours = (int)TimeUnit.MILLISECONDS.toHours(interval);
        interval -= TimeUnit.HOURS.toMillis(hours);
        int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(interval);

        int nextHour;
        int nextMinute;
        String am_pm;
        int carryOver=0;
        //add minute to mMinute
        //73 minute
        nextMinute = minutes + randomMinute +carryOver;
        if (nextMinute>59){
            nextMinute=nextMinute - 60;
            carryOver ++;
        }
        //add hours to morningHour
        nextHour = hours + randomHour;
        if (nextHour>23){
            nextHour = nextHour - 24;
        }
        if (nextHour < 12) {
            am_pm = "am";
        } else {
            nextHour -=12;
            am_pm ="pm";
        }
        if(nextHour ==0){
            nextHour = 12;
        }

        String nextTime = Long.toString(nextHour)+":"+Long.toString(nextMinute)+am_pm;

        SharedPreferences mSharedPreference = context.getSharedPreferences("UserSchedulePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString("nextRandomK", nextTime);
        editor.commit();
    }


}