package com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

import questionFragments.RomanticQuestionsFragment_page1;
import questionFragments.RomanticQuestionsFragment_page2;

/**
 * @author Tisay Huynh && John D. Miller
 * @version 1.0.2
 * @since 05/02/2016
 */
public class FirstLaunchActivity extends AppCompatActivity {

    private ImageView bannerImg;
    private Fragment mRomanticFragment_Page1;
    private Fragment mRomanticFragment_Page2;
    private FragmentTransaction mFragmentTransaction;
    private LayoutInflater layoutInflater;
    private ImageView letmeBanner;
    private ImageView mBlob;
    private RelativeLayout mRelativeLayout;
    private Button previousButton;
    private Button nextButton;
    private Button submitButton;
    private int mPageCount;


    private SharedPreferences mSharedPref;
    private SharedPreferences mSharedPreference;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String key_is1stPageFilled = "is1stPagefilledK";
    public static final String key_isFormFilled = "isFormFilledK";
    public static final String key_isDating= "isDatingK";
    public static final String key_gender = "genderK";
    public static final String key_is2ndPageFilled = "is2ndPageFilledK";
    public static final String key_name= "nameK";
    public static final String key_number = "numberK";
    public static final String key_birthday = "birthdayK";
    public static final String key_anniversary = "anniversaryK";
    public static final String key_morningEnabled = "morningEnabledK";
    public static final String key_nightEnabled = "nightEnabledK";
    public static final String key_randomEnabled = "randomEnabledK";
    public static final String key_importantEnabled = "importantEnabledK";

    public static final String SchedulePREFERENCES = "UserSchedulePrefs" ;
    public static final String key_userMorning = "morningScheduleK";
    public static final String key_userNight = "nightScheduleK";
    public static final String key_morningSchedule = "nextMorningK";
    public static final String key_nightSchedule = "nextNightK";
    public static final String key_randomSchedule = "nextRandomK";


    //answers
    private Boolean mIsFormFilled;
    private Boolean mIsDating;
    private boolean mIs2ndPageFilled;
    private Boolean mIs1stPageFilled;
    private String mGender;
    private String mName;
    private String mNumber;
    private String mAnniversary;
    private String mBirthday;
    private Boolean mMorningEnabled;
    private Boolean mNightEnabled;
    private Boolean mRandomEnabled;
    private Boolean mImportantEnabled;
    private String mMorningTime;
    private String mNightTime;
    private String mNextMorning;
    private String mNextNight;
    private String mNextRandom;

    // TODO Add to Tisa's version
    // Permission requests
    private static final int REQUEST_PERMISSION = 1;
    private static final int PERMISSION_SEND_SMS = 2;
    private static final int PERMISSION__READ_CONTACTS = 3;

    // TODO Add to Tisa's version
    private Uri contact;
    private String contactId, contactNumber;

    // TODO Add to Tisa's version
    // Calendar variables
    private static boolean anniversaryButtonClicked = false;
    private static boolean birthdayButtonClicked = false;


    //test
    private RadioGroup group1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //TODO INSTANTIATE THE SHARED PREFERENCES HERE


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        instantiateSharedPreferences ();
        initialize();
        listeners();

        // TODO Add this to Tisa's version
        //Requesting Marshmallow permissions to send text
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);

        // CREATE A LAYOUT INFLATER
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // SET THE BACKGROUND OF THE IMAGEVIEW TO THE PIG ANIMATION
        letmeBanner = (ImageView) layoutInflater.inflate(R.layout.help_message_view, null);
        letmeBanner.setBackgroundResource(R.drawable.help_message_animation);

        // CREATE AN ANIMATION DRAWABLE OBJECT BASED ON THIS BACKGROUND
        AnimationDrawable manAnimate = (AnimationDrawable) letmeBanner.getBackground();
        manAnimate.start();

        letmeBanner.setY(300);
        mRelativeLayout.addView(letmeBanner, 1);

        // SET THE BACKGROUND OF THE IMAGEVIEW TO THE PIG ANIMATION
        mBlob = (ImageView) layoutInflater.inflate(R.layout.help_message_view, null);
        mBlob.setBackgroundResource(R.drawable.blob_animation);

        // CREATE AN ANIMATION DRAWABLE OBJECT BASED ON THIS BACKGROUND
        manAnimate = (AnimationDrawable) mBlob.getBackground();
        manAnimate.start();

        mBlob.setY(400);
        mBlob.setX(200);
        mRelativeLayout.addView(mBlob, 1);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

