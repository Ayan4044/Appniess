package com.ayan.appniess

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayan.appniess.adapter.ContactListRecyclerAdapter
import com.ayan.appniess.databinding.FragmentSecondBinding
import com.ayan.appniess.model.DataClassHierarchy
import com.ayan.appniess.model.DataClassHierarchyList
import com.ayan.appniess.model.HeirarchyList
import com.ayan.appniess.viewmodel.MainViewModel
import com.google.gson.Gson


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), LifecycleObserver {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var contactList: ArrayList<HeirarchyList>
    private lateinit var recyclerviewContactList: ContactListRecyclerAdapter


    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactList = ArrayList()
        recyclerviewContactList = ContactListRecyclerAdapter(requireContext(), contactList)
        binding.constraintLayoutSearch.edtTextCommon.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                recyclerviewContactList.filter.filter(binding.constraintLayoutSearch.edtTextCommon.text.toString())
            }

        })


        recyclerviewContactList.onContactListClickListener(object : ContactListRecyclerAdapter.OnItemClickListener {
            override fun callItemClickListener(pos: Int) {
               println("Call Clicked")
                val contact = contactList[pos]
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${contact.contactNumber}")
                startActivity(intent)
            }

            override fun messageClicklistener(pos: Int) {
                val contact = contactList[pos]


                val sms_uri = Uri.parse("smsto:${contact.contactNumber}")
                val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)


                startActivity(sms_intent)
            }

        })

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStarted(){
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        mainViewModel.liveDataContactList.observe(viewLifecycleOwner) { liveDataContactList ->
            if (liveDataContactList != null) {
                println("Body: ${Gson().toJson(liveDataContactList)}")

                contactList.clear()
                try {
                    val dataObject: ArrayList<DataClassHierarchy> = liveDataContactList.dataObject

                    var obj: DataClassHierarchy? = null

                    var heirarchyList: ArrayList<DataClassHierarchyList> = ArrayList()

                    for (dataobj in dataObject) {

                        obj = dataobj

                    }

                    if (obj != null) {
                        heirarchyList = obj.myHierarchy
                        //  println("Items ${Gson().toJson(heirarchyList[0])}")
                        for (list in heirarchyList) {
                            println("Items ${Gson().toJson(list.heirarchyList)}")
                            contactList.addAll(list.heirarchyList)


                        }
                        layoutManager = LinearLayoutManager(requireContext())
                        binding.contactListRecycler.layoutManager = layoutManager

                        binding.contactListRecycler.adapter = recyclerviewContactList
                    }

                    }
                    catch (ex: Exception){
                        Toast.makeText(requireContext(), "Something went wrong!!",Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}