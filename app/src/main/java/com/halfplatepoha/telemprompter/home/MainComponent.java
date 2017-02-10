package com.halfplatepoha.telemprompter.home;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
