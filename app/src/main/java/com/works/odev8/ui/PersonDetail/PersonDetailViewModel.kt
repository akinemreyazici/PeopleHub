package com.works.odev8.ui.PersonDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.odev8.MainActivity
import com.works.odev8.models.Person

class PersonDetailViewModel : ViewModel() {

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person> get() = _person

    var thread: Thread? = null

    fun getSelectedPerson(id: Int) {
        thread = Thread {
            val db = MainActivity.db
            val selectedPerson = db.personDao().getByID(id)
            _person.postValue(selectedPerson)
        }
        thread?.start()
    }

    fun deletePerson(person: Person) {
        thread = Thread {
            val db = MainActivity.db
            val selectedPerson = db.personDao().delete(person)
        }
        thread?.start()
    }

    override fun onCleared() {
        super.onCleared()
        thread?.interrupt()
    }
}