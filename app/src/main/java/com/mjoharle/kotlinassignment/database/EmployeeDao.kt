package com.mjoharle.kotlinassignment.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mjoharle.kotlinassignment.model.Employee


/**
 * Room Data Access Obejct interface
 * @author : Mayur Joharle
 */
@Dao
interface EmployeeDao{

    @Insert
    suspend fun insert(employee: Employee)

    @Insert
    suspend fun insertAll(employee: List<Employee>)

    @Query("SELECT * FROM employeetable WHERE employee_name = :name")
    suspend fun findEmployeeByName(name: String): Employee

    @Query("SELECT * FROM employeetable")
    suspend fun getAllEmployees():List<Employee>

    @Delete
    suspend fun delete(employeeTable: Employee)

    @Query ( "DELETE FROM employeetable")
    suspend fun deleteAll()
}