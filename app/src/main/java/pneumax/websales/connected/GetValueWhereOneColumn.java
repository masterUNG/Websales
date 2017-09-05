package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * Created by sitrach on 05/09/2017.
 */

public class GetValueWhereOneColumn extends AsyncTask<String, Void, String> {

    private Context context;
    public GetValueWhereOneColumn(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add(params[0], params[1])
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
