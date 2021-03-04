package com.example.cloneapp.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloneapp.R
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.ui.search.SearchAdapter
import java.text.SimpleDateFormat
import android.widget.RatingBar
import com.example.cloneapp.ui.search.SearchActivity
import java.util.*

object DataBindingUtils {
    @BindingAdapter("bind_img")
    @JvmStatic
    fun bindImg(imageView: ImageView, id:Int){
        val url = "http://smparkworld.com/Android/USProject/thebrains/images/ad_dialog_images/"
        Glide.with(imageView.context)
                .load(url + id.toString())
                .error(R.drawable.noimage)
                .into(imageView)
    }

    @BindingAdapter("bind_grade")
    @JvmStatic
    fun bindDouble(textView:TextView, id:Double){
        textView.text = id.toString()
    }

    @BindingAdapter("bind_cnt")
    @JvmStatic
    fun bindCnt(textView:TextView, id:Int){
        textView.text = "($id)"
    }

    @BindingAdapter("bind_boolean")
    @JvmStatic
    fun bindBoolean(textView:TextView, id:Boolean){
        if(id){
            textView.text = "원격줄서기"
        }
        else{
            textView.text = "즉시예약"
        }
    }

    @BindingAdapter("bind_rating")
    @JvmStatic
    fun bindRating(ratingBar: RatingBar, id:Double){
        ratingBar.rating = id.toFloat()
    }

    @BindingAdapter("bind_writer")
    @JvmStatic
    fun bindWriter(textView:TextView, id:String){
        if(id.isEmpty()) {
            val str = "익명"
            textView.text = str
        }else {
            val ran = IntRange(0,1)
            val string = id.slice(ran)
            textView.text = "$string**"
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @BindingAdapter("bind_time")
    @JvmStatic
    fun bindTime(textView:TextView, writingtime:String){
        val sampleDate = writingtime
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sf.parse(sampleDate)
        val today = Calendar.getInstance()
        val calcuDate = (today.time.time - date.time) / (60 * 60 * 24 * 1000)

        textView.text = calcuDate.toString() + "일 전"
    }

}