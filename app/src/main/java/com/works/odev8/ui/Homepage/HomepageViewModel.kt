package com.works.odev8.ui.Homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.works.odev8.MainActivity
import com.works.odev8.models.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomepageViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Person>>()
    val list: LiveData<List<Person>> get() = _list

    var thread: Thread? = null

    init {
        fetchData()
    }

    fun fetchData()
    {
        thread = Thread {
            val db = MainActivity.db
            val data = db.personDao().getLastTenNotes()
            _list.postValue(data)
        }
        thread?.start()
    }

    fun searchData(search : String)
    {
        thread = Thread {
            val db = MainActivity.db
            val query = db.personDao().searchByWord(search)
            _list.postValue(query)
        }
        thread?.start()
    }

    override fun onCleared() {
        super.onCleared()
        thread?.interrupt()
    }

}