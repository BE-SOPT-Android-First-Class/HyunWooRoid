package com.l2hyunwoo.android.presentation.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.base.BindingActivity
import com.l2hyunwoo.android.databinding.ActivitySignInBinding
import com.l2hyunwoo.android.presentation.main.MainActivity
import com.l2hyunwoo.android.presentation.signup.SignUpActivity
import com.l2hyunwoo.android.presentation.util.toast

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        androidInjector.maybeInject(this)
        binding.lifecycleOwner = this
        binding.viewModel = signInViewModel
        LifeCycleEventLogger(javaClass.simpleName).registerLogger(this.lifecycle)
        initView()
    }

    private fun initView() {
        setUIListener()
        subscribeData()
    }

    private fun setUIListener() {
        val signUpActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK)
                    toast("회원가입이 완료되었습니다.")
            }

        binding.btnSigninSignup.setOnClickListener {
            signUpActivityLauncher.launch(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun subscribeData() {
        with(signInViewModel) {
            loginResult.observe(this@SignInActivity) { handleLoginResult(it) }
        }
    }

    private fun handleLoginResult(loginResult: LoginResult) {
        when (loginResult) {
            is NoneId -> toast("ID가 존재하지 않습니다.")
            is Inconsistent -> toast("ID와 비밀번호가 일치하지 않습니다.")
            is Valid -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("sample", ANY_NUMBER)
                toast("로그인이 되었습니다.")
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        // 다음 Activity에 값이 넘어가는 지 확인하기 위한 Int value
        const val ANY_NUMBER = 100
    }
}
