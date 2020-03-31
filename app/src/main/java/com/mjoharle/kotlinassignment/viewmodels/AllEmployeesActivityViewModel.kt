package com.mjoharle.kotlinassignment.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mjoharle.kotlinassignment.model.Employee
import com.mjoharle.kotlinassignment.model.ResponseModel
import com.mjoharle.kotlinassignment.network.APIClient
import com.mjoharle.kotlinassignment.network.ApiInterface
import com.mjoharle.kotlinassignment.repository.EmployeeDataSource
import com.mjoharle.kotlinassignment.utils.SupportUtils
import com.radutopor.viewmodelfactory.annotations.Provided
import com.radutopor.viewmodelfactory.annotations.ViewModelFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class for Activity
 * Annotated ith ViewModelFactory to provide instance of Employee repo.
 * @author : Mayur Joharle
 */
@ViewModelFactory
class AllEmployeesActivityViewModel(@Provided private val employeeDataSource: EmployeeDataSource) :
    ViewModel(), Callback<ResponseModel> {
    private val TAG: String = "HR_MANAGEMENT_APP"

    lateinit var responseData: ArrayList<Employee>

    /* MutableLiveData to hold Employee data*/
    var allEmployees: MutableLiveData<ArrayList<Employee>>

    /* MutuableLiveData to show Progressbar behaviour */
    var mIsUpdating = MutableLiveData<Boolean>()

    init {
        val list = ArrayList<Employee>()
        allEmployees = MutableLiveData<ArrayList<Employee>>()
        allEmployees.value = list
    }

    fun getEmployeesList(): MutableLiveData<ArrayList<Employee>> {
        return allEmployees
    }


    /**
     * This method makes an API call to the fetch the data from server.
     * */
    fun getAPIList(context: Context) {
        if (SupportUtils.isNetworkAvailable(context)) {
            dbDataExists(true)
        } else {
            // Check DB
            dbDataExists(false)
        }
    }

    /**
     * @param connection: set value to true if internet connectivity is available
     * */
    fun dbDataExists(connection: Boolean) {
        // check DB if Employee data exists!

        CoroutineScope(IO).launch {
            val data = employeeDataSource.getEmployees()

            if (data.size > 0) {
                val list = allEmployees.value
                list?.addAll(data)

                allEmployees.postValue(list)

            }
            //Else Get Data from Server
            else if (connection) {
                callApi()
            }
        }
    }

    /**
     * fun to make a API Call to Server and Fetch EMployee Data
    * */
    fun callApi() {
        // Get Data from API
        Log.i(TAG, "View Model : API Called")

        mIsUpdating.postValue(true)

        var apiServices = APIClient.client.create(ApiInterface::class.java)
        val call = apiServices.getProjectList()

        call.enqueue(this)
    }

    /**
     * @param employeeList : Input `ArrayList<Employee>` to save in Room Database
     * */
    fun storeInDB(employeeList: ArrayList<Employee>) {

        Log.d(TAG, "SAVING Data to DB")
        CoroutineScope(Dispatchers.IO).launch {
            employeeDataSource.insertEmployees(employeeList)
            Log.d(TAG, "Data SAVED")
        }
    }

    fun getIsUpdating():MutableLiveData<Boolean>{
        return mIsUpdating
    }

    /**
     * Retrofit onFailure Callback
     */
    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
        Log.d(TAG, "API ERROR")
        mIsUpdating.postValue(false)
    }

    /**
     * Retrofit onResponse Callback
     */
    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
        val jsonResponse = response.body()

        CoroutineScope(IO).launch {
            var newEmployee = allEmployees.value

            responseData = jsonResponse?.data as ArrayList<Employee>
            newEmployee?.addAll(responseData)

            /* Cache data into our Room Database*/
            storeInDB(responseData)

            /* Post Values to the MutableLiveData to Display it on RecyclerView */
            withContext(Main) {
                mIsUpdating.postValue(false)
                allEmployees.postValue(newEmployee)
            }
        }
    }

}