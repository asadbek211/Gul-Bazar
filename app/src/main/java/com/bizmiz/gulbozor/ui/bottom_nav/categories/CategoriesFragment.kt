
package com.bizmiz.gulbozor.ui.bottom_nav.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment(R.layout.fragment_categories) {
    private val binding by viewBinding { FragmentCategoriesBinding.bind(it) }

    private var adapter: ExListAdapter? = null
    private var listNumbers: List<String>? = null
    private var listContacts: HashMap<String, Contacto>? = null
    private var lastExpandedPosition = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        windowStatus()
        initM()
        binding.expandableListView.setAdapter(adapter)
        binding.expandableListView.setOnGroupExpandListener { grPosition ->
            if (lastExpandedPosition != -1 && grPosition != lastExpandedPosition) {
                binding.expandableListView.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = grPosition
        }
        /*binding.expandableListView.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(
                requireContext(),
                " expanded",
                Toast.LENGTH_SHORT
            ).show()
            // TODO: expandable
        }*/

    }

    private fun initM() {
        listContacts = getContacts()
        listNumbers = ArrayList(listContacts!!.keys)
        adapter = ExListAdapter(requireContext(), listNumbers, listContacts)
        binding.homRoomTxt.setOnClickListener(View.OnClickListener {
            Toast.makeText(requireContext(), "IssiqXona qurish", Toast.LENGTH_SHORT).show()
        })
        binding.takeCareTxt.setOnClickListener(View.OnClickListener {
            Toast.makeText(requireContext(), "Parvarishlash", Toast.LENGTH_SHORT).show()
        })
        binding.shopsTxt.setOnClickListener(View.OnClickListener {
            Toast.makeText(requireContext(), "Do'konlar", Toast.LENGTH_SHORT).show()
        })
    }

    private fun getContacts(): java.util.HashMap<String, Contacto> {
        val listC = java.util.HashMap<String, Contacto>()
        listC["Gullar"] = Contacto(
            "Gullar",
            "Buketli gullar", "Yer gullar",
            "Tuvak gullar"
        )
        listC["O'g'it"] = Contacto(
            "O'g'it va tuvaklar",
            "O'g'itlar", "Tuvaklar",
            null
        )
        listC["Daraxt"] = Contacto(
            "Daraxt ko'chatlari",
            "Archalar",
            "Mevali daraxtlar",
            "Manzarali daraxtlar"
        )

        return listC
    }