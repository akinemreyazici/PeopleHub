package com.works.odev8.ui.Family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.works.odev8.MainActivity
import com.works.odev8.configs.AppDatabase
import com.works.odev8.models.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FamilyViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Person>>()
    val list: LiveData<List<Person>> get() = _list

    var thread: Thread? = null

    init {
        familyFetchData()
    }

    fun familyFetchData()
    {
        thread = Thread {
            val db = MainActivity.db
            val data = db.personDao().getSelectedGroupPerson("Family")
            _list.postValue(data)
        }
        thread?.start()
    }

    override fun onCleared() {
        super.onCleared()
        thread?.interrupt()
    }
}

