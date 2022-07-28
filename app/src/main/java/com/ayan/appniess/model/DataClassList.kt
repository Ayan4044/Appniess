package com.ayan.appniess.model

import com.google.gson.annotations.SerializedName

data class DataClassResponseWrapper (
    val response: Any, val statusCode: Int
)

data class DataClassList(

    @SerializedName("statusCode" ) var statusCode : Int?                  = null,
    @SerializedName("status"     ) var status     : Boolean?              = null,
    @SerializedName("message"    ) var message    : String?               = null,
    @SerializedName("dataObject" ) var dataObject : ArrayList<DataClassHierarchy> = arrayListOf()

)


data class DataClassHierarchy (

    @SerializedName("myHierarchy" ) var myHierarchy : ArrayList<DataClassHierarchyList> = arrayListOf()

)

data class DataClassHierarchyList (

    @SerializedName("heirarchyList" ) var heirarchyList : ArrayList<HeirarchyList> = arrayListOf()

)
data class HeirarchyList(

    @SerializedName("contactName") var contactName: String? = null,
    @SerializedName("contactNumber") var contactNumber: String? = null,
    @SerializedName("designationName") var designationName: String? = null

)