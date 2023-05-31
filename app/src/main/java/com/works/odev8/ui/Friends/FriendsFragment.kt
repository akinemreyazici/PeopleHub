package com.works.odev8.ui.Friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.works.odev8.adapters.PersonListAdapter
import com.works.odev8.databinding.FragmentFriendsBinding
import com.works.odev8.models.Person
import com.works.odev8.ui.Homepage.HomepageFragmentDirections

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(FriendsViewModel::class.java)

        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val friendsListView = binding.FriendsListView
        viewModel.list.observe(viewLifecycleOwner) {
            val adapter = PersonListAdapter(this, it)
            friendsListView.adapter = adapter
        }

        fun navigateToPersonDetailFragment(id: Int) {
            val action = FriendsFragmentDirections.actionNavFriendsToPersonDetailFragment(id)
            findNavController().navigate(action)
        }

        friendsListView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPerson = friendsListView.getItemAtPosition(i) as Person
            if (selectedPerson.id != null) {
                navigateToPersonDetailFragment(selectedPerson.id)
            }
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.friendsFetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}