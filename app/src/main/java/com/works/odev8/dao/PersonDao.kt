package com.works.odev8.dao

import androidx.room.*
import com.works.odev8.models.Person

@Dao
interface PersonDao {

    @Insert
    fun addPerson(person: Person): Long // Yeni kişi ekleme

    @Query("select * from person") // Tüm listeyi getirmesi için
    fun getAllPerson(): List<Person>

    @Query("SELECT * FROM person ORDER BY id DESC LIMIT 10") // En son eklenen 10 kişiyi gösterme Homepage de default gözükecek
    fun getLastTenNotes(): List<Person>

    @Query("select * from person where groupName like :groupName ") // Grubuna göre liste getirme yan sekmelerde buna göre kullanıcam
    fun getSelectedGroupPerson(groupName: String): List<Person>

    @Query("SELECT * FROM person WHERE groupName LIKE '%' || :search || '%' OR name LIKE '%' || :search || '%' OR surname LIKE '%' || :search || '%' OR phone LIKE '%' || :search || '%' OR email LIKE '%' || :search || '%'")
    fun searchByWord(search: String?): List<Person>

    @Query("select * from person where id like :id") // Burada detay sayfasına giderken ID'sini fragment'a yollayıp bu fonksiyon ile bilgileri çekicem
    fun getByID(id: Int): Person

    @Delete
    fun delete(person: Person): Int // Detay sayfasında ilgili kişiyi silmek için

    @Update
    fun update(person: Person): Int // Detay sayfasında ilgili kişinin bilgilerini güncellemek için
}