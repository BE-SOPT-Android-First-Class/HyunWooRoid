package com.l2hyunwoo.android.presentation.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingActivity
import com.l2hyunwoo.android.databinding.ActivityMainBinding
import com.l2hyunwoo.android.presentation.signin.SignInActivity
import com.l2hyunwoo.android.presentation.util.intExtra
import com.l2hyunwoo.android.presentation.util.toast

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val sample by intExtra(ERROR_CODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        checkCorrectTransfer()
        setUpBottomNavigation()
        LifeCycleEventLogger(javaClass.simpleName).log()
    }

    private fun setUpBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBnv.setupWithNavController(navController)
    }

    private fun checkCorrectTransfer() =
        if (sample == SignInActivity.ANY_NUMBER) toast("CORRECT") else toast("ERROR")

    companion object {
        private const val ERROR_CODE = -1
    }
}
