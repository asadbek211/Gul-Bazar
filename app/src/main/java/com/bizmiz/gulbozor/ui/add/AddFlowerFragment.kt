package com.bizmiz.gulbozor.ui.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.FlowerListResponse
import com.bizmiz.gulbozor.core.utils.*
import com.bizmiz.gulbozor.databinding.FragmentAddFlowerBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.IntentUtils
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddFlowerFragment : Fragment(R.layout.fragment_add_flower) {
    private val addFlowerViewModel: AddFlowerViewModel by viewModel()
    private var potAdd = true
    private var dungAdd = true
    private lateinit var imagePath:Uri
    private var img1ImageUri: Uri? = null
    private var img2ImageUri: Uri? = null
    private var img3ImageUri: Uri? = null
    private lateinit var partBody: MultipartBody.Part
    private val sectionList: ArrayList<String> = arrayListOf("so'm", "$")
    private lateinit var binding: FragmentAddFlowerBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        requireActivity().window.setFlags(
            0,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        if (!::binding.isInitialized) {
            binding = FragmentAddFlowerBinding.bind(view)
        }
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                !isHasPermission(Manifest.permission.CAMERA) || !isHasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        !isHasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                false
            }
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
        binding.etPrice.addTextChangedListener(
            NumberFormat(
                binding.etPrice
            )
        )
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
        binding.progress.setOnClickListener {}
        binding.btnAnnouncement.setOnClickListener {
            if (checkAnnounce()) {
            binding.progress.visibility = View.VISIBLE
                val file = File(img1ImageUri?.path!!)
            partBody =
                MultipartBody.Part.createFormData(
                    file.name,
                    file.name,
                    file.asRequestBody(
                        "image/png".toMediaTypeOrNull()
                    )
                )
            addFlowerViewModel.addFlower(partBody)
                addFlowerViewModel.setAnnounce(
                    FlowerListResponse(
                        active = true,
                        allowed = true,
                        createAt = System.currentTimeMillis().toString(),
                        description = binding.etDescription.text.toString().trim(),
                        diameter = binding.etWidth.text.toString().trim().toInt(),
                        height = binding.etHeight.text.toString().trim().toInt(),
                        mainAttachId = 23,
                        price = binding.etPrice.text.trim().toString()
                            .replace("\\s".toRegex(), "").toInt(),
                        title = binding.etTitle.text.toString().trim(),
                        weight = 23,
                        withFertilizer = dungAdd,
                        withPot = potAdd
                    )
                )
//            Glide.with(this)
//                .asBitmap()
//                .load("https://gulbazar.herokuapp.com/attachment/getMainAttachmentFromSystem/30.png")
//                .into(object :CustomTarget<Bitmap>(){
//                    override fun onResourceReady(
//                        resource: Bitmap,
//                        transition: Transition<in Bitmap>?
//                    ) {
//                        val fos: FileOutputStream? =
//                            activity?.openFileOutput( "image1", AppCompatActivity.MODE_PRIVATE)
//                        resource.compress(Bitmap.CompressFormat.PNG, 50, fos)
//                        fos?.close()
//                        imagePath = activity?.applicationContext?.getFileStreamPath("image1")?.absolutePath?.toUri()!!
//                        Log.d("imagePath",imagePath.toString())
//                        binding.image2.setImageURI(imagePath)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {
//
//                    }
//
//                })
        }}
        binding.btnViewAnnouncement.setOnClickListener {
            if (checkAnnounce()) {
                val bundle = bundleOf(
                    "flowerData" to FlowerListResponse(
                        active = true,
                        allowed = true,
                        createAt = System.currentTimeMillis().toString(),
                        description = binding.etDescription.text.toString().trim(),
                        diameter = binding.etWidth.text.toString().trim().toInt(),
                        height = binding.etHeight.text.toString().trim().toInt(),
                        mainAttachId = 23,
                        price = binding.etPrice.text.trim().toString().replace("\\s".toRegex(), "")
                            .toInt(),
                        title = binding.etTitle.text.toString().trim(),
                        weight = 23,
                        withFertilizer = dungAdd,
                        withPot = potAdd
                    )
                )
                val navController =
                    Navigation.findNavController(
                        requireActivity(),
                        R.id.mainContainer
                    )
                navController.navigate(R.id.action_bottomNavFragment_to_detailsFragment, bundle)
            }
        }
        imageResultObserve()
        binding.image1.onClick {
            imageUploadDialog(IMG1)
            showImage(this)
        }
        announceResultObserve()
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.etTitle.text.toString().trim().isEmpty()) {
                    binding.titleLayout.isErrorEnabled = true
                    binding.titleLayout.error = "Sarlavha kiriting"
                } else {
                    binding.titleLayout.isErrorEnabled = false
                }
            }

        })
        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.etPrice.text.toString().trim().isEmpty()) {
                    binding.priceLayout.isErrorEnabled = true
                    binding.priceLayout.error = "Narx kiriting"
                } else {
                    binding.priceLayout.isErrorEnabled = false
                }
            }

        })
        binding.etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.etDescription.text.toString().trim().isEmpty()) {
                    binding.descriptionLayout.isErrorEnabled = true
                    binding.descriptionLayout.error = "Qo'shimcha ma'lumot kriting"
                } else {
                    binding.descriptionLayout.isErrorEnabled = false
                }
            }

        })
        binding.titleLayout.isHintEnabled = false
        binding.priceLayout.isHintEnabled = false
        binding.descriptionLayout.isHintEnabled = false
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

                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun announceResultObserve() {
        addFlowerViewModel.resultAnnounce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    Snackbar.make(binding.root, "E'lon saqlandi", Snackbar.LENGTH_SHORT).show()
                    val navController =
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.nav_host_fragment_activity_main
                        )
                    navController.popBackStack()
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

    private fun checkAnnounce(): Boolean {
        return when {
            binding.etTitle.text.isEmpty() -> {
                binding.titleLayout.error = "Sarlavha kiriting"
                binding.etTitle.showSoftKeyboard()
                false
            }
            img1ImageUri == null -> {
                Toast.makeText(requireActivity(), "E'longa rasm qo'shing", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            binding.etPrice.text.isEmpty() -> {
                binding.priceLayout.error = "Narx kiriting"
                binding.etPrice.showSoftKeyboard()
                false
            }
            binding.etWidth.text.isEmpty() -> {
                Toast.makeText(requireActivity(), "Diametr kiriting", Toast.LENGTH_SHORT).show()
                binding.etWidth.showSoftKeyboard()
                false
            }
            binding.etHeight.text.isEmpty() -> {
                Toast.makeText(requireActivity(), "Balandlik kiriting", Toast.LENGTH_SHORT).show()
                binding.etHeight.showSoftKeyboard()
                false
            }
            binding.etDescription.text?.isEmpty() == true -> {
                binding.descriptionLayout.error = "Qo'shimcha ma'lumot kiriting"
                binding.etDescription.showSoftKeyboard()
                false
            }
            else -> {
                true
            }
        }
    }
}