package com.bizmiz.gulbozor.ui.bottom_nav.home.details.buket_details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentBuketDetailsBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class BuketDetailsFragment : Fragment() {
    private var isFavourite = false
    private lateinit var flowerData: AnnounceResponseData
    private var flowerUrlList:ArrayList<String> = arrayListOf()
    private lateinit var binding: FragmentBuketDetailsBinding
    private val buketDetailsViewModel:BuketDetailsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        binding = FragmentBuketDetailsBinding.inflate(inflater, container, false)
        flowerData.categoryId?.let { buketDetailsViewModel.getFlowerType(it) }
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
            val navController =
                Navigation.findNavController(requireActivity(), R.id.mainContainer)
            navController.popBackStack()
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
        binding.flowerTitle.text = flowerData.title
        binding.tvDescription.text = flowerData.description
        val df = DecimalFormat("#,###.##")
        val number = df.format(flowerData.price)
        binding.flowerPrice.text = number.toString()
        flowerTypeObserve()
        return binding.root
    }
    private fun flowerTypeObserve() {
        buketDetailsViewModel.flowerType.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.flowerType.text = it.data?.name
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}