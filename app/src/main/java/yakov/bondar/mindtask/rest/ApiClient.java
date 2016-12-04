package yakov.bondar.mindtask.rest;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://madiosgames.com";
    private static Retrofit sRetrofit;

    public static <T> T getInstance(Class<T> instance) {
        if (sRetrofit == null) {
            synchronized (ApiClient.class) {
                if (sRetrofit == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    httpClient.addInterceptor(logging);

                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(BASE_URL)
                            .client(httpClient.build())
                            .addConverterFactory(GsonConverterFactory.create());
                    sRetrofit = builder.build();
                }
            }
        }
        return sRetrofit.create(instance);
    }
}
