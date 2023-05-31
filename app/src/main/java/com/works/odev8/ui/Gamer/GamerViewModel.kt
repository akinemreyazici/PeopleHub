package com.works.odev8.ui.Gamer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.odev8.MainActivity
import com.works.odev8.models.Person

class GamerViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Person>>()
    val list: LiveData<List<Person>> get() = _list

    var thread: Thread? = null

    init {
        gamerFetchData()
    }

    fun gamerFetchData()
    {
        thread = Thread {
            val db = MainActivity.db
            val data = db.personDao().getSelectedGroupPerson("Gamer")
            _list.postValue(data)
        }
        thread?.start()
    }
}