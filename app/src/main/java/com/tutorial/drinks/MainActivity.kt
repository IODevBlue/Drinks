package com.tutorial.drinks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tutorial.drinks.arch.DrinksRepository
import com.tutorial.drinks.arch.DrinksViewModel
import com.tutorial.drinks.arch.DrinksViewModelFactory
import com.tutorial.drinks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: DrinksViewModel
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModelProviderFactory = DrinksViewModelFactory(DrinksRepository())
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(DrinksViewModel::class.java)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.allDrinksFragment,
            R.id.searchDrinksFragment,
            R.id.categoryFragment
        ))

        val fragHost = supportFragmentManager.findFragmentById(R.id.fragHost) as NavHostFragment
        navController = fragHost.findNavController()
        setupActionBarWithNavController(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _->
            binding.bottomNav.isVisible = appBarConfiguration.topLevelDestinations.contains(destination.id)

        }
        binding.bottomNav.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}