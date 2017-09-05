package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * Created by Sitrach on 04/09/2017.
 */

public class GetSalesNameWhere extends AsyncTask<String, Void, String>{

    private Context context;

    public GetSalesNameWhere(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add("STFcode", params[0])
                    .add("SAcode", params[1])
                    .add("DPcode", params[2])
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(params[3]).post(data).build();
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
}// Main Class
