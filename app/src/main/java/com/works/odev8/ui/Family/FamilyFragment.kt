package com.works.odev8.ui.Family

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
import com.works.odev8.ui.Friends.FriendsFragmentDirections
import com.works.odev8.ui.School.SchoolViewModel

class FamilyFragment : Fragment() {

    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FamilyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(FamilyViewModel::class.java)

        _binding = FragmentFamilyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val familyListView = binding.FamilyListView

        viewModel.list.observe(viewLifecycleOwner) { personListData ->
            val adapter = PersonListAdapter(this, personListData)
            familyListView.adapter = adapter
        }

        fun navigateToPersonDetailFragment(id: Int) {
            val action = FamilyFragmentDirections.actionNavFamilyToPersonDetailFragment(id)
            findNavController().navigate(action)
        }

        familyListView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPerson = familyListView.getItemAtPosition(i) as Person
            if (selectedPerson.id != null) {
                navigateToPersonDetailFragment(selectedPerson.id)
            }
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.familyFetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}