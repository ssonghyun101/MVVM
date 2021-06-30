package com.example.mvvm_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_1.databinding.ActivityAddBinding
import com.example.mvvm_1.rom.Contact
import kotlinx.coroutines.InternalCoroutinesApi

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var contactViewModel: ContactViewModel
    private var id : Long? = null
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactViewModel = ViewModelProvider(this,defaultViewModelProviderFactory)
            .get(ContactViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTAR_CONTACT_NAME) && intent.hasExtra(EXTRA_CONTACT_NUMBER)
            && intent.hasExtra(EXTRA_CONTACT_ID)){
            binding.addEdittextName.setText(intent.getStringExtra(EXTAR_CONTACT_NAME))
            binding.addEdittextNumber.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        binding.addButton.setOnClickListener {



            val name = binding.addEdittextName.text.toString().trim()
            val number = binding.addEdittextNumber.text.toString()

            if(name.isEmpty() || number.isEmpty()) {
                Toast.makeText(this,"입력하라고",Toast.LENGTH_SHORT).show()
            }
            else{
                val initial = name[0].uppercase()
                val contact = Contact(id,name,number,initial)
                contactViewModel.insert(contact)
                finish()

            }
        }




    }

    companion object{
        const val EXTAR_CONTACT_NAME ="EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}