package nhom9.watchluxury.data.remote;

import nhom9.watchluxury.data.remote.service.ServiceHolder;
import nhom9.watchluxury.util.APIUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {

        if (retrofit == null)
        {
            ServiceHolder serviceHolder = new ServiceHolder();

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .authenticator(new TokenAuthenticator(serviceHolder))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(okClient)
                    .build();

            serviceHolder.setService(APIUtils.getAuthenticationService());
        }

        return retrofit;
    }
}
