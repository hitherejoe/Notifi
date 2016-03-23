package com.hitherejoe.notifi.injection.component;


import com.hitherejoe.notifi.injection.PerActivity;
import com.hitherejoe.notifi.injection.module.ActivityModule;
import com.hitherejoe.notifi.ui.main.MainActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}