//        RomanticQuestionsFragment_page2.anniversaryButton.setEnabled(false);
//        RomanticQuestionsFragment_page2.birthdayButton.setEnabled(false);
    }


    public void initialize() {
        getSupportActionBar().setElevation(0);

        bannerImg = (ImageView) findViewById(R.id.bannerImg);
        mRomanticFragment_Page1 = new RomanticQuestionsFragment_page1();
        mRomanticFragment_Page2 = new RomanticQuestionsFragment_page2();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        previousButton = (Button) findViewById(R.id.prevBtn);
        nextButton = (Button) findViewById(R.id.nextBtn);
        submitButton = (Button) findViewById(R.id.submitBtn);

        //default visibility to gone
        previousButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);

        mPageCount = 0;
    }

    private void instantiateSharedPreferences () {
        mIsDating=false;
        mGender = "";
        mIs1stPageFilled =false;
        mName = "";
        mNumber="";
        mAnniversary="";
        mBirthday="";
        mMorningEnabled=false;
        mNightEnabled=false;
        mRandomEnabled=false;
        mImportantEnabled=false;
        mIs2ndPageFilled=false;
        mMorningTime="9:00";
        mNightTime ="23:00";
        mNextMorning="00:00";
        mNextNight="00:00";
        mNextRandom="00:00";



        mSharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(key_isFormFilled, false);
        editor.putBoolean(key_isDating, mIsDating);
        editor.putString(key_gender, mGender);
        editor.putBoolean(key_is1stPageFilled, mIs1stPageFilled);
        editor.putString(key_name, mName);
        editor.putString(key_number, mNumber);
        editor.putString(key_anniversary, mAnniversary);
        editor.putString(key_birthday, mBirthday);
        editor.putBoolean(key_morningEnabled, mMorningEnabled);
        editor.putBoolean(key_nightEnabled, mNightEnabled);
        editor.putBoolean(key_randomEnabled, mRandomEnabled);
        editor.putBoolean(key_importantEnabled, mImportantEnabled);
        editor.commit();

        mSharedPreference = getSharedPreferences(SchedulePREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = mSharedPreference.edit();
        editor2.putString(key_userMorning, mMorningTime);
        editor2.putString(key_userNight, mNightTime);
        editor2.putString(key_morningSchedule, mNextMorning);
        editor2.putString(key_nightSchedule, mNextNight);
        editor2.putString(key_randomSchedule,mNextRandom);

        editor2.commit();

    }

    public void listeners() {

        //---set listener to nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to indicate what fragment/form page user will be on
                mPageCount++;

                switch (mPageCount) {
                    case 1:
                        mFragmentTransaction = getFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, mRomanticFragment_Page1);
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();

                        //permanently kills first animations
                        mBlob.setVisibility(View.GONE);
                        letmeBanner.setVisibility(View.GONE);
                        break;
                    //TODO IF USER PRESSES NO DON'T GO ON
                    case 2:
                        //retrieve if users enters No for Dating
                        mSharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        mIsDating = mSharedPref.getBoolean("isDatingK", false);

                        //if user enters yes to dating then go to 2nd form page - or else skip to schedule activity
                        if (mIsDating) {
                            mFragmentTransaction = getFragmentManager().beginTransaction();
                            mFragmentTransaction.replace(R.id.fragmentContainer, mRomanticFragment_Page2);
                            mFragmentTransaction.addToBackStack(null);
                            mFragmentTransaction.commit();

                            //makes previous button visible to allow them to go back to the first page
                            previousButton.setVisibility(View.VISIBLE);
                            nextButton.setEnabled(false);

                            //enables submitButton
                            submitButton.setVisibility(View.VISIBLE);
                        } else {
                            //indicate that form is filled completely and saves user profile
                            mIsFormFilled = true;
                            mSharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putBoolean(key_isFormFilled, mIsFormFilled);
                            editor.commit();

                            //displays message to user
                            Toast.makeText(FirstLaunchActivity.this, "You Dating Settings Saved Successfully!", Toast.LENGTH_LONG)
                                    .show();
                            //moves onto to user's schedule questions & disables next/previous button
                            nextButton.setVisibility(View.GONE);
                            submitButton.setVisibility(View.GONE);
                            previousButton.setVisibility(View.GONE);

                            //opens schedule activity
                            Intent myIntent = new Intent(FirstLaunchActivity.this, ScheduleActivity.class);
                            FirstLaunchActivity.this.startActivity(myIntent);

                        }
                        break;
                }
            }
        });

        //listener to previous button changes
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //to indicate what fragment/form page user will be on
                mPageCount--;


                switch (mPageCount) {
                    case 1:
                        mFragmentTransaction = getFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.fragmentContainer, mRomanticFragment_Page1);
                        mFragmentTransaction.addToBackStack(null);
                        mFragmentTransaction.commit();

                        //makes previou button AND submit Button visibility invisbile and enables next button
                        previousButton.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                        nextButton.setEnabled(true);
                        break;
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO when user submits, check if forms are filled properly, if not error

                //get sharedpref values
                mSharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                mIs1stPageFilled = mSharedPref.getBoolean("is1stPagefilledK", false);
                mIs2ndPageFilled = mSharedPref.getBoolean("is2ndPageFilledK", false);

                if (mIs1stPageFilled && mIs2ndPageFilled) {
                    mIsFormFilled = true;
                    mSharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPref.edit();
                    editor.putBoolean(key_isFormFilled, mIsFormFilled);
                    editor.commit();
                    Toast.makeText(FirstLaunchActivity.this, "You Dating Settings Saved Successfully!", Toast.LENGTH_LONG)
                            .show();
                    //moves onto to user's schedule questions & disables next/previous button
                    nextButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.GONE);

                    //opens schedule activity
                    Intent myIntent = new Intent(FirstLaunchActivity.this, ScheduleActivity.class);
                    FirstLaunchActivity.this.startActivity(myIntent);

                    //CHECKS WHAT USERS SELECTED FOR TEXTING PREFS
                    if (mIsDating){

                    }

                } else {
                    Toast.makeText(FirstLaunchActivity.this, "Please fill out form completely.", Toast.LENGTH_LONG)
                            .show();
                }
                //mRomanticFragment_Page1  =  getFragmentManager().findFragmentById(R.id.radiogroup1);
            }
        });
    }



    //do nothing if user presses back
    @Override
    public void onBackPressed() {
        Toast.makeText(FirstLaunchActivity.this, "No way pal, complete this first.", Toast.LENGTH_LONG)
                .show();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FirstLaunch Page", // TODO: Define a title for the content shown.
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
                "FirstLaunch Page", // TODO: Define a title for the content shown.
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

    // TODO Add to Tisa's Version
    public void chooseContact(View view){

        //Requesting Marshmallow permissions to read contacts
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION__READ_CONTACTS);
    }

    // TODO Add to Tisa's Version

    /**
     * Opens DatePickerFragment and sets text.
     * @param view the button.
     */
    public void chooseAnniversary(View view){

        // This button was clicked
        anniversaryButtonClicked = true;

        // Opening Fragment
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // TODO Add to Tisa's Version
    /**
     * Opens DatePickerFragment and sets text.
     * @param view the button.
     */
    public void chooseBirthday(View view){

        // This button was clicked
        birthdayButtonClicked = true;

        // Opening Fragment
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // TODO Add to Tisa's version
    /**
     * Handles operations based on permission results.
     * @param requestCode the request code.
     * @param permissions the result code.
     * @param grantResults the grant results array.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        switch (requestCode){
            case PERMISSION__READ_CONTACTS: {
                // Granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_PERMISSION);
                }
                // Blocked
                else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){
                    new AlertDialog.Builder(this)
                            .setTitle("Permission was blocked!")
                            .setMessage("You have previously blocked this app from accessing contacts. This feature will not function without this access. Would you like to go to settings and allow this permission?")

                            // Open Settings button
                            .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goToSettings();
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    RomanticQuestionsFragment_page2.chooseContactButton.setEnabled(false);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // Denied
                else{
                    new AlertDialog.Builder(this)
                            .setTitle("Permission was denied!")
                            .setMessage("This feature will not function without access to contacts. Would you like to allow access?")

                            // Open Settings button
                            .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(FirstLaunchActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION__READ_CONTACTS);
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    RomanticQuestionsFragment_page2.chooseContactButton.setEnabled(false);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return;
            }

            case PERMISSION_SEND_SMS:{
                // Granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Continue with app
                }
                // Blocked
                else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                    new AlertDialog.Builder(this)
                            .setTitle("Permission was blocked!")
                            .setMessage("You have previously blocked this app from sending SMS. This app will not function without this access. Would you like to go to settings and allow this permission?")

                            // Open Settings button
                            .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goToSettings();
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //android.os.Process.killProcess(android.os.Process.myPid());
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // Denied
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("Permission was denied!")
                            .setMessage("This app will not function without access to sending SMS. Would you like to allow access?")

                            // Open Settings button
                            .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(FirstLaunchActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //android.os.Process.killProcess(android.os.Process.myPid());
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        }
    }

    // TODO Add to Tisa's version
    /**
     * Performs operations upon successful target selection.
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data the data returned from the Intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION && resultCode == RESULT_OK){
            contact = data.getData();
            retrieveContactName();
            retrieveContactNumber();
        }
    }

    // TODO Add to Tisa's version
    /**
     * Gets the Contact's Name.
     */
    private void retrieveContactName() {

        RomanticQuestionsFragment_page2.question4.setText("");

        Cursor cursor = getContentResolver().query(contact, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            RomanticQuestionsFragment_page2.question4.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            cursor.close();
        }
    }

    // TODO Add to Tisa's version
    /**
     * Gets the Contact's Number.
     */
    private void retrieveContactNumber() {

        RomanticQuestionsFragment_page2.question5.setText("");

        contactNumber = null;

        // Querying Contact ID
        Cursor cursor = getContentResolver().query(contact, new String[]{ContactsContract.Contacts._ID}, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            cursor.close();
        }

        // Retrieving Mobile Number
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                new String[]{contactId}, null);

        // Storing Mobile Number
        if (cursor != null && cursor.moveToFirst()){
            contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            // Cleaning input
            contactNumber = contactNumber.replaceAll("\\D+", "");

            // Trim leading 1
            if (contactNumber.length() > 10){
                contactNumber = contactNumber.substring(1, 11);
            }

            // Setting Text
            RomanticQuestionsFragment_page2.question5.setText(contactNumber);

            // Closing Cursor
            cursor.close();
        }
        if (contactNumber == null || contactNumber.equals("")){
            Toast.makeText(this, "This contact has no associated mobile number!!", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO Add to Tisa's version
    /**
     * Opens the app's settings page in AppManager.
     */
    private void goToSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION);
    }

    /**
     * Inner Class to model a DatePickerFragment
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            // Month is zero indexed, adjusting...
            month += 1;

            // Setting text
            if (anniversaryButtonClicked) {
                if (month < 10 && day < 10){
                    RomanticQuestionsFragment_page2.question6.setText("0" + month + "/" + "0" + day + "/" + year);
                }
                else if (month < 10) {
                    RomanticQuestionsFragment_page2.question6.setText("0" + month + "/" + day + "/" + year);
                }
                else if (day < 10){
                    RomanticQuestionsFragment_page2.question6.setText(month + "/" + "0" + day + "/" + year);
                }
                anniversaryButtonClicked = false;
            }
            else if (birthdayButtonClicked) {
                if (month < 10 && day < 10){
                    RomanticQuestionsFragment_page2.question7.setText("0" + month + "/" + "0" + day + "/" + year);
                }
                else if (month < 10) {
                    RomanticQuestionsFragment_page2.question7.setText("0" + month + "/" + day + "/" + year);
                }
                else if (day < 10){
                    RomanticQuestionsFragment_page2.question7.setText(month + "/" + "0" + day + "/" + year);
                }
                birthdayButtonClicked = false;
            }
        }
    }
}
