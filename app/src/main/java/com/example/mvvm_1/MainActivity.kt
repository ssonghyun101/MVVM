package com.example.mvvm_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Property.of
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_1.databinding.ActivityMainBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.format.DecimalStyle.of
import java.time.temporal.WeekFields.of
import java.util.EnumSet.of
import java.util.List.of
import java.util.Map.of
import java.util.Optional.of
import java.util.stream.Stream.of

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val adapter = ContactAdapter({contact ->
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra(AddActivity.EXTAR_CONTACT_NAME,contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER,contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID,contact.id)
            startActivity(intent)
        }, { contact ->
            deleteDialog(contact)
        })



        val lm = LinearLayoutManager(this)
        binding.mainRecycleview.adapter = adapter
        binding.mainRecycleview.layoutManager = lm
        binding.mainRecycleview.setHasFixedSize(true)


        contactViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contract ->
            adapter.setContacts(contract!!)

        })

        binding.mainButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)


        }
    }

    @InternalCoroutinesApi
    private fun deleteDialog(contact: Contact){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("삭제할거냐?")
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("YES") {_, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}