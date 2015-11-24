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


public class Tab6Fragment extends Fragment {
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public static Tab6Fragment newInstance() {
        Tab6Fragment fragment = new Tab6Fragment();
        return fragment;
    }

    public Tab6Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_frag6_layout, null);
        return root;
    }
}