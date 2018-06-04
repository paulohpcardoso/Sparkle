package com.example.danijela.sparkle;

import android.content.Context;

import com.example.danijela.sparkle.api.HabitMockService;
import com.example.danijela.sparkle.api.HabitService;
//import com.facebook.FacebookSdk;
import com.example.danijela.sparkle.api.ServiceGenerator;
import com.jakewharton.threetenabp.AndroidThreeTen;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class App extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this); //za statistiku
    }

    private HabitService habitService;
    private Scheduler scheduler;

    private static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static App create(Context context) {
        return App.get(context);
    }

    public HabitService getHabitService() {
        if (habitService == null) {

            habitService = new HabitMockService();
            //habitService = ServiceGenerator.createService(HabitService.class);
        }
        return habitService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) scheduler = Schedulers.io();

        return scheduler;
    }

//        public void setHabitService(HabitService habitService) {
//            this.habitService = habitService;
//        }

//        public void setScheduler(Scheduler scheduler) {
//            this.scheduler = scheduler;
//        }
}

