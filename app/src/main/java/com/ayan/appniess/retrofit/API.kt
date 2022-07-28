package com.ayan.blockchain.retrofit


import com.ayan.appniess.model.DataClassList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface API {

    @GET("getMyList")
    fun loadContactList(): Call<DataClassList>
}