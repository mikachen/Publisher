package com.zoe.publisher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zoe.publisher.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding

    lateinit var viewModel: PostingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application

        viewModel = PostingViewModel(application)

        viewModel.postSuccess.observe(viewLifecycleOwner){
            if(it) {
                findNavController().navigate(R.id.FirstFragment)
            }
        }

        spinnersSetup()
        addDataBtnSetup()

        return binding.root
    }


    private fun addDataBtnSetup() {
        binding.postButton.setOnClickListener {
            if (UserManager.userToken != null) {
                val title = binding.edit1.text.toString()
                val content = binding.edit2.text.toString()
                viewModel.addNewPost(title = title, content = content)
            } else {
                Toast.makeText(context, "Please login first!!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_global_loginFragment)
            }
        }
    }

    private fun spinnersSetup() {

        val spinnerCategory = binding.categorySpinner
        val category = requireContext().resources.getStringArray(R.array.category_type)


        val categoryAdapter = object : ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, category
        ) {
            override fun getCount(): Int {
                return category.size - 1
            }
        }
        categoryAdapter.setDropDownViewResource(R.layout.cutom_spinner_view)
        spinnerCategory.adapter = categoryAdapter
        spinnerCategory.setSelection(category.size - 1)


        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val data = parent?.getItemAtPosition(position).toString()
                viewModel.slCategory(data)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).btnTitleSetup()
    }
}