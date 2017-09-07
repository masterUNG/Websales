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
import android.widget.Button;
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
    private String DPcodeString, SAcodeString, startDateString, endDateString;
    private TextView mtxtvSearchStartDate;
    private TextView mtxtvSearchEndDate;
    private Calendar calendar;
    private int currentDayAnInt, currentMonthAnInt, currentYearAnInt;

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
        return view;
    }//onCreateView


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

        //Setup Start and End Date
        setupStartEndDate();

        //Create ListView
        createListView();

        //Find Current Time
        findCurrentTime();

        //Search Controller
        searchController();

        //Setup Search Start and End Date
        setupSearchStartEndDate();

    }//onActivityCreated

    private void setupStartEndDate() {
        String tag = "7SepV1";
        MyConstant myConstant = new MyConstant();
        startDateString = myConstant.getStartDateString();
        endDateString = myConstant.getEndDateString();
        Log.d(tag, "StartDate (0)==> " + startDateString);
    }

    private void findCurrentTime() {
        calendar = Calendar.getInstance();

        int currentDayStartAnInt, currentMonthStartAnInt, currentYearStartAnInt;
        Calendar calendarStartDate = Calendar.getInstance();
        //Before 10 Day
//        int intDay = calendar.get(Calendar.DAY_OF_YEAR);
//        if (intDay>10) {
//            intDay = intDay - 10;
//        }
//        calendar.set(Calendar.DAY_OF_YEAR, intDay);

        //Before 1 Month
        int intMonth = calendarStartDate.get(Calendar.MONTH);
        if (intMonth != 0) {
            intMonth -= 1;
        }
        calendarStartDate.set(Calendar.MONTH, intMonth);
        currentDayStartAnInt = calendarStartDate.get(Calendar.DAY_OF_MONTH);
        currentMonthStartAnInt = calendarStartDate.get(Calendar.MONTH); //0 ==> Jan, 1 ==> Feb
        currentYearStartAnInt = calendarStartDate.get(Calendar.YEAR);

        currentDayAnInt = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonthAnInt = calendar.get(Calendar.MONTH); //0 ==> Jan, 1 ==> Feb
        currentYearAnInt = calendar.get(Calendar.YEAR);

        startDateString = mySetupDate(currentYearStartAnInt, currentMonthStartAnInt, currentDayStartAnInt);
        endDateString = mySetupDate(currentYearAnInt, currentMonthAnInt, currentDayAnInt);

        TextView _txtvSearchStartDate = getView().findViewById(R.id.txtvSearchStartDate);
        TextView _txtvSearchEndDate = getView().findViewById(R.id.txtvSearchEndate);
        _txtvSearchStartDate.setText(startDateString);
        _txtvSearchEndDate.setText(endDateString);
    }//findCurrentTime

    private void setupSearchStartEndDate() {
        final TextView textView = getView().findViewById(R.id.txtvSearchStartDate);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int intYear, int intMonth, int intDay) {
                        startDateString = mySetupDate(intYear, intMonth, intDay);
                        textView.setText(startDateString);
                    }
                }, currentYearAnInt, currentMonthAnInt, currentDayAnInt);
                datePickerDialog.show();
            }
        });

        final TextView _txtvSearchEndDate = getView().findViewById(R.id.txtvSearchEndate);
        _txtvSearchEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int intYear, int intMonth, int intDay) {
                        endDateString = mySetupDate(intYear, intMonth, intDay);
                        _txtvSearchEndDate.setText(endDateString);
                    }
                }, currentYearAnInt, currentMonthAnInt, currentDayAnInt);
                datePickerDialog.show();
            }
        });
    }//setupStartDate

    private String mySetupDate(int intYear, int intMonth, int intDay) {
        String tag = "7SepV1";
        String resultString = null;

        String strYear = Integer.toString(intYear);
        String strMonth = Integer.toString(intMonth + 1);;
        if (strMonth.length() == 1) {
            strMonth = "0" + strMonth;
        }

        String strDay = Integer.toString(intDay);
        if (strDay.length() == 1) {
            strDay = "0" + strDay;
        }
        resultString = strYear + "-" + strMonth + "-" + strDay;
        Log.d(tag, "resultString ==> " + resultString);

        return resultString;
    }

    private void searchController() {
        Button button = getView().findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create ListView
                createListView();
            }
        });
    }//Search Controller


    private void createListView() {
        ListView listView = getView().findViewById(R.id.livAppointment);
        MyConstant myConstant = new MyConstant();
        String tag = "6SepV2";

        try {
            GetAppointmentGridWhere getAppointmentGridWhere = new GetAppointmentGridWhere(getActivity());
            getAppointmentGridWhere.execute(
                    objectSaleLogin.DPcode,
                    objectSaleLogin.SACode,
                    startDateString,
                    endDateString,
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
    }//createListView


    private void confirmDialog(String appdateString, String appStartTimeString) {
        String tag = "7SepV2";
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
