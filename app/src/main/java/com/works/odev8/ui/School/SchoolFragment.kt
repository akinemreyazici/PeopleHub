package com.works.odev8.ui.School

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.works.odev8.adapters.PersonListAdapter
import com.works.odev8.databinding.FragmentSchoolBinding
import com.works.odev8.models.Person
import com.works.odev8.ui.Family.FamilyViewModel
import com.works.odev8.ui.Homepage.HomepageFragmentDirections


class SchoolFragment : Fragment() {

    private var _binding: FragmentSchoolBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SchoolViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(SchoolViewModel::class.java)

        _binding = FragmentSchoolBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val schoolListView = binding.SchoolListView
        // Observe the data in viewModel
        viewModel.list.observe(viewLifecycleOwner) {
            val adapter = PersonListAdapter(this, it)
            schoolListView.adapter = adapter
        }

        fun navigateToPersonDetailFragment(id: Int) {
            val action = SchoolFragmentDirections.actionNavSchoolToPersonDetailFragment(id)
            findNavController().navigate(action)
        }
        schoolListView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPerson = schoolListView.getItemAtPosition(i) as Person
            if (selectedPerson.id != null) {
                navigateToPersonDetailFragment(selectedPerson.id)
            }
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.schoolFetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}