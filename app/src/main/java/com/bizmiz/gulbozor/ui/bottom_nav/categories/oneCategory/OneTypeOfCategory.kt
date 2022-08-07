package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
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

    private var page: Int = 0

    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.onBack == "home") {
            viewModel.getDepartment(args.categoryId.toInt(), page)
        } else if (args.onBack == "category") {
            viewModel.getByCategoryID(args.categoryId.toInt(), page)
        }
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

        intent()

        binding.backPressed.setOnClickListener(View.OnClickListener {
            if (args.onBack == "home") {
                findNavController().navigate(R.id.one_to_home)
            } else if (args.onBack == "category") {
                findNavController().navigate(R.id.onBack_to_category)
            }
        })
        binding.swipeContainer.setOnRefreshListener {
            if (args.onBack == "home") {
                categoryAdapter.clearAdapter()
                viewModel.getDepartment(args.categoryId.toInt(), 0)
                isLastPage = false
            } else if (args.onBack == "category") {
                categoryAdapter.clearAdapter()
                viewModel.getByCategoryID(args.categoryId.toInt(), 0)
                isLastPage = false
            }
        }
        // TODO: OnBackPressedFromDetails
        binding.scrollNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!isLastPage) {
                    page++
                    binding.progressBarOneCat.visibility = View.VISIBLE
                    if (args.onBack == "home") {
                        viewModel.getDepartment(args.categoryId.toInt(), page)
                        binding.progressBarOneCat.visibility = View.VISIBLE
                    } else if (args.onBack == "category") {
                        viewModel.getByCategoryID(args.categoryId.toInt(), page)
                        binding.progressBarOneCat.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Boshqa elonlar mavjud emas",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBarOneCat.visibility = View.GONE
                }

            }
        })
    }

    private fun intent() {
        categoryAdapter.onClickListener {
            if (it.department != null) {
                val bundle = bundleOf(
                    "flowerData" to it,
                    "desId" to 0,
                )
                destination(it.department, bundle)
            }
        }
    }

    private fun destination(categoryId: Int, bundle: Bundle?) {
        val navController = Navigation.findNavController(
            requireActivity(), R.id.mainContainer
        )
        when (categoryId) {
            1 -> {
                navController.navigate(R.id.home_to_buketDetails, bundle)
            }
            2 -> {
                navController.navigate(R.id.home_to_flowerDetails, bundle)
            }
            3 -> {
                navController.navigate(R.id.home_to_treeDetails, bundle)
            }
            4 -> {
                navController.navigate(R.id.home_to_potDetails, bundle)
            }
            5 -> {
                navController.navigate(R.id.home_to_fetilizersDetails, bundle)
            }
        }
    }

    private fun announceObserve() {
        if (args.onBack == "category") {
            viewModel.parentCategory.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        categoryAdapter.addOneCategoryListData(it.data!!.content)
                        if (it.data.empty) {
                            isLastPage = true
                        }

                    }
                    ResourceState.ERROR -> {
                        binding.swipeContainer.isRefreshing = false
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else if (args.onBack == "home") {
            viewModel.department.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        categoryAdapter.addOneCategoryListData(it.data!!.content)
                        binding.progressBarOneCat.visibility = View.GONE
                        if (it.data.empty) {
                            isLastPage = true
                        }
                    }
                    ResourceState.ERROR -> {
                        binding.swipeContainer.isRefreshing = false
                    }
                }
            })
        }

        viewModel.getReklamaId.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    slideModels.clear()
                    slideModels.add(SlideModel(it.data!!.`object`.image1, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image2, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image3, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image4, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image5, ScaleTypes.FIT))
                    binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.swipeContainer.isRefreshing = false
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