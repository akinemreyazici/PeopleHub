package com.works.odev8.ui.Homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.works.odev8.adapters.PersonListAdapter
import com.works.odev8.databinding.FragmentHomepageBinding
import com.works.odev8.models.Person


class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomepageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(HomepageViewModel::class.java)

        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val personList = binding.HomepageListView
        val searchEditText = binding.searchEditText
        val btnSearch = binding.btnSearch

        viewModel.list.observe(viewLifecycleOwner) {
            val adapter = PersonListAdapter(this, it)
            personList.adapter = adapter
        }

        btnSearch.setOnClickListener {
            val search = searchEditText.text.toString()
            if (search.isBlank()) {
                viewModel.fetchData()
            } else {
                viewModel.searchData(search)
            }
        }
        fun navigateToPersonDetailFragment(id: Int) {
            val action = HomepageFragmentDirections.actionNavHomepageToPersonDetailFragment(id)
            findNavController().navigate(action)
        }

        personList.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPerson = personList.getItemAtPosition(i) as Person
            if (selectedPerson.id != null) {
                navigateToPersonDetailFragment(selectedPerson.id)
            }
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}