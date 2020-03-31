package com.mjoharle.kotlinassignment.repository

import com.mjoharle.kotlinassignment.model.Employee

/**@author : Mayur Joharle
 */
interface EmployeeRepository {

//    lateinit var empArraylist: ArrayList<EmployeeModel>
//    lateinit var data: MutableLiveData<ArrayList<EmployeeModel>>

    suspend fun getEmployees(): ArrayList<Employee>

    fun insertEmployees(list: ArrayList<Employee>)

    fun deleteEmployees()
}