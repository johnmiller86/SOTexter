package questionFragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter.R;

import java.util.Calendar;

/**
 * @author Tisay Huynh && John D. Miller
 * @version 1.0.2
 * @since 05/02/2016
 */
public class RomanticQuestionsFragment_page2  extends Fragment implements View.OnClickListener{

    // UI Components
    private EditText question4, question5;
    private static EditText question6, question7;
    private CheckBox morningCB, nightCB, randomCB;
    private CheckBox importantCB;
    private ImageButton chooseContactButton;
    private Button anniversaryButton, birthdayButton;
    private View view;

    // Global/Instance Variables
    private String mName;
    private String mNumber;
    private String mAnniversary;
    private String mBirthday;
    private Boolean mMorningEnabled;
    private Boolean mNightEnabled;
    private Boolean mRandomEnabled;
    private Boolean mImportantEnabled;
    private Boolean mIs2ndPageFilled;

    // Calendar
    private Calendar mSystemCalendar;
    private int mSystemYear;

    // Shared Prefs
    private SharedPreferences mSharedPreference;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String key_is2ndPageFilled = "is2ndPageFilledK";
    public static final String key_name= "nameK";
    public static final String key_number = "numberK";
    public static final String key_birthday = "birthdayK";
    public static final String key_anniversary = "anniversaryK";
    public static final String key_morningEnabled = "morningEnabledK";
    public static final String key_nightEnabled = "nightEnabledK";
    public static final String key_randomEnabled = "randomEnabledK";
    public static final String key_importantEnabled = "importantEnabledK";

    // Permissions requests
    private static final int REQUEST_PERMISSION = 1;
    private static final int PERMISSION__READ_CONTACTS = 2;

    // Contact Variables
    private Uri contact;
    private String contactId, contactNumber;

    // Calendar variables
    private static boolean anniversaryButtonClicked = false;
    private static boolean birthdayButtonClicked = false;


