package au.edu.unsw.infs3634.musicrecommender;

import android.app.Application;


public class MyApplication extends Application {

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        if (instance != null) {
            //数据库初始化
//            LitePal.initialize(instance);


        }
    }


    public static MyApplication getInstance() {
        return instance;
    }
}
