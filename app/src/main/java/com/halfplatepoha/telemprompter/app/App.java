package com.halfplatepoha.telemprompter.app;

import android.app.Application;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        component = DaggerAppComponent.builder()
                .appModule(getAppModule())
                .build();
        component.inject(this);
    }

    public AppComponent getComponent() {
        return component;
    }

    private AppModule getAppModule() {
        return new AppModule(this);
    }
}
