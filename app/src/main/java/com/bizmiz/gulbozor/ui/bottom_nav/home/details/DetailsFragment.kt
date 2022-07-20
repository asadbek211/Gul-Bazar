package com.bizmiz.gulbozor.ui.bottom_nav.home.details

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.AnnounceData
import com.bizmiz.gulbozor.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class DetailsFragment : Fragment() {
    private var isFavourite = false
    private lateinit var flowerData: AnnounceData
    private var flowerUrlList:ArrayList<String> = arrayListOf()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var imageList: ArrayList<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        flowerData = requireArguments().get("flowerData") as AnnounceData
        flowerData.image1?.let { flowerUrlList.add(it) }
        flowerData.image2?.let { flowerUrlList.add(it) }
        flowerData.image3?.let { flowerUrlList.add(it) }
        flowerData.image4?.let { flowerUrlList.add(it) }
        flowerData.image5?.let { flowerUrlList.add(it) }
        flowerData.image6?.let { flowerUrlList.add(it) }
        flowerData.image7?.let { flowerUrlList.add(it) }
        flowerData.image8?.let { flowerUrlList.add(it) }
        imageList =
            arrayListOf(R.drawable.img_7, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = FragmentDetailsBinding.bind(
            inflater.inflate(
                R.layout.fragment_details,
                container,
                false
            )
        )
        onBackPressed()
        binding.carouselView.setImageListener { position, imageView ->
            Glide.with(imageView).load(flowerUrlList[position])
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
        binding.flowerTitle.text = flowerData.title
        binding.tvDescription.text = flowerData.description
        val df = DecimalFormat("#,###.##")
        val number = df.format(flowerData.price)
        binding.flowerPrice.text = number.toString()
        binding.flowerWidth.text = "${flowerData.diameter} sm"
        binding.flowerHeight.text = "${flowerData.height} sm"
        flowerData.withPot?.let { checkPot(binding.potCheck, it) }
        flowerData.withFertilizer?.let { checkPot(binding.dungCheck, it) }
        return binding.root
    }

    private fun checkPot(imageView: ImageView, boolean: Boolean) {
        if (boolean) {
            imageView.setImageResource(R.drawable.ic_check_yes_24)
        } else {
            imageView.setImageResource(R.drawable.ic_check_no_24)
        }
    }

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.details_to_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

}