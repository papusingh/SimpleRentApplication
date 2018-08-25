package provider.androidbuffer.com.samplerentapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by incred-dev on 13/8/18.
 */

public class MyDataBase extends SQLiteOpenHelper {

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table rent(_id integer primary key, propertyType text, numberOfRooms text, otherFacility text," +
                " apartment text, condo text, boat text, land text, rooms text, noRoom text, swimming text, garden text, garage text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rent");
        onCreate(sqLiteDatabase);
    }
}
