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
import com.bizmiz.gulbozor.databinding.FragmentOneCategoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OneTypeOfCategory : Fragment() {

    val args: OneTypeOfCategoryArgs by navArgs()
    private val viewModel: OneTypeOfCatVM by viewModel()

    private var _binding: FragmentOneCategoryBinding? = null
    private val binding get() = _binding!!

    private val categoryAdapter = OneTypeAdapterCategory()

    private val parentId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getParentCatByID(parentId)
        viewModel.getAnnounce()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneCategoryBinding.inflate(inflater, container, false)
        return binding.root
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

        announceObserve()
        if (args.onBack == "home") {
            onBackHomePressed()
        } else if (args.onBack == "category") {
            onBackCategoryPressed()
        }
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

    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.bizmiz.gulbozor.R.color.gray_main)
    }

    private fun onBackHomePressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.one_to_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun onBackCategoryPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.onBack_to_category)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

}