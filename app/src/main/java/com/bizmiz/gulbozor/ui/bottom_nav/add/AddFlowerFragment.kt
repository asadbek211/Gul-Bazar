package com.bizmiz.gulbozor.ui.add

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.AnnounceData
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.core.utils.NumberFormat
import com.bizmiz.gulbozor.core.utils.PhoneNumberTextWatcher
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentAddFlowerBinding
import com.bizmiz.gulbozor.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.snackbar.Snackbar
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.Base64.getEncoder


class AddFlowerFragment : Fragment(R.layout.fragment_add_flower) {
    private val addFlowerViewModel: AddFlowerViewModel by viewModel()
    private var potAdd = true
    private var dungAdd = true
    private var regionId:Int = 1
    private var cityId:Int = 1
    private val imageUrlList: ArrayList<Uri> = arrayListOf()
    private var file1:File? = null
    private var file2:File? = null
    private var file3:File? = null
    private var file4:File? = null
    private var file5:File? = null
    private var file6:File? = null
    private var file7:File? = null
    private var file8:File? = null
    private val sectionList: List<String> = listOf("so'm", "$")
    private val flowerList: List<String> = listOf("Ramashka", "Atirgul","Qandaydir gul")
    private lateinit var binding: FragmentAddFlowerBinding

    @OptIn(DelicateCoroutinesApi::class)
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
        addFlowerViewModel.getRegion()
        requireActivity().window.setFlags(
            0,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        if (!::binding.isInitialized) {
            binding = FragmentAddFlowerBinding.bind(view)
        }
        if (
            !isHasPermission(Manifest.permission.CAMERA) || !isHasPermission(
                READ_EXTERNAL_STORAGE
            ) ||
                    !isHasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            askPermission(
                arrayOf(
                    Manifest.permission.CAMERA,
                    READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1003
            )
        }
        setAdapter(binding.spPriceType,sectionList)
        setAdapter(binding.spFlowerType,flowerList)
        binding.etNumber.addTextChangedListener(
            PhoneNumberTextWatcher(
                binding.etNumber
            )
        )
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
            when (imageUrlList.size) {
                1 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                }
                2 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                }
                3 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                    file3 = uriToImageFile(imageUrlList[2])
                }
                4 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                    file3 = uriToImageFile(imageUrlList[2])
                    file4 = uriToImageFile(imageUrlList[3])
                }
                5 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                    file3 = uriToImageFile(imageUrlList[2])
                    file4 = uriToImageFile(imageUrlList[3])
                    file5 = uriToImageFile(imageUrlList[4])
                }
                6 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                    file3 = uriToImageFile(imageUrlList[2])
                    file4 = uriToImageFile(imageUrlList[3])
                    file5 = uriToImageFile(imageUrlList[4])
                    file6 = uriToImageFile(imageUrlList[5])
                }
                7 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                    file3 = uriToImageFile(imageUrlList[2])
                    file4 = uriToImageFile(imageUrlList[3])
                    file5 = uriToImageFile(imageUrlList[4])
                    file6 = uriToImageFile(imageUrlList[5])
                    file7 = uriToImageFile(imageUrlList[6])
                }
                8 -> {
                    file1 = uriToImageFile(imageUrlList[0])
                    file2 = uriToImageFile(imageUrlList[1])
                    file3 = uriToImageFile(imageUrlList[2])
                    file4 = uriToImageFile(imageUrlList[3])
                    file5 = uriToImageFile(imageUrlList[4])
                    file6 = uriToImageFile(imageUrlList[5])
                    file7 = uriToImageFile(imageUrlList[6])
                    file8 = uriToImageFile(imageUrlList[7])
                }
            }
             CoroutineScope(Dispatchers.IO).launch {
                 val compressImage1 = compressImage(file1)
                 val compressImage2 = compressImage(file2)
                 val compressImage3 = compressImage(file3)
                 val compressImage4 = compressImage(file4)
                 val compressImage5 = compressImage(file5)
                 val compressImage6 = compressImage(file6)
                 val compressImage7 = compressImage(file7)
                 val compressImage8 = compressImage(file8)
                 withContext(Dispatchers.Main){
                     addFlowerViewModel.addFlower(
                         compressImage1?.let { it1 -> createFormData(it1, "image1") },
                         compressImage2?.let { it1 -> createFormData(it1, "image2") },
                         compressImage3?.let { it1 -> createFormData(it1, "image3") },
                         compressImage4?.let { it1 -> createFormData(it1, "image4") },
                         compressImage5?.let { it1 -> createFormData(it1, "image5") },
                         compressImage6?.let { it1 -> createFormData(it1, "image6") },
                         compressImage7?.let { it1 -> createFormData(it1, "image7") },
                         compressImage8?.let { it1 -> createFormData(it1, "image8") }
                     )
                 }
             }
        }
