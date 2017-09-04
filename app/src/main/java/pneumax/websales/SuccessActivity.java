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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private AlertDialog.Builder builder;
    private Employees mEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // get inbound intent
        Intent inboundIntent = getIntent();
        mEmployees = (Employees) inboundIntent.getParcelableExtra(Employees.TABLE_NAME);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        //Default fragment Appointment
        //Call fragment Appointment
        CallfragmentAppointment();
    }

    public void initNavigationDrawer() {
        builder = new AlertDialog.Builder(this);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id){
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
                                .setPositiveButton("Log Out", new DialogInterface.OnClickListener()
                                {
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
        TextView txtviewSAcode = (TextView)header.findViewById(R.id.SAcode);
        txtviewSAcode.setText("Sale Code : 2035-0" + System.getProperty("line.separator")+"Name : สิทธิ์ราช เรืองสมานไมตรี");
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //Set Title Toolbar Name
    private void setTitleToolbar(String s){
        this.setTitle(s);
    }

    //Call fragment Appointment
    private void CallfragmentAppointment(){
        setTitleToolbar("Appointment");
        //Toast.makeText(getApplicationContext(),"Appointment",Toast.LENGTH_SHORT).show();
    }

    //Call fragment Appointment Result
    private void CallfragmentAppointmentResult(){
        setTitleToolbar("Appointment Result");
        //Toast.makeText(getApplicationContext(),"Appointment Result",Toast.LENGTH_SHORT).show();
    }

    //Call fragment CustInfoByphone
    private void CallfragmentCustInfoByphone(){
        setTitleToolbar("Cust.Info by phone");
        //Toast.makeText(getApplicationContext(),"Cust.Info by phone",Toast.LENGTH_SHORT).show();
    }

    //Logout Program
    private void Logout(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();
    }

}
