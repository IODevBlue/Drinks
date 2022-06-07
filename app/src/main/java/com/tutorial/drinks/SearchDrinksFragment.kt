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
import com.tutorial.drinks.databinding.FragmentSearchDrinksBinding
import com.tutorial.drinks.network.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchDrinksFragment : Fragment() {

    private var _binding:FragmentSearchDrinksBinding? = null
    private val binding get() = _binding!!
    private val viewModel:DrinksViewModel by activityViewModels()
    private var job:Job? = null
    private val searchAdapter:DrinksAdapter by lazy { DrinksAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchDrinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchDrink.isActivated = false
        setAdapter()

        binding.searchDrink.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.searchDrink.isActivated = true
                job?.cancel()
                job = MainScope().launch {
                    delay(1000L)
                    if (newText!!.isNotBlank()) {
                        newText.let { viewModel.searchDrinks(it) }
                    }
                }
                return true
            }
        })
        searchAdapter.adapterClickListener {
            val navigate = SearchDrinksFragmentDirections.actionSearchDrinksFragmentToDrinkDetailsFragment(it.idDrink)
            findNavController().navigate(navigate)
        }
    }

    private fun setAdapter(){
        binding.allDrinksRV.adapter = searchAdapter
        viewModel.searchDrinks.observe(viewLifecycleOwner){resource->
            when(resource){
                is Resource.Successful->{
                    binding.emptyStateTv.isVisible = false
                    hideError()
                    hideLoadingState()
                    searchAdapter.submitList(resource.data?.drinks)
                }
                is Resource.Failure->{
                    binding.emptyStateTv.isVisible = false
                    showError(resource.msg.toString())
                    hideLoadingState()
                }
                is Resource.Loading->{
                    binding.emptyStateTv.isVisible = false
                    hideError()
                    showLoadingState()
                }
                is Resource.Empty->{
                    hideError()
                    hideLoadingState()
                    binding.emptyStateTv.isVisible = true
                }
//                else->Unit
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