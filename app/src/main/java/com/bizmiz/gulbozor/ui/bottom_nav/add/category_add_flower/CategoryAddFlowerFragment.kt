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
import com.bizmiz.gulbozor.databinding.FragmentCategoryAddFlowerBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity

class CategoryAddFlowerFragment : Fragment() ,View.OnClickListener{
    private var departmentId:Int? = null
    private var isSeller:Boolean? = null
    private var _binding: FragmentCategoryAddFlowerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isSeller = requireArguments().getBoolean("isSeller")
        _binding = FragmentCategoryAddFlowerBinding.inflate(inflater, container, false)
        instalization()
        return binding.root
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