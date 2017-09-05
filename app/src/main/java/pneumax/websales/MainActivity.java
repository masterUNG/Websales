package pneumax.websales;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import pneumax.websales.connected.BasicAuthInterceptor;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.Employees;

//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.client.HttpClient;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginBtn;
    private String _username;
    private String _password;

    private Employees mEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load Layout Activity_main
        setContentView(R.layout.activity_main);
        //bind ตัวแปร
        bindWidgets();
        setEvent();
        //Call Method RuntimePermission
        checkRuntimPermission();
    }

    // Check Runtime Permission -- BEGIN
    public void checkRuntimPermission() {
        Nammu.init(this);
        // Check Runtime Permission
        Nammu.askForPermission(this, Manifest.permission.INTERNET, new PermissionCallback() {
            @Override
            public void permissionGranted() {
//                Toast.makeText(MainActivity.this, "Manifest.permission.INTERNET - Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionRefused() {
                Toast.makeText(MainActivity.this, "Manifest.permission.INTERNET - Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void bindWidgets() {
        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mLoginBtn = (Button) findViewById(R.id.LoginButton);

//        User=pramote,pass=Mote_1597
//        User=sws,pass=167
//        User=AYK,pass=4683432
//        User=mnt,pass=0851556798
//        User=WATCHARAPONG,pass=058774205
//        User=sarayut,pass=22888
    }

    private void setEvent() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                _username = mUsernameEditText.getText().toString();
                _password = mPasswordEditText.getText().toString();

                if (GlobalVar.getInstance().isEmptyString(_username)) {
                    Toast.makeText(MainActivity.this, "กรุณาป้อน Username ด้วย !!!", Toast.LENGTH_SHORT).show();
                } else if (GlobalVar.getInstance().isEmptyString(_password)) {
                    Toast.makeText(MainActivity.this, "กรุณาป้อน Password ด้วย !!!", Toast.LENGTH_SHORT).show();
                } else {
                    MyConstant myConstant = new MyConstant();
                    String url = myConstant.geturlGetLoginWhere();
                    OkHttpHandler okHttpHandler = new OkHttpHandler();
                    okHttpHandler.execute(url);
                }
            }
        });
    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            RequestBody data = new FormBody.Builder()
                    .add("UserName", _username)
                    .add("Password", _password)
                    .build();
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]).post(data).build();
            Request request = builder.build();

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new BasicAuthInterceptor())
                        .build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //Shift+CTrl+Enter ปิดตัวจบทุกอย่าง
                String myJSON;
                myJSON = GlobalVar.getInstance().JsonXmlToJsonString(s);
                String tag = "4SepV2";
                Log.d(tag, "myJSON ==> " + myJSON);

                //               for Not User Pacel
                if (myJSON.equals("[]")) {
                    Toast.makeText(MainActivity.this, "Username หรือ Password ไม่ถูกต้อง !!!", Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray jsonArray = new JSONArray(myJSON);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    MyConstant myConstant = new MyConstant();
                    String[] columnResult = myConstant.getColumnEmployeesStrings();
                    String[] userLoginStrings = new String[columnResult.length];
                    for(int i=0; i<columnResult.length; i+=1){
                        userLoginStrings[i] = jsonObject.getString(columnResult[i]);
                        Log.d(tag,"userLogin[" + i +"] ==>" + userLoginStrings[i]);
                    }

                    s = GlobalVar.getInstance().JsonXmlToJsonStringNotSquareBracket(s);
                    Gson gson = new Gson();
                    Employees rusultEmployeesLogin = gson.fromJson(s.toString(), Employees.class);

                    Intent intent = new Intent(MainActivity.this, ChooseSalesActivity.class);
                    intent.putExtra(Employees.TABLE_NAME, rusultEmployeesLogin);
                    startActivity(intent);
                    //finish(); //ถ้ากด back กลับมาจะไม่เจอหน้า Login แล้ว
                }


//                s = GlobalVar.getInstance().JsonXmlToJsonStringNotSquareBracket(s);
//                Gson gson = new Gson();
//                Employees resultEmps = gson.fromJson(s.toString(), Employees.class);
//
//                if (resultEmps == null) {
//                    Toast.makeText(MainActivity.this, "Username หรือ Password ไม่ถูกต้อง !!!", Toast.LENGTH_SHORT).show();
//                } else {
//                    mEmployees = resultEmps;
//                    if (mEmployees.STFcode == null) {
//                        Toast.makeText(MainActivity.this, "Username หรือ Password ไม่ถูกต้อง !!!", Toast.LENGTH_SHORT).show();
//                    } else {
//                    //Toast.makeText(MainActivity.this, mEmployee.getSTFfullname(), Toast.LENGTH_SHORT).show();
//                    //เหมือนกับ ซองจดหมาย แล้วข้างในซองจดหมายอาจมีอะไรหลายอย่าง ใช้สำหรับสื่อสารข้าม object
//                    Intent i = new Intent(getApplicationContext(), ChooseSalesActivity.class);
//                    //ต้องการ Put Intent ข้อมูล ทั้ง result แต่ต้องไปทำที่หน้า UserBean ก่อน ไม่งั้น Error
//                    i.putExtra(Employees.TABLE_NAME, resultEmps);
//                    startActivity(i);
//                    }
//                } // if
            } catch (Exception e) {
                //e.printStackTrace();
                Toast.makeText(MainActivity.this, "e:" + e, Toast.LENGTH_SHORT).show();
            }

        }
    }

}
