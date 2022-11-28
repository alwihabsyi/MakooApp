package com.alwihabsyi.makooap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.dataNews.ArticlesItem

class NewsAdapter( private val dataset: List<ArticlesItem>,
    val eventHandling: (ArticlesItem) -> Unit ) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vh_news,parent,false), eventHandling
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBindView(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size
}