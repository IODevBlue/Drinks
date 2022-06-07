package com.tutorial.drinks.arch

import com.tutorial.drinks.network.Resource
import com.tutorial.drinks.network.ApiService

class DrinksRepository {
    suspend fun getAllDrinks(alcoholicOrNot: String) =
        try {
            val drinksResponse = ApiService.retrofitApiService.getAllDrinks(alcoholicOrNot)
            val drinks = drinksResponse.body()
            if (drinksResponse.isSuccessful) {
                Resource.Successful(drinks)
            } else {
                Resource.Failure(drinksResponse.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Failure("An Exception Occurred ${e.message}")
        }


    suspend fun searchDrinks(name: String) =
        try {
            val drinksResponse = ApiService.retrofitApiService.searchAllDrinks(name)
            val drinks = drinksResponse.body()
            if (drinksResponse.isSuccessful) {
                Resource.Successful(drinks)
            } else {
                Resource.Failure(drinksResponse.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Failure("An Exception Occurred ${e.message}")
        }
    suspend fun getDrinksById(id: String) =
        try {
            val drinksResponse = ApiService.retrofitApiService.getDrinkById(id)
            val drinks = drinksResponse.body()
            if (drinksResponse.isSuccessful ) {  //&& drinksResponse.body() != null
                Resource.Successful(drinks)
            } else {
                Resource.Failure(drinksResponse.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Failure("An Exception Occurred ${e.message}")
        }

    suspend fun getDrinksCategory() =
        try {
            val drinksResponse = ApiService.retrofitApiService.getAllCategories()
            val drinks = drinksResponse.body()
            if (drinksResponse.isSuccessful) {
                Resource.Successful(drinks)
            } else {
                Resource.Failure(drinksResponse.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Failure("An Exception Occurred ${e.message}")
        }

    suspend fun getDrinksByCategory(categroyName:String) =
        try {
            val drinksResponse = ApiService.retrofitApiService.getDrinksByCategory(categroyName)
            val drinks = drinksResponse.body()
            if (drinksResponse.isSuccessful) {
                Resource.Successful(drinks)
            } else {
                Resource.Failure(drinksResponse.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Failure("An Exception Occurred ${e.message}")
        }
}