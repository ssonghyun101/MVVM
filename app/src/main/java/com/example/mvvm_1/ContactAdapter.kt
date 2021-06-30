package com.example.mvvm_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_1.databinding.ItemContactBinding
import com.example.mvvm_1.rom.Contact

class ContactAdapter(val contactItemClick: (Contact) -> Unit, val contactItemLongClick: (Contact) -> Unit )
    : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

        private var contacts : List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {

       val binding = ItemContactBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(contacts[position])
    }

    inner class ViewHolder(
        private val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(contact : Contact)
        {

            binding.itemTvInitial.text = contact.initial.toString()
            binding.itemTvName.text = contact.name
            binding.itemTvNumber.text = contact.number

            itemView.setOnClickListener {
                contactItemClick(contact)
                true
            }

            itemView.setOnLongClickListener {

                contactItemLongClick(contact)
                true

            }



        }
    }
// 뷰 갱신할 떄 사용
    //데이터베이스 변경될떄마다 호출
    fun setContacts(contacts : List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }




}

