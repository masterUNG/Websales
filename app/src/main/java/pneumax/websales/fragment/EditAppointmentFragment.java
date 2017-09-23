package pneumax.websales.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import pneumax.websales.R;
import pneumax.websales.SuccessActivity;
import pneumax.websales.connected.GetValueWhereNotColumn;
import pneumax.websales.connected.GetValueWhereOneColumn;
import pneumax.websales.connected.GetValueWhereTwoColumn;
import pneumax.websales.connected.InsertAppointment;
import pneumax.websales.connected.PostFourString;
import pneumax.websales.connected.UpdateAppointment;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.Appointment;
import pneumax.websales.object.Customer;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;
import pneumax.websales.object.ResultExecuteSQL;

/**
 * Created by Sitrach on 07/09/2017.
 */

public class EditAppointmentFragment extends Fragment {

    private Employees employeesLogin;
    private ObjectSale objectSaleLogin;
    private GlobalVar globalVar;
    private MyConstant myConstant;
    private String strappDate, strappTime, DPcodeString, SAcodeString;
    private String getCurrentModify;
    private String appDateString;
    private int DayAppDateAnInt, MonthAppDateAnInt, YearAppDateAnInt;
    private Calendar calendarAppDate;
    private Appointment objappointment;
    private Customer objCustomer;
    //Array for Spinner appTime
    private String[] AppTimeStrings;
    private String AppTimeChooseString; //for Choose Value from Spinner Appointment Time
    //Array for Spinner ContactPersons
    private String[] CTPcodeStrings, CTPnameStrings;
    private String CTPcodeChooseString; //for Choose Value from Spinner ContactPersons
    //Array for Spinner WorkType
    private String[] WTcodeStrings, WTnameStrings;
    private String WTcodeChooseString; //for Choose Value from Spinner WorkType
    //Array for Spinner Purpose
    private String[] PURPcodeStrings, PURPNameStrings;
    private String PURPcodeChooseString; //for Choose Value from Spinner Purpose

    private String SearchCScodeString;
    //Object Control
    private EditText vtxtSearchCustName, vtxtRemark, vtxtAppReasonReturn;
    private Button btnsearchcust, vbtnSave;
    private TextView vtxtViewCScode, vtxtViewCSIcode, vtxtViewAreaName, vtxtViewCSBcode, vtxtViewAppDate;
    private Spinner vspnCTPname, vspnAppTime, vspnWTname, vspnPurposeName;
    private CheckBox vchkOffice, vchkAbsent;


    public static EditAppointmentFragment editAppointmentInstance(Parcelable parcelEmplyeesLogin,
                                                                  Parcelable parcelObjectSaleLogin,
                                                                  String strCurrentModify,
                                                                  String strAppDate, String strAppTime) {
        EditAppointmentFragment editAppointmentFragment = new EditAppointmentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Employees.TABLE_NAME, parcelEmplyeesLogin);
        bundle.putParcelable(ObjectSale.TABLE_NAME, parcelObjectSaleLogin);
        bundle.putString(GlobalVar.getInstance().getCurrentModifyTitle, strCurrentModify);
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
        myConstant = new MyConstant();
        employeesLogin = (Employees) getArguments().getParcelable(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);
        getCurrentModify = getArguments().getString(GlobalVar.getInstance().getCurrentModifyTitle);
        strappDate = getArguments().getString("AppDate");
        if (!strappDate.equals("")) {
            strappDate = globalVar.FormatStringDate_ddMMyyyy_To_yyyyMMdd(strappDate);
        }
        strappTime = getArguments().getString("AppTime");
        DPcodeString = objectSaleLogin.DPcode;
        SAcodeString = objectSaleLogin.SACode;

