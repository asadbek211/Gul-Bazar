package com.bizmiz.gulbozor.ui.bottom_nav.categories.mainCategory

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentCategoryBinding

class CategoryFragment : androidx.fragment.app.Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private var expanded1: Boolean = false
    private var expanded2: Boolean = false
    private var expanded3: Boolean = false

    private var justNumber1: Int = 0
    private var justNumber2: Int = 0
    private var justNumber3: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        windowStatus()
        checking()
        onBackPressed()

        loadListener(view)
    }

    private fun checking() {

        binding.flowersCatGr.setOnClickListener(View.OnClickListener {
            if (justNumber1 == 0) {
                it.setBackgroundResource(R.drawable.button_bg)
                binding.flowersTxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_up_24)
                binding.flowersChild.visibility = View.VISIBLE
                expanded1 = !expanded1
                justNumber1 = 1

                binding.treeCatGr.setBackgroundResource(R.color.gray_main)
                binding.treeTxt.setTextColor(Color.parseColor("#626262"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.potAndDungCatGr.setBackgroundResource(R.color.gray_main)
                binding.potTxt.setTextColor(Color.parseColor("#626262"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.treeChild.visibility = View.GONE
                binding.potAndDungChild.visibility = View.GONE

                justNumber2 = 0
                justNumber3 = 0
            }
            if (!expanded1) {
                it.setBackgroundResource(R.color.gray_main)
                binding.flowersTxt.setTextColor(Color.parseColor("#626262"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_down_24)
                binding.flowersChild.visibility = View.GONE
                expanded1 = true

            } else {
                it.setBackgroundResource(R.drawable.button_bg)
                binding.flowersTxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_up_24)
                binding.flowersChild.visibility = View.VISIBLE
                expanded1 = !expanded1
                expanded2 = false
                expanded3 = false

                binding.treeCatGr.setBackgroundResource(R.color.gray_main)
                binding.treeTxt.setTextColor(Color.parseColor("#626262"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.potAndDungCatGr.setBackgroundResource(R.color.gray_main)
                binding.potTxt.setTextColor(Color.parseColor("#626262"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.treeChild.visibility = View.GONE
                binding.potAndDungChild.visibility = View.GONE

                justNumber2 = 0
                justNumber3 = 0
            }
        })

        binding.treeCatGr.setOnClickListener(View.OnClickListener {
            if (justNumber2 == 0) {
                it.setBackgroundResource(R.drawable.button_bg)
                binding.treeTxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_up_24)
                binding.treeChild.visibility = View.VISIBLE
                expanded2 = !expanded2
                justNumber2 = 1

                binding.flowersCatGr.setBackgroundResource(R.color.gray_main)
                binding.flowersTxt.setTextColor(Color.parseColor("#626262"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.potAndDungCatGr.setBackgroundResource(R.color.gray_main)
                binding.potTxt.setTextColor(Color.parseColor("#626262"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.flowersChild.visibility = View.GONE
                binding.potAndDungChild.visibility = View.GONE
                justNumber1 = 0
                justNumber3 = 0
            }
            if (!expanded2) {
                it.setBackgroundResource(R.color.gray_main)
                binding.treeTxt.setTextColor(Color.parseColor("#626262"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_down_24)
                binding.treeChild.visibility = View.GONE
                expanded2 = true

            } else {
                it.setBackgroundResource(R.drawable.button_bg)
                binding.treeTxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_up_24)
                binding.treeChild.visibility = View.VISIBLE
                expanded2 = !expanded2

                binding.flowersCatGr.setBackgroundResource(R.color.gray_main)
                binding.flowersTxt.setTextColor(Color.parseColor("#626262"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.potAndDungCatGr.setBackgroundResource(R.color.gray_main)
                binding.potTxt.setTextColor(Color.parseColor("#626262"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.flowersChild.visibility = View.GONE
                binding.potAndDungChild.visibility = View.GONE

                expanded1 = false
                expanded3 = false
                justNumber1 = 0
                justNumber3 = 0
            }

        })

        binding.potAndDungCatGr.setOnClickListener(View.OnClickListener {
            if (justNumber3 == 0) {
                it.setBackgroundResource(R.drawable.button_bg)
                binding.potTxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_up_24)
                binding.potAndDungChild.visibility = View.VISIBLE
                expanded3 = !expanded3
                justNumber3 = 1


                binding.flowersCatGr.setBackgroundResource(R.color.gray_main)
                binding.flowersTxt.setTextColor(Color.parseColor("#626262"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.treeCatGr.setBackgroundResource(R.color.gray_main)
                binding.treeTxt.setTextColor(Color.parseColor("#626262"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.flowersChild.visibility = View.GONE
                binding.treeChild.visibility = View.GONE
                justNumber1 = 0
                justNumber2 = 0
            }
            if (!expanded3) {
                it.setBackgroundResource(R.color.gray_main)
                binding.potTxt.setTextColor(Color.parseColor("#626262"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_down_24)
                binding.potAndDungChild.visibility = View.GONE
                expanded3 = true
            } else {
                it.setBackgroundResource(R.drawable.button_bg)
                binding.potTxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.potArrow.setImageResource(R.drawable.ic_arrow_up_24)
                binding.potAndDungChild.visibility = View.VISIBLE
                expanded3 = !expanded3
                //justNumber3 = 1


                binding.flowersCatGr.setBackgroundResource(R.color.gray_main)
                binding.flowersTxt.setTextColor(Color.parseColor("#626262"))
                binding.flowersArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.treeCatGr.setBackgroundResource(R.color.gray_main)
                binding.treeTxt.setTextColor(Color.parseColor("#626262"))
                binding.treeArrow.setImageResource(R.drawable.ic_arrow_down_24)

                binding.flowersChild.visibility = View.GONE
                binding.treeChild.visibility = View.GONE

                expanded1 = false
                expanded2 = false
                justNumber1 = 0
                justNumber2 = 0
            }
        })

    }
    private fun loadListener(view: View) {
        binding.bucketFlowersTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("Buket gullar", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.homemadeFlowersCatTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("Xonaki gullar", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.potFlowersCatTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("Tuvakli gullar", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.archalarTreeTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("Archalar", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.fruitTreeTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("Mavali daraxtlar", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.sceneTreeTxt.setOnClickListener(View.OnClickListener {
            val action =
                CategoryFragmentDirections.navCategoryToOne("Manzarali daraxtlar", "category")
            Navigation.findNavController(view).navigate(action)
        })

        binding.potCatTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("Tuvaklar", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.dungTxt.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToOne("O'g'itlar", "category")
            Navigation.findNavController(view).navigate(action)
        })

        binding.hotRoomCat.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToYouTube("Issiq xona qurish")
            Navigation.findNavController(view).navigate(action)
        })
        binding.takeCareCat.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToYouTube("Parvarishlash darslari")
            Navigation.findNavController(view).navigate(action)
        })
        binding.shopsCat.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToShop("category")
            Navigation.findNavController(view).navigate(action)
        })
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.bizmiz.gulbozor.R.color.gray_main)
    }

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.nav_category_to_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }
}