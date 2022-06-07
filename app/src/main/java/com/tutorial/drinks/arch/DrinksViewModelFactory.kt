package com.tutorial.drinks.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DrinksViewModelFactory(private val repository: DrinksRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DrinksViewModel(repository) as T
    }
}