        this.appDateString = "";
        if (savedInstanceState != null) {
            this.appDateString = savedInstanceState.getString(globalVar.getAppDateString);
            DayAppDateAnInt = globalVar.getDay_fromStringYYYYMMDD(appDateString);
            MonthAppDateAnInt = globalVar.getMonth_fromStringYYYYMMDD(appDateString);
            YearAppDateAnInt = globalVar.getYear_fromStringYYYYMMDD(appDateString);
            MonthAppDateAnInt -= 1;
            calendarAppDate = Calendar.getInstance();
            calendarAppDate.set(Calendar.YEAR, YearAppDateAnInt);
            calendarAppDate.set(Calendar.MONTH, MonthAppDateAnInt);
            calendarAppDate.set(Calendar.DAY_OF_MONTH, DayAppDateAnInt);
        }

        Log.d(tag, "DPcode on Fragment ==> " + DPcodeString);
        Log.d(tag, "SAcode on Fragment ==> " + SAcodeString);
        Log.d(tag, "AppDate on Fragment ==> " + strappDate);
        Log.d(tag, "AppTime on Fragment ==> " + strappTime);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save value Current
        outState.putString(globalVar.getAppDateString, this.appDateString);
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

        //Display Add Edit Preview
        TextView mtxtviewCurrentModify = getView().findViewById(R.id.txtviewCurrentModify);
        mtxtviewCurrentModify.setText(getArguments().getString(GlobalVar.getInstance().getCurrentModifyTitle));

        //Set new Object
        vtxtSearchCustName = getView().findViewById(R.id.txtSearchCSNameEdit);
        btnsearchcust = getView().findViewById(R.id.btnSearchCust);
        vtxtViewCScode = getView().findViewById(R.id.txtViewCScode);
        vtxtViewCSIcode = getView().findViewById(R.id.txtViewCSIcode);
        vtxtViewAreaName = getView().findViewById(R.id.txtViewAreaName);
        vtxtViewCSBcode = getView().findViewById(R.id.txtViewCSBcode);
        vspnCTPname = getView().findViewById(R.id.spnCTPname);
        vtxtViewAppDate = getView().findViewById(R.id.txtViewAppDate);
        vspnAppTime = getView().findViewById(R.id.spnAppTime);
        vchkOffice = getView().findViewById(R.id.chkOffice);
        vchkAbsent = getView().findViewById(R.id.chkAbsent);
        vspnWTname = getView().findViewById(R.id.spnWTname);
        vspnPurposeName = getView().findViewById(R.id.spnPurposeName);
        vtxtRemark = getView().findViewById(R.id.txtRemark);
        vtxtAppReasonReturn = getView().findViewById(R.id.txtAppReasonReturn);

