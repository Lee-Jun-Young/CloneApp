package com.example.cloneapp.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.databinding.ItemSearchRvBinding

class SearchAdapter(val context: Context, datas: List<DataList>) :
        RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    val data = datas

    class MyViewHolder(val binding: ItemSearchRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : DataList){
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}