package provider.androidbuffer.com.samplerentapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import provider.androidbuffer.com.samplerentapplication.database.MyDataBase;
import provider.androidbuffer.com.samplerentapplication.model.MainModel;
import provider.androidbuffer.com.samplerentapplication.network.ApiProvider;
import provider.androidbuffer.com.samplerentapplication.network.WebSericeInterface;
import provider.androidbuffer.com.samplerentapplication.service.MyJobSchedular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";

//    @BindView(R.id.tvPropertyType)
    TextView tvPropertyType;

    @BindView(R.id.cbApartment)
    CheckBox cbApartment;

//    @BindView(R.id.tvApartment)
    TextView tvApartment;

    @BindView(R.id.cbCondo)
    CheckBox cbCondo;

//    @BindView(R.id.tvCondo)
    TextView tvCondo;

    @BindView(R.id.cbBoatHouse)
    CheckBox cbBoatHouse;

//    @BindView(R.id.tvBoatHouse)
    TextView tvBoatHouse;

    @BindView(R.id.cbLand)
    CheckBox cbLand;

//    @BindView(R.id.tvLand)
    TextView tvLand;

//    @BindView(R.id.tvNoOfRooms)
    TextView tvNoOfRooms;

    @BindView(R.id.cbRooms)
    CheckBox cbRooms;

//    @BindView(R.id.tvRooms)
    TextView tvRooms;

    @BindView(R.id.cbNoRooms)
    CheckBox cbNoRooms;

//    @BindView(R.id.tvNoRooms)
    TextView tvNoRooms;

//    @BindView(R.id.tvOtherFacilities)
    TextView tvOtherFacilities;

    @BindView(R.id.cbSwimmingPool)
    CheckBox cbSwimmingPool;

//    @BindView(R.id.tvSwimmingPool)
    TextView tvSwimmingPool;

    @BindView(R.id.cbGardenArea)
    CheckBox cbGardenArea;

//    @BindView(R.id.tvGardenArea)
    TextView tvGardenArea;

    @BindView(R.id.cbGarage)
    CheckBox cbGarage;

//    @BindView(R.id.tvGarage)
    TextView tvGarage;

    @BindView(R.id.btnSucess)
    Button btnSucess;

    MyDataBase myDataBase;
    SQLiteDatabase sqLiteDatabase;
    MainModel mainModel;
    private WebSericeInterface webSericeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ButterKnife.bind(this);
        mainModel = new MainModel();
        myDataBase = new MyDataBase(MainActivity.this,MyJobSchedular.name,null,MyJobSchedular.VERSION);
        sqLiteDatabase = myDataBase.getReadableDatabase();
        new MyAsyncTask().execute();
