package questionFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageButton;


import java.util.Calendar;

import com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter.R;

/**
 * @author Tisay Huynh && John D. Miller
 * @version 1.0.2
 * @since 05/02/2016
 */
public class RomanticQuestionsFragment_page2  extends Fragment{

    // TODO Make these EditTexts public statics
    public static EditText question4;
    public static EditText question5;
    public static EditText question6;
    public static EditText question7;

    private CheckBox morningCB;
    private CheckBox nightCB;
    private CheckBox randomCB;
    private CheckBox importantCB;
    private View view;


    private String mName;
    private String mNumber;
    private String mAnniversary;
    private String mBirthday;
    private Boolean mMorningEnabled;
    private Boolean mNightEnabled;
    private Boolean mRandomEnabled;
    private Boolean mImportantEnabled;
    private Boolean mIs2ndPageFilled;

    // TODO Add to Tisa's version
    public static ImageButton chooseContactButton;
    public static Button anniversaryButton, birthdayButton;

    private Calendar mSystemCalendar;
    private int mSystemYear;

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

        //TODO add to Tisa's version
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
            }else{
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

}