//            val params = "m=62ada9786a1a1d8d2dee422b;ac.order_id=2;a=50000"
//            val data = params.toByteArray(StandardCharsets.UTF_8)
//            val base64 = Base64.encodeToString(data,Base64.DEFAULT)
//            val payMeUrl = "https://checkout.test.paycom.uz/$base64"
//            Log.d("urlPay",payMeUrl)
//            addFlowerViewModel.getPayment(payMeUrl)
        }
//        binding.btnViewAnnouncement.setOnClickListener {
//            if (checkAnnounce()) {
//                val bundle = bundleOf(
//                    "flowerData" to AnnounceData(
//                        active = true,
//                        allowed = true,
//                        description = binding.etDescription.text.toString().trim(),
//                        diameter = binding.etWidth.text.toString().trim().toInt(),
//                        height = binding.etHeight.text.toString().trim().toInt(),
//                        price = binding.etPrice.text.trim().toString().replace("\\s".toRegex(), "")
//                            .toInt(),
//                        title = binding.etTitle.text.toString().trim(),
//                        weight = 23,
//                        withFertilizer = dungAdd,
//                        withPot = potAdd
//                    )
//                )
//                val navController =
//                    Navigation.findNavController(
//                        requireActivity(),
//                        R.id.mainContainer
//                    )
//                navController.navigate(R.id.action_bottomNavFragment_to_detailsFragment, bundle)
//            }
//        }
        imageResultObserve()
        binding.image1.onClick {
            pickImage()
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
        binding.spVilList.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                addFlowerViewModel.getCity(position+1)
                regionId = position+1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spTumanList.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cityId = position+1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        addFlowerViewModel.getCity((binding.spVilList.selectedItemId+1).toInt())
        binding.titleLayout.isHintEnabled = false
        binding.priceLayout.isHintEnabled = false
        binding.descriptionLayout.isHintEnabled = false
        regionResultObserve()
        cityResultObserve()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
        val imageViewList:List<ImageView> = listOf(
            binding.image1,
            binding.image2,
            binding.image3,
            binding.image4,
            binding.image5,
            binding.image6,
            binding.image7,
            binding.image8
        )
        when (resultCode) {
            Activity.RESULT_OK -> {
                imageUrlList.clear()
                binding.image1.setImageResource(R.drawable.add)
                binding.image2.setImageResource(R.drawable.ic_group_1)
                binding.image3.setImageResource(R.drawable.ic_group_1)
                binding.image4.setImageResource(R.drawable.ic_group_1)
                binding.image5.setImageResource(R.drawable.ic_group_1)
                binding.image6.setImageResource(R.drawable.ic_group_1)
                binding.image7.setImageResource(R.drawable.ic_group_1)
                binding.image8.setImageResource(R.drawable.ic_group_1)
                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        if (imageUrlList.size<8){
                            val imageUrl = data.clipData!!.getItemAt(i).uri
                            imageUrlList.add(imageUrl)
                            val thumbnailRequest = Glide
                                .with(requireContext())
                                .load(R.drawable.ic_group_1)
                            Glide.with(requireContext())
                                .load(imageUrl)
                                .transition(withCrossFade())
                                .thumbnail(thumbnailRequest)
                                .into(imageViewList[i])
                        }
                    }
                    if (count >8)
                        Toast.makeText(requireActivity(), "Max 8 photos!", Toast.LENGTH_SHORT).show()
                } else {
                    val imageUrl = data.data
                    if (imageUrl != null) {
                        imageUrlList.add(imageUrl)
                    }
                    val thumbnailRequest = Glide
                        .with(requireContext())
                        .load(R.drawable.ic_group_1)
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .transition(withCrossFade())
                        .thumbnail(thumbnailRequest)
                        .into(imageViewList[0])
                }
            }
        }
    }
    }

    private fun imageResultObserve() {
        addFlowerViewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    val img1 = it.data?.image1
                    val img2 = it.data?.image2
                    val img3 = it.data?.image3
                    val img4 = it.data?.image4
                    val img5 = it.data?.image5
                    val img6 = it.data?.image6
                    val img7 = it.data?.image7
                    val img8 = it.data?.image8
                    addFlowerViewModel.setAnnounce(
                        AnnounceData(
                            active = true,
                            allowed = true,
                            description = binding.etDescription.text.toString().trim(),
                            diameter = binding.etWidth.text.toString().trim().toInt(),
                            height = binding.etHeight.text.toString().trim().toInt(),
                            image1 = img1,
                            image2 = img2,
                            image3 = img3,
                            image4 = img4,
                            image5 = img5,
                            image6 = img6,
                            image7 = img7,
                            image8 = img8,
                            price = binding.etPrice.text.trim().toString()
                                .replace("\\s".toRegex(), "").toLong(),
                            title = binding.etTitle.text.toString().trim(),
                            weight = 23,
                            withFertilizer = dungAdd,
                            withPot = potAdd,
                            categoryId = 1,
                            sellerId = 1,
                            shopId = 1,
                            flowerType = 1,
                            cityId = cityId,
                            regionId = regionId,
                            myAnnounce = true,
                            topNumber = 0,
                            phoneNumber =
                                "+998${binding.etNumber.text.trim().toString()
                                    .replace("\\s".toRegex(), "")
                            }",
                            seller = true
                        )
                    )
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun announceResultObserve() {
        addFlowerViewModel.resultAnnounce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
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
    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1000
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }
    private fun regionResultObserve() {
        addFlowerViewModel.regionList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    it.data?.let { it1 -> setAdapter(binding.spVilList, it1) }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun cityResultObserve() {
        addFlowerViewModel.cityData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    it.data?.let { it1 -> setAdapter(binding.spTumanList, it1) }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun checkAnnounce(): Boolean {
        return when {
            binding.etTitle.text.isEmpty() -> {
                binding.titleLayout.error = "Sarlavha kiriting"
                binding.etTitle.showSoftKeyboard()
                false
            }
            imageUrlList.isEmpty() -> {
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
            binding.etNumber.text.isEmpty() -> {
                Toast.makeText(requireActivity(), "Telefon raqam kiriting", Toast.LENGTH_SHORT).show()
                binding.etNumber.showSoftKeyboard()
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pick image after request permission success
                    pickImage()
                }
            }
        }
    }
    private fun pickImage() {
        if (ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }
    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = (activity as MainActivity).contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }
    private fun createFormData(file: File,imgName:String): MultipartBody.Part {
        val requestFile: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            file
        )
        return MultipartBody.Part.createFormData(imgName, file.name, requestFile)
    }
    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun compressImage(file:File?): File? {
            val compressedImageFile =
                file?.let { it1 ->
                    Compressor.compress(requireContext(), it1){
                        resolution(1280, 720)
                        quality(80)
                        format(Bitmap.CompressFormat.WEBP)
                        size(2_097_152)
                    }
                }
            return compressedImageFile
    }
    private fun setAdapter(sp:Spinner,list:List<String>){
        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = adapter
    }
}