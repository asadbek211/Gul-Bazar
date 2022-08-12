package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentShopsBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentShops : Fragment() {

    private val slideModels: ArrayList<SlideModel> = ArrayList()

    val args: FragmentShopsArgs by navArgs()
    private var _binding: FragmentShopsBinding? = null
    val binding get() = _binding!!

    private val shopsViewModel: ShopsViewModel by viewModel()

    private var shopAdapter: ShopsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shopsViewModel.getShopsList()// this is list of adapter
        shopsViewModel.getReklamaImages(3)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basic()
        setListeners()
        announceObserve()// live data
    }

    private fun setListeners() {
        shopAdapter?.setOnItemClickListener(object : ShopsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = FragmentShopsDirections.shopsToShop((position).toString())
                Navigation.findNavController(view!!).navigate(action)
            }
        })
        binding.backPressed.setOnClickListener(View.OnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_activity_main
                )
            navController.popBackStack()
        })
    }

    private fun basic() {
        shopAdapter = ShopsAdapter()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        binding.shopsRecycler.adapter = shopAdapter
        binding.shopsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun announceObserve() {
        shopsViewModel.shops.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    //shopAdapter.clearAdapter()
                    shopAdapter?.addShopListData(it.data!!)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
            shopsViewModel.getReklamaId.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
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
                    }
                }
            })
        })

    }


}