package com.example.cloneapp.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloneapp.R
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.data.network.RetrofitClient
import com.example.cloneapp.databinding.ActivitySearchBinding
import com.example.cloneapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        binding.activity = this@SearchActivity

        et_searchText.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val mText = et_searchText.text.toString()
                    searchData(mText)
                    afterSearching()
                    true
                }
                else -> false
            }
        }

    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.iv_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> null
        }
    }

    private fun afterSearching() {
        et_searchText.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(et_searchText.windowToken, 0)
    }

    private fun searchData(mText : String) {

        RetrofitClient.getInstance().requestSearchData(mText).enqueue(object : Callback<List<DataList>> {

            override fun onResponse(call: Call<List<DataList>>, response: Response<List<DataList>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {

                        var searchAdapter = SearchAdapter(applicationContext,body)
                        binding.rvSearchView.layoutManager = GridLayoutManager(applicationContext,2)
                        binding.rvSearchView.adapter = searchAdapter
                        searchAdapter.notifyDataSetChanged()

                    }
                }
            }

            override fun onFailure(call: Call<List<DataList>>, t: Throwable) {
                Log.d("this is error", t.message.toString())
            }
        })
    }

}