package com.alwihabsyi.makooap

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.dataNews.ArticlesItem
import com.squareup.picasso.Picasso

class NewsViewHolder(view: View, val eventHandling: (ArticlesItem) -> Unit ):RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.tv_newstitle)
    val cover = view.findViewById<ImageView>(R.id.img_covnews)

    fun onBindView(news: ArticlesItem){
        title.text = news.title
        Picasso
            .get()
            .load(news.urlToImage)
            .into(cover)

        itemView.setOnClickListener { eventHandling(news) }
    }
}