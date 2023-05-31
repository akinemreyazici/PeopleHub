package com.works.odev8.ui.PersonDetail.PersonUpdate

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.works.odev8.MainActivity
import com.works.odev8.R
import com.works.odev8.databinding.FragmentPersonUpdateBinding
import com.works.odev8.models.Person
import com.works.odev8.ui.Homepage.HomepageViewModel
import com.works.odev8.ui.PersonDetail.PersonDetailFragmentArgs


class PersonUpdateFragment : Fragment() {

    private val argument: PersonUpdateFragmentArgs by navArgs()

    private var _binding: FragmentPersonUpdateBinding? = null
    private val binding get() = _binding!!

    var thread: Thread? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonUpdateBinding.inflate(inflater, container, false)
        val root = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personId = argument.id


        val nameUpdateEditText = binding.nameUpdateEditText
        val surnameUpdateEditText = binding.surnameUpdateEditText
        val ageUpdateEditText = binding.ageUpdateEditText
        val emailUpdateEditText = binding.emailUpdateEditText
        val phoneUpdateEditText = binding.phoneUpdateEditText
        val groupUpdateSpinner = binding.groupUpdateSpinner
        val btnUpdate = binding.updateButton



        thread = Thread {
            val db = MainActivity.db
            val person = db.personDao().getByID(personId)
            val groupNames = resources.getStringArray(R.array.group_names)
            val spinnerPosition =
                groupNames.indexOf(person?.groupName) // Spinnerda indexe göre seçildiğinden array'de grup elemanın kaçıncı indexde olduğunub ulup değişkende tutuyoruz.

            activity?.runOnUiThread {
                nameUpdateEditText.setText(person?.name)
                surnameUpdateEditText.setText(person?.surname)
                ageUpdateEditText.setText(person?.age.toString())
                emailUpdateEditText.setText(person?.email)
                phoneUpdateEditText.setText(person?.phone)
                groupUpdateSpinner.setSelection(spinnerPosition)
            }
        }
        thread?.start()

        btnUpdate.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to update this person?")
                .setPositiveButton("Yes") { _, _ ->
                    val updatedName = nameUpdateEditText.text.toString()
                    val updatedSurname = surnameUpdateEditText.text.toString()
                    val updatedAge = ageUpdateEditText.text.toString().toInt()
                    val updatedEmail = emailUpdateEditText.text.toString()
                    val updatedPhone = phoneUpdateEditText.text.toString()
                    val updatedGroup = groupUpdateSpinner.selectedItem.toString()
                    val updatedPerson = Person(
                        personId,
                        updatedGroup,
                        updatedName,
                        updatedSurname,
                        updatedAge,
                        updatedEmail,
                        updatedPhone
                    )
                    Thread {
                        MainActivity.db.personDao().update(updatedPerson)
                        activity?.runOnUiThread {
                            findNavController().popBackStack()
                        }
                    }.start()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        thread?.interrupt()
    }

}