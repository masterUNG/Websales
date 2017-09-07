package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * Created by Sitrach on 07/09/2017.
 */

public class Post4String extends AsyncTask<String, Void, String> {

    private Context context;
    public Post4String(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            okhttp3.RequestBody data = new FormBody.Builder()
                    .add(strings[0], strings[1])
                    .add(strings[2], strings[3])
                    .add("CScode", "")
                    .add(strings[4], strings[5])
                    .add(strings[6], strings[7])
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(strings[8]).post(data).build();
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
