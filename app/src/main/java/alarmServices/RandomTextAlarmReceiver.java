package alarmServices;

/**
 * Created by Tisa on 4/28/2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

import textServices.TextGenerator;

public class RandomTextAlarmReceiver extends BroadcastReceiver {

    private TextGenerator tg;

    private static final String ROMPREFERENCES = "MyPrefs";
    public static final String key_number = "numberK";
    public static final String key_gender = "genderK";

    private String mNumber;
    private String mGender;

    @Override
    public void onReceive(Context context, Intent intent) {
        readPreferences(context);
        tg = new TextGenerator();

        String phoneNo = mNumber;
        String message = tg.getRandomPhrases()+" "+tg.getPetName(mGender)+tg.getPunctuation()+tg.getSmiley();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void readPreferences (Context contx) {
        // get current user preferences
        SharedPreferences userPrefs = contx.getSharedPreferences(ROMPREFERENCES, Context.MODE_PRIVATE);
        mNumber = userPrefs.getString(key_number, "");
        mGender = userPrefs.getString(key_gender, "");

    }

}