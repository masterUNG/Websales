package pneumax.websales.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import pneumax.websales.R;
import pneumax.websales.connected.GetAppointmentGridWhere;
import pneumax.websales.manager.AppointmentAdapter;
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
    private GlobalVar globalVar;
    //Explicit
    private String DPcodeString, SAcodeString;
    private TextView mtxtvSearchStartDate;
    private TextView mtxtvSearchEndDate;

    public static AppointmentFragment appointmentInsatance(Parcelable parcelEmplyeesLogin,
                                                           Parcelable parcelObjectSaleLogin) {
        //Convert Parcelable To Bundle
        //ถ้าต้องการส่ง Parcelable เข้า Fragment ต้องทำเป็น Bundle ก่อน จากนั้นจึงค่อย Get ค่าได้
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

        mtxtvSearchStartDate = (TextView) view.findViewById(R.id.txtvSearchStartDate);
        mtxtvSearchEndDate = (TextView) view.findViewById(R.id.txtvSearchEndate);

        mtxtvSearchStartDate.setText("2017-08-01");
        mtxtvSearchEndDate.setText("2017-09-30");

        //set Search StartDate EndDate
        setSearchDate();

        return view;
    }//onCreateView

    private void setSearchDate() {
        mtxtvSearchStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                //ถ้า วันที่นัด เป็นว่างให้ Set ค่าเริ่มต้นด้วย
//                String strStartDate = mtxtvSearchStartDate.getText().toString();
//                if (!globalVar.isEmptyString(strStartDate)) {
//                    Date date = globalVar.setConvertStringToDate(mtxtvSearchStartDate.getText().toString());
//
//                    mYear = 2017; // current year
//                    mMonth = c.get(Calendar.MONTH); // current month
//                    mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                }

                // date picker dialog
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                mtxtvSearchStartDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mtxtvSearchEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }//setSearchDate

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        employeesLogin = (Employees) getArguments().getParcelable(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);
        DPcodeString = objectSaleLogin.DPcode;
        SAcodeString = objectSaleLogin.SACode;
        globalVar = new GlobalVar();

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

            resultJSON = globalVar.JsonXmlToJsonString(resultJSON);
            Log.d(tag, "JSON ==> " + resultJSON);

            JSONArray jsonArray = new JSONArray(resultJSON);
            final String[] AppdateStrings = new String[jsonArray.length()];
            final String[] AppStartTimeStrings = new String[jsonArray.length()];
            String[] CSnameStrings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AppdateStrings[i] = globalVar.FormatDateOfString_ddMMyyyy(jsonObject.getString("AppDate"));
                AppStartTimeStrings[i] = jsonObject.getString("AppStartTime");
                CSnameStrings[i] = jsonObject.getString("CSthiname");

                Log.d(tag, "Name [" + i + "] ==> " + CSnameStrings[i]);
            }//for

            AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getActivity(),
                    AppdateStrings, AppStartTimeStrings, CSnameStrings);
            listView.setAdapter(appointmentAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Click Item in Row ListView
                    confirmDialog(AppdateStrings[i], AppStartTimeStrings[i]);
                }
            });

        } catch (Exception e) {
            Log.d(tag, "e Create ListView ==> " + e.toString());
        }


    }//onActivityCreated

    private void confirmDialog(String appdateString, String appStartTimeString) {
        CharSequence[] charSequences = new CharSequence[]{"Edit", "Delete"};
        final int[] ints = new int[]{0};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); //ถ้าไม่มีการเลือก false คือไม่ต้องทำอะไร
        builder.setIcon(android.R.drawable.ic_dialog_info); //Icon Dialog
        builder.setTitle("Do you want to Edit or Delete ?");
        //Default ตัวแปรก Edit ต้อง set เป็น 0
        builder.setSingleChoiceItems(charSequences, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ints[0] = i + 1;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); //Close ตัวเอง
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }//ConfirmDialog

}//Main Class
