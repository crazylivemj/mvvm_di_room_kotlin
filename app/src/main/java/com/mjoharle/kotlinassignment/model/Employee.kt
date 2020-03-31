package com.mjoharle.kotlinassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Employee Model data class used to hold data to be store and Display it on ViewAdapter
 * @author : Mayur Joharle
* */
@Entity(tableName = "employeetable")
data class Employee(

    @PrimaryKey(autoGenerate = true)
    val sr_no: Int,
    val id: String,
    val employee_name: String,
    val employee_salary: String,
    val employee_age: String,
    val profile_image: String
)