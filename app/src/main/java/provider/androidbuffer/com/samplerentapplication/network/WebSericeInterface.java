package provider.androidbuffer.com.samplerentapplication.network;

import java.util.List;

import provider.androidbuffer.com.samplerentapplication.model.MainModel;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by incred-dev on 12/8/18.
 */

public interface WebSericeInterface {

    @GET("/iranjith4/ad-assignment/db")
    Call<MainModel> getInformation();
}
