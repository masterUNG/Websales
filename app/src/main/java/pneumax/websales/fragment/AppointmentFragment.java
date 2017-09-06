package pneumax.websales.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import pneumax.websales.R;
import pneumax.websales.connected.GetAppointmentGridWhere;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;

/**
 * Created by Sitrach on 06/09/2017.
 */

public class AppointmentFragment extends Fragment {

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
        DPcodeString = objectSaleLogin.DPcode;
        SAcodeString = objectSaleLogin.SACode;

        Log.d("6SepV1", "DPcode on Fragment ==> " + DPcodeString);
        Log.d("6SepV1", "SAcode on Fragment ==> " + SAcodeString);
    }//onCreate

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create ListView
        ListView listView = getView().findViewById(R.id.livAppointment);
        MyConstant myConstant = new MyConstant();
        String tag = "6SepV2";

        try {
            GetAppointmentGridWhere getAppointmentGridWhere = new GetAppointmentGridWhere(getActivity());
            getAppointmentGridWhere.execute(
                    objectSaleLogin.DPcode,
                    objectSaleLogin.SACode,
                    myConstant.getStartDateString(),
                    myConstant.getEndDateString(),
                    myConstant.getUrlGetAppointmentGrid());

            String resultJSON = getAppointmentGridWhere.get();

            GlobalVar globalVar = new GlobalVar();
            resultJSON = globalVar.JsonXmlToJsonString(resultJSON);
            Log.d(tag, "JSON ==> " + resultJSON);

            JSONArray jsonArray = new JSONArray(resultJSON);
            String[] AppdateStrings = new String[jsonArray.length()];
            String[] AppStartTimeStrings = new String[jsonArray.length()];
            String[] CSnameStrings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AppdateStrings[i] = jsonObject.getString("AppDate");
                AppStartTimeStrings[i] = jsonObject.getString("AppStartTime");
                CSnameStrings[i] = jsonObject.getString("CSthiname");

                Log.d(tag, "Name [" + i + "] ==> " + CSnameStrings[i]);
            }//for

        } catch (Exception e) {
            Log.d(tag, "e Create ListView ==> " + e.toString());
        }


    }//onActivityCreated

}//Main Class
