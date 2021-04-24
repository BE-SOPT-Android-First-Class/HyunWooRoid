package com.l2hyunwoo.android.presentation.main.subscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingFragment
import com.l2hyunwoo.android.databinding.FragmentHomeBinding
import com.l2hyunwoo.android.presentation.main.HomeViewModel

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModel()
    private val repoListAdapter by lazy { RepositoryListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setRepoListAdapter()
        subscribeData()
    }

    private fun setRepoListAdapter() {
        binding.rvHomeRepos.adapter = repoListAdapter
        homeViewModel.getUserRepos("l2hyunwoo")
    }

    private fun subscribeData() {
        homeViewModel.userRepos.observe(viewLifecycleOwner) { repoListAdapter.submitList(it) }
    }
}