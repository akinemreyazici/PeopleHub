package com.works.odev8.ui.School

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.works.odev8.MainActivity
import com.works.odev8.models.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchoolViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Person>>()
    val list: LiveData<List<Person>> get() = _list

    var thread: Thread? = null

    init {
        schoolFetchData()
    }

    fun schoolFetchData()
    {
        thread = Thread {
            val db = MainActivity.db
            val data = db.personDao().getSelectedGroupPerson("School")
            _list.postValue(data)
        }
        thread?.start()
    }

    override fun onCleared() {
        super.onCleared()
        thread?.interrupt()
    }
}