package com.example.mvvm_1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Contact::class],version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object{

        private var INSTANCE : ContactDatabase? = null
        @InternalCoroutinesApi
        fun getInstance(context : Context) : ContactDatabase? {

            if(INSTANCE == null){

                synchronized(ContactDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ContactDatabase::class.java, "contact")
                        .fallbackToDestructiveMigration()
                        .build()



                }
            }
            return INSTANCE

        }
    }
}