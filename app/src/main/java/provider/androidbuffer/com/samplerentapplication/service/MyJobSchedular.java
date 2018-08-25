package provider.androidbuffer.com.samplerentapplication.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import provider.androidbuffer.com.samplerentapplication.MainActivity;
import provider.androidbuffer.com.samplerentapplication.database.MyDataBase;
import provider.androidbuffer.com.samplerentapplication.model.MainModel;
import provider.androidbuffer.com.samplerentapplication.network.ApiProvider;
import provider.androidbuffer.com.samplerentapplication.network.WebSericeInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by incred-dev on 14/8/18.
 */

public class MyJobSchedular extends JobService {

    private static final String TAG = "JobService";
    private boolean jobCancelled = false;
    public static final String PROPERTY_TYPE = "propertyType";
    public static final String APARTMENT = "apartment";
    public static final String CONDO = "condo";
    public static final String BOAT_HOUSE = "boat";
    public static final String LAND = "land";
    public static final String NO_OF_ROOMS = "numberOfRooms";
    public static final String ROOMS = "rooms";
    public static final String NO_ROOM = "noRoom";
    public static final String OTHER_FACILITY = "otherFacility";
    public static final String SWIMMING_POOL = "swimming";
    public static final String GARDEN_AREA = "garden";
    public static final String GARAGE = "garage";
    public static final String name = "RENT";
    public static final int VERSION = 1;

    private WebSericeInterface webSericeInterface;
    private MainModel mainModel;
    MyDataBase myDataBase;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        myDataBase = new MyDataBase(MyJobSchedular.this,name,null,VERSION);
        sqLiteDatabase = myDataBase.getWritableDatabase();
        mainModel = new MainModel();

        new Thread(new Runnable() {
            @Override
            public void run() {
                callApi();
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"job cancelled before completion ");
        jobCancelled = true;
        return true;
    }

    private void callApi(){
        webSericeInterface = ApiProvider.getApiProvider().create(WebSericeInterface.class);
        Call<MainModel> call = webSericeInterface.getInformation();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                mainModel = response.body();
                setDataToDataBase();
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }

    private void setDataToDataBase(){
        ContentValues cv = new ContentValues();
        if (mainModel != null && mainModel.getFacilities() != null){
            if (mainModel.getFacilities().get(0).getName() != null){
                cv.put(PROPERTY_TYPE,mainModel.getFacilities().get(0).getName());
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(0).getName() != null){
                    cv.put(APARTMENT,mainModel.getFacilities().get(0).getOptions().get(0).getName());


                }
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(1).getName() != null){
                    cv.put(CONDO,mainModel.getFacilities().get(0).getOptions().get(1).getName());
                }
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(2).getName() != null){
                    cv.put(BOAT_HOUSE,mainModel.getFacilities().get(0).getOptions().get(2).getName());
                }
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(3).getName() != null){
                    cv.put(LAND,mainModel.getFacilities().get(0).getOptions().get(3).getName());
                }
            }
            if (mainModel.getFacilities().get(1).getName() != null){
                cv.put(NO_OF_ROOMS,mainModel.getFacilities().get(1).getName());
                if (mainModel.getFacilities().get(1).getOptions() != null && mainModel.getFacilities().get(1).getOptions().get(0).getName() != null){
                    cv.put(ROOMS,mainModel.getFacilities().get(1).getOptions().get(0).getName());
                }
                if (mainModel.getFacilities().get(1).getOptions() != null && mainModel.getFacilities().get(1).getOptions().get(1).getName() != null){
                    cv.put(NO_ROOM,mainModel.getFacilities().get(1).getOptions().get(1).getName());
                }
            }
            if (mainModel.getFacilities().get(2).getName() != null){
                cv.put(OTHER_FACILITY,mainModel.getFacilities().get(2).getName());
                if (mainModel.getFacilities().get(2).getOptions() != null && mainModel.getFacilities().get(2).getOptions().get(0).getName() != null){
                    cv.put(SWIMMING_POOL,mainModel.getFacilities().get(2).getOptions().get(0).getName());
                }
                if (mainModel.getFacilities().get(2).getOptions() != null && mainModel.getFacilities().get(2).getOptions().get(1).getName() != null){
                    cv.put(GARDEN_AREA,mainModel.getFacilities().get(2).getOptions().get(1).getName());
                }
                if (mainModel.getFacilities().get(2).getOptions() != null && mainModel.getFacilities().get(2).getOptions().get(2).getName() != null){
                    cv.put(GARAGE,mainModel.getFacilities().get(2).getOptions().get(2).getName());
                }
            }
        }

        sqLiteDatabase.insert("rent",null,cv);
        Toast.makeText(MyJobSchedular.this,"Data inserted in table",Toast.LENGTH_SHORT).show();

    }
}
