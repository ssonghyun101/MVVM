package com.example.mvvm_1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mvvm_1.rom.Contact
import kotlinx.coroutines.InternalCoroutinesApi

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    @InternalCoroutinesApi
    private val repository = ContactRepository(application)
    @InternalCoroutinesApi
    private val contacts = repository.getAll()

    @InternalCoroutinesApi
    fun getAll() : LiveData<List<Contact>> {
        return this.contacts
    }

    @InternalCoroutinesApi
    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    @InternalCoroutinesApi
    fun delete(contact: Contact) {

        repository.delete(contact)
    }


}