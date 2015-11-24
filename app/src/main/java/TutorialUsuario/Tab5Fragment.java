package TutorialUsuario;

/**
 * Created by Nicole on 15-10-2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicole.smartcoming.R;


public class Tab5Fragment extends Fragment {
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public static Tab5Fragment newInstance() {
        Tab5Fragment fragment = new Tab5Fragment();
        return fragment;
    }

    public Tab5Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_frag5_layout, null);
        return root;
    }
}