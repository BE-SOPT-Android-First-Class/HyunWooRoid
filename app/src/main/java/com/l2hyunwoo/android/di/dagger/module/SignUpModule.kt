package com.l2hyunwoo.android.di.dagger.module

import androidx.lifecycle.ViewModel
import com.l2hyunwoo.android.di.dagger.ViewModelBuilder
import com.l2hyunwoo.android.di.dagger.ViewModelKey
import com.l2hyunwoo.android.presentation.signin.SignInActivity
import com.l2hyunwoo.android.presentation.signin.SignInViewModel
import com.l2hyunwoo.android.presentation.signup.SignUpActivity
import com.l2hyunwoo.android.presentation.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SignUpModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun signUpActivity(): SignUpActivity

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindViewModel(viewModel: SignUpViewModel): ViewModel
}