package com.works.odev8.ui.AddPerson

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.works.odev8.MainActivity
import com.works.odev8.databinding.FragmentAddPersonBinding
import com.works.odev8.models.Person


class AddPersonFragment : Fragment() {

    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPersonBinding.inflate(inflater, container, false)

        val nameEditText = binding.nameEditText
        val surnameEditText = binding.surnameEditText
        val ageEditText = binding.ageEditText
        val emailEditText = binding.emailEditText
        val phoneEditText = binding.phoneEditText
        val groupSpinner = binding.groupSpinner
        val btnSave = binding.saveButton

        btnSave.setOnClickListener {
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()
            val age = ageEditText.text.toString().toInt()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val group = groupSpinner.selectedItem.toString()


            val person = Person(null, group, name, surname, age, email, phone)
            fun SaveData() {

                var thread: Thread?
                thread = Thread {
                    val db = MainActivity.db
                    db.personDao().addPerson(person)
                }
                thread.start()
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to add this person?")
                .setPositiveButton("Yes") { _, _ ->
                    SaveData()
                    findNavController().popBackStack()
                    Snackbar.make(
                        requireView(),
                        "Person has been added successfully.",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null).show()
                }
                .setNegativeButton("No", null)
                .show()
        }
        return binding.root
    }


}