package com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_pots

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
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.models.AnnounceRequestData
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.NumberFormat
import com.bizmiz.gulbozor.core.utils.PhoneNumberTextWatcher
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentAddFlowerBinding
import com.bizmiz.gulbozor.databinding.FragmentAddPotBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity
import com.bizmiz.gulbozor.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
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


class AddPotFragment : Fragment(R.layout.fragment_add_pot) {

    private val addPotViewModel: AddPotViewModel by viewModel()
    private var regionIdList:ArrayList<Int> = arrayListOf()
    private var cityIdList:ArrayList<Int> = arrayListOf()
    private var regionId:Int? = null
    private var cityId:Int? = null
    private val imageUrlList: ArrayList<Uri> = arrayListOf()
    private var file1:File? = null
    private var file2:File? = null
    private var file3:File? = null
    private var file4:File? = null
    private var file5:File? = null
    private var file6:File? = null
    private var file7:File? = null
    private var file8:File? = null
    private var img1:String? = null
    private var img2:String? = null
    private var img3:String? = null
    private var img4:String? = null
    private var img5:String? = null
    private var img6:String? = null
    private var img7:String? = null
    private var img8:String? = null
    private var imageViewList:List<ImageView> = listOf()
    private val sectionList: List<String> = listOf("so'm")
    private var departmentId:Int? = null
    private var isSeller:Boolean? = null
    private var flowerTypeList:ArrayList<Int> = arrayListOf()
    private var flowerTypeId:Int? = null
    private var spFlowerPosition:Int = 0
    private var spRegionPosition:Int = 0
    private var spCityPosition:Int = 0
    private lateinit var flowerNameList:ArrayList<String>
    private lateinit var binding: FragmentAddPotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPotViewModel.getRegion()
        addPotViewModel.getFlowerType()
        flowerNameList = arrayListOf()
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isSeller = requireArguments().getBoolean("isSeller")
        departmentId = requireArguments().getInt("department")
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
        binding = FragmentAddPotBinding.bind(view)
        if (
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                !isHasPermission(Manifest.permission.CAMERA) || !isHasPermission(
                    READ_EXTERNAL_STORAGE
                ) ||
                        !isHasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                false
            }
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
        imageViewList = listOf(
            binding.image1,
            binding.image2,
            binding.image3,
            binding.image4,
            binding.image5,
            binding.image6,
            binding.image7,
            binding.image8
        )
        if (imageUrlList.isNotEmpty()){
            for (i in 0 until imageUrlList.size){
                val thumbnailRequest = Glide
                    .with(requireContext())
                    .load(R.drawable.ic_group_1)
                Glide.with(requireContext())
                    .load(imageUrlList[i])
                    .transition(withCrossFade())
                    .thumbnail(thumbnailRequest)
                    .into(imageViewList[i])
            }
        }
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
                        addPotViewModel.addFlower(
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

        }
        binding.btnViewAnnouncement.setOnClickListener {
            if (checkAnnounce()) {
                when (imageUrlList.size) {
                    1 -> {
                        img1 = imageUrlList[0].toString()
                    }
                    2 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                    }
                    3 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                        img3 = imageUrlList[2].toString()
                    }
                    4 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                        img3 = imageUrlList[2].toString()
                        img4 = imageUrlList[3].toString()
                    }
                    5 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                        img3 = imageUrlList[2].toString()
                        img4 = imageUrlList[3].toString()
                        img5 = imageUrlList[4].toString()
                    }
                    6 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                        img3 = imageUrlList[2].toString()
                        img4 = imageUrlList[3].toString()
                        img5 = imageUrlList[4].toString()
                        img6 = imageUrlList[5].toString()
                    }
                    7 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                        img3 = imageUrlList[2].toString()
                        img4 = imageUrlList[3].toString()
                        img5 = imageUrlList[4].toString()
                        img6 = imageUrlList[5].toString()
                        img7 = imageUrlList[6].toString()
                    }
                    8 -> {
                        img1 = imageUrlList[0].toString()
                        img2 = imageUrlList[1].toString()
                        img3 = imageUrlList[2].toString()
                        img4 = imageUrlList[3].toString()
                        img5 = imageUrlList[4].toString()
                        img6 = imageUrlList[5].toString()
                        img7 = imageUrlList[6].toString()
                        img8 = imageUrlList[7].toString()
                    }
                }
                val bundle = bundleOf(
                    "flowerData" to AnnounceResponseData(
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
                            .replace("\\s".toRegex(), "").replace(",", "").replace(".", "").toLong(),
                        title = binding.etTitle.text.toString().trim(),
                        weight = null,
                        withFertilizer = null,
                        withPot = null,
                        categoryId = flowerTypeId,
                        sellerId = AppCache.getHelper().userId,
                        department = departmentId,
                        shopId = null,
                        cityId = cityId,
                        regionId = regionId,
                        myAnnounce = true,
                        topNumber = 0,
                        phoneNumber =
                        "+998${binding.etNumber.text.trim().toString()
                            .replace("\\s".toRegex(), "")
                        }",
                        seller = isSeller,
                        id = null,
                        createAt = null,
                    ),
                    "desId" to 1,
                )
                val navController =
                    Navigation.findNavController(
                        requireActivity(),
                        R.id.addContainer
                    )
                navController.navigate(R.id.addPot_to_potDetails, bundle)
            }
        }
        imageResultObserve()
        regionResultObserve()
        cityResultObserve()
        typeResultObserve()
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
                if (regionIdList.isNotEmpty()){
                    regionId = regionIdList[position]
                    addPotViewModel.getCity(regionId!!)
                }
                spRegionPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spMaterialType.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                flowerTypeId = flowerTypeList[position]
                spFlowerPosition = position
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
                if (cityIdList.isNotEmpty()){
                    cityId = cityIdList[position]
                }
                spCityPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        if (regionIdList.isNotEmpty()){
            regionId = regionIdList[spRegionPosition]
            addPotViewModel.getCity(regionId!!)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    imageUrlList.clear()
                    CoroutineScope(Dispatchers.IO).launch {
                        img1 = null
                        img2 = null
                        img3 = null
                        img4 = null
                        img5 = null
                        img6 = null
                        img7 = null
                        img8 = null
                    }
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
        addPotViewModel.result.observe(viewLifecycleOwner, Observer {
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
                    addPotViewModel.setAnnounce(
                        AnnounceRequestData(
                            active = false,
                            allowed = false,
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
                                .replace("\\s".toRegex(), "").replace(",", "").replace(".", "").toLong(),
                            title = binding.etTitle.text.toString().trim(),
                            weight = null,
                            withFertilizer = null,
                            withPot = null,
                            categoryId = flowerTypeId,
                            sellerId = AppCache.getHelper().userId,
                            department = departmentId,
                            shopId = null,
                            cityId = cityId,
                            regionId = regionId,
                            myAnnounce = true,
                            topNumber = 0,
                            phoneNumber =
                            "+998${binding.etNumber.text.trim().toString()
                                .replace("\\s".toRegex(), "")
                            }",
                            seller = isSeller
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
        addPotViewModel.resultAnnounce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    val navController =
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.addContainer
                        )
                    navController.navigate(R.id.addPot_to_addSuccess)
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
    private fun typeResultObserve() {
        addPotViewModel.getTypeData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    it.data?.forEach {
                        flowerNameList.add(it.name)
                        flowerTypeList.add(it.id)
                    }
                    setAdapter(binding.spMaterialType,flowerNameList)
                    flowerTypeId = flowerTypeList[0]
                    binding.spMaterialType.setSelection(spFlowerPosition)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun regionResultObserve() {
        val list:ArrayList<String> = arrayListOf()
        addPotViewModel.regionList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    it.data?.forEach {
                        list.add(it.name)
                        regionIdList.add(it.id)
                    }
                    if (regionIdList.isNotEmpty()){
                        regionId = regionIdList[0]
                    }
                    setAdapter(binding.spVilList, list)
                    binding.spVilList.setSelection(spRegionPosition)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun cityResultObserve() {
        val list:ArrayList<String> = arrayListOf()
        addPotViewModel.cityData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    it.data?.forEach {
                        list.add(it.name)
                        cityIdList.add(it.id)
                    }
                    if (cityIdList.isNotEmpty()){
                        cityId = cityIdList[0]
                    }
                    setAdapter(binding.spTumanList, list)
                    binding.spTumanList.setSelection(spCityPosition)
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
        val cursor = (activity as AddAnnounceActivity).contentResolver.query(uri, filePathColumn, null, null, null)
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