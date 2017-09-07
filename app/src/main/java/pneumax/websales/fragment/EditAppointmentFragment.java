package pneumax.websales.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import pneumax.websales.R;
import pneumax.websales.connected.PostFourString;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.Appointment;
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
        globalVar = new GlobalVar();
        employeesLogin = (Employees) getArguments().getParcelable(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);
        appDateString = globalVar.FormatStringDate_ddMMyyyy_To_yyyyMMdd(getArguments().getString("AppDate"));
        appTimeString = getArguments().getString("AppTime");
        DPcodeString = objectSaleLogin.DPcode;
        SAcodeString = objectSaleLogin.SACode;


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Show View
        showView();
    }

    private void showView() {
        String tag = "7SepV3";
        MyConstant myConstant = new MyConstant();
        try {
            PostFourString postFourString = new PostFourString(getActivity());
            postFourString.execute(
                    "DPcode", DPcodeString,
                    "SAcode", SAcodeString,
                    "AppDate", appDateString,
                    "AppTime", appTimeString, myConstant.getUrlGetAppointment());
            String strJSON = postFourString.get();
            Log.d(tag, "strJSON ==> " + strJSON);
            strJSON = globalVar.JsonXmlToJsonString(strJSON);
            Gson gson = new Gson();
            Appointment appointment = gson.fromJson(strJSON.toString(), Appointment.class);

        } catch (Exception e) {
            Log.d(tag, "e ShoView ==> " + e.toString());

        }
    }
}//Main Class
