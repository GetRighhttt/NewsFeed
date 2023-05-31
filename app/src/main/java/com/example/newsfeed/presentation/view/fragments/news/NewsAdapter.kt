package com.example.newsfeed.presentation.view.fragments.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Results
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
    private val callback = object : DiffUtil.ItemCallback<Results>() {
        /*
        Decides if two objects are the same.
         */
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
           return oldItem.link!! == newItem.link!!
        }

        /*
        Decides if those objects contents are the same.
         */
        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
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
        fun bind(results: Results) {
            binding.apply {
                tvTitle.text = results.title
                tvDescription.text = results.description
                tvPublishedAt.text = results.pubDate
                /*
                Get the image with glide.
                 */
                Glide.with(ivArticleImage.context)
                    .load(results.image_url)
                    .transition(DrawableTransitionOptions().crossFade(1000))
                    .fitCenter()
                    .placeholder(R.drawable.icons8_placeholder_64)
                    .into(ivArticleImage)

                root.setOnClickListener{
                    onItemClickListener?.let {
                        it(results)
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
    private var onItemClickListener: ((Results) -> Unit)? = null

    /*
  Setter method for the onItemClickListener.
   */
    fun setOnItemClickListener(listener: ((Results) -> Unit)?) {
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