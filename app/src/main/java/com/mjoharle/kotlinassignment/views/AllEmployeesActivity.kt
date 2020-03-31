package com.mjoharle.kotlinassignment.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mjoharle.kotlinassignment.R
import com.mjoharle.kotlinassignment.adapters.AllEmployeesAdapter
import com.mjoharle.kotlinassignment.dagger.AppModule
import com.mjoharle.kotlinassignment.dagger.DaggerApplicationComponent.builder
import com.mjoharle.kotlinassignment.dagger.RoomModule
import com.mjoharle.kotlinassignment.repository.EmployeeDataSource
import com.mjoharle.kotlinassignment.viewmodels.AllEmployeesActivityViewModel
import com.mjoharle.kotlinassignment.viewmodels.AllEmployeesActivityViewModelFactory2
import javax.inject.Inject


/**@author : Mayur Joharle
 */
class AllEmployeesActivity : AppCompatActivity() {

    private val TAG: String = "HR_MANAGEMENT_APP"

    private lateinit var employeeRecyclerView: RecyclerView;
    private lateinit var progressBar: ContentLoadingProgressBar;

    private lateinit var viewAdapter: AllEmployeesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var mAllEmployeesActivityViewModel: AllEmployeesActivityViewModel

    @Inject
    lateinit var allEmployeesActivityViewModelFactory2: AllEmployeesActivityViewModelFactory2

    @Inject
    lateinit var employeeDataSource: EmployeeDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_employees)

        initDagger()
        initViews()
        initRecyclerView()
    }

    private fun initDagger() {

        builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)

    }

    private fun initViews() {
        employeeRecyclerView = findViewById(R.id.employee_recyclerview)
        progressBar = findViewById(R.id.progress_circular)
        hideProgress()

        mAllEmployeesActivityViewModel =
            ViewModelProvider(this, allEmployeesActivityViewModelFactory2.create()).get(
                AllEmployeesActivityViewModel::class.java
            )
        mAllEmployeesActivityViewModel.getEmployeesList().observe(this, Observer { employees ->
            Log.i(TAG, "UPDATE Called")
            viewAdapter.notifyDataSetChanged()
        })

        mAllEmployeesActivityViewModel.getIsUpdating().observe(this, Observer { what ->
            if (what) {
                showProgress()
            } else {
                hideProgress()
            }
        })

    }

    private fun initRecyclerView() {
        viewAdapter = AllEmployeesAdapter(mAllEmployeesActivityViewModel.getEmployeesList().value!!)
        viewManager = LinearLayoutManager(this)

        employeeRecyclerView.layoutManager = viewManager
        employeeRecyclerView.adapter = viewAdapter

        mAllEmployeesActivityViewModel.getAPIList(this)

    }

    /* Show Progress bar while fetching data from API Call */
    fun showProgress() {
        progressBar.show()
    }

    /* Hide call before displaying data on the RecyclerView */
    fun hideProgress() {
        progressBar.hide()
    }

}