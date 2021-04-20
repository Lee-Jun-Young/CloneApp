package com.example.cloneapp.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloneapp.R
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.data.network.RetrofitClient
import com.example.cloneapp.databinding.FragmentSearchBinding
import com.example.cloneapp.ui.main.HomeFragment
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : androidx.fragment.app.Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.search = this@SearchFragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        et_searchText.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val mText = et_searchText.text.toString()
                    searchData(mText, 2)
                    afterSearching()
                    true
                }
                else -> false
            }
        }

        val searchViewTextWatcher: TextWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.isEmpty()) {
                    ll_rv.visibility = View.GONE
                    ll_text.visibility = View.GONE
                } else {
                    ll_rv.visibility = View.VISIBLE
                    ll_text.visibility = View.VISIBLE
                    searchData(s.toString(), 1)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        }

        et_searchText.addTextChangedListener(searchViewTextWatcher)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_back -> {
                parentFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
            }
        }
    }

    private fun afterSearching() {
        et_searchText.clearFocus()
        val mInputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.hideSoftInputFromWindow(et_searchText.windowToken, 0)
    }

    fun searchData(mText : String, type : Int) {

        tv_search_result.text = mText
        RetrofitClient.getInstance().requestSearchData(mText).enqueue(object : Callback<List<DataList>> {

            override fun onResponse(call: Call<List<DataList>>, response: Response<List<DataList>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        if(body.isEmpty()) {
                            ll_rv.visibility = View.GONE
                            ll_text.visibility = View.VISIBLE
                        }
                        else{
                            when(type){
                                1 -> {
                                    ll_rv.visibility = View.VISIBLE
                                    ll_text.visibility = View.GONE
                                    val searchWhileAdapter = view?.context?.let { it1 -> SearchAdapter(it1, it, type) }
                                    val layoutManager = LinearLayoutManager(context)
                                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                                    binding.rvSearchView.layoutManager = layoutManager
                                    binding.rvSearchView.adapter = searchWhileAdapter


                                }

                                else -> {
                                    val searchAdapter = view?.context?.let { it2 -> SearchAdapter(it2, it, type) }
                                    binding.rvSearchView.layoutManager = GridLayoutManager(context,2)
                                    binding.rvSearchView.adapter = searchAdapter
                                    searchAdapter?.notifyDataSetChanged()
                                    afterSearching()
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<DataList>>, t: Throwable) {
                Log.d("this is error", t.message.toString())
            }
        })
    }

}