    private String blockCharacterSet = "*+#-.";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_romantic_2nd_page, container, false);

        //initialize fragment widgets
        initializeWidgets();

        //add listener events
        listeners();

        //return view
        return  view;
    }

    private void initializeWidgets() {
        //declare
        question4 = (EditText)view.findViewById(R.id.ques4_editText);
        question5 = (EditText)view.findViewById(R.id.ques5_editText);
        question6 = (EditText)view.findViewById(R.id.rQuestion6_editText);
        question7 = (EditText)view.findViewById(R.id.rQuestion7_editText);
        morningCB = (CheckBox)view.findViewById(R.id.morning_checkBox);
        nightCB = (CheckBox)view.findViewById(R.id.night_checkBox);
        randomCB = (CheckBox)view.findViewById(R.id.random_checkBox);
        importantCB = (CheckBox)view.findViewById(R.id.importantDate_checkBox);
        chooseContactButton = (ImageButton) view.findViewById(R.id.button2);
        anniversaryButton = (Button) view.findViewById(R.id.button3);
        birthdayButton = (Button) view.findViewById(R.id.button4);

        //add lsiteners to texxt changed
        question4.addTextChangedListener(question4TextWatcher);
        question5.addTextChangedListener(question5TextWatcher);
        question6.addTextChangedListener(question6TextWatcher);
        question6.setFilters(new InputFilter[]{filter});
        question7.addTextChangedListener(question7TextWatcher);
        question7.setFilters(new InputFilter[]{filter});

        // Adding button listeners
        chooseContactButton.setOnClickListener(this);
        birthdayButton.setOnClickListener(this);
        anniversaryButton.setOnClickListener(this);

        mSystemCalendar = Calendar.getInstance();
        mSystemYear = mSystemCalendar.get(Calendar.YEAR);

        //initialize member vars.
        mName = "";
        mNumber="";
        mAnniversary="";
        mBirthday="";
        mMorningEnabled=false;
        mNightEnabled=false;
        mRandomEnabled=false;
        mImportantEnabled=false;
        mIs2ndPageFilled=false;

    }
    // Button Listener
    @Override
    public void onClick(View view){
        DialogFragment dialogFragment;
        switch (view.getId()){
            case R.id.button2:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION__READ_CONTACTS);
                }else{
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_PERMISSION);
                }

                break;
            case R.id.button3:
                // This button was clicked
                anniversaryButtonClicked = true;

                // Opening Fragment
                dialogFragment = new DatePickerFragment();
                dialogFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.button4:
                // This button was clicked
                birthdayButtonClicked = true;

                // Opening Fragment
                dialogFragment = new DatePickerFragment();
                dialogFragment.show(getFragmentManager(), "datePicker");
                break;
        }
    }

    private final TextWatcher question4TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mName = question4.getText().toString();
            preferencesChanged ();
        }
    };

    private final TextWatcher question5TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = question5.getText().toString();
            if (temp.length()==10) {
                // Number retrieved, storing
                mNumber = question5.getText().toString();
                preferencesChanged();
            }
            else if(temp.length() == 0){
                // Filled by contact picker no error
            }
            else{
                // Set error not 10 digits
                question5.setError("Valid phone number format is 10 digits. You entered: "+temp.length());
            }
        }
    };

    private final TextWatcher question6TextWatcher = new TextWatcher() {
        private String current = "";
        private String mmddyyyy = "MMDDYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + mmddyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int mon  = Integer.parseInt(clean.substring(0,2));
                    int day  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>mSystemYear)?mSystemYear:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",mon, day, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                question6.setText(current);
                question6.setSelection(sel < current.length() ? sel : current.length());
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
            //if mm/dd/yyy was filled out correctly
            if (!question6.getText().toString().matches(".*[DMY].*")){
                mAnniversary = question6.getText().toString();
                preferencesChanged();
            } else {
                question6.setError("Please enter valid date.");
            }
        }
    };

    private final TextWatcher question7TextWatcher = new TextWatcher() {
        private String current = "";
        private String mmddyyyy = "MMDDYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + mmddyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int mon  = Integer.parseInt(clean.substring(0,2));
                    int day  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>mSystemYear)?mSystemYear:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",mon, day, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                question7.setText(current);
                question7.setSelection(sel < current.length() ? sel : current.length());
            }
        }


        @Override
        public void afterTextChanged(Editable s) {
            //if mm/dd/yyy was filled out correctly
            if (!question7.getText().toString().matches(".*[DMY].*")){
                mBirthday = question7.getText().toString();
                preferencesChanged ();
            } else {
                question7.setError("Please enter valid date.");
            }
        }
    };

    private void listeners () {
        morningCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                     if (isChecked){
                                                         mMorningEnabled =true;
                                                     } else
                                                     {
                                                         mMorningEnabled=false;
                                                     }
                                                     preferencesChanged ();
                                                 }

                                             }
        );
        nightCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                     if (isChecked){
                                                         mNightEnabled =true;
                                                     } else
                                                     {
                                                         mNightEnabled=false;
                                                     }
                                                     preferencesChanged ();

                                                 }
                                             }
        );
        randomCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                     if (isChecked){
                                                         mRandomEnabled=true;
                                                     } else
                                                     {
                                                         mRandomEnabled=false;
                                                     }
                                                     preferencesChanged ();

                                                 }
                                             }
        );
        importantCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                     if (isChecked){
                                                         mImportantEnabled=true;
                                                     } else
                                                     {
                                                         mImportantEnabled=false;
                                                     }
                                                     preferencesChanged ();

                                                 }
                                             }
        );
    }



    private void preferencesChanged () {
        mSharedPreference = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString(key_name, mName);
        editor.putString(key_number, mNumber);
        editor.putString(key_anniversary, mAnniversary);
        editor.putString(key_birthday, mBirthday);
        editor.putBoolean(key_morningEnabled, mMorningEnabled);
        editor.putBoolean(key_nightEnabled, mNightEnabled);
        editor.putBoolean(key_randomEnabled, mRandomEnabled);
        editor.putBoolean(key_importantEnabled, mImportantEnabled);
        //will check if all required fields are answered, then 2nd page is filled
        validate();
        if (mIs2ndPageFilled){
            editor.putBoolean(key_is2ndPageFilled, mIs2ndPageFilled);
        }
        editor.commit();
    }

    public void validate() {
        Boolean name_phoneNum = false;
        Boolean anniversary_birthday = false;

        //if question 4 and 5 has been filled out
        if (!question4.getText().toString().trim().equals("") && !question5.getText().toString().trim().equals("")) {
            name_phoneNum = true;
        }



        //if both question6 and question 7 is filled out
         if (!question6.getText().toString().matches(".*[DMY].*") && !question7.getText().toString().matches(".*[DMY].*")) {
             if (!question6.getText().toString().trim().equals("") && !question7.getText().toString().trim().equals("")) {
                 String tempTest = question6.getText().toString();
                 anniversary_birthday = true;
             }
         }

         if (name_phoneNum && anniversary_birthday) {
                mIs2ndPageFilled = true;
         } else {
                mIs2ndPageFilled = false;
         }
    }

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
                else if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)){
                    new AlertDialog.Builder(getActivity())
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
                                    chooseContactButton.setEnabled(false);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // Denied
                else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Permission was denied!")
                            .setMessage("This feature will not function without access to contacts. Would you like to allow access?")

                            // Open Settings button
                            .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION__READ_CONTACTS);
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    chooseContactButton.setEnabled(false);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return;
            }
        }
    }

    /**
     * Performs operations upon successful target selection.
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data the data returned from the Intent.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION && resultCode == Activity.RESULT_OK){
            contact = data.getData();
            retrieveContactName();
            retrieveContactNumber();
        }
    }

    /**
     * Gets the Contact's Name.
     */
    private void retrieveContactName() {

        question4.setText("");

        Cursor cursor = getActivity().getContentResolver().query(contact, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            question4.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            cursor.close();
        }
    }

    /**
     * Gets the Contact's Number.
     */
    private void retrieveContactNumber() {

        question5.setText("");

        contactNumber = null;

        // Querying Contact ID
        Cursor cursor = getActivity().getContentResolver().query(contact, new String[]{ContactsContract.Contacts._ID}, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            cursor.close();
        }

        // Retrieving Mobile Number
        cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
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
            question5.setText(contactNumber);

            // Closing Cursor
            cursor.close();
        }
        if (contactNumber == null || contactNumber.equals("")){
            Toast.makeText(getActivity(), "This contact has no associated mobile number!!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens the app's settings page in AppManager.
     */
    private void goToSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
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
                    question6.setText("0" + month + "/" + "0" + day + "/" + year);
                }
                else if (month < 10) {
                    question6.setText("0" + month + "/" + day + "/" + year);
                }
                else if (day < 10){
                    question6.setText(month + "/" + "0" + day + "/" + year);
                }
                anniversaryButtonClicked = false;
            }
            else if (birthdayButtonClicked) {
                if (month < 10 && day < 10){
                    question7.setText("0" + month + "/" + "0" + day + "/" + year);
                }
                else if (month < 10) {
                    question7.setText("0" + month + "/" + day + "/" + year);
                }
                else if (day < 10){
                    question7.setText(month + "/" + "0" + day + "/" + year);
                }
                birthdayButtonClicked = false;
            }
        }
    }
}
