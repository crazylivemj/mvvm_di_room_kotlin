package com.mjoharle.kotlinassignment.dagger

import android.app.Application
import com.mjoharle.kotlinassignment.database.EmployeeDao
import com.mjoharle.kotlinassignment.database.EmployeeDatabase
import com.mjoharle.kotlinassignment.views.AllEmployeesActivity
import dagger.Component
import javax.inject.Singleton


/**
 * Dagger Component interface
 * @author : Mayur Joharle
 */
@Singleton
@Component(dependencies = [], modules = [AppModule::class,RoomModule::class])
interface ApplicationComponent {

    fun inject(allEmployeesActivity: AllEmployeesActivity)

    fun employeeDao():EmployeeDao

    fun employeeDatabase():EmployeeDatabase

    fun application():Application

}