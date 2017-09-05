package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import pneumax.websales.BasicAuthInterceptor;

/**
 * Created by sitrach on 05/09/2017.
 */

public class GetSalesCodeWhere extends AsyncTask<String, Void, String> {

    private Context context;
    public GetSalesCodeWhere(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add("STFcode", params[0])
                    .add("DPcode", params[1])
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(params[2]).post(data).build();
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
