package com.alwihabsyi.makooap

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.alwihabsyi.makooap.dataNews.ArticlesItem
import com.alwihabsyi.makooap.databinding.ActivityNewsDetailBinding
import com.squareup.picasso.Picasso

class newsDetail : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        val user = intent.getParcelableExtra<ArticlesItem>("user")

        //INISIALISASI
        val ivcover = binding.ivCover
        val title = binding.tvTitle
        val date = binding.tvDate
        val author = binding.tvAuthor
        val content = binding.tvContent
        val btn_likep = binding.btnLihatlengkap

        //INPUT DATA
        Picasso
            .get()
            .load(user?.urlToImage)
            .into(ivcover)
        title.text = user?.title
        date.text = user?.publishedAt
        author.text = user?.author
        content.text = user?.content

        //BUTTON
        btn_likep.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user?.url))
            startActivity(intent)
        }
    }
}