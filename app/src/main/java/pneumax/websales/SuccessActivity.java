package pneumax.websales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pneumax.websales.fragment.AppointResultFragment;
import pneumax.websales.fragment.AppointmentFragment;
import pneumax.websales.fragment.CustInfoByPhoneFragment;
import pneumax.websales.object.Employees;
import pneumax.websales.object.ObjectSale;


public class SuccessActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private AlertDialog.Builder builder;
    private Employees employeesLogin;
    private ObjectSale objectSaleLogin;
    private TextView viewById;

    private String DPcodeString, SAcodeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        //getValueFromIntent
        getValueFromIntent();
        //bindWidgets
        bindWidgets();

        setSupportActionBar(toolbar);

        initNavigationDrawer();


        //Add Fragment to Activity
        addfragmentFirst(savedInstanceState);
    }//onCreate

    private void addfragmentFirst(Bundle savedInstanceState) {
        SAcodeString = objectSaleLogin.SACode;
        DPcodeString = objectSaleLogin.DPcode;
        Log.d("6SepV1", "SAcodeString ==> " + SAcodeString);
        Log.d("6SepV1", "DPcodeString ==> " + DPcodeString);

        //savedInstanceState เหมือนเก็บหน้าเดิมไว้ แต่ปิดโรปแกรมไปแล้ว จะเป็น null
        if (savedInstanceState == null) {
            //Default fragment Appointment
            //Call fragment Appointment
            CallfragmentAppointment();
        }
    }//addfragmentFirst

    private void getValueFromIntent() {
        Intent inboundIntent = getIntent();
        employeesLogin = (Employees) inboundIntent.getParcelableExtra(Employees.TABLE_NAME);
        objectSaleLogin = (ObjectSale) inboundIntent.getParcelableExtra(ObjectSale.TABLE_NAME);
    }//getValueFromIntent

    private void bindWidgets() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


    }

    public void initNavigationDrawer() {
        builder = new AlertDialog.Builder(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.Appointment:
                        //Call fragment Appointment
                        CallfragmentAppointment();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.AppointmentResult:
                        //Call fragment Appointment Result
                        CallfragmentAppointmentResult();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.CustInfoByPhone:
                        //Call fragment CustInfoByphone
                        CallfragmentCustInfoByphone();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        builder
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Log Out")
                                .setMessage("Are you sure you want to log out?")
                                .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Logout();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);

        TextView _txtHeaderSAcode = (TextView) header.findViewById(R.id.txtViewHeaderSAcode);
        _txtHeaderSAcode.setText("Sale Code : " + objectSaleLogin.SACode + System.getProperty("line.separator") + "Name : " + objectSaleLogin.STFfullname);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }//initNavigationDrawer

    //Set Title Toolbar Name
    private void setTitleToolbar(String s) {
        this.setTitle(s);
    }


    private void CallfragmentAppointment() {
        setTitleToolbar("Appointment");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.serviceContentFragment,
                        AppointmentFragment.appointmentInsatance(DPcodeString, SAcodeString))
                .commit();
        //Toast.makeText(getApplicationContext(),"Appointment",Toast.LENGTH_SHORT).show();
    }//Call fragment Appointment


    private void CallfragmentAppointmentResult() {
        setTitleToolbar("Appointment Result");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.serviceContentFragment, new AppointResultFragment())
                .commit();
        //Toast.makeText(getApplicationContext(),"Appointment Result",Toast.LENGTH_SHORT).show();
    }//Call fragment Appointment Result


    private void CallfragmentCustInfoByphone() {
        setTitleToolbar("Cust.Info by phone");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.serviceContentFragment, new CustInfoByPhoneFragment())
                .commit();
    }//Call fragment CustInfoByphone


    private void Logout() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();
    }//Logout Program

}
