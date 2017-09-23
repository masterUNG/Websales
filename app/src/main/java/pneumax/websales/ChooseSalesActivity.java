package pneumax.websales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import pneumax.websales.connected.GetSalesCodeWhere;
import pneumax.websales.connected.GetSalesNameWhere;
import pneumax.websales.connected.GetValueWhereOneColumn;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;

public class ChooseSalesActivity extends AppCompatActivity {

    private Employees employeesLogin;
    private ObjectSale objectSaleLogin;

    private MyConstant myConstant;
    private GlobalVar globalVar;
    private String[] userLoginString;
    //Array for Spinner SalesName
    private String[] STFcodeStrings, STFnameStrings;
    private String STFcodeChooseString, STFnameChooseString; //for Choose Value from Spinner SalesName
    //Array for Spinner Department
    private String[] DPcodeStrings, DPnameStrings;
    private String DPcodeChooseString, DPnameChooseString; //for Choose Value from Spinner Department

    private TextView mtxtViewSAcode;
    private TextView mtxtViewSAJobDesc;
    private Button mBtnOK;
    private Button mBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sales);

        //กด Ctrl+Alt+M จะไปสร้าง Method แทน ตั้งชื่อตาม Comment
        //Get Value from Intent
        getValueFromIntent();
        //bindWidgets
        bindWidgets();
        //Create Spinner Sales Name
        createSpinnerSalesName();
        //setEvent
        setEvent();
    }


    private void setEvent() {

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalVar.isEmptyString(STFcodeChooseString)) {
                    Toast.makeText(ChooseSalesActivity.this, "กรุณาเลือก Sale Name ด้วย !!!", Toast.LENGTH_SHORT).show();
                } else if (globalVar.isEmptyString(DPcodeChooseString)) {
                    Toast.makeText(ChooseSalesActivity.this, "กรุณาเลือก Department ด้วย !!!", Toast.LENGTH_SHORT).show();
                }
                else if (globalVar.isEmptyString(mtxtViewSAcode.getText().toString())) {
                    Toast.makeText(ChooseSalesActivity.this, "กรุณาเลือก Sale Code ด้วย !!!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ChooseSalesActivity.this, SuccessActivity.class);
                    intent.putExtra(Employees.TABLE_NAME, employeesLogin);
                    intent.putExtra(ObjectSale.TABLE_NAME, objectSaleLogin);
                    startActivity(intent);
//                    finish();
                }
            }
        });//BtnOK

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });//BtnCancel

    }//setEvents


    private void logout() {
        //Logout
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();
    }//Logout


    private void getValueFromIntent() {
        userLoginString = getIntent().getStringArrayExtra(Employees.TABLE_NAME);
        // get inbound intent
        employeesLogin = (Employees) getIntent().getParcelableExtra(Employees.TABLE_NAME);
    }//getValueFromIntent


    private void bindWidgets() {

        myConstant = new MyConstant();
        globalVar = new GlobalVar();

        mtxtViewSAcode = (TextView) findViewById(R.id.txtViewSAcode);
        mtxtViewSAJobDesc = (TextView) findViewById(R.id.txtViewSAJobDesc);
        mBtnOK = (Button) findViewById(R.id.btnOK);
        mBtnLogout = (Button) findViewById(R.id.btnLogout);

    }//bindWidgets


    private void createSpinnerSalesName() {
        String tag = "4SepV3";

        try {
            GetSalesNameWhere getSalesNameWhere = new GetSalesNameWhere(ChooseSalesActivity.this);
            getSalesNameWhere.execute(
                    employeesLogin.STFcode,
                    employeesLogin.SACode,
                    employeesLogin.DPcode,
                    myConstant.getUrlGetSalesNameWhere());
            String strJSON = getSalesNameWhere.get();
            Log.d(tag, "JSON ==> " + strJSON);

            String fullJSON = globalVar.JsonXmlToJsonString(strJSON);
            JSONArray jsonArray = new JSONArray(fullJSON);
            //จองหน่วยความจำ
            STFcodeStrings = new String[jsonArray.length()];
            STFnameStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                STFcodeStrings[i] = jsonObject.getString("STFcode");
                STFnameStrings[i] = jsonObject.getString("STFname");

                Log.d("4SepV4", "STFname[" + i + "] ==> " + STFnameStrings[i]);
            }//for

            Spinner nameSpinner = (Spinner) findViewById(R.id.spnSalesName);
            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(ChooseSalesActivity.this,
                    android.R.layout.simple_list_item_1, STFnameStrings);
            nameSpinner.setAdapter(nameArrayAdapter);
            nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    STFnameChooseString = STFnameStrings[i];
                    STFcodeChooseString = STFcodeStrings[i];
                    //Call Choose Spinner Department
                    createSpinnerDepartment(STFcodeStrings[i]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //Begin Default Array Index 0
                    STFnameChooseString = STFnameStrings[0];
                    STFcodeChooseString = STFcodeStrings[0];
                    //Call Choose Spinner Department
                    createSpinnerDepartment(STFcodeStrings[0]);
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinnerSalesName ==> " + e.toString());
        }
    }//createSpinnerSalesName


    private void createSpinnerDepartment(String stFcodeString) {
        String tag = "5SepSpinnerDepartment";
        Log.d(tag, "STFcode ที่ให้แสดง ==> " + stFcodeString);

        try {
            GetValueWhereOneColumn getValueWhereOneColumn = new GetValueWhereOneColumn(ChooseSalesActivity.this);
            getValueWhereOneColumn.execute("STFcode", stFcodeString,
                    myConstant.getUrlGetDepartmentWhere());
            String strJSON = getValueWhereOneColumn.get();
            Log.d(tag, "JSON ==> " + strJSON);

            String fullJSON = globalVar.JsonXmlToJsonString(strJSON);
            JSONArray jsonArray = new JSONArray(fullJSON);
            //จองหน่วยความจำ
            DPcodeStrings = new String[jsonArray.length()];
            DPnameStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DPcodeStrings[i] = jsonObject.getString("DPcode");
                DPnameStrings[i] = jsonObject.getString("DPname");

                Log.d("LoopSpinnerDepartment", "STFname[" + i + "] ==> " + DPnameStrings[i]);
            }//for

            Spinner nameSpinner = (Spinner) findViewById(R.id.spnDepartment);
            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(ChooseSalesActivity.this,
                    android.R.layout.simple_list_item_1, DPnameStrings);
            nameSpinner.setAdapter(nameArrayAdapter);
            nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    DPnameChooseString = DPnameStrings[i];
                    DPcodeChooseString = DPcodeStrings[i];

                    SetTextViewSAcode(STFcodeChooseString, DPcodeChooseString);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    DPnameChooseString = DPnameStrings[0];
                    DPcodeChooseString = DPcodeStrings[0];

                    SetTextViewSAcode(STFcodeChooseString, DPcodeChooseString);
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinnerDepartment ==> " + e.toString());
        }
    }//createSpinnerDepartment


    private Void SetTextViewSAcode(String stfcode, String dpcode) {
        String tag = "5SepSetTextViewSAcode";
        Log.d(tag, "STFcode ที่ให้แสดง ==> " + stfcode + " DPcode ==> " + dpcode);
        try {
            GetSalesCodeWhere getSalesCodeWhere = new GetSalesCodeWhere(ChooseSalesActivity.this);
            getSalesCodeWhere.execute(
                    stfcode,
                    dpcode,
                    myConstant.getUrlGetSalesCodeWhere());
            String strJSON = getSalesCodeWhere.get();
            strJSON = globalVar.JsonXmlToJsonStringNotSquareBracket(strJSON);
            Log.d(tag, "JSON ==> " + strJSON);
            Gson gson = new Gson();
            ObjectSale objectSale = gson.fromJson(strJSON.toString(), ObjectSale.class);
            objectSaleLogin = objectSale; //set Value ObjectSales
            mtxtViewSAcode.setText(objectSale.SACode);
            mtxtViewSAJobDesc.setText(objectSale.SAJobDesc);
        } catch (Exception e) {
            Log.d(tag, "e SetTextViewSAcode ==> " + e.toString());
        }
        return null;
    }//SetTextViewSAcode

} //Main Class
