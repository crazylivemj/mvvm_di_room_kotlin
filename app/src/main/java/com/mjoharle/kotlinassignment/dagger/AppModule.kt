package com.mjoharle.kotlinassignment.dagger

import android.app.Application
import dagger.Module
import dagger.Provides


/**
 * App Module to provide App instance
 *
 * @author : Mayur Joharle
 */
@Module
class AppModule(application: Application) {
    val mApplication: Application;

    init{
        mApplication = application
    }

    @Provides
    fun providesApplication(): Application{
        return mApplication
    }

}