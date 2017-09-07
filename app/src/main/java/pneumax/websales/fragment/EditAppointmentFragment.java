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
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;

/**
 * Created by Sitrach on 07/09/2017.
 */

public class EditAppointmentFragment extends Fragment {

    private Employees employeesLogin;
    private ObjectSale objectSaleLogin;
    private GlobalVar globalVar;
    private String appDateString, appTimeString, DPcodeString, SAcodeString;

    public static EditAppointmentFragment editAppointmentInstance(Parcelable parcelEmplyeesLogin,
                                                                  Parcelable parcelObjectSaleLogin,
                                                                  String strAppDate, String strAppTime) {
        EditAppointmentFragment editAppointmentFragment = new EditAppointmentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Employees.TABLE_NAME, parcelEmplyeesLogin);
        bundle.putParcelable(ObjectSale.TABLE_NAME, parcelObjectSaleLogin);
        bundle.putString("AppDate", strAppDate);
        bundle.putString("AppTime", strAppTime);
        editAppointmentFragment.setArguments(bundle);
        return editAppointmentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tag = "7SepV3";
        employeesLogin = (Employees) getArguments().getParcelable(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);
        appDateString = getArguments().getString("AppDate");
        appTimeString = getArguments().getString("AppTime");
        DPcodeString = objectSaleLogin.DPcode;
        SAcodeString = objectSaleLogin.SACode;
        globalVar = new GlobalVar();

        Log.d(tag, "DPcode on Fragment ==> " + DPcodeString);
        Log.d(tag, "SAcode on Fragment ==> " + SAcodeString);
        Log.d(tag, "AppDate on Fragment ==> " + appDateString);
        Log.d(tag, "AppTime on Fragment ==> " + appTimeString);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_appointment, container, false);

        return view;
    }
}//Main Class
