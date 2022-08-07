package com.bizmiz.gulbozor.ui.bottom_nav.home.details.buket_details

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.networkCheck
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentBuketDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class BuketDetailsFragment : Fragment(R.layout.fragment_buket_details) {
    private var isFavourite = false
    private lateinit var flowerData: AnnounceResponseData
    private var desId: Int? = null
    private var flowerUrlList: ArrayList<String> = arrayListOf()
    private val binding by viewBinding { FragmentBuketDetailsBinding.bind(it) }
    private val buketDetailsViewModel: BuketDetailsViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        if (flowerData.sellerId==AppCache.getHelper().userId){
            binding.btnRemove.visibility = View.VISIBLE
            binding.btnRemove.isEnabled = true
        }
        binding.btnRemove.setOnClickListener {
            if (!networkCheck(requireContext())){
                Toast.makeText(requireActivity(), "Internet aloqasi yo'q", Toast.LENGTH_SHORT).show()
            }else{
                val message = AlertDialog.Builder(requireActivity())
                message.setTitle("Gul Bazar")
                    .setMessage("E'loningizni o'chirmoqchimisiz?")
                    .setCancelable(false)
                    .setPositiveButton("Ha") { message, _ ->
                        flowerData.id?.let { it1 -> buketDetailsViewModel.deleteAnnounceById(it1) }
//                        binding.loading.visibility = View.VISIBLE
                    }.setNegativeButton("Yo'q"){message,_->
                        message.dismiss()
                    }.create().show()
            }
        }
        flowerData.categoryId?.let { buketDetailsViewModel.getFlowerType(it) }
        binding.carouselView.setImageListener { position, imageView ->
            Glide.with(imageView).load(flowerUrlList[position].toUri())
                .error(R.drawable.ic_launcher_background)
                .listener(listener(binding.progressBarItem))
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
//        binding.ivFavourite.setOnClickListener {
//            if (isFavourite) {
//                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                isFavourite = false
//            } else {
//                binding.ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_on_purple)
//                isFavourite = true
//            }
//        }
        binding.btnPhone.setOnClickListener {
            val phone = flowerData.phoneNumber.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
//        binding.btnAds.setOnClickListener {
//            val intent = Intent(requireActivity(), PaymentActivity::class.java)
//            intent.putExtra("flowerData", flowerData)
//            startActivity(intent)
//        }
//        binding.btnEdit.setOnClickListener {
//            val bundle = bundleOf(
//                "data" to flowerData
//            )
//            val navController =
//                Navigation.findNavController(requireActivity(), R.id.mainContainer)
//            navController.navigate(R.id.action_buketDetails_to_editBuket, bundle)
//        }
        binding.flowerTitle.text = flowerData.title
        binding.tvDescription.text = flowerData.description
        val df = DecimalFormat("#,###.##")
        val number = df.format(flowerData.price)
        binding.flowerPrice.text = number.toString()
        flowerTypeObserve()
        deleteAnnounceObserve()
    }

    private fun listener(progressBar: ProgressBar) = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar.isVisible = false
            return false
        }

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
    private fun deleteAnnounceObserve() {
        buketDetailsViewModel.deleteAnnounceResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {

                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}