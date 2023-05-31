package com.works.odev8.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.works.odev8.R
import com.works.odev8.models.Person
import com.bumptech.glide.Glide
import com.works.odev8.common.GlideImage

class PersonListAdapter(
    private val fragment: Fragment,
    private val list: List<Person>
) : ArrayAdapter<Person>(fragment.requireContext(), R.layout.person_list, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = fragment.layoutInflater.inflate(R.layout.person_list,null,true)

        val personName = root.findViewById<TextView>(R.id.r_personName)
        val personPhone = root.findViewById<TextView>(R.id.r_personPhone)
        val personImg = root.findViewById<ImageView>(R.id.r_personImg)

        val person = list.get(position)
        val fullName = person.name + " " + person.surname
        personName.text = fullName
        personPhone.text = person.phone


        val familyImg = GlideImage.familyImg
        val schoolImg = GlideImage.schoolImg
        val businessImg = GlideImage.businessImg
        val gamerImg = GlideImage.gamerImg
        val friendsImg = GlideImage.friendsImg
        val defaultImg = GlideImage.defaultImg

        val imageLink = when (person.groupName) {
            "Family" -> familyImg
            "School" -> schoolImg
            "Business" -> businessImg
            "Gamer" -> gamerImg
            "Friends" -> friendsImg
            else -> defaultImg // Eğer hiçbir grup seçilmezse default simge gözükecektir.
        }

        Glide.with(fragment.requireContext())
            .load(imageLink)
            .into(personImg)

        return root
    }
}
