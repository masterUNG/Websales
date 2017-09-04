package pneumax.websales.connected;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pneumax.websales.BasicAuthInterceptor;

/**
 * Created by Sitrach on 04/09/2017.
 */

public class GetValueWhereOneColumn extends AsyncTask<String, View, String>{
    private Context context;

    public GetValueWhereOneColumn(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add(params[0], params[1])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[2]).post(requestBody).build();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor()).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}//Main Class
