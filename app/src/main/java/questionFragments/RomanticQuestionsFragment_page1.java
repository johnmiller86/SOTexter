package questionFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter.R;

/**
 * @author Tisay Huynh
 * @version 1.0.2
 * @since 05/02/2016
 */
public class RomanticQuestionsFragment_page1 extends Fragment {

    //radio groups
    private RadioGroup rRadioGroup1;
    private RadioGroup rRadioGroup2;

    //Textview questions
    private TextView question2;
    View view;

    //answers
    private Boolean mIsDating;
    private String mGender;
    private Boolean mIsfirstPagefilled;

    private SharedPreferences mSharedPreference;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String key_is1stPageFilled = "is1stPagefilledK";
    public static final String key_isFormFilled = "isFormFilledK";
    public static final String key_isDating= "isDatingK";
    public static final String key_gender = "genderK";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_romantic_1st_page, container, false);

        //initialize fragment widgets
        initialize();

        //add listener events
        listeners();
        
        //call activity
        //mActivity =(FirstLaunchActivity)getActivity();
        //mActivity.userPreferences.setIsDating(mIsDating);

        return view;
    }


    private void initialize() {
        //declare
        rRadioGroup1 = (RadioGroup) view.findViewById(R.id.radiogroup1);
        rRadioGroup2 = (RadioGroup) view.findViewById(R.id.radiogroup2);
        question2 = (TextView) view.findViewById(R.id.question2_textview);

        mIsDating=false;
        mGender = "";
        mIsfirstPagefilled =false;
    }


    private void listeners() {
        rRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ques2_no_radio) {
                    //IF user is not dating, hide dating related questions & answers
                    question2.setVisibility(View.GONE);
                    rRadioGroup2.setVisibility(View.GONE);
                    mGender = "notDating";
                    mIsDating = false;
                } else if (checkedId == R.id.ques1_yes_radio) {
                    //IF user is not dating, hide dating related questions & answers
                    question2.setVisibility(View.VISIBLE);
                    rRadioGroup2.setVisibility(View.VISIBLE);
                    mIsDating = true;
                }
                preferenceChanged();
            }//end of oncheckedchanged listener
        });

        rRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boy_radio) {
                   mGender = "boy";
                } else if (checkedId == R.id.girl_radio) {
                    mGender = "girl";
                } else if (checkedId == R.id.other_radio) {
                    mGender = "other";
                }
                preferenceChanged ();
            }//end of oncheckedchanged listener
        });

    }

    public void preferenceChanged () {
        mSharedPreference = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(key_isFormFilled, false);
        editor.putBoolean(key_isDating, mIsDating);
        editor.putString(key_gender, mGender);
        //either all radiogroups are filled or if user answers no to dating
        if ((rRadioGroup1.getCheckedRadioButtonId() != -1 && rRadioGroup2.getCheckedRadioButtonId() != -1 || rRadioGroup1.getCheckedRadioButtonId() ==  R.id.ques2_no_radio )){
            mIsfirstPagefilled =true;
        } else {
            mIsfirstPagefilled=false;
        }
        editor.putBoolean(key_is1stPageFilled, mIsfirstPagefilled);
        editor.commit();
    }


}