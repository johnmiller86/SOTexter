package alarmServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import textServices.TextGenerator;

public class AnniversaryTextAlarmReceiver extends BroadcastReceiver {

    private TextGenerator tg;

    private static final String ROMPREFERENCES = "MyPrefs";
    public static final String key_isDating= "isDatingK";
    public static final String key_number = "numberK";
    public static final String key_gender = "genderK";
    public static final String key_anniversary = "anniversaryK";

    private Boolean mIsDating;
    private String mNumber;
    private String mGender;
    private String mAnniversary;


    @Override
    public void onReceive(Context context, Intent intent) {
        // If this broadcast is activated (which does everyday),
        // check if current date equals to userpref's saved anniversary
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        Calendar calendar = Calendar.getInstance();
        String todayDate = dateFormat.format(calendar.getTime());

        //extract day and month from birthday
        mAnniversary = dateFormat.format(mAnniversary);


        readPreferences(context);

        if (mIsDating) {
            if (mAnniversary.equals(todayDate)) {
                tg = new TextGenerator();
                String phoneNo = mNumber;
                String message = tg.getAnniversaryPhrases() + " " + tg.getPetName(mGender);

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(context, "Anniversary SMS sent.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Anniversary SMS was not sent, today is not an anniversary.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void readPreferences (Context contx) {
        // get current user preferences
        SharedPreferences userPrefs = contx.getSharedPreferences(ROMPREFERENCES, Context.MODE_PRIVATE);
        mIsDating = userPrefs.getBoolean(key_isDating, false);
        mNumber = userPrefs.getString(key_number, "");
        mGender = userPrefs.getString(key_gender, "");
        mAnniversary = userPrefs.getString(key_anniversary, "");

    }

}