//        setDataToActivity();
        validation();

    }

    private void init(){
        tvPropertyType = findViewById(R.id.tvPropertyType);
        tvApartment = findViewById(R.id.tvApartment);
        tvCondo = findViewById(R.id.tvCondo);
        tvBoatHouse = findViewById(R.id.tvBoatHouse);
        tvLand = findViewById(R.id.tvLand);
        tvNoOfRooms = findViewById(R.id.tvNoOfRooms);
        tvRooms = findViewById(R.id.tvRooms);
        tvNoRooms = findViewById(R.id.tvNoRooms);
        tvOtherFacilities = findViewById(R.id.tvOtherFacilities);
        tvSwimmingPool = findViewById(R.id.tvSwimmingPool);
        tvGardenArea = findViewById(R.id.tvGardenArea);
        tvGarage = findViewById(R.id.tvGarage);
    }

    private void callApi(){
        webSericeInterface = ApiProvider.getApiProvider().create(WebSericeInterface.class);
        Call<MainModel> call = webSericeInterface.getInformation();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                mainModel = response.body();
                setDataFromApi();
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }

    public void jobScheduled(){
        ComponentName componentName = new ComponentName(MainActivity.this, MyJobSchedular.class);
        JobInfo jobInfo = new JobInfo.Builder(123,componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(jobInfo);
        if (resultCode == scheduler.RESULT_SUCCESS){
            Log.d(TAG,"Job scheduled");
        } else {
            Log.d(TAG, "job scheduled failed");
        }
    }

    private void setData(Boolean flag){
        if (flag){
            setDataFromDB();
        } else {
            callApi();
        }
    }

    private void setDataFromDB(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from rent",null);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                tvPropertyType.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.PROPERTY_TYPE)));
                tvApartment.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.APARTMENT)));
                tvCondo.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.CONDO)));
                tvBoatHouse.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.BOAT_HOUSE)));
                tvLand.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.LAND)));
                tvNoOfRooms.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.NO_OF_ROOMS)));
                tvRooms.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.ROOMS)));
                tvNoRooms.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.NO_ROOM)));
                tvOtherFacilities.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.OTHER_FACILITY)));
                tvSwimmingPool.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.SWIMMING_POOL)));
                tvGardenArea.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.GARDEN_AREA)));
                tvGarage.setText(cursor.getString(cursor.getColumnIndex(MyJobSchedular.GARAGE)));
            }
        } else {
            new MyAsyncTask().execute();
        }

    }

    private void validation(){
        cbLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbLand.isChecked()){
                    cbRooms.setClickable(false);
                    Toast.makeText(MainActivity.this,getString(R.string.not_1_to_3_option),Toast.LENGTH_SHORT).show();
                } else {
                    cbRooms.setClickable(true);
                }
            }
        });

        cbRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbRooms.isChecked()){
                    cbLand.setClickable(false);
                    Toast.makeText(MainActivity.this,getString(R.string.not_land_option),Toast.LENGTH_SHORT).show();
                } else {
                    cbLand.setClickable(true);
                }
            }
        });

        cbBoatHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbBoatHouse.isChecked()){
                    cbGarage.setClickable(false);
                    Toast.makeText(MainActivity.this,getString(R.string.not_garage_option),Toast.LENGTH_SHORT).show();
                } else {
                    cbGarage.setClickable(true);
                }
            }
        });

        cbGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbGarage.isChecked()){
                    cbBoatHouse.setClickable(false);
                    cbNoRooms.setClickable(false);
                    Toast.makeText(MainActivity.this,getString(R.string.not_boat_house_no_rooms_option),Toast.LENGTH_SHORT).show();
                } else {
                    cbBoatHouse.setClickable(true);
                    cbNoRooms.setClickable(true);
                }
            }
        });

        cbNoRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbNoRooms.isChecked()){
                    cbGarage.setClickable(false);
                    Toast.makeText(MainActivity.this,getString(R.string.not_no_room_option),Toast.LENGTH_SHORT).show();
                } else {
                    cbGarage.setClickable(true);
                }
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {

            Cursor cursor = sqLiteDatabase.rawQuery("select * from rent",null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0){
                return true;
            } else {
                jobScheduled();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            setData(aBoolean);
        }
    }

    private void setDataFromApi(){

        if (mainModel != null && mainModel.getFacilities() != null){
            if (mainModel.getFacilities().get(0).getName() != null){
//                cv.put(PROPERTY_TYPE,mainModel.getFacilities().get(0).getName());
                tvPropertyType.setText(mainModel.getFacilities().get(0).getName());
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(0).getName() != null){
//                    cv.put(APARTMENT,mainModel.getFacilities().get(0).getOptions().get(0).getName());
                    tvApartment.setText(mainModel.getFacilities().get(0).getOptions().get(0).getName());
                }
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(1).getName() != null){
//                    cv.put(CONDO,mainModel.getFacilities().get(0).getOptions().get(1).getName());
                    tvCondo.setText(mainModel.getFacilities().get(0).getOptions().get(1).getName());
                }
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(2).getName() != null){
//                    cv.put(BOAT_HOUSE,mainModel.getFacilities().get(0).getOptions().get(2).getName());
                    tvBoatHouse.setText(mainModel.getFacilities().get(0).getOptions().get(2).getName());
                }
                if (mainModel.getFacilities().get(0).getOptions() != null && mainModel.getFacilities().get(0).getOptions().get(3).getName() != null){
//                    cv.put(LAND,mainModel.getFacilities().get(0).getOptions().get(3).getName());
                    tvLand.setText(mainModel.getFacilities().get(0).getOptions().get(3).getName());
                }
            }
            if (mainModel.getFacilities().get(1).getName() != null){
//                cv.put(NO_OF_ROOMS,mainModel.getFacilities().get(1).getName());
                tvNoOfRooms.setText(mainModel.getFacilities().get(1).getName());
                if (mainModel.getFacilities().get(1).getOptions() != null && mainModel.getFacilities().get(1).getOptions().get(0).getName() != null){
//                    cv.put(ROOMS,mainModel.getFacilities().get(1).getOptions().get(0).getName());
                    tvRooms.setText(mainModel.getFacilities().get(1).getOptions().get(0).getName());
                }
                if (mainModel.getFacilities().get(1).getOptions() != null && mainModel.getFacilities().get(1).getOptions().get(1).getName() != null){
//                    cv.put(NO_ROOM,mainModel.getFacilities().get(1).getOptions().get(1).getName());
                    tvNoRooms.setText(mainModel.getFacilities().get(1).getOptions().get(1).getName());
                }
            }
            if (mainModel.getFacilities().get(2).getName() != null){
//                cv.put(OTHER_FACILITY,mainModel.getFacilities().get(2).getName());
                tvOtherFacilities.setText(mainModel.getFacilities().get(2).getName());
                if (mainModel.getFacilities().get(2).getOptions() != null && mainModel.getFacilities().get(2).getOptions().get(0).getName() != null){
//                    cv.put(SWIMMING_POOL,mainModel.getFacilities().get(2).getOptions().get(0).getName());
                    tvSwimmingPool.setText(mainModel.getFacilities().get(2).getOptions().get(0).getName());
                }
                if (mainModel.getFacilities().get(2).getOptions() != null && mainModel.getFacilities().get(2).getOptions().get(1).getName() != null){
//                    cv.put(GARDEN_AREA,mainModel.getFacilities().get(2).getOptions().get(1).getName());
                    tvGardenArea.setText(mainModel.getFacilities().get(2).getOptions().get(1).getName());
                }
                if (mainModel.getFacilities().get(2).getOptions() != null && mainModel.getFacilities().get(2).getOptions().get(2).getName() != null){
//                    cv.put(GARAGE,mainModel.getFacilities().get(2).getOptions().get(2).getName());
                    tvGarage.setText(mainModel.getFacilities().get(2).getOptions().get(2).getName());
                }
            }
        }

//        sqLiteDatabase.insert("rent",null,cv);
//        Toast.makeText(MyJobSchedular.this,"Data inserted in table",Toast.LENGTH_SHORT).show();

    }
}

