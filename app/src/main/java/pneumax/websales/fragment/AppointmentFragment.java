package pneumax.websales.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pneumax.websales.R;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;

/**
 * Created by Sitrach on 06/09/2017.
 */

public class AppointmentFragment extends Fragment{

    private Employees employeesLogin;
    private ObjectSale objectSaleLogin;
    //Explicit
    private String DPcodeString, SAcodeString;


    public static AppointmentFragment appointmentInsatance(Parcelable parcelEmplyeesLogin,
                                                           Parcelable parcelObjectSaleLogin) {

        AppointmentFragment appointmentFragment = new AppointmentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Employees.TABLE_NAME, parcelEmplyeesLogin);
        bundle.putParcelable(ObjectSale.TABLE_NAME, parcelObjectSaleLogin);
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

        employeesLogin = (Employees) getArguments().getParcelable(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);

        Log.d("6SepV1", "DPcode on Fragment ==> " + objectSaleLogin.DPcode);
        Log.d("6SepV1", "SAcode on Fragment ==> " + objectSaleLogin.SACode);
    }//onCreate

}//Main Class
