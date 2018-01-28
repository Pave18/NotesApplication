package com.apalon.notes;

import android.app.Application;

import com.apalon.notes.dao.DaoMaster;
import com.apalon.notes.dao.DaoSession;

import org.greenrobot.greendao.database.Database;


public class AppController extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }


    public static DaoSession getDaoSession() {
        return daoSession;
    }


}