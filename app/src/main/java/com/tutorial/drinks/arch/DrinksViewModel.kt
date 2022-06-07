package com.tutorial.drinks.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.drinks.network.*
import kotlinx.coroutines.launch

class DrinksViewModel(private val repository: DrinksRepository):ViewModel() {
    private val _allDrinks = MutableLiveData<Resource<AllDrinksResponse>>()
    val allDrinks :LiveData<Resource<AllDrinksResponse>> get() = _allDrinks

 private val _drinksByCategory = MutableLiveData<Resource<AllDrinksResponse>>()
    val drinksByCategory :LiveData<Resource<AllDrinksResponse>> get() = _drinksByCategory

    private val _searchDrinks = MutableLiveData<Resource<SearchDrinksResponse>>(Resource.Empty())
    val searchDrinks :LiveData<Resource<SearchDrinksResponse>> get() = _searchDrinks

private val _drinksById = MutableLiveData<Resource<DrinkDetailResponse>>(Resource.Empty())
    val drinksById :LiveData<Resource<DrinkDetailResponse>> get() = _drinksById

private val _drinkCategory = MutableLiveData<Resource<DrinkCategoryResponse>>(Resource.Empty())
    val drinkCategory :LiveData<Resource<DrinkCategoryResponse>> get() = _drinkCategory



    fun getAllDrinks(alcoholicOrNot:String){
        _allDrinks.value = Resource.Loading()
        viewModelScope.launch {
            when(val drinksResponse = repository.getAllDrinks(alcoholicOrNot)){
                is Resource.Successful->{
                    _allDrinks.value = Resource.Successful(drinksResponse.data)
                }
                is Resource.Failure->{
                    _allDrinks.value = Resource.Failure(drinksResponse.msg)
                }
                else->Unit
            }
        }
    }


    fun searchDrinks(name:String){
        _searchDrinks.value = Resource.Loading()
        viewModelScope.launch {
            when(val drinksResponse = repository.searchDrinks(name)){
                is Resource.Successful->{
                    _searchDrinks.value = Resource.Successful(drinksResponse.data)
                }
                is Resource.Failure->{
                    _searchDrinks.value = Resource.Failure(drinksResponse.msg)
                }
                else->Unit
            }
        }
    }
    fun getDrinksById(id:String){
        _drinksById.value = Resource.Loading()
        viewModelScope.launch {
            when(val drinksResponse = repository.getDrinksById(id)){
                is Resource.Successful->{
                    _drinksById.value = Resource.Successful(drinksResponse.data)
                }
                is Resource.Failure->{
                    _drinksById.value = Resource.Failure(drinksResponse.msg)
                }
                else->Unit
            }
        }
    }

    fun getAllCategory(){
        _drinkCategory.value = Resource.Loading()
        viewModelScope.launch {
            when(val drinksResponse = repository.getDrinksCategory()){
                is Resource.Successful->{
                    _drinkCategory.value = Resource.Successful(drinksResponse.data)
                }
                is Resource.Failure->{
                    _drinkCategory.value = Resource.Failure(drinksResponse.msg)
                }
                else->Unit
            }
        }
    }
    fun getDrinksByCategory(categoryName:String){
        _drinksByCategory.value = Resource.Loading()
        viewModelScope.launch {
            when(val drinksResponse = repository.getDrinksByCategory(categoryName)){
                is Resource.Successful->{
                    _drinksByCategory.value = Resource.Successful(drinksResponse.data)
                }
                is Resource.Failure->{
                    _drinksByCategory.value = Resource.Failure(drinksResponse.msg)
                }
                else->Unit
            }
        }
    }


}