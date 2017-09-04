package pneumax.websales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ChooseSalesActivity extends AppCompatActivity {

    private Employees mEmployees;
    private String STFcodeString, STFtitleString, DPcodeString, DPnameString, PSTdes_EngString,
            PSTCodeString, SACodeString, STFfnameString, STFlnameString, STFfullnameString,
            BRcode1String, BRdesc_TString, STFstartString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sales);

        // get inbound intent
        getInboundIntent();

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
