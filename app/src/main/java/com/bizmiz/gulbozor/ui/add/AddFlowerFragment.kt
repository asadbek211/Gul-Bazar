package com.bizmiz.gulbozor.ui.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentAddFlowerBinding
import com.bizmiz.gulbozor.ui.Utils.NumberFormat
import com.bizmiz.gulbozor.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.IntentUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddFlowerFragment : Fragment() {
    private val addFlowerViewModel: AddFlowerViewModel by viewModel()
    private var potAdd = true
    private var dungAdd = true
    private var imageUrl: Uri? = null
    private var img1ImageUri: Uri? = null
    private var img2ImageUri: Uri? = null
    private var img3ImageUri: Uri? = null
    private lateinit var partBody: MultipartBody.Part
    private val sectionList: ArrayList<String> = arrayListOf("so'm", "$")
    private lateinit var binding: FragmentAddFlowerBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        binding = FragmentAddFlowerBinding.inflate(inflater, container, false)
        if (!isHasPermission(Manifest.permission.CAMERA) || !isHasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
            !isHasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            askPermission(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1003
            )
        }
        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, sectionList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPriceType.adapter = adapter
        binding.etPrice.addTextChangedListener(NumberFormat(binding.etPrice))
        binding.widthUp.setOnClickListener {
            if (binding.etWidth.text.isNotEmpty()) {
                val price = binding.etWidth.text.toString().replace(" ", "").toInt() + 1
                binding.etWidth.setText("$price")
            } else {
                binding.etWidth.setText("1")
            }
        }
        binding.widthDown.setOnClickListener {
            if (binding.etWidth.text.isNotEmpty()) {
                if (binding.etWidth.text.toString().trim().toInt() >= 1) {
                    val price = binding.etWidth.text.toString().replace(" ", "").toInt() - 1
                    binding.etWidth.setText("$price")
                }
            }
        }
        binding.heightUp.setOnClickListener {
            if (binding.etHeight.text.isNotEmpty()) {
                val price = binding.etHeight.text.toString().replace(" ", "").toInt() + 1
                binding.etHeight.setText("$price")
            } else {
                binding.etHeight.setText("1")
            }
        }
        binding.heightDown.setOnClickListener {
            if (binding.etHeight.text.isNotEmpty()) {
                if (binding.etHeight.text.toString().trim().toInt() >= 1) {
                    val price = binding.etHeight.text.toString().replace(" ", "").toInt() - 1
                    binding.etHeight.setText("$price")
                }
            }
        }
        binding.potButtonNo.setOnClickListener {
            binding.potButtonNo.setBackgroundResource(R.drawable.button_shape)
            binding.potButtonNo.setTextColor(resources.getColor(R.color.white))
            binding.potButtonYes.setBackgroundResource(R.drawable.button_shape_stroke)
            binding.potButtonYes.setTextColor(resources.getColor(R.color.purple_500))
            potAdd = false
        }
        binding.potButtonYes.setOnClickListener {
            binding.potButtonYes.setBackgroundResource(R.drawable.button_shape)
            binding.potButtonYes.setTextColor(resources.getColor(R.color.white))
            binding.potButtonNo.setBackgroundResource(R.drawable.button_shape_stroke)
            binding.potButtonNo.setTextColor(resources.getColor(R.color.purple_500))
            potAdd = true
        }
        binding.dungButtonNo.setOnClickListener {
            binding.dungButtonNo.setBackgroundResource(R.drawable.button_shape)
            binding.dungButtonNo.setTextColor(resources.getColor(R.color.white))
            binding.dungButtonYes.setBackgroundResource(R.drawable.button_shape_stroke)
            binding.dungButtonYes.setTextColor(resources.getColor(R.color.purple_500))
            dungAdd = false
        }
        binding.dungButtonYes.setOnClickListener {
            binding.dungButtonYes.setBackgroundResource(R.drawable.button_shape)
            binding.dungButtonYes.setTextColor(resources.getColor(R.color.white))
            binding.dungButtonNo.setBackgroundResource(R.drawable.button_shape_stroke)
            binding.dungButtonNo.setTextColor(resources.getColor(R.color.purple_500))
            dungAdd = true
        }
        binding.btnAnnouncement.setOnClickListener {
            val file = File(img1ImageUri?.path!!)
            partBody = MultipartBody.Part.createFormData(
                "file",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull()
            ))
            addFlowerViewModel.addFlower(partBody)
        }
        imageResultObserve()
        binding.image1.onClick {
            imageUploadDialog(IMG1)
            showImage(this)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri? = data?.data!!
                when (requestCode) {
                    IMG1_GALLERY_REQ_CODE, IMG1_CAMERA_REQ_CODE -> {
                        this.img1ImageUri = uri
                        if (uri != null) {
                            binding.image1.setLocalImage(uri)
                        }

                    }
                    IMG2_GALLERY_REQ_CODE, IMG2_CAMERA_REQ_CODE -> {
                        this.img2ImageUri = uri
                        if (uri != null) {
                            binding.image2.setLocalImage(uri)
                        }
                    }
                    IMG3_GALLERY_REQ_CODE, IMG3_CAMERA_REQ_CODE -> {
                        this.img3ImageUri = uri
                        if (uri != null) {
                            binding.image3.setLocalImage(uri)
                        }
                    }
                }
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun imageResultObserve() {
        addFlowerViewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    Toast.makeText(requireActivity(), "Rasm saqlandi", Toast.LENGTH_SHORT).show()
                    Log.d("resultImage", it.data.toString())
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showImage(view: View) {
        val uri = when (view) {
            binding.image1 -> this.img1ImageUri
            binding.image2 -> this.img2ImageUri
            binding.image3 -> this.img3ImageUri
            else -> null
        }
        uri?.let {
            startActivity(IntentUtils.getUriViewIntent(requireContext(), uri))
        }
    }

    private fun imageUploadDialog(type: Int) {
        val dialog = ImageUploadDialog(this)
        dialog.onGallerySelected {
            when (type) {
                IMG1 -> pickGalleryImage(IMG1_GALLERY_REQ_CODE)
                IMG2 -> pickGalleryImage(IMG2_GALLERY_REQ_CODE)
                IMG3 -> pickGalleryImage(IMG3_GALLERY_REQ_CODE)
            }
        }
        dialog.onCameraSelected {
            when (type) {
                IMG1 -> pickCameraImage(IMG1_CAMERA_REQ_CODE)
                IMG2 -> pickCameraImage(IMG2_CAMERA_REQ_CODE)
                IMG3 -> pickCameraImage(IMG3_CAMERA_REQ_CODE)
            }
        }
    }

    companion object {
        private const val IMG1_GALLERY_REQ_CODE = 102
        private const val IMG1_CAMERA_REQ_CODE = 103
        private const val IMG2_GALLERY_REQ_CODE = 104
        private const val IMG2_CAMERA_REQ_CODE = 105
        private const val IMG3_GALLERY_REQ_CODE = 106
        private const val IMG3_CAMERA_REQ_CODE = 107

        private const val IMG1 = 0
        private const val IMG2 = 1
        private const val IMG3 = 2
    }
}