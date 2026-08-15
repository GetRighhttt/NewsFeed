package com.example.newsfeed.presentation.view.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentDetailsBinding
import com.example.newsfeed.presentation.model.toResults
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private lateinit var viewModel: NewsViewModel
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
    Method to receive the argument details from the news fragment and display the web view.

    This is how you receive arguments with the navigation component, and display them
    in a web view. VERY useful.

    This is also the best approach when wanting to display details on a recycler view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.selectedArticle
        configureWebView(article.link)

        /*
        Use that view model instance to get the save article() method and save the article
        instance from the bundle arguments above.

         */
        viewModel = (requireActivity() as MainActivity).viewModel
        binding.apply {
            floatingActionButton.setOnClickListener {
                viewModel.saveArticle(article.toResults())
                Snackbar.make(
                    binding.root,
                    R.string.article_saved,
                    Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(articleUrl: String?) {
        binding.webview.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                javaScriptCanOpenWindowsAutomatically = false
                allowFileAccess = false
                allowContentAccess = false
            }
            webViewClient = WebViewClient()
            articleUrl?.let(::loadUrl)
        }
    }

    override fun onDestroyView() {
        binding.webview.apply {
            stopLoading()
            destroy()
        }
        _binding = null
        super.onDestroyView()
    }
}
