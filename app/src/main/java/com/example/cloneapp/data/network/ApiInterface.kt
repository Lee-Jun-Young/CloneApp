package com.example.cloneapp.data.network

import com.example.cloneapp.data.model.DataList
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("/CloneApp_loadData.php")
    fun requestAllData() : Call<List<DataList>>

    @FormUrlEncoded
    @POST("/CloneApp_searchData.php")
    fun requestSearchData(
            @Field("searchText") searchText: String?
    ): Call<List<DataList>>

}