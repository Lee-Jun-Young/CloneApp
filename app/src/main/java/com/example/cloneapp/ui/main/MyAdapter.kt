package com.example.cloneapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloneapp.R
import com.example.cloneapp.data.model.DataList
import com.example.cloneapp.databinding.ItemNewRvBinding
import com.example.cloneapp.databinding.ItemReviewRvBinding
import com.example.cloneapp.databinding.ItemRvBinding

class MyAdapter(val context: Context, datas: List<DataList>, type : Int) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = datas
    private val type = type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            1 -> {
                val binding = ItemRvBinding.inflate(LayoutInflater.from(context), parent, false)
                MultiViewHolder1(binding)
            }
            2 -> {
                val binding = ItemNewRvBinding.inflate(LayoutInflater.from(context), parent, false)
                MultiViewHolder2(binding)
            }
            else -> {
                val binding = ItemReviewRvBinding.inflate(LayoutInflater.from(context), parent, false)
                MultiViewHolder3(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MultiViewHolder1(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataList) {
            binding.data = data
        }
    }

    inner class MultiViewHolder2(val binding: ItemNewRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataList) {
            binding.data = data
        }
    }

    inner class MultiViewHolder3(val binding: ItemReviewRvBinding) : RecyclerView.ViewHolder(binding.root) {
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
            else -> {
                (holder as MultiViewHolder3).onBind(data[position])
                holder.setIsRecyclable(false)
            }

        }
    }
}