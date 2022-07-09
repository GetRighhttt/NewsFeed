package com.example.newsfeed.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsfeed.R
import com.example.newsfeed.databinding.ActivityMainBinding
import com.example.newsfeed.presentation.view.fragments.news.NewsAdapter
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import com.example.newsfeed.presentation.viewmodel.factory.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
All Hilt dependency injection must include this in their Main Activity.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    /*
    Construct view binding instance.
     */
    private lateinit var binding: ActivityMainBinding

    /*
    Create a reference to ViewModel here and share it amongst other fragments since we are
    using the Single Activity multiple fragment approach with DI.
     */
    lateinit var viewModel: NewsViewModel

    /*
    We are going to use DI to provide an instance of ViewModel Factory.

    Also use DI to provide an instance of Adapter.
     */
    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Set up nav controller and nav host fragment with bottom navigation bar.
         */
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        /*
        Write the code to get the ViewModel.
         */
        viewModel = ViewModelProvider(this, newsViewModelFactory)[NewsViewModel::class.java]
    }
}