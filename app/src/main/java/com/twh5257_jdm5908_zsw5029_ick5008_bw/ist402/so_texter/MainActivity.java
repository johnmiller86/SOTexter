package com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import alarmServices.MorningTextAlarmReceiver;

/**
 * @author Tisa Huynh
 * @version 1.0.2
 * @since 05/02/2016
 */
public class MainActivity extends AppCompatActivity {

    private static Context context;

    //widgets
    private ImageView bannerImg;
    private TextView greetingTextView;
    private TextView morningDialog;
    private TextView nightDialog;
    private TextView randomDialog;
    private Switch morningSwitch;
    private Switch nightSwitch;
    private Switch randomSwitch;
    private Switch importantSwitch;

    //widgets for tab 2
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private Button submitBtn_tb2;
    private Switch morningSwitch2;
    private Switch nightSwitch2;
    private Switch randomSwitch2;
    private Switch importantSwitch2;

    //widgets for tab 3
    private Button emailBtn;

    //answers
    private Boolean mIsDating;
    private String mGender;
    private Boolean mTextEnabled;
    private String mName;
    private String mNumber;
    private String mAnniversary;
    private String mBirthday;
    private Boolean mMorningEnabled;
    private Boolean mNightEnabled;
    private Boolean mRandomEnabled;
    private Boolean mImportantEnabled;
    private String mNextMorningText;
    private String mNextNightText;
    private String mNextRandomText;
    private TabHost mTabHost;

    private static final String ROMPREFERENCES = "MyPrefs";
    public static final String key_isDating = "isDatingK";
    public static final String key_gender = "genderK";
    public static final String key_textEnabled = "textEnabledK";
    public static final String key_name = "nameK";
    public static final String key_number = "numberK";
    public static final String key_birthday = "birthdayK";
    public static final String key_anniversary = "anniversaryK";
    public static final String key_morningEnabled = "morningEnabledK";
    public static final String key_nightEnabled = "nightEnabledK";
    public static final String key_randomEnabled = "randomEnabledK";
    public static final String key_importantEnabled = "importantEnabledK";

    private static final String SCHEDULEPREFERENCES = "UserSchedulePrefs";
    public static final String key_userMorning = "userMorningK";
    public static final String key_userNight = "userNightK";
    public static final String key_morningSchedule = "nextMorningK";
    public static final String key_nightSchedule = "nextNightK";
    public static final String key_randomSchedule = "nextRandomK";






    private SharedPreferences mSharedPreferences;
    private PendingIntent pendingIntent;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reads user preferences from sharedpreferences
        readUserPreferences();

        //check if it is user's first time, if so, redirect them to form
        checkFirstTimeUser();

        //initialize any widgets
        initializeWidgets();

        //contains listeners all in one method
        listeners();

