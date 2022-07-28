package com.ayan.appniess.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayan.appniess.R
import com.ayan.appniess.model.HeirarchyList
import com.google.android.material.button.MaterialButton

class ContactListRecyclerAdapter ( val context: Context,val dataset: ArrayList<HeirarchyList>):
    RecyclerView.Adapter<ContactListRecyclerAdapter.ContactAdapter>(), Filterable
{

    var contactFilterList = ArrayList<HeirarchyList>()


    init {
        contactFilterList = dataset as ArrayList<HeirarchyList>
    }

    private var ItemClickListener:  OnItemClickListener? = null

    interface OnItemClickListener{
        abstract fun callItemClickListener(pos: Int)
        abstract fun messageClicklistener(pos: Int)

    }

    fun onContactListClickListener(ontemClickListener:OnItemClickListener){
        ItemClickListener = ontemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ContactAdapter(LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false),
        ItemClickListener
    )

    class ContactAdapter(inflate: View, ItemClickListener: OnItemClickListener?): RecyclerView.ViewHolder(inflate){
        var textViewName = inflate.findViewById<TextView>(R.id.textViewName)
        var textviewDesignation = inflate.findViewById<TextView>(R.id.textviewdeisgnation)

        var buttonCall: ImageView = inflate.findViewById(R.id.buttoncall)

        var buttonMessage: ImageView = inflate.findViewById(R.id.buttonmessage)

        init {
            buttonCall.setOnClickListener(View.OnClickListener {
                if (ItemClickListener != null) {
                    val pos:Int = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        ItemClickListener.callItemClickListener(pos)
                    }
                }
            })

            buttonMessage.setOnClickListener(View.OnClickListener {
                if (ItemClickListener != null) {
                    val pos:Int = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        ItemClickListener.messageClicklistener(pos)
                    }
                }
            })
        }


    }

    override fun onBindViewHolder(holder: ContactListRecyclerAdapter.ContactAdapter, position: Int) {
        val distItem = contactFilterList[position]
        holder.textViewName.text = distItem.contactName
        holder.textviewDesignation.text = distItem.designationName
    }

    fun returnNewList():ArrayList<HeirarchyList> = contactFilterList

    override fun getItemCount(): Int = contactFilterList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    contactFilterList = dataset as ArrayList<HeirarchyList>
                } else {
                    val resultList = ArrayList<HeirarchyList>()
                    for (row in dataset) {
                        if (row.contactName!!.lowercase().contains(constraint.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    contactFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contactFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                contactFilterList = results?.values as ArrayList<HeirarchyList>
                notifyDataSetChanged()
            }
        }
    }



}