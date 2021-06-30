@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.mvvm_1.rom

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm_1.rom.Contact

@Dao

interface ContactDao {
    annotation class Dao


    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll() : LiveData<List<Contact>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}


