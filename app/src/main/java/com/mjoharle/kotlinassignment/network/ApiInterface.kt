package com.mjoharle.kotlinassignment.network

import com.mjoharle.kotlinassignment.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET


/**
 * Retrofit API Call interface
 *
 * @author : Mayur Joharle
 */
interface ApiInterface {

    @GET("api/v1/employees")
    fun getProjectList(): Call<ResponseModel>
}