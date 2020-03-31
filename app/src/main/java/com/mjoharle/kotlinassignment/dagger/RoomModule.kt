package com.mjoharle.kotlinassignment.dagger

import android.app.Application
import androidx.room.Room
import com.mjoharle.kotlinassignment.database.EmployeeDao
import com.mjoharle.kotlinassignment.database.EmployeeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module to provide Database instance
 *
 * @author : Mayur Joharle
 */
@Module
class RoomModule(application: Application) {

    var employeeDatabase: EmployeeDatabase

    init {
        employeeDatabase = Room.databaseBuilder(application, EmployeeDatabase::class.java, "employee_db").build()
    }

    @Singleton
    @Provides
    fun providesDatabase(): EmployeeDatabase{
        return employeeDatabase
    }

    @Singleton
    @Provides
    fun providesEmployeeDao(): EmployeeDao{
        return employeeDatabase.employeetableDao()
    }


}