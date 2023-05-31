package com.works.odev8.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,

    val groupName : String?, // Family , Business , School , Gamer , Friends
    val name : String?,
    val surname : String?,
    val age : Int?,
    val email : String?,
    val phone : String?
    )
