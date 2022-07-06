package com.bizmiz.gulbozor.ui.bottom_nav.payment.ads_price

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentAdsCostsBinding
import com.bizmiz.gulbozor.ui.bottom_nav.payment.PaymentActivity
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class AdsCostsFragment : Fragment() {
    private var _binding: FragmentAdsCostsBinding? = null
    private val binding get() = _binding!!
    private var price:String = "11 000"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val data = (activity as PaymentActivity).getData()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.ads_color)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        _binding = FragmentAdsCostsBinding.inflate(inflater, container, false)
        binding.btnPay.text = when(price){
            "11 000"->{
                "3x    11 000 so’m        To’lov qilish"
            }
            "15 000"->{
                "5x    15 000 so’m        To’lov qilish"
            }
            "21 000"->{
                "10x   21 000 so’m        To’lov qilish"
            }
            else -> {""}
        }

        binding.checkBox1.setOnClickListener {
            threeCheckCheckbox(binding.checkBox1,binding.checkBox2,binding.checkBox3)
            binding.btnPay.text = "3x    11 000 so’m        To’lov qilish"
            price = "11 000"
        }
        binding.checkBox2.setOnClickListener {
            threeCheckCheckbox(binding.checkBox2,binding.checkBox1,binding.checkBox3)
            binding.btnPay.text = "5x    15 000 so’m        To’lov qilish"
            price = "15 000"
        }
        binding.checkBox3.setOnClickListener {
            threeCheckCheckbox(binding.checkBox3,binding.checkBox1,binding.checkBox2)
            binding.btnPay.text = "10x   21 000 so’m        To’lov qilish"
            price = "21 000"
        }
        val df = DecimalFormat("#,###.##")
        val number = df.format(data.price)
        binding.flowerPrice.text = number.replace(","," ").replace("."," ")
        binding.flowerName.text = data.title
        binding.flowerDescription.text = data.description
        Glide.with(binding.root.context).load(data.image1)
            .into(binding.flowerImage)
        binding.btnBack.setOnClickListener {
            requireActivity().finish()
        }
        binding.btnPay.setOnClickListener {
            val bundle = bundleOf(
                "price" to price,
                "paymentId" to data.id
            )
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.payContainer
                )
            navController.navigate(R.id.action_adsCosts_to_paymentSystems, bundle)
        }
        return binding.root
    }
    private fun threeCheckCheckbox(
        chb1: CheckBox,
        chb2: CheckBox,
        chb3: CheckBox
    ) {
        chb1.isChecked = true
        chb2.isChecked = false
        chb3.isChecked = false
    }
}