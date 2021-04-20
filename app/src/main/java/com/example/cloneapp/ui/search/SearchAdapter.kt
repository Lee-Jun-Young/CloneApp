package com.example.cloneapp.ui.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.databinding.*
import retrofit2.Callback

class SearchAdapter(val context: Context, datas: List<DataList>, val type : Int) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = datas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            1 -> {
                val binding = ItemSearchWhileBinding.inflate(LayoutInflater.from(context),parent,false)
                MultiViewHolder1(binding)
            }
            else -> {
                val binding = ItemSearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
                MultiViewHolder2(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MultiViewHolder1(val binding: ItemSearchWhileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataList) {
            binding.data = data

            binding.root.setOnClickListener{
                SearchFragment().searchData(data.name,2)
                Log.d("test!!","click success")

            }

        }
    }

    inner class MultiViewHolder2(val binding: ItemSearchRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataList) {
            binding.data = data
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                (holder as MultiViewHolder1).onBind(data[position])
                holder.setIsRecyclable(false)
            }
            2 -> {
                (holder as MultiViewHolder2).onBind(data[position])
                holder.setIsRecyclable(false)
            }

        }
    }
}