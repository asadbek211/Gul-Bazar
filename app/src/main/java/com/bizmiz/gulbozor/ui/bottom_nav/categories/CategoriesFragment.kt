package com.bizmiz.gulbozor.ui.bottom_nav.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {
    private var adapter: ExListAdapter? = null
    private var listNumbers: List<String>? = null
    private var listContacts: HashMap<String, Contacto>? = null
    private var lastExpandedPosition = -1

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initM()
        binding.expandableListView.setAdapter(adapter)
        binding.expandableListView.setOnGroupExpandListener { grPosition ->
            if (lastExpandedPosition != -1 && grPosition != lastExpandedPosition) {
                binding.expandableListView.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = grPosition
        }

        // TODO: expandable 

    }

    private fun initM() {

        listContacts = getContacts()
        listNumbers = ArrayList(listContacts!!.keys)
        adapter = ExListAdapter(requireContext(), listNumbers, listContacts)
    }

    private fun getContacts(): java.util.HashMap<String, Contacto> {
        val listC = java.util.HashMap<String, Contacto>()
        listC["Gullar"] = Contacto(
            "Gullar",
            "Buketli gullar", "Yer gullar",
            "Tuvak gullar"
        )
        listC["Daraxt"] = Contacto(
            "Daraxt ko'chatlari",
            "Archalar",
            "Mevali daraxtlar",
            "Manzarali daraxtlar"
        )
        listC["O'g'it"] = Contacto(
            "O'g'it va tuvaklar",
            "O'g'itlar", "Tuvaklar",
            "Last one"
        )

        return listC
    }
}