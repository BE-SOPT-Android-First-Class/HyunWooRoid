package com.l2hyunwoo.android.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingActivity
import com.l2hyunwoo.android.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel
    }
}
