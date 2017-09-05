package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * Created by Sitrach on 05/09/2017.
 */

public class GetAppointmentGridWhere extends AsyncTask<String, Void, String>{

    private Context context;
    public GetAppointmentGridWhere(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add("DPcode", params[0])
                    .add("SAcode", params[1])
                    .add("StartDate", params[2])
                    .add("EndDate", params[3])
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(params[4]).post(data).build();
            okhttp3.Request request = builder.build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor())
                    .build();
            okhttp3.Response response = client.newCall(request).execute();
            String result = response.body().string();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
