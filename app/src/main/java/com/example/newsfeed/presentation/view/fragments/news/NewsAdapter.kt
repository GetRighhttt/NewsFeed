package com.example.newsfeed.presentation.view.fragments.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.NewsListItemBinding

/*
Here we will give an example of the DiffUtil class.
 */

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    /*
    DiffUtil reduces the number of updates for converting one list to another.

    We always pass in our list of data for the item call back.

    Class has two override methods that we will use to update the list.
     */
    private val callback = object : DiffUtil.ItemCallback<Article>() {
        /*
        Decides if two objects are the same.
         */
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.link!! == newItem.link!!
        }

        /*
        Decides if those objects contents are the same.
         */
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    /*
    Now we need the AsyncListDiffer to
     */
    val differ = AsyncListDiffer(this, callback)



    inner class NewsViewHolder(
        private val binding: NewsListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        /*
        Binding article info from data model to recycler view in a method.
         */
        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                tvDescription.text = article.excerpt
                tvPublishedAt.text = article.published_date
                tvSource.text = article.rights?.toString() ?: ""
                /*
                Get the image with glide.
                 */
                Glide.with(ivArticleImage.context)
                    .load(article.media)
                    .into(ivArticleImage)

                root.setOnClickListener{
                    onItemClickListener?.let {
                        it(article)
                    }
                }
            }
        }
        /*
        Bind linear list item for animation.
         */
        val linearListItem = binding.linearListItem
    }

    /*
Item click listener variable.
 */
    private var onItemClickListener: ((Article) -> Unit)? = null

    /*
  Setter method for the onItemClickListener.
   */
    fun setOnItemClickListener(listener: ((Article) -> Unit)?) {
        onItemClickListener = listener
    }

    /*
    Inflate the list item using view binding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    /*
    Bind the views from ViewHolder class to the view holder.
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
        holder.linearListItem.startAnimation(
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.favorite_anim)
        )
    }

    /*
    Return the size of the list.
     */
    override fun getItemCount(): Int = differ.currentList.size

}