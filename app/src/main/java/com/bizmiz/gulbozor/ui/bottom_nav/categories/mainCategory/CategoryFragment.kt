package com.bizmiz.gulbozor.ui.bottom_nav.categories.mainCategory

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentCategoryBinding
import com.bizmiz.gulbozor.ui.bottom_nav.categories.mainCategory.adapters.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private val binding by viewBinding { FragmentCategoryBinding.bind(it) }
    private var expanded1: Boolean = false
    private var expanded2: Boolean = false
    private var expanded3: Boolean = false

    private var justNumber1: Int = 0
    private var justNumber2: Int = 0
    private var justNumber3: Int = 0


    private var categoryNum1: Int = 0
    private var categoryNum2: Int = 0
    private var categoryNum3: Int = 0
    private var categoryNum4: Int = 0
    private var categoryNum5: Int = 0

    private val viewModel: CategoryVM by viewModel()
    private val categoryAdapter1 = CategoryAdapter1()
    private val categoryAdapter2 = CategoryAdapter2()
    private val categoryAdapter3 = CategoryAdapter3()
    private val categoryAdapter4 = CategoryAdapter4()
    private val categoryAdapter5 = CategoryAdapter5()
    private val categoryAdapter6 = CategoryAdapter6()
    private val categoryAdapter7 = CategoryAdapter7()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getByPArentCatId(Constant.BUCKET_CATEGORY_ID)
        viewModel.getByPArentCatId1(Constant.HOUSE_PLANTS_CATEGORY_ID)
        viewModel.getByPArentCatId2(Constant.ARCHA_CATEGORY_ID)
        viewModel.getByPArentCatId3(Constant.FRUIT_TREE_CATEGORY_ID)
        viewModel.getByPArentCatId4(Constant.LANDSCAPE_CATEGORY_ID)
        viewModel.getByPArentCatId5(Constant.DUNG_CATEGORY_ID)
        viewModel.getByPArentCatId6(Constant.POT_CATEGORY_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        windowStatus()
        checking()
        loadListener(view)
        setListeners(view)
    }

    private fun setListeners(view: View) {
        binding.bucketFlowersRecycler.adapter = categoryAdapter1
        binding.bucketFlowersRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.potFlowersRecycler.adapter = categoryAdapter2
        binding.potFlowersRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.fruitTreeRecycler.adapter = categoryAdapter3
        binding.fruitTreeRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.archaTreeRecycler.adapter = categoryAdapter4
        binding.archaTreeRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.landscapeTreeRecycler.adapter = categoryAdapter5
        binding.landscapeTreeRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.potRecycler.adapter = categoryAdapter6
        binding.potRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.dungRecycler.adapter = categoryAdapter7
        binding.dungRecycler.layoutManager = LinearLayoutManager(requireContext())

        categoryAdapter1.setOnItemClickListener(object : CategoryAdapter1.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }

        })
        categoryAdapter2.setOnItemClickListener(object : CategoryAdapter2.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }

        })
        categoryAdapter3.setOnItemClickListener(object : CategoryAdapter3.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }
        })
        categoryAdapter4.setOnItemClickListener(object : CategoryAdapter4.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }
        })

        categoryAdapter5.setOnItemClickListener(object : CategoryAdapter5.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }
        })
        categoryAdapter6.setOnItemClickListener(object : CategoryAdapter6.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }
        })
        categoryAdapter7.setOnItemClickListener(object : CategoryAdapter7.onItemClickListenerCat {
            override fun onItemClick(id: Int, categoryName: String) {
                val action = CategoryFragmentDirections.navCategoryToOne(
                    categoryName,
                    "category",
                    id.toString()
                )
                Navigation.findNavController(view).navigate(action)
            }
        })
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
        flowersParent()
        treesParent()
        dungPotParent()

        binding.hotRoomCat.setOnClickListener(View.OnClickListener {
            val action =
                CategoryFragmentDirections.navCategoryToYouTube("Issiq xona qurish", "category")
            Navigation.findNavController(view).navigate(action)
        })
        binding.takeCareCat.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToYouTube(
                "Parvarishlash darslari",
                "category"
            )
            Navigation.findNavController(view).navigate(action)
        })
        binding.shopsCat.setOnClickListener(View.OnClickListener {
            val action = CategoryFragmentDirections.navCategoryToShop("category")
            Navigation.findNavController(view).navigate(action)
        })
    }

    private fun dungPotParent() {
        binding.potCatTxt.setOnClickListener(View.OnClickListener {
            //viewModel.getByPArentCatId5(9)
            binding.potRecycler.visibility = View.VISIBLE
            binding.dungRecycler.visibility = View.GONE
            binding.dungTxt.setTextColor(Color.parseColor("#626262"))
            binding.potCatTxt.setTextColor(Color.parseColor("#00B83F"))
            viewModel.potCategoryID.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    ResourceState.SUCCESS -> {
                        categoryAdapter6.categoryList = it.data!!
                    }
                }
            })
        })
        binding.dungTxt.setOnClickListener(View.OnClickListener {
            /*viewModel.getByPArentCatId6(8)*/
            binding.potRecycler.visibility = View.GONE
            binding.dungRecycler.visibility = View.VISIBLE
            binding.dungTxt.setTextColor(Color.parseColor("#00B83F"))
            binding.potCatTxt.setTextColor(Color.parseColor("#626262"))
            viewModel.dungCategoryID.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    ResourceState.SUCCESS -> {
                        categoryAdapter7.categoryList = it.data!!
                    }
                }
            })
        })
    }

    private fun treesParent() {

        binding.sceneTreeTxt.setOnClickListener(View.OnClickListener {
            categoryNum5 = 1
            if (categoryNum5 == 1) {
                //viewModel.getByPArentCatId4(7)
                binding.fruitTreeRecycler.visibility = View.GONE
                binding.landscapeTreeRecycler.visibility = View.VISIBLE
                binding.archaTreeRecycler.visibility = View.GONE
                binding.fruitTreeTxt.setTextColor(Color.parseColor("#626262"))
                binding.sceneTreeTxt.setTextColor(Color.parseColor("#00B83F"))
                binding.archalarTreeTxt.setTextColor(Color.parseColor("#626262"))
                viewModel.landScapeCategoryID.observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        ResourceState.SUCCESS -> {
                            categoryAdapter5.categoryList = it.data!!
                        }
                        ResourceState.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
        binding.archalarTreeTxt.setOnClickListener(View.OnClickListener {
            categoryNum4 = 1
            if (categoryNum4 == 1) {
                //viewModel.getByPArentCatId3(8)
                binding.fruitTreeRecycler.visibility = View.GONE
                binding.landscapeTreeRecycler.visibility = View.GONE
                binding.archaTreeRecycler.visibility = View.VISIBLE
                binding.fruitTreeTxt.setTextColor(Color.parseColor("#626262"))
                binding.sceneTreeTxt.setTextColor(Color.parseColor("#626262"))
                binding.archalarTreeTxt.setTextColor(Color.parseColor("#00B83F"))
                viewModel.archaCategoryID.observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        ResourceState.SUCCESS -> {
                            categoryAdapter4.categoryList = it.data!!
                        }
                        ResourceState.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
        binding.fruitTreeTxt.setOnClickListener(View.OnClickListener {
            categoryNum3 = 1
            if (categoryNum3 == 1) {
                binding.fruitTreeRecycler.visibility = View.VISIBLE
                binding.landscapeTreeRecycler.visibility = View.GONE
                binding.archaTreeRecycler.visibility = View.GONE
                binding.fruitTreeTxt.setTextColor(Color.parseColor("#00B83F"))
                binding.sceneTreeTxt.setTextColor(Color.parseColor("#626262"))
                binding.archalarTreeTxt.setTextColor(Color.parseColor("#626262"))
                viewModel.fruitCategoryID.observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        ResourceState.SUCCESS -> {
                            categoryAdapter3.categoryList = it.data!!
                        }
                        ResourceState.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
    }

    private fun flowersParent() {
        binding.bucketFlowersTxt.setOnClickListener(View.OnClickListener {
            categoryNum1 = 1
            if (categoryNum1 == 1) {
                //viewModel.getByPArentCatId(4)
                binding.potFlowersRecycler.visibility = View.GONE
                binding.bucketFlowersRecycler.visibility = View.VISIBLE
                binding.bucketFlowersTxt.setTextColor(Color.parseColor("#00B83F"))
                binding.potFlowersCatTxt.setTextColor(Color.parseColor("#626262"))
                viewModel.childCat.observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        ResourceState.SUCCESS -> {
                            categoryAdapter1.categoryList = it.data!! as ArrayList<ByParentIDItem>
                        }
                        ResourceState.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
        binding.potFlowersCatTxt.setOnClickListener(View.OnClickListener {
            categoryNum2 = 1
            if (categoryNum2 == 1) {
                //viewModel.getByPArentCatId1(5)
                binding.potFlowersRecycler.visibility = View.VISIBLE
                binding.bucketFlowersRecycler.visibility = View.GONE
                binding.potFlowersCatTxt.setTextColor(Color.parseColor("#00B83F"))
                binding.bucketFlowersTxt.setTextColor(Color.parseColor("#626262"))
                viewModel.childCat1.observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        ResourceState.SUCCESS -> {
                            categoryAdapter2.categoryList = it.data!! as ArrayList<ByParentIDItem>
                        }
                        ResourceState.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.gray_main)
    }

}