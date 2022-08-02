package com.example.newsfeed.presentation.view.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentDetailsBinding
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    /*
    Method to receive the argument details from the news fragment and display the web view.

    This is how you receive arguments with the navigation component, and display them
    in a web view. VERY useful.

    This is also the best approach when wanting to display details on a recycler view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        /*
        Get detail fragment arguments using navArgs().
         */
        val args: DetailsFragmentArgs by navArgs()
        val article = args.selectedArticle
        binding.webview.apply {
            webViewClient = WebViewClient()
            if (article.url != null) {
                loadUrl(article.url)
            }
        }

        /*
        Get main activity viewmodel instance.

        Use that view model instance to get the savearticle() method and save the article
        instance from the bundle arguments above.

        Then display the save article in a snackbar.
         */
        viewModel = (activity as MainActivity).viewModel
        binding.apply {
            floatingActionButton.setOnClickListener {
                viewModel.saveArticle(article)
                Snackbar.make(view,
                    "Saved Successfully!",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}