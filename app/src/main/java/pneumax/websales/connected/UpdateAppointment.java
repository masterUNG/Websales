package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * Created by Sitrach on 22/09/2017.
 */

public class UpdateAppointment extends AsyncTask<String, Void, String> {

    private Context context;

    public UpdateAppointment(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add("DPcodeCond", strings[0])
                    .add("SAcodeCond", strings[1])
                    .add("CScodeCond", strings[2])
                    .add("AppDateCond", strings[3])
                    .add("AppStartTimeCond", strings[4])
                    .add("CScode", strings[5])
                    .add("AppDate", strings[6])
                    .add("AppStartTime", strings[7])
                    .add("CTPcode", strings[8])
                    .add("WTcode", strings[9])
                    .add("PURPcode", strings[10])
                    .add("Remark", strings[11])
                    .add("AppReasonReturn", strings[12])
                    .add("AppWorkDetail", strings[13])
                    .add("AppVisit_Byphone", strings[14])
                    .add("CurrentUserSTFcode", strings[15])
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(strings[16]).post(data).build();
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
