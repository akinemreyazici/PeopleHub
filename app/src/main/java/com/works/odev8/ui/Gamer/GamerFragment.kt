package com.works.odev8.ui.Gamer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.works.odev8.R
import com.works.odev8.adapters.PersonListAdapter
import com.works.odev8.databinding.FragmentFriendsBinding
import com.works.odev8.databinding.FragmentGamerBinding
import com.works.odev8.models.Person
import com.works.odev8.ui.Family.FamilyFragmentDirections

class GamerFragment : Fragment() {

    private var _binding: FragmentGamerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GamerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GamerViewModel::class.java)
        _binding = FragmentGamerBinding.inflate(inflater, container, false)
        val root = binding.root

        val gamerListView = binding.GamerListView
        viewModel.list.observe(viewLifecycleOwner) {
            val adapter = PersonListAdapter(this, it)
            gamerListView.adapter = adapter
        }
        fun navigateToPersonDetailFragment(id: Int) {
            val action = GamerFragmentDirections.actionNavGamerToPersonDetailFragment(id)
            findNavController().navigate(action)
        }

        gamerListView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPerson = gamerListView.getItemAtPosition(i) as Person
            if (selectedPerson.id != null) {
                navigateToPersonDetailFragment(selectedPerson.id)
            }
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.gamerFetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}