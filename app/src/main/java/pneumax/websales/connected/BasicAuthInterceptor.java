package pneumax.websales.connected;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sitrach on 15/08/2017.
 * Authenrization Webservice
 */

public class BasicAuthInterceptor  implements Interceptor {

    private String credentials;

    public BasicAuthInterceptor() {
        this.credentials = Credentials.basic("usertest", "User1234");
    }
//    public BasicAuthInterceptor(String user, String password) {
//        this.credentials = Credentials.basic(user, password);
//    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }

}