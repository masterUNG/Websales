package pneumax.websales.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pneumax.websales.R;

/**
 * Created by Sitrach on 06/09/2017.
 */

public class AppointmentFragment extends Fragment{

    //Explicit
    private String DPcodeString, SAcodeString;


    public static AppointmentFragment appointmentInsatance(String strDPcode,
                                                           String strSAcode) {

        AppointmentFragment appointmentFragment = new AppointmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DPcode", strDPcode);
        bundle.putString("SAcode", strSAcode);
        appointmentFragment.setArguments(bundle);
        return appointmentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        return view;
    }//onCreateView

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DPcodeString = getArguments().getString("DPcode");
        SAcodeString = getArguments().getString("SAcode");

        Log.d("6SepV1", "DPcode on Fragment ==> " + DPcodeString);
        Log.d("6SepV1", "SAcode on Fragment ==> " + SAcodeString);
    }//onCreate

}//Main Class
