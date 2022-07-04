package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.databinding.FragmentShopsBinding

class FragmentShops : Fragment() {

    private var _binding: FragmentShopsBinding? = null
    val binding get() = _binding!!

    private val shopAdapter = ShopsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        loadData()
        binding.shopsRecycler.adapter = shopAdapter
        binding.shopsRecycler.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun loadData() {
        val data = ArrayList<ShopsData>()
        val data1 = ArrayList<ShopsData>()
        val shop1 = ShopsData("Green House")
        val shop2 = ShopsData("Tashkent Flowers")
        val shop3 = ShopsData("Uz flower")
        val shop4 = ShopsData("Yashil Olam")
        val shop6 = ShopsData("G'uncha'")
        val shop7 = ShopsData("Bahor")
        val shop8 = ShopsData("MyHouse")
        val shop9 = ShopsData("Rose")
        val shop10 = ShopsData("Parkent trees")
        val shop11 = ShopsData("Golden flower")
        val shop12 = ShopsData("Last One>)")
        data1.add(shop1)
        data1.add(shop2)
        data1.add(shop3)
        data1.add(shop4)
        data1.add(shop6)
        data1.add(shop7)
        data1.add(shop8)
        data1.add(shop9)
        data1.add(shop10)
        data1.add(shop11)
        data1.add(shop12)
        data.addAll(data1)

        shopAdapter.data = data
    }


}