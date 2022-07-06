package com.bizmiz.gulbozor.ui.bottom_nav.home.details.fetilizers_details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentFetilizersDetailsBinding
import com.bizmiz.gulbozor.ui.bottom_nav.payment.PaymentActivity
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class FetilizersDetailsFragment : Fragment(R.layout.fragment_fetilizers_details) {
    private var isFavourite = false
    private var desId: Int? = null
    private lateinit var flowerData: AnnounceResponseData
    private var flowerUrlList: ArrayList<String> = arrayListOf()
    private val binding by viewBinding { FragmentFetilizersDetailsBinding.bind(it) }
    private val fetilizersDetailsViewModel: FetilizersDetailsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        desId = requireArguments().getInt("desId")
        flowerData = requireArguments().get("flowerData") as AnnounceResponseData
        flowerData.image1?.let { flowerUrlList.add(it) }
        flowerData.image2?.let { flowerUrlList.add(it) }
        flowerData.image3?.let { flowerUrlList.add(it) }
        flowerData.image4?.let { flowerUrlList.add(it) }
        flowerData.image5?.let { flowerUrlList.add(it) }
        flowerData.image6?.let { flowerUrlList.add(it) }
        flowerData.image7?.let { flowerUrlList.add(it) }
        flowerData.image8?.let { flowerUrlList.add(it) }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        flowerData.categoryId?.let { fetilizersDetailsViewModel.getFlowerType(it) }
        binding.carouselView.setImageListener { position, imageView ->
            Glide.with(imageView).load(flowerUrlList[position].toUri())
                .into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setPadding(0, 0, 0, 110)
        }
        binding.carouselView.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.carouselView.pageCount = flowerUrlList.size
        binding.ivBack.setOnClickListener {
            if (desId == 1) {
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.addContainer)
                navController.popBackStack()
            } else {
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.mainContainer)
                navController.popBackStack()
            }
        }
        binding.ivFavourite.setOnClickListener {
            if (isFavourite) {
                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                isFavourite = false
            } else {
                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_on_purple)
                isFavourite = true
            }
        }
        binding.btnPhone.setOnClickListener {
            val phone = flowerData.phoneNumber.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
        binding.btnAds.setOnClickListener {
            val intent = Intent(requireActivity(), PaymentActivity::class.java)
            intent.putExtra("flowerData", flowerData)
            startActivity(intent)
        }
        binding.btnEdit.setOnClickListener {
            val bundle = bundleOf(
                "data" to flowerData
            )
            val navController =
                Navigation.findNavController(requireActivity(), R.id.mainContainer)
            navController.navigate(R.id.action_fetilizersDetails_to_editFertilizers, bundle)
        }
        binding.flowerTitle.text = flowerData.title
        binding.tvDescription.text = flowerData.description
        val df = DecimalFormat("#,###.##")
        val number = df.format(flowerData.price)
        binding.flowerPrice.text = number.toString()
        binding.flowerWidth.text = "${flowerData.weight} kg"
        flowerTypeObserve()
        return binding.root
    }

    private fun flowerTypeObserve() {
        fetilizersDetailsViewModel.flowerType.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.fetilizerType.text = it.data?.name
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}