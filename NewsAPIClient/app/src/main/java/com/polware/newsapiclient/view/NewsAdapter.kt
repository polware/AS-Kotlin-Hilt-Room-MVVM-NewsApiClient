package com.polware.newsapiclient.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.databinding.NewsItemBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var onItemClickListener: ((Article) -> Unit)? = null
    // DiffUtil can replaces the function "notifyDataSetChanged" (this calculates the difference between two lists)
    private val callback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

    inner class NewsViewHolder(
        private val bindingAdapter: NewsItemBinding): RecyclerView.ViewHolder(bindingAdapter.root) {

        fun bind(article: Article){
            Log.i("MYTAG","came here ${article.title}")
            bindingAdapter.tvTitle.text = article.title
            bindingAdapter.tvDescription.text = article.description
            bindingAdapter.tvPublishedAt.text = article.publishedAt
            bindingAdapter.tvSource.text = article.source?.name

            Glide.with(bindingAdapter.ivArticleImage.context).
            load(article.imageURL).
            into(bindingAdapter.ivArticleImage)

            bindingAdapter.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(NewsItemBinding.inflate(LayoutInflater
            .from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}