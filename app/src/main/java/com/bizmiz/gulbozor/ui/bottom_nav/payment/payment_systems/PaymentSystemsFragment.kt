package com.bizmiz.gulbozor.ui.bottom_nav.payment.payment_systems

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentPaymentSystemsBinding
import com.bumptech.glide.Glide

class PaymentSystemsFragment : Fragment(R.layout.fragment_payment_systems), View.OnClickListener {
    private val binding by viewBinding { FragmentPaymentSystemsBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val price = requireArguments().getString("price")
        val paymentId = requireArguments().getString("paymentId")
        setImage(binding.btnPayAnorBank, "https://logobank.uz:8005/media/logos_png/Anorbank-01.png")
        setImage(binding.btnPayApelsin, "https://logobank.uz:8005/media/logos_png/Apelsin-01.png")
        setImage(binding.btnPayClick, "https://logobank.uz:8005/media/logos_png/click-01.png")
        setImage(binding.btnPayPayMe, "https://logobank.uz:8005/media/logos_png/payme-01.png")
        setImage(binding.btnPayUzCard, "https://logobank.uz:8005/media/logos_png/Uzcard-01.png")
        setImage(binding.btnPayHumo, "https://logobank.uz:8005/media/logos_preview/Humo-01.jpg")
        binding.tvPayPrice.text = "To‘lov miqdori:    $price so’m"
        binding.tvPayPrice.text = "To‘lov raqami:    $paymentId"
        onClickInstalization()
    }

    private fun setImage(img: ImageView, url: String) {
        Glide.with(requireContext()).load(url)
            .into(img)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPayAnorBank -> {
                navigation()
            }
            R.id.btnPayApelsin -> {
                navigation()
            }
            R.id.btnPayClick -> {
                navigation()
            }
            R.id.btnPayPayMe -> {
                navigation()
            }
            R.id.btnPayUzCard -> {
                navigation()
            }
            R.id.btnPayHumo -> {
                navigation()
            }
        }
    }

    private fun navigation() {
        val navController =
            Navigation.findNavController(
                requireActivity(),
                R.id.payContainer
            )
        navController.navigate(R.id.action_paymentSystems_to_successPay)
    }

    private fun onClickInstalization() {
        binding.btnPayAnorBank.setOnClickListener(this)
        binding.btnPayApelsin.setOnClickListener(this)
        binding.btnPayClick.setOnClickListener(this)
        binding.btnPayPayMe.setOnClickListener(this)
        binding.btnPayUzCard.setOnClickListener(this)
        binding.btnPayHumo.setOnClickListener(this)
    }
}