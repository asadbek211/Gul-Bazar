package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentCategoryBinding
import com.bizmiz.gulbozor.databinding.FragmentOneCategoryBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class OneTypeOfCategory : Fragment(R.layout.fragment_one_category) {
    private val slideModels: ArrayList<SlideModel> = ArrayList()
    val args: OneTypeOfCategoryArgs by navArgs()

    private val viewModel: OneTypeOfCatVM by viewModel()
    private val binding by viewBinding { FragmentOneCategoryBinding.bind(it) }

    private val categoryAdapter = OneTypeAdapterCategory()

    private val parentId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getParentCatByID(parentId)
        viewModel.getAnnounce()
        viewModel.getReklamaImages(4)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryRecyclerView.adapter = categoryAdapter

        binding.categoryU.setOnClickListener(View.OnClickListener {
            //findNavController().navigate(R.id.oneType_to_categories)
        })
        val category = args.categoryName
        binding.oneCatTitle.text = category
        windowStatus()
        onBackPressed()
        announceObserve()

        binding.backPressed.setOnClickListener(View.OnClickListener {
            if (args.onBack == "home") {
                findNavController().navigate(R.id.one_to_home)
            } else if (args.onBack == "category") {
                findNavController().navigate(R.id.onBack_to_category)
            }
        })
    }

    private fun announceObserve() {

        viewModel.parentCategory.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {

                }
            }
        })
        viewModel.announce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    categoryAdapter.clearAdapter()
                    categoryAdapter.categoryList = (it.data as ArrayList<AnnounceResponseData>?)!!
                    /*Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                        .show()*/

                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getReklamaId.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    slideModels.add(SlideModel(it.data!!.`object`.image1, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image2, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image3, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image4, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image5, ScaleTypes.FIT))
                    binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.bizmiz.gulbozor.R.color.white)
    }

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (args.onBack == "home") {
                    findNavController().navigate(R.id.one_to_home)
                } else if (args.onBack == "category") {
                    findNavController().navigate(R.id.onBack_to_category)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

}