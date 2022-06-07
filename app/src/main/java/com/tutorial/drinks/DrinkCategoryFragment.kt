package com.tutorial.drinks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tutorial.drinks.arch.DrinksViewModel
import com.tutorial.drinks.databinding.FragmentCategoryBinding
import com.tutorial.drinks.databinding.FragmentSearchDrinksBinding
import com.tutorial.drinks.network.Resource

class DrinkCategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel :DrinksViewModel by activityViewModels()
    private val categoryAdapter:DrinksCategoryAdapter by lazy { DrinksCategoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllCategory()
        setAdapter()
        categoryAdapter.adapterClickListener {category->
            val navigate = DrinkCategoryFragmentDirections.actionCategoryFragmentToDrinksByCategoryFragment(category.strCategory)
            findNavController().navigate(navigate)
        }

    }

    private fun setAdapter(){
        binding.drinkCategoryRV.adapter= categoryAdapter
        viewModel.drinkCategory.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Successful->{
                    binding.emptyStateTv.isVisible = false
                    hideError()
                    hideLoadingState()
                    response.data?.let {
                        categoryAdapter.submitList(it.drinks)
                    }
                }
                is Resource.Failure->{
                    binding.emptyStateTv.isVisible = false
                    hideLoadingState()
                    showError(response.msg.toString())
                }
                is Resource.Loading->{
                    hideError()
                    binding.emptyStateTv.isVisible = false
                    showLoadingState()
                }
                is Resource.Empty->{
                    hideError()
                    hideLoadingState()
                    binding.emptyStateTv.isVisible = true
                }
            }
        }
    }

    private fun showLoadingState(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(errorText:String){
        binding.errorImg.visibility = View.VISIBLE
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = errorText


    }

    private fun hideError(){
        binding.errorImg.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

}