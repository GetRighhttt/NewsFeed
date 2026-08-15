package com.example.newsfeed.presentation.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsfeed.R
import com.example.newsfeed.databinding.ActivityMainBinding
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyWindowInsets()

        /*
        Set up nav controller and nav host fragment with bottom navigation bar.
         */
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

    }

    private fun applyWindowInsets() {
        ViewGroupCompat.installCompatInsetsDispatch(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
        ViewCompat.requestApplyInsets(binding.root)
    }
}