        //edits displayed first tab dialogs
        editDialogs();

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(MainActivity.this, MorningTextAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        //sets text messages


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void checkFirstTimeUser() {
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = 0;
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // handle exception
            e.printStackTrace();
            return;
        }

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // get current user preferences
        SharedPreferences userPrefs = getSharedPreferences(ROMPREFERENCES, MODE_PRIVATE);
        Boolean isFormFilled = userPrefs.getBoolean("isFormFilledK", false);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode && isFormFilled == true) {
            // This is just a normal run
            return;
        } else if (savedVersionCode == DOESNT_EXIST || isFormFilled == false) {
            // TODO This is a new install (or the user cleared the shared preferences)
            // Permissions got denied or user was not setup, ending
            finish();
            showHelp();
        } else if (currentVersionCode > savedVersionCode) {
            return;
        }
        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).commit();
    }

    public void showHelp() {
        startActivity(new Intent(MainActivity.this, FirstLaunchActivity.class));
        Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                .show();
    }

    private void editDialogs() {
        //get default greeting TextView and set into string greeting
        String greeting = greetingTextView.getText().toString();
        if (mIsDating) {
            greeting = greeting.replace("NAMEHERE", mName);
            greeting = greeting.replace("ANNIVERSARYHERE", mAnniversary);
            greeting = greeting.replace("BIRTHDAYHERE", mBirthday);
        } else {
            greeting = getResources().getString(R.string.ALTERNATIVEdialogLine1);
        }
        //replaces greeting textview's text with current greeting after replacements
        greetingTextView.setText(greeting);

        //get default morning text dialog and set into string
        String morningDialogStr = morningDialog.getText().toString();
        morningDialogStr = morningDialogStr.replace("MORNINGTIME", mNextMorningText);
        morningDialog.setText(morningDialogStr);

        //get default random text dialof and set into string
        String randomDialogStr = randomDialog.getText().toString();
        randomDialogStr = randomDialogStr.replace("RANDOMTIME", mNextRandomText);
        randomDialog.setText(randomDialogStr);

        // get default night text dialog and set itno string
        String nightDialogStr = nightDialog.getText().toString();
        nightDialogStr = nightDialogStr.replace("NIGHTTIME", mNextNightText);
        nightDialog.setText(nightDialogStr);

        // ----------------Edit tab 2 dialogs
        //set line1
        String tempString = getResources().getString(R.string.cDialog1);
        tempString = tempString.replace("ISDATINGHERE" , mIsDating.toString());
        textView1.setText(tempString);
        //set line2
        tempString = getResources().getString(R.string.cDialog2);
        tempString = tempString.replace("NAMEHERE", mName);
        textView2.setText(tempString);
        //set her, his, or their by mGender
        String tempGender = "";
        if (mGender.equals("girl")){
            tempGender = "Her";
        } else if (mGender.equals("boy")){
            tempGender = "His";
        } else {
            tempGender = "Their";
        }
        //set line4
        tempString = getResources().getString(R.string.cDialog4);
        tempString = tempString.replace("NAMEHERE", mName);
        tempString = tempString.replace("ANNIVERSARYHERE", mAnniversary);
        textView4.setText(tempString);
        //set line5
        tempString = getResources().getString(R.string.cDialog5);
        tempString =  tempString.replace("GENDERHERE", tempGender);
        tempString = tempString.replace("BIRTHDAYHERE", mBirthday);
        textView5.setText(tempString);
        //set line6
        tempString = getResources().getString(R.string.cDialog6);
        tempString = tempString.replace("GENDERHERE", tempGender);
        tempString = tempString.replace("PHONENUMBERHERE", mNumber);
        textView6.setText(tempString);


    }

    public void readUserPreferences() {
        // get current user preferences
        SharedPreferences userPrefs = getSharedPreferences(ROMPREFERENCES, MODE_PRIVATE);
        mIsDating = userPrefs.getBoolean(key_isDating, false);
        mGender = userPrefs.getString(key_gender, "");
        //TODO might remove the textenabled
        mTextEnabled = userPrefs.getBoolean(key_textEnabled, false);
        mName = userPrefs.getString(key_name, "");
        mNumber = userPrefs.getString(key_number, "");
        mBirthday = userPrefs.getString(key_birthday, "");
        mAnniversary = userPrefs.getString(key_anniversary, "");
        mMorningEnabled = userPrefs.getBoolean(key_morningEnabled, false);
        mNightEnabled = userPrefs.getBoolean(key_nightEnabled, false);
        mRandomEnabled = userPrefs.getBoolean(key_randomEnabled, false);
        mImportantEnabled = userPrefs.getBoolean(key_importantEnabled, false);

        //schedule preferences
        userPrefs = getSharedPreferences(SCHEDULEPREFERENCES, MODE_PRIVATE);
        mNextMorningText = userPrefs.getString(key_morningSchedule, "");
        mNextNightText = userPrefs.getString(key_nightSchedule, "");
        mNextRandomText = userPrefs.getString(key_randomSchedule, "");


    }

    private void listeners() {
        submitBtn_tb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FirstLaunchActivity.class));
                Toast.makeText(MainActivity.this, "Edit Form", Toast.LENGTH_LONG)
                        .show();
            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    /*    //MORNING SWITCH LISTENER
        morningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //turns on alarm manager for morning
                } else {
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        //NIGHT SWITCH LISTENER
        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

        //RANDOM SWITCH LISTENER
        randomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

        //IMPORTANT SWITCH LISTENER
        importantSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });*/
    }

    public void initializeWidgets() {
        getSupportActionBar().setElevation(0);
        bannerImg = (ImageView) findViewById(R.id.bannerImg);
        bannerImg.setScaleType(ImageView.ScaleType.FIT_XY);
        greetingTextView = (TextView) findViewById(R.id.tab1_textview1);
        morningDialog = (TextView) findViewById(R.id.morningDialog_TextView);
        nightDialog = (TextView) findViewById(R.id.nightDialog_TextView);
        randomDialog = (TextView) findViewById(R.id.randomDialog_TextView);


        //All that tab crap
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Dashboard");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Controls");
        host.addTab(spec);

        textView1 = (TextView) findViewById(R.id.textView1tb2);
        textView2 = (TextView) findViewById(R.id.textView2tb2);
        textView4 = (TextView) findViewById(R.id.textView4tb2);
        textView5 = (TextView) findViewById(R.id.textView5tb2);
        textView6 = (TextView) findViewById(R.id.textView6tb2);
        morningSwitch2 = (Switch) findViewById(R.id.morningSwitch2);
        morningSwitch2.setClickable(false);
        nightSwitch2 = (Switch) findViewById(R.id.nightSwitch2);
        nightSwitch2.setClickable(false);
        randomSwitch2 = (Switch) findViewById(R.id.randomSwitch2);
        randomSwitch2.setClickable(false);
        importantSwitch2 = (Switch) findViewById(R.id.importantSwitch2);
        importantSwitch2.setClickable(false);
        //switch turned on/off based on saved sharedpreferences
        if (mMorningEnabled){
            morningSwitch2.setChecked(true);
        }
        if (mNightEnabled) {
            nightSwitch2.setChecked(true);
        }
        if (mImportantEnabled) {
            importantSwitch2.setChecked(true);
        }
        if (mRandomEnabled) {
            randomSwitch2.setChecked(true);
        }


        submitBtn_tb2 = (Button) findViewById(R.id.submitBtn_tb2);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Contact Us");
        host.addTab(spec);
        emailBtn = (Button) findViewById(R.id.emailBtn_tb3);

    }

    protected void sendEmail() {
        String[] TO = {"twh5257@psu.edu, chefjohn2006@gmail.com, zacwhite62@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tisa.messagemybitch/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tisa.messagemybitch/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
