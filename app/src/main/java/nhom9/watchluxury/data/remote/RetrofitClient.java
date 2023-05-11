package nhom9.watchluxury.data.remote;

import android.util.Log;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private final Retrofit retrofit;
    private final String BASE_URL;

    public RetrofitClient(String baseUrl) {

        BASE_URL = baseUrl;
        Log.d("Init", "Creating Retrofit client (" + baseUrl + ")");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .authenticator(new TokenAuthenticator())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okClient)
                .build();
    }

    public Retrofit get() {
        return retrofit;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }
}
