package pneumax.websales.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pneumax.websales.R;

/**
 * Created by Sitrach on 06/09/2017.
 */

public class CustInfoByPhoneFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cust_byphone, container, false);

        return view;
    }
}//Main Class
