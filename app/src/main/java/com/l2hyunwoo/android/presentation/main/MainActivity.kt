package com.l2hyunwoo.android.presentation.main

import android.os.Bundle
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingActivity
import com.l2hyunwoo.android.databinding.ActivityMainBinding
import com.l2hyunwoo.android.presentation.signin.SignInActivity
import com.l2hyunwoo.android.presentation.util.intExtra
import com.l2hyunwoo.android.presentation.util.toast

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val homeViewModel: HomeViewModel by viewModel()
    private val sample by intExtra(ERROR_CODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel
        checkCorrectTransfer()
        LifeCycleEventLogger(javaClass.simpleName).log()
    }

    private fun checkCorrectTransfer() =
        if (sample == SignInActivity.ANY_NUMBER) toast("CORRECT") else toast("ERROR")

    companion object {
        private const val ERROR_CODE = -1
    }
}
