package com.example.cloneapp.data.model

import com.google.gson.annotations.SerializedName

data class DataList(

    @SerializedName("no")
    var no : Int,

    @SerializedName("name")
    var name : String,

    @SerializedName("grade")
    var grade :Double,

    @SerializedName("review")
    var review : String,

    @SerializedName("writer")
    var writer : String,

    @SerializedName("location")
    var location : String,

    @SerializedName("writingtime")
    var writingtime : String,

    @SerializedName("remote")
    var remote : Boolean,

    @SerializedName("category")
    var category : String

)
