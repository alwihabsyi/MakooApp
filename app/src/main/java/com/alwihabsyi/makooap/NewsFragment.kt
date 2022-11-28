package com.alwihabsyi.makooap

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwihabsyi.makooap.dataNews.APInews
import com.alwihabsyi.makooap.dataNews.ArticlesItem
import com.alwihabsyi.makooap.dataNews.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsFragment : Fragment(R.layout.fragment_news) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView News
        val rvNews = view.findViewById<RecyclerView>(R.id.rv_news)
        rvNews.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL, false)
        rvNews.setHasFixedSize(true)
        rvNews.isNestedScrollingEnabled = false
        getNewsData { news: List<ArticlesItem> ->
            rvNews.adapter = NewsAdapter(news) {
                    Intent(this.context, newsDetail::class.java).apply{
                        putExtra("user", it)
                        startActivity(this)
                    }
                }
            }
        }

    private fun getNewsData(callback: (List<ArticlesItem>) -> Unit) {
        val apiservice = retrofitservice().create(APInews::class.java)
        apiservice.getNews().enqueue(object: Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                return callback(response.body()!!.articles!!)
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {

            }

        })
    }

    private var retrofit: Retrofit? = null
    fun retrofitservice(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}