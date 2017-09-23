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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import pneumax.websales.R;
import pneumax.websales.SuccessActivity;
import pneumax.websales.connected.GetAppointmentGridWhere;
import pneumax.websales.connected.PostFourString;
import pneumax.websales.manager.AppointmentAdapter;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;
import pneumax.websales.object.ResultExecuteSQL;

/**
 * Created by Sitrach on 06/09/2017.
 */

public class AppointmentFragment extends Fragment {

    private Employees employeesLogin;
    private ObjectSale objectSaleLogin;
    private GlobalVar globalVar;
    private MyConstant myConstant;
    //Explicit
    private String DPcodeString, SAcodeString, startDateString, endDateString, searchCustName;
    private TextView mtxtvSearchStartDate;
    private TextView mtxtvSearchEndDate;
    private EditText mtxtSearchCustName;
    private FloatingActionButton mFabBtnAdd;
    private Calendar calendarStartDate, calendarEndDate;
    private int currentDayStartAnInt, currentMonthStartAnInt, currentYearStartAnInt;
    private int currentDayEndAnInt, currentMonthEndAnInt, currentYearEndAnInt;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        employeesLogin = (Employees) getArguments().getParcelable(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);
        DPcodeString = objectSaleLogin.DPcode;
        SAcodeString = objectSaleLogin.SACode;
        globalVar = new GlobalVar();
        myConstant = new MyConstant();
        this.startDateString = "";
        this.endDateString = "";
        this.searchCustName = "";
        if (savedInstanceState != null) {
            this.startDateString = savedInstanceState.getString(globalVar.getStartDateString);
            currentDayStartAnInt = globalVar.getDay_fromStringYYYYMMDD(startDateString);
            currentMonthStartAnInt = globalVar.getMonth_fromStringYYYYMMDD(startDateString);
            currentYearStartAnInt = globalVar.getYear_fromStringYYYYMMDD(startDateString);
            currentMonthStartAnInt -= 1;
            calendarStartDate = Calendar.getInstance();
            calendarStartDate.set(Calendar.YEAR, currentYearStartAnInt);
            calendarStartDate.set(Calendar.MONTH, currentMonthStartAnInt);
            calendarStartDate.set(Calendar.DAY_OF_MONTH, currentDayStartAnInt);

            this.endDateString = savedInstanceState.getString(globalVar.getEndDateString);
            currentDayEndAnInt = globalVar.getDay_fromStringYYYYMMDD(endDateString);
            currentMonthEndAnInt = globalVar.getMonth_fromStringYYYYMMDD(endDateString);
            currentYearEndAnInt = globalVar.getYear_fromStringYYYYMMDD(endDateString);
            currentMonthEndAnInt -= 1;
            calendarEndDate = Calendar.getInstance();
            calendarEndDate.set(Calendar.YEAR, currentYearEndAnInt);
            calendarEndDate.set(Calendar.MONTH, currentMonthEndAnInt);
            calendarEndDate.set(Calendar.DAY_OF_MONTH, currentDayEndAnInt);

            searchCustName = savedInstanceState.getString(globalVar.getCSNameSring);
        }
        //Find Current Time
        findCurrentTime();

