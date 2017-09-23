package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * Created by Sitrach on 22/09/2017.
 */

public class InsertAppointment extends AsyncTask<String, Void, String> {


    private Context context;

    public InsertAppointment(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add("DPcode", strings[0])
                    .add("SAcode", strings[1])
                    .add("CScode", strings[2])
                    .add("AppDate", strings[3])
                    .add("AppStartTime", strings[4])
                    .add("CTPcode", strings[5])
                    .add("WTcode", strings[6])
                    .add("PURPcode", strings[7])
                    .add("AppStatus", strings[8])
                    .add("Remark", strings[9])
                    .add("AppReasonReturn", strings[10])
                    .add("AppWorkDetail", strings[11])
                    .add("AppVisit_Byphone", strings[12])
                    .add("AppSourceflag", strings[13])
                    .add("CurrentUserSTFcode", strings[14])
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(strings[15]).post(data).build();
            okhttp3.Request request = builder.build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor())
                    .build();
            okhttp3.Response response = client.newCall(request).execute();
            String result = response.body().string();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
