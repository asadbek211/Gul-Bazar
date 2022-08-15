package com.bizmiz.gulbozor.ui.bottom_nav.profile.shop

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.models.AnnounceRequestData
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.user.edit_user.UserEditRequest
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.showSoftKeyboard
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentCreateShopBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_buket.AddBuketViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateShopFragment : Fragment(R.layout.fragment_create_shop) {
    private val binding by viewBinding { FragmentCreateShopBinding.bind(it) }
    private val createShopViewModel: CreateShopViewModel by viewModel()
    private var regionIdList:ArrayList<Int> = arrayListOf()
    private var cityIdList:ArrayList<Int> = arrayListOf()
    private var regionId:Int? = null
    private var cityId:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createShopViewModel.getRegion()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progress.setOnClickListener {  }
        binding.btnBack.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.popBackStack()
        }
        binding.spVilList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (regionIdList.isNotEmpty()){
                    regionId = regionIdList[position]
                    createShopViewModel.getCity(regionId!!)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spTumanList.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (cityIdList.isNotEmpty()){
                    cityId = cityIdList[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        regionResultObserve()
        cityResultObserve()
        binding.btnNext.setOnClickListener {
            if (checkShop()){
                binding.progress.visibility = View.VISIBLE
                createShopViewModel.createShop(
                    CreateShopRequest(
                        cityId = cityId,
                        phoneNumber1 = binding.phoneNumber1.text?.trim().toString()
                            .replace("\\s".toRegex(), ""),
                        phoneNumber2 = binding.phoneNumber2.text?.trim().toString()
                            .replace("\\s".toRegex(), ""),
                        regionId= regionId,
                        sellerId = AppCache.getHelper().userId,
                        shopName = binding.etShopName.text?.trim().toString(),
                        streetHouse = binding.etStreet.text?.trim().toString()
                    )
                )
            }
        }
        createShopResultObserve()
        updateShopIdObserve()
    }
    private fun regionResultObserve() {
        val list:ArrayList<String> = arrayListOf()
        createShopViewModel.regionList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    it.data?.forEach {
                        list.add(it.name)
                        regionIdList.add(it.id)
                    }
                    if (regionIdList.isNotEmpty()){
                        regionId = regionIdList[0]
                    }
                    setAdapter(binding.spVilList, list)
                    binding.spVilList.setSelection(0)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun cityResultObserve() {
        val list:ArrayList<String> = arrayListOf()
        createShopViewModel.cityData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    list.clear()
                    cityIdList.clear()
                    it.data?.forEach {
                        list.add(it.name)
                        cityIdList.add(it.id)
                    }
                    if (cityIdList.isNotEmpty()){
                        cityId = cityIdList[0]
                    }
                    setAdapter(binding.spTumanList, list)
                    binding.spTumanList.setSelection(0)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun createShopResultObserve() {
        createShopViewModel.resultShop.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    val phoneNumber = requireArguments().getString("phone_number")
                    val username = requireArguments().getString("username")
                    val surname = requireArguments().getString("surname")
                    if (phoneNumber!=null && username!=null && surname!=null){
                        createShopViewModel.updateShopId(
                            AppCache.getHelper().userId,
                            UserEditRequest(
                                phoneNumber,
                                1,
                                surname,
                                username

                            )
                        )
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun updateShopIdObserve() {
        createShopViewModel.resultShopId.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    val navController =
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.mainContainer
                        )
                    navController.navigate(R.id.action_createShop_to_createShopSuccessFragment)
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun setAdapter(sp: Spinner, list:List<String>){
        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = adapter
    }
    private fun checkShop(): Boolean {
        return when {
            binding.etShopName.text?.isEmpty() == true -> {
                binding.etShopName.error = "Do'kon nomini kiriting"
                binding.etShopName.showSoftKeyboard()
                false
            }
            binding.phoneNumber1.text?.isEmpty() == true -> {
                binding.phoneNumber1.error = "Telefon raqam kiriting"
                binding.phoneNumber1.showSoftKeyboard()
                false
            }
            binding.phoneNumber2.text?.isEmpty() == true -> {
                binding.phoneNumber2.error = "Telefon raqam kiriting"
                binding.phoneNumber2.showSoftKeyboard()
                false
            }
            binding.etStreet.text?.isEmpty() == true -> {
                binding.etStreet.error = "Manzil kiriting"
                binding.etStreet.showSoftKeyboard()
                false
            }
            else -> {
                true
            }
        }
    }
}