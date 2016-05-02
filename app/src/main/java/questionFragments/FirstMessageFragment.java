package questionFragments;

/**
 * Created by Tisa on 4/3/2016.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.twh5257_jdm5908_zsw5029_ick5008_bw.ist402.so_texter.R;

public class FirstMessageFragment extends Fragment {
    private ImageView banner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_message, container, false);

        /*inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //set background of imageview of banner animation
        banner = (ImageView) inflater.inflate(R.layout.help_message_view, null);
        banner.setBackgroundResource(R.anim.help_message_animation);

        // CREATE AN ANIMATION DRAWABLE OBJECT BASED ON THIS BACKGROUND
        AnimationDrawable manAnimate = (AnimationDrawable) banner.getBackground();
        manAnimate.start();*/

    }
}
