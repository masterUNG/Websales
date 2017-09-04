package pneumax.websales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import pneumax.websales.connected.GetSalesNameWhere;
import pneumax.websales.manager.MyConstant;

public class ChooseSalesActivity extends AppCompatActivity {

    private Employees mEmployees;
    private String STFcodeString, STFtitleString, DPcodeString, DPnameString, PSTdes_EngString,
            PSTCodeString, SACodeString, STFfnameString, STFlnameString, STFfullnameString,
            BRcode1String, BRdesc_TString, STFstartString;
    private String[] userLoginString;
    private String[] STFcodeStrings, STFnameStrings;
    private String nameChooseString, stfCodeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sales);

        // get inbound intent
        //getInboundIntent();

        //กด Ctrl+Alt+M จะไปสร้าง Method แทน ตั้งชื่อตาม Comment
        //Get Value from Intent
        getValueFromIntent();

        //Create Spinner
        createSpinner();

    }

    private void createSpinner() {
        String tag = "4SepV3";
        MyConstant myConstant = new MyConstant();

        try {
            GetSalesNameWhere getSalesNameWhere = new GetSalesNameWhere(ChooseSalesActivity.this);
            getSalesNameWhere.execute(
                    userLoginString[0],
                    userLoginString[6],
                    userLoginString[2],
                    myConstant.getUrlGetSalesNameWhere());
            String strJSON = getSalesNameWhere.get();
            Log.d(tag, "JSON ==> " + strJSON);

            GlobalVar globalVar = new GlobalVar();
            String fullJSON = globalVar.JsonXmlToJsonString(strJSON);
            fullJSON = "[" + fullJSON + "]";

            JSONArray jsonArray = new JSONArray(fullJSON);
            //จองหน่วยความจำ
            STFcodeStrings = new String[jsonArray.length()];
            STFnameStrings = new String[jsonArray.length()];

            for(int i=0; i<jsonArray.length(); i+=1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                STFcodeStrings[i] = jsonObject.getString("STFcode");
                STFnameStrings[i] = jsonObject.getString("STFname");

                Log.d("4SepV4", "STFname[" + i + "] ==> " + STFnameStrings[i]);
            }//for

            Spinner nameSpinner = (Spinner)findViewById(R.id.spnName);
            ArrayAdapter<String> nameArrayAdapter = new ArrayAdapter<String>(ChooseSalesActivity.this,
                    android.R.layout.simple_list_item_1, STFnameStrings);
            nameSpinner.setAdapter(nameArrayAdapter);
            nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    nameChooseString = STFnameStrings[i];
                    stfCodeString = STFcodeStrings[i];
                    showMyView(STFcodeStrings[i]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    nameChooseString = STFnameStrings[0];
                    stfCodeString = STFcodeStrings[0];
                    showMyView(STFcodeStrings[0]);
                }
            });
        } catch (Exception e) {
            Log.d(tag, "e createSpinner ==> " + e.toString());
        }

    }//Create Spinner

    private void showMyView(String stFcodeString) {
        String tag = "4SepV5";
        Log.d(tag, "STFcode ที่ให้แสดง ==> " + stFcodeString);


    }//showMyView

    private void getValueFromIntent() {
        userLoginString = getIntent().getStringArrayExtra("UserLogin");
    }

    private void getInboundIntent() {
        String tag = "4SepV1";
        Intent inboundIntent = getIntent();
        mEmployees = (Employees) inboundIntent.getParcelableExtra(Employees.TABLE_NAME);

        Log.d(tag, "column1 ==> " + mEmployees.STFcode);
        Log.d(tag, "column2 ==> " + mEmployees.STFtitle);
        Log.d(tag, "column3 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column4 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column5 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column6 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column7 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column8 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column9 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column10 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column11 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column12 ==> " + mEmployees.STFfullname);
        Log.d(tag, "column13 ==> " + mEmployees.STFfullname);


    }
} //Main Class
