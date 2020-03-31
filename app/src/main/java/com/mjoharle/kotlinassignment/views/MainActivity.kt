package com.mjoharle.kotlinassignment.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mjoharle.kotlinassignment.R

/**
 * Starting Activity for Project
 * @author : Mayur Joharle
 */
class MainActivity : AppCompatActivity() {

    lateinit var mBtnGetEmployee: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnGetEmployee = findViewById(R.id.btnGet)
        mBtnGetEmployee.setOnClickListener(View.OnClickListener {

            var intent = Intent(this,AllEmployeesActivity::class.java)
            startActivity(intent)

        })

    }
}
