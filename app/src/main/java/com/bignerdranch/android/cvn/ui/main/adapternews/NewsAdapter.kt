package com.bignerdranch.android.cvn.ui.main.adapternews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cvn.R
import com.bignerdranch.android.cvn.data.News
import com.bignerdranch.android.cvn.databinding.NewsCardBinding
import com.bumptech.glide.Glide

class NewsAdapter(private var news: List<News>, private val listener: NewsAdapterListener) :
    RecyclerView.Adapter<NewsyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return NewsyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsyViewHolder, position: Int) {
        val newsInfo = news[position]
        holder.bind(newsInfo)
        holder.itemView.setOnClickListener {
            listener.onNewsItemClick(newsInfo.webUrl)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}

class NewsyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding: NewsCardBinding = NewsCardBinding.bind(itemView)
    fun bind(news: News) {
        binding.tvTitle.text = news.title
        binding.tvDate.text = news.publishedDateTime
        if (news.images?.isNotEmpty() == true) {
            val imageUrl = news.images[0]
            Glide
                .with(itemView.context)
                .load(imageUrl.url)
                .centerCrop()
                .into(binding.imageViewNews)
        }
    }
}