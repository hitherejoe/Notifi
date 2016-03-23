package com.hitherejoe.notifi;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.notifi.injection.component.ApplicationComponent;
import com.hitherejoe.notifi.injection.component.DaggerApplicationComponent;
import com.hitherejoe.notifi.injection.module.ApplicationModule;

import timber.log.Timber;

public class NotifiApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static NotifiApplication get(Context context) {
        return (NotifiApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

}
