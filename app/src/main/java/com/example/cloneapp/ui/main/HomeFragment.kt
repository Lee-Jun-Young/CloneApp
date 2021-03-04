package com.example.cloneapp.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloneapp.R
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.data.network.RetrofitClient
import com.example.cloneapp.databinding.FragmentHomeBinding
import com.example.cloneapp.ui.search.SearchActivity
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class HomeFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
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
                val dlg: AlertDialog.Builder = AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
                dlg.setTitle("접수 방법")
                dlg.setMessage("        ")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
                dlg.show()
            }
            R.id.iv_search -> {
                val intent = Intent(context, SearchActivity::class.java)
                startActivity(intent)
                activity?.finish()
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
                        rv_view.layoutManager = layoutManager1
                        rv_view.adapter = adapter

                        var newAdapter = view?.context?.let { it2 -> MyAdapter(it2, it,2 ) }
                        val layoutManager2 = LinearLayoutManager(context)
                        layoutManager2.orientation = LinearLayoutManager.VERTICAL
                        rv_new_view.layoutManager = layoutManager2
                        rv_new_view.adapter = newAdapter

                        var reviewAdapter = view?.context?.let { it3 -> MyAdapter(it3, it, 3) }
                        val layoutManager3 = LinearLayoutManager(context)
                        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL
                        rv_review_view.layoutManager = layoutManager3
                        rv_review_view.adapter = reviewAdapter

                    }
                }
            }

            override fun onFailure(call: Call<List<DataList>>, t: Throwable) {
                Log.d("this is error", t.message.toString())
            }
        })
    }

}