package TutorialAdministrador;


/**
 * Created by Nicole on 21-09-2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicole.smartcoming.R;


public class Tab6admFragment extends Fragment {
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public static Tab6admFragment newInstance() {
        Tab6admFragment fragment = new Tab6admFragment();
        return fragment;
    }

    public Tab6admFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_frag6adm_layout, null);
        return root;
    }
}
