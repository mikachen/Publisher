package com.zoe.publisher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zoe.publisher.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)

        val viewModel = BlogViewModel()
        val adapter = BlogAdapter()

        binding.articlesRecyclerView.adapter = adapter

        viewModel.articles.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        viewModel.refreshStatus.observe(viewLifecycleOwner)
        {
            it?.let {
                binding.layoutSwipeRefreshHome.isRefreshing = it
            }
        }

        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.getAllArticles()
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).btnTitleSetup()
    }
}