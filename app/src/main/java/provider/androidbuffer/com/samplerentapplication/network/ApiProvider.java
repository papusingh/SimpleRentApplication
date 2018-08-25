package provider.androidbuffer.com.samplerentapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by incred-dev on 12/8/18.
 */

public class ApiProvider {

    public static final String BASE_URL = "https://my-json-server.typicode.com";
    public static Retrofit retrofit = null;

    public static Retrofit getApiProvider(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
