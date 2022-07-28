package com.ayan.appniess.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayan.appniess.CheckInternet
import com.ayan.appniess.model.DataClassList
import com.ayan.appniess.model.DataClassResponseWrapper
import com.ayan.blockchain.retrofit.RetrofitSingletonInstance
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.net.ConnectException
import kotlin.system.exitProcess

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context = application.applicationContext


    companion object {


        val dataclassFailed = DataClassResponseWrapper(
            "Failed",
            -1
        )

        val dataclassError = DataClassResponseWrapper(
            "Error",
            -2
        )
    }

    val liveResponseWrapper: MutableLiveData<DataClassResponseWrapper> =
        MutableLiveData()

    val liveDataContactList: MutableLiveData<DataClassList?> = MutableLiveData()

    fun loadDataList(){
        if(CheckInternet.isConnected(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val callDataListResponse =
                        RetrofitSingletonInstance.instance.loadContactList().awaitResponse()

                    if (callDataListResponse.code() == 200) {
                        if (callDataListResponse.body() is DataClassList)
                            callDataListResponse.body()?.let {
                                liveResponseWrapper.postValue(DataClassResponseWrapper(it, 1))
                            }
                        else
                            liveResponseWrapper.postValue(dataclassError)


                    }
                } catch (ex: ConnectException) {
                    liveResponseWrapper.postValue(dataclassFailed)
                    println("Ex $ex")
                }
            }
        }
        else {
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()

        }
    }



}