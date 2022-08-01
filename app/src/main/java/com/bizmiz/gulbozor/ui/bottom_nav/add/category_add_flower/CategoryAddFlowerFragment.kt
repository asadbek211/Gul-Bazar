package com.bizmiz.gulbozor.ui.bottom_nav.add.category_add_flower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentAddSuccessBinding
import com.bizmiz.gulbozor.databinding.FragmentCategoryAddFlowerBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity

class CategoryAddFlowerFragment : Fragment(R.layout.fragment_category_add_flower) ,View.OnClickListener{
    private val binding by viewBinding { FragmentCategoryAddFlowerBinding.bind(it) }
    private var departmentId:Int? = null
    private var isSeller:Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isSeller = requireArguments().getBoolean("isSeller")
        instalization()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn1->{
            departmentId = 1
                navigation(R.id.categoryAddFlower_to_addBuket)
            }
            R.id.btn2->{
                departmentId = 2
                navigation(R.id.categoryAddFlower_to_addFlower)
            }
            R.id.btn3->{
                departmentId = 3
                navigation(R.id.categoryAddFlower_to_addTree)
            }
            R.id.btn4->{
                departmentId = 4
                navigation(R.id.categoryAddFlower_to_addPot)
            }
            R.id.btn5->{
                departmentId = 5
                navigation(R.id.categoryAddFlower_to_addFertilizers)
            }
        }
    }
    private fun instalization(){
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
    }
    private fun navigation(destination: Int){
        val bundle = bundleOf(
            "isSeller" to isSeller,
            "department" to departmentId
        )
        val navController =
            Navigation.findNavController(
                requireActivity(),
                R.id.addContainer
            )
        navController.navigate(destination,bundle)
    }
}