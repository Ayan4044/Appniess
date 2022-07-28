package com.ayan.appniess

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.ayan.appniess.databinding.FragmentFirstBinding
import com.ayan.appniess.model.DataClassList
import com.ayan.appniess.model.DataClassResponseWrapper
import com.ayan.appniess.viewmodel.MainViewModel
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), LifecycleObserver {


    //viewmodel
    private  lateinit var viewModel: MainViewModel


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStarted() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.liveResponseWrapper.observe(viewLifecycleOwner){
            dataClassResponseWrapper ->
                if(dataClassResponseWrapper != null)
                {
                    when(dataClassResponseWrapper.statusCode)
                    {
                        1 ->   viewModel.liveDataContactList.postValue(dataClassResponseWrapper.response as DataClassList)
                        -2 -> Toast.makeText(
                        requireContext(),
                        "Something went wrong!!",
                        Toast.LENGTH_SHORT)
                    }
                }
        }

        viewModel.liveDataContactList.observe(viewLifecycleOwner) { liveDataCryptoList ->
            if (liveDataCryptoList != null) {
                println("Body: ${Gson().toJson(liveDataCryptoList)}")
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

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