package com.bashirli.mymovie.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.util.gone
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.databinding.ActivityMainBinding
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.MoviesMVVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup(){

        binding.apply {
            val navFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navFragment.navController
            bottomNavigationView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener{_,destination,_->
               with(bottomNavigationView){
                   when(destination.id){
                       R.id.reviewsFragment->gone()
                       R.id.imagesFragment->gone()
                       R.id.splashFragment->gone()
                       R.id.getStartedFragment->gone()
                       R.id.loginFragment->gone()
                       R.id.registerFragment->gone()
                       else->{if(visibility==View.GONE) visible()}
                   }
               }
            }

        }
    }



}