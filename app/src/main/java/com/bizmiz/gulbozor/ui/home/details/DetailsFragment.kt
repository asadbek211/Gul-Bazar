package com.bizmiz.gulbozor.ui.home.details

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentDetailsBinding
import com.bizmiz.gulbozor.ui.model.FlowerListResponse
import com.bizmiz.gulbozor.utils.ResourceState
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class DetailsFragment : Fragment() {
    private val detailsViewModel:DetailsViewModel by viewModel()
    private var isFavourite = false
    private var byteArray: ByteArray? = null
    private lateinit var flowerData: FlowerListResponse
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var imageList: ArrayList<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsViewModel.getData(58)
        flowerData = requireArguments().get("flowerData") as FlowerListResponse
        imageList =
            arrayListOf(R.drawable.test0, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
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
        binding.carouselView.setImageListener { position, imageView ->
//            Glide.with(imageView).load(imageList[position])
//                .into(imageView)
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size!!))
//            val d: Drawable = binding.image1.drawable
//            if (d is BitmapDrawable) {
//                d.bitmap.recycle()
//            }
            imageView.setImageResource(imageList[position])
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
        binding.carouselView.pageCount = imageList.size
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
                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_on)
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
        checkPot(binding.potCheck,flowerData.withPot)
        checkPot(binding.dungCheck,flowerData.withFertilizer)
        dataResultObserve()
        return binding.root
    }
    private fun checkPot(imageView: ImageView,boolean: Boolean){
        if(boolean){
            imageView.setImageResource(R.drawable.img_yes)
        }else{
            imageView.setImageResource(R.drawable.img_no)
        }
    }
    private fun dataResultObserve() {
        detailsViewModel.data.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    byteArray = it.data
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}