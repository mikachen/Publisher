package com.zoe.publisher

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zoe.publisher.Util.toDataClass
import com.zoe.publisher.databinding.ItemArticleBinding

class BlogAdapter: ListAdapter<Articles, BlogAdapter.ArticlesViewHolder>(DiffCall()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticlesViewHolder {
        return ArticlesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {

        val data = getItem(position)
        holder.bind(data)

    }

    class ArticlesViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(it: Articles) {
            val authorInfo: Author = it.author.toDataClass()

            binding.title.text = it.title
            binding.createdTime.text = Util.convertLongToTime(it.createdTime)
            binding.authorName.text = authorInfo.name
            binding.category.text = it.category
            binding.content.text = it.content

            when(it.category){
                "Beauty" -> binding.category.setTextColor(Color.parseColor("#2894FF"))
                "Gossiping" -> binding.category.setTextColor(Color.parseColor("#B15BFF"))
                else -> binding.category.setTextColor(Color.parseColor("#00EC00"))
            }
        }


        companion object {
            fun from(parent: ViewGroup):ArticlesViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemArticleBinding.inflate(layoutInflater, parent, false)

                return ArticlesViewHolder(binding)
            }
        }
    }
}

class DiffCall : DiffUtil.ItemCallback<Articles>() {
    override fun areItemsTheSame(
        oldItem: Articles,
        newItem: Articles,
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Articles,
        newItem: Articles,
    ): Boolean {
        return oldItem == newItem
    }
}