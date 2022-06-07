package com.tutorial.drinks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.tutorial.drinks.arch.DrinksViewModel
import com.tutorial.drinks.databinding.FragmentDrinkDetailsBinding
import com.tutorial.drinks.network.Resource

class DrinkDetailsFragment : Fragment() {
    private var _binding:FragmentDrinkDetailsBinding? = null
    private val binding get() = _binding!!
    val args:DrinkDetailsFragmentArgs by navArgs()
    private val viewModel:DrinksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDrinkDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDrinksById(args.drinksId)

        viewModel.drinksById.observe(viewLifecycleOwner){resource->
            when(resource){
                is Resource.Successful->{
                    hideError()
                    hideLoadingState()
                    binding.apply {
//                        drinkName.text = resource.data!!.drinks[0].strDrink

                        resource.data?.let {
                            drinkName.text = it.drinks[0].strDrink
                        }
                        resource.data?.let {
                            drinkCategoryTv.text = it.drinks[0].strCategory
                        }
                        resource.data?.let {
                            drinkTypeTv.text = it.drinks[0].strAlcoholic
                        }
                        resource.data?.let {
                            dateTv.text = it.drinks[0].dateModified
                        }
                        resource.data?.let {
                            drinkInstrsTV.text = it.drinks[0].strInstructions
                        }
                        resource.data?.let {
                            drinkImg.load(it.drinks[0].strDrinkThumb){
                                crossfade(true)
                                placeholder(R.drawable.loading_img)
                                error(R.drawable.ic_baseline_broken_image_24)
                            }
                        }
                       /* drinkCategoryTv.text = resource.data?.drinks[0].strCategory
                        drinkTypeTv.text = resource.data.drinks[0].strAlcoholic
                        dateTv.text = resource.data.drinks[0].dateModified
                        drinkInstrsTV.text = resource.data.drinks[0].strInstructions
                        drinkImg.load(resource.data.drinks[0].strDrinkThumb){
                            placeholder(R.drawable.loading_img)
                            error(R.drawable.ic_baseline_broken_image_24)
                        }*/
                    }
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