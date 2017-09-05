package pneumax.websales;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import pneumax.websales.connected.GetSalesNameWhere;
import pneumax.websales.connected.GetValueWhereOneColumn;
import pneumax.websales.manager.MyConstant;

public class ChooseSalesActivity extends AppCompatActivity {

    private Employees employeesLogin;
    private String STFcodeString, STFtitleString, DPcodeString, DPnameString, PSTdes_EngString,
            PSTCodeString, SACodeString, STFfnameString, STFlnameString, STFfullnameString,
            BRcode1String, BRdesc_TString, STFstartString;

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
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sales);

        //กด Ctrl+Alt+M จะไปสร้าง Method แทน ตั้งชื่อตาม Comment
        //Get Value from Intent
        getValueFromIntent();

        //Create Spinner Sales Name
        createSpinnerSalesName();
    }

    private void getValueFromIntent() {
        userLoginString = getIntent().getStringArrayExtra(Employees.TABLE_NAME);
        // get inbound intent
        employeesLogin = (Employees) getIntent().getParcelableExtra(Employees.TABLE_NAME);
        myConstant = new MyConstant();
        globalVar = new GlobalVar();

        mtxtViewSAcode = (TextView) findViewById(R.id.txtViewSAcode);
        mtxtViewSAJobDesc = (TextView) findViewById(R.id.txtViewSAJobDesc);
    }

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
        String tag = "4SepSpinnerDepartment";
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
        String tag = "4SepSpinnerDepartment";
        Log.d(tag, "STFcode ที่ให้แสดง ==> " + stfcode + " DPcode ==> " + dpcode);
        try {
            mtxtViewSAcode.setText(stfcode);
            mtxtViewSAJobDesc.setText(dpcode);
        } catch (Exception e) {
            Log.d(tag, "e SetTextViewSAcode ==> " + e.toString());
        }
        return null;
    }//SetTextViewSAcode

} //Main Class
