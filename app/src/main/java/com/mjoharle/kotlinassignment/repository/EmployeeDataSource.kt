package com.mjoharle.kotlinassignment.repository

import com.mjoharle.kotlinassignment.model.Employee
import com.mjoharle.kotlinassignment.database.EmployeeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

/**@author : Mayur Joharle
 */
class EmployeeDataSource @Inject constructor(employeeDao: EmployeeDao) : EmployeeRepository {

    var mEmployeeDao: EmployeeDao = employeeDao

    override suspend fun getEmployees(): ArrayList<Employee> {
        var data: ArrayList<Employee> =
            ArrayList<Employee>()
        data = mEmployeeDao.getAllEmployees() as ArrayList<Employee>
        return data
    }

    override fun insertEmployees(list: ArrayList<Employee>) {
        CoroutineScope(IO).launch {
            mEmployeeDao.insertAll(list as List<Employee>)
        }
    }

    override fun deleteEmployees() {
        CoroutineScope(IO).launch {
            mEmployeeDao.deleteAll()
        }
    }
}