        Log.d("6SepV1", "DPcode on Fragment ==> " + DPcodeString);
        Log.d("6SepV1", "SAcode on Fragment ==> " + SAcodeString);
    }//onCreate

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save value Current
        outState.putString(globalVar.getStartDateString, this.startDateString);
        outState.putString(globalVar.getEndDateString, this.endDateString);
        outState.putString(globalVar.getCSNameSring, mtxtSearchCustName.getText().toString());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        return view;
    }//onCreateView

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //getView().findViewById(R.id. ต้องไว้ใน  onActivityCreated Or onCreateView
        mtxtvSearchStartDate = getView().findViewById(R.id.txtvSearchStartDate);
        mtxtvSearchEndDate = getView().findViewById(R.id.txtvSearchEndate);
        mtxtvSearchStartDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(startDateString));
        mtxtvSearchEndDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(endDateString));
        mtxtSearchCustName = getView().findViewById(R.id.txtSearchCustName);
        mtxtSearchCustName.setText("");

        mFabBtnAdd = getView().findViewById(R.id.fabBtnAdd);

        //Create ListView
        createListView();

        //Search Controller
        searchController();

        //Setup Search Start and End Date
        setupSearchStartEndDate();

        //Setup Button Add
        setupButtonAdd();


    }//onActivityCreated


    @Override
    public void onResume() {
        super.onResume();

        try {
            // Check for Return Value from Fragment in main activity
            String message = ((SuccessActivity) getActivity()).getReturnValueFragment();

            // If any, display as snackbar
            if (message != null) {
                createListView();

                // Reset Return Value from Fragment in SuccessActivity
                ((SuccessActivity) getActivity()).resetReturnValueFragment();
            }

            Log.d("7SepV4", "onResume Work");
        } catch (Exception e) {
            Log.d("7SepV4", "e onResume ==> " + e.toString());
        }
    }//onResume


    public void getResultFragment(String strResult) {
        String s = strResult;
    }

    private void findCurrentTime() {
        if (startDateString.equals("")) {
            calendarStartDate = Calendar.getInstance();
            //Before 1 Month
//            int intMonth = calendarStartDate.get(Calendar.MONTH);
//            if (intMonth != 0) {
//                intMonth -= 1;
//            }
//            calendarStartDate.set(Calendar.MONTH, intMonth);

            //Start Date
            //Before 8 Day
            int intDay = calendarStartDate.get(Calendar.DAY_OF_YEAR);
//            if (intDay > 8) {
            intDay = intDay - 8;
//            }
            calendarStartDate.set(Calendar.DAY_OF_YEAR, intDay);
            currentDayStartAnInt = calendarStartDate.get(Calendar.DAY_OF_MONTH);
            currentMonthStartAnInt = calendarStartDate.get(Calendar.MONTH); //0 ==> Jan, 1 ==> Feb
            currentYearStartAnInt = calendarStartDate.get(Calendar.YEAR);
            //Parameters Start Date
            startDateString = globalVar.FormatDateyyyy_MM_dd_fromDateInteger(currentYearStartAnInt, currentMonthStartAnInt, currentDayStartAnInt);
        }

        if (endDateString.equals("")) {
            calendarEndDate = Calendar.getInstance();
            //Before 1 Month
            int intMonth = calendarEndDate.get(Calendar.MONTH);
            if (intMonth != 0) {
                intMonth += 2;
            }
            calendarEndDate.set(Calendar.MONTH, intMonth);

            //End Date
            currentDayEndAnInt = calendarEndDate.get(Calendar.DAY_OF_MONTH);
            currentMonthEndAnInt = calendarEndDate.get(Calendar.MONTH); //0 ==> Jan, 1 ==> Feb
            currentYearEndAnInt = calendarEndDate.get(Calendar.YEAR);
            //Parameters End Date
            endDateString = globalVar.FormatDateyyyy_MM_dd_fromDateInteger(currentYearEndAnInt, currentMonthEndAnInt, currentDayEndAnInt);
        }
    }//findCurrentTime


    private void setupSearchStartEndDate() {
        mtxtvSearchStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int intYear, int intMonth, int intDay) {
                        startDateString = globalVar.FormatDateyyyy_MM_dd_fromDateInteger(intYear, intMonth, intDay);
                        mtxtvSearchStartDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(startDateString));
                        currentYearStartAnInt = intYear;
                        currentMonthStartAnInt = intMonth;
                        currentDayStartAnInt = intDay;

                        calendarStartDate.set(Calendar.DAY_OF_MONTH, currentDayStartAnInt);
                        calendarStartDate.set(Calendar.MONTH, currentMonthStartAnInt);
                        calendarStartDate.set(Calendar.YEAR, currentYearStartAnInt);
                    }
                }, currentYearStartAnInt, currentMonthStartAnInt, currentDayStartAnInt);
                datePickerDialog.show();
            }
        });

        mtxtvSearchEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int intYear, int intMonth, int intDay) {
                        endDateString = globalVar.FormatDateyyyy_MM_dd_fromDateInteger(intYear, intMonth, intDay);
                        mtxtvSearchEndDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(endDateString));
                        currentYearEndAnInt = intYear;
                        currentMonthEndAnInt = intMonth;
                        currentDayEndAnInt = intDay;

                        calendarEndDate.set(Calendar.DAY_OF_MONTH, currentDayEndAnInt);
                        calendarEndDate.set(Calendar.MONTH, currentMonthEndAnInt);
                        calendarEndDate.set(Calendar.YEAR, currentYearEndAnInt);
                    }
                }, currentYearEndAnInt, currentMonthEndAnInt, currentDayEndAnInt);
                datePickerDialog.show();
            }
        });
    }//setupStartDate


    private void searchController() {
        Button button = getView().findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mtxtvSearchStartDate.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือกวันที่นัด เริ่มต้น ด้วย !!!", Toast.LENGTH_SHORT).show();
                } else if (mtxtvSearchEndDate.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือกวันที่นัด สิ้นสุด ด้วย !!!", Toast.LENGTH_SHORT).show();
                }
