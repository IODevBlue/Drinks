package com.tutorial.drinks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tutorial.drinks.arch.DrinksViewModel
import com.tutorial.drinks.databinding.FragmentDrinksByCategoryBinding
import com.tutorial.drinks.network.Resource

class DrinksByCategoryFragment : Fragment() {

    private var _binding:FragmentDrinksByCategoryBinding? = null
    private val binding get() = _binding!!
    private val args:DrinksByCategoryFragmentArgs by navArgs()
    private val drinksByCategoryAdapter:DrinksAdapter by lazy { DrinksAdapter() }
    private val viewModel:DrinksViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDrinksByCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDrinksByCategory(args.categoryId)
        setAdapter()

        drinksByCategoryAdapter.adapterClickListener { drink->
            val navigate = DrinksByCategoryFragmentDirections.actionDrinksByCategoryFragmentToDrinkDetailsFragment(drink.idDrink)
            findNavController().navigate(navigate)
        }
    }

    private fun setAdapter(){
        binding.drinksByCategoryRV.adapter= drinksByCategoryAdapter
        viewModel.drinksByCategory.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Successful->{
                    binding.emptyStateTv.isVisible = false
                    hideError()
                    hideLoadingState()
                    response.data?.let {
                        drinksByCategoryAdapter.submitList(it.drinks)
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