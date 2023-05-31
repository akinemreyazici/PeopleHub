package com.works.odev8.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.works.odev8.dao.PersonDao
import com.works.odev8.models.Person

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
}