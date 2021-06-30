package com.example.mvvm_1

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mvvm_1.rom.Contact
import com.example.mvvm_1.rom.ContactDao
import com.example.mvvm_1.rom.ContactDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.Exception

@InternalCoroutinesApi
class ContactRepository(application: Application) {

    @InternalCoroutinesApi
    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao : ContactDao = contactDatabase.contactDao()
    private val contacts : LiveData<List<Contact>> = contactDao.getAll()

    fun getAll() : LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact){
        try {
            val thread = Thread(Runnable {

                contactDao.insert(contact) })
            thread.start()
        } catch (e: Exception) {}

    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e : Exception) { }
    }

}