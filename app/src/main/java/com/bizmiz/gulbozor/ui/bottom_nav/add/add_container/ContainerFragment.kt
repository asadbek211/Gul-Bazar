package com.bizmiz.gulbozor.ui.bottom_nav.add.add_container

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity

class ContainerFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(requireActivity(),AddAnnounceActivity::class.java))
        requireActivity().finish()
    }
}