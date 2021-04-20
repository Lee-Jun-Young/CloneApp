package com.example.cloneapp.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.cloneapp.R
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.data.network.RetrofitClient
import com.example.cloneapp.databinding.FragmentHomeBinding
import com.example.cloneapp.ui.search.SearchFragment
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class HomeFragment : androidx.fragment.app.Fragment(), View.OnClickListener{
    private lateinit var binding: FragmentHomeBinding

    private val dialogImgList : MutableList<Int> = mutableListOf(
            R.drawable.dialog_1,
            R.drawable.dialog_2,
            R.drawable.dialog_3,
            R.drawable.dialog_4,
            R.drawable.dialog_5,
            R.drawable.dialog_6
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.home = this@HomeFragment

        loadData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        iv_search.setOnClickListener(this)
        dialog_button.setOnClickListener(this)

        app_bar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                toolbar_name.text = "테이블링"
            } else {
                toolbar_name.text = " "
            }
        })
    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.dialog_button -> {
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)
                val mBuilder = AlertDialog.Builder(context)
                        .setView(mDialogView)

                val  mAlertDialog = mBuilder.show()

                val closeDialog = mDialogView.findViewById<ImageView>(R.id.iv_close)
                closeDialog.setOnClickListener {
                    mAlertDialog.dismiss()
                }

                view_pager.adapter = ViewPagerAdapter(context,dialogImgList)
                view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            }

            R.id.iv_search -> {
                parentFragmentManager.beginTransaction().replace(R.id.linearLayout, SearchFragment()).commitAllowingStateLoss()
            }
            else -> null
        }
    }

    private fun loadData() {

        RetrofitClient.getInstance().requestAllData().enqueue(object : Callback<List<DataList>> {

            override fun onResponse(call: Call<List<DataList>>, response: Response<List<DataList>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("test!!", body.toString())
                    body?.let {

                        var adapter = view?.context?.let { it1 -> MyAdapter(it1, it, 1) }
                        val layoutManager1 = LinearLayoutManager(context)
                        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
                        binding.rvView.layoutManager = layoutManager1
                        binding.rvView.adapter = adapter

                        var newAdapter = view?.context?.let { it2 -> MyAdapter(it2, it,2 ) }
                        val layoutManager2 = LinearLayoutManager(context)
                        layoutManager2.orientation = LinearLayoutManager.VERTICAL
                        binding.rvNewView.layoutManager = layoutManager2
                        binding.rvNewView.adapter = newAdapter

                        var reviewAdapter = view?.context?.let { it3 -> MyAdapter(it3, it, 3) }
                        val layoutManager3 = LinearLayoutManager(context)
                        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL
                        binding.rvReviewView.layoutManager = layoutManager3
                        binding.rvReviewView.adapter = reviewAdapter

                    }
                }
            }

            override fun onFailure(call: Call<List<DataList>>, t: Throwable) {
                Log.d("this is error", t.message.toString())
            }
        })
    }


}