        String _getCScodeString = ((SuccessActivity) getActivity()).getReturnValueFragment();
        if (getCurrentModify.equals(globalVar.getADD)) {
            // Check for Return Value from Fragment in main activity
            if ((_getCScodeString != null) || ((objCustomer != null) && (!vtxtViewCScode.getText().toString().equals("")))) {
                if (_getCScodeString != null) {
                    SearchCScodeString = _getCScodeString;
                } else {
                    SearchCScodeString = objCustomer.CScode.trim();
                }
                btnsearchcust.setFocusable(true);
                getDataCustomer();
                vtxtSearchCustName.setText(objCustomer.CSname.trim());
                vtxtViewCScode.setText(objCustomer.CScode);
                vtxtViewCSIcode.setText(objCustomer.CSIdes);
                vtxtViewAreaName.setText(objCustomer.AREANAME);
                vtxtViewCSBcode.setText(objCustomer.CSBdes);

            } else {
                vtxtSearchCustName.setText("");
                vtxtViewCScode.setText("");
                vtxtViewCSIcode.setText("");
                vtxtViewAreaName.setText("");
                vtxtViewCSBcode.setText("");
                appDateString = "";
            }
            AppTimeChooseString = "";
            CTPcodeChooseString = "";
            WTcodeChooseString = "";
            PURPcodeChooseString = "";

            //setValueAppDate
            setValueAppDate();
            vtxtViewAppDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(appDateString));
            vchkOffice.setChecked(false);
            vchkAbsent.setChecked(false);
            vtxtRemark.setText("");
            vtxtAppReasonReturn.setText("");
        } else {
            //Get Data Appointment
            getDataAppointment();
            if (objappointment != null) {
                if (((_getCScodeString == null) || (_getCScodeString.equals(""))) && ((vtxtSearchCustName == null) || (vtxtSearchCustName.getText().toString().equals("")))) {
                    vtxtSearchCustName.setText(objappointment.CSthiname);
                    vtxtViewCScode.setText(objappointment.CSCode);
                    SearchCScodeString = objappointment.CSCode;
                    getDataCustomer();
                    vtxtViewCSIcode.setText(objappointment.CSIcode);
                    vtxtViewAreaName.setText(objappointment.AreaName);
                    vtxtViewCSBcode.setText(objappointment.CSBdes);
                    AppTimeChooseString = objappointment.AppStartTime.trim();
                    CTPcodeChooseString = objappointment.CTPcode.trim();
                    WTcodeChooseString = objappointment.WTcode.trim();
                    PURPcodeChooseString = objappointment.PURPcode.trim();

                    appDateString = objappointment.AppDate;
                    //setValueAppDate
                    setValueAppDate();
                    vtxtViewAppDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(appDateString));
                    vspnAppTime = getView().findViewById(R.id.spnAppTime);
                    vchkOffice.setChecked((objappointment.AppVisit_ByPhone.equals("O")));
                    vchkAbsent.setChecked((objappointment.AppVisit_ByPhone.equals("A")));
                    vtxtRemark.setText(objappointment.Remark);
                    vtxtAppReasonReturn.setText(objappointment.AppReasonReturn);
                } else {
                    SearchCScodeString = _getCScodeString;
                    getDataCustomer();
                    vtxtSearchCustName.setText(objCustomer.CSname);
                    vtxtViewCScode.setText(objCustomer.CScode);
                    vtxtViewCSIcode.setText(objCustomer.CSIdes);
                    vtxtViewAreaName.setText(objCustomer.AREANAME);
                    vtxtViewCSBcode.setText(objCustomer.CSBdes);

                    //setValueAppDate
                    setValueAppDate();
                    vtxtViewAppDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(appDateString));
                }
            }
        }

        createSpinnerTime();
        createSpinnerContactPersons();
        createSpinnerWorkType();
        createSpinnerPurpose();

        //Set Enable Object
        setEnableObject();

        //Button Search Customer
        ButtonSearchCustomer();

        //clickAppDate
        clickAppDate();

        //back fragment
        backFragment();

        //SaveAppointment
        saveAppointment();
        //setCheckBox
        setCheckBox();

    }//onActivityCreated

    @Override
    public void onResume() {
        super.onResume();

        try {
            // Check for Return Value from Fragment in main activity
            String _getCScode = ((SuccessActivity) getActivity()).getReturnValueFragment();

            // If any, display as snackbar
            if (_getCScode != null) {
                vtxtSearchCustName.setText(objCustomer.CSname.trim());
                // Reset Return Value from Fragment in SuccessActivity
                ((SuccessActivity) getActivity()).resetReturnValueFragment();
            }

            Log.d("7SepV4", "onResume Work");
        } catch (Exception e) {
            Log.d("7SepV4", "e onResume ==> " + e.toString());
        }
    }


    private boolean checkEnabled() {
        Boolean aBoolean = true;
        if (getCurrentModify.equals(globalVar.getPREVIEW)) {
            aBoolean = false;
        }

        return aBoolean;
    }//checkEnabled

    private void setValueAppDate() {
        if (appDateString.equals("")) {
            calendarAppDate = Calendar.getInstance();
            DayAppDateAnInt = calendarAppDate.get(Calendar.DAY_OF_MONTH);
            MonthAppDateAnInt = calendarAppDate.get(Calendar.MONTH); //0 ==> Jan, 1 ==> Feb
            YearAppDateAnInt = calendarAppDate.get(Calendar.YEAR);
            //Parameters Start Date
            appDateString = globalVar.FormatDateyyyy_MM_dd_fromDateInteger(YearAppDateAnInt, MonthAppDateAnInt, DayAppDateAnInt);
        } else {
            DayAppDateAnInt = globalVar.getDay_fromStringYYYYMMDD(appDateString);
            MonthAppDateAnInt = globalVar.getMonth_fromStringYYYYMMDD(appDateString);
            YearAppDateAnInt = globalVar.getYear_fromStringYYYYMMDD(appDateString);
            MonthAppDateAnInt -= 1;
            calendarAppDate = Calendar.getInstance();
            calendarAppDate.set(Calendar.YEAR, YearAppDateAnInt);
            calendarAppDate.set(Calendar.MONTH, MonthAppDateAnInt);
            calendarAppDate.set(Calendar.DAY_OF_MONTH, DayAppDateAnInt);
        }
    }//setValueAppDate

    private void createSpinnerTime() {
        String tag = "7SepV3";

        try {
            String strArray = globalVar.getArrayAppointmentTime();
            JSONArray jsonArray = new JSONArray(strArray);

            //จองหน่วยความจำ
            AppTimeStrings = new String[jsonArray.length()];
            int spinnerDefaultPosition = 0;
            for (int i = 0; i < jsonArray.length(); i += 1) {
                AppTimeStrings[i] = jsonArray.getString(i);
//                if ((!getCurrentModify.equals(globalVar.getADD)) && (AppTimeChooseString.equals(AppTimeStrings[i]))) {
                if ((AppTimeChooseString.equals(AppTimeStrings[i]))) {
                    spinnerDefaultPosition = i;
                }
                Log.d("LoopSpinnerTime", "Time[" + i + "] ==> " + AppTimeStrings[i]);
            }//for

            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, AppTimeStrings);
            vspnAppTime.setAdapter(nameArrayAdapter);
            //set Default value WorkType if Appointment Edit Or Preview
            if (spinnerDefaultPosition > 0) {
                vspnAppTime.setSelection(spinnerDefaultPosition);
                AppTimeChooseString = AppTimeStrings[spinnerDefaultPosition];
            } else {
                AppTimeChooseString = AppTimeStrings[0];
            }

            vspnAppTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AppTimeChooseString = AppTimeStrings[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    AppTimeChooseString = AppTimeStrings[0];
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinnerTime ==> " + e.toString());
        }
    }//createSpinnerTime

    private void createSpinnerContactPersons() {
        String tag = "7SepV3";

        try {
            GetValueWhereOneColumn getValueWhereOneColumn = new GetValueWhereOneColumn(getActivity());
            getValueWhereOneColumn.execute("CScode", vtxtViewCScode.getText().toString(),
                    myConstant.getUrlGetContactPersonCombobox());
            String strJSON = getValueWhereOneColumn.get();
            Log.d(tag, "JSON ==> " + strJSON);

            String fullJSON = globalVar.JsonXmlToJsonString(strJSON);
            JSONArray jsonArray = new JSONArray(fullJSON);
            //จองหน่วยความจำ
            CTPcodeStrings = new String[jsonArray.length()];
            CTPnameStrings = new String[jsonArray.length()];

            int spinnerDefaultPosition = 0;
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CTPcodeStrings[i] = jsonObject.getString("CTPcode").trim();
                CTPnameStrings[i] = jsonObject.getString("CTPname").trim();
//                if ((!getCurrentModify.equals(globalVar.getADD)) && (CTPcodeChooseString.equals(CTPcodeStrings[i]))) {
                if ((CTPcodeChooseString.equals(CTPcodeStrings[i]))) {
                    spinnerDefaultPosition = i;
                }
                Log.d("LoopSpinnerContact", "CTPname[" + i + "] ==> " + CTPnameStrings[i]);
            }//for

            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, CTPnameStrings);
            vspnCTPname.setAdapter(nameArrayAdapter);
            //set Default value ContactPerson if Appointment Edit Or Preview
            if (spinnerDefaultPosition > 0) {
                vspnCTPname.setSelection(spinnerDefaultPosition);
                CTPcodeChooseString = CTPcodeStrings[spinnerDefaultPosition];
            } else {
                CTPcodeChooseString = CTPcodeStrings[0];
            }

            vspnCTPname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    CTPcodeChooseString = CTPcodeStrings[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    CTPcodeChooseString = CTPcodeStrings[0];
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinnerContactPersons ==> " + e.toString());
        }
    }//createSpinnerContactPersons

    private void createSpinnerWorkType() {
        String tag = "7SepV3";

        try {
            GetValueWhereOneColumn getValueWhereOneColumn = new GetValueWhereOneColumn(getActivity());
            String strWTVisitType = "";
            if (vchkOffice.isChecked()) {
                strWTVisitType = "O";
            }
            if (vchkAbsent.isChecked()) {
                strWTVisitType = "A";
            }
            getValueWhereOneColumn.execute("WTVisit_Type", strWTVisitType,
                    myConstant.getUrlGetAppointmentWorkType());
            String strJSON = getValueWhereOneColumn.get();
            Log.d(tag, "JSON ==> " + strJSON);

            String fullJSON = globalVar.JsonXmlToJsonString(strJSON);
            JSONArray jsonArray = new JSONArray(fullJSON);
            //จองหน่วยความจำ
            WTcodeStrings = new String[jsonArray.length()];
            WTnameStrings = new String[jsonArray.length()];
            int spinnerDefaultPosition = 0;
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                WTcodeStrings[i] = jsonObject.getString("WTcode");
                WTnameStrings[i] = jsonObject.getString("WTname");
//                if ((!getCurrentModify.equals(globalVar.getADD)) && (WTcodeChooseString.equals(WTcodeStrings[i]))) {
                if ((WTcodeChooseString.equals(WTcodeStrings[i]))) {
                    spinnerDefaultPosition = i;
                }
                Log.d("LoopSpinnerWorkType", "WTname[" + i + "] ==> " + WTnameStrings[i]);
            }//for

            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, WTnameStrings);
            vspnWTname.setAdapter(nameArrayAdapter);
            //set Default value WorkType if Appointment Edit Or Preview
            if (spinnerDefaultPosition > 0) {
                vspnWTname.setSelection(spinnerDefaultPosition);
                WTcodeChooseString = WTcodeStrings[spinnerDefaultPosition];
            }else {
                WTcodeChooseString = WTcodeStrings[0];
            }

            vspnWTname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    WTcodeChooseString = WTcodeStrings[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    WTcodeChooseString = WTcodeStrings[0];
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinnerWorkType ==> " + e.toString());
        }
    }//createSpinnerWorkType


    private void createSpinnerPurpose() {
        String tag = "7SepV3";

        try {
            GetValueWhereNotColumn getValueWhereNotColumn = new GetValueWhereNotColumn(getActivity());

            getValueWhereNotColumn.execute(myConstant.getUrlGetAppointmentPurpose());
            String strJSON = getValueWhereNotColumn.get();
            Log.d(tag, "JSON ==> " + strJSON);

            String fullJSON = globalVar.JsonXmlToJsonString(strJSON);
            JSONArray jsonArray = new JSONArray(fullJSON);
            //จองหน่วยความจำ
            PURPcodeStrings = new String[jsonArray.length()];
            PURPNameStrings = new String[jsonArray.length()];
            int spinnerDefaultPosition = 0;
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PURPcodeStrings[i] = jsonObject.getString("PURPcode");
                PURPNameStrings[i] = jsonObject.getString("PURPName");
//                if ((!getCurrentModify.equals(globalVar.getADD)) && (PURPcodeChooseString.equals(PURPcodeStrings[i]))) {
                if ((PURPcodeChooseString.equals(PURPcodeStrings[i]))) {
                    spinnerDefaultPosition = i;
                }
                Log.d("LoopSpinnerPurpose", "PURPName[" + i + "] ==> " + PURPNameStrings[i]);
            }//for

            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, PURPNameStrings);
            vspnPurposeName.setAdapter(nameArrayAdapter);
            //set Default value WorkType if Appointment Edit Or Preview
            if (spinnerDefaultPosition > 0) {
                vspnPurposeName.setSelection(spinnerDefaultPosition);
                PURPcodeChooseString = PURPcodeStrings[spinnerDefaultPosition];
            } else {
                PURPcodeChooseString = PURPcodeStrings[0];
            }

            vspnPurposeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    PURPcodeChooseString = PURPcodeStrings[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    PURPcodeChooseString = PURPcodeStrings[0];
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinnerPurpose ==> " + e.toString());
        }
    }//createSpinnerPurpose


    private void setEnableObject() {
        Boolean blnEnableObj = false;
        Boolean blnEnableObjSearchCustName = false;
        blnEnableObj = (checkEnabled());
        blnEnableObjSearchCustName = (blnEnableObj && ((SearchCScodeString == null) || !SearchCScodeString.trim().equals("285498")));
        vtxtSearchCustName.setEnabled(blnEnableObjSearchCustName);
        if (blnEnableObjSearchCustName) {
            btnsearchcust.setVisibility(View.VISIBLE);
        } else {
            btnsearchcust.setVisibility(View.GONE);
        }
        vspnCTPname.setEnabled(blnEnableObj);
        vtxtViewAppDate.setEnabled(blnEnableObj);
        vspnAppTime.setEnabled(blnEnableObj);
        vchkOffice.setEnabled(blnEnableObj);
        vchkAbsent.setEnabled(blnEnableObj);
        vspnWTname.setEnabled(blnEnableObj);
        vspnPurposeName.setEnabled(blnEnableObj);
        vtxtRemark.setEnabled(blnEnableObj);
        vtxtAppReasonReturn.setEnabled(blnEnableObj);
    }//setEnableObject


    private void ButtonSearchCustomer() {
        btnsearchcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vtxtSearchCustName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "กรุณาป้อนชื่อลูกค้า บางส่วนด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (vtxtSearchCustName.getText().toString().length() < 3) {
                    Toast.makeText(getActivity(), "กรุณาป้อนชื่อลูกค้า อย่างน้อย 3 ตัวอักษร !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //addToBackStack ค้างหน้าเดิมไว้ด้วย ถ้ามีการ back กลับมาหน้าเดิมได้
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.serviceContentFragment,
                                CustomerFragment.customerFragment(
                                        objectSaleLogin, vtxtSearchCustName.getText().toString()))
                        .addToBackStack(null)//addToBackStack ค้างหน้าเดิมไว้ด้วย ถ้ามีการ back กลับมาหน้าเดิมได้
                        .commit();
            }
        });
    }//ButtonSearchCustomer


    private void clickAppDate() {
        vtxtViewAppDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int intYear, int intMonth, int intDay) {
                        appDateString = globalVar.FormatDateyyyy_MM_dd_fromDateInteger(intYear, intMonth, intDay);
                        vtxtViewAppDate.setText(globalVar.FormatDateddMMyyyy_fromStringYYYY_MM_DD(appDateString));
                        YearAppDateAnInt = intYear;
                        MonthAppDateAnInt = intMonth;
                        DayAppDateAnInt = intDay;

                        calendarAppDate.set(Calendar.YEAR, YearAppDateAnInt);
                        calendarAppDate.set(Calendar.MONTH, MonthAppDateAnInt);
                        calendarAppDate.set(Calendar.DAY_OF_MONTH, DayAppDateAnInt);
                    }
                }, YearAppDateAnInt, MonthAppDateAnInt, DayAppDateAnInt);
                datePickerDialog.show();
            }
        });
    }//clickAppDate


    private void setCheckBox() {
        //CheckOffice
        vchkOffice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    vchkAbsent.setChecked(false);
                    setValueFromCheckOfficeAbsent(true);
                } else {
                    setValueFromCheckOfficeAbsent(false);
                }
            }
        });

        //Check Absent
        vchkAbsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    vchkOffice.setChecked(false);
                    setValueFromCheckOfficeAbsent(true);
                } else {
                    setValueFromCheckOfficeAbsent(false);
                }
            }
        });
    }//setCheckBox


    private void setValueFromCheckOfficeAbsent(Boolean blnChecked) {
        if (blnChecked) {
            SearchCScodeString = "285498";
            getDataCustomer();
            vtxtSearchCustName.setText(objCustomer.CSname);
            vtxtViewCScode.setText(objCustomer.CScode);
            vtxtViewCSIcode.setText(objCustomer.CSIdes);
            vtxtViewAreaName.setText(objCustomer.AREANAME);
            vtxtViewCSBcode.setText(objCustomer.CSBdes);
            AppTimeChooseString = "08:00";
            WTcodeChooseString = "";
            PURPcodeChooseString = "0002";
        } else {
            SearchCScodeString = "";
            getDataCustomer();
            vtxtSearchCustName.setText("");
            vtxtViewCScode.setText("");
            vtxtViewCSIcode.setText("");
            vtxtViewAreaName.setText("");
            vtxtViewCSBcode.setText("");
            AppTimeChooseString = "";
            WTcodeChooseString = "";
            PURPcodeChooseString = "";
        }
        createSpinnerTime();
        createSpinnerContactPersons();
        createSpinnerWorkType();
        createSpinnerPurpose();
        //Set Enable Object
        setEnableObject();
    }//setValueFromCheckOfficeAbsent


    private void backFragment() {
        TextView mtxtviewBack = getView().findViewById(R.id.txtviewBack);
        mtxtviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }//backFragment


    private void saveAppointment() {
        vbtnSave = getView().findViewById(R.id.btnSave);
        if (getCurrentModify.equals(globalVar.getPREVIEW)) {
            vbtnSave.setVisibility(View.GONE);
        } else {
            vbtnSave.setVisibility(View.VISIBLE);
        }

        vbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vtxtSearchCustName.getText().equals("")) {
                    Toast.makeText(getActivity(), "กรุณาป้อน ลูกค้า ด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CTPcodeChooseString.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือก ผู้ติดต่อ ด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (appDateString.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือก วันที่นัด ด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (AppTimeChooseString.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือก เวลานัด ด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (WTcodeChooseString.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือก ประเภทงาน ด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PURPcodeChooseString.equals("")) {
                    Toast.makeText(getActivity(), "กรุณาเลือก จุดประสงค์ ด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Back
                String resultJSON = getInsertUpdateAppointment();
                String fullJSON = globalVar.JsonXmlToJsonStringNotSquareBracket(resultJSON);

                Gson gson = new Gson();
                ResultExecuteSQL resultExecuteSQL = gson.fromJson(fullJSON.toString(), ResultExecuteSQL.class);

                if (resultExecuteSQL.ResultID.toUpperCase().equals(globalVar.getSuccess.toUpperCase())) {
                    // Set Return Value in success activity
                    ((SuccessActivity) getActivity()).setReturnValueFragment(globalVar.getSuccess);
                    getActivity().getSupportFragmentManager().popBackStack();
                } else if (resultExecuteSQL.ResultID.toUpperCase().equals(globalVar.getInsertDuplicate.toUpperCase())) {
                    Toast.makeText(getActivity(), resultExecuteSQL.ResultMessage, Toast.LENGTH_SHORT).show();
                    return;
                }
            }//onClick
        });
    }//saveAppointment


    private String getInsertUpdateAppointment() {
        String resultString = "";
        String tag = "7SepV3";
        try {
            String strAppVisit_ByPhone = "V";
            if (vchkOffice.isChecked()) {
                strAppVisit_ByPhone = "O";
            }
            if (vchkAbsent.isChecked()) {
                strAppVisit_ByPhone = "A";
            }

            if (getCurrentModify.equals(globalVar.getADD)) {
                InsertAppointment insertAppointment = new InsertAppointment(getActivity());
                insertAppointment.execute(
                        objectSaleLogin.DPcode, objectSaleLogin.SACode,
                        SearchCScodeString, appDateString,
                        AppTimeChooseString, CTPcodeChooseString,
                        WTcodeChooseString, PURPcodeChooseString,
                        "N", vtxtRemark.getText().toString(), vtxtAppReasonReturn.getText().toString(),
                        "", strAppVisit_ByPhone, "Y", employeesLogin.STFcode,
                        myConstant.getUrlInsertAppointment());

                resultString = insertAppointment.get();
            } else {
                UpdateAppointment updateAppointment = new UpdateAppointment(getActivity());
                updateAppointment.execute(
                        objectSaleLogin.DPcode, objectSaleLogin.SACode,
                        objappointment.CSCode, objappointment.AppDate,
                        objappointment.AppStartTime, SearchCScodeString, appDateString,
                        AppTimeChooseString, CTPcodeChooseString,
                        WTcodeChooseString, PURPcodeChooseString,
                        vtxtRemark.getText().toString(), vtxtAppReasonReturn.getText().toString(),
                        "", strAppVisit_ByPhone, employeesLogin.STFcode,
                        myConstant.getUrlUpdateAppointment());

                resultString = updateAppointment.get();
            }
            Log.d("7SepV3", "JSON ==> " + resultString);
            return resultString;
        } catch (Exception e) {
            Log.d(tag, "e ShowView ==> " + e.toString());
            return "";
        }
    }//getInsertUpdateAppointment

    private void getDataAppointment() {
        String tag = "7SepV3";
        try {
            PostFourString postFourString = new PostFourString(getActivity());
            postFourString.execute(
                    "DPcode", DPcodeString,
                    "SAcode", SAcodeString,
                    "AppDate", strappDate,
                    "AppTime", strappTime, myConstant.getUrlGetAppointment());
            String strJSON = postFourString.get();
            Log.d(tag, "strJSON ==> " + strJSON);
            strJSON = globalVar.JsonXmlToJsonStringNotSquareBracket(strJSON);
            Gson gson = new Gson();
            objappointment = gson.fromJson(strJSON.toString(), Appointment.class);

        } catch (Exception e) {
            Log.d(tag, "e ShowView ==> " + e.toString());
        }
    }//getDataAppointment

    private void getDataCustomer() {
        String tag = "7SepV3";
        try {
            GetValueWhereTwoColumn getValueWhereTwoColumn = new GetValueWhereTwoColumn(getActivity());
            getValueWhereTwoColumn.execute(
                    "CScode", SearchCScodeString,
                    "SAcode", SAcodeString,
                    myConstant.getUrlGetCustomer());
            String strJSON = getValueWhereTwoColumn.get();
            Log.d(tag, "strJSON ==> " + strJSON);
            strJSON = globalVar.JsonXmlToJsonStringNotSquareBracket(strJSON);
            Gson gson = new Gson();
            objCustomer = gson.fromJson(strJSON.toString(), Customer.class);

        } catch (Exception e) {
            Log.d(tag, "e ShowView ==> " + e.toString());
        }
    }//getDataCustomer

}//Main Class