//                ใช้แบบนี้ก็ได้
//                else if (calendarStartDate.get(calendarStartDate.DAY_OF_YEAR) > calendarEndDate.get(calendarEndDate.DAY_OF_YEAR)) {
//                    Toast.makeText(getActivity(), "ห้ามเลือก วันที่นัดเริ่มต้น มากกว่า วันที่นัดสิ้นสุด ด้วย !!!", Toast.LENGTH_SHORT).show();
//                }
                else if (calendarStartDate.after(calendarEndDate)) {
                    Toast.makeText(getActivity(), "ห้ามเลือกวันที่นัด เริ่มต้น มากกว่า สิ้นสุด !!!", Toast.LENGTH_SHORT).show();
                } else {
                    searchCustName = mtxtSearchCustName.getText().toString();
                    //Create ListView
                    createListView();
                }
            }
        });
    }//Search Controller


    private void createListView() {
        ListView listView = getView().findViewById(R.id.livAppointment);
        String tag = "6SepV2";

        try {
            GetAppointmentGridWhere getAppointmentGridWhere = new GetAppointmentGridWhere(getActivity());
            getAppointmentGridWhere.execute(
                    objectSaleLogin.DPcode,
                    objectSaleLogin.SACode,
                    startDateString,
                    endDateString,
                    searchCustName,
                    myConstant.getUrlGetAppointmentGrid());

            String resultJSON = getAppointmentGridWhere.get();
            resultJSON = globalVar.JsonXmlToJsonString(resultJSON);
            Log.d(tag, "JSON ==> " + resultJSON);

            JSONArray jsonArray = new JSONArray(resultJSON);
            //Array Data Appointment To ListView
            final String[] AppdateStrings = new String[jsonArray.length()];
            final String[] AppStartTimeStrings = new String[jsonArray.length()];
            String[] CSnameStrings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AppdateStrings[i] = globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(jsonObject.getString("AppDate"));
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


    private void setupButtonAdd() {
        mFabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAddEditPreviewAppointment(globalVar.getADD, "", "");
            }
        });
    }//setupButtonAdd


    private void confirmDialog(final String appdateString, final String appStartTimeString) {
        final String tag = "7SepV2";
        CharSequence[] charSequences = new CharSequence[]{globalVar.getEDIT, globalVar.getDELETE, globalVar.getPREVIEW};
        final int[] selectedChoice = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); //ถ้าไม่มีการเลือก false คือไม่ต้องทำอะไร
        builder.setIcon(android.R.drawable.ic_dialog_info); //Icon Dialog
        builder.setTitle("Do you want to Edit or Delete ?");
        //Default ตัวแปรก Edit ต้อง set เป็น 0
        builder.setSingleChoiceItems(charSequences, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedChoice[0] = i;
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

                Log.d(tag, "Choose ==> " + selectedChoice[0]);//1 ==> Delete, 0 = Edit

                switch (selectedChoice[0]) {
                    case 0: //Edit
                        myAddEditPreviewAppointment(globalVar.getEDIT, appdateString, appStartTimeString);
                        break;
                    case 1:
                        myDeleteAppointment(appdateString, appStartTimeString);
                        break;
                    case 2: //Preview
                        myAddEditPreviewAppointment(globalVar.getPREVIEW, appdateString, appStartTimeString);
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }//ConfirmDialog


    private void myAddEditPreviewAppointment(String currentModify, String strAppDate, String strAppStartTime) {

        if (!currentModify.equals(globalVar.getADD)) {
            if (!strAppDate.trim().equals("")) {
                //Don't Edit AppDate less than CurrentDate
                //Current Date
                Calendar calendarCurrentDate, _calendarAppDate;
                calendarCurrentDate = Calendar.getInstance();
                //AppDate
                _calendarAppDate = Calendar.getInstance();
                int DayAppDateAnInt, MonthAppDateAnInt, YearAppDateAnInt;
                String _strAppDate = globalVar.FormatStringDate_ddMMyyyy_To_yyyyMMdd(strAppDate);
                DayAppDateAnInt = globalVar.getDay_fromStringYYYYMMDD(_strAppDate);
                MonthAppDateAnInt = globalVar.getMonth_fromStringYYYYMMDD(_strAppDate);
                YearAppDateAnInt = globalVar.getYear_fromStringYYYYMMDD(_strAppDate);
                MonthAppDateAnInt -= 1;
                _calendarAppDate.set(Calendar.YEAR, YearAppDateAnInt);
                _calendarAppDate.set(Calendar.MONTH, MonthAppDateAnInt);
                _calendarAppDate.set(Calendar.DAY_OF_MONTH, DayAppDateAnInt);

                if ((calendarCurrentDate.after(_calendarAppDate)) && (currentModify.equals(globalVar.getEDIT))) {
                    Toast.makeText(getActivity(), "คุณไม่สามารถแก้ไขข้อมูลการนัดหมายย้อนหลังได้ !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(getActivity(), "กรุณาเลือก นัดหมาย ที่ต้องการด้วย !!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //addToBackStack ค้างหน้าเดิมไว้ด้วย ถ้ามีการ back กลับมาหน้าเดิมได้
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.serviceContentFragment,
                        EditAppointmentFragment.editAppointmentInstance(
                                employeesLogin, objectSaleLogin,
                                currentModify, strAppDate, strAppStartTime))
                .addToBackStack(null)//addToBackStack ค้างหน้าเดิมไว้ด้วย ถ้ามีการ back กลับมาหน้าเดิมได้
                .commit();

//        //call the main activity set tile method
//        ((SuccessActivity) getActivity()).setTitle(currentModify + " Appointment");
//        ((SuccessActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }//myEditAppointment


    private void myDeleteAppointment(String strAppDate, String strAppStartTime) {

        String tag = "7SepV2";
        String myAppDate = globalVar.FormatStringDate_ddMMyyyy_To_yyyyMMdd(strAppDate);

        Log.d(tag, "DPcode == >" + DPcodeString);
        Log.d(tag, "SAcode == >" + SAcodeString);
        Log.d(tag, "AppDate == >" + myAppDate);
        Log.d(tag, "AppStartTime == >" + strAppStartTime);

        try {
            PostFourString post4String = new PostFourString(getActivity());
            post4String.execute(
                    "DPcode", DPcodeString,
                    "SAcode", SAcodeString,
                    "AppDate", myAppDate,
                    "AppStartTime", strAppStartTime, myConstant.getUrlDeleteAppointment());
            String strJSON = post4String.get();
            strJSON = globalVar.JsonXmlToJsonStringNotSquareBracket(strJSON);
            Gson gson = new Gson();
            ResultExecuteSQL resultExecuteSQL = gson.fromJson(strJSON.toString(), ResultExecuteSQL.class);

            String strResultID = resultExecuteSQL.ResultID;
            if (strResultID.equals(globalVar.getSuccess)) {
                //Create ListView
                createListView();
                Toast.makeText(getActivity(), "Delete Success", Toast.LENGTH_SHORT).show();
            } else if (strResultID.equals(globalVar.getUnSuccess)) {
                Toast.makeText(getActivity(), resultExecuteSQL.ResultMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "ไม่สามารถ Delete ได้ เนื่องจาก " + resultExecuteSQL.ResultMessage, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }//myDeleteAppointment


}//Main Class
