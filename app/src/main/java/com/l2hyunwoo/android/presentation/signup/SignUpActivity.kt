package com.l2hyunwoo.android.presentation.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingActivity
import com.l2hyunwoo.android.databinding.ActivitySignUpBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel: SignUpViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel
        LifeCycleEventLogger(javaClass.simpleName).registerLogger(this.lifecycle)
        subscribeEvent()
    }

    private fun subscribeEvent() {
        signUpViewModel.signUpEvent.observe(this) {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
