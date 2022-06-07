package com.tutorial.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tutorial.drinks.arch.DrinksViewModel
import com.tutorial.drinks.databinding.FragmentAllDrinksBinding
import com.tutorial.drinks.network.Resource


class AllDrinksFragment : Fragment() {
    private var _binding: FragmentAllDrinksBinding? = null
    private val binding get() = _binding!!
    private val viewModel:DrinksViewModel by activityViewModels()
    private val drinksAdapter:DrinksAdapter by lazy { DrinksAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllDrinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllDrinks(binding.categorySP.selectedItem.toString())
        setAdapter()

        binding.categorySP.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0->viewModel.getAllDrinks("Alcoholic")
                    1->viewModel.getAllDrinks("Non_Alcoholic")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        drinksAdapter.adapterClickListener {drinks->
            val navigate = AllDrinksFragmentDirections.actionAllDrinksFragmentToDrinkDetailsFragment(drinks.idDrink)
            findNavController().navigate(navigate)
        }
    }

    private fun setAdapter(){
        binding.allDrinksRV.adapter = drinksAdapter
        viewModel.allDrinks.observe(viewLifecycleOwner){resource->
            when(resource){
                is Resource.Successful->{
                    hideError()
                    hideLoadingState()
                    drinksAdapter.submitList(resource.data?.drinks)
                }
                is Resource.Failure->{
                    hideLoadingState()
                    showError(resource.msg.toString())
                }
                is Resource.Loading->{
                    hideError()
                    showLoadingState()
                }
                else->Unit
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