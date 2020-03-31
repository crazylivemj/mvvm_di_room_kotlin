package com.mjoharle.kotlinassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mjoharle.kotlinassignment.R
import com.mjoharle.kotlinassignment.model.Employee
import kotlin.collections.ArrayList

/**
 * RecyclerView Adapter to bind Employee data and display on View
 * @author : Mayur Joharle
 */
class AllEmployeesAdapter(private var employeeList: ArrayList<Employee>) :
    RecyclerView.Adapter<AllEmployeesAdapter.EmployeeViewHolder>() {

    fun updateData(data : ArrayList<Employee>){
        employeeList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return EmployeeViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = employeeList.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employeeModel: Employee = employeeList.get(position)

        holder.bind(employeeModel)
    }

    /**@author : Mayur Joharle
     * View Holder class to bind data to the View.
     */
    class EmployeeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_employee, parent, false)){

        private var mTxtEmployee: TextView? = null

        init {
            mTxtEmployee = itemView.findViewById(R.id.txtEmployee)
        }

        fun bind(employeeModel: Employee) {
            var emp: String =
                "${employeeModel.employee_name} \n ${employeeModel.employee_age} \n ${employeeModel.employee_salary}"

            mTxtEmployee?.text = emp
        }
    }

}