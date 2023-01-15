package com.polware.newsapiclient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.polware.newsapiclient.R
import com.polware.newsapiclient.databinding.ActivityMainBinding
import com.polware.newsapiclient.viewmodel.NewsViewModel
import com.polware.newsapiclient.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Development Steps:
 * 1) Data: create Models data class
 * 2) ViewModel: create Repository, and UseCases classes
 * 3) Data / Api: NewsApiService
 * 4) ViewModel: create DataSources classes, NewsRepositoryImpl, NewsViewModel, ViewModelFactory
 * 5) View: NewsApp, D.I. NetworkModule, RemoteDataModule, RepositoryModule, UseCasesModule, FactoryModule
 * 6) View: Navigation components, Adapter class, NewsFragment, DetailsNewsFragment
 * 7) Data: Database classes | ViewModel -> NewsLocalDataSource
 * 8) View: DetailsNewsFragment, SavedNewsFragment
 * 9) Room Database connection: DAO -> NewsLocalDataSource + Impl. -> NewsRepository + Impl.
 *    -> DeleteNewsUseCase -> NewsViewModel + Factory -> D.I. FactoryMod, UseCaseMod
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModel: NewsViewModel
    @Inject
    lateinit var factory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

    }

}