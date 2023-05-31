package com.works.odev8.ui.Business

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.works.odev8.R
import com.works.odev8.adapters.PersonListAdapter
import com.works.odev8.databinding.FragmentFamilyBinding
import com.works.odev8.models.Person
import com.works.odev8.ui.Family.FamilyFragmentDirections
import com.works.odev8.ui.Family.FamilyViewModel

class BusinessFragment : Fragment() {

    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)
        val root = binding.root

        val businessListView = binding.FamilyListView
        viewModel.list.observe(viewLifecycleOwner) {
            val adapter = PersonListAdapter(this, it)
            businessListView.adapter = adapter
        }

        fun navigateToPersonDetailFragment(id: Int) {
            val action = BusinessFragmentDirections.actionNavBusinessToPersonDetailFragment(id)
            findNavController().navigate(action)
        }

        businessListView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPerson = businessListView.getItemAtPosition(i) as Person
            if (selectedPerson.id != null) {
                navigateToPersonDetailFragment(selectedPerson.id)
            }
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.